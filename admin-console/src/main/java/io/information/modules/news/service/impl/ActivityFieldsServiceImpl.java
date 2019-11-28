package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.ActivityFieldsDao;
import io.information.modules.news.entity.ActivityFieldsEntity;
import io.information.modules.news.service.ActivityFieldsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class ActivityFieldsServiceImpl extends ServiceImpl<ActivityFieldsDao, ActivityFieldsEntity> implements ActivityFieldsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActivityFieldsEntity> page = this.page(
                new Query<ActivityFieldsEntity>().getPage(params),
                new QueryWrapper<ActivityFieldsEntity>()
        );

        return new PageUtils(page);
    }

}