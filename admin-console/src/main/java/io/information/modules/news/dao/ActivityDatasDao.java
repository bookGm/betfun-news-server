package io.information.modules.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.news.entity.ActivityDatasEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资讯活动动态表单数据
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 11:03:00
 */
@Mapper
public interface ActivityDatasDao extends BaseMapper<ActivityDatasEntity> {

}
