package io.information.modules.news.controller;

import com.guansuo.common.StringUtil;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.ActivityEntity;
import io.information.modules.news.service.ActivityService;
import io.information.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 资讯活动表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-08 17:01:27
 */
@RestController
@RequestMapping("news/activity")
public class ActivityController extends AbstractController {
    @Autowired
    private ActivityService activityService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:activity:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = activityService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 审核
     */
    @RequestMapping("/auditList")
    public R audit(@RequestParam Map<String, Object> params) {
        PageUtils page = activityService.audit(params);
        return R.ok().put("page", page);
    }


    /**
     * 已通过
     */
    @PostMapping("/auditOk")
    @RequiresPermissions("news:activity:update")
    public R auditOk(@RequestBody Map<String, Object> map) {
        if (null != map.get("actId") && StringUtil.isNotBlank(map.get("actId"))) {
            long actId = Long.parseLong(String.valueOf(map.get("actId")));
            ActivityEntity activityEntity = new ActivityEntity();
            activityEntity.setActId(actId);
            activityEntity.setActStatus(2);
            activityService.updateById(activityEntity);
            return R.ok();
        }
        return R.error("缺少必要的参数");
    }


    /**
     * 未通过
     */
    @PostMapping("/auditNo")
    @RequiresPermissions("news:activity:update")
    public R auditNo(@RequestBody Map<String, Object> map) {
        if (null != map.get("actId") && StringUtil.isNotBlank(map.get("actId"))) {
            long actId = Long.parseLong(String.valueOf(map.get("actId")));
            ActivityEntity activityEntity = new ActivityEntity();
            activityEntity.setActId(actId);
            activityEntity.setActStatus(0);
            activityService.updateById(activityEntity);
            return R.ok();
        }
        return R.error("缺少必要的参数");
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{actId}")
    @RequiresPermissions("news:activity:info")
    public R info(@PathVariable("actId") Long actId) {
        ActivityEntity activity = activityService.getById(actId);

        return R.ok().put("activity", activity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:activity:save")
    public R save(@RequestBody ActivityEntity activity) {
        activity.setuId(getUserId());
        activity.setActCreateTime(new Date());
        activityService.save(activity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:activity:update")
    public R update(@RequestBody ActivityEntity activity) {
        activityService.updateById(activity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:activity:delete")
    public R delete(@RequestBody Long[] actIds) {
        activityService.removeByIds(Arrays.asList(actIds));

        return R.ok();
    }

}
