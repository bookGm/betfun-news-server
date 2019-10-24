package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InActivityDatasDao;
import io.information.modules.app.entity.InActivityDatas;
import io.information.modules.app.service.IInActivityDatasService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("activityDatasService")
public class InActivityDatasServiceImpl extends ServiceImpl<InActivityDatasDao, InActivityDatas> implements IInActivityDatasService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InActivityDatas> page = this.page(
                new Query<InActivityDatas>().getPage(params),
                new QueryWrapper<InActivityDatas>()
        );

        return new PageUtils(page);
    }

}