package io.information.modules.app.controller;


import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardArgueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 资讯帖子辩论表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/card/argue")
@Api(value = "/app/card/argue", tags = "APP帖子_辩论表")
public class InCardArgueController {
    @Autowired
    private IInCardArgueService argueService;


    /**
     * 辩论支持
     */
    @Login
    @GetMapping("/support")
    @ApiOperation(value = "支持辩论方", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "帖子id", name = "cId", required = true),
            @ApiImplicitParam(value = "支持方（0：正方  1：反方）", name = "supportSide", required = false),
    })
    public R support(@RequestParam("cId")Long cId, @RequestParam("supportSide")Integer supportSide, @ApiIgnore @LoginUser InUser user) {
        return R.ok().put("supportSide", argueService.support(cId, user.getuId(), supportSide));
    }


    /**
     * 加入辩论
     */
    @Login
    @GetMapping("/join")
    @ApiOperation(value = "加入辩论方", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "帖子id", name = "cId", required = true),
            @ApiImplicitParam(value = "加入方（0：正方  1：反方）", name = "joinSide", required = false),
    })
    public R join(@RequestParam("cId")Long cId, @RequestParam("joinSide")Integer joinSide, @ApiIgnore @LoginUser InUser user) {
        return R.ok().put("joinSide", argueService.join(cId, user.getuId(), joinSide));
    }

}
