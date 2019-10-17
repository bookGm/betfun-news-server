package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
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

import java.util.Arrays;
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
    private IInUserService iInUserService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //TODO 微信扫码登录

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "单个或批量删除咨讯用户", httpMethod = "DELETE", notes = "根据uId[数组]删除用户")
    @ApiImplicitParam(name = "uIds", value = "用户ID", dataType = "Array", required = true)
    public R delete(@RequestBody Long[] uIds) {
        iInUserService.removeByIds(Arrays.asList(uIds));
        rabbitTemplate.convertAndSend(Constants.userExchange,
                Constants.card_Save_RouteKey, uIds);
        return R.ok();
    }


    /**
     * 更新
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改咨讯用户信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "user", value = "用户信息", required = true)
    public R update(@RequestBody InUser user) {
        iInUserService.updateById(user);
        rabbitTemplate.convertAndSend(Constants.userExchange,
                Constants.card_Update_RouteKey, user);
        return R.ok();
    }


    /**
     * 模糊匹配用户
     */
    @GetMapping("/queryLike")
    @ApiOperation(value = "模糊查询用户", httpMethod = "GET", notes = "根据用户昵称模糊匹配用户")
    @ApiImplicitParam(name = "map", value = "分页数据、用户昵称[uNick]", required = true)
    public R queryLikeByUser(@RequestParam Map<String, Object> map) {
        PageUtils page = iInUserService.queryLikeByUser(map);
        return R.ok().put("page", page);
    }


}
