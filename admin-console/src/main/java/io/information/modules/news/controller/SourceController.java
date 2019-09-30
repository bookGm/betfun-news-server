package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.SourceEntity;
import io.information.modules.news.service.SourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    @GetMapping("/list")
    @RequiresPermissions("news:source:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sourceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 角色列表
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select(){
        Map<String, Object> map = new HashMap<>();

        List<SourceEntity> list = (List<SourceEntity>) sourceService.listByMap(map);

        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{sUrl}")
    @RequiresPermissions("news:source:info")
    public R info(@PathVariable("sUrl") String sUrl){
		SourceEntity source = sourceService.getByUrl(sUrl);

        return R.ok().put("source", source);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
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
		sourceService.updateByUrl(source);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:source:delete")
    public R delete(@RequestBody String[] sUrls){
		sourceService.removeByUrl(Arrays.asList(sUrls));

        return R.ok();
    }

}
