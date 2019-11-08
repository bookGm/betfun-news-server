package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 日志表 接口
 * </p>
 *
 * @author LX
 * @since 2019-11-07
 */
@Mapper
public interface InLogDao extends BaseMapper<InLog> {
}
