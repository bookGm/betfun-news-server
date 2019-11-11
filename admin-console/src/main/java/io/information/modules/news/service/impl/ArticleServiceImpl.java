package io.information.modules.news.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.JsonUtil;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.utils.*;
import io.information.modules.news.dao.ArticleDao;
import io.information.modules.news.entity.ArticleEntity;
import io.information.modules.news.entity.TagEntity;
import io.information.modules.news.entity.UserEntity;
import io.information.modules.news.service.ArticleService;
import io.information.modules.news.service.TagService;
import io.information.modules.news.service.UserService;
import io.information.modules.news.service.feign.common.FeignRes;
import io.information.modules.news.service.feign.service.BbtcService;
import io.information.modules.news.service.feign.vo.AuthorInfoVo;
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
    UserService userService;
    @Autowired
    TagService tagService;
    @Autowired
    RedisUtils redisUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<ArticleEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (null != params.get("aTitle") && StringUtil.isNotBlank(params.get("aTitle"))) {
            String aTitle = String.valueOf(params.get("aTitle"));
            queryWrapper.like(ArticleEntity::getaTitle, aTitle);
        }
        IPage<ArticleEntity> page = this.page(
                new Query<ArticleEntity>().getPage(params),
                queryWrapper
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


    /**
     * 保存文章
     *
     * @param v 文章实体
     * @param b 巴比特接口实体
     */
    void saveArticle(VirtualArticle v, BbtcListVo b, HashSet<String> hashSet, Long uId) {
        ArticleEntity a = new ArticleEntity();
        a.setaId(IdGenerator.getId());
        a.setuId(uId);
        a.setuName(v.getAuthor());
        a.setaTitle(b.getTitle());
        a.setaBrief(b.getDesc());
        a.setaContent(v.getContent());
        a.setaSource("巴比特");
        a.setaLink(v.getSource());
        a.setaCover(b.getImage());
        Date date = DateUtils.stringToDate(b.getPost_date_format(), "yyyy-MM-dd HH:mm:ss");
        a.setaCreateTime(date);
        a.setaSimpleTime(DateUtils.getSimpleTime(date));
        a.setaStatus(Integer.parseInt(NewsEnum.文章状态_已发布.getCode()));
        a.setaReadNumber(Long.parseLong(b.getViews()));
        a.setaType(Integer.parseInt(NewsEnum.文章类型_转载.getCode()));
        StringBuffer ts = new StringBuffer();
        for (Object t : b.getTags()) {
            String tName = JsonUtil.parseJSONObject(JsonUtil.toJSONString(t)).getString("name");
            ts.append(tName).append(",");
            hashSet.add(tName);
        }
        if (ts.indexOf(",") > 0) {
            a.setaKeyword(ts.substring(0, ts.length() - 1));
        }
        this.save(a);
    }

    /**
     * 保存标签
     *
     * @param hashSet
     */
    void saveTag(HashSet<String> hashSet) {
        if (redisUtils.isFuzzyEmpty(RedisKeys.TAGNAME)) {
            Map<String, String> mtl = new HashMap<>();
            for (TagEntity t : tagService.list()) {
                mtl.put(RedisKeys.TAGNAME + t.gettName(), t.gettName());
            }
            redisUtils.batchSet(mtl);
            mtl = null;
        }
        Map<String, String> mt = new HashMap<>();
        for (String str : hashSet) {
            String s = str.trim();
            if (redisUtils.hasKey(RedisKeys.TAGNAME + s)) {
                continue;
            }
            mt.put(RedisKeys.TAGNAME + s, s);
            TagEntity tag = new TagEntity();
            tag.settCreateTime(new Date());
            tag.settFrom(Integer.parseInt(NewsEnum.标签来源_爬虫抓取.getCode()));
            tag.settName(s);
            tagService.save(tag);
            tag = null;
        }
        redisUtils.batchSet(mt);
        mt = null;
    }

    /**
     * 保存作者
     *
     * @param author
     */
    void saveAuthor(AuthorInfoVo author, Long uId) {
        //用户id
        String authorId = author.getId();
        if (redisUtils.isFuzzyEmpty(RedisKeys.AUTHORID)) {
            Map<String, String> umap = new HashMap<>();
            for (UserEntity u : userService.list(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getuPotential, NewsEnum.用户类型_抓取用户.getCode()))) {
                umap.put(RedisKeys.AUTHORID + u.getuAccount(), u.getuAccount());
            }
            redisUtils.batchSet(umap);
            umap = null;
        }
        if (redisUtils.hasKey(RedisKeys.AUTHORID + authorId)) {
            return;
        }
        redisUtils.set(RedisKeys.AUTHORID + authorId, authorId);
        UserEntity user = new UserEntity();
        user.setuId(uId);
        user.setuAccount(authorId);
        user.setuAuthStatus(Integer.parseInt(NewsEnum.用户认证状态_审核通过.getCode()));
        user.setuAuthType(Integer.parseInt(NewsEnum.用户认证类型_个人.getCode()));
        user.setuName(author.getName());
        user.setuIntro(author.getDesc());
        user.setuNick(author.getDisplay_name());
        String[] as = author.getAvatars();
        if (null != as && as.length > 0) {
            user.setuPhoto(as[as.length - 1]);
        } else {
            user.setuPhoto(author.getAvatar());
        }
        user.setuPotential(Integer.parseInt(NewsEnum.用户类型_抓取用户.getCode()));
        user.setuCreateTime(new Date());
        userService.save(user);
        user = null;
    }

    /**
     * 获取巴比特文章首页数据
     */
    List<BbtcListVo> getBbtcList(int page) {
        FeignRes res = bbtcService.getPageList(20, page);
        JSONObject obj = JsonUtil.parseJSONObject(JsonUtil.toJSONString(res.get("data")));
        List<BbtcListVo> blist = JsonUtil.parseList(obj.getString("list"), BbtcListVo.class);
        return blist;
    }

    @Override
    public void catchArticles(int page) {
        boolean lock = redisUtils.lock(RedisKeys.CATCH_ARTICLE_LOCK, RedisKeys.CATCH_ARTICLE_LOCK, 10);
        HashSet<String> hashSet = new HashSet<String>();
        if (lock) {
            for (BbtcListVo b : getBbtcList(page)) {
                Long bid = b.getId();
                if (redisUtils.hasKey(RedisKeys.ARTICLE + bid)) {
                    continue;
                }
                redisUtils.set(RedisKeys.ARTICLE + bid, String.valueOf(bid));
                String url = "https://www.8btc.com/article/" + bid;
                HunterProcessor hunter = new BlogHunterProcessor(url, true);
                CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
                VirtualArticle v = null;
                try {
                    if (null != list && list.size() > 0) {
                        v = list.get(0);
                        Long uid = IdGenerator.getId();
                        //保存文章
                        saveArticle(v, b, hashSet, uid);
                        //保存作者
                        saveAuthor(b.getAuthor_info(), uid);
                    }
                } catch (Exception e) {
                    LOG.error("同步文章异常：---------------------", e.getMessage());
                }
                hunter = null;
                list = null;
            }
            //保存标签
            saveTag(hashSet);
            redisUtils.releaseLock(RedisKeys.CATCH_ARTICLE_LOCK, RedisKeys.CATCH_ARTICLE_LOCK);
        }
    }


    /**
     * 增量同步文章，用户，标签
     */
    @Override
    public void catchIncrementArticles() {
        catchArticles(1);
    }

}