package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 资讯菜单表 Mapper 接口
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Mapper
public interface InMenuDao extends BaseMapper<InMenu> {

}
