package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 资讯文章表 Mapper 接口
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Component
@Mapper
public interface InArticleDao extends BaseMapper<InArticle> {
    /**
     * 增加点赞数
     *
     * @param aid
     */
    void addALike(Long aid);

    /**
     * 增加收藏数
     *
     * @param aid
     */
    void addACollect(Long aid);

    /**
     * 增加访问量
     *
     * @param aReadNumber
     * @param aId
     */
    void addReadNumber(@Param("aReadNumber") Long aReadNumber, @Param("aId") Long aId);

    /**
     * 下一篇(时间排序)
     */
    InArticle timeNext(@Param("aId") Long aId, @Param("aStatus") Integer aStatus);

    /**
     * 下一篇(浏览量排序)
     */
    InArticle readNext(@Param("aId") Long aId, @Param("aStatus") Integer aStatus);

    /**
     * 下一篇(综合排序)
     */
    InArticle synNext(@Param("aId") Long aId, @Param("aStatus") Integer aStatus);

    /**
     * 查询文章ID和标题
     * 根据用户ID和分页
     */
    List<Object> searchTitleAndId(@Param("uId") Long uId, @Param("currPage") Integer currPage, @Param("pageSize") Integer pageSize);

    /**
     * 查询热门文章
     * 根据点赞和评论排序
     */
    List<ArticleVo> searchArticleByTime();

    /**
     * 1：行业要闻  2：技术前沿
     */
    List<Object> searchArticleInTag(@Param("status") Integer status, @Param("currPage") Integer currPage, @Param("pageSize") Integer pageSize);

    /**
     * 1：行业要闻  2：技术前沿
     * 热门
     */
    List<Object> searchArticleInTagByLike(@Param("status") Integer status, @Param("currPage") Integer currPage, @Param("pageSize") Integer pageSize);

    /**
     * 1：行业要闻  2：技术前沿
     * 推荐
     */
    List<Object> searchArticleInTagByTime(@Param("status") Integer status, @Param("currPage") Integer currPage, @Param("pageSize") Integer pageSize);

    /**
     * 首页标签
     */
    List<Object> searchArticleByTag(@Param("tName") String tName, @Param("currPage") Integer currPage, @Param("pageSize") Integer pageSize);

    /**
     * 获取用户所有文章ID
     */
    List<Long> allAId(@Param("uId") Long uId);

    /**
     * 感兴趣的文章
     */
    List<InArticle> interested();

    /**
     * 查询所有文章
     */
    List<InArticle> all();

    /**
     * 减少点赞数
     *
     * @param aid
     */
    void removeALike(long aid);

    /**
     * 减少收藏数
     *
     * @param aid
     */
    void removeACollect(long aid);
}
