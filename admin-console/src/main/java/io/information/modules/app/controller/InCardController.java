package io.information.modules.app.controller;

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
        if (2 == user.getuAuthStatus()) {
            cardService.issueCard(card, user);
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }


    /**
     * 帖子详情
     */
    @GetMapping("/details/{cId}")
    @ApiOperation(value = "帖子详情", httpMethod = "POST")
    @ApiImplicitParam(name = "cardBase", value = "基础帖子信息", required = true)
    public R details(@PathVariable("cId") Long cId) {
        InCard card = cardService.details(cId);
        return R.ok().put("card", card);
    }


    /**
     * 帖子删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "帖子删除", httpMethod = "DELETE")
    @ApiImplicitParam(name = "cardBase", value = "帖子ID数组", dataType = "Long[ ]", required = true)
    public R delete(@RequestBody Long[] cIds) {
        cardService.delete(cIds);
        return R.ok();
    }
}
