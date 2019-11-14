package io.information.modules.app.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guansuo.newsenum.NewsEnum;
import io.elasticsearch.entity.EsUserEntity;
import io.information.common.utils.*;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.dto.IdentifyCompanyDTO;
import io.information.modules.app.dto.IdentifyPersonalDTO;
import io.information.modules.app.dto.RedactDataDTO;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.IInActivityService;
import io.information.modules.app.service.IInArticleService;
import io.information.modules.app.service.IInCardService;
import io.information.modules.app.service.IInUserService;
import io.information.modules.app.vo.InLikeVo;
import io.information.modules.app.vo.UserBoolVo;
import io.information.modules.app.vo.UserCardVo;
import io.information.modules.sys.controller.AbstractController;
import io.mq.utils.Constants;
import io.swagger.annotations.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "/app/user", tags = "APP咨讯用户接口")
public class InUserController extends AbstractController {
    @Autowired
    private IInUserService userService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IInArticleService articleService;
    @Autowired
    private IInActivityService activityService;
    @Autowired
    private IInCardService cardService;
    @Autowired
    RedisUtils redisUtils;


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
        boolean flag = userService.change(uPwd, newPwd, user);
        if (flag) {
            return R.ok();
        } else {
            return R.error("输入的旧密码有误");
        }
    }


    /**
     * 查询用户信息
     */
    @Login
    @GetMapping("/info")
    @ApiOperation(value = "查询用户信息", httpMethod = "GET")
    public ResultUtil<InUser> info(@ApiIgnore @LoginUser InUser user) {
        return ResultUtil.ok(userService.getById(user.getuId()));
    }


    /**
     * 上传用户头像
     */
    @Login
    @PutMapping("/photo")
    @ApiOperation(value = "上传用户头像", httpMethod = "PUT")
    public ResultUtil photo(@RequestBody String uPhoto, @ApiIgnore @LoginUser InUser user) {
        InUser inUser = new InUser();
        inUser.setuId(user.getuId());
        inUser.setuPhone(uPhoto);
        userService.updateById(inUser);
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
        InUser u = DataUtils.copyData(redactDataDTO, InUser.class);
        u.setuId(user.getuId());
        userService.updateById(u);
        InUser inUser = userService.getById(u.getuId());
        EsUserEntity esUser = BeanHelper.copyProperties(inUser, EsUserEntity.class);
        rabbitTemplate.convertAndSend(Constants.userExchange,
                Constants.user_Update_RouteKey, esUser);
        return R.ok();
    }


    /**
     * 关注用户 <作者，节点，人物>
     */
    @Login
    @PostMapping("/focus")
    @ApiOperation(value = "关注用户", httpMethod = "POST", notes = "被关注的用户id")
    @ApiImplicitParam(value = "用户id", name = "uId", required = true)
    public R focus(@RequestBody Long uId, @ApiIgnore @LoginUser InUser user) {
        InUser inUser = userService.getById(uId);
        userService.focus(user.getuId(), inUser.getuPotential(),uId);
        return R.ok();
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
    @Login
    @GetMapping("/userNumber")
    @ApiOperation(value = "用户数据信息[点赞，收藏，评论]", httpMethod = "GET", response = UserBoolVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "目标用户ID", name = "uId", required = true),
            @ApiImplicitParam(value = "目标[文章，帖子，活动]ID", name = "tId", required = true),
            @ApiImplicitParam(value = "目标类型(0：文章 1：帖子 2：活动)", name = "type", required = true)
    })
    public ResultUtil<UserBoolVo> userNumber(@RequestParam Long uId, @RequestParam Long tId, @RequestParam Integer type, @ApiIgnore @LoginUser InUser user) {
        UserBoolVo boolVo = userService.userNumber(uId, tId, type, user);
        return ResultUtil.ok(boolVo);
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
    public ResultUtil<PageUtils<InCommonReply>> comment(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils<InCommonReply> page = userService.comment(map);
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
    public ResultUtil<PageUtils<InCommonReply>> commentUser(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils<InCommonReply> page = userService.commentUser(map);
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
    public ResultUtil<PageUtils<InLikeVo>> like(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        PageUtils<InLikeVo> page = userService.like(map, user.getuId());
        return ResultUtil.ok(page);
    }


    /**
     * 个人消息 -- 系统
     * TODO
     */
    @Login
    @GetMapping("/system")
//    @ApiOperation(value = "个人消息 -- 系统", httpMethod = "GET", notes = "分页数据")
    public R system(@RequestParam Map<String, Object> params, @ApiIgnore @LoginUser InUser user) {
        params.put("uId", user.getuId());
        return R.ok();
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
    public ResultUtil<PageUtils<UserCardVo>> card(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
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
    public ResultUtil<PageUtils<InCommonReply>> reply(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
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
    public ResultUtil<PageUtils<InActivity>> active(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
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
    public ResultUtil<PageUtils<InUser>> fansWriter(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
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
    public ResultUtil<PageUtils<InNode>> fansNode(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
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
    public ResultUtil<PageUtils<InUser>> fansPerson(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
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
    public ResultUtil<PageUtils<InUser>> follower(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
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
    public ResultUtil<PageUtils<InCard>> favorite(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.favorite(map);
        return ResultUtil.ok(page);
    }

    /**
     * 个人中心 -- 删除收藏
     */
    @Login
    @GetMapping("/delFavorite")
    @ApiOperation(value = "个人中心 -- 删除收藏", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "文章、帖子、活动id", name = "id", required = true),
            @ApiImplicitParam(value = "id类型 0：文章 1：帖子 2：活动", name = "type", required = true),
    })
    public ResultUtil delFavorite(@RequestParam String id, @RequestParam String type, @ApiIgnore @LoginUser InUser user) {
        Long uId = -1L;
        if (NewsEnum.收藏_文章.getCode().equals(type)) {
            uId = articleService.getById(id).getuId();
        }
        if (NewsEnum.收藏_帖子.getCode().equals(type)) {
            uId = cardService.getById(id).getuId();
        }
        if (NewsEnum.收藏_活动.getCode().equals(type)) {
            uId = activityService.getById(id).getuId();
        }
        String key = id + "-" + user.getuId() + "-" + uId + "-" + type;
        Long r = redisUtils.hremove(RedisKeys.COLLECT, key);
        if (r > 0) {
            return ResultUtil.ok();
        } else {
            return ResultUtil.error("收藏删除失败，请重试");
        }
    }
}
