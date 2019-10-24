package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.ActivityFieldsEntity;
import io.information.modules.news.service.ActivityFieldsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 资讯活动动态表单属性
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 10:53:16
 */
@RestController
@RequestMapping("news/activityfields")
public class ActivityFieldsController {
    @Autowired
    private ActivityFieldsService activityFieldsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:activityfields:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = activityFieldsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fId}")
    @RequiresPermissions("news:activityfields:info")
    public R info(@PathVariable("fId") Long fId){
		ActivityFieldsEntity activityFields = activityFieldsService.getById(fId);

        return R.ok().put("activityFields", activityFields);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:activityfields:save")
    public R save(@RequestBody ActivityFieldsEntity activityFields){
		activityFieldsService.save(activityFields);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:activityfields:update")
    public R update(@RequestBody ActivityFieldsEntity activityFields){
		activityFieldsService.updateById(activityFields);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:activityfields:delete")
    public R delete(@RequestBody Long[] fIds){
		activityFieldsService.removeByIds(Arrays.asList(fIds));

        return R.ok();
    }

}
