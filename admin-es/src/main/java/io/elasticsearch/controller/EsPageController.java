package io.elasticsearch.controller;

import io.elasticsearch.entity.LocationEntity;
import io.elasticsearch.service.impl.LocationServiceImpl;
import io.elasticsearch.utils.PageUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RunWith()
public class EsPageController {
    @Autowired
    private LocationServiceImpl locationService;

    /**
     * 添加索引
     */
    @PostMapping("/add")
    public ResponseEntity addEsData(LocationEntity entity) {
        locationService.save(entity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }




    /**
     * 根据索引ID查询
     */
    @GetMapping("/queryById")
    public ResponseEntity queryById(String id) {
        LocationEntity entity = locationService.findById(id);
        return ResponseEntity.ok(entity);
    }

    /**
     * 删除
     */
    @PostMapping("/update")
    public ResponseEntity delete(LocationEntity entity) {
        locationService.delete(entity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 分页
     *
     * @param params   查询条件
     * @param currPage 当前页
     * @param pageSize 显示数量
     * @return
     */
    @GetMapping("/queryByPage")
    public PageUtils queryListByPage(@RequestParam(value = "key", required = false) String params,
                                     @RequestParam(defaultValue = "1") Integer currPage,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {

        return locationService.findPage(params, currPage, pageSize);
    }

}
