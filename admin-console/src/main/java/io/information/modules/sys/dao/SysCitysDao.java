

package io.information.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.sys.entity.SysCitysEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 省市县（区）
 *
 * @author LX
 */
@Mapper
public interface SysCitysDao extends BaseMapper<SysCitysEntity> {

}
