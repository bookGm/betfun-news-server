package io.information.modules.app.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;


@RestController
@RequestMapping("/app/card")
@Api(value = "/app/card", tags = "APP帖子公共接口")
public class InCardController {
    @Autowired
    private IInCardService cardService;

    /**
     * 发布帖子
     */
    @Login
    @PostMapping("/issue")
    @ApiOperation(value = "发布帖子", httpMethod = "POST")
    @ApiImplicitParam(name = "card", value = "帖子信息", required = true)
    public R issueCard(@RequestBody InCard card, @ApiIgnore @LoginUser InUser user) {
        cardService.issueCard(card, user);
        return R.ok();

    }


    /**
     * 帖子详情
     */
    @GetMapping("/details/{cId}")
    @ApiOperation(value = "帖子详情", httpMethod = "GET")
    @ApiImplicitParam(name = "cId", value = "帖子ID", required = true)
    public R details(@PathVariable("cId") Long cId) {
        InCard card = cardService.details(cId);
        return R.ok().put("card", card);
    }


    /**
     * 帖子删除
     */
    @Login
    @DeleteMapping("/delete")
    @ApiOperation(value = "帖子删除", httpMethod = "DELETE")
    @ApiImplicitParam(name = "cardBase", value = "帖子ID数组", dataType = "Long[ ]", required = true)
    public R delete(@RequestBody Long[] cIds) {
        cardService.delete(cIds);
        return R.ok();
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取某一类帖子", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据，帖子类型<aCategory>", required = true)
    public R status(@RequestParam Map<String, Object> map) {
        PageUtils page = cardService.queryPage(map);
        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @Login
    @GetMapping("/listUser")
    @ApiOperation(value = "获取本人发布的帖子", httpMethod = "GET", notes = "自动获取用户信息")
    @ApiImplicitParam(name = "map", value = "分页数据，帖子类型<aCategory>", required = true)
    public R stateUser(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = cardService.queryPage(map);
        return R.ok().put("page", page);
    }
}
