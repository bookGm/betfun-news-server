package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InSource;
import io.information.modules.app.service.IInSourceService;
import io.information.modules.news.entity.SourceEntity;
import io.information.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/app/source")
@Api(value = "APP咨讯资源接口<关联菜单接口>")
public class InSourceController extends AbstractController {
    @Autowired
    private IInSourceService sourceService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取全部资源",httpMethod = "GET")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public ResponseEntity<PageUtils> list(@RequestParam Map<String, Object> map) {
        PageUtils page = sourceService.queryPage(map);

        return ResponseEntity.ok(page);
    }


    /**
     * 角色列表
     */
    @GetMapping("/select")
    @ApiOperation(value = "",httpMethod = "GET")
    public ResponseEntity<List<InSource>> select() {
        Map<String, Object> map = new HashMap<>();

        List<InSource> list = (List<InSource>) sourceService.listByMap(map);

        return ResponseEntity.ok(list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{sUrl}")
    @ApiOperation(value = "查询资源",httpMethod = "GET",notes = "根据sUrl查询资源")
    @ApiImplicitParam(name = "sUrl",value = "资源路径",required = true)
    public ResponseEntity<InSource> info(@PathVariable("sUrl") String sUrl) {
        InSource source = sourceService.getByUrl(sUrl);

        return ResponseEntity.ok(source);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增资源",httpMethod = "POST",notes = "根据sUrl新增资源")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public ResponseEntity<Void> save(@RequestBody InSource source) {
        source.setsOperationUserid(getUserId());
        sourceService.save(source);
        return ResponseEntity.ok().build();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改资讯资源",httpMethod = "PUT")
    @ApiImplicitParam(name = "source",value = "资源数据",required = true)
    public ResponseEntity<Void> update(@RequestBody InSource source) {
        sourceService.updateByUrl(source);

        return ResponseEntity.ok().build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "单个或批量删除资源",httpMethod = "DELETE",notes = "根据sUrl[数组]删除资源")
    @ApiImplicitParam(name = "sUrls",value = "资源路径",dataType = "Array",required = true)
    public ResponseEntity<Void> delete(@RequestBody String[] sUrls) {
        sourceService.removeByUrl(Arrays.asList(sUrls));
        return ResponseEntity.ok().build();
    }
}
