package io.information.modules.news.controller;

import io.information.common.utils.IdGenerator;
import io.information.common.utils.R;
import io.information.modules.news.entity.MenuEntity;
import io.information.modules.news.service.MenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


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
    public List<MenuEntity> list() {
        List<MenuEntity> menuEntities = menuService.list();
        for (MenuEntity menuEntity : menuEntities) {
            MenuEntity parentMenuEntity = menuService.getByCode(menuEntity.getmPcode());
            if (parentMenuEntity != null) {
                menuEntity.setmPname(parentMenuEntity.getmName());
            }
        }

        return menuEntities;
    }


    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public R select(){
        //查询列表数据
        List<MenuEntity> menuList = menuService.list();

        //添加顶级菜单
        MenuEntity root = new MenuEntity();
        //TODO  参数类型
        root.setmCode("0");
        root.setmName("一级菜单");
        root.setmPcode("-1");
        root.setOpen(true);
        menuList.add(root);

        return R.ok().put("menuList", menuList);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{mId}")
    @RequiresPermissions("news:menu:info")
    public R info(@PathVariable("mId") Long mId) {
        MenuEntity menu = menuService.getById(mId);

        return R.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:menu:save")
    public R save(@RequestBody MenuEntity menu) {
        menu.setmCode(IdGenerator.getId()+"");
        menuService.save(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:menu:update")
    public R update(@RequestBody MenuEntity menu) {
        menuService.updateById(menu);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{mId}")
    @RequiresPermissions("news:menu:delete")
    public R delete(@PathVariable("mId") Long mId) {
        menuService.removeById(mId);

        return R.ok();
    }

}
