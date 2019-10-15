package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
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

import java.util.List;
import java.util.Map;
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
    public PageUtils queryAllCardBase(Map<String,Object> map,Long userId) {
        QueryWrapper<InCardBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InCardBase::getuId, userId);
        IPage<InCardBase> page = this.page(
                new Query<InCardBase>().getPage(map),
                queryWrapper
        );
        return new PageUtils(page);
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InCardBase> page = this.page(
                new Query<InCardBase>().getPage(params),
                new QueryWrapper<InCardBase>()
        );
        return new PageUtils(page);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllCard(Long userId) {
        LambdaQueryWrapper<InCardBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InCardBase::getuId,userId);
        List<InCardBase> baseList = this.list(queryWrapper);
        List<Long> cardIds = baseList.stream().map(InCardBase::getcId).collect(Collectors.toList());
        cardVoteService.removeByIds(cardIds);
        cardArgueService.removeByIds(cardIds);
        this.removeByIds(cardIds);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllCardBase(Long userId) {
        LambdaQueryWrapper<InCardBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InCardBase::getuId,userId);
        List<InCardBase> list = this.list(queryWrapper);
        List<Long> cardIds = list.stream().map(InCardBase::getcId).collect(Collectors.toList());
        this.removeByIds(cardIds);
    }
}
