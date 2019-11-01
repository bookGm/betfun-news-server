package io.information.modules.app.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.entity.InCommonReply;
import io.information.modules.app.service.IInCommonReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 评论回复表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-08 15:11:28
 */
@Api(value = "/app/commonreply", tags = "APP评论与回复")
@RestController
@RequestMapping("/app/commonreply")
public class InCommonReplyController {
    @Autowired
    private IInCommonReplyService commonReplyService;


    /**
     * 信息
     */
    @GetMapping("/info/{crId}")
    @ApiOperation(value = "单个评论信息", httpMethod = "GET")
    @ApiImplicitParam(name = "crId", value = "评论ID", required = true)
    public R info(@PathVariable("crId") Long crId) {
        InCommonReply commonReply = commonReplyService.getById(crId);

        return R.ok().put("commonReply", commonReply);
    }

    /**
     * 保存
     */
    @Login
    @PostMapping("/save")
    @ApiOperation(value = "保存评论信息", httpMethod = "POST")
    @ApiImplicitParam(name = "commonReply", value = "评论信息", required = true)
    public R save(@RequestBody InCommonReply commonReply) {
        commonReply.setCrTime(new Date());
        commonReplyService.save(commonReply);

        return R.ok();
    }


    /**
     * 删除
     */
    @Login
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除评论信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "crId", value = "评论ID", dataType = "Long[ ]", required = true)
    public R delete(Long[] crIds) {
        commonReplyService.removeByIds(Arrays.asList(crIds));
        return R.ok();
    }


    /**
     * 评论列表 <根据 类型和ID信息 查询评论>
     */
    @GetMapping("/discuss")
    @ApiOperation(value = "查询某个页面的评论列表", httpMethod = "GET", notes = "类型[tType] ID[id]")
    @ApiImplicitParam(name = "map", value = "分页数据，目标类型和ID", required = true)
    public R discuss(@RequestParam Map<String, Object> map) {
        PageUtils discuss = commonReplyService.discuss(map);
        return R.ok().put("discuss", discuss);
    }


    /**
     * 回复列表 <根据 评论ID信息 查询回复>
     */
    @GetMapping("/revert")
    @ApiOperation(value = "查询某个评论的回复信息", httpMethod = "GET", notes = "ID[id]")
    @ApiImplicitParam(name = "map", value = "分页数据，评论ID", required = true)
    public R revert(@RequestParam Map<String, Object> map) {
        PageUtils revert = commonReplyService.revert(map);
        return R.ok().put("revert", revert);
    }
}
