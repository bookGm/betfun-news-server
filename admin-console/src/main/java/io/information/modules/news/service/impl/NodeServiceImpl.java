package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.NodeDao;
import io.information.modules.news.entity.NodeEntity;
import io.information.modules.news.service.NodeService;
import org.springframework.stereotype.Service;

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

}