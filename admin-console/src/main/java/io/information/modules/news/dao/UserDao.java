package io.information.modules.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.news.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资讯用户表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-14 09:12:17
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

}
