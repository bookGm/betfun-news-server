package io.information.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.news.entity.NodeEntity;

import java.util.Map;

/**
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-11-04 09:11:01
 */
public interface NodeService extends IService<NodeEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

