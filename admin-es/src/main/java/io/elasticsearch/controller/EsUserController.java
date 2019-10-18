package io.elasticsearch.controller;

import io.elasticsearch.entity.EsUserEntity;
import io.elasticsearch.service.EsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/es/user")
public class EsUserController {
    @Autowired
    private EsUserService userService;

    /**
     * 昵称匹配用户<不分页>
     */
    @GetMapping("/search")
    public List<EsUserEntity> search(String key){
        return userService.search(key);
    }
}
