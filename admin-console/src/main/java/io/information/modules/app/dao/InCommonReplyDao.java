package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InCommonReply;
import io.information.modules.app.vo.DynamicReplyVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

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

    /**
     * 查询评论ID、评论目标名称和评论时间
     * 根据创建时间排序
     */
	List<DynamicReplyVo> searchReplyByTime();
}
