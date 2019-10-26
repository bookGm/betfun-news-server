package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.service.IInNewsFlashService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    IInNewsFlashService iInNewsFlashService;
    /**
     * 列表 esOK
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页获取快讯", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = iInNewsFlashService.queryPage(map);
        return R.ok().put("page", page);
    }

}
