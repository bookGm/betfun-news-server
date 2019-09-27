package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.MenuEntity;
import io.information.modules.news.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 资讯菜单表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@RestController
@RequestMapping("news/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:menu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = menuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{mId}")
    @RequiresPermissions("news:menu:info")
    public R info(@PathVariable("mId") Long mId){
		MenuEntity menu = menuService.getById(mId);

        return R.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:menu:save")
    public R save(@RequestBody MenuEntity menu){
		menuService.save(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:menu:update")
    public R update(@RequestBody MenuEntity menu){
		menuService.updateById(menu);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:menu:delete")
    public R delete(@RequestBody Long[] mIds){
		menuService.removeByIds(Arrays.asList(mIds));

        return R.ok();
    }

}
