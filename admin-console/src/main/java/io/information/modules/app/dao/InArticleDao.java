package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 查询文章ID和标题
     * 根据用户ID和分页
     */
    List<ArticleVo> searchTitleAndId(@Param("uId") Long uId, @Param("currPage") Integer currPage, @Param("pageSize") Integer pageSize);

    /**
     * 查询热门文章
     * 根据点赞和评论排序
     */
    List<ArticleVo> searchArticleByTime();
}
