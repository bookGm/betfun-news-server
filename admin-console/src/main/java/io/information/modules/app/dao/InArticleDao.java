package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;

/**
 * <p>
 * 资讯文章表 Mapper 接口
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Mapper
public interface InArticleDao extends BaseMapper<InArticle> {
    /**
     * 增加点赞数
     * @param aid
     */
    void addALike (Long aid);
    /**
     * 增加收藏数
     * @param aid
     */
    void addACollect (Long aid);

    /**
     * 增加访问量
     * @param aReadNumber
     * @param aId
     */
    void addReadNumber(@Param("aReadNumber") Long aReadNumber, @Param("aId") Long aId);
}
