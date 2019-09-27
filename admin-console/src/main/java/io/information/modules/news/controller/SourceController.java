package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.SourceEntity;
import io.information.modules.news.service.SourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 资讯资源表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@RestController
@RequestMapping("news/source")
public class SourceController {
    @Autowired
    private SourceService sourceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:source:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sourceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{sUrl}")
    @RequiresPermissions("news:source:info")
    public R info(@PathVariable("sUrl") String sUrl){
		SourceEntity source = sourceService.getById(sUrl);

        return R.ok().put("source", source);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:source:save")
    public R save(@RequestBody SourceEntity source){
		sourceService.save(source);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:source:update")
    public R update(@RequestBody SourceEntity source){
		sourceService.updateById(source);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:source:delete")
    public R delete(@RequestBody String[] sUrls){
		sourceService.removeByIds(Arrays.asList(sUrls));

        return R.ok();
    }

}