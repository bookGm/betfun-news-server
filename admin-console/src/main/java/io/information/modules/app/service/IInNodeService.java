package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InNode;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.vo.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 帖子节点表  服务类
 * </p>
 *
 * @author zxs
 * @since 2019-11-04
 */
public interface IInNodeService extends IService<InNode> {

    PageUtils queryPage(Map<String, Object> params);

    Map<Long, String> search(Long noType);

    Map<Long, List<InNode>> query(Map<String, Object> map);

    Map<String, Object> special(Map<String, Object> map);

    PageUtils cardList(Map<String, Object> map);

    Map<Integer, List<InUser>> star(Map<String, Object> map);

    Long focus(Long uId, Long noId, Long type);

    CardUserVo cardRecommended(Map<String, Object> map);

    List<CardBaseVo> heatCard();

    List<NodeVo> recommendNode();

    List<NewDynamicVo> newDynamic();

}

