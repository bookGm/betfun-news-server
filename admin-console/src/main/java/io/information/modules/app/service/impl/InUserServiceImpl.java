package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.JsonUtil;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.annotation.HashCacheable;
import io.information.common.exception.ExceptionEnum;
import io.information.common.exception.IMException;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.common.utils.RedisUtils;
import io.information.modules.app.dao.InUserDao;
import io.information.modules.app.dto.CollectDTO;
import io.information.modules.app.dto.InUserDTO;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void change(String uPwd, String newPwd, InUser user) {
        //3. 校验密码  解密
        if (!user.getuPwd().equals(uPwd)) {
            //密码错误
            throw new IMException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        } else {
            user.setuPwd(newPwd);
            this.updateById(user);
        }
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
    @HashCacheable(key = RedisKeys.FOCUS, keyField = "#uId-#status-#fId")
    public Long focus(Long uId, Long fId, Integer status) {
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

    private void getTitle(Long id, String type, InLikeVo likeVo) {
        if (NewsEnum.点赞_文章.getCode().equals(type)) {
            likeVo.setData(articleService.getById(id).getaTitle());
            likeVo.setType(NewsEnum.点赞_文章.getName());
        }
        if (NewsEnum.点赞_帖子.getCode().equals(type)) {
            likeVo.setData(baseService.getById(id).getcTitle());
            likeVo.setType(NewsEnum.点赞_帖子.getName());
        }
        if (NewsEnum.点赞_活动.getCode().equals(type)) {
            likeVo.setData(activityService.getById(id).getActTitle());
            likeVo.setType(NewsEnum.点赞_活动.getName());
        }
    }

    @Override
    public PageUtils like(Map<String, Object> map, Long uId) {
        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer page = StringUtil.isBlank(map.get("currPage")) ? 0 : Integer.parseInt(String.valueOf(map.get("currPage")));
        Integer bindex = page * size;
        //模糊查询出某类的key  #id-#uid-#tId-#type
        List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.LIKE, "*-*-" + uId + "-*");
        ArrayList<InLikeVo> newsLike = null;
        if (null != cmap && cmap.size() > 0) {
            newsLike = new ArrayList<>();
            if (bindex + size < cmap.size()) {
                cmap = cmap.subList(bindex, bindex + size);
            }
        } else {
            return new PageUtils(newsLike, 0, size, page);
        }
        for (Map.Entry<Object, Object> obj : cmap) {
            InLikeVo likeVo = new InLikeVo();
            String key = String.valueOf(obj.getKey());
            String[] str = key.split("-");
            Long id = Long.valueOf(str[1]);
            likeVo.setTime(DateUtils.stringToDate(String.valueOf(obj.getValue()), "yyyy-MM-dd HH:mm:ss"));
            Object oUser = redisTemplate.opsForHash().get(RedisKeys.INUSER, id);
            InUser user = (InUser) oUser;
            if (null != user) {
                likeVo.setNick(user.getuNick());
                likeVo.setPhoto(user.getuPhoto());
                getTitle(id, str[3], likeVo);
                newsLike.add(likeVo);
            }
        }
        return new PageUtils(newsLike, cmap.size(), size, page);
    }

    @Override
    public PageUtils active(Map<String, Object> map) {
        LambdaQueryWrapper<InActivity> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("type") && StringUtil.isNotBlank(map.get("type"))) {
            Integer type = (Integer) map.get("type");
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
    public PageUtils fansWriter(Map<String, Object> map) {
        //作者需要认证通过  #uId-#status-#fId
        Long uId = Long.valueOf((String) map.get("uId"));
        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer page = StringUtil.isBlank(map.get("currPage")) ? 0 : Integer.parseInt(String.valueOf(map.get("currPage")));
        Integer bindex = page * size;
        //根据uId查询用户关注的目标
        List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.FOCUS, uId + "-2-*");
        ArrayList<InUserDTO> newsFocus = null;
        //关注目标信息
        if (null != cmap && cmap.size() > 0) {
            newsFocus = new ArrayList<>();
            if (bindex + size < cmap.size()) {
                cmap = cmap.subList(bindex, bindex + size);
            }
        } else {
            return new PageUtils(newsFocus, 0, size, page);
        }
        for (Map.Entry<Object, Object> obj : cmap) {
            InUserDTO userDTO = new InUserDTO();
            String[] str = String.valueOf(obj.getKey()).split("-");
            Long id = Long.valueOf(str[2]);
            Object oUser = redisTemplate.opsForHash().get(RedisKeys.INUSER, id);
            InUser user = (InUser) oUser;
            if (null != user) {
                userDTO.setuNick(user.getuNick());
                userDTO.setuPhoto(user.getuPhoto());
                userDTO.setuIntro(user.getuIntro());
                newsFocus.add(userDTO);
            }
        }
        return new PageUtils(newsFocus, cmap.size(), size, page);
    }

    @Override
    public PageUtils fansPerson(Map<String, Object> map) {
        Integer page = StringUtil.isBlank(map.get("currPage")) ? 0 : Integer.parseInt(String.valueOf(map.get("currPage")));
        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer bindex = page * size;
        Long uId = Long.valueOf((String) map.get("uId"));
        //根据uId查询用户关注的目标  #uId-#type-#fId
        ArrayList<InUserDTO> newsFocus = null;
        List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.FOCUS, uId + "-*-*");
        //关注目标信息
        List<Map.Entry<Object, Object>> slist = null;
        if (bindex + size < cmap.size()) {
            slist = cmap.subList(bindex, bindex + size);
        } else {
            slist = cmap;
        }
        if (slist.size() > 0) {
            newsFocus = new ArrayList<>();
        }
        for (Map.Entry<Object, Object> obj : slist) {
            InUserDTO userDTO = new InUserDTO();
            String[] str = String.valueOf(obj.getKey()).split("-");
            Long id = Long.valueOf(str[2]);
            Object oUser = redisTemplate.opsForHash().get(RedisKeys.INUSER, id);
            InUser user = (InUser) oUser;
            if (null != user) {
                userDTO.setuPhoto(user.getuPhoto());
                userDTO.setuIntro(user.getuIntro());
                userDTO.setuNick(user.getuNick());
                newsFocus.add(userDTO);
            }
        }
        return new PageUtils(newsFocus, cmap.size(), size, page);
    }

    @Override
    public PageUtils follower(Map<String, Object> map) {
        Long uId = Long.valueOf((String) map.get("uId"));
        Integer page = StringUtil.isBlank(map.get("currPage")) ? 0 : Integer.parseInt(String.valueOf(map.get("currPage")));
        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer bindex = page * size;
        //以uId为目标查询粉丝  #id-#uid-#tId-#type
        ArrayList<InUserDTO> newsFans = null;
        List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.FOCUS, "*-*-" + uId);
        //关注目标信息
        List<Map.Entry<Object, Object>> slist = null;
        if (bindex + size < cmap.size()) {
            slist = cmap.subList(bindex, bindex + size);
        } else {
            slist = cmap;
        }
        if (slist.size() > 0) {
            newsFans = new ArrayList<>();
        }
        for (Map.Entry<Object, Object> obj : slist) {
            InUserDTO userDTO = new InUserDTO();
            String[] str = String.valueOf(obj.getKey()).split("-");
            Long id = Long.valueOf(str[0]);
            Object oUser = redisTemplate.opsForHash().get(RedisKeys.INUSER, id);
            InUser user = (InUser) oUser;
            if (null != user) {
                userDTO.setuPhoto(user.getuPhoto());
                userDTO.setuIntro(user.getuIntro());
                userDTO.setuNick(user.getuNick());
                userDTO.setuFans(user.getuFans());
            }
            //TODO  是否互相关注
            newsFans.add(userDTO);
        }
        return new PageUtils(newsFans, cmap.size(), size, page);
    }

    @Override
    public PageUtils favorite(Map<String, Object> map) {
        if (null != map.get("type") && StringUtil.isNotBlank(map.get("type"))) {
            Integer type = (Integer) map.get("type");
            Long uId = Long.valueOf((String) map.get("uId"));
            Integer page = StringUtil.isBlank(map.get("currPage")) ? 0 : Integer.parseInt(String.valueOf(map.get("currPage")));
            Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
            Integer bindex = page * size;
            //查询用户的收藏   #id-#tid-#type-#uid
            ArrayList<CollectDTO> collects = null;
            List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.COLLECT, "*-*-" + type + "-" + uId);
            List<Map.Entry<Object, Object>> slist = null;
            if (bindex + size < cmap.size()) {
                slist = cmap.subList(bindex, bindex + size);
            } else {
                slist = cmap;
            }
            if (slist.size() > 0) {
                collects = new ArrayList<>();
            }
            switch (type) {
                case 0:     //文章
                    for (Map.Entry<Object, Object> obj : slist) {
                        CollectDTO dto = new CollectDTO();
                        String[] str = String.valueOf(obj.getKey()).split("-");
                        Long id = Long.valueOf(str[0]);
                        InActivity activity = activityService.getById(id);//目标信息
                        dto.setActivity(activity);
                        collects.add(dto);
                    }
                    break;
                case 1:     //帖子
                    for (Map.Entry<Object, Object> obj : slist) {
                        CollectDTO dto = new CollectDTO();
                        String[] str = String.valueOf(obj.getKey()).split("-");
                        Long id = Long.valueOf(str[0]);
                        InCardBase cardBase = baseService.getById(id);//目标信息
                        dto.setCardBase(cardBase);
                        collects.add(dto);
                    }
                    break;
                case 2:     //活动
                    for (Map.Entry<Object, Object> obj : slist) {
                        CollectDTO dto = new CollectDTO();
                        String[] str = String.valueOf(obj.getKey()).split("-");
                        Long id = Long.valueOf(str[0]);
                        InArticle article = articleService.getById(id);//目标信息
                        dto.setArticle(article);
                        collects.add(dto);
                    }
                    break;
            }
            return new PageUtils(collects, cmap.size(), size, page);
        }
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
