package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        IPage<NodeEntity> page = this.page(
                new Query<NodeEntity>().getPage(params),
                new QueryWrapper<NodeEntity>()
        );

        return new PageUtils(page);
    }

}