package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InCardBase;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 资讯帖子基础表 Mapper 接口
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Mapper
public interface InCardBaseDao extends BaseMapper<InCardBase> {
    /**
     * 增加点赞数
     * @param cid
     */
    void addALike (Long cid);
    /**
     * 增加收藏数
     * @param cid
     */
    void addACollect (Long cid);
}
