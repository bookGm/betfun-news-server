package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InCardBaseDao;
import io.information.modules.app.entity.InCardArgue;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InCardVote;
import io.information.modules.app.service.IInCardArgueService;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInCardVoteService;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InCardBase> page = this.page(
                new Query<InCardBase>().getPage(params),
                new QueryWrapper<InCardBase>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryStateCard(Map<String, Object> map, Long uId) {
        int cCategory = (int) map.get("cCategory");
        if (cCategory == 0) {
            //普通帖子
            QueryWrapper<InCardBase> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(InCardBase::getuId, uId).eq(InCardBase::getcCategory, 0);
            IPage<InCardBase> page = this.page(
                    new Query<InCardBase>().getPage(map),
                    queryWrapper
            );
            return new PageUtils(page);
        } else if (cCategory == 1) {
            //辩论帖子
            QueryWrapper<InCardBase> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(InCardBase::getuId, uId).eq(InCardBase::getcCategory, 1);
            List<InCardBase> bases = this.list(queryWrapper);
            List<Long> cIds = bases.stream().map(InCardBase::getcId).collect(Collectors.toList());
            LambdaQueryWrapper<InCardArgue> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.in(InCardArgue::getcId, cIds);
            IPage<InCardArgue> page = cardArgueService.page(
                    new Query<InCardArgue>().getPage(map),
                    queryWrapper1
            );
            return new PageUtils(page);
        } else {
            //投票帖子
            //辩论帖子
            QueryWrapper<InCardBase> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(InCardBase::getuId, uId).eq(InCardBase::getcCategory, 2);
            List<InCardBase> bases = this.list(queryWrapper);
            List<Long> cIds = bases.stream().map(InCardBase::getcId).collect(Collectors.toList());
            LambdaQueryWrapper<InCardVote> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.in(InCardVote::getcId, cIds);
            IPage<InCardVote> page = cardVoteService.page(
                    new Query<InCardVote>().getPage(map),
                    queryWrapper2
            );
            return new PageUtils(page);
        }
    }

    @Override
    public PageUtils status(Map<String, Object> map) {
        int cCategory = (int) map.get("cCategory");
        if (cCategory == 0) {
            //普通帖子
            QueryWrapper<InCardBase> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(InCardBase::getcCategory, 0);
            IPage<InCardBase> page = this.page(
                    new Query<InCardBase>().getPage(map),
                    queryWrapper
            );
            return new PageUtils(page);
        } else if (cCategory == 1) {
            //辩论帖子
            QueryWrapper<InCardBase> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(InCardBase::getcCategory, 1);
            List<InCardBase> bases = this.list(queryWrapper);
            List<Long> cIds = bases.stream().map(InCardBase::getcId).collect(Collectors.toList());
            LambdaQueryWrapper<InCardArgue> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.in(InCardArgue::getcId, cIds);
            IPage<InCardArgue> page = cardArgueService.page(
                    new Query<InCardArgue>().getPage(map),
                    queryWrapper1
            );
            return new PageUtils(page);
        } else {
            //投票帖子
            //辩论帖子
            QueryWrapper<InCardBase> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(InCardBase::getcCategory, 2);
            List<InCardBase> bases = this.list(queryWrapper);
            List<Long> cIds = bases.stream().map(InCardBase::getcId).collect(Collectors.toList());
            LambdaQueryWrapper<InCardVote> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.in(InCardVote::getcId, cIds);
            IPage<InCardVote> page = cardVoteService.page(
                    new Query<InCardVote>().getPage(map),
                    queryWrapper2
            );
            return new PageUtils(page);
        }
    }

}
