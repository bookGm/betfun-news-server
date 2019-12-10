package io.information.modules.app.controller;

import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.information.modules.app.service.UserEsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/es/user")
public class EsUserController {
    @Autowired
    private UserEsService userService;

    /**
     * 搜索作者
     */
    @PostMapping("/search")
    @ApiOperation(value = "搜索文章", httpMethod = "POST")
    public PageUtils searchUsers(@RequestBody SearchRequest request) {
        return userService.searchUsers(request);
    }


//    /**
//     * 搜索作者
//     */
//    @PostMapping("/test")
//    public PageUtils searchText(@RequestBody SearchRequest request) {
//        return userService.searchTest(request);
//    }
}
