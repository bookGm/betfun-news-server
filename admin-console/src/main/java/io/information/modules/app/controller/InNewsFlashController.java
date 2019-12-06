package io.information.modules.app.controller;


import com.guansuo.common.StringUtil;
import io.elasticsearch.entity.EsFlashEntity;
import io.information.common.utils.BeanHelper;
import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InNewsFlash;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInNewsFlashService;
import io.mq.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯快讯表 前端控制器
 * </p>
 *
 * @author LX
 * @since 2019-10-26
 */
@RestController
@RequestMapping("/app/newsflash")
@Api(value = "/app/newsflash", tags = "APP快讯接口")
public class InNewsFlashController {
    @Autowired
    private IInNewsFlashService newsFlashService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 列表 esOK
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页获取快讯", httpMethod = "GET", notes = "分页数据")
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = newsFlashService.queryPage(map);
        return R.ok().put("page", page);
    }

    /**
     * 查询 gId 上一条 tId 下一条
     */
    @GetMapping("/info/{nId}")
    @ApiOperation(value = "查询单个快讯", httpMethod = "GET", notes = "快讯ID")
    public R info(@PathVariable("nId") Long nId) {
        InNewsFlash newsFlash = newsFlashService.getById(nId);
        return R.ok().put("newsFlash", newsFlash);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增快讯", httpMethod = "POST")
    public R save(@RequestBody InNewsFlash flash) {
        flash.setnId(IdGenerator.getId());
        flash.setnCreateTime(new Date());
        newsFlashService.save(flash);
        EsFlashEntity esFlash = BeanHelper.copyProperties(flash, EsFlashEntity.class);
        rabbitTemplate.convertAndSend(Constants.flashExchange,
                Constants.flash_Save_RouteKey, esFlash);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改快讯", httpMethod = "PUT")
    public R update(@RequestBody InNewsFlash flash) {
        newsFlashService.updateById(flash);
        EsFlashEntity esFlash = BeanHelper.copyProperties(flash, EsFlashEntity.class);
        rabbitTemplate.convertAndSend(Constants.flashExchange,
                Constants.flash_Update_RouteKey, esFlash);
        return R.ok();
    }


    /**
     * 利好利空
     */
    @Login
    @PostMapping("/attitude")
    @ApiOperation(value = "利好利空", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nId", value = "资讯id", required = true),
            @ApiImplicitParam(name = "bId", value = "0：利空 1：利好", required = true)
    })
    public R attitude(@RequestBody Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        if ((null != map.get("nId") && StringUtil.isNotBlank(map.get("nId")))
                && (null != map.get("bId") && StringUtil.isNotBlank(map.get("bId")))) {
            long nId = Long.parseLong(String.valueOf(map.get("nId")));
            int bId = Integer.parseInt(String.valueOf(map.get("bId")));
            newsFlashService.attitude(nId, user.getuId(), bId);
            return R.ok();
        }
        return R.error("缺少必要的参数");
    }

//    @GetMapping("/esSave")
    public R esSave() {
        List<InNewsFlash> users = newsFlashService.all();
        List<EsFlashEntity> fEsList = BeanHelper.copyWithCollection(users, EsFlashEntity.class);
        if (null != fEsList) {
            for (EsFlashEntity esFlash : fEsList) {
                rabbitTemplate.convertAndSend(Constants.flashExchange,
                        Constants.flash_Save_RouteKey, esFlash);
            }
        }
        return R.ok();
    }
}
