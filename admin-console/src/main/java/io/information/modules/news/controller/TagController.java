package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.TagEntity;
import io.information.modules.news.service.TagService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    @RequestMapping("/list")
    @RequiresPermissions("news:tag:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tagService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{tId}")
    @RequiresPermissions("news:tag:info")
    public R info(@PathVariable("tId") Long tId){
		TagEntity tag = tagService.getById(tId);

        return R.ok().put("tag", tag);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:tag:save")
    public R save(@RequestBody TagEntity tag){
		tagService.save(tag);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:tag:update")
    public R update(@RequestBody TagEntity tag){
		tagService.updateById(tag);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:tag:delete")
    public R delete(@RequestBody Long[] tIds){
		tagService.removeByIds(Arrays.asList(tIds));

        return R.ok();
    }

}
