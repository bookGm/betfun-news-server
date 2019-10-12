package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InSource;
import io.information.modules.app.service.IInSourceService;
import io.information.modules.news.entity.SourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯资源表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/news/source")
public class InSourceController {
    @Autowired
    private IInSourceService sourceService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageUtils> list(@RequestParam Map<String, Object> params) {
        PageUtils page = sourceService.queryPage(params);

        return ResponseEntity.ok(page);
    }


    /**
     * 角色列表
     */
    @GetMapping("/select")
    public ResponseEntity<List<InSource>> select() {
        Map<String, Object> map = new HashMap<>();

        List<InSource> list = (List<InSource>) sourceService.listByMap(map);

        return ResponseEntity.ok(list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{sUrl}")
    public ResponseEntity<InSource> info(@PathVariable("sUrl") String sUrl) {
        InSource source = sourceService.getByUrl(sUrl);

        return ResponseEntity.ok(source);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody InSource source) {
        sourceService.save(source);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public ResponseEntity<Void> update(@RequestBody InSource source) {
        sourceService.updateByUrl(source);

        return ResponseEntity.ok().build();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody String[] sUrls) {
        sourceService.removeByUrl(Arrays.asList(sUrls));
        return ResponseEntity.ok().build();
    }
}
