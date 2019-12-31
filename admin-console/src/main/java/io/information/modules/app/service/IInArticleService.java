package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.vo.ArticleBannerVo;
import io.information.modules.app.vo.ArticleUserVo;
import io.information.modules.app.vo.ArticleVo;
import io.information.modules.app.vo.TagArticleVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯文章表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInArticleService extends IService<InArticle> {

    void updateReadNumber(Long aLong, Long aId);

    PageUtils queryPage(Map<String, Object> params);

    List<ArticleVo> hotTopic();

    ArticleUserVo articleRecommended(Map<String, Object> map);

    PageUtils doubleArticle(Map<String, Object> map);

    /**
     * 点赞
     * type(0：文章 1：帖子 2：活动)
     */
    String giveALike(Long id, Long tId, int type, Long uid);

    /**
     * 收藏
     * type(0：文章 1：帖子 2：活动)
     */
    String collect(Long id, Long tId, int type, Long uid);

    /**
     * 取消点赞
     */
    Long removeALike(long id, Long uId, int type, Long tid);

    /**
     * q取消收藏
     * type(0：文章 1：帖子 2：活动)
     */
    Long removeCollect(Long id, Long tId, int type, Long uid);

    TagArticleVo tagArticle(Map<String, Object> map);

    List<InArticle> all();

    List<ArticleBannerVo> banner();

    List<InArticle> interested();

    InArticle next(Long aId);
}
