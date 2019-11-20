package io.information.modules.news.controller;

import io.information.common.annotation.SysLog;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.common.validator.ValidatorUtils;
import io.information.modules.app.config.IdWorker;
import io.information.modules.news.dto.TagDTO;
import io.information.modules.news.entity.TagEntity;
import io.information.modules.news.service.TagService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
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
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = tagService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{tId}")
    @RequiresPermissions("news:tag:info")
    public R info(@PathVariable("tId") Long tId) {
        TagEntity tag = tagService.getById(tId);

        return R.ok().put("tag", tag);
    }

    /**
     * 保存
     */
    @SysLog("保存标签")
    @PostMapping("/save")
    @RequiresPermissions("news:tag:save")
    public R save(@RequestBody TagEntity tag) {
        tag.settId(new IdWorker().nextId());
        tag.settCreateTime(new Date());
        ValidatorUtils.validateEntity(tag);
        tagService.save(tag);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:tag:update")
    public R update(@RequestBody TagEntity tag) {
        ValidatorUtils.validateEntity(tag);
        tagService.updateById(tag);

        return R.ok();
    }

    /**
     * 批量设置标签分类
     */
    @PostMapping("/batchType")
    @RequiresPermissions("news:tag:update")
    public R batchType(@RequestBody TagDTO tagDTO) {
        if (null != tagDTO.gettIds()) {
            for (Long tId : tagDTO.gettIds()) {
                TagEntity tag = new TagEntity();
                tag.settId(tId);
                tag.settCategory(tagDTO.gettCategory());
                tagService.updateById(tag);
            }
            return R.ok();
        } else {
            return R.error("没有选中任何数据");
        }
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:tag:delete")
    public R delete(@RequestBody Long[] tIds) {
        tagService.removeByIds(Arrays.asList(tIds));

        return R.ok();
    }

}
