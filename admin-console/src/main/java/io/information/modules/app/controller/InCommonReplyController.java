package io.information.modules.app.controller;

import io.information.common.utils.R;
import io.information.modules.app.entity.InCommonReply;
import io.information.modules.app.service.IInCommonReplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    @PostMapping("/save")
    @ApiOperation(value = "保存评论信息", httpMethod = "POST")
    @ApiImplicitParam(name = "commonReply", value = "评论信息", required = true)
    public R save(@RequestBody InCommonReply commonReply) {
        commonReplyService.save(commonReply);

        return R.ok();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除评论信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "crId", value = "评论ID",dataType = "Long[ ]",required = true)
    public R delete(Long[] crIds) {
        commonReplyService.removeByIds(Arrays.asList(crIds));
        return R.ok();
    }


    /**
     * 查询 <根据 类型和ID信息 查询评论>
     *
     *  前端应在显示某一个页面 ex:帖子 文章 时，将其信息传入？
     */
    @GetMapping("/search")
    @ApiOperation(value = "查询某个页面的评论列表", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "类型和ID信息", required = true)
    public R search(@RequestParam Map<String, Object> map) {
        commonReplyService.search(map);
        return R.ok();
    }

}
