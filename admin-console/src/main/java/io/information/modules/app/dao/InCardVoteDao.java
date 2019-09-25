package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InCardVote;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 资讯投票帖详情（最多30个投票选项） Mapper 接口
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Mapper
public interface InCardVoteDao extends BaseMapper<InCardVote> {

}
