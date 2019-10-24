package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.ActivityTicketDao;
import io.information.modules.news.entity.ActivityTicketEntity;
import io.information.modules.news.service.ActivityTicketService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("activityTicketService")
public class ActivityTicketServiceImpl extends ServiceImpl<ActivityTicketDao, ActivityTicketEntity> implements ActivityTicketService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActivityTicketEntity> page = this.page(
                new Query<ActivityTicketEntity>().getPage(params),
                new QueryWrapper<ActivityTicketEntity>()
        );

        return new PageUtils(page);
    }

}