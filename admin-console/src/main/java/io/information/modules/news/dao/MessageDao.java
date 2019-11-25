package io.information.modules.news.dao;

import io.information.modules.news.entity.MessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-11-25 10:56:47
 */
@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {
	
}
