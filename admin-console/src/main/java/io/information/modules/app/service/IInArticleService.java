package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.vo.ArticleUserVo;
import io.information.modules.app.vo.ArticleVo;
import io.information.modules.app.vo.CardUserVo;

import java.util.Date;
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

    List<InArticle> doubleArticle(Map<String, Object> map);

    /**
     * 点赞
     * type(0：文章 1：帖子 2：活动)
     */
    String giveALike(Long id,Long tId,int type, Long uid);

    /**
     * 收藏
     * type(0：文章 1：帖子 2：活动)
     */
    String collect(Long id,Long tId,int type, Long uid);
}
