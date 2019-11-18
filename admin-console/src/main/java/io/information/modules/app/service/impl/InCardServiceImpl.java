package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.JsonUtil;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.utils.*;
import io.information.modules.app.dao.InCardBaseDao;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.IInCardArgueService;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInCardService;
import io.information.modules.app.service.IInCardVoteService;
import io.information.modules.app.vo.CardArgueVo;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InCardServiceImpl extends ServiceImpl<InCardBaseDao, InCardBase> implements IInCardService {
    @Autowired
    IInCardBaseService baseService;
    @Autowired
    IInCardArgueService argueService;
    @Autowired
    IInCardVoteService voteService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RedisUtils redisUtils;


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
        logOperate(user.getuId(), cId, NewsEnum.操作_发布);
    }


    /**
     * 记录操作日志
     */
    void logOperate(Long uId, Long tId, NewsEnum e) {
        InLog log = new InLog();
        log.setlOperateId(uId);
        log.setlTargetId(tId);
        log.setlTargetType(1);
        log.setlDo(Integer.parseInt(e.getCode()));
        log.setlTime(new Date());
        rabbitTemplate.convertAndSend(Constants.logExchange, Constants.log_Save_RouteKey, JsonUtil.toJSONString(log));
    }


    @Override
    public InCard details(Long cId) {
        InCard card = new InCard();
        InCardBase base = baseService.getById(cId);
        if (null != base) {
            card.setBase(base);
            if (null != base.getcCategory()) {
                Integer category = base.getcCategory();
                if (1 == category) {
                    //辩论帖子
                    InCardArgue argue = argueService.getById(base.getcId());
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
        return null;
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


    @Override
    public CardArgueVo loginArgue(Long cId, Long uId) {
        //模糊查询出某类的key  #cid-#uid-#sIndex
        CardArgueVo argueVo = new CardArgueVo();
        List<Map.Entry<Object, Object>> smap = redisUtils.hfget(RedisKeys.SUPPORT, cId + "-" + uId + "-*");
        List<Map.Entry<Object, Object>> jmap = redisUtils.hfget(RedisKeys.JOIN, cId + "-" + uId + "-*");
        //查询支持状态
        if (null != smap && smap.size() > 0) {
            for (Map.Entry<Object, Object> obj : smap) {
                int sStatus = Integer.parseInt(String.valueOf(obj.getKey()));
                argueVo.setIsSupport(sStatus);
            }
        } else {
            argueVo.setIsSupport(2);
        }
        //查询加入状态
        if (null != jmap && jmap.size() > 0) {
            for (Map.Entry<Object, Object> obj : jmap) {
                int jStatus = Integer.parseInt(String.valueOf(obj.getKey()));
                argueVo.setIsJoin(jStatus);
            }
        } else {
            argueVo.setIsSupport(2);
        }

        return argueVo;
    }

}
