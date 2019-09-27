package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.CardVoteDao;
import io.information.modules.news.entity.CardVoteEntity;
import io.information.modules.news.service.CardVoteService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("cardVoteService")
public class CardVoteServiceImpl extends ServiceImpl<CardVoteDao, CardVoteEntity> implements CardVoteService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CardVoteEntity> page = this.page(
                new Query<CardVoteEntity>().getPage(params),
                new QueryWrapper<CardVoteEntity>()
        );

        return new PageUtils(page);
    }

}