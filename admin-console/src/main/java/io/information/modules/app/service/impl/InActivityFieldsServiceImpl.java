package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InActivityFieldsDao;
import io.information.modules.app.entity.InActivityFields;
import io.information.modules.app.service.IInActivityFieldsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class InActivityFieldsServiceImpl extends ServiceImpl<InActivityFieldsDao, InActivityFields> implements IInActivityFieldsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InActivityFields> page = this.page(
                new Query<InActivityFields>().getPage(params),
                new QueryWrapper<InActivityFields>()
        );

        return new PageUtils(page);
    }

}