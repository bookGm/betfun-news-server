package io.elasticsearch.controller;

import io.elasticsearch.service.EsFlashService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * es快讯查询
 */
@RestController
@RequestMapping("/es/flash")
public class EsFlashController {
    @Autowired
    private EsFlashService flashService;

    /**
     * 搜索快讯
     */
    @GetMapping("/search")
    public PageUtils searchFlash(@RequestParam SearchRequest request){
        return flashService.searchFlash(request);
    }
}