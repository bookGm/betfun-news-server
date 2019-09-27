package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.modules.app.dao.InCardBaseDao;
import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InCardArgue;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InCardVote;
import io.information.modules.app.service.IInCardArgueService;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInCardVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资讯帖子基础表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InCardBaseServiceImpl extends ServiceImpl<InCardBaseDao, InCardBase> implements IInCardBaseService {
    @Autowired
    private IInCardArgueService cardArgueService;
    @Autowired
    private IInCardVoteService cardVoteService;


    @Override
    public InCard queryCard(Long cardId) {
        InCard card = new InCard();
        InCardBase base = this.getById(cardId);
        InCardArgue argue = cardArgueService.getById(cardId);
        InCardVote vote = cardVoteService.getById(cardId);
        card.setBase(base);
        card.setArgue(argue);
        card.setVote(vote);
        return card;
    }

    @Override
    public List<InCardBase> queryAllCardBase(Long userId) {
        QueryWrapper<InCardBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InCardBase::getUId,userId);
        return this.list(queryWrapper);
    }

    @Override
    public List<InCard> queryAllCard(Long userId) {
        List<InCard> cards = new ArrayList<>();
        //查询Ids
        List<InCardBase> baseList = this.queryAllCardBase(userId);
        List<Long> cardIds = baseList.stream().map(InCardBase::getCId).collect(Collectors.toList());
        //添加信息
        cardIds.forEach(cardId -> {
            cards.add(this.queryCard(cardId));
        });

        return cards;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllCard(Long userId) {
        List<InCardBase> baseList = this.queryAllCardBase(userId);
        List<Long> cardIds = baseList.stream().map(InCardBase::getCId).collect(Collectors.toList());
        this.deleteCard(cardIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCard(List<Long> cardIds) {
        this.removeByIds(cardIds);
        cardArgueService.removeByIds(cardIds);
        cardVoteService.removeByIds(cardIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllCardBase(Long userId) {
        List<InCardBase> cardList = this.queryAllCardBase(userId);
        List<Long> cardIds = cardList.stream().map(InCardBase::getCId).collect(Collectors.toList());
        this.removeByIds(cardIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCard(InCardBase base, InCardArgue argue, InCardVote vote) {
        argue.setCId(base.getCId());
        vote.setCId(base.getCId());
        this.save(base);
        cardArgueService.save(argue);
        cardVoteService.save(vote);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCard(InCardBase base, InCardArgue argue, InCardVote vote) {
        argue.setCId(base.getCId());
        vote.setCId(base.getCId());
        this.updateById(base);
        cardArgueService.updateById(argue);
        cardVoteService.updateById(vote);
    }
}
