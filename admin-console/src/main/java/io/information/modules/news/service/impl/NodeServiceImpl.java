package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.NodeDao;
import io.information.modules.news.entity.NodeEntity;
import io.information.modules.news.service.NodeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class NodeServiceImpl extends ServiceImpl<NodeDao, NodeEntity> implements NodeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<NodeEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (null != params.get("key") && StringUtil.isNotBlank(params.get("key"))) {
            String key = String.valueOf(params.get("key"));
            queryWrapper.eq(NodeEntity::getNoId, key)
                    .or()
                    .like(NodeEntity::getNoName, key);
        }
        queryWrapper.orderByDesc(NodeEntity::getNoCreateTime);
        IPage<NodeEntity> page = this.page(
                new Query<NodeEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public Map<Long, String> search(Long noType) {
        LambdaQueryWrapper<NodeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NodeEntity::getNoType, noType);
        List<NodeEntity> nodeList = this.list(queryWrapper);
        if (null != nodeList && !nodeList.isEmpty()) {
            HashMap<Long, String> map = new HashMap<>();
            for (NodeEntity node : nodeList) {
                map.put(node.getNoId(), node.getNoName());
            }
            return map;
        }
        return null;
    }
}