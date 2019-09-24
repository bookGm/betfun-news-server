package io.information.modules.app.controller;


import io.information.modules.app.entity.InCardArgue;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardArgueService;
import io.information.modules.app.service.IInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资讯帖子辩论表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/news/card/argue")
public class InCardArgueController {
    @Autowired
    private IInCardArgueService cardArgueService;
    @Autowired
    private IInUserService userService;

    /**
     * 添加辩论帖子
     * @param cardArgue
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Void> addCardArgue(InCardArgue cardArgue){
        cardArgueService.save(cardArgue);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据辩论帖子ID删除
     * @param cardArgueIds
     * @return
     */
    @DeleteMapping("/cardArgueId")
    public ResponseEntity<Void> deleteCardArgue(List<Long> cardArgueIds){
        cardArgueService.removeByIds(cardArgueIds);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改辩论帖子
     * @param cardArgue
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Void> updateCardArgue(InCardArgue cardArgue){
        cardArgueService.updateById(cardArgue);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据辩论帖子ID查询
     * @param cardArgueId
     * @return
     */
    @GetMapping("/cardArgueId")
    public ResponseEntity<InCardArgue> queryCardArgue(Long cardArgueId){
        InCardArgue cardArgue = cardArgueService.getById(cardArgueId);
        return ResponseEntity.ok(cardArgue);
    }


    /**
     * 根据正反方ids字符串,查询用户信息
     * @param userIds
     * @return
     */
    @GetMapping("/userIds")
    public ResponseEntity<List<InUser>> queryUsersByArgueIds(String userIds){
        List<InUser> userList = userService.queryUsersByArgueIds(userIds);
        return ResponseEntity.ok(userList);
    }

}
