package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guansuo.common.DateUtils;
import com.guansuo.common.JsonUtil;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.annotation.HashCacheable;
import io.information.common.utils.*;
import io.information.modules.app.dao.InUserDao;
import io.information.modules.app.dto.CollectDTO;
import io.information.modules.app.dto.InNodeDTO;
import io.information.modules.app.dto.InUserDTO;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.*;
import io.information.modules.app.vo.InLikeVo;
import io.information.modules.app.vo.UserBoolVo;
import org.apache.shiro.crypto.hash.Sha256Hash;
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
    @Autowired
    private IInNodeService nodeService;


    @Override
    public boolean change(String uPwd, String newPwd, InUser user) {
        if (!uPwd.equals(new Sha256Hash(user.getuPwd(), user.getuSalt()).toHex())) {
            return false;
        } else {
            user.setuPwd(newPwd);
            this.updateById(user);
            return true;
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
    //status为0则不是人物
    @HashCacheable(key = RedisKeys.FOCUS, keyField = "#uId-#status-#fId")
    public String focus(Long uId, Long fId, Integer status) {
        this.baseMapper.addFans(uId);
        this.baseMapper.addFocus(fId);
        return String.valueOf(fId);
    }

    @Override
    public List<Long> searchFocusId(Long uId) {
        ArrayList<Long> list = new ArrayList<>();
        List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.FOCUS, uId + "-*-*");
        if (!cmap.isEmpty()) {
            for (Map.Entry<Object, Object> entry : cmap) {
                String key = String.valueOf(entry.getKey());
                String[] split = key.split("-");
                Long value = Long.valueOf(split[2]);
                list.add(value);
            }
        }
        return list;
    }

    @Override
    public PageUtils fansNode(Map<String, Object> map) {
        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer page = StringUtil.isBlank(map.get("currPage")) ? 0 : Integer.parseInt(String.valueOf(map.get("currPage")));
        Integer bindex = page * size;
        //模糊查询出某类的key  #uId-#type-#noId
        Long uId = Long.parseLong(String.valueOf(map.get("uId")));
        List<Map.Entry<Object, Object>> nmap = redisUtils.hfget(RedisKeys.NODES, uId + "-*-*");
        ArrayList<InNodeDTO> nodeFocus = null;
        //关注目标信息
        if (null != nmap && nmap.size() > 0) {
            nodeFocus = new ArrayList<>();
            if (bindex + size < nmap.size()) {
                nmap = nmap.subList(bindex, bindex + size);
            }
        } else {
            return new PageUtils(nodeFocus, 0, size, page);
        }
        for (Map.Entry<Object, Object> obj : nmap) {
            String[] str = String.valueOf(obj.getKey()).split("-");
            Long id = Long.valueOf(str[2]);  //节点ID
            InNode node = nodeService.getById(id);
            if (null != node) {
                InNodeDTO dto = BeanHelper.copyProperties(node, InNodeDTO.class);
                nodeFocus.add(dto);
            }
        }
        return new PageUtils(nodeFocus, nmap.size(), size, page);

    }

    @Override
    //目标用户ID   目标ID   目标类型   用户ID
    public UserBoolVo userNumber(Long uId, Long tId, Integer type, InUser user) {
        if (null != uId) {
            //用户信息
            InUser inUser = this.getById(uId);
            UserBoolVo boolVo = BeanHelper.copyProperties(inUser, UserBoolVo.class);
            if (null != boolVo) {
                //是否收藏
                Boolean isCollect = redisUtils.hashHasKey(RedisKeys.COLLECT, tId + "-" + user.getuId() + "-" + uId + "-*");
                boolVo.setCollect(isCollect);
                //收藏数量
                List<Map.Entry<Object, Object>> cNumber = redisUtils.hfget(RedisKeys.COLLECT, "*-*-" + uId + "-*");
                boolVo.setCollectNumber(cNumber == null ? 0 : cNumber.size());
                //是否点赞
                Boolean isLike = redisUtils.hashHasKey(RedisKeys.LIKE, tId + "-" + user.getuId() + "-" + uId + "-*");
                boolVo.setLike(isLike);
                //点赞数量
                List<Map.Entry<Object, Object>> lNumber = redisUtils.hfget(RedisKeys.LIKE, "*-*-" + uId + "-*");
                boolVo.setLikeNumber(lNumber == null ? 0 : lNumber.size());
                //评论数量
                int count = commonReplyService.count(new LambdaQueryWrapper<InCommonReply>().eq(InCommonReply::gettId, uId));
                boolVo.setReplyNumber(count);
            }
            return boolVo;
        }
        return null;
    }

    @Override
    public Boolean isFocus(Long tId, Long uId) {
        return redisUtils.hashHasKey(RedisKeys.FOCUS, uId + "-*-" + tId);
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
            Long uId = Long.parseLong(String.valueOf(map.get("uId")));
            //根据用户ID查询所有目标ID<根>或被评论ID找到回复用户的评论
            LambdaQueryWrapper<InCardBase> cardQuery = new LambdaQueryWrapper<>();
            cardQuery.eq(InCardBase::getuId, uId);
            List<InCardBase> baseList = baseService.list(cardQuery);
            if (null != baseList && !baseList.isEmpty()) {
                //获取用户帖子ID
                List<Long> cIds = baseList.stream().map(InCardBase::getcId).collect(Collectors.toList());
                //获取回帖信息
                if (null == cIds || cIds.size() < 1) {
                    return null;
                }
                return commonReplyService.reply(map, cIds);
            }
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
            int type = Integer.parseInt(String.valueOf(map.get("type")));
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
            queryWrapper.eq(InActivity::getuId, Long.parseLong(String.valueOf(map.get("uId"))));
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
        Long uId = Long.parseLong(String.valueOf(map.get("uId")));
        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer page = StringUtil.isBlank(map.get("currPage")) ? 0 : Integer.parseInt(String.valueOf(map.get("currPage")));
        Integer bindex = page * size;
        //根据uId查询用户关注的目标
        List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.FOCUS, uId + "-*-*");
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
        Long uId = Long.parseLong(String.valueOf(map.get("uId")));
        //根据uId查询用户关注的目标  #uId-#type-#fId
        ArrayList<InUserDTO> newsFocus = null;
        List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.FOCUS, uId + "-1-*");
        List<Map.Entry<Object, Object>> cmap2 = redisUtils.hfget(RedisKeys.FOCUS, uId + "-2-*");
        if (null != cmap) {
            cmap.addAll(cmap2);
        } else {
            cmap = cmap2;
        }
        List<Map.Entry<Object, Object>> slist = null;
        //关注目标信息
        if (null != cmap && cmap.size() > 0) {
            newsFocus = new ArrayList<>();
            if (bindex + size < cmap.size()) {
                cmap = cmap.subList(bindex, bindex + size);
            }
        } else {
            return new PageUtils(newsFocus, 0, size, page);
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
        Long uId = Long.parseLong(String.valueOf(map.get("uId")));
        Integer page = StringUtil.isBlank(map.get("currPage")) ? 0 : Integer.parseInt(String.valueOf(map.get("currPage")));
        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer bindex = page * size;
        //以uId为目标查询粉丝  #uId-#type-#fId
        ArrayList<InUserDTO> newsFans = null;
        List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.FOCUS, "*-*-" + uId);
        List<Map.Entry<Object, Object>> slist = null;
        //关注目标信息
        if (null != cmap && cmap.size() > 0) {
            newsFans = new ArrayList<>();
            if (bindex + size < cmap.size()) {
                cmap = cmap.subList(bindex, bindex + size);
            }
        } else {
            return new PageUtils(newsFans, 0, size, page);
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
//            //根据用户ID找到用户所有关注的用户   匹配关注者的ID  是否关注
//            ArrayList<Long> fIds = new ArrayList<>();
//            List<Map.Entry<Object, Object>> fmap = redisUtils.hfget(RedisKeys.FOCUS, uId + "-*-*");
//            for (Map.Entry<Object, Object> entry : fmap) {
//                String[] strings = String.valueOf(entry.getKey()).split("-");
//                Long fId = Long.valueOf(strings[2]);
//                fIds.add(fId);
//            }
//            userDTO.setuFIds(fIds);
//            newsFans.add(userDTO);
        }
        return new PageUtils(newsFans, cmap.size(), size, page);
    }

    @Override
    public PageUtils favorite(Map<String, Object> map) {
        if (null != map.get("type") && StringUtil.isNotBlank(map.get("type"))) {
            int type = Integer.parseInt(String.valueOf(map.get("type")));
            Long uId = Long.parseLong(String.valueOf(map.get("uId")));
            Integer page = StringUtil.isBlank(map.get("currPage")) ? 0 : Integer.parseInt(String.valueOf(map.get("currPage")));
            Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
            Integer bindex = page * size;
            //查询用户的收藏   #id-#uid-#tId-#type
            ArrayList<CollectDTO> collects = null;
            List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.COLLECT, "*-" + uId + "-*-" + type);
            List<Map.Entry<Object, Object>> slist = null;
            //关注目标信息
            if (null != cmap && cmap.size() > 0) {
                collects = new ArrayList<>();
                if (bindex + size < cmap.size()) {
                    cmap = cmap.subList(bindex, bindex + size);
                }
            } else {
                return new PageUtils(collects, 0, size, page);
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

}
