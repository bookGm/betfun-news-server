package io.information.modules.app.controller;


import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.InTag;
import io.information.modules.app.service.IInTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/news/tag")
public class InTagController {
    @Autowired
    private IInTagService tagService;


    /**
     * 添加标签
     * @return
     */
    @PostMapping("/addTag")
    public ResponseEntity<Void> addTag(InTag tag){
        tag.setTId(new IdWorker().nextId());
        tag.setTCreateTime(LocalDateTime.now());
        tagService.save(tag);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除标签
     * @return
     */
    @DeleteMapping("/deleteTag")
    public ResponseEntity<Void> deleteTag(Long tagId){
        tagService.removeById(tagId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 批量删除标签
     * @return
     */
    @DeleteMapping("/deleteBatchTag")
    public ResponseEntity<Void> deleteBatchTag(List<Long> tagIds){
        tagService.removeByIds(tagIds);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改标签
     * @param tag
     * @return
     */
    @PutMapping("/updateTage")
    public ResponseEntity<Void> updateTage(InTag tag){
        tagService.updateById(tag);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 查询标签
     * @return
     */
    @GetMapping("/queryTag")
    public ResponseEntity<InTag> queryTag(Long tagId){
        InTag tag = tagService.getById(tagId);
        return ResponseEntity.ok(tag);
    }


    /**
     * 获取所有标签
     * @return
     */
    @GetMapping("/queryAllTag")
    public ResponseEntity<List<InTag>> queryAllTag(){
        List<InTag> tags = tagService.queryAllTag();
        return ResponseEntity.ok(tags);
    }



    //TODO 多标签查询
}
