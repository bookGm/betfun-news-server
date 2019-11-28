package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.service.NewsFlashService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/news/newsflash")
public class NewsFlashController {
    @Autowired
    private NewsFlashService newsFlashService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:newsflash:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = newsFlashService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:newsflash:delete")
    public R delete(@RequestBody Long[] nIds) {
        newsFlashService.removeByIds(Arrays.asList(nIds));
        return R.ok();
    }
}
