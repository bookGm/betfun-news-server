package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.InTag;
import io.information.modules.app.service.IInTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 咨讯标签表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/tag")
@Api(value = "APP咨讯标签接口")
public class InTagController {
    @Autowired
    private IInTagService tagService;


    /**
     * 添加
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增咨讯标签")
    public ResponseEntity<Void> save(@RequestBody InTag tag){
        tag.settId(new IdWorker().nextId());
        tag.settCreateTime(new Date());
        tagService.save(tag);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "单个或批量删除咨讯标签")
    public ResponseEntity<Void> delete(@RequestBody Long[] tIds){
        tagService.removeByIds(Arrays.asList(tIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改咨讯标签")
    public ResponseEntity<Void> update(@RequestBody InTag tag){
        tagService.updateById(tag);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 查询
     */
    @GetMapping("/info/{tId}")
    @ApiOperation(value = "查询单个咨讯标签")
    public ResponseEntity<InTag> queryTag(@PathVariable("tId") Long tId){
        InTag tag = tagService.getById(tId);
        return ResponseEntity.ok(tag);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取全部资讯标签")
    public ResponseEntity<PageUtils> list(@RequestParam Map<String,Object> params){
        PageUtils page = tagService.queryPage(params);
        return ResponseEntity.ok(page);
    }
}
