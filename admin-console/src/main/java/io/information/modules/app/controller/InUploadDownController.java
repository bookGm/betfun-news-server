package io.information.modules.app.controller;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import io.information.common.utils.R;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;
import io.information.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("app/uploadDown")
public class InUploadDownController {
    @Autowired
    ServerConfig serverConfig;

    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "***";
    String SECRET_KEY = "***";
    //要上传的空间名
    String bucketname = "";
    String key = "";

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    //创建上传对象
    UploadManager uploadManager = new UploadManager(new Configuration());


    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    /**
     * 文件上传
     */
    @PostMapping("/uploadPic")
    public R upload(@RequestParam("upload") MultipartFile picture, HttpServletRequest request) throws Exception {
        String contextPath=request.getContextPath();
        //获取文件在服务器的储存位置
        File path = new File(URLDecoder.decode(ResourceUtils.getURL("classpath:").getPath(),"utf-8"));
        if(!path.exists()) {
            path = new File("");
        }
//        String path=ResourceUtils.getURL("classpath:").getPath()+"static/upload/";
        File filePath = new File(path.getAbsolutePath()+"/static/upload/");
        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdirs();
        }
        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();
        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." + type;
        //在指定路径下创建一个文件
        File targetFile = new File(filePath, fileName);

        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            //将文件在服务器的存储路径返回
            return R.ok().put("url",serverConfig.getUrl()+"/upload/" + fileName).put("uploaded",1).put("formUrl","/upload/" + fileName);
        } catch (IOException e) {
        e.printStackTrace();
        return R.error("上传失败").put("uploaded",0);
        }
        }


    @PostMapping("/uploadDle")
    public R delete(String img) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath() + "static/upload/";
        boolean flag = FileUtils.deleteQuietly(new File(path + img));
        if (flag) {
            return R.ok();
        } else {
            return R.error("删除失败");
        }
    }

    /**
     * @param FilePath 本地文件路径
     * @return 保存的文件名称
     */
    public String upload(String FilePath) throws IOException {

        String[] split = FilePath.split("\\.");

        //通过日期生成图片名称
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        key = sdf.format(new Date());
        key += (int) (Math.random() * 1000);
        key += "." + split[split.length - 1];

        try {
            System.out.println(FilePath);
            //调用put方法上传
            Response res = uploadManager.put(FilePath, key, getUpToken());
            //打印返回的信息
            System.out.println(res.bodyString());
            System.out.println(res.statusCode);//200为上传成功
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //
            }
        }
        return "http://七牛云.com/" + key;  //返回图片地址
    }


    /**
     * 通过输入流上传
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public String upload(InputStream inputStream) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        key = sdf.format(new Date());
        key += (int) (Math.random() * 1000);

        try {
            byte[] uploadBytes = new byte[inputStream.available()];

            inputStream.read(uploadBytes);

            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            String upToken = auth.uploadToken(bucketname);
            try {
                Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //
        }
        return "http://七牛云.com/" + key;
    }
}