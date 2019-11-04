package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guansuo.common.StringUtil;
import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.IInCardArgueService;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInCardService;
import io.information.modules.app.service.IInCardVoteService;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class InCardServiceImpl implements IInCardService {
    @Autowired
    private IInCardBaseService baseService;
    @Autowired
    private IInCardArgueService argueService;
    @Autowired
    private IInCardVoteService voteService;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void issueCard(InCard card, InUser user) {
        long cId = IdGenerator.getId();
        InCardArgue argue = card.getArgue();
        InCardBase base = card.getBase();
        InCardVote vote = card.getVote();
        base.setcId(cId);
        if (null != base.getcTitle()) {
            if (null != argue.getCaRside() && StringUtil.isNotBlank(argue.getCaRside())
                    && null != argue.getCaFside() && StringUtil.isNotBlank(argue.getCaFside())) {
                argue.setcId(cId);
                argueService.save(argue);
                rabbitTemplate.convertAndSend(Constants.cardExchange,
                        Constants.card_Save_RouteKey, argue);
            }
            if (null != vote.getCvInfo() && StringUtil.isNotBlank(vote.getCvInfo())) {
                vote.setcId(cId);
                voteService.save(vote);
                rabbitTemplate.convertAndSend(Constants.cardExchange,
                        Constants.card_Save_RouteKey, vote);
            }
            base.setcCreateTime(new Date());
            baseService.save(base);
            rabbitTemplate.convertAndSend(Constants.cardExchange,
                    Constants.card_Save_RouteKey, base);
        }
    }

    @Override
    public InCard details(Long cId) {
        InCard card = new InCard();
        InCardBase base = baseService.getById(cId);
        Integer category = base.getcCategory();
        card.setBase(base);
        if (null != category) {
            if (1 == category) {
                //辩论帖子
                InCardArgue argue = argueService.getById(base.getcId());
                if (null != argue.getCaRsideUids() && StringUtil.isNotBlank(argue.getCaRsideUids())) {
                    String[] rNumber = argue.getCaRsideUids().split(",");
                    argue.setCaRsideNumber(rNumber.length);
                }
                if (null != argue.getCaFsideUids() && StringUtil.isNotBlank(argue.getCaFsideUids())) {
                    String[] fNumber = argue.getCaFsideUids().split(",");
                    argue.setCaFsideNumber(fNumber.length);
                }
                card.setArgue(argue);
                return card;
            }
            if (2 == category) {
                //投票帖子
                InCardVote vote = voteService.getById(base.getcId());
                card.setVote(vote);
                return card;
            }
        }
        //基础帖子
        return card;
    }

    @Override
    public void delete(Long[] cIds) {
        for (Long cId : cIds) {
            InCardBase base = baseService.getById(cId);
            Integer category = base.getcCategory();
            if (null != category) {
                if (1 == category) {
                    //辩论帖子
                    argueService.removeById(cId);
                }
                if (2 == category) {
                    //投票帖子
                    voteService.removeById(cId);
                }
            }
            //基础帖子
            baseService.removeById(cId);
        }
        rabbitTemplate.convertAndSend(Constants.cardExchange,
                Constants.card_Delete_RouteKey, cIds);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> map) {
        LambdaQueryWrapper<InCardBase> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("uId")) {
            queryWrapper.eq(InCardBase::getuId, Long.valueOf(String.valueOf(map.get("type"))));
        }
        if (null != map.get("type")) {
            queryWrapper.eq(InCardBase::getcCategory, Integer.valueOf(String.valueOf(map.get("type"))));
        }
        IPage<InCardBase> page = baseService.page(
                new Query<InCardBase>().getPage(map),
                queryWrapper
        );
        return new PageUtils(page);
    }
}
