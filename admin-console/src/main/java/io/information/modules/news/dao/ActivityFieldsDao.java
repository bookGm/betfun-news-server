package io.information.modules.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.news.entity.ActivityFieldsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资讯活动动态表单属性
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 10:53:16
 */
@Mapper
public interface ActivityFieldsDao extends BaseMapper<ActivityFieldsEntity> {

}
