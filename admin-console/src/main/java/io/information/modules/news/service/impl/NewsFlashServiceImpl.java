package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.JsonUtil;
import io.information.common.utils.IdGenerator;
import io.information.common.utils.JsonUtils;
import io.information.modules.app.dao.InNewsFlashDao;
import io.information.modules.app.entity.InNewsFlash;
import io.information.modules.app.service.IInNewsFlashService;
import io.information.modules.news.dao.NewsFlashDao;
import io.information.modules.news.entity.NewsFlash;
import io.information.modules.news.service.NewsFlashService;
import io.information.modules.news.service.feign.common.FeignResJinSe;
import io.information.modules.news.service.feign.service.JinSeService;
import io.information.modules.news.service.feign.vo.JinSeLivesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 资讯快讯表 服务实现类
 * </p>
 *
 * @author LX
 * @since 2019-10-26
 */
@Service
public class NewsFlashServiceImpl extends ServiceImpl<NewsFlashDao, NewsFlash> implements NewsFlashService {
    @Autowired
    JinSeService jinSeService;
    @Override
    public void catchNewsFlash(int start,int pages) {
        for(int i=0;i<pages;i++){
            int s=start-(i*20);
            FeignResJinSe jinse=jinSeService.getPageList(20,s,null,null,null);
            List<JinSeLivesVo> jinseList=JsonUtil.parseList(jinse.getList().getJSONObject(0).getString("lives"),JinSeLivesVo.class);
            for(JinSeLivesVo se:jinseList){
                NewsFlash n=new NewsFlash();
                String c=se.getContent();
                String title=c.substring(c.indexOf("【")+1,c.indexOf("】"));
                String content=c.substring(c.indexOf("】")+1);
                n.setnId(IdGenerator.getId());
                n.setnTitle(title);
                n.setnBrief(content);
                n.setnContent(content);
                n.setnBull(se.getUp_counts());
                n.setnBad(se.getDown_counts());
                n.setnCreateTime(new Date(se.getCreated_at()*1000));
                this.save(n);
            }
        }
    }

}
