package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.service.IInCardBaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取全部基础帖子", httpMethod = "GET", notes = "分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public R status(@RequestParam Map<String, Object> map) {
        PageUtils page = cardBaseService.queryPage(map);
        return R.ok().put("page", page);
    }

}
