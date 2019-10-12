package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InMenuSource;
import io.information.modules.app.service.IInMenuSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 资讯菜单资源关系表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-25
 */
@RestController
@RequestMapping("/news/menusource")
public class InMenuSourceController {
    @Autowired
    private IInMenuSourceService menuSourceService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageUtils> list(@RequestParam Map<String, Object> params) {
        PageUtils page = menuSourceService.queryPage(params);

        return ResponseEntity.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{msId}")
    public ResponseEntity<InMenuSource> info(@PathVariable("msId") Long msId) {
        InMenuSource menuSource = menuSourceService.getById(msId);

        return ResponseEntity.ok(menuSource);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody InMenuSource menuSource) {
        menuSourceService.save(menuSource);

        return ResponseEntity.ok().build();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody InMenuSource menuSource) {
        menuSourceService.updateById(menuSource);

        return ResponseEntity.ok().build();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody Long[] msIds) {
        menuSourceService.removeByIds(Arrays.asList(msIds));

        return ResponseEntity.ok().build();
    }
}
