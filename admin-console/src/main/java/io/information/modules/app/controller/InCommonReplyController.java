package io.information.modules.app.controller;

import io.information.common.utils.R;
import io.information.modules.app.entity.InCommonReply;
import io.information.modules.app.service.IInCommonReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 评论回复表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-08 15:11:28
 */
@RestController
@RequestMapping("/app/commonreply")
public class InCommonReplyController {
    @Autowired
    private IInCommonReplyService commonReplyService;


    /**
     * 信息
     */
    @GetMapping("/info/{crId}")
    public R info(@PathVariable("crId") Long crId) {
        InCommonReply commonReply = commonReplyService.getById(crId);

        return R.ok().put("commonReply", commonReply);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody InCommonReply commonReply) {
        commonReplyService.save(commonReply);

        return R.ok();
    }


    /**
     * 查询<根据 类型和ID信息 查询评论>
     * <p>
     * 前端应在显示某一个页面 ex:帖子 文章 时，将其信息传入？
     */
    @GetMapping("/search")
    public R search(@RequestParam Map<String, Object> map) {
        commonReplyService.search(map);
        return R.ok();
    }

}
