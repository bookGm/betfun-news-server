package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.StringUtil;
import io.information.common.annotation.HashCacheable;
import io.information.common.utils.BeanHelper;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.modules.app.dao.InCardBaseDao;
import io.information.modules.app.dao.InCommonReplyDao;
import io.information.modules.app.dao.InNodeDao;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InNode;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInArticleService;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInNodeService;
import io.information.modules.app.service.IInUserService;
import io.information.modules.app.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 帖子节点表  服务实现类
 * </p>
 *
 * @author zxs
 * @since 2019-11-04
 */
@Service
public class InNodeServiceImpl extends ServiceImpl<InNodeDao, InNode> implements IInNodeService {
    @Autowired
    private IInUserService userService;
    @Autowired
    private IInArticleService articleService;
    @Autowired
    private IInCardBaseService baseService;
    @Autowired
    private InCardBaseDao baseDao;
    @Autowired
    private InCommonReplyDao replyDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InNode> page = this.page(
                new Query<InNode>().getPage(params),
                new QueryWrapper<InNode>()
        );

        return new PageUtils(page);
    }

    @Override
    public Map<Long, String> search(Long noType) {
        LambdaQueryWrapper<InNode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InNode::getNoType, noType);
        List<InNode> nodeList = this.list(queryWrapper);
        if (null != nodeList && !nodeList.isEmpty()) {
            HashMap<Long, String> map = new HashMap<>();
            for (InNode node : nodeList) {
                map.put(node.getNoId(), node.getNoName());
            }
            return map;
        }
        return null;
    }

    @Override
    public Map<Long, List<InNode>> query(Map<String, Object> map) {
        List<InNode> nodes = this.list();
        if (!nodes.isEmpty()) {
            for (InNode node : nodes) {
                //获取节点对应的帖子集合求出总数(size)
                int cardNumber = baseService.count(new LambdaQueryWrapper<InCardBase>().eq(InCardBase::getNoId, node.getNoId()));
                //将节点和对应帖子的总数放入集合
                node.setCardNumber(cardNumber);
            }
        }
        return nodes.stream().collect(Collectors.groupingBy(InNode::getNoType));
    }


    @Override
    public Map<String, Object> special(Map<String, Object> map) {
        //寻找通过任何认证的用户
        LambdaQueryWrapper<InUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InUser::getuAuthStatus, 2);
        IPage<InUser> page = userService.page(
                new Query<InUser>().getPage(map),
                queryWrapper
        );
        List<InUser> users = page.getRecords();
        //拼装 文章 浏览量 获赞数
        Map<String, Object> rm = new HashMap<>();
        for (InUser user : users) {
            List<InArticle> list = articleService.list(new LambdaQueryWrapper<InArticle>().eq(InArticle::getuId, user.getuId()));
            //累计文章数
            rm.put("aCount", list.size());
            //累计阅读量
            LongSummaryStatistics readNumber = list.stream().collect(Collectors.summarizingLong((n) -> n.getaReadNumber() == null ? 0L : n.getaReadNumber()));
            rm.put("rCount", readNumber == null ? 0L : readNumber.getSum());
            //累计点赞数
            LongSummaryStatistics likeNumber = list.stream().collect(Collectors.summarizingLong((n) -> n.getaLike() == null ? 0L : n.getaLike()));
            rm.put("kCount", likeNumber == null ? 0L : likeNumber.getSum());
            //用户信息
            rm.put("user", user);
            //分页数据
            rm.put("pageSize", page.getSize());    //显示条数
            rm.put("currPage", page.getCurrent());    //当前页数
            rm.put("totalCount", page.getTotal());   //总条数
            rm.put("totalPage", page.getPages());  //总页数
        }
        return rm;
    }

    @Override
    public PageUtils cardList(Map<String, Object> map) {
        LambdaQueryWrapper<InCardBase> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("noId") && StringUtil.isNotBlank(map.get("noId"))) {
            Long noId = Long.parseLong(String.valueOf(map.get("noId")));
            queryWrapper.eq(InCardBase::getNoId, noId);

            if (null != map.get("cCategory") && StringUtil.isNotBlank(map.get("cCategory"))) {
                int type = Integer.parseInt(String.valueOf(map.get("cCategory")));
                switch (type) {
                    case 0: //投票
                        queryWrapper.eq(InCardBase::getcCategory, 2);
                        break;
                    case 1: //辩论
                        queryWrapper.eq(InCardBase::getcCategory, 1);
                        break;
                }
            }
            if (null != map.get("type") && StringUtil.isNotBlank(map.get("type"))) {
                int type = Integer.parseInt(String.valueOf(map.get("type")));
                switch (type) {
                    case 0: //最新
                        queryWrapper.orderByDesc(InCardBase::getcCreateTime);
                        break;
                    case 1: //最热
                        queryWrapper.orderByDesc(InCardBase::getcLike);
                        break;
                }
            }
            IPage<InCardBase> page = baseService.page(
                    new Query<InCardBase>().getPage(map),
                    queryWrapper
            );
            return new PageUtils(page);
        }
        return null;
    }


    @Override
    public Map<Integer, List<InUser>> star(Map<String, Object> map) {
        //榜单用户需要为人物
        LambdaQueryWrapper<InUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(InUser::getuPotential, 1, 2);
        List<InUser> list = userService.list(queryWrapper);
        return list.stream().collect(Collectors.groupingBy(InUser::getuPotential));
    }


    @Override
    @HashCacheable(key = RedisKeys.NODES, keyField = "#uId-#type-#noId")
    public Long focus(Long uId, Long noId, Long type) {
        this.baseMapper.increaseFocus(noId);
        return noId;
    }


    @Override
    public CardUserVo cardRecommended(Map<String, Object> map) {
        Integer pageSize = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer currPage = StringUtil.isBlank(map.get("currPage")) ? 0 : Integer.parseInt(String.valueOf(map.get("currPage")));
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            long uId = Long.parseLong(String.valueOf(map.get("uId")));
            InUser user = userService.getById(uId);
            CardUserVo cardUserVo = BeanHelper.copyProperties(user, CardUserVo.class);
            if (null != cardUserVo) {
                LambdaQueryWrapper<InCardBase> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(InCardBase::getuId, uId);
                int cardNumber = baseService.count(queryWrapper);
                if (cardNumber > 0) {
                    cardUserVo.setCardNumber(cardNumber);
                    List<CardBaseVo> cardBaseVos = baseDao.searchTitleAndId(uId, currPage, pageSize);
                    long sum = cardBaseVos.stream().mapToLong(CardBaseVo::getcLike).sum();
                    cardUserVo.setcLike(sum);
                    cardUserVo.setCardBaseVos(cardBaseVos);
                } else {
                    cardUserVo.setCardNumber(0);
                    cardUserVo.setcLike(0L);
                    cardUserVo.setCardBaseVos(null);
                }
                return cardUserVo;
            }
            return null;
        }
        return null;
    }


    @Override
    public List<NodeVo> recommendNode() {
        return this.baseMapper.searchNodeByFocus();
    }


    @Override
    public List<CardBaseVo> heatCard() {
        return baseDao.searchBaseByLike();
    }


    @Override
    public List<NewDynamicVo> newDynamic() {
        List<DynamicCardVo> base = baseDao.searchBaseByTime();
        List<DynamicReplyVo> reply = replyDao.searchReplyByTime();
        List<String> collect = base.stream().map(DynamicCardVo::getcCreateTime).collect(Collectors.toList());
        List<String> list = reply.stream().map(DynamicReplyVo::getCrTime).collect(Collectors.toList());
        collect.addAll(list);
        //TODO  排序
        return null;
    }


}