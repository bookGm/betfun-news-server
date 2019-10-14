package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.InTag;
import io.information.modules.app.service.IInTagService;
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
 *  前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/tag")
public class InTagController {
    @Autowired
    private IInTagService tagService;


    /**
     * 添加
     */
    @PostMapping("/save")
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
    public ResponseEntity<Void> delete(@RequestBody Long[] tIds){
        tagService.removeByIds(Arrays.asList(tIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody InTag tag){
        tagService.updateById(tag);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 查询
     */
    @GetMapping("/info/{tId}")
    public ResponseEntity<InTag> queryTag(@PathVariable("tId") Long tId){
        InTag tag = tagService.getById(tId);
        return ResponseEntity.ok(tag);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageUtils> list(@RequestParam Map<String,Object> params){
        PageUtils page = tagService.queryPage(params);
        return ResponseEntity.ok(page);
    }
}
