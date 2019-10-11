package io.information.modules.app.controller;


import io.information.common.utils.IdGenerator;
import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInUserService;
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
    @Autowired
    private IInUserService iInUserService;

    /**
     * 添加基础帖子
     * @param cardBase
     * @return
     */
    @PostMapping("/addCardBase")
    public ResponseEntity<Void> addCardBase(InCardBase cardBase){
        Long id= IdGenerator.getId();
        cardBase.setcId(id);
        cardBaseService.save(cardBase);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除基础信息
     * @param cardIds
     * @return
     */
    @DeleteMapping("/deleteCardBase")
    public ResponseEntity<Void> deleteCardBase(List<Long> cardIds){
        cardBaseService.removeByIds(cardIds);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



    /**
     * 用户ID删除帖子基础信息
     * @param userId
     * @return
     */
    @DeleteMapping("/deleteAllCardBase")
    public ResponseEntity<Void> deleteAllCardBase(Long userId){
        cardBaseService.deleteAllCardBase(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据用户ID删除帖子所有信息<包含关联表>
     * @param userId
     * @return
     */
    @DeleteMapping("/deleteAllCard")
    public ResponseEntity<Void> deleteAllCard(Long userId){
        cardBaseService.deleteAllCard(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改基础帖子
     * @param cardBase
     * @return
     */
    @PutMapping("/updateCardBase")
    public ResponseEntity<Void> updateCardBase(InCardBase cardBase){
        cardBaseService.updateById(cardBase);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 根据帖子ID查询基础帖子
     * @param cardIds
     * @return
     */
    @GetMapping("/queryCardBase")
    public ResponseEntity<InCardBase> queryCardBase(Long cardIds){
        InCardBase cardBase = cardBaseService.getById(cardIds);
        return ResponseEntity.ok(cardBase);
    }


    /**
     * 根据帖子ID查询帖子<包含关联表>
     * @param cardId
     * @return
     */
    @GetMapping("/queryCard")
    public ResponseEntity<InCard> queryCard(Long cardId){
        InCard card = cardBaseService.queryCard(cardId);
        return ResponseEntity.ok(card);
    }


    /**
     * 根据用户ID查询基础帖子
     * @param userId
     * @return
     */
    @GetMapping("/queryAllCardBase")
    public ResponseEntity<List<InCardBase>> queryAllCardBase(Long userId){
        List<InCardBase> cardBaseList = cardBaseService.queryAllCardBase(userId);
        return ResponseEntity.ok(cardBaseList);
    }


    /**
     * 根据用户ID查询所有帖子<包含关联表>
     * @param userId // ERROR
     * @return
     */
    @GetMapping("/queryAllCard")
    public ResponseEntity<List<InCard>> queryAllCard(Long userId){
        List<InCard> cards = cardBaseService.queryAllCard(userId);
        return ResponseEntity.ok(cards);
    }


    /**
     * 根据正反方ids字符串,查询用户信息
     * @param userIds
     * @return
     */
    @GetMapping("/queryUserList")
    public ResponseEntity<List<InUser>> queryUsersByArgueIds(String userIds){
        List<InUser> userList = iInUserService.queryUsersByArgueIds(userIds);
        return ResponseEntity.ok(userList);
    }
}
