package io.information.modules.app.controller;


import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.InDic;
import io.information.modules.app.service.IInDicService;
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
@RequestMapping("/news/dic")
public class InDicController {
    @Autowired
    private IInDicService dicService;

    /**
     * 新增
     */
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody InDic dic){
        dic.setdId(new IdWorker().nextId());
        dicService.save(dic);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody Long[] dIds){
        dicService.deleteDic(dIds);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody InDic dic){
        dicService.updateDic(dic);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * ID查询
     * TODO
     */
    @GetMapping("/info/{dId}")
    public ResponseEntity<List<InDic>> info(@PathVariable("dId") Long dId){
        List<InDic> dics = dicService.queryDicById(dId);
        return ResponseEntity.ok(dics);
    }


    /**
     * 编码查询
     */
    @GetMapping("/infoCode/{dCode}")
    public ResponseEntity<List<InDic>> infoCode(@PathVariable("dCode") String dCode){
        List<InDic> dics = dicService.queryDicByCode(dCode);
        return ResponseEntity.ok(dics);
    }


    /**
     * 名称模糊查询
     */
    @GetMapping("/infoLike/{dName}")
    public ResponseEntity<List<InDic>> infoLike(@PathVariable("dName") String dName){
        List<InDic> dicList = dicService.queryNameDic(dName);
        return ResponseEntity.ok(dicList);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<InDic>> list(){
        List<InDic> dicList = dicService.queryAllDic();
        return ResponseEntity.ok(dicList);
    }

}
