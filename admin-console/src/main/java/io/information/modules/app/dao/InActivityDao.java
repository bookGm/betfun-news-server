package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 资讯活动表 Mapper 接口
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Component
@Mapper
public interface InActivityDao extends BaseMapper<InActivity> {
    /**
     * 增加点赞数
     * @param actid
     */
    void addALike (Long actid);
    /**
     * 增加收藏数
     * @param actid
     */
    void addACollect (Long actid);
    /**
     * 增加报名数
     * @param actid
     */
    void addApply (Long actid);

    /**
     * 获取用户所有活动ID
     */
    List<Long> allActId(@Param("uId") Long uId);
}
