package io.information.modules.app.controller;

import io.elasticsearch.service.EsUserService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/app/es/user")
public class EsUserController {
    @Autowired
    private EsUserService userService;

    /**
     * 搜索作者
     */
    @GetMapping("/search")
    public PageUtils search(@RequestParam Map<String,Object> map){
        return userService.search(map);
    }
}
