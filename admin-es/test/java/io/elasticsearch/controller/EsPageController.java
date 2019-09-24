package io.elasticsearch.controller;

import io.elasticsearch.entity.LocationEntity;
import io.elasticsearch.service.impl.LocationServiceImpl;
import io.elasticsearch.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EsPageController {
    @Autowired
    private LocationServiceImpl locationService;

    /**
     * 添加
     * @param id
     * @param firstName
     * @param lastName
     * @param age
     * @param about
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity addEsData(@RequestParam(name = "")String id,
                                    @RequestParam(name = "")String firstName,
                                    @RequestParam(name = "")String lastName,
                                    @RequestParam(name = "")Integer age, String about){
        for(int i=0;i<100;i++){
            LocationEntity entity = new LocationEntity();
            entity.setId(id+i);
            entity.setFirstName(firstName+i);
            entity.setLastName(i+lastName);
            entity.setAge(age);
            entity.setAbout(about);
            locationService.save(entity);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }



    /**
     * 根据ID查询
     * @return
     */
    @GetMapping("/queryById")
    public ResponseEntity queryById(@RequestParam(name = "") String id) {
        LocationEntity entity = locationService.findById(id);
        return ResponseEntity.ok(entity);
    }

    /**
     * 删除
     * @param entity
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity delete(@RequestParam(name = "")LocationEntity entity){
        locationService.delete(entity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 分页
     * @param params 查询条件
     * @param currPage 当前页
     * @param pageSize 显示数量
     * @return
     */
    @GetMapping("/queryByPage")
    public PageUtils queryListByPage(@RequestParam(value = "key", required = false)String params,
                                                @RequestParam(defaultValue = "1")Integer currPage,
                                                @RequestParam(defaultValue = "10")Integer pageSize){

        return locationService.findPage(params, currPage, pageSize);
    }

}
