package io.information.modules.app.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.elasticsearch.entity.EsUserEntity;
import io.information.common.utils.*;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.dao.InActivityDao;
import io.information.modules.app.dao.InArticleDao;
import io.information.modules.app.dao.InCardBaseDao;
import io.information.modules.app.dto.IdentifyCompanyDTO;
import io.information.modules.app.dto.IdentifyPersonalDTO;
import io.information.modules.app.dto.RedactDataDTO;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.*;
import io.information.modules.app.vo.InLikeVo;
import io.information.modules.app.vo.UserBoolVo;
import io.information.modules.app.vo.UserCardVo;
import io.information.modules.sys.controller.AbstractController;
import io.mq.utils.Constants;
import io.swagger.annotations.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 资讯用户表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/user")
@Api(value = "/app/user", tags = "APP资讯用户接口")
public class InUserController extends AbstractController {
    @Autowired
    private IInCommonReplyService replyService;
    @Autowired
    private IInActivityService activityService;
    @Autowired
    private InArticleDao articleDao;
    @Autowired
    private IInArticleService articleService;
    @Autowired
    private InActivityDao activityDao;
    @Autowired
    private IInMessageService messageService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IInUserService userService;
    @Autowired
    private IInCardService cardService;
    @Autowired
    private InCardBaseDao cardBaseDao;
    @Autowired
    private IInCardBaseService baseService;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 修改密码
     */
    @Login
    @PutMapping("/change")
    @ApiOperation(value = "修改密码", httpMethod = "PUT", notes = "旧密码[uPwd]  新密码[newPwd]")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "旧密码", name = "uPwd", required = true),
            @ApiImplicitParam(value = "新密码", name = "newPwd", required = true)
    })
    public R change(@RequestParam String uPwd, @RequestParam String newPwd, @ApiIgnore @LoginUser InUser user) {
        if (!user.getuPwd().equals(new Sha256Hash(uPwd, user.getuSalt()).toHex())) {
            return R.error("旧密码不匹配");
        } else if (user.getuPwd().equals(new Sha256Hash(newPwd, user.getuSalt()).toHex())) {
            return R.error("新密码与旧密码不能相同");
        } else {
            user.setuPwd(new Sha256Hash(newPwd, user.getuSalt()).toHex());
            userService.updateById(user);
            return R.ok();
        }
    }


    /**
     * 查询用户信息
     */
    @Login
    @GetMapping("/info")
    @ApiOperation(value = "查询登录用户信息", httpMethod = "GET")
    public ResultUtil<InUser> loginInfo(@ApiIgnore @LoginUser InUser user) {
        if (null == user.getuPhoto() || user.getuPhoto().equals("")) {
            user.setuPhoto("http://guansuo.info/news/upload/20191231115456head.png");
        }
        return ResultUtil.ok(userService.getById(user.getuId()));
    }


    /**
     * 查询用户信息
     */
    @GetMapping("/info/{uId}")
    @ApiOperation(value = "查询用户信息", httpMethod = "GET")
    @ApiImplicitParam(value = "用户ID", name = "uId", required = true)
    public ResultUtil<InUser> info(@PathVariable("uId") Long uId) {
        InUser user = userService.getById(uId);
        if (null == user.getuPhoto() || user.getuPhoto().equals("")) {
            user.setuPhoto("http://guansuo.info/news/upload/20191231115456head.png");
        }
        return ResultUtil.ok(user);
    }


    /**
     * 上传用户封面
     */
    @Login
    @PostMapping("/cover")
    @ApiOperation(value = "用户上传封面", httpMethod = "POST")
    @ApiImplicitParam(value = "封面地址", name = "uCover", required = true)
    public ResultUtil cover(@RequestParam String uCover, @ApiIgnore @LoginUser InUser user) {
        user.setuCover(uCover);
        userService.updateById(user);
        return ResultUtil.ok();
    }


    /**
     * 个人认证
     */
    @Login
    @PostMapping("/identifyPersonal")
    @ApiOperation(value = "个人认证", httpMethod = "POST")
    public R identifyPersonal(@RequestBody IdentifyPersonalDTO identifyPersonalDTO, @ApiIgnore @LoginUser InUser user) {
        InUser u = DataUtils.copyData(identifyPersonalDTO, InUser.class);
        u.setuId(user.getuId());
        u.setuAuthType(0);
        u.setuAuthStatus(1);
        userService.updateById(u);
        InUser inUser = userService.getById(u.getuId());
        EsUserEntity esUser = BeanHelper.copyProperties(inUser, EsUserEntity.class);
        rabbitTemplate.convertAndSend(Constants.userExchange,
                Constants.user_Update_RouteKey, esUser);
        return R.ok();
    }


    /**
     * 企业认证
     */
    @Login
    @PostMapping("/identifyCompany")
    @ApiOperation(value = "企业认证", httpMethod = "POST")
    public R identifyCompany(@RequestBody IdentifyCompanyDTO identifyCompanyDTO, @ApiIgnore @LoginUser InUser user) {
        InUser u = DataUtils.copyData(identifyCompanyDTO, InUser.class);
        u.setuId(user.getuId());
        u.setuAuthType(2);
        u.setuAuthStatus(1);
        userService.updateById(u);
        InUser inUser = userService.getById(u.getuId());
        EsUserEntity esUser = BeanHelper.copyProperties(inUser, EsUserEntity.class);
        rabbitTemplate.convertAndSend(Constants.userExchange,
                Constants.user_Update_RouteKey, esUser);
        return R.ok();
    }


    /**
     * 媒体认证
     */
    @Login
    @PostMapping("/identifyMedia")
    @ApiOperation(value = "媒体认证", httpMethod = "POST")
    public R identifyMedia(@RequestBody IdentifyCompanyDTO identifyCompanyDTO, @ApiIgnore @LoginUser InUser user) {
        InUser u = DataUtils.copyData(identifyCompanyDTO, InUser.class);
        u.setuId(user.getuId());
        u.setuAuthType(1);
        u.setuAuthStatus(1);
        userService.updateById(u);
        InUser inUser = userService.getById(u.getuId());
        EsUserEntity esUser = BeanHelper.copyProperties(inUser, EsUserEntity.class);
        rabbitTemplate.convertAndSend(Constants.userExchange,
                Constants.user_Update_RouteKey, esUser);
        return R.ok();
    }


    /**
     * 编辑资料
     */
    @Login
    @PutMapping("/redactData")
    @ApiOperation(value = "编辑资料", httpMethod = "PUT")
    public R redactData(@RequestBody RedactDataDTO redactDataDTO, @ApiIgnore @LoginUser InUser user) {
        InUser u = new InUser();
        if (null != redactDataDTO) {
            u.setuId(user.getuId());
            u.setuNick(redactDataDTO.getuNick());
            u.setuIntro(redactDataDTO.getuIntro());
            if (null != redactDataDTO.getuPhoto() && !redactDataDTO.getuPhoto().isEmpty()) {
                u.setuPhoto(redactDataDTO.getuPhoto());
            }
            userService.updateById(u);
            InUser inUser = userService.getById(u.getuId());
            EsUserEntity esUser = BeanHelper.copyProperties(inUser, EsUserEntity.class);
            rabbitTemplate.convertAndSend(Constants.userExchange,
                    Constants.user_Update_RouteKey, esUser);
        }
        return R.ok();
    }


    /**
     * 关注用户 <作者，节点，人物>
     */
    @Login
    @PostMapping("/focus")
    @ApiOperation(value = "关注用户", httpMethod = "POST", notes = "被关注的用户id")
    @ApiImplicitParam(value = "用户id", name = "uId", required = true)
    public R focus(@RequestBody Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            long uId = Long.parseLong(String.valueOf(map.get("uId")));
            InUser inUser = userService.getById(uId);
            if (null != inUser) {
                if (uId == user.getuId()) {
                    return R.error("您不能关注自己");
                } else {
                    String key = user.getuId() + "-" + inUser.getuPotential() + "-" + uId;
                    Object o = redisUtils.hget(RedisKeys.FOCUS, key);
                    if (null != o) {
                        return R.error("已关注，请不要重复关注");
                    }
                    //用户ID，被关注用户身份，被关注用户ID
                    userService.focus(user.getuId(), inUser.getuPotential(), uId);
                    return R.ok();
                }
            }
            return R.error("用户已不存在");
        }
        return R.error("必要参数不能为空");
    }


    /**
     * 取消关注用户
     */
    @Login
    @PostMapping("/delFocus")
    @ApiOperation(value = "取消关注用户", httpMethod = "POST", notes = "关注的用户id")
    @ApiImplicitParam(value = "用户id", name = "uId", required = true)
    public ResultUtil delFocus(@RequestBody Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        //#uId-#status-#fId
        //用户ID，被关注用户身份，被关注用户ID
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            long uId = Long.parseLong(String.valueOf(map.get("uId")));
            InUser inUser = userService.getById(uId);
            if (null != inUser) {
                String key = user.getuId() + "-" + inUser.getuPotential() + "-" + uId;
                Long r = redisUtils.hremove(RedisKeys.FOCUS, key);
                if (r > 0) {
                    userService.removeFocus(user.getuId(), inUser.getuPotential(), uId);
                    return ResultUtil.ok();
                } else {
                    return ResultUtil.error("取消关注失败，请重试");
                }
            } else {
                String key = user.getuId() + "-*-" + uId;
                List<Map.Entry<Object, Object>> list = redisUtils.hfget(RedisKeys.FOCUS, key);
                Map.Entry<Object, Object> entry = list.get(0);
                Object entryKey = entry.getKey();
                Long r = redisUtils.hremove(RedisKeys.FOCUS, entryKey);
                if (r > 0) {
                    userService.delFocus(user.getuId());
                    return ResultUtil.ok();
                } else {
                    return ResultUtil.error("取消关注失败，请重试");
                }
            }
        }
        return ResultUtil.error("必要参数不能为空");
    }


    /**
     * 是否关注用户
     */
    @Login
    @GetMapping("/isFocus")
    @ApiOperation(value = "是否已关注用户", httpMethod = "GET", notes = "true：已关注  false：未关注")
    @ApiImplicitParam(value = "目标用户ID", name = "uId", required = true)
    public Boolean isFocus(@RequestParam Long uId, @ApiIgnore @LoginUser InUser user) {
        return userService.isFocus(uId, user.getuId());
    }


    /**
     * 用户 -- 相关数量
     */
    @GetMapping("/userNumber")
    @ApiOperation(value = "用户数据信息[点赞，收藏，评论]", httpMethod = "GET", response = UserBoolVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户ID", name = "uId", required = true),
            @ApiImplicitParam(value = "目标[文章，帖子，活动]ID", name = "tId", required = true),
            @ApiImplicitParam(value = "目标类型(0：文章 1：帖子 2：活动)", name = "type", required = true)
    })
    public ResultUtil<UserBoolVo> userNumber(@RequestParam Map<String, Object> map) {
        UserBoolVo boolVo = null;
        if (null != map.get("tId") && StringUtil.isNotBlank(map.get("tId"))) {
            long tId = Long.parseLong(String.valueOf(map.get("tId")));
            Integer type = StringUtil.isBlank(map.get("type")) ? 0 : Integer.parseInt(String.valueOf(map.get("type")));
            if (NewsEnum.点赞_文章.getCode().equals(String.valueOf(type))) {
                InArticle a = articleService.getById(tId);
                if (null == a) {
                    return ResultUtil.error("不存在此文章");
                }
                boolVo = new UserBoolVo();
                //目标用户ID
                boolVo.setuId(a.getuId());
                boolVo.setLikeNumber(a.getaLike());
                boolVo.setCollectNumber(a.getaCollect());
                boolVo.setReplyNumber(replyService.count(new LambdaQueryWrapper<InCommonReply>().eq(InCommonReply::gettId, tId)));
            }
            if (NewsEnum.点赞_帖子.getCode().equals(String.valueOf(type))) {
                InCardBase c = baseService.getById(tId);
                if (null == c) {
                    return ResultUtil.error("不存在此帖子");
                }
                boolVo = new UserBoolVo();
                boolVo.setuId(c.getuId());
                boolVo.setLikeNumber(c.getcLike());
                boolVo.setCollectNumber(c.getcCollect());
                boolVo.setReplyNumber(replyService.count(new LambdaQueryWrapper<InCommonReply>().eq(InCommonReply::gettId, tId)));
            }
            if (NewsEnum.点赞_活动.getCode().equals(String.valueOf(type))) {
                InActivity a = activityService.getById(tId);
                if (null == a) {
                    return ResultUtil.error("不存在此活动");
                }
                boolVo = new UserBoolVo();
                boolVo.setuId(a.getuId());
                boolVo.setLikeNumber(a.getActLike());
                boolVo.setCollectNumber(a.getActCollect());
                boolVo.setReplyNumber(replyService.count(new LambdaQueryWrapper<InCommonReply>().eq(InCommonReply::gettId, tId)));
            }
            if (null != boolVo && StringUtil.isNotBlank(boolVo.getuId())) {
                InUser u = userService.getById(boolVo.getuId());
                if (null != u) {
                    boolVo.setuNick(u.getuNick() == null ? u.getuName() : u.getuNick());
                    boolVo.setuPhoto(u.getuPhoto() == null || u.getuPhoto().equals("")
                            ? "http://guansuo.info/news/upload/20191231115456head.png" : u.getuPhoto());
                } else {
                    boolVo.setuNick("用户已不存在");
                    boolVo.setuPhoto("http://guansuo.info/news/upload/20191231115456head.png");
                }
                if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
                    long uId = Long.parseLong(String.valueOf(map.get("uId")));
                    //#id-#uid-#tId-#type  ID   用户ID  目标用户ID  类型
                    boolVo.setLike(redisUtils.hashHasKey(RedisKeys.LIKE, tId + "-" + uId + "-" + boolVo.getuId() + "-" + type));
                    boolVo.setCollect(redisUtils.hashHasKey(RedisKeys.COLLECT, tId + "-" + uId + "-" + boolVo.getuId() + "-" + type));
                } else {
                    boolVo.setLike(false);
                    boolVo.setCollect(false);
                }
            }
            return ResultUtil.ok(boolVo);
        }
        return ResultUtil.error("缺少必要参数");
    }


    /**
     * 专栏主页用户统计
     */
    @Login
    @GetMapping("/getUserStatistics")
    @ApiOperation(value = "获取专栏主页用户统计数据", httpMethod = "GET")
    @ApiImplicitParam(value = "用户ID", name = "uId", required = true)
    @ApiResponse(code = 200, message = "uLook：浏览量  uLike：获赞数  uFans：粉丝数  isFocus：是否关注")
    public R getUserStatistics(@RequestParam Long uId, @ApiIgnore @LoginUser InUser user) {
        Map<String, Object> rm = new HashMap<>();
        List<InArticle> list = articleService.list(new LambdaQueryWrapper<InArticle>().eq(InArticle::getuId, uId));
        //浏览量
        LongSummaryStatistics readNumber = list.stream().collect(Collectors.summarizingLong((n) -> n.getaReadNumber() == null ? 0L : n.getaReadNumber()));
        rm.put("uLook", readNumber == null ? 0 : readNumber.getSum());
        //获赞数
        LongSummaryStatistics likeNumber = list.stream().collect(Collectors.summarizingLong((n) -> n.getaLike() == null ? 0L : n.getaLike()));
        rm.put("uLike", likeNumber == null ? 0 : likeNumber.getSum());
        //粉丝数
        InUser inUser = userService.getById(uId);
        rm.put("uFans", inUser == null ? 0 : inUser.getuFans());
        //是否关注
        Boolean isFocus = userService.isFocus(uId, user.getuId());
        rm.put("isFocus", isFocus);
        return R.ok(rm);
    }


    /**
     * 个人成就<文章数量，文章获赞，评论数量>
     */
    @Login
    @GetMapping("/honor")
    @ApiOperation(value = "个人成就", httpMethod = "GET")
    @ApiResponse(code = 200, message = "artNumber：文章数量   artLikeNumber：文章获赞数量   replyNumber：评论数量")
    public R honor(@ApiIgnore @LoginUser InUser user) {
        Map<String, Object> params = userService.honor(user.getuId());
        return R.ok().put("map", params);
    }


    /**
     * 个人中心 -- 评论
     */
    @Login
    @GetMapping("/comment")
    @ApiOperation(value = "个人中心 -- 评论", httpMethod = "GET", notes = "目标类型（字典 ：0文章，1帖子，2活动，3用户）")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public ResultUtil<PageUtils<InCommonReply>> comment
    (@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        //显示用户本人的评论
        map.put("uId", user.getuId());
        PageUtils<InCommonReply> page = userService.commentUser(map);
        return ResultUtil.ok(page);
    }


    /**
     * 个人消息 -- 评论
     */
    @Login
    @GetMapping("/commentUser")
    @ApiOperation(value = "个人消息 -- 评论", httpMethod = "GET", notes = "目标类型（字典 ：0文章，1帖子，2活动，3用户）", response = InCommonReply.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public ResultUtil<PageUtils<InCommonReply>> commentUser
    (@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        //显示其他用户向用户本人发布的评论
        map.put("uId", user.getuId());
        PageUtils<InCommonReply> page = userService.comment(map);
        return ResultUtil.ok(page);
    }


    /**
     * 个人消息 -- 点赞
     */
    @Login
    @GetMapping("/like")
    @ApiOperation(value = "个人消息 -- 点赞", httpMethod = "GET", notes = "分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public ResultUtil<PageUtils<InLikeVo>> like
    (@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        PageUtils<InLikeVo> page = userService.like(map, user.getuId());
        return ResultUtil.ok(page);
    }


    /**
     * 个人消息 -- 系统
     */
    @Login
    @GetMapping("/system")
    @ApiOperation(value = "获取系统消息列表", httpMethod = "GET", notes = "分页数据", response = InMessage.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public R system(@RequestParam Map<String, Object> params, @ApiIgnore @LoginUser InUser user) {
        params.put("uId", user.getuId());
        PageUtils page = messageService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 个人中心 -- 帖子
     */
    @Login
    @GetMapping("/card")
    @ApiOperation(value = "个人中心 -- 帖子", httpMethod = "GET", notes = "分页数据", response = UserCardVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public ResultUtil card
    (@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.card(map);
        return ResultUtil.ok(page);
    }


    /**
     * 个人消息 -- 回帖
     */
    @Login
    @GetMapping("/reply")
    @ApiOperation(value = "个人消息 -- 回帖", httpMethod = "GET", notes = "分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public ResultUtil<PageUtils<InCommonReply>> reply
    (@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils<InCommonReply> page = userService.reply(map);
        return ResultUtil.ok(page);
    }


    /**
     * 个人消息 -- 活动
     */
    @Login
    @GetMapping("/active")
    @ApiOperation(value = "个人消息 -- 活动", httpMethod = "GET", notes = "分页数据，状态码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "0：未开始 1：进行中 2：已结束", name = "type", required = true)
    })
    public ResultUtil<PageUtils<InActivity>> active
    (@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        map.put("uName", user.getuName() == null || user.getuName().equals("")
                ? user.getuNick() : user.getuName());
        PageUtils<InActivity> page = userService.active(map);
        return ResultUtil.ok(page);
    }


    /**
     * 个人关注 -- 作者
     */
    @Login
    @GetMapping("/writer")
    @ApiOperation(value = "个人消息 -- 关注 -- 作者", httpMethod = "GET", notes = "分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public ResultUtil<PageUtils<InUser>> fansWriter
    (@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.fansWriter(map);
        return ResultUtil.ok(page);
    }


    /**
     * 个人关注 -- 节点
     */
    @Login
    @GetMapping("/node")
    @ApiOperation(value = "个人消息 -- 关注 -- 节点", httpMethod = "GET", notes = "分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public ResultUtil<PageUtils<InNode>> fansNode
    (@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.fansNode(map);
        return ResultUtil.ok(page);
    }


    /**
     * 个人关注 -- 人物
     */
    @Login
    @GetMapping("/person")
    @ApiOperation(value = "个人消息 -- 关注 -- 人物", httpMethod = "GET", notes = "分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public ResultUtil<PageUtils<InUser>> fansPerson
    (@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.fansPerson(map);
        return ResultUtil.ok(page);
    }

    /**
     * 个人中心 -- 关注者
     */
    @Login
    @GetMapping("/follower")
    @ApiOperation(value = "个人中心 -- 粉丝 -- 关注者", httpMethod = "GET", notes = "分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public ResultUtil<PageUtils<InUser>> follower
    (@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.follower(map);
        return ResultUtil.ok(page);
    }


    /**
     * 个人中心 -- 收藏
     */
    @Login
    @GetMapping("/favorite")
    @ApiOperation(value = "个人中心 -- 收藏", httpMethod = "GET", notes = "分页数据，状态码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "0：文章 1：帖子 2：活动", name = "type", required = true)
    })
    public ResultUtil<PageUtils<InCard>> favorite
    (@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.favorite(map);
        return ResultUtil.ok(page);
    }


    /**
     * 个人中心 -- 删除收藏
     */
    @Login
    @PostMapping("/delFavorite")
    @ApiOperation(value = "个人中心 -- 删除收藏", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "文章、帖子、活动id", name = "id", required = true),
            @ApiImplicitParam(value = "id类型 0：文章 1：帖子 2：活动", name = "type", required = true),
    })
    public ResultUtil delFavorite(@RequestParam String id, @RequestParam String type, @ApiIgnore @LoginUser InUser
            user) {
        Long uId = -1L;
        if (NewsEnum.收藏_文章.getCode().equals(String.valueOf(type))) {
            uId = articleService.getById(id) == null ? 0 : articleService.getById(id).getuId();
            articleDao.removeACollect(Long.parseLong(id));
        }
        if (NewsEnum.收藏_帖子.getCode().equals(String.valueOf(type))) {
            uId = cardService.getById(id) == null ? 0 : cardService.getById(id).getuId();
            cardBaseDao.removeACollect(Long.parseLong(id));
        }
        if (NewsEnum.收藏_活动.getCode().equals(String.valueOf(type))) {
            uId = activityService.getById(id) == null ? 0 : activityService.getById(id).getuId();
            activityDao.removeACollect(Long.parseLong(id));
        }
        if (uId == 0) {
            String key = id + "-" + user.getuId() + "-*-" + type;
            List<Map.Entry<Object, Object>> list = redisUtils.hfget(RedisKeys.COLLECT, key);
            Map.Entry<Object, Object> entry = list.get(0);
            Object entryKey = entry.getKey();
            Long r = redisUtils.hremove(RedisKeys.COLLECT, entryKey);
            if (r > 0) {
                return ResultUtil.ok();
            } else {
                return ResultUtil.error("收藏删除失败，请重试");
            }
        } else {
            String key = id + "-" + user.getuId() + "-" + uId + "-" + type;
            Long r = redisUtils.hremove(RedisKeys.COLLECT, key);
            if (r > 0) {
                return ResultUtil.ok();
            } else {
                return ResultUtil.error("收藏删除失败，请重试");
            }
        }
    }


    /**
     * 公告
     */
    @Login
    @GetMapping("/notice")
    @ApiOperation(value = "个人中心 -- 公告", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public ResultUtil notice(@RequestParam Map<String, Object> map) {
        LambdaQueryWrapper<InMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InMessage::gettId, -1);
        IPage<InMessage> page = messageService.page(
                new Query<InMessage>().getPage(map),
                queryWrapper
        );
        return ResultUtil.ok(new PageUtils<>(page));
    }
}
