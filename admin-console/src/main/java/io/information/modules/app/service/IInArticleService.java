package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InArticle;

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

    void deleteAllArticle(Long userId);

    PageUtils queryAllArticle(Map<String, Object> params, Long userId);

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 点赞
     */
    boolean giveALike(Long aid, Long uid);

    /**
     * 收藏
     */
    boolean collect(Long aid, Long uid);

    PageUtils uDraft(Map<String, Object> params, Long userId);

    void updateReadNumber(Long aLong,Long aId);
}
