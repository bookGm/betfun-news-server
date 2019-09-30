package io.information.modules.news.controller;

import io.information.common.annotation.SysLog;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.common.validator.ValidatorUtils;
import io.information.modules.news.entity.TagEntity;
import io.information.modules.news.service.TagService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;



/**
 * 
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@RestController
@RequestMapping("news/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:tag:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tagService.queryPage(params);

        return R.ok().put("page", page);
    }
 

    /**
     * 信息
     */
    @GetMapping("/info/{tId}")
    @RequiresPermissions("news:tag:info")
    public R info(@PathVariable("tId") Long tId){
		TagEntity tag = tagService.getById(tId);

        return R.ok().put("tag", tag);
    }

    /**
     * 保存
     */
    @SysLog("保存标签")
    @PostMapping("/save")
    @RequiresPermissions("news:tag:save")
    public R save(@RequestBody TagEntity tag){
        ValidatorUtils.validateEntity(tag);
		tagService.saveTag(tag);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:tag:update")
    public R update(@RequestBody TagEntity tag){
        ValidatorUtils.validateEntity(tag);
		tagService.updateById(tag);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:tag:delete")
    public R delete(@RequestBody Long[] tIds){
        Long tId = tIds[0];
        tagService.delete(tIds);

        return R.ok();
    }

}
