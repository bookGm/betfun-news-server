package io.information.modules.app.controller;


import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.InDic;
import io.information.modules.app.service.IInDicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资讯字典表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/news/dic")
public class InDicController {
    @Autowired
    private IInDicService dicService;

    /**
     * 新增字典
     * @param dic
     * @return
     */
    @PostMapping("/addDic")
    public ResponseEntity<Void> addDic(InDic dic){
        dic.setDId(new IdWorker().nextId());
        dicService.save(dic);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除字典
     * @param disId
     * @return
     */
    @DeleteMapping("/deleteDic")
    public ResponseEntity<Void> deleteDic(Long disId){
        dicService.deleteDic(disId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改字典
     * @param dic
     * @return
     */
    @PutMapping("/updateDic")
    public ResponseEntity<Void> updateDic(InDic dic){
        dicService.updateDic(dic);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据ID查询节点和子节点
     * @param dicId
     * @return
     */
    @GetMapping("/queryDicById")
    public ResponseEntity<List<InDic>> queryDicById(Long dicId){
        List<InDic> dics = dicService.queryDicById(dicId);
        return ResponseEntity.ok(dics);
    }


    /**
     * 跟据编码查询节点和子节点
     * @param dicCode
     * @return
     */
    @GetMapping("/queryDicByCode")
    public ResponseEntity<List<InDic>> queryDicByCode(String dicCode){
        List<InDic> dics = dicService.queryDicByCode(dicCode);
        return ResponseEntity.ok(dics);
    }


    /**
     * 根据字典名称模糊查询字典
     * @param dicName
     * @return
     */
    @GetMapping("/queryNameDic")
    public ResponseEntity<List<InDic>> queryNameDic(String dicName){
        List<InDic> dicList = dicService.queryNameDic(dicName);
        return ResponseEntity.ok(dicList);
    }


    /**
     * 获取全部字典
     * @return
     */
    @GetMapping("/queryAllDic")
    public ResponseEntity<List<InDic>> queryAllDic(){
        List<InDic> dicList = dicService.queryAllDic();
        return ResponseEntity.ok(dicList);
    }
}
