package io.information.modules.app.controller;


import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInUserService;
import io.information.modules.sys.controller.AbstractController;
import io.mq.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    //TODO 微信扫码登录
    // 用户消息<分页>
    // 用户关注列表<分页>
    // 用户消息<分页>
    // ...


    /**
     * 密码登录
     */
    @GetMapping("/upLogin")
    @ApiOperation(value = "密码登录", httpMethod = "GET", notes = "使用用户名和密码登录状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)})
    public R upLogin(String username, String password) {
        userService.findUser(username, password);
        return R.ok();
    }


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
    @PutMapping("/update")
    @ApiOperation(value = "修改咨讯用户", httpMethod = "PUT")
    @ApiImplicitParam(name = "user", value = "用户信息", required = true)
    public R update(@RequestBody InUser user) {
        userService.updateById(user);
//        rabbitTemplate.convertAndSend(Constants.userExchange,
//                Constants.user_Update_RouteKey, user);
        return R.ok();
    }



}
