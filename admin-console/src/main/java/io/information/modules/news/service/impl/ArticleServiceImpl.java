package io.information.modules.news.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.JsonUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.utils.*;
import io.information.modules.news.dao.ArticleDao;
import io.information.modules.news.entity.ArticleEntity;
import io.information.modules.news.entity.TagEntity;
import io.information.modules.news.service.ArticleService;
import io.information.modules.news.service.TagService;
import io.information.modules.news.service.feign.common.FeignRes;
import io.information.modules.news.service.feign.service.BbtcService;
import io.information.modules.news.service.feign.vo.BbtcListVo;
import me.zhyd.hunter.entity.VirtualArticle;
import me.zhyd.hunter.processor.BlogHunterProcessor;
import me.zhyd.hunter.processor.HunterProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {
    private static final Logger LOG = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Autowired
    BbtcService bbtcService;
    @Autowired
    TagService tagService;
    @Autowired
    RedisUtils redisUtils;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ArticleEntity> page = this.page(
                new Query<ArticleEntity>().getPage(params),
                new QueryWrapper<ArticleEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils audit(Map<String, Object> params) {
        QueryWrapper<ArticleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ArticleEntity::getaStatus, 1);
        IPage<ArticleEntity> page = this.page(
                new Query<ArticleEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }


    @Override
    public void catchArticles(int page) {
        HashSet<String> hashSet = new HashSet<String>();
            FeignRes res=bbtcService.getPageList(20,1);
            JSONObject obj=JsonUtil.parseJSONObject(JsonUtil.toJSONString(res.get("data")));
            List<BbtcListVo> blist=JsonUtil.parseList(obj.getString("list"), BbtcListVo.class);
                for(BbtcListVo b:blist){
                    Long bid=b.getId();
                    if(redisUtils.hasKey(RedisKeys.ARTICLE+b.getTitle())){
                        LOG.error("文章title---------------------已存在continue当前循环:",RedisKeys.ARTICLE+b.getTitle());
                        continue;
                    }
                    redisUtils.set(RedisKeys.ARTICLE+b.getTitle(),String.valueOf(bid),60*60*24*3);
                    String url = "https://www.8btc.com/article/"+bid;
                    HunterProcessor hunter = new BlogHunterProcessor(url, true);
                    CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
                    VirtualArticle v=null;
                    try {
                        if(null!=list&&list.size()>0){
                            v=list.get(0);
                            ArticleEntity a=new ArticleEntity();
                            a.setaId(IdGenerator.getId());
                            a.setuName(v.getAuthor());
                            a.setaTitle(b.getTitle());
                            a.setaBrief(b.getDesc());
                            a.setaContent(v.getContent());
                            a.setaSource("巴比特");
                            a.setaLink(v.getSource());
                            a.setaCover(b.getImage());
                            Date date=DateUtils.stringToDate(b.getPost_date_format(),"yyyy-MM-dd HH:mm:ss");
                            a.setaCreateTime(date);
                            a.setaSimpleTime(DateUtils.getSimpleTime(date));
                            a.setaStatus(Integer.parseInt(NewsEnum.文章状态_已发布.getCode()));
                            a.setaReadNumber(Long.parseLong(b.getViews()));
                            a.setaType(Integer.parseInt(NewsEnum.文章类型_转载.getCode()));
                            StringBuffer ts=new StringBuffer();
                            for(Object t:b.getTags()){
                                String tName=JsonUtil.parseJSONObject(JsonUtil.toJSONString(t)).getString("name");
                                ts.append(tName).append(",");
                                hashSet.add(tName);
                            }
                            if(ts.indexOf(",")>0){
                                a.setaKeyword(ts.substring(0,ts.length()-1));
                            }
                            this.save(a);
                        }
                    } catch (Exception e) {
                        LOG.error("同步文章异常：---------------------",e.getMessage());
                    }
                    hunter=null;
                    list=null;
                  }
                 res=null;
                 obj=null;
                 blist=null;
        if(redisUtils.isFuzzyEmpty(RedisKeys.TAGNAME)){
            Map<String,String> mtl=new HashMap<>();
            for(TagEntity t:tagService.list()){
                mtl.put(RedisKeys.TAGNAME+t.gettName(),t.gettName());
            }
            redisUtils.batchSet(mtl);
            mtl=null;
        }
        Map<String,String> mt=new HashMap<>();
        for (String str : hashSet) {
            String s=str.trim();
            if(redisUtils.hasKey(RedisKeys.TAGNAME+s)){
                continue;
            }
            mt.put(RedisKeys.TAGNAME+s,s);
            TagEntity tag=new TagEntity();
            tag.settCreateTime(new Date());
            tag.settFrom(Integer.parseInt(NewsEnum.标签来源_爬虫抓取.getCode()));
            tag.settName(s);
            tagService.save(tag);
            tag=null;
        }
        redisUtils.batchSet(mt);
        mt=null;
    }

    @Override
    public void catchIncrementArticles() {
        catchArticles(1);
    }

}