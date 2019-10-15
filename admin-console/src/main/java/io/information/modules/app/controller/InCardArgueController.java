package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCardArgue;
import io.information.modules.app.service.IInCardArgueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯帖子辩论表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/card/argue")
@Api(value = "APP帖子_辩论表")
public class InCardArgueController {
    @Autowired
    private IInCardArgueService cardArgueService;

    /**
     * 添加
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增辩论帖子",httpMethod = "POST")
    @ApiImplicitParam(name = "cardArgue",value = "辩论帖子信息",required = true)
    public ResponseEntity<Void> save(@RequestBody InCardArgue cardArgue){
        cardArgueService.save(cardArgue);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "单个或批量删除辩论帖子",httpMethod = "DELETE",notes = "根据cId[数组]删除辩论帖子")
    @ApiImplicitParam(name = "cIds",value = "帖子ID",dataType = "Array",required = true)
    public ResponseEntity<Void> delete(@RequestBody Long[] cIds){
        cardArgueService.removeByIds(Arrays.asList(cIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改辩论帖子",httpMethod = "PUT")
    @ApiImplicitParam(name = "cardArgue",value = "辩论帖子信息",required = true)
    public ResponseEntity<Void> updateCardArgue(@RequestBody InCardArgue cardArgue){
        cardArgueService.updateById(cardArgue);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 查询
     */
    @GetMapping("/info/{cId}")
    @ApiOperation(value = "查询单个辩论帖子",httpMethod = "GET",notes = "根据帖子ID查询辩论帖子信息")
    @ApiImplicitParam(name = "cId",value = "帖子ID",required = true)
    public ResponseEntity<InCardArgue> queryCardArgue(@PathVariable("cId") Long cId){
        InCardArgue cardArgue = cardArgueService.getById(cId);
        return ResponseEntity.ok(cardArgue);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取全部辩论帖子",httpMethod = "GET")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public ResponseEntity<PageUtils> list(@RequestParam Map<String, Object> map){
        PageUtils page = cardArgueService.queryPage(map);
        return ResponseEntity.ok(page);
    }

}
