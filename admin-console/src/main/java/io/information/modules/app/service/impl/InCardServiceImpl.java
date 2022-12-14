package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.JsonUtil;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.utils.*;
import io.information.modules.app.dao.InCardBaseDao;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.*;
import io.information.modules.app.vo.CardArgueVo;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InCardServiceImpl extends ServiceImpl<InCardBaseDao, InCardBase> implements IInCardService {
    @Autowired
    IInCardVoteService iInCardVoteService;
    @Autowired
    IInCardArgueService argueService;
    @Autowired
    IInCardBaseService baseService;
    @Autowired
    IInCardVoteService voteService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    IInUserService iInUserService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    DicHelper dicHelper;


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
        logOperate(user.getuId(), cId, card.getBase().getcTitle(), NewsEnum.??????_??????);
    }


    /**
     * ??????????????????
     */
    void logOperate(Long uId, Long tId, String tName, NewsEnum e) {
        InLog log = new InLog();
        log.setlOperateId(uId);
        log.setlTargetId(tId);
        log.setlTargetType(1);
        log.setlTargetName(tName);
        log.setlDo(Integer.parseInt(e.getCode()));
        log.setlTime(new Date());
        rabbitTemplate.convertAndSend(Constants.logExchange, Constants.log_Save_RouteKey, JsonUtil.toJSONString(log));
    }


    @Override
    public InCard details(Map<String, Object> map) {
        if (null != map.get("cId") && StringUtil.isNotBlank(map.get("cId"))) {
            long cId = Long.parseLong(String.valueOf(map.get("cId")));
            InCard card = new InCard();
            InCardBase base = baseService.getById(cId);
            if (null != base) {
                Object number = redisTemplate.opsForValue().get(RedisKeys.CARDBROWSE + cId);
                if (null != number) {
                    long readNumber = Long.parseLong(String.valueOf(number));
                    base.setcReadNumber(readNumber);
                }
                if (null != base.getcNodeCategory()) {
                    base.setcNodeCategoryValue(dicHelper.getDicName(base.getcNodeCategory().longValue()));
                }
                if (null != iInUserService.getById(base.getuId())) {
                    InUser user = iInUserService.getById(base.getuId());
                    base.setuNick(user.getuNick() == null ? user.getuName() : user.getuNick());
                }
                base.setcSimpleTime(DateUtils.getSimpleTime(base.getcCreateTime()));
                card.setBase(base);
                if (null != base.getcCategory()) {
                    switch (base.getcCategory()) {
                        case 1:
                            //????????????
                            InCardArgue argue = argueService.getById(base.getcId());
                            if (null != argue) {
                                long currTime = new Date().getTime();   //????????????
                                long closeTime = argue.getCaCloseTime().getTime();  //????????????
                                if (currTime > closeTime) {
                                    //?????????
                                    argue.setTimeType(true);
                                } else if (currTime < closeTime) {
                                    //?????????
                                    argue.setTimeType(false);
                                } else {
                                    argue.setTimeType(true);
                                }
                                card.setArgue(argue);
                            }
                            break;
                        case 2:
                            //????????????
                            InCardVote vote = voteService.getById(base.getcId());
                            if (null != vote) {
                                long currTime = new Date().getTime();   //????????????
                                long closeTime = vote.getCvCloseTime().getTime();   //????????????
                                if (currTime > closeTime) {
                                    //?????????
                                    vote.setTimeType(true);
                                } else if (currTime < closeTime) {
                                    //?????????
                                    vote.setTimeType(false);
                                } else {
                                    vote.setTimeType(true);
                                }
                                //????????????
                                List<Map.Entry<Object, Object>> votes = redisUtils.hfget(RedisKeys.VOTE, cId + "-*");
                                vote.setVoteNumber(votes == null ? 0 : votes.size());
                                //??????????????????
                                if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
                                    long uId = Long.parseLong(String.valueOf(map.get("uId")));
                                    String optIndexs = "";
                                    List<Map.Entry<Object, Object>> list = redisUtils.hfget(RedisKeys.VOTE, cId + "-" + uId);
                                    if (null == list) {
                                        vote.setVote(false);
                                    } else {
                                        for (Map.Entry<Object, Object> entry : list) {
                                            optIndexs = (String) entry.getValue();
                                        }
                                        vote.setOptIndexs(optIndexs);
                                        vote.setVote(true);
                                    }
                                }
                                card.setVote(vote);
                            }
                            break;
                    }
                }
                return card;
            }
            return null;
        }
        return null;
    }

    @Override
    public void delete(Long[] cIds) {
        if (null != cIds && cIds.length > 0) {
            for (Long cId : cIds) {
                InCardBase base = baseService.getById(cId);
                if (null != base) {
                    if (null != base.getcCategory()) {
                        switch (base.getcCategory()) {
                            case 1:
                                //????????????
                                argueService.removeById(cId);
                                break;
                            case 2:
                                //????????????
                                voteService.removeById(cId);
                                break;
                        }
                    }
                    //????????????
                    baseService.removeById(cId);
                }
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
        for (InCardBase base : page.getRecords()) {
            Object number = redisTemplate.opsForValue().get(RedisKeys.CARDBROWSE + base.getcId());
            if (null != number) {
                long readNumber = Long.parseLong(String.valueOf(number));
                base.setcReadNumber(readNumber);
            }
        }
        return new PageUtils(page);
    }

    @Override
    public void update(InCard card) {
        if (null != card) {
            int category = card.getBase().getcCategory();
            switch (category) {
                case 1:
                    InCardArgue argue = BeanHelper.copyProperties(card.getArgue(), InCardArgue.class);
                    if (null != argue) {
                        argue.setcId(card.getBase().getcId());
                        InCardArgue flag = argueService.getById(argue.getcId());
                        if (null != flag) {
                            argueService.updateById(argue);
                        } else {
                            argueService.save(argue);
                        }
                    }
                    break;
                case 2:
                    InCardVote vote = BeanHelper.copyProperties(card.getVote(), InCardVote.class);
                    if (null != vote) {
                        vote.setcId(card.getBase().getcId());
                        InCardVote flag = voteService.getById(vote.getcId());
                        if (null != flag) {
                            voteService.updateById(vote);
                        } else {
                            voteService.save(vote);
                        }
                    }
                    break;
            }
            baseService.updateById(card.getBase());
        }
    }


    @Override
    public CardArgueVo loginArgue(Long cId, Long uId) {
        //????????????????????????key  #cid-#uid-#sIndex
        CardArgueVo argueVo = new CardArgueVo();
        List<Map.Entry<Object, Object>> smap = redisUtils.hfget(RedisKeys.SUPPORT, cId + "-" + uId);
        List<Map.Entry<Object, Object>> jmap = redisUtils.hfget(RedisKeys.JOIN, cId + "-" + uId);
        //??????????????????
        if (null != smap && smap.size() > 0) {
            for (Map.Entry<Object, Object> obj : smap) {
                int sStatus = Integer.parseInt(String.valueOf(obj.getValue()));
                argueVo.setIsSupport(sStatus);
            }
        } else {
            argueVo.setIsSupport(2);
        }
        //??????????????????
        if (null != jmap && jmap.size() > 0) {
            for (Map.Entry<Object, Object> obj : jmap) {
                int jStatus = Integer.parseInt(String.valueOf(obj.getValue()));
                argueVo.setIsJoin(jStatus);
            }
        } else {
            argueVo.setIsJoin(2);
        }

        return argueVo;
    }

}
