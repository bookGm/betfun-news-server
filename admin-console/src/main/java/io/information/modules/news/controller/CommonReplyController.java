package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.CommonReplyEntity;
import io.information.modules.news.service.CommonReplyService;
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
@RequestMapping("news/commonreply")
public class CommonReplyController {
    @Autowired
    private CommonReplyService commonReplyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:commonreply:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = commonReplyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{crId}")
    @RequiresPermissions("news:commonreply:info")
    public R info(@PathVariable("crId") Long crId){
		CommonReplyEntity commonReply = commonReplyService.getById(crId);

        return R.ok().put("commonReply", commonReply);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:commonreply:save")
    public R save(@RequestBody CommonReplyEntity commonReply){
		commonReplyService.save(commonReply);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:commonreply:update")
    public R update(@RequestBody CommonReplyEntity commonReply){
		commonReplyService.updateById(commonReply);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:commonreply:delete")
    public R delete(@RequestBody Long[] crIds){
		commonReplyService.removeByIds(Arrays.asList(crIds));

        return R.ok();
    }


    /**
     * 查询
     */
    public R search(Long crIds){
        commonReplyService.search(crIds);
        return R.ok();
    }

}
