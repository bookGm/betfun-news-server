package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.ActivityEntity;
import io.information.modules.news.service.ActivityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 资讯活动表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@RestController
@RequestMapping("news/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:activity:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = activityService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 用户查询
     */
    @GetMapping("/infoAll/{uId}")
    @RequiresPermissions("news:activity:infoAll")
    public R infoAll(@PathVariable("uId") Long uId){
        PageUtils userActivity = activityService.queryUserActivity(uId);
        return R.ok().put("userActivity", userActivity);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{actId}")
    @RequiresPermissions("news:activity:info")
    public R info(@PathVariable("actId") Long actId){
		ActivityEntity activity = activityService.getById(actId);

        return R.ok().put("activity", activity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:activity:save")
    public R save(@RequestBody ActivityEntity activity){
		activityService.save(activity);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:activity:update")
    public R update(@RequestBody ActivityEntity activity){
		activityService.updateById(activity);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:activity:delete")
    public R delete(@RequestBody Long[] actIds){
		activityService.removeByIds(Arrays.asList(actIds));

        return R.ok();
    }

}
