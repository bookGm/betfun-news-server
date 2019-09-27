package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.BeanHelper;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.CardBaseDao;
import io.information.modules.news.entity.CardArgueEntity;
import io.information.modules.news.entity.CardBaseEntity;
import io.information.modules.news.entity.CardVo;
import io.information.modules.news.entity.CardVoteEntity;
import io.information.modules.news.service.CardArgueService;
import io.information.modules.news.service.CardBaseService;
import io.information.modules.news.service.CardVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;


@Service("cardBaseService")
public class CardBaseServiceImpl extends ServiceImpl<CardBaseDao, CardBaseEntity> implements CardBaseService {
    @Autowired
    private CardArgueService argueService;
    @Autowired
    private CardVoteService voteService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CardBaseEntity> page = this.page(
                new Query<CardBaseEntity>().getPage(params),
                new QueryWrapper<CardBaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCard(CardVo cardVo){
        CardBaseEntity baseEntity = BeanHelper.copyProperties(cardVo, CardBaseEntity.class);
        CardArgueEntity argueEntity = BeanHelper.copyProperties(cardVo, CardArgueEntity.class);
        CardVoteEntity voteEntity = BeanHelper.copyProperties(cardVo, CardVoteEntity.class);
        this.save(baseEntity);
        argueService.save(argueEntity);
        voteService.save(voteEntity);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCard(Long[] cardIds) {
        this.removeByIds(Arrays.asList(cardIds));
        argueService.removeByIds(Arrays.asList(cardIds));
        voteService.removeByIds(Arrays.asList(cardIds));

    }

    @Override
    public PageUtils queryAllCard(Long userId) {
        LambdaQueryWrapper<CardVo> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper

        return new PageUtils(null);
    }


}