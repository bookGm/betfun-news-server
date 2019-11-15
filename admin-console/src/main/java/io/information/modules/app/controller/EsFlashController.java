package io.information.modules.app.controller;

import io.elasticsearch.service.EsFlashService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * es快讯查询
 */
@RestController
@RequestMapping("/app/es/flash")
public class EsFlashController {
    @Autowired
    private EsFlashService flashService;

    /**
     * 搜索快讯
     */
    @PostMapping("/search")
    public PageUtils searchFlash(@RequestBody SearchRequest request){
        return flashService.searchFlash(request);
    }
}
