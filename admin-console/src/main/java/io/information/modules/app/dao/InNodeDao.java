package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 帖子节点表  接口
 * </p>
 *
 * @author zxs
 * @since 2019-11-04
 */
@Mapper
public interface InNodeDao extends BaseMapper<InNode> {
    /**
     * 关注+1
     * @param noId
     */
    void increaseFocus(Long noId);
}
