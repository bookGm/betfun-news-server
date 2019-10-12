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
     * 文章关键字查询
     */
    @RequestMapping("/page")
    //pageSize 每页数量   currPage  当页数
    public PageUtils articleSearch(SearchRequest request) {
        return articleService.articleSearchKey(request);
    }

    /**
     * 文章过滤查询
     */
    @RequestMapping("/filter")
    public ResponseEntity<Map<String, List<?>>> articleFilter(@RequestBody SearchRequest request){
        return ResponseEntity.ok(articleService.articleFilter(request));
    }


    /**
     * 帖子关键字查询
     */
    @RequestMapping("/page")
    //pageSize 每页数量   currPage  当页数
    public PageUtils cardSearch(SearchRequest request) {
        return articleService.searchPage(request);
    }

    /**
     * 帖子过滤查询
     */
    @RequestMapping("/filter")
    public ResponseEntity<Map<String, List<?>>> cardFilter(@RequestBody SearchRequest request){
        return null;
    }
}
