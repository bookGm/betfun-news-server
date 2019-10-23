package io.information.modules.app.service.impl;

import io.information.modules.app.entity.*;
import io.information.modules.app.service.IInCardArgueService;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInCardService;
import io.information.modules.app.service.IInCardVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InCardServiceImpl implements IInCardService {
    @Autowired
    private IInCardBaseService baseService;
    @Autowired
    private IInCardArgueService argueService;
    @Autowired
    private IInCardVoteService voteService;


    @Override
    public void issueCard(InCard card, InUser user) {
        InCardArgue argue = card.getArgue();
        InCardBase base = card.getBase();
        InCardVote vote = card.getVote();
        if (null != base.getcTitle()) {
            Long cId = base.getcId();
            if (null != argue.getCaRside() && "".equals(argue.getCaRside())
                    && null != argue.getCaFside() && !"".equals(argue.getCaFside())) {
                argue.setcId(cId);
                argueService.save(argue);
            }
            if (null != vote.getCvInfo() && !"".equals(vote.getCvInfo())) {
                vote.setcId(cId);
                voteService.save(vote);
            }
            baseService.save(base);
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
                String[] rNumber = argue.getCaRsideUids().split(",");
                String[] fNumber = argue.getCaFsideUids().split(",");
                argue.setCaRsideNumber(rNumber.length);
                argue.setCaFsideNumber(fNumber.length);
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
    }
}
