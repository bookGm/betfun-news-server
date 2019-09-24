package com.guansuo.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ResourceUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtils {
	public static final String zipPwd="EXJ_JS_5G";
	
    /**
     * 获取ZIP文件中的文件名和目录名
     * @param zipFilePath
     * @param password
     * @return
     */
    public static List<String> getEntryNames(String zipFilePath, String password){
        List<String> entryList = new ArrayList<String>();
        ZipFile zf;
        try {
            zf = new ZipFile(zipFilePath);
            zf.setFileNameCharset("GBK");//默认UTF8，如果压缩包中的文件名是GBK会出现乱码
            if(zf.isEncrypted()){
                zf.setPassword(password);//设置压缩密码
            }
            for(Object obj : zf.getFileHeaders()){
                FileHeader fileHeader = (FileHeader)obj;
                String fileName = fileHeader.getFileName();//文件名会带上层级目录信息
                entryList.add(fileName);
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return entryList;
    }
    /**
     * 密码获取ZIP文件中的流
     * @param zipFilePath
     * @param password
     * @return
     */
    public static InputStream zipStream(String zipFilePath, String password){
    	List<String> entryList = new ArrayList<String>();
    	InputStream is=null;
        try {
        	System.out.println(">>>>>>>>>>"+zipFilePath);
        	ZipFile zf = new ZipFile(zipFilePath);
            zf.setFileNameCharset("GBK");
            if(zf.isEncrypted()){
                zf.setPassword(password);
            }
            for(Object obj : zf.getFileHeaders()){
                FileHeader fileHeader = (FileHeader)obj;
                entryList.add(fileHeader.getFileName());
            }
            is=zf.getInputStream(zf.getFileHeader(entryList.get(0)));
        }catch(Exception e){
        	e.printStackTrace();
        }
        return is;
    }

    /** 
     * 压缩文件 
     * @param fileToAdd 文件压缩路径 
     * @param files 被压缩的文件路径 
     * @throws ZipException 
     */  
    public static void zipFile(String fileToAdd,String files[]) throws ZipException{  
        //文件压缩路径  
        ZipFile zipFile = new ZipFile(fileToAdd);  
        //被压缩的文件路径  
        ArrayList<File> filesToAdd = new ArrayList<File>();  
        for(String f:files){  
            filesToAdd.add(new File(f));  
        }  
        ZipParameters parameters = new ZipParameters();  
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);  
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);    
        zipFile.addFiles(filesToAdd, parameters);     
    }  
      
    /** 
     * 压缩文件-加密 
     * @param fileToAdd 文件压缩路径 
     * @param files 被压缩的文件路径 
     * @param password 密码 
     * @throws ZipException 
     */  
    public static void zipFile(String fileToAdd,String files[],String password) throws ZipException{  
        //文件压缩路径  
        ZipFile zipFile = new ZipFile(fileToAdd);  
        //被压缩的文件路径  
        ArrayList<File> filesToAdd = new ArrayList<File>();  
        for(String f:files){  
            filesToAdd.add(new File(f));  
        }  
        ZipParameters parameters = new ZipParameters();  
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);  
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);   
        // Set password    
        parameters.setEncryptFiles(true);    
        //--标准   
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);    
        //--AES  
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);    
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);    
        parameters.setPassword(password);   
        zipFile.addFiles(filesToAdd, parameters);     
    }  
    
    /** 
     * 压缩文件夹 
     * @param folderToAdd 
     * @param dirs 
     * @throws ZipException 
     */  
    public static void zipDir(String folderToAdd,String dirs) throws ZipException{  
        ZipFile zipFile = new ZipFile(folderToAdd);  
        ZipParameters parameters = new ZipParameters();       
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);         
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);  
        zipFile.addFolder(dirs, parameters);  
    }  
      
    /** 
     * 压缩文件夹-加密 
     * @param folderToAdd 
     * @param dirs 
     * @throws ZipException 
     */  
    public static void zipDir(String folderToAdd,String dirs,String password) throws ZipException{  
        ZipFile zipFile = new ZipFile(folderToAdd);  
        ZipParameters parameters = new ZipParameters();       
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);         
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);  
        // Set password    
        parameters.setEncryptFiles(true);    
        //--标准   
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);    
        //--AES  
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);    
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);    
        parameters.setPassword(password);   
        zipFile.addFolder(dirs, parameters);  
    }  
      
    /** 
     * 压缩文件列表 
     * @param folderToAdd 
     * @throws ZipException 
     */  
    @SuppressWarnings("unchecked")  
    public static void zipShowList(String folderToAdd) throws ZipException{  
        ZipFile zipFile = new ZipFile(folderToAdd);  
        List<FileHeader> fileHeaderList = zipFile.getFileHeaders();  
        for (int i = 0; i < fileHeaderList.size(); i++) {  
            FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);  
            System.out.println("****File Details for: " + fileHeader.getFileName() + "*****");  
            System.out.println("Name: " + fileHeader.getFileName());  
            System.out.println("Compressed Size: " + fileHeader.getCompressedSize());  
            System.out.println("Uncompressed Size: " + fileHeader.getUncompressedSize());  
            System.out.println("CRC: " + fileHeader.getCrc32());  
            System.out.println("************************************************************");  
        }  
    }  
      
    /** 
     * 解压zip 
     * @param folderOutAdd 
     * @param dir 
     * @throws ZipException 
     */  
    public static void unzip(String folderOutAdd,String dir) throws ZipException{  
        ZipFile zipFile = new ZipFile(folderOutAdd);            
        zipFile.extractAll(dir);   
    }  
      
    /** 
     * 解压带密码的zip 
     * @param folderOutAdd 
     * @param dir 
     * @param password 
     * @throws ZipException 
     */  
    public static void unzip(String folderOutAdd,String dir,String password) throws ZipException{  
        ZipFile zipFile = new ZipFile(folderOutAdd);   
        if (zipFile.isEncrypted()) {    
            zipFile.setPassword(password);    
        }        
        zipFile.extractAll(dir);   
    }  
    /**
     * 获取resource路径
     * @return
     */
    public static String getResourcePath(){
    	String filepath="";
		try {
			filepath = ResourceUtils.getURL("classpath:").getPath();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(filepath.startsWith("/")||filepath.startsWith("\\")){
			filepath=filepath.substring(1);
		}
		return filepath;
    }
}
