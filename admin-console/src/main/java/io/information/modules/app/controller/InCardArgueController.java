package io.information.modules.app.controller;


import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InCardArgue;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardArgueService;
import io.information.modules.app.service.IInCardBaseService;
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
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IInCardBaseService baseService;

    /**
     * 添加 esOK
     */
    @Login
    @PostMapping("/save")
    @ApiOperation(value = "新增辩论帖子", httpMethod = "POST")
    @ApiImplicitParam(name = "cardArgue", value = "基础和辩论帖子信息", required = true)
    public R save(@RequestBody InCardArgue cardArgue) {
        long cId = IdGenerator.getId();
        InCardBase cardBase = cardArgue.getInCardBase();
        cardBase.setcId(cId);
        cardArgue.setcId(cId);
        argueService.save(cardArgue);
        baseService.save(cardBase);
        rabbitTemplate.convertAndSend(Constants.cardExchange,
                Constants.card_Save_RouteKey, cardArgue);
        return R.ok();
    }


    /**
     * 删除 esOK
     */
    @Login
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除辩论帖子", httpMethod = "DELETE", notes = "根据cId[数组]删除辩论帖子")
    @ApiImplicitParam(name = "cIds", value = "帖子ID", dataType = "Array", required = true)
    public R delete(@RequestBody Long[] cIds) {
        argueService.removeByIds(Arrays.asList(cIds));
        rabbitTemplate.convertAndSend(Constants.cardExchange,
                Constants.card_Delete_RouteKey, cIds);
        return R.ok();
    }


    /**
     * 修改 esOK
     */
    @Login
    @PutMapping("/update")
    @ApiOperation(value = "修改辩论帖子", httpMethod = "PUT")
    @ApiImplicitParam(name = "cardArgue", value = "辩论帖子信息", required = true)
    public R updateCardArgue(@RequestBody InCardArgue cardArgue) {
        argueService.updateById(cardArgue);
        rabbitTemplate.convertAndSend(Constants.cardExchange,
                Constants.card_Update_RouteKey, cardArgue);
        return R.ok();
    }


    /**
     * 查询 esOK
     */
    @GetMapping("/info/{cId}")
    @ApiOperation(value = "单个辩论帖子", httpMethod = "GET", notes = "根据帖子ID查询辩论帖子信息")
    @ApiImplicitParam(name = "cId", value = "帖子ID", required = true)
    public R queryCardArgue(@PathVariable("cId") Long cId) {
        InCardArgue cardArgue = argueService.getById(cId);
        return R.ok().put("cardArgue", cardArgue);
    }


    /**
     * 列表 esOK
     */
    @GetMapping("/list")
    @ApiOperation(value = "全部辩论帖子", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = argueService.queryPage(map);
        return R.ok().put("page", page);
    }

    /**
     * 辩论支持
     */
    @Login
    @GetMapping("/support")
    @ApiOperation(value = "支持辩论方", httpMethod = "GET")
    public R support(Long cid, Integer supportSide, @LoginUser InUser user) {
        return R.ok().put("supportSide", argueService.support(cid, user.getuId(), supportSide));
    }

    /**
     * 加入辩论
     */
    @Login
    @GetMapping("/join")
    @ApiOperation(value = "加入辩论方", httpMethod = "GET")
    public R join(Long cid, Integer joinSide, @ApiIgnore @LoginUser InUser user) {
        return R.ok().put("joinSide", argueService.support(cid, user.getuId(), joinSide));
    }

}
