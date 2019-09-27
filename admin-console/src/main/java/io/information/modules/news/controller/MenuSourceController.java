package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.MenuSourceEntity;
import io.information.modules.news.service.MenuSourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 资讯菜单资源关系表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:24
 */
@RestController
@RequestMapping("news/menusource")
public class MenuSourceController {
    @Autowired
    private MenuSourceService menuSourceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:menusource:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = menuSourceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{msId}")
    @RequiresPermissions("news:menusource:info")
    public R info(@PathVariable("msId") Long msId){
		MenuSourceEntity menuSource = menuSourceService.getById(msId);

        return R.ok().put("menuSource", menuSource);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:menusource:save")
    public R save(@RequestBody MenuSourceEntity menuSource){
		menuSourceService.save(menuSource);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:menusource:update")
    public R update(@RequestBody MenuSourceEntity menuSource){
		menuSourceService.updateById(menuSource);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:menusource:delete")
    public R delete(@RequestBody Long[] msIds){
		menuSourceService.removeByIds(Arrays.asList(msIds));

        return R.ok();
    }

}
