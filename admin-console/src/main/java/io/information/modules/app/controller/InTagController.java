package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.InTag;
import io.information.modules.app.service.IInTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 资讯标签表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/tag")
@Api(value = "/app/tag", tags = "APP资讯标签接口")
public class InTagController {
    @Autowired
    private IInTagService tagService;


    /**
     * 添加
     */
    @PostMapping("/save")
    public R save(@RequestBody InTag tag) {
        tag.settId(new IdWorker().nextId());
        tag.settCreateTime(new Date());
        tagService.save(tag);
        return R.ok();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] tIds) {
        tagService.removeByIds(Arrays.asList(tIds));
        return R.ok();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody InTag tag) {
        tagService.updateById(tag);
        return R.ok();
    }


    /**
     * 查询
     */
    @GetMapping("/info/{tId}")
    @ApiOperation(value = "查询单个资讯标签", httpMethod = "GET")
    @ApiImplicitParam(name = "tId", value = "标签ID", required = true)
    public R queryTag(@PathVariable("tId") Long tId) {
        InTag tag = tagService.getById(tId);
        return R.ok().put("tag", tag);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页获取资讯标签", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = tagService.queryPage(map);
        return R.ok().put("page", page);
    }

    /**
     * 全部资讯标签
     */
    @GetMapping("/getAllList")
    @ApiOperation(value = "获取全部资讯标签", httpMethod = "GET")
    public R getAllList() {
        return R.ok().put("tags", tagService.list());
    }
}
