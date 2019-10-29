package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.JsonUtil;
import com.guansuo.common.StringUtil;
import io.information.common.exception.ExceptionEnum;
import io.information.common.exception.IMException;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.common.utils.RedisUtils;
import io.information.modules.app.dao.InUserDao;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 资讯用户表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InUserServiceImpl extends ServiceImpl<InUserDao, InUser> implements IInUserService {
    private static final Logger LOG = LoggerFactory.getLogger(InUserServiceImpl.class);
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    private IInArticleService articleService;
    @Autowired
    private IInCardService cardService;
    @Autowired
    private IInCardBaseService baseService;
    @Autowired
    private IInCommonReplyService commonReplyService;
    @Autowired
    private IInActivityService activityService;

    @Override
    public PageUtils queryUsersByArgueIds(Map<String, Object> params) {
        String userIds = null;
        String caFsideUids = (String) params.get("caFsideUids");
        String caRsideUids = (String) params.get("caRsideUids");
        if (!"".equals(caFsideUids) && !caFsideUids.isEmpty()) {
            userIds = (String) params.get("caFsideUids");
        }
        if (!"".equals(caRsideUids) && !caRsideUids.isEmpty()) {
            userIds = (String) params.get("caRsideUids");
        }

        if (!userIds.isEmpty()) {
            String[] ids = caFsideUids.split(",");
            List<Long> idList = Arrays.stream(ids).map(Long::valueOf).collect(Collectors.toList());

            QueryWrapper<InUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(InUser::getuId, idList);
            IPage<InUser> page = this.page(
                    new Query<InUser>().getPage(params),
                    queryWrapper
            );
            return new PageUtils(page);
        }
        return null;
    }

    @Override
    public Boolean saveWithCache(InUser inUser) {
        if (this.save(inUser)) {
            try {
                redisUtils.hset(RedisKeys.INUSER, String.valueOf(inUser.getuId()), JsonUtil.toJSONString(inUser));
            } catch (Exception e) {
                LOG.error("新注册用户:" + inUser.getuPhone() + "放入缓存失败");
            }
            return true;
        }
        return false;
    }

    @Override
    public void change(Map<String, Object> map, InUser user) {
        String dbPwd = user.getuPwd();
        map.get("输入密码");
        //TODO
    }

    @Override
    @Cacheable(value = RedisKeys.FOCUS, key = "#uId+'-'+#fId")
    public Long focus(Long uId, Long fId) {
        this.baseMapper.addFans(uId);
        this.baseMapper.addFocus(fId);
        return fId;
    }

    @Override
    public PageUtils comment(Map<String, Object> params) {
        //根据用户ID查询所有目标ID<根>或被评论ID找到回复用户的评论
        return commonReplyService.userMsg(params);
    }

    @Override
    public Map<String, Object> honor(Long uId) {
        LambdaQueryWrapper<InArticle> articleQuery = new LambdaQueryWrapper<>();
        articleQuery.eq(InArticle::getuId, uId);
        List<InArticle> articleList = articleService.list(articleQuery);
        long likeSum = articleList.stream().mapToLong(InArticle::getaLike).sum();
        LambdaQueryWrapper<InCommonReply> replyQuery = new LambdaQueryWrapper<>();
        replyQuery.eq(InCommonReply::gettId, uId).or().eq(InCommonReply::getToCrId, uId);
        List<InCommonReply> replyList = commonReplyService.list(replyQuery);
        HashMap<String, Object> map = new HashMap<>();
        map.put("artNumber", articleList.size());
        map.put("artLikeNumber", likeSum);
        map.put("replyNumber", replyList.size());
        return map;
    }

    @Override
    public PageUtils card(Map<String, Object> map) {
        return cardService.queryPage(map);
    }

    @Override
    public PageUtils reply(Map<String, Object> map) {
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            Long uId = (Long) map.get("uId");
            //根据用户ID查询所有目标ID<根>或被评论ID找到回复用户的评论
            LambdaQueryWrapper<InCardBase> cardQuery = new LambdaQueryWrapper<>();
            cardQuery.eq(InCardBase::getuId, uId);
            List<InCardBase> baseList = baseService.list(cardQuery);
            //获取用户帖子ID
            List<Long> cIds = baseList.stream().map(InCardBase::getcId).collect(Collectors.toList());
            //获取回帖信息
            return commonReplyService.reply(map, cIds);
        }
        return null;
    }

    @Override
    public PageUtils like(Map<String, Object> map) {
        if (null != map.get("Type") && StringUtil.isNotBlank(map.get("Type"))) {
            Long uId = (Long) map.get("uId");
            int type = (int) map.get("Type");
            Integer pageSize = (Integer) map.get("pageSize");
            Integer currPage = (Integer) map.get("currPage");
            //模糊查询出某类的key
            Set<String> keys = redisTemplate.keys("*_" + uId + type);
            //点赞目标信息，点赞用户信息，点赞时间，点赞类型
            List<InLikeVo> list = new ArrayList<>();
            switch (type) {
                case 0:  //文章
                    for (String key : keys) {
                        InLikeVo likeVo = new InLikeVo();
                        String[] str = key.split("_");
                        Long aId = Long.valueOf(str[0]);
                        InArticle article = articleService.getById(aId);
                        InUser user = this.getById(article.getuId());  //点赞用户
                        Date date = (Date) redisUtils.hget(RedisKeys.LIKE, key);  //点赞时间
                        likeVo.setNick(user.getuNick());
                        likeVo.setType("文章");
                        likeVo.setPhoto(user.getuPhoto());
                        likeVo.setData(article.getaTitle());
                        likeVo.setTime(date);
                        list.add(likeVo);
                    }
                    break;
                case 1:  //帖子
                    for (String key : keys) {
                        InLikeVo likeVo = new InLikeVo();
                        String[] str = key.split("_");
                        Long aId = Long.valueOf(str[0]);
                        InCardBase base = baseService.getById(aId);
                        InUser user = this.getById(base.getuId());  //点赞用户
                        Date date = (Date) redisUtils.hget(RedisKeys.LIKE, key);  //点赞时间
                        likeVo.setNick(user.getuNick());
                        likeVo.setType("帖子");
                        likeVo.setPhoto(user.getuPhoto());
                        likeVo.setData(base.getcTitle());
                        likeVo.setTime(date);
                        list.add(likeVo);
                    }
                    break;
                case 2:  //活动
                    for (String key : keys) {
                        InLikeVo likeVo = new InLikeVo();
                        String[] str = key.split("_");
                        Long aId = Long.valueOf(str[0]);
                        InActivity activity = activityService.getById(aId);
                        InUser user = this.getById(activity.getuId());  //点赞用户
                        Date date = (Date) redisUtils.hget(RedisKeys.LIKE, key);  //点赞时间
                        likeVo.setNick(user.getuNick());
                        likeVo.setType("活动");
                        likeVo.setPhoto(user.getuPhoto());
                        likeVo.setData(activity.getActTitle());
                        likeVo.setTime(date);
                        list.add(likeVo);
                    }
                    break;
            }
            int totalCount = list.size();
            ArrayList<InLikeVo> newsLike = new ArrayList<>();
            for (int i = currPage * pageSize; i < currPage * pageSize + pageSize; i++) {
                InLikeVo likeVo = list.get(i);
                newsLike.add(likeVo);
            }

            return new PageUtils(newsLike,totalCount,pageSize,currPage);
        }
        return null;
    }

    @Override
    public PageUtils active(Map<String, Object> map) {
        LambdaQueryWrapper<InActivity> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("Type") && StringUtil.isNotBlank(map.get("Type"))) {
            Integer type = (Integer) map.get("Type");
            switch (type) {
                case 0:  //获取未开始活动    开始时间 gt> 当前时间
                    queryWrapper.gt(InActivity::getActStartTime, new Date());
                    break;
                case 1:  //获取进行中活动    开始时间 it 当前时间  &&  结束时间 gt 当前时间
                    queryWrapper.lt(InActivity::getActStartTime, new Date())
                            .gt(InActivity::getActCloseTime, new Date());
                    break;
                case 2:  //获取已结束活动    结束时间 it< 当前时间
                    queryWrapper.lt(InActivity::getActCloseTime, new Date());
                    break;
            }
        }
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            queryWrapper.eq(InActivity::getuId, map.get("uId"));
        }
        IPage<InActivity> page = activityService.page(
                new Query<InActivity>().getPage(map),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public InUser findUser(String username, String password) {
        QueryWrapper<InUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InUser::getuName, username);
        InUser user = this.getOne(queryWrapper);
        //2. 判断是否存在
        if (user == null) {
            //用户名错误
            throw new IMException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }
        //3. 校验密码  解密
        if (!password.equals(user.getuPwd())) {
            //密码错误
            throw new IMException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }

        return user;
    }

}
