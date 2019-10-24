package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.ActivityDatasDao;
import io.information.modules.news.entity.ActivityDatasEntity;
import io.information.modules.news.service.ActivityDatasService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("activityDatasService")
public class ActivityDatasServiceImpl extends ServiceImpl<ActivityDatasDao, ActivityDatasEntity> implements ActivityDatasService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActivityDatasEntity> page = this.page(
                new Query<ActivityDatasEntity>().getPage(params),
                new QueryWrapper<ActivityDatasEntity>()
        );

        return new PageUtils(page);
    }

}