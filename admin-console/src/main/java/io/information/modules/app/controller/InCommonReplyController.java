package io.information.modules.app.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.entity.InCommonReply;
import io.information.modules.app.service.IInCommonReplyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RestController
@RequestMapping("/app/commonreply")
public class InCommonReplyController {
    @Autowired
    private IInCommonReplyService commonReplyService;


    /**
     * 信息
     */
    @GetMapping("/info/{crId}")
    public R info(@PathVariable("crId") Long crId){
		InCommonReply commonReply = commonReplyService.getById(crId);

        return R.ok().put("commonReply", commonReply);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody InCommonReply commonReply){
		commonReplyService.save(commonReply);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody InCommonReply commonReply){
		commonReplyService.updateById(commonReply);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] crIds){
		commonReplyService.removeByIds(Arrays.asList(crIds));

        return R.ok();
    }


    /**
     * 查询<根据 被评论或回复id 查询评论>
     *
     *     前端应在显示某一个页面 ex:帖子 文章 时，将其ID传入
     */
    @GetMapping("/search")
    public R search(Long ToCrId){
        commonReplyService.search(ToCrId);
        return R.ok();
    }

}
