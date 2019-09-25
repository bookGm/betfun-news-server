package io.information.modules.app.controller;


import io.information.modules.app.entity.InCardArgue;
import io.information.modules.app.service.IInCardArgueService;
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

    /**
     * 添加辩论帖子
     * @param cardArgue
     * @return
     */
    @PostMapping("/addCardArgue")
    public ResponseEntity<Void> addCardArgue(InCardArgue cardArgue){
        cardArgueService.save(cardArgue);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据帖子ID删除
     * @param cardIds
     * @return
     */
    @DeleteMapping("/deleteCardArgue")
    public ResponseEntity<Void> deleteCardArgue(List<Long> cardIds){
        cardArgueService.removeByIds(cardIds);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改辩论帖子
     * @param cardArgue
     * @return
     */
    @PutMapping("/updateCardArgue")
    public ResponseEntity<Void> updateCardArgue(InCardArgue cardArgue){
        cardArgueService.updateById(cardArgue);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据帖子ID查询
     * @param cardIds
     * @return
     */
    @GetMapping("/queryCardArgue")
    public ResponseEntity<InCardArgue> queryCardArgue(Long cardIds){
        InCardArgue cardArgue = cardArgueService.getById(cardIds);
        return ResponseEntity.ok(cardArgue);
    }

}
