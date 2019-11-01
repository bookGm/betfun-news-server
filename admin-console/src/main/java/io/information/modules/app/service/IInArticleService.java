package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InArticle;

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

    /**
     * 点赞
     * type(0：文章 1：帖子 2：活动)
     */
    Date giveALike(Long id,Long tId,int type, Long uid);

    /**
     * 收藏
     * type(0：文章 1：帖子 2：活动)
     */
    Date collect(Long id,Long tId,int type, Long uid);


    List<InArticle> hotTopic();
}
