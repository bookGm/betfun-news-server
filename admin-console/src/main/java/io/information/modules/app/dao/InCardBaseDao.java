package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.vo.CardBaseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 资讯帖子基础表 Mapper 接口
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Component
@Mapper
public interface InCardBaseDao extends BaseMapper<InCardBase> {
    /**
     * 增加点赞数
     *
     * @param cid 帖子ID
     */
    void addALike(Long cid);

    /**
     * 增加收藏数
     *
     * @param cid 帖子ID
     */
    void addACollect(Long cid);

    /**
     * 查询帖子ID和标题
     * 根据用户ID和分页信息
     */
    List<CardBaseVo> searchTitleAndId(@Param("uId") Long uId, @Param("currPage") Integer currPage, @Param("pageSize") Integer pageSize);

    /**
     * 查询帖子ID和标题
     * 根据关注排序
     */
    List<CardBaseVo> searchBaseByLike();

    /**
     * 增加访问量
     */
    void addReadNumber(@Param("number") long number, @Param("cId") Long cId);
}
