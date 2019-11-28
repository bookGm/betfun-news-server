package io.information.modules.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.news.entity.MenuSourceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资讯菜单资源关系表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:24
 */
@Mapper
public interface MenuSourceDao extends BaseMapper<MenuSourceEntity> {

}
