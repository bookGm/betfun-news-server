package io.information.modules.app.controller;

import io.elasticsearch.service.EsArticleService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * es文章操作
 */
@RestController
@RequestMapping("/app/es/article")
public class EsArticleController {
    @Autowired
    private EsArticleService articleService;

    /**
     * 搜索咨讯
     */
    @GetMapping("/search")
    public PageUtils searchInfo(@RequestParam SearchRequest request) {
        return articleService.searchInfo(request);
    }
}
