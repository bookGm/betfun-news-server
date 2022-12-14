package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.annotation.HashCacheable;
import io.information.common.utils.*;
import io.information.modules.app.dao.*;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.*;
import io.information.modules.app.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
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
    IInUserService userService;
    @Autowired
    InUserDao userDao;
    @Autowired
    IInArticleService articleService;
    @Autowired
    IInCardBaseService baseService;
    @Autowired
    InCardBaseDao baseDao;
    @Autowired
    InCommonReplyDao replyDao;
    @Autowired
    IInDicService dicService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    InLogDao logDao;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    DicHelper dicHelper;

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
    public PageUtils<List<UserNodeVo>> special(Map<String, Object> map) {
        //寻找通过任何认证的用户
        LambdaQueryWrapper<InUser> queryWrapper = new LambdaQueryWrapper<>();
        Integer pageSize = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer currPage = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        queryWrapper.eq(InUser::getuAuthStatus, NewsEnum.文章状态_已发布.getCode());
        IPage<InUser> page = userService.page(
                new Query<InUser>().getPage(map),
                queryWrapper
        );
        //拼装 文章 浏览量 获赞数
        ArrayList<UserNodeVo> vos = new ArrayList<>();
        for (InUser user : page.getRecords()) {
            UserNodeVo vo = BeanHelper.copyProperties(user, UserNodeVo.class);
            if (null != vo) {
                List<InArticle> list = articleService.list(new LambdaQueryWrapper<InArticle>().eq(InArticle::getuId, user.getuId()).eq(InArticle::getaStatus, NewsEnum.文章状态_已发布.getCode()));
                //累计文章数
                vo.setArticleNumber(list.size());
                //累计浏览量
                Long readNumber = 0L;
                for (InArticle article : list) {
                    Object number = redisTemplate.opsForValue().get(RedisKeys.ABROWSE + article.getaId());
                    if (null != number) {
                        readNumber += Long.parseLong(String.valueOf(number));
                    }
                }
                vo.setReadNumber(readNumber);
                //累计点赞数
                LongSummaryStatistics likeNumber = list.stream().collect(Collectors.summarizingLong((n) -> n.getaLike() == null ? 0L : n.getaLike()));
                vo.setLikeNumber(likeNumber == null ? 0L : likeNumber.getSum());
                //添加
                vos.add(vo);
            }
        }
        int total = (int) page.getTotal();
        return new PageUtils(vos, total, pageSize, currPage);
    }

    @Override
    public PageUtils<UserCardVo> cardList(Map<String, Object> map) {
        LambdaQueryWrapper<InCardBase> queryWrapper = new LambdaQueryWrapper<>();
        Integer pageSize = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer currPage = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        if (null != map.get("noId") && StringUtil.isNotBlank(map.get("noId"))) {
            Long noId = Long.parseLong(String.valueOf(map.get("noId")));
            queryWrapper.eq(InCardBase::getNoId, noId);
        }
        if (null != map.get("cCategory") && StringUtil.isNotBlank(map.get("cCategory"))) {
            int cCategory = Integer.parseInt(String.valueOf(map.get("cCategory")));
            if (cCategory != 0) {
                queryWrapper.eq(InCardBase::getcCategory, cCategory);
            }
        }
        if (null != map.get("type") && StringUtil.isNotBlank(map.get("type"))) {
            int type = Integer.parseInt(String.valueOf(map.get("type")));
            switch (type) {
                case 1: //最新
                    queryWrapper.orderByDesc(InCardBase::getcCreateTime).orderByDesc(InCardBase::getcReadNumber);
                    break;
                case 2: //最热
                    queryWrapper.orderByDesc(InCardBase::getcLike);
                    break;
            }
        }
        IPage<InCardBase> page = baseService.page(
                new Query<InCardBase>().getPage(map),
                queryWrapper
        );
        ArrayList<UserCardVo> list = new ArrayList<>();
        for (InCardBase base : page.getRecords()) {
            if (null != base.getuId() && StringUtil.isNotBlank(base.getuId())) {
                InUser user = userService.getById(base.getuId());
                if (null != user) {
                    if (user.getuPhoto() == null || "".equals(user.getuPhone())) {
                        user.setuPhoto("http://guansuo.info/news/upload/20191231115456head.png");
                    }
                    UserCardVo cardVo = BeanHelper.copyProperties(user, UserCardVo.class);
                    if (null != cardVo) {
                        String simpleTime = DateUtils.getSimpleTime(base.getcCreateTime() == null ? new Date() : base.getcCreateTime());
                        cardVo.setTime(simpleTime);
//                        InDic dic = dicService.getById(base.getcNodeCategory() == null ? 0 : base.getcNodeCategory());
                        String dicName = dicHelper.getDicName(base.getcNodeCategory()) == null
                                ? "" : dicHelper.getDicName(base.getcNodeCategory());
                        cardVo.setType(dicName);
                        cardVo.setcId(base.getcId());
                        cardVo.setcTitle(base.getcTitle());
                        cardVo.setcId(base.getcId());
                        Object number = redisTemplate.opsForValue().get(RedisKeys.CARDBROWSE + cardVo.getcId());
                        if (null != number) {
                            long readNumber = Long.parseLong(String.valueOf(number));
                            cardVo.setReadNumber(readNumber);
                        }
                        cardVo.setReplyNumber(base.getcCritic());
                        list.add(cardVo);
                    }
                } else {
                    InUser user1 = new InUser();
                    user1.setuNick("用户已不存在");
                    user1.setuPhoto("http://guansuo.info/news/upload/20191231115456head.png");
                    UserCardVo cardVo = BeanHelper.copyProperties(user1, UserCardVo.class);
                    list.add(cardVo);
                }
            }
        }
        int total = (int) page.getTotal();
        return new PageUtils(list, total, pageSize, currPage);
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
    public String focus(Long uId, Long noId, Long type) {
        userDao.addFocus(uId);
        this.baseMapper.increaseFocus(noId);
        return String.valueOf(type);
    }

    @Override
    public void delFocus(Long uId, Long noId) {
        userDao.delFocus(uId);
        this.baseMapper.removeFocus(noId);
    }

    @Override
    public Boolean isFocus(Long uId, Long noId) {
        Object obj = redisUtils.hfget(RedisKeys.NODES, uId + "-*-" + noId);
        return null != obj && ((List) obj).size() > 0;
    }

    @Override
    public CardUserVo cardRecommended(Map<String, Object> map) {
        Integer pageSize = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer currPage = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            long uId = Long.parseLong(String.valueOf(map.get("uId")));
            InUser user = userService.getById(uId);
            if (null != user) {
                CardUserVo cardUserVo = BeanHelper.copyProperties(user, CardUserVo.class);
                if (null != cardUserVo) {
                    LambdaQueryWrapper<InCardBase> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(InCardBase::getuId, uId);
                    int cardNumber = baseService.count(queryWrapper);
                    if (cardNumber > 0) {
                        cardUserVo.setCardNumber(cardNumber);
                        List<Object> list = baseDao.searchTitleAndId(uId, (currPage - 1 < 0 ? 0 : currPage - 1) * pageSize, pageSize);
                        List<CardBaseVo> cardBaseVos = (List<CardBaseVo>) list.get(0);
                        int total = ((List<Integer>) list.get(1)).get(0);//总量
                        long sum = cardBaseVos.stream().mapToLong(CardBaseVo::getcLike).sum();
                        cardUserVo.setcLike(sum);
                        cardUserVo.setCardBaseVos(cardBaseVos);
                        cardUserVo.setTotalCount((int) Math.ceil((double) total / pageSize));
                        cardUserVo.setCurrPage(currPage);
                        cardUserVo.setPageSize(pageSize);
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
    public List<DynamicVo> newDynamic() {
        List<InLog> logs = logDao.newDynamic();
        if (null != logs) {
            ArrayList<DynamicVo> vos = new ArrayList<>();
            for (InLog log : logs) {
                DynamicVo vo = new DynamicVo();
                vo.setuId(log.getlOperateId());
                vo.settId(log.getlTargetId());
                vo.setuName(log.getlOperateName());
                vo.settName(log.getlTargetName());
                vo.setSimpleDate(DateUtils.getSimpleTime(log.getlTime()));
                switch (log.getlDo()) {
                    case 3:
                        vo.setdType("评论");
                        break;
                    case 4:
                        vo.setdType("发布");
                        break;
                }
                vos.add(vo);
            }
            return vos;
        }
        return null;
    }

    @Override
    public UserArticleVo articleList(Map<String, Object> map) {
        LambdaQueryWrapper<InArticle> queryWrapper = new LambdaQueryWrapper<>();
        Integer pageSize = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer currPage = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            long uId = Long.parseLong(String.valueOf(map.get("uId")));
            InUser user = userService.getById(uId);
            UserArticleVo vo = BeanHelper.copyProperties(user, UserArticleVo.class);
            if (null != vo) {
                queryWrapper.eq(InArticle::getuId, uId);
                List<InArticle> articles = articleService.list(queryWrapper);
                //获赞数
                long likeNumber = articles.stream().mapToLong(InArticle::getaLike).sum();
                //浏览量
                long readNumber = 0L;
                for (InArticle article : articles) {
                    Object number = redisTemplate.opsForValue().get(RedisKeys.ABROWSE + article.getaId());
                    if (null != number) {
                        readNumber += Long.parseLong(String.valueOf(number));
                    }
                }
                vo.setReadNumber(readNumber);
                vo.setLikeNumber(likeNumber);
                IPage<InArticle> page = articleService.page(
                        new Query<InArticle>().getPage(map),
                        queryWrapper
                );

                //文章信息
                List<InArticle> articleList = page.getRecords();
                if (null != articleList) {
                    for (InArticle article : articleList) {
                        Object number = redisTemplate.opsForValue().get(RedisKeys.ABROWSE + article.getaId());
                        if (null != number) {
                            article.setaReadNumber(Long.parseLong(String.valueOf(number)));
                        }
                    }
                    vo.setArticles(articleList);
                }
                //分页数据
                vo.setTotalPage(articles.size());
                vo.setCurrPage(currPage);
                vo.setPageSize(pageSize);
                return vo;
            }
            return null;
        }
        return null;
    }


    @Override
    public UserSpecialVo specialList(Map<String, Object> map) {
        LambdaQueryWrapper<InArticle> queryWrapper = new LambdaQueryWrapper<>();
        Integer pageSize = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer currPage = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            long uId = Long.parseLong(String.valueOf(map.get("uId")));
            InUser user = userService.getById(uId);
            UserSpecialVo vo = BeanHelper.copyProperties(user, UserSpecialVo.class);
            if (null != vo) {
                queryWrapper.eq(InArticle::getuId, uId);
                List<InArticle> articles = articleService.list(queryWrapper);
                //获赞数
                long likeNumber = articles.stream().mapToLong(InArticle::getaLike).sum();
                //浏览量
                long readNumber = 0L;
                for (InArticle article : articles) {
                    Object number = redisTemplate.opsForValue().get(RedisKeys.ABROWSE + article.getaId());
                    if (null != number) {
                        readNumber += Long.parseLong(String.valueOf(number));
                    }
                }
                vo.setLikeNumber(likeNumber);
                vo.setReadNumber(readNumber);
                IPage<InArticle> page = articleService.page(
                        new Query<InArticle>().getPage(map),
                        queryWrapper
                );
                //文章信息
                List<InArticle> articleList = page.getRecords();
                if (null != articleList) {
                    for (InArticle article : articleList) {
                        Object number = redisTemplate.opsForValue().get(RedisKeys.ABROWSE + article.getaId());
                        if (null != number) {
                            article.setaReadNumber(Long.parseLong(String.valueOf(number)));
                        }
                    }
                    vo.setArticles(articleList);
                }
                //分页数据
                vo.setTotalPage(articles.size());
                vo.setCurrPage(currPage);
                vo.setPageSize(pageSize);
                return vo;
            }
            return null;
        }
        return null;
    }
}