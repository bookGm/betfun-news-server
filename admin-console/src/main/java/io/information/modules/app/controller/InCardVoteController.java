package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCardVote;
import io.information.modules.app.service.IInCardVoteService;
import io.information.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/app/card/vote")
@Api(value = "APP帖子_投票帖子")
public class InCardVoteController extends AbstractController {
    @Autowired
    private IInCardVoteService cardVoteService;

    /**
     * 添加
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增投票帖子",httpMethod = "POST")
    @ApiImplicitParam(name = "cardVote",value = "投票帖子信息",required = true)
    public ResponseEntity<Void> save(@RequestBody InCardVote cardVote){
        cardVoteService.save(cardVote);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 投票
     */
    @PostMapping("/vote")
    @ApiOperation(value = "投票",httpMethod = "POST",notes = "根据帖子ID和选择的投票选项，更新投票选项数量")
    @ApiImplicitParam(name = "cid、optIndexs",value = "帖子ID、投票选项索引",dataType = "Long、List<int>")
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
    @ApiOperation(value = "单个或批量删除投票帖子",httpMethod = "DELETE",notes = "根据cIds[数组]删除投票帖子")
    @ApiImplicitParam(name = "cIds",value = "帖子ID",dataType = "Array",required = true)
    public ResponseEntity<Void> delete(@RequestBody Long[] cIds){
        cardVoteService.removeByIds(Arrays.asList(cIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改投票帖子",httpMethod = "PUT")
    @ApiImplicitParam(name = "cardVote",value = "投票帖子信息",required = true)
    public ResponseEntity<Void> update(@RequestBody InCardVote cardVote){
        cardVoteService.updateById(cardVote);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 查询
     */
    @GetMapping("/info/{cId}")
    @ApiOperation(value = "查询单个投票帖子",httpMethod = "GET")
    @ApiImplicitParam(name = "cId",value = "帖子ID",required = true)
    public ResponseEntity<InCardVote> queryCardVote(@PathVariable("cId") Long cId){
        InCardVote cardArgue = cardVoteService.getById(cId);
        return ResponseEntity.ok(cardArgue);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取全部投票帖子",httpMethod = "GET")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public ResponseEntity<PageUtils> list(@RequestParam Map<String, Object> map) {
        PageUtils page = cardVoteService.queryPage(map);
        return ResponseEntity.ok(page);
    }
}
