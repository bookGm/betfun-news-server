package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.common.utils.RedisUtils;
import io.information.modules.news.dao.CardArgueDao;
import io.information.modules.news.entity.CardArgueEntity;
import io.information.modules.news.entity.CardBaseEntity;
import io.information.modules.news.service.CardArgueService;
import io.information.modules.news.service.CardBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class CardArgueServiceImpl extends ServiceImpl<CardArgueDao, CardArgueEntity> implements CardArgueService {
    @Autowired
    RedisUtils redisUtils;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CardArgueEntity> page = this.page(
                new Query<CardArgueEntity>().getPage(params),
                new QueryWrapper<CardArgueEntity>()
        );
        for(CardArgueEntity a:page.getRecords()){
            Object obj=redisUtils.hget(RedisKeys.INUSER,a.getcId()+"");
            if(obj instanceof CardBaseEntity){
                a.setBaseCard((CardBaseEntity)obj);
            }
        }
        return new PageUtils(page);
    }

}