package io.information.modules.app.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.entity.InActivityDatas;
import io.information.modules.app.service.IInActivityDatasService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 资讯活动动态表单数据
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 11:03:00
 */
@RestController
@RequestMapping("news/activitydatas")
public class InActivityDatasController {
    @Autowired
    private IInActivityDatasService activityDatasService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:activitydatas:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = activityDatasService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dId}")
    @RequiresPermissions("news:activitydatas:info")
    public R info(@PathVariable("dId") Long dId) {
        InActivityDatas activityDatas = activityDatasService.getById(dId);

        return R.ok().put("activityDatas", activityDatas);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:activitydatas:save")
    public R save(@RequestBody InActivityDatas activityDatas) {
        activityDatasService.save(activityDatas);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:activitydatas:update")
    public R update(@RequestBody InActivityDatas activityDatas) {
        activityDatasService.updateById(activityDatas);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:activitydatas:delete")
    public R delete(@RequestBody Long[] dIds) {
        activityDatasService.removeByIds(Arrays.asList(dIds));

        return R.ok();
    }

}
