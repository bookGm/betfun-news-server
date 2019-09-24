package io.information.modules.app.controller;


import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.service.IInCardBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资讯帖子基础表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/news/card/base")
public class InCardBaseController {
    @Autowired
    private IInCardBaseService cardBaseService;

    /**
     * 添加基础帖子
     * @param cardBase
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Void> addCardBase(InCardBase cardBase){
        cardBase.setCId(new IdWorker().nextId());
        cardBaseService.save(cardBase);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据基础帖子ID删除
     * @param cardBaseIds
     * @return
     */
    @DeleteMapping("/cardBaseId")
    public ResponseEntity<Void> deleteCardBase(List<Long> cardBaseIds){
        cardBaseService.removeByIds(cardBaseIds);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据基础用户ID删除帖子
     * @param userId
     * @return
     */
    @DeleteMapping("/userId")
    public ResponseEntity<Void> deleteAllCardBase(Long userId){
        cardBaseService.deleteAllCardBase(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改基础帖子
     * @param cardBase
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Void> updateCardBase(InCardBase cardBase){
        cardBaseService.updateById(cardBase);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据基础帖子ID查询
     * @param cardBaseId
     * @return
     */
    @GetMapping("/cardBaseId")
    public ResponseEntity<InCardBase> queryCardBase(Long cardBaseId){
        InCardBase cardBase = cardBaseService.getById(cardBaseId);
        return ResponseEntity.ok(cardBase);
    }


    /**
     * 根据基础用户ID查询
     * @param userId
     * @return
     */
    @GetMapping("/userId")
    public ResponseEntity<List<InCardBase>> queryAllCardBase(Long userId){
        List<InCardBase> cardBaseList = cardBaseService.queryAllCardBase(userId);
        return ResponseEntity.ok(cardBaseList);
    }


    //TODO 帖子节点分类<字典>
}
