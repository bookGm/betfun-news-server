package io.information.modules.news.dao;

import io.information.modules.news.entity.ActivityEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资讯活动表
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@Mapper
public interface ActivityDao extends BaseMapper<ActivityEntity> {
	
}
