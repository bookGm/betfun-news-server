package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InArticle;

import java.util.Date;
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
     */
    Date giveALike(Long aid, Long uid);

    /**
     * 收藏
     */
    Date collect(Long aid, Long uid);


}
