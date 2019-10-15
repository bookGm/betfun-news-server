package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InMenu;
import io.information.modules.app.entity.InMenus;
import io.information.modules.app.service.IInMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@Api(value = "APP咨讯菜单接口<关联资源接口>")
public class InMenuController {
    @Autowired
    private IInMenuService menuService;

    /**
     * 添加
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增咨询菜单")
    public ResponseEntity<Void> save(@RequestBody InMenu menu) {
        menuService.save(menu);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 添加<包含关联表>
     */
    @ApiOperation(value = "新增咨询菜单、资讯资源", httpMethod = "POST")
    @ApiImplicitParam(name = "menus", value = "菜单资源信息", required = true)
    @PostMapping("/saveList")
    public ResponseEntity<Void> saveList(@RequestBody InMenus menus) {
        menuService.addMenu(menus);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete/{mId}")
    @ApiOperation(value = "单个删除咨询菜单", httpMethod = "DELETE")
    @ApiImplicitParam(name = "mId", value = "菜单ID", required = true)
    public ResponseEntity<Void> delete(@PathVariable("mId") Long mId) {
        menuService.deleteMenu(mId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/deleteAll/{mId}")
    @ApiOperation(value = "删除咨询菜单、资讯资源", httpMethod = "DELETE")
    @ApiImplicitParam(name = "mId", value = "菜单ID", required = true)
    public ResponseEntity<Void> deleteAll(@PathVariable("mId") Long mId) {
        menuService.deleteAll(mId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改咨询菜单", httpMethod = "PUT")
    @ApiImplicitParam(name = "menu", value = "菜单信息", required = true)
    public ResponseEntity<Void> update(@RequestBody InMenu menu) {
        menuService.updateById(menu);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 修改<包含关联表>
     */
    @PutMapping("/updateList")
    @ApiOperation(value = "修改咨询菜单、资讯资源", httpMethod = "PUT")
    @ApiImplicitParam(name = "menus", value = "菜单信息、资源信息", required = true)
    public ResponseEntity<Void> updateList(@RequestBody InMenus menus) {
        menuService.updateMenu(menus);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 查询
     */
    @GetMapping("/info/{mId}")
    @ApiOperation(value = "查询单个咨询菜单", httpMethod = "GET")
    @ApiImplicitParam(name = "mId", value = "菜单信ID", required = true)
    public ResponseEntity<InMenus> info(@PathVariable("mId") Long mId) {
        InMenus mSource = menuService.queryMenuById(mId);
        return ResponseEntity.ok(mSource);
    }

    /**
     * 名称模糊查询
     */
    @GetMapping("/infoName/{mName}")
    @ApiOperation(value = "名称模糊查询咨询菜单", httpMethod = "GET")
    @ApiImplicitParam(name = "mName", value = "菜单大致名称", required = true)
    public ResponseEntity<InMenus> infoName(@PathVariable("mName") String mName) {
        InMenus mSource = menuService.queryLikeMenu(mName);
        return ResponseEntity.ok(mSource);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取所有咨询菜单", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public ResponseEntity<PageUtils> list(@RequestParam Map<String, Object> map) {
        PageUtils page = menuService.queryPage(map);
        return ResponseEntity.ok(page);
    }
}
