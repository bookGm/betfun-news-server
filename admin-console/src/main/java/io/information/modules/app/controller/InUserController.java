package io.information.modules.app.controller;


import com.alibaba.fastjson.JSON;
import io.information.common.utils.DataUtils;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.common.utils.ResultUtil;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.dto.IdentifyCompanyDTO;
import io.information.modules.app.dto.IdentifyPersonalDTO;
import io.information.modules.app.dto.RedactDataDTO;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.IInUserService;
import io.information.modules.app.vo.InLikeVo;
import io.information.modules.sys.controller.AbstractController;
import io.mq.utils.Constants;
import io.swagger.annotations.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

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
     * 个人认证
     */
    @Login
    @PostMapping("/identifyPersonal")
    @ApiOperation(value = "个人认证", httpMethod = "POST")
    public R identifyPersonal(@RequestBody IdentifyPersonalDTO identifyPersonalDTO, @ApiIgnore @LoginUser InUser user) {
        InUser u = DataUtils.copyData(identifyPersonalDTO, InUser.class);
        u.setuId(user.getuId());
        userService.updateById(u);
        rabbitTemplate.convertAndSend(Constants.userExchange,
                Constants.user_Update_RouteKey, JSON.toJSON(u));
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
        userService.updateById(u);
        rabbitTemplate.convertAndSend(Constants.userExchange,
                Constants.user_Update_RouteKey, JSON.toJSON(u));
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
        userService.updateById(u);
        rabbitTemplate.convertAndSend(Constants.userExchange,
                Constants.user_Update_RouteKey, JSON.toJSON(u));
        return R.ok();
    }


    /**
     * 编辑资料
     */
    @Login
    @PostMapping("/redactData")
    @ApiOperation(value = "编辑资料", httpMethod = "POST")
    public R redactData(@RequestBody RedactDataDTO redactDataDTO, @ApiIgnore @LoginUser InUser user) {
        InUser u = DataUtils.copyData(redactDataDTO, InUser.class);
        u.setuId(user.getuId());
        userService.updateById(u);
        rabbitTemplate.convertAndSend(Constants.userExchange,
                Constants.user_Update_RouteKey, JSON.toJSON(u));
        return R.ok();
    }


    /**
     * 关注用户 <作者，节点，人物>
     */
    @Login
    @PostMapping("/focus")
    @ApiOperation(value = "关注用户", httpMethod = "POST", notes = "被关注的用户id")
    @ApiImplicitParam(value = "用户id", name = "uId", required = true)
    public R focus(@RequestParam Long uId, @ApiIgnore @LoginUser InUser user) {
        userService.focus(user.getuId(), uId, user.getuAuthStatus());
        return R.ok();
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
     * 个人消息 -- 评论
     */
    @Login
    @GetMapping("/comment")
    @ApiOperation(value = "个人消息 -- 评论", httpMethod = "GET", notes = "分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public ResultUtil<PageUtils<InCommonReply>> comment(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.comment(map);
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
        PageUtils page = userService.like(map, user.getuId());
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
     * 个人消息 -- 帖子
     */
    @Login
    @GetMapping("/card")
    @ApiOperation(value = "个人消息 -- 帖子", httpMethod = "GET", notes = "分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "帖子节点分类", name = "type", required = true),
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public ResultUtil<PageUtils<InCard>> card(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
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
    public ResultUtil<PageUtils<InCard>> reply(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.reply(map);
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
            @ApiImplicitParam(value = "0：未开始 1：已开始 2：已结束", name = "type", required = true)
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
     * 查询用户的所有关注ID<用户ID>
     */
    @Login
    @GetMapping("/focusIds")
    @ApiOperation(value = "查询当前用户的关注ID集合", httpMethod = "GET")
    @ApiResponse(code = 200, message = "uId：用户ID")
    public List<Long> searchFocusId(@ApiIgnore @LoginUser InUser user) {
        return userService.searchFocusId(user.getuId());
    }
}
