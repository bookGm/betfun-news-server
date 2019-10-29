package io.information.modules.news.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.JsonUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.utils.*;
import io.information.modules.news.entity.TagEntity;
import io.information.modules.news.service.TagService;
import io.information.modules.news.service.feign.common.FeignRes;
import io.information.modules.news.service.feign.service.BbtcService;
import io.information.modules.news.dao.ArticleDao;
import io.information.modules.news.entity.ArticleEntity;
import io.information.modules.news.service.ArticleService;
import io.information.modules.news.service.feign.vo.BbtcListVo;
import me.zhyd.hunter.config.HunterConfig;
import me.zhyd.hunter.config.HunterConfigContext;
import me.zhyd.hunter.config.platform.Platform;
import me.zhyd.hunter.entity.VirtualArticle;
import me.zhyd.hunter.enums.ExitWayEnum;
import me.zhyd.hunter.processor.BlogHunterProcessor;
import me.zhyd.hunter.processor.HunterProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {
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
        for(int i=1;i<=page;i++){
            FeignRes res=bbtcService.getPageList(20,i);
            JSONObject obj=JsonUtil.parseJSONObject(JsonUtil.toJSONString(res.get("data")));
            List<BbtcListVo> blist=JsonUtil.parseList(obj.getString("list"), BbtcListVo.class);
            for(BbtcListVo b:blist){
                Long bid=b.getId();
                if(redisUtils.exists(RedisKeys.ARTICLE+bid)){
                    continue;
                }
                redisUtils.set(RedisKeys.ARTICLE+bid,bid,60*60*2);
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
                    continue;
                }
                hunter=null;
                list=null;
              }
            }
        if(redisUtils.isFuzzyEmpty(RedisKeys.TAGNAME)){
            for(TagEntity t:tagService.list()){
                redisUtils.set(RedisKeys.TAGNAME+t.gettName(),t.gettName());
            }
        }
        for (String str : hashSet) {
            String s=str.trim();
            if(redisUtils.exists(RedisKeys.TAGNAME+s)){
                continue;
            }
            redisUtils.set(RedisKeys.ARTICLE+s,s);
            TagEntity tag=new TagEntity();
            tag.settCreateTime(new Date());
            tag.settFrom(Integer.parseInt(NewsEnum.标签来源_爬虫抓取.getCode()));
            tag.settName(s);
            tagService.save(tag);
        }

    }

    @Override
    public void catchIncrementArticles() {
        catchArticles(1);
    }

}