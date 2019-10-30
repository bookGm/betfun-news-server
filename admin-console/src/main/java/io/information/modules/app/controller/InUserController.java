package io.information.modules.app.controller;


import com.alibaba.fastjson.JSON;
import io.information.common.utils.DataUtils;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.dto.IdentifyCompanyDTO;
import io.information.modules.app.dto.IdentifyPersonalDTO;
import io.information.modules.app.dto.RedactDataDTO;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInUserService;
import io.information.modules.sys.controller.AbstractController;
import io.mq.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    @ApiOperation(value = "修改密码", httpMethod = "PUT", notes = "已登录状态，修改用户密码")
    @ApiImplicitParam(name = "map", value = "新旧密码", required = true)
    public R change(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        userService.change(map, user);
        return R.ok();
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
    @ApiOperation(value = "关注", httpMethod = "POST")
    @ApiImplicitParam(name = "uId", value = "关注的用户id", required = true)
    public R focus(Long uId, @ApiIgnore @LoginUser InUser user) {
        userService.focus(user.getuId(), uId);
        return R.ok();
    }


    /**
     * 个人成就<文章数量，文章获赞，评论数量>
     */
    @Login
    @GetMapping("/honor")
    @ApiOperation(value = "个人成就", httpMethod = "GET")
    public R honor(@ApiIgnore @LoginUser InUser user) {
        Map<String, Object> params = userService.honor(user.getuId());
        return R.ok().put("map", params);
    }

    /**
     * 个人消息 -- 评论
     */
    @Login
    @GetMapping("/comment")
    @ApiOperation(value = "个人消息 -- 评论", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R comment(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.comment(map);
        return R.ok().put("page", page);
    }

    /**
     * 个人消息 -- 点赞
     */
    @Login
    @GetMapping("/like")
    @ApiOperation(value = "个人消息 -- 点赞", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", dataType = "Map", required = true)
    public R like(@RequestParam Map<String, Object> params, @ApiIgnore @LoginUser InUser user) {
        PageUtils page = userService.like(params,user);
        return R.ok().put("page",page);
    }

    /**
     * 个人消息 -- 系统
     */
    @Login
    @GetMapping("/system")
    @ApiOperation(value = "个人消息 -- 系统", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R system(@RequestParam Map<String, Object> params, @ApiIgnore @LoginUser InUser user) {
        params.put("uId", user.getuId());
        return R.ok();
    }

    /**
     * 个人消息 -- 帖子
     */
    @Login
    @GetMapping("/card")
    @ApiOperation(value = "个人消息 -- 帖子", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R card(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.card(map);
        return R.ok().put("page", page);
    }

    /**
     * 个人消息 -- 回帖
     */
    @Login
    @GetMapping("/reply")
    @ApiOperation(value = "个人消息 -- 回帖", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据，", required = true)
    public R reply(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.reply(map);
        return R.ok().put("page", page);
    }

    /**
     * 个人消息 -- 活动
     */
    @Login
    @GetMapping("/active")
    @ApiOperation(value = "个人消息 -- 活动", httpMethod = "GET", notes = "状态码[type] 0：未开始 1：已开始 2：已结束")
    @ApiImplicitParam(name = "map", value = "分页数据，状态码", required = true)
    public R active(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.active(map);
        return R.ok().put("page", page);
    }


    /**
     * 个人中心 -- 粉丝
     */
    @Login
    @GetMapping("/fans")
    @ApiOperation(value = "个人消息 -- 粉丝", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R fans(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.fans(map);
        return R.ok().put("page", page);
    }

}
