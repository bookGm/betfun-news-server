package io.information.modules.app.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.entity.InActivityFields;
import io.information.modules.app.service.IInActivityFieldsService;
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
@RequestMapping("app/activity/fields")
public class InActivityFieldsController {
    @Autowired
    private IInActivityFieldsService activityFieldsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = activityFieldsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fId}")
    public R info(@PathVariable("fId") Long fId) {
        InActivityFields activityFields = activityFieldsService.getById(fId);

        return R.ok().put("actFields", activityFields);
    }

    /**
     * 保存
     */
    @Login
    @RequestMapping("/save")
    public R save(@RequestBody InActivityFields activityFields) {
        activityFieldsService.save(activityFields);

        return R.ok();
    }

    /**
     * 修改
     */
    @Login
    @RequestMapping("/update")
    public R update(@RequestBody InActivityFields activityFields) {
        activityFieldsService.updateById(activityFields);

        return R.ok();
    }

    /**
     * 删除
     */
    @Login
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] fIds) {
        activityFieldsService.removeByIds(Arrays.asList(fIds));

        return R.ok();
    }

}
