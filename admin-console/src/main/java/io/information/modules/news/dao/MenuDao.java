package io.information.modules.news.dao;

import io.information.modules.news.entity.MenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 资讯菜单表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@Mapper
public interface MenuDao extends BaseMapper<MenuEntity> {
    String getMaxCode(@Param("mPcode")String pcode);
}
