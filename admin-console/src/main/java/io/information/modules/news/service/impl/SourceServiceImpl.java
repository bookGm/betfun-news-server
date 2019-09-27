package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.SourceDao;
import io.information.modules.news.entity.SourceEntity;
import io.information.modules.news.service.SourceService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sourceService")
public class SourceServiceImpl extends ServiceImpl<SourceDao, SourceEntity> implements SourceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SourceEntity> page = this.page(
                new Query<SourceEntity>().getPage(params),
                new QueryWrapper<SourceEntity>()
        );

        return new PageUtils(page);
    }

}