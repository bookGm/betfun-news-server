package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.entity.InMenu;
import io.information.modules.app.entity.InMenus;
import io.information.modules.app.service.IInMenuService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯菜单表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/menu")
public class InMenuController {
    @Autowired
    private IInMenuService menuService;

    /**
     * 添加
     */
    @PostMapping("/save")
    public R save(@RequestBody InMenu menu) {
        menuService.save(menu);
        return R.ok();
    }

    /**
     * 添加<包含关联表>
     */
    @PostMapping("/saveList")
    public R saveList(@RequestBody InMenus menus) {
        menuService.addMenu(menus);
        return R.ok();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete/{mId}")
    public R delete(@PathVariable("mId") Long mId) {
        menuService.deleteMenu(mId);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/deleteAll/{mId}")
    public R deleteAll(@PathVariable("mId") Long mId) {
        menuService.deleteAll(mId);
        return R.ok();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody InMenu menu) {
        menuService.updateById(menu);
        return R.ok();
    }

    /**
     * 修改<包含关联表>
     */
    @PutMapping("/updateList")
    public R updateList(@RequestBody InMenus menus) {
        menuService.updateMenu(menus);
        return R.ok();
    }


    /**
     * 查询
     */
    @GetMapping("/info/{mId}")
    @ApiOperation(value = "查询单个咨询菜单", httpMethod = "GET")
    @ApiImplicitParam(name = "mId", value = "菜单信ID", required = true)
    public R info(@PathVariable("mId") Long mId) {
        InMenus menus = menuService.queryMenuById(mId);
        return R.ok().put("menus",menus);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取所有咨询菜单", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = menuService.queryPage(map);
        return R.ok().put("page",page);
    }

    /**
     * 递归
     * @param total
     * @param menu
     */
    public void getMenuTree(List<InMenu> total,InMenu menu){
        List<InMenu> tlist= new ArrayList<InMenu>(total);
        List<InMenu> clist=new ArrayList<InMenu>();
        Iterator<InMenu> iterator = tlist.iterator();
        while (iterator.hasNext()) {
            InMenu m = iterator.next();
            if (m.getmPcode().equals(menu.getmCode())) {
                clist.add(m);
                iterator.remove();
                getMenuTree(tlist,m);
            }
        }
        if(clist.size()<1){
            clist=null;
        }else{
            menu.setChildren(clist);
        }
    }

    /**
     * 获取所有咨讯菜单
     * @return
     */
    @GetMapping("getAllMenu")
    public R getAllMenu(){
        InMenu menu =new InMenu();
        menu.setmCode("0");
        getMenuTree(menuService.list(),menu);
        return R.ok().put("menus",menu.getChildren());
    }
}
