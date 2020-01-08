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
import io.information.modules.app.dao.InActivityDao;
import io.information.modules.app.dao.InArticleDao;
import io.information.modules.app.dao.InUserDao;
import io.information.modules.app.dto.CollectDTO;
import io.information.modules.app.dto.InNodeDTO;
import io.information.modules.app.dto.InUserDTO;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.*;
import io.information.modules.app.vo.InLikeVo;
import io.information.modules.app.vo.UserBoolVo;
import io.information.modules.app.vo.UserCardVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    IInCommonReplyService commonReplyService;
    @Autowired
    IInActivityService activityService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    IInArticleService articleService;
    @Autowired
    IInCardBaseService baseService;
    @Autowired
    IInNodeService nodeService;
    @Autowired
    InActivityDao activityDao;
    @Autowired
    IInDicService dicService;
    @Autowired
    InArticleDao articleDao;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    DicHelper dicHelper;

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
    public String focus(Long uId, Integer status, Long fId) {
        this.baseMapper.addFans(fId);
        this.baseMapper.addFocus(uId);
        return String.valueOf(status);
    }

    @Override
    public Boolean isFocus(Long tId, Long uId) {
        Object obj = redisUtils.hfget(RedisKeys.FOCUS, uId + "-*-" + tId);
        return null != obj && ((List) obj).size() > 0;
//        JSONArray array = JsonUtil.parseJSONArray(JsonUtil.toJSONString(obj));
//        String flag = array.getJSONObject(0).getString("value");
//        return "true".equalsIgnoreCase(flag);
    }

    @Override
    public PageUtils<InCommonReply> commentUser(Map<String, Object> map) {
        LambdaQueryWrapper<InCommonReply> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            long uId = Long.parseLong(String.valueOf(map.get("uId")));
            //查询用户发布的所有文章和活动ID
            ArrayList<Long> list = new ArrayList<>();
            List<Long> actIds = activityDao.allActId(uId);
            List<Long> aIds = articleDao.allAId(uId);
            list.addAll(actIds);
            list.addAll(aIds);
            if (null != list && !list.isEmpty()) {
                queryWrapper.ne(InCommonReply::gettType, 3).in(InCommonReply::gettId, list);
                IPage<InCommonReply> page = commonReplyService.page(
                        new Query<InCommonReply>().getPage(map),
                        queryWrapper
                );
                for (InCommonReply record : page.getRecords()) {
                    InUser user = this.getById(record.getcId());
                    record.setcName(user.getuName());
                    record.setcPhoto(user.getuPhoto());
                    record.setCrSimpleTime(DateUtils.getSimpleTime(record.getCrTime()));
                    String typeNmae = "";
                    switch (record.gettType()) {
                        //0文章，1帖子，2活动，3用户
                        case 0:
                            typeNmae = "文章";
                            break;
                        case 1:
                            typeNmae = "帖子";
                            break;
                        case 2:
                            typeNmae = "活动";
                            break;
                        case 3:
                            typeNmae = "用户";
                            break;
                    }
                    record.settTypeName(typeNmae);
                }
                return new PageUtils<>(page);
            }
            return null;
        }
        return null;
    }

    @Override
    public PageUtils fansNode(Map<String, Object> map) {
        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer page = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        if (page == 0) {
            page = 1;
        }
        Integer bindex = (page - 1) * size;
        //模糊查询出某类的key  #uId-#type-#noId
        Long uId = Long.parseLong(String.valueOf(map.get("uId")));
        List<Map.Entry<Object, Object>> nmap = redisUtils.hfget(RedisKeys.NODES, uId + "-*-*");
        int nSize = 0;
        ArrayList<InNodeDTO> nodeFocus = null;
        //关注目标信息
        if (null != nmap && nmap.size() > 0) {
            nSize = nmap.size();
            nodeFocus = new ArrayList<>();
            if (bindex + size < nmap.size()) {
                nmap = nmap.subList(bindex, bindex + size);
            } else if (bindex < nmap.size()) {
                nmap = nmap.subList(bindex, nmap.size());
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
            } else {
                InNodeDTO nodeDTO = new InNodeDTO();
                nodeDTO.setNoName("节点不存在");
                nodeDTO.setNoBrief("节点已经不能使用了");
                nodeFocus.add(nodeDTO);
            }
        }
        return new PageUtils(nodeFocus, nSize, size, page);

    }

    @Override
    //目标用户ID   目标ID   目标类型   用户ID
    public UserBoolVo userNumber(Long uId, Long tId, Integer type, InUser user) {
        if (null != uId) {
            //用户信息
            InUser inUser = this.getById(uId);
            if (inUser == null) {
                return null;
            }
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
//                boolVo.setLikeNumber(lNumber == null ? 0 : lNumber.size());
                //评论数量
                int count = commonReplyService.count(new LambdaQueryWrapper<InCommonReply>().eq(InCommonReply::gettId, uId));
                boolVo.setReplyNumber(count);
                return boolVo;
            }
        }
        return null;
    }


    @Override
    public PageUtils<InCommonReply> comment(Map<String, Object> params) {
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
        Integer pageSize = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer currPage = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        LambdaQueryWrapper<InCardBase> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            long uId = Long.parseLong(String.valueOf(map.get("uId")));
            queryWrapper.eq(InCardBase::getuId, uId);
            IPage<InCardBase> page = baseService.page(
                    new Query<InCardBase>().getPage(map),
                    queryWrapper
            );
            ArrayList<UserCardVo> list = new ArrayList<>();
            for (InCardBase base : page.getRecords()) {
                if (null != base.getuId() && StringUtil.isNotBlank(base.getuId())) {
                    InUser user = this.getById(base.getuId());
                    UserCardVo cardVo = BeanHelper.copyProperties(user, UserCardVo.class);
                    if (null != cardVo) {
                        String simpleTime = DateUtils.getSimpleTime(base.getcCreateTime() == null ? new Date() : base.getcCreateTime());
                        cardVo.setTime(simpleTime);
                        InDic dic = dicService.getById(base.getcNodeCategory() == null ? 0 : base.getcNodeCategory());
                        if (null != dic) {
                            cardVo.setType(dic.getdName());
                            cardVo.setcId(base.getcId());
                            cardVo.setcTitle(base.getcTitle());
                            cardVo.setcId(base.getcId());
                            Object number = redisTemplate.opsForValue().get(RedisKeys.CARDBROWSE + cardVo.getcId());
                            if (null != number) {
                                long readNumber = Long.parseLong(String.valueOf(number));
                                cardVo.setReadNumber(readNumber);
                            }
                            cardVo.setReplyNumber(base.getcCritic());
                        }
                        list.add(cardVo);
                    }
                }
            }
            int total = (int) page.getTotal();
            return new PageUtils(list, total, pageSize, currPage);
        }
        return null;
    }

    @Override
    public PageUtils<InCommonReply> reply(Map<String, Object> map) {
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

    private void getTitle(String id, String type, InLikeVo likeVo) {
        if (NewsEnum.点赞_文章.getCode().equals(type)) {
            if (null != articleService.getById(id)) {
                likeVo.setData(articleService.getById(id).getaTitle());
                likeVo.setType(NewsEnum.点赞_文章.getName());
            }
        }
        if (NewsEnum.点赞_帖子.getCode().equals(type)) {
            if (null != baseService.getById(id)) {
                likeVo.setData(baseService.getById(id).getcTitle());
                likeVo.setType(NewsEnum.点赞_帖子.getName());
            }
        }
        if (NewsEnum.点赞_活动.getCode().equals(type)) {
            if (null != activityService.getById(id)) {
                likeVo.setData(activityService.getById(id).getActTitle());
                likeVo.setType(NewsEnum.点赞_活动.getName());
            }
        }
    }

    @Override
    public PageUtils<InLikeVo> like(Map<String, Object> map, Long uId) {
        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer page = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        if (page == 0) {
            page = 1;
        }
        Integer bindex = (page - 1) * size;
        //模糊查询出某类的key  #id-#uid-#tId-#type
        List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.LIKE, "*-*-" + uId + "-*");
        int cSize = cmap == null ? 0 : cmap.size();
        ArrayList<InLikeVo> newsLike = null;
        if (null != cmap && cmap.size() > 0) {
            newsLike = new ArrayList<>();
            if (bindex + size < cmap.size()) {
                cmap = cmap.subList(bindex, bindex + size);
            } else if (bindex < cmap.size()) {
                cmap = cmap.subList(bindex, cmap.size());
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
            InUser user = this.getById(id);
//            Object oUser = redisTemplate.opsForHash().get(RedisKeys.INUSER, String.valueOf(id));
//            InUser user = (InUser) oUser;
            if (null != user) {
                likeVo.setNick(user.getuNick());
                likeVo.setPhoto(user.getuPhoto());
            } else {
                likeVo.setNick("用户已不存在");
            }
            getTitle(str[0], str[3], likeVo);
            newsLike.add(likeVo);
        }
        return new PageUtils(newsLike, cSize, size, page);
    }


    @Override
    public PageUtils<InActivity> active(Map<String, Object> map) {
        LambdaQueryWrapper<InActivity> queryWrapper = new LambdaQueryWrapper<>();
        if (null != map.get("type") && StringUtil.isNotBlank(map.get("type"))) {
            int type = Integer.parseInt(String.valueOf(map.get("type")));
            Date currTime = new Date();
            switch (type) {
                case 0:  //获取未开始活动    开始时间 gt> 当前时间
                    queryWrapper.gt(InActivity::getActStartTime, currTime);
                    break;
                case 1:  //获取进行中活动    开始时间 it< 当前时间  &&  结束时间 gt> 当前时间
                    queryWrapper.lt(InActivity::getActStartTime, currTime)
                            .gt(InActivity::getActCloseTime, currTime);
                    break;
                case 2:  //获取已结束活动    结束时间 it< 当前时间
                    queryWrapper.lt(InActivity::getActCloseTime, currTime);
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
        for (InActivity act : page.getRecords()) {
//            act.setExist(0);
            act.setuName(String.valueOf(map.get("uName") == null ? "" : map.get("uName")));
            act.setaSimpleTime(DateUtils.getSimpleTime(act.getActCreateTime()));
        }
        return new PageUtils(page);
    }

    @Override
    public PageUtils fansWriter(Map<String, Object> map) {
        //#uId-#status-#fId
        Long uId = Long.parseLong(String.valueOf(map.get("uId")));
        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        Integer page = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        if (page == 0) {
            page = 1;
        }
        Integer bindex = (page - 1) * size;
        //根据uId查询用户关注的目标
        List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.FOCUS, uId + "-*-*");
        int cSize = cmap == null ? 0 : cmap.size();
        ArrayList<InUserDTO> newsFocus = null;
        //关注目标信息
        if (null != cmap && cmap.size() > 0) {
            newsFocus = new ArrayList<>();
            if (bindex + size < cmap.size()) {
                cmap = cmap.subList(bindex, bindex + size);
            } else if (bindex < cmap.size()) {
                cmap = cmap.subList(bindex, cmap.size());
            }
        } else {
            return new PageUtils(newsFocus, 0, size, page);
        }
        for (Map.Entry<Object, Object> obj : cmap) {
            InUserDTO userDTO = new InUserDTO();
            String[] str = String.valueOf(obj.getKey()).split("-");
            Long id = Long.valueOf(str[2]);
//            Object oUser = redisTemplate.opsForHash().get(RedisKeys.INUSER, String.valueOf(id));
//            InUser user = (InUser) oUser;
            InUser user = this.getById(id);
            if (null != user) {
                userDTO.setuNick(user.getuNick());
                userDTO.setuPhoto(user.getuPhoto());
                userDTO.setuIntro(user.getuIntro());
                newsFocus.add(userDTO);
            } else {
                InUserDTO dto = new InUserDTO();
                dto.setuId(id);
                dto.setuNick("用户已不存在");
                newsFocus.add(dto);
            }
        }
        return new PageUtils(newsFocus, cSize, size, page);
    }

    @Override
    public PageUtils fansPerson(Map<String, Object> map) {
        Integer page = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        if (page == 0) {
            page = 1;
        }
        Integer bindex = (page - 1) * size;
        Long uId = Long.parseLong(String.valueOf(map.get("uId")));
        //根据uId查询用户关注的目标  #uId-#type-#fId
        List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.FOCUS, uId + "-1-*");
        List<Map.Entry<Object, Object>> cmap2 = redisUtils.hfget(RedisKeys.FOCUS, uId + "-2-*");
        int cSize = cmap == null ? 0 : cmap.size();
        int c2Size = cmap2 == null ? 0 : cmap2.size();
        int sSize = cSize + c2Size;
        ArrayList<InUserDTO> newsFocus = null;
        if (null != cmap && cmap2 != null) {
            cmap.addAll(cmap2);
        } else if (cmap2 == null && cmap == null) {
            return null;
        } else if (cmap == null && cmap2 != null) {
            cmap = cmap2;
        }

        //关注目标信息
        if (null != cmap && cmap.size() > 0) {
            newsFocus = new ArrayList<>();
            if (bindex + size < cmap.size()) {
                cmap = cmap.subList(bindex, bindex + size);
            } else if (bindex < cmap.size()) {
                cmap = cmap.subList(bindex, cmap.size());
            }
        } else {
            return new PageUtils(newsFocus, 0, size, page);
        }
        for (Map.Entry<Object, Object> obj : cmap) {
            String[] str = String.valueOf(obj.getKey()).split("-");
            Long id = Long.valueOf(str[2]);
            InUser user = this.getById(id);
            if (null != user) {
                InUserDTO userDTO = BeanHelper.copyProperties(user, InUserDTO.class);
                if (null != userDTO) {
                    newsFocus.add(userDTO);
                }
            } else {
                InUserDTO dto = new InUserDTO();
                dto.setuId(id);
                dto.setuNick("用户已不存在");
                newsFocus.add(dto);
            }
        }
        return new PageUtils(newsFocus, sSize, size, page);
    }


    @Override
    public PageUtils follower(Map<String, Object> map) {
        Long uId = Long.parseLong(String.valueOf(map.get("uId")));
        Integer page = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
        Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
        if (page == 0) {
            page = 1;
        }
        Integer bindex = (page - 1) * size;
        //以uId为目标查询粉丝  #uId-#status-#fId
        List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.FOCUS, "*-*-" + uId);
        int cSize = cmap == null ? 0 : cmap.size();
        ArrayList<InUserDTO> newsFans = null;

        //关注目标信息
        if (null != cmap && cmap.size() > 0) {
            newsFans = new ArrayList<>();
            if (bindex + size < cmap.size()) {
                cmap = cmap.subList(bindex, bindex + size);
            } else if (bindex < cmap.size()) {
                cmap = cmap.subList(bindex, cmap.size());
            }
        } else {
            return new PageUtils(newsFans, 0, size, page);
        }
        for (Map.Entry<Object, Object> obj : cmap) {
            String[] str = String.valueOf(obj.getKey()).split("-");
            Long id = Long.valueOf(str[0]);
            InUser user = this.getById(id);
            if (null != user) {
                InUserDTO userDTO = BeanHelper.copyProperties(user, InUserDTO.class);
                if (null != userDTO) {
                    newsFans.add(userDTO);
                }
            } else {
                InUserDTO dto = new InUserDTO();
                dto.setuId(id);
                dto.setuNick("用户已不存在");
                newsFans.add(dto);
            }
        }
        return new PageUtils(newsFans, cSize, size, page);
    }


    @Override
    public PageUtils favorite(Map<String, Object> map) {
        if (null != map.get("type") && StringUtil.isNotBlank(map.get("type"))) {
            int type = Integer.parseInt(String.valueOf(map.get("type")));
            Long uId = Long.parseLong(String.valueOf(map.get("uId")));
            Integer page = StringUtil.isBlank(map.get("currPage")) ? 1 : Integer.parseInt(String.valueOf(map.get("currPage")));
            Integer size = StringUtil.isBlank(map.get("pageSize")) ? 10 : Integer.parseInt(String.valueOf(map.get("pageSize")));
            if (page == 0) {
                page = 1;
            }
            Integer bindex = (page - 1) * size;
//            Integer bindex = page * size;
            //查询用户的收藏   #id-#uid-#tId-#type
            ArrayList<CollectDTO> collects = null;
            List<Map.Entry<Object, Object>> cmap = redisUtils.hfget(RedisKeys.COLLECT, "*-" + uId + "-*-" + type);
            int cSize = cmap == null ? 0 : cmap.size();
//            List<Map.Entry<Object, Object>> slist = null;
            //关注目标信息
            if (null != cmap && cmap.size() > 0) {
                collects = new ArrayList<>();
                if (bindex + size < cmap.size()) {
                    cmap = cmap.subList(bindex, bindex + size);
                } else if (bindex < cmap.size()) {
                    cmap = cmap.subList(bindex, cmap.size());
                }
            } else {
                return new PageUtils(collects, 0, size, page);
            }
            switch (type) {
                case 0:     //文章
                    for (Map.Entry<Object, Object> obj : cmap) {
                        CollectDTO dto = new CollectDTO();
                        String[] str = String.valueOf(obj.getKey()).split("-");
                        Long id = Long.valueOf(str[0]);
                        InArticle article = articleService.getById(id);//目标信息
                        if (null != article) {
//                            article.setExist(0);
                            Object number = redisTemplate.opsForValue().get(RedisKeys.ABROWSE + article.getaId());
                            if (null != number) {
                                long readNumber = Long.parseLong(String.valueOf(number));
                                article.setaReadNumber(readNumber);
                            }
                            if (null == article.getaCreateTime()) {
                                article.setaSimpleTime(DateUtils.getSimpleTime(new Date()));
                            } else {
                                article.setaSimpleTime(DateUtils.getSimpleTime(article.getaCreateTime()));
                            }
                            if (null != article.getuId()) {
                                InUser user = this.getById(article.getuId());
                                if (null != user) {
                                    article.setuName(user.getuName() == null ? "" : user.getuName());
                                }
                            }
                            dto.setArticle(article);
                            collects.add(dto);
                        } else {
                            InArticle inArticle = new InArticle();
                            inArticle.setExist(1);
                            inArticle.setaId(id);
                            inArticle.setuId(null);
                            inArticle.setaTitle("该文章已被作者删除");
                            dto.setArticle(inArticle);
                            collects.add(dto);
                        }
                    }
                    break;
                case 1:     //帖子
                    for (Map.Entry<Object, Object> obj : cmap) {
                        CollectDTO dto = new CollectDTO();
                        String[] str = String.valueOf(obj.getKey()).split("-");
                        Long id = Long.valueOf(str[0]);
                        InCardBase cardBase = baseService.getById(id);//目标信息
                        if (null != cardBase) {
                            Object number = redisTemplate.opsForValue().get(RedisKeys.CARDBROWSE + cardBase.getcId());
                            if (null != number) {
                                long readNumber = Long.parseLong(String.valueOf(number));
                                cardBase.setcReadNumber(readNumber);
                            }
                            if (null != cardBase.getcNodeCategory()) {
                                cardBase.setcNodeCategoryValue(dicHelper.getDicName(cardBase.getcNodeCategory().longValue()));
                            }
                            if (null == cardBase.getcCreateTime()) {
                                cardBase.setcSimpleTime(DateUtils.getSimpleTime(new Date()));
                            } else {
                                cardBase.setcSimpleTime(DateUtils.getSimpleTime(cardBase.getcCreateTime()));
                            }
                            if (null != cardBase.getuId()) {
                                InUser user = this.getById(cardBase.getuId());
                                if (null != user) {
                                    cardBase.setuNick(user.getuNick() == null ? "" : user.getuNick());
                                    cardBase.setuPhoto(user.getuPhoto());
                                }
                            }
                            dto.setCardBase(cardBase);
                            collects.add(dto);
                        } else {
                            InCardBase inCardBase = new InCardBase();
                            inCardBase.setcId(id);
                            inCardBase.setuId(null);
                            inCardBase.setcTitle("该帖子已被作者删除");
                            dto.setCardBase(inCardBase);
                            collects.add(dto);
                        }
                    }
                    break;
                case 2:     //活动
                    for (Map.Entry<Object, Object> obj : cmap) {
                        CollectDTO dto = new CollectDTO();
                        String[] str = String.valueOf(obj.getKey()).split("-");
                        Long id = Long.valueOf(str[0]);
                        InActivity activity = activityService.getById(id);//目标信息
                        if (null != activity) {
//                            activity.setExist(0);
                            if (null == activity.getActCreateTime()) {
                                activity.setaSimpleTime(DateUtils.getSimpleTime(new Date()));
                            } else {
                                activity.setaSimpleTime(DateUtils.getSimpleTime(activity.getActCreateTime()));
                            }
                            if (null != activity.getuId()) {
                                InUser user = this.getById(activity.getuId());
                                if (null != user) {
                                    activity.setuName(user.getuName() == null ? "" : user.getuName());
                                }
                            }
                            dto.setActivity(activity);
                            collects.add(dto);
                        } else {
                            InActivity inActivity = new InActivity();
                            inActivity.setExist(1);
                            inActivity.setActId(id);
                            inActivity.setuId(null);
                            inActivity.setActTitle("该活动已被作者删除");
                            dto.setActivity(inActivity);
                            collects.add(dto);
                        }
                    }
                    break;
            }
            return new PageUtils(collects, cSize, size, page);
        }
        return null;
    }


    @Override
    public List<InUser> all() {
        return this.baseMapper.all();
    }
}
