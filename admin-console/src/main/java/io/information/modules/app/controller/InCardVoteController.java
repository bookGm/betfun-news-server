package io.information.modules.app.controller;


import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InCardVote;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInCardVoteService;
import io.mq.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.HashMap;
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
@Api(value = "/app/card/vote", tags = "APP帖子_投票帖子")
public class InCardVoteController {
    @Autowired
    private IInCardVoteService voteService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private IInCardBaseService baseService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 添加
     */
    @Login
    @PostMapping("/save")
    @ApiOperation(value = "新增投票帖子", httpMethod = "POST")
    @ApiImplicitParam(name = "cardVote", value = "基础和投票帖子信息", required = true)
    public R save(@RequestBody InCardVote cardVote) {
        long cId = IdGenerator.getId();
        InCardBase cardBase = cardVote.getInCardBase();
        cardBase.setcId(cId);
        cardVote.setcId(cId);
        voteService.save(cardVote);
        baseService.save(cardBase);
        rabbitTemplate.convertAndSend(Constants.cardExchange,
                Constants.card_Save_RouteKey, cardVote);
        return R.ok();
    }


    /**
     * 投票
     */
    @Login
    @PostMapping("/vote")
    @ApiOperation(value = "投票", httpMethod = "POST", notes = "根据帖子ID和选择的投票选项，更新投票选项数量")
    @ApiImplicitParam(name = "cid、optIndexs", value = "帖子ID、投票选项索引", dataType = "Long、List<int>")
    public R vote(@RequestParam("cid")Long cid, @RequestParam(value = "optIndexs",required = false) List<Integer> optIndexs, @ApiIgnore @LoginUser InUser user) {
        List<Integer> vote = voteService.vote(cid, user.getuId(), optIndexs);
        return R.ok().put("vote",vote);
    }


    /**
     * 删除
     */
    @Login
    @DeleteMapping("/delete")
    @ApiOperation(value = "单个或批量删除投票帖子", httpMethod = "DELETE", notes = "根据cIds[数组]删除投票帖子")
    @ApiImplicitParam(name = "cIds", value = "帖子ID", dataType = "Array", required = true)
    public R delete(@RequestBody Long[] cIds) {
        voteService.removeByIds(Arrays.asList(cIds));
        rabbitTemplate.convertAndSend(Constants.cardExchange,
                Constants.card_Save_RouteKey, cIds);
        return R.ok();
    }


    /**
     * 修改
     */
    @Login
    @PutMapping("/update")
    @ApiOperation(value = "修改投票帖子", httpMethod = "PUT")
    @ApiImplicitParam(name = "cardVote", value = "投票帖子信息", required = true)
    public R update(@RequestBody InCardVote cardVote) {
        voteService.updateById(cardVote);
        rabbitTemplate.convertAndSend(Constants.cardExchange,
                Constants.card_Update_RouteKey, cardVote);
        return R.ok();

    }


    /**
     * 查询
     */
    @Login
    @GetMapping("/info/{cId}")
    @ApiOperation(value = "查询单个投票帖子", httpMethod = "GET")
    @ApiImplicitParam(name = "cId", value = "帖子ID", required = true)
    public R queryCardVote(@PathVariable("cId") Long cId, @ApiIgnore @LoginUser InUser user) {
        Map<String, Object> map = new HashMap<>();
        //帖子信息
        InCardVote cardVote = voteService.getById(cId);
        //投票信息
        List<Integer> vote = voteService.vote(cId, user.getuId(), null);
        map.put("cardVote", cardVote);
        map.put("voteList", vote);
        return R.ok().put("card", map);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取全部投票帖子", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = voteService.queryPage(map);
        return R.ok().put("page", page);
    }
}
