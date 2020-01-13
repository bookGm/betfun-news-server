package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InNode;
import io.information.modules.app.vo.NodeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
     *
     * @param noId 节点ID
     */
    void increaseFocus(Long noId);

    /**
     * 关注-1
     *
     * @param noId 节点ID
     */
    void removeFocus(Long noId);

    /**
     * 查询节点ID、节点名称和节点图标
     * 根据关注排序
     */
    List<NodeVo> searchNodeByFocus();
}
