package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.utils.BeanHelper;
import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InCardBaseDao;
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
public class InCardServiceImpl  extends ServiceImpl<InCardBaseDao, InCardBase> implements IInCardService {
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
        Long cId = IdGenerator.getId();
        InCardBase base = card.getBase();
        if (null != card.getArgue() && StringUtil.isNotBlank(card.getArgue().getCaRside())
                && null != card.getArgue().getCaFside() && StringUtil.isNotBlank(card.getArgue().getCaFside())) {
            InCardArgue argue = card.getArgue();
            argue.setcId(cId);
            argueService.save(argue);
        }
        if (null != card.getVote() && StringUtil.isNotBlank(card.getVote().getCvInfo())) {
            InCardVote vote = card.getVote();
            vote.setcId(cId);
            voteService.save(vote);
        }
        base.setcId(cId);
        base.setuId(user.getuId());
        base.setcCreateTime(new Date());
        baseService.save(base);
        logOperate(user.getuId(),cId,NewsEnum.操作_发布);
    }

    /**
     * 记录操作日志
     */
    void logOperate(Long uId, Long tId, NewsEnum e){
        InLog log=new InLog();
        log.setlOperateId(uId);
        log.setlTargetId(tId);
        log.setlTargetType(1);
        log.setlDo(Integer.parseInt(e.getCode()));
        log.setlTime(new Date());
        rabbitTemplate.convertAndSend(Constants.logExchange, Constants.log_Save_RouteKey, log);
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
            }
            if (2 == category) {
                //投票帖子
                InCardVote vote = voteService.getById(base.getcId());
                card.setVote(vote);
            }
        }
        //基础帖子
        return card;
    }

    @Override
    public void delete(Long[] cIds) {
        if (null != cIds && cIds.length > 0) {
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
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> map) {
        LambdaQueryWrapper<InCardBase> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtil.isNotBlank(map.get("uId"))) {
            queryWrapper.eq(InCardBase::getuId, Long.valueOf(String.valueOf(map.get("uId"))));
        }
        if (StringUtil.isNotBlank(map.get("type"))) {
            queryWrapper.eq(InCardBase::getcCategory, Integer.valueOf(String.valueOf(map.get("type"))));
        }
        IPage<InCardBase> page = baseService.page(
                new Query<InCardBase>().getPage(map),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public void update(InCard card) {
        if (null != card) {
            int category = card.getBase().getcCategory();
            switch (category) {
                case 1:
                    InCardArgue argue = BeanHelper.copyProperties(card, InCardArgue.class);
                    if (null != argue) {
                        argueService.updateById(argue);
                    }
                    break;
                case 2:
                    InCardVote vote = BeanHelper.copyProperties(card, InCardVote.class);
                    if (null != vote) {
                        voteService.updateById(vote);
                    }
                    break;
            }
            baseService.updateById(card.getBase());
        }
    }
}
