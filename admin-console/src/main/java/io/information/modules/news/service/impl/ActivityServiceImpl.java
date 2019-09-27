package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.ActivityDao;
import io.information.modules.news.entity.ActivityEntity;
import io.information.modules.news.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("activityService")
public class ActivityServiceImpl extends ServiceImpl<ActivityDao, ActivityEntity> implements ActivityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActivityEntity> page = this.page(
                new Query<ActivityEntity>().getPage(params),
                new QueryWrapper<ActivityEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryUserActivity(Long uId) {

        return null;
    }

}