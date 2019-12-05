package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.JsonUtil;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.annotation.HashCacheable;
import io.information.common.utils.*;
import io.information.modules.app.dao.InActivityDao;
import io.information.modules.app.dao.InArticleDao;
import io.information.modules.app.dao.InCardBaseDao;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InLog;
import io.information.modules.app.entity.InTag;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInArticleService;
import io.information.modules.app.service.IInTagService;
import io.information.modules.app.service.IInUserService;
import io.information.modules.app.vo.ArticleBannerVo;
import io.information.modules.app.vo.ArticleUserVo;
import io.information.modules.app.vo.ArticleVo;
import io.information.modules.app.vo.TagArticleVo;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯文章表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InArticleServiceImpl extends ServiceImpl<InArticleDao, InArticle> implements IInArticleService {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    IInUserService userService;
    @Autowired
    InActivityDao inActivityDao;
    @Autowired
    InCardBaseDao inCardBaseDao;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    IInTagService tagService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<InArticle> qw = new LambdaQueryWrapper<>();
        if (null != params.get("type") && StringUtil.isNotBlank(params.get("type"))) {
            int type = Integer.parseInt(String.valueOf(params.get("type")));
            switch (type) {
                case 0:
                    qw.orderByDesc(InArticle::getaCreateTime);
                    break;
                case 1:
                    qw.orderByDesc(InArticle::getaReadNumber);
                    break;
                case 2:
                    qw.orderByDesc(InArticle::getaReadNumber, InArticle::getaCreateTime);
                    break;
            }
        }
        if (null != params.get("uId") && StringUtil.isNotBlank(params.get("uId"))) {
            long uId = Long.parseLong(String.valueOf(params.get("uId")));
            qw.eq(InArticle::getuId, uId);
        }
        if (null != params.get("aStatus") && StringUtil.isNotBlank(params.get("aStatus"))) {
            int aStatus = Integer.parseInt(String.valueOf(params.get("aStatus")));
            qw.eq(InArticle::getaStatus, aStatus);
        }
        IPage<InArticle> page = this.page(
                new Query<InArticle>().getPage(params), qw
        );
        for (InArticle a : page.getRecords()) {
//            Object obj = redisUtils.hget(RedisKeys.INUSER, String.valueOf(a.getuId()));
            InUser user = userService.getById(a.getaId());
            if (null != user) {
                a.setuName(user.getuName());
            }
            if (StringUtil.isNotBlank(a.getaCreateTime())) {
                a.setaSimpleTime(DateUtils.getSimpleTime(a.getaCreateTime()));
            }
        }
        return new PageUtils(page);
    }


    @Override
    public void updateReadNumber(Long aReadNumber, Long aId) {
        this.baseMapper.addReadNumber(aReadNumber, aId);
    }


    @Override
    public List<ArticleVo> hotTopic() {
        return this.baseMapper.searchArticleByTime();
    }

    @Override
    public ArticleUserVo articleRecommended(Map<String, Object> map) {
        Integer pageSize = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer currPage = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            long uId = Long.parseLong(String.valueOf(map.get("uId")));
            InUser user = userService.getById(uId);
            if (null != user) {
                ArticleUserVo articleUserVo = DataUtils.copyData(user, ArticleUserVo.class);
                int total = 0;
                if (null != articleUserVo) {
                    LambdaQueryWrapper<InArticle> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(InArticle::getuId, uId).eq(InArticle::getaStatus, 2);
                    int articleNumber = this.count(queryWrapper);
                    if (articleNumber > 0) {
                        List<InArticle> list = this.list(queryWrapper);
                        long likeNumber = list.stream().mapToLong(InArticle::getaLike).sum();
                        articleUserVo.setArticleNumber(articleNumber);
                        articleUserVo.setLikeNumber(likeNumber);
                        List<Object> list1 = this.baseMapper.searchTitleAndId(uId, (currPage - 1 < 0 ? 0 : currPage - 1) * pageSize, pageSize);
                        List<ArticleVo> articleVos = (List<ArticleVo>) list1.get(0);
                        total = ((List<Integer>) list1.get(1)).get(0);//总量
                        if (null != articleVos && !articleVos.isEmpty()) {
                            for (ArticleVo articleVo : articleVos) {
                                articleVo.setaSimpleTime(DateUtils.getSimpleTime(articleVo.getaCreateTime()));
                            }
                            articleUserVo.setArticleVos(articleVos);
                        } else {
                            articleUserVo.setLikeNumber(0L);
                            articleUserVo.setArticleVos(null);
                        }
                    } else {
                        articleUserVo.setArticleNumber(0);
                        articleUserVo.setLikeNumber(0L);
                        articleUserVo.setArticleVos(null);
                    }
                    articleUserVo.setPageSize(pageSize);
                    articleUserVo.setCurrPage(currPage);
                    articleUserVo.setTotalCount(total);
                    articleUserVo.setTotalPage((int) Math.ceil((double) total / pageSize));
                    return articleUserVo;
                }
            }
            return null;
        }
        return null;
    }

    @Override
    public PageUtils doubleArticle(Map<String, Object> map) {
        Integer pageSize = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer currPage = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        ArrayList<InArticle> arrayList = new ArrayList<>();
        if (null != map.get("status") && StringUtil.isNotBlank(map.get("status"))) {
            int status = Integer.parseInt(String.valueOf(map.get("status")));
            if (null != map.get("type") && StringUtil.isNotBlank(map.get("type"))) {
                int type = Integer.parseInt(String.valueOf(map.get("type")));
                int total = 0;
                switch (type) {
                    case 0:
                        List<Object> list = this.baseMapper.searchArticleInTagByLike(status, (currPage - 1 < 0 ? 0 : currPage - 1) * pageSize, pageSize);
                        List<InArticle> inArticles = (List<InArticle>) list.get(0);
                        total = ((List<Integer>) list.get(1)).get(0);//总量
                        if (null != inArticles && !inArticles.isEmpty()) {
                            for (InArticle inArticle : inArticles) {
                                inArticle.setaSimpleTime(DateUtils.getSimpleTime(inArticle.getaCreateTime()));
                            }
                            arrayList.addAll(inArticles);
                        }
                        break;
                    case 1:
                        List<Object> list1 = this.baseMapper.searchArticleInTagByTime(status, (currPage - 1 < 0 ? 0 : currPage - 1) * pageSize, pageSize);
                        List<InArticle> inArticleList = (List<InArticle>) list1.get(0);
                        total = ((List<Integer>) list1.get(1)).get(0);//总量
                        if (null != inArticleList && !inArticleList.isEmpty()) {
                            for (InArticle inArticle : inArticleList) {
                                inArticle.setaSimpleTime(DateUtils.getSimpleTime(inArticle.getaCreateTime()));
                            }
                            arrayList.addAll(inArticleList);
                        }
                        break;
                }
                return new PageUtils(arrayList, total, pageSize, currPage);
            } else {
                List<Object> list = this.baseMapper.searchArticleInTag(status, (currPage - 1 < 0 ? 0 : currPage - 1) * pageSize, pageSize);
                List<InArticle> inArticleList = (List<InArticle>) list.get(0);
                int total = ((List<Integer>) list.get(1)).get(0);//总量
                if (null != inArticleList && !inArticleList.isEmpty()) {
                    for (InArticle inArticle : inArticleList) {
                        inArticle.setaSimpleTime(DateUtils.getSimpleTime(inArticle.getaCreateTime()));
                    }
                }
                return new PageUtils(inArticleList, total, pageSize, currPage);
            }
        }
        return null;
    }

    /**
     * 记录操作日志
     */
    void logOperate(Long uId, Long tId, NewsEnum e) {
        InLog log = new InLog();
        log.setlOperateId(uId);
        log.setlTargetId(tId);
        log.setlTargetType(1);
        log.setlDo(Integer.parseInt(e.getCode()));
        log.setlTime(new Date());
        rabbitTemplate.convertAndSend(Constants.logExchange, Constants.log_Save_RouteKey, JsonUtil.toJSONString(log));
    }

    @Override
    @HashCacheable(key = RedisKeys.LIKE, keyField = "#id-#uid-#tId-#type")
    public String giveALike(Long id, Long uid, int type, Long tId) {
        if (NewsEnum.点赞_文章.getCode().equals(String.valueOf(type))) {
            this.baseMapper.addALike(id);
            System.out.println("添加完成");
        }
        if (NewsEnum.点赞_帖子.getCode().equals(String.valueOf(type))) {
            logOperate(uid, id, NewsEnum.操作_点赞);
            this.inCardBaseDao.addALike(id);
            System.out.println("添加完成");
        }
        if (NewsEnum.点赞_活动.getCode().equals(String.valueOf(type))) {
            this.inActivityDao.addALike(id);
            System.out.println("添加完成");
        }
        return DateUtils.format(new Date());
    }


    @Override
    public Long removeALike(long id, Long uid, int type, Long tid) {
        if (NewsEnum.点赞_文章.getCode().equals(String.valueOf(type))) {
            this.baseMapper.removeALike(id);
            System.out.println("删除完成");
        }
        if (NewsEnum.点赞_帖子.getCode().equals(String.valueOf(type))) {
            logOperate(uid, id, NewsEnum.操作_点赞);
            this.inCardBaseDao.removeALike(id);
            System.out.println("删除完成");
        }
        if (NewsEnum.点赞_活动.getCode().equals(String.valueOf(type))) {
            this.inActivityDao.removeALike(id);
            System.out.println("删除完成");
        }
        String key = id + "-" + uid + "-" + tid + "-" + type;
        return redisUtils.hremove(RedisKeys.LIKE, key);
    }

    @Override
    public Long removeCollect(Long id, Long tid, int type, Long uid) {
        if (NewsEnum.收藏_文章.getCode().equals(String.valueOf(type))) {
            this.baseMapper.removeACollect(id);
        }
        if (NewsEnum.收藏_帖子.getCode().equals(String.valueOf(type))) {
            logOperate(uid, id, NewsEnum.操作_收藏);
            this.inCardBaseDao.removeACollect(id);
        }
        if (NewsEnum.收藏_活动.getCode().equals(String.valueOf(type))) {
            this.inActivityDao.removeACollect(id);
        }
        String key = id + "-" + uid + "-" + tid + "-" + type;
        return redisUtils.hremove(RedisKeys.COLLECT, key);
    }

    @Override
    @HashCacheable(key = RedisKeys.COLLECT, keyField = "#id-#uid-#tId-#type")
    public String collect(Long id, Long uid, int type, Long tId) {
        if (NewsEnum.收藏_文章.getCode().equals(String.valueOf(type))) {
            this.baseMapper.addACollect(id);
        }
        if (NewsEnum.收藏_帖子.getCode().equals(String.valueOf(type))) {
            logOperate(uid, id, NewsEnum.操作_收藏);
            this.inCardBaseDao.addACollect(id);
        }
        if (NewsEnum.收藏_活动.getCode().equals(String.valueOf(type))) {
            this.inActivityDao.addACollect(id);
        }
        return DateUtils.format(new Date());
    }

    @Override
    public TagArticleVo tagArticle(Map<String, Object> map) {
        Integer pageSize = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer currPage = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        if (null != map.get("tId") && StringUtil.isNotBlank(map.get("tId"))) {
            Long tId = Long.parseLong(String.valueOf(map.get("tId")));
            InTag tag = tagService.getById(tId);
            List<Object> list = this.baseMapper.searchArticleByTag(tag.gettName(), (currPage - 1 < 0 ? 0 : currPage - 1) * pageSize, pageSize);
            List<InArticle> articles = (List<InArticle>) list.get(0);
            int total = ((List<Integer>) list.get(1)).get(0);//总量
            if (null != articles) {
                for (InArticle article : articles) {
                    article.setaSimpleTime(DateUtils.getSimpleTime(article.getaCreateTime()));
                }
                TagArticleVo vo = new TagArticleVo();
                vo.setTag(tag);
                vo.setArticleList(articles);
                vo.setTotalCount(total);
                vo.setCurrPage(currPage);
                vo.setPageSize(pageSize);
                vo.setTotalCount((int) Math.ceil((double) total / pageSize));
                return vo;
            }
            return null;
        }
        return null;
    }

    @Override
    public List<InArticle> all() {
        return this.baseMapper.all();
    }

    @Override
    public List<ArticleBannerVo> banner() {
        LambdaQueryWrapper<InArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InArticle::getaBanner, 1).eq(InArticle::getaStatus, 2);
        List<InArticle> inArticles = this.list(queryWrapper);
        List<ArticleBannerVo> bannerVos = BeanHelper.copyWithCollection(inArticles, ArticleBannerVo.class);
        if (null != bannerVos && !bannerVos.isEmpty()) {
            return bannerVos;
        }
        return null;
    }

    @Override
    public List<InArticle> interested() {
        return this.baseMapper.interested();
    }

    @Override
    public InArticle next(Long uId) {
        return this.baseMapper.next(uId);
    }
}
