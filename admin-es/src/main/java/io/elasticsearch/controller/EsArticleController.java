package io.elasticsearch.controller;

import io.elasticsearch.service.EsArticleService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EsArticleController {
    @Autowired
    private EsArticleService articleService;

    /**
     * 关键字查询
     */
    @RequestMapping("/page")
    //pageSize 每页数量   currPage  当页数
    public PageUtils searchPage(SearchRequest request) {
        return articleService.searchPage(request);
    }

    /**
     * 过滤查询
     */
    @RequestMapping("/filter")
    public ResponseEntity<Map<String, List<?>>> filter(@RequestBody SearchRequest request){
        return ResponseEntity.ok(articleService.queryFilter(request));
    }
}
