package io.information.modules.app.controller;


import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.InDic;
import io.information.modules.app.service.IInDicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 资讯字典表 前端控制器
 * </p>
 *  TODO
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/dic")
@Api(value = "APP咨讯字典接口")
public class InDicController {
    @Autowired
    private IInDicService dicService;

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增字典节点",httpMethod = "POST")
    @ApiImplicitParam(name = "dic",value = "字典信息",required = true)
    public ResponseEntity<Void> save(@RequestBody InDic dic){
        dic.setdId(new IdWorker().nextId());
        dicService.save(dic);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "单个或批量删除字典节点",httpMethod = "DELETE",notes = "根据dId[数组]删除字典节点")
    @ApiImplicitParam(name = "dIds",value = "字典ID",required = true)
    public ResponseEntity<Void> delete(@RequestBody Long[] dIds){
        dicService.deleteDic(dIds);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改字典节点",httpMethod = "PUT")
    @ApiImplicitParam(name = "dic",value = "字典信息",required = true)
    public ResponseEntity<Void> update(@RequestBody InDic dic){
        dicService.updateDic(dic);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 查询
     */
    @GetMapping("/info/{dId}")
    @ApiOperation(value = "查询节点信息",httpMethod = "GET",notes = "根据字典ID查询节点及其子节点")
    @ApiImplicitParam(name = "dId",value = "字典ID",dataType = "Array")
    public ResponseEntity<List<InDic>> info(@PathVariable("dId") Long dId){
        List<InDic> dics = dicService.queryDicById(dId);
        return ResponseEntity.ok(dics);
    }


    /**
     * 编码查询
     */
    @GetMapping("/infoCode/{dCode}")
    @ApiOperation(value = "dCode查询节点信息",httpMethod = "GET",notes="根据编码查询节点及其子节点")
    public ResponseEntity<List<InDic>> infoCode(@PathVariable("dCode") String dCode){
        List<InDic> dics = dicService.queryDicByCode(dCode);
        return ResponseEntity.ok(dics);
    }


    /**
     * 名称模糊查询
     */
    @GetMapping("/infoLike/{dName}")
    @ApiOperation(value = "dName查询节点信息",httpMethod = "GET",notes = "根据名称模糊查询节点及其子节点")
    public ResponseEntity<List<InDic>> infoLike(@PathVariable("dName") String dName){
        List<InDic> dicList = dicService.queryNameDic(dName);
        return ResponseEntity.ok(dicList);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取所有字典节点信息",httpMethod = "GET",notes = "不需要任何参数")
    public ResponseEntity<List<InDic>> list(){
        List<InDic> dicList = dicService.queryAllDic();
        return ResponseEntity.ok(dicList);
    }

}
