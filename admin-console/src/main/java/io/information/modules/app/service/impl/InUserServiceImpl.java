package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.JsonUtil;
import io.information.common.exception.ExceptionEnum;
import io.information.common.exception.IMException;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.common.utils.RedisUtils;
import io.information.modules.app.dao.InUserDao;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InCommonReply;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private IInCardService cardService;
    @Autowired
    private IInArticleService articleService;
    @Autowired
    private IInCommonReplyService commonReplyService;

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
                redisUtils.hset(RedisKeys.INUSER,String.valueOf(inUser.getuId()), JsonUtil.toJSONString(inUser));
            } catch (Exception e) {
                LOG.error("新注册用户:"+inUser.getuPhone()+"放入缓存失败");
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
    @Cacheable(value=RedisKeys.FOCUS,key = "#uId+'-'+#fId")
    public Long focus(Long uId, Long fId) {
        this.baseMapper.addFans(uId);
        this.baseMapper.addFocus(fId);
        return fId;
    }

    @Override
    public PageUtils comment(Map<String,Object> params) {
        //根据用户ID查询所有目标ID<根>或被评论ID找到回复用户的评论
        return commonReplyService.userMsg(params);
    }

    @Override
    public Map<String, Object> honor(Long uId) {
        LambdaQueryWrapper<InArticle> articleQuery = new LambdaQueryWrapper<>();
        articleQuery.eq(InArticle::getuId,uId);
        List<InArticle> articleList = articleService.list(articleQuery);
        long likeSum = articleList.stream().mapToLong(InArticle::getaLike).sum();
        LambdaQueryWrapper<InCommonReply> replyQuery = new LambdaQueryWrapper<>();
        replyQuery.eq(InCommonReply::gettId,uId).or().eq(InCommonReply::getToCrId,uId);
        List<InCommonReply> replyList = commonReplyService.list(replyQuery);
        HashMap<String, Object> map = new HashMap<>();
        map.put("artNumber",articleList.size());
        map.put("artLikeNumber",likeSum);
        map.put("replyNumber",replyList.size());
        return map;
    }

    @Override
    public PageUtils card(Map<String, Object> map) {
        return cardService.queryPage(map);
    }

    @Override
    public PageUtils reply(Map<String, Object> map) {
        //根据用户ID查询所有目标ID<根>或被评论ID找到回复用户的评论
        LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InCommonReply::gettId);
        return null;
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
