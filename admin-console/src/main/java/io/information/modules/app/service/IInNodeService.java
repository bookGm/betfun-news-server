package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InNode;

import java.util.List;
import java.util.Map;

/**
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-11-04 09:11:01
 */
public interface IInNodeService extends IService<InNode> {

    PageUtils queryPage(Map<String, Object> params);

    Map<Long, String> search(Long noType);

    PageUtils query(Map<String, Object> map);

    Map<String, Object> special(Map<String, Object> map);


    PageUtils cardList(Map<String, Object> map);

    PageUtils star(Map<String, Object> map);

    Long focus(Long uId, Long noId, Long type);
}

