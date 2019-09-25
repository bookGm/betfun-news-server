package io.information.modules.app.controller;


import io.information.modules.app.entity.InCardVote;
import io.information.modules.app.service.IInCardVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资讯投票帖详情（最多30个投票选项） 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/news/card/vote")
public class InCardVoteController {
    @Autowired
    private IInCardVoteService cardVoteService;

    /**
     * 添加投票贴
     * @param cardVote
     * @return
     */
    @PostMapping("/addCardVote")
    public ResponseEntity<Void> add(InCardVote cardVote){
        cardVoteService.save(cardVote);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除投票贴子
     * @param cardIds
     * @return
     */
    @DeleteMapping("/deleteCardVote")
    public ResponseEntity<Void> delete(List<Long> cardIds){
        cardVoteService.removeByIds(cardIds);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改投票帖子
     * @param cardVote
     * @return
     */
    @PutMapping("/updateCardVote")
    public ResponseEntity<Void> updateCardVote(InCardVote cardVote){
        cardVoteService.updateById(cardVote);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据帖子ID查询
     * @param cardIds
     * @return
     */
    @GetMapping("/queryCardVote")
    public ResponseEntity<InCardVote> queryCardVote(Long cardIds){
        InCardVote cardArgue = cardVoteService.getById(cardIds);
        return ResponseEntity.ok(cardArgue);
    }

}
