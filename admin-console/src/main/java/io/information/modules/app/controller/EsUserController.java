package io.information.modules.app.controller;

import io.elasticsearch.service.EsUserService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/es/user")
public class EsUserController {
    @Autowired
    private EsUserService userService;

    /**
     * 搜索作者
     */
    @PostMapping("/search")
    public PageUtils search(@RequestBody SearchRequest request){
        return userService.search(request);
    }
}
