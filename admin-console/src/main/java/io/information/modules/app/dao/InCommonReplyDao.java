package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InCommonReply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 评论回复表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-08 15:11:28
 */
@Component
@Mapper
public interface InCommonReplyDao extends BaseMapper<InCommonReply> {

}
