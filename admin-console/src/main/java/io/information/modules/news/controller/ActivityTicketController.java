package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.ActivityTicketEntity;
import io.information.modules.news.service.ActivityTicketService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 活动票
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 13:32:16
 */
@RestController
@RequestMapping("news/activity/ticket")
public class ActivityTicketController {
    @Autowired
    private ActivityTicketService activityTicketService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:actTicket:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = activityTicketService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{tId}")
    @RequiresPermissions("news:actTicket:info")
    public R info(@PathVariable("tId") Long tId) {
        ActivityTicketEntity activityTicket = activityTicketService.getById(tId);

        return R.ok().put("actTicket", activityTicket);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:actTicket:save")
    public R save(@RequestBody ActivityTicketEntity activityTicket) {
        activityTicketService.save(activityTicket);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:actTicket:update")
    public R update(@RequestBody ActivityTicketEntity activityTicket) {
        activityTicketService.updateById(activityTicket);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:actTicket:delete")
    public R delete(@RequestBody Long[] tIds) {
        activityTicketService.removeByIds(Arrays.asList(tIds));

        return R.ok();
    }

}
