package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.ActivityDao;
import io.information.modules.news.entity.ActivityEntity;
import io.information.modules.news.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityDao, ActivityEntity> implements ActivityService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<ActivityEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (null != params.get("key") && StringUtil.isNotBlank(params.get("key"))) {
            String key = String.valueOf(params.get("key"));
            queryWrapper.like(ActivityEntity::getActTitle, key)
                    .or()
                    .eq(ActivityEntity::getActId, key)
                    .or()
                    .eq(ActivityEntity::getActContact, key);
        }
        queryWrapper.orderByDesc(ActivityEntity::getActBanner, ActivityEntity::getActCreateTime);
        IPage<ActivityEntity> page = this.page(
                new Query<ActivityEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }


    @Override
    public PageUtils audit(Map<String, Object> params) {
        LambdaQueryWrapper<ActivityEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (null != params.get("key") && StringUtil.isNotBlank(params.get("key"))) {
            String key = String.valueOf(params.get("key"));
            queryWrapper.like(ActivityEntity::getActTitle, key)
                    .or()
                    .eq(ActivityEntity::getActId, key)
                    .or()
                    .eq(ActivityEntity::getActContact, key);
        }
        queryWrapper.eq(ActivityEntity::getActStatus, 1);
        queryWrapper.orderByDesc(ActivityEntity::getActBanner, ActivityEntity::getActCategory);
        IPage<ActivityEntity> page = this.page(
                new Query<ActivityEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

}