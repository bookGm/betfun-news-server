package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InNewsFlash;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资讯快讯表 Mapper 接口
 * </p>
 *
 * @author LX
 * @since 2019-10-26
 */
@Mapper
public interface InNewsFlashDao extends BaseMapper<InNewsFlash> {
    /**
     * 增加利好
     *
     * @param nId
     */
    void addNBull(@Param("nId") Long nId);

    /**
     * 增加利空
     *
     * @param nId
     */
    void addNBad(@Param("nId") Long nId);

    List<InNewsFlash> all();
}
