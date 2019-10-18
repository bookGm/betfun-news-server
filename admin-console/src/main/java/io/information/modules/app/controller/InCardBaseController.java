package io.information.modules.app.controller;


import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInUserService;
import io.mq.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 资讯帖子基础表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/card/base")
@Api(value = "/app/card/base", tags = "APP帖子_基础表")
public class InCardBaseController {
    @Autowired
    private IInCardBaseService cardBaseService;
    @Autowired
    private IInUserService iInUserService;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 添加
     */
    @Login
    @PostMapping("/save")
    @ApiOperation(value = "新增基础帖子", httpMethod = "POST")
    @ApiImplicitParam(name = "cardBase", value = "基础帖子信息", required = true)
    public R save(@RequestBody InCardBase cardBase, @ApiIgnore @LoginUser InUser user) {
        if (2 == user.getuAuthStatus()) {
            cardBase.setcId(IdGenerator.getId());
            cardBaseService.save(cardBase);
            rabbitTemplate.convertAndSend(Constants.cardExchange,
                    Constants.card_Save_RouteKey, cardBase);
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }


    /**
     * 删除
     */
    @Login
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除基础帖子", httpMethod = "DELETE", notes = "根据cId[数组]删除基础帖子")
    @ApiImplicitParam(name = "cIds", value = "帖子ID", dataType = "Array", required = true)
    public R delete(@RequestBody Long[] cIds, @ApiIgnore @LoginUser InUser user) {
        if (2 == user.getuAuthStatus()) {
            cardBaseService.removeByIds(Arrays.asList(cIds));
            rabbitTemplate.convertAndSend(Constants.cardExchange,
                    Constants.card_Delete_RouteKey, cIds);
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }


    /**
     * 修改
     */
    @Login
    @PutMapping("/update")
    @ApiOperation(value = "修改基础帖子", httpMethod = "PUT")
    @ApiImplicitParam(name = "cardBase", value = "基础帖子信息", required = true)
    public R update(@RequestBody InCardBase cardBase, @ApiIgnore @LoginUser InUser user) {
        if (2 == user.getuAuthStatus()) {
            cardBaseService.updateById(cardBase);
            rabbitTemplate.convertAndSend(Constants.cardExchange,
                    Constants.card_Update_RouteKey, cardBase);
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "全部基础帖子", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = cardBaseService.queryPage(map);
        return R.ok().put("page", page);
    }

    /**
     * 查询
     */
    @GetMapping("/info/{cId}")
    @ApiOperation(value = "单个基础帖子", httpMethod = "GET")
    @ApiImplicitParam(name = "cId", value = "帖子ID", required = true)
    public R info(@PathVariable("cId") Long cId) {
        InCardBase cardBase = cardBaseService.getById(cId);
        return R.ok().put("cardBase", cardBase);
    }


    /**
     * 根据正反方ids字符串,查询用户信息
     */
    @GetMapping("/idsUser")
    @ApiOperation(value = "分割查询用户信息", httpMethod = "GET", notes = "根据正反方ids字符串，用 ，分隔")
    @ApiImplicitParam(name = "map", value = "分页数据、正反方ids字符串", required = true)
    public R idsUser(@RequestParam Map<String, Object> map) {
        PageUtils page = iInUserService.queryUsersByArgueIds(map);
        return R.ok().put("page", page);
    }


    /**
     * 帖子查询
     */
    @GetMapping("/status")
    @ApiOperation(value = "查询某一类帖子", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据，帖子类型<aCategory>", required = true)
    public R status(@RequestParam Map<String, Object> map) {
        PageUtils page = cardBaseService.status(map);
        return R.ok().put("page", page);
    }


    /**
     * 用户帖子查询
     */
    @Login
    @GetMapping("/stateUser")
    @ApiOperation(value = "查询用户的某一类帖子", httpMethod = "GET", notes = "自动获取用户信息")
    @ApiImplicitParam(name = "map", value = "分页数据，帖子类型<aCategory>", required = true)
    public R stateUser(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        PageUtils page = cardBaseService.queryStateCard(map, user.getuId());
        return R.ok().put("page", page);
    }
}
