package io.information.modules.app.controller;


import com.alibaba.fastjson.JSON;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInUserService;
import io.information.modules.sys.controller.AbstractController;
import io.mq.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    @Autowired
    private RedisTemplate redisTemplate;


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
     * 更新
     */
    @Login
    @PutMapping("/update")
    @ApiOperation(value = "修改咨讯用户", httpMethod = "PUT")
    @ApiImplicitParam(name = "user", value = "用户信息", required = true)
    public R update(@RequestBody InUser user) {
        userService.updateById(user);
        rabbitTemplate.convertAndSend(Constants.userExchange,
                Constants.user_Update_RouteKey, JSON.toJSON(user));
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
    @ApiOperation(value = "个人消息 -- 系统", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R like(@RequestParam Map<String, Object> params, @ApiIgnore @LoginUser InUser user) {
        params.put("uId", user.getuId());
//        PageUtils page = userService.like(params);
//        Object obj = redisUtils.hget(RedisKeys., String.valueOf(uId);
        return R.ok()/*.put("page",page)*/;
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
    @ApiOperation(value = "获取本人发布的帖子", httpMethod = "GET")
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
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R reply(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = userService.reply(map);
        return R.ok().put("page", page);
    }
}
