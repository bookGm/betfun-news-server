package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.utils.BeanHelper;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.CardBaseDao;
import io.information.modules.news.entity.*;
import io.information.modules.news.service.CardArgueService;
import io.information.modules.news.service.CardBaseService;
import io.information.modules.news.service.CardVoteService;
import io.information.modules.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;


@Service
public class CardBaseServiceImpl extends ServiceImpl<CardBaseDao, CardBaseEntity> implements CardBaseService {
    @Autowired
    private CardArgueService argueService;
    @Autowired
    private CardVoteService voteService;
    @Autowired
    private UserService userService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<CardBaseEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CardBaseEntity::getcCategory, NewsEnum.帖子分类_投票帖.getCode());
        if (null != params.get("key") && StringUtil.isNotBlank(params.get("key"))) {
            String key = String.valueOf(params.get("key"));
            queryWrapper.like(CardBaseEntity::getcTitle, key)
                    .or()
                    .eq(CardBaseEntity::getcId, key);
        }
        queryWrapper.orderByDesc(CardBaseEntity::getcCreateTime);
        IPage<CardBaseEntity> page = this.page(
                new Query<CardBaseEntity>().getPage(params),
                queryWrapper
        );
        for (CardBaseEntity c : page.getRecords()) {
            UserEntity u = userService.getById(c.getuId());
            if (null != u) {
                c.setuName(u.getuName());
            }
        }
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCard(CardVo cardVo) {
        if (cardVo != null) {
            CardBaseEntity baseEntity = BeanHelper.copyProperties(cardVo, CardBaseEntity.class);
            CardArgueEntity argueEntity = BeanHelper.copyProperties(cardVo, CardArgueEntity.class);
            CardVoteEntity voteEntity = BeanHelper.copyProperties(cardVo, CardVoteEntity.class);
            if (null != baseEntity) {
                this.save(baseEntity);
            }
            if (null != argueEntity) {
                argueService.save(argueEntity);
            }
            if (null != voteEntity) {
                voteService.save(voteEntity);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCard(Long[] cardIds) {
        this.removeByIds(Arrays.asList(cardIds));
        argueService.removeByIds(Arrays.asList(cardIds));
        voteService.removeByIds(Arrays.asList(cardIds));

    }

    @Override
    public PageUtils queryAllCard(Map<String, Object> params, Long userId) {
        LambdaQueryWrapper<CardBaseEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(CardBaseEntity::getuId, userId);
        IPage<CardBaseEntity> page = this.page(
                new Query<CardBaseEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }


}