package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.CardBaseDao;
import io.information.modules.news.entity.CardBaseEntity;
import io.information.modules.news.service.CardBaseService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cardBaseService")
public class CardBaseServiceImpl extends ServiceImpl<CardBaseDao, CardBaseEntity> implements CardBaseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CardBaseEntity> page = this.page(
                new Query<CardBaseEntity>().getPage(params),
                new QueryWrapper<CardBaseEntity>()
        );

        return new PageUtils(page);
    }

}