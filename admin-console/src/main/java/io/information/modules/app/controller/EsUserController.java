package io.information.modules.app.controller;

import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.information.common.utils.R;
import io.information.modules.app.service.UserEsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 同步
     */
    @PostMapping("/KSIQUXOPELK77")
    public R update() {
        userService.updateAll();
        return R.ok();
    }
}
