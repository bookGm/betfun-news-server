package io.information.modules.app.controller;


import com.alibaba.fastjson.JSON;
import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.entity.InNewsFlash;
import io.information.modules.app.service.IInNewsFlashService;
import io.mq.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
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
    @ApiOperation(value = "分页获取快讯", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = newsFlashService.queryPage(map);
        return R.ok().put("page", page);
    }

    /**
     * 查询
     */
    @GetMapping("/info/{nId}")
    @ApiOperation(value = "查询单个快讯", httpMethod = "GET")
    @ApiImplicitParam(name = "nId", value = "快讯ID", required = true)
    public R info(@PathVariable("nId") Long nId){
        InNewsFlash newsFlash = newsFlashService.getById(nId);
        return R.ok().put("newsFlash",newsFlash);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增快讯", httpMethod = "POST")
    @ApiImplicitParam(name = "flash", value = "快讯对象", required = true)
    public R save(@RequestBody InNewsFlash flash) {
        flash.setnId(IdGenerator.getId());
        flash.setnCreateTime(new Date());
        newsFlashService.save(flash);
        rabbitTemplate.convertAndSend(Constants.flashExchange,
                Constants.flash_Save_RouteKey, JSON.toJSON(flash));
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改快讯", httpMethod = "PUT")
    @ApiImplicitParam(name = "flash", value = "快讯对象", required = true)
    public R update(@RequestBody InNewsFlash flash) {
        newsFlashService.updateById(flash);
        rabbitTemplate.convertAndSend(Constants.flashExchange,
                Constants.flash_Update_RouteKey, JSON.toJSON(flash));
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除单个或多个快讯", httpMethod = "DELETE")
    @ApiImplicitParam(name = "nIds", value = "快讯ID",dataType = "Long[ ]",required = true)
    public R delete(@RequestBody Long[] nIds) {
        newsFlashService.removeByIds(Arrays.asList(nIds));
        rabbitTemplate.convertAndSend(Constants.flashExchange,
                Constants.flash_Delete_RouteKey, nIds);
        return R.ok();
    }
}
