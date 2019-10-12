package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCardVote;
import io.information.modules.app.service.IInCardVoteService;
import io.information.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
public class InCardVoteController extends AbstractController {
    @Autowired
    private IInCardVoteService cardVoteService;

    /**
     * 添加
     */
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody InCardVote cardVote){
        cardVoteService.save(cardVote);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 投票
     */
    @PostMapping("/vote")
    public ResponseEntity<Void> vote(Long cid,List<Integer> optIndexs){
        for(Integer optIndex:optIndexs){
            cardVoteService.vote(cid,getUserId(),optIndex);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody Long[] cIds){
        cardVoteService.removeByIds(Arrays.asList(cIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody InCardVote cardVote){
        cardVoteService.updateById(cardVote);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 查询
     */
    @GetMapping("/info/{cId}")
    public ResponseEntity<InCardVote> queryCardVote(@PathVariable("cId") Long cId){
        InCardVote cardArgue = cardVoteService.getById(cId);
        return ResponseEntity.ok(cardArgue);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageUtils> list(@RequestParam Map<String, Object> params) {
        PageUtils page = cardVoteService.queryPage(params);
        return ResponseEntity.ok(page);
    }
}
