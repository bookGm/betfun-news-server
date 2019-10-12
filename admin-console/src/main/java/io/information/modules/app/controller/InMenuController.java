package io.information.modules.app.controller;


import io.information.modules.app.entity.InMSource;
import io.information.modules.app.entity.InMenu;
import io.information.modules.app.entity.InMenuSource;
import io.information.modules.app.entity.InSource;
import io.information.modules.app.service.IInMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资讯菜单表 前端控制器
 * </p>
 *  TODO
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/news/menu")
public class InMenuController {
    @Autowired
    private IInMenuService menuService;

    /**
     * 添加<包含关联表>
     */
    @PostMapping("/saveList")
    public ResponseEntity<Void> saveList(InMenu menu, InMenuSource menuSource, InSource source){
        menuService.addMenu(menu,menuSource,source);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 添加<包含关联表>
     */
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody InMenu menu){
        menuService.save(menu);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete/{mId}")
    public ResponseEntity<Void> delete(@PathVariable("mId") Long mId){
        menuService.deleteMenu(mId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改<包含关联表>
     */
    @PutMapping("/updateList")
    public ResponseEntity<Void> updateList(InMenu menu, InMenuSource menuSource, InSource source){
        menuService.updateMenu(menu,menuSource,source);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody InMenu menu){
        menuService.updateById(menu);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据ID查询咨讯菜单
     * @param menuId
     * @return
     */
    @GetMapping("/queryMenuById")
    public ResponseEntity<InMSource> queryMenuById(Long menuId){
        InMSource mSource = menuService.queryMenuById(menuId);
        return ResponseEntity.ok(mSource);
    }


    /**
     * 根据菜单名称模糊查询咨讯菜单
     * @param menuName
     * @return
     */
    @GetMapping("/queryLikeMenu")
    public ResponseEntity<InMSource> queryLikeMenu(String menuName){
        InMSource mSource = menuService.queryLikeMenu(menuName);
        return ResponseEntity.ok(mSource);
    }


    /**
     * 获取所有咨讯菜单
     * @return
     */
    @GetMapping("/queryAllMenu")
    public ResponseEntity<List<InMSource>> queryAllMenu(){
        List<InMSource> mSourceList = menuService.queryAllMenu();
        return ResponseEntity.ok(mSourceList);
    }
}
