package io.information.modules.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.news.entity.CommonReplyEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论回复表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-08 15:11:28
 */
@Mapper
public interface CommonReplyDao extends BaseMapper<CommonReplyEntity> {

}
