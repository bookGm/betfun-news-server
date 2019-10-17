package io.elasticsearch.controller;

import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.service.EsArticleService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * es文章操作
 * TODO userID的来源
 */
@RestController
@RequestMapping("es/article")
public class EsArticleController {
    @Autowired
    private EsArticleService articleService;


    /**
     * 查询所有已发布文章
     */
    @GetMapping("/search")
    public PageUtils search(@RequestBody SearchRequest request) {
        return articleService.articleSearch(request);
    }

    /**
     * 文章关键字查询
     */
    @GetMapping("/keySearch")
    public PageUtils keySearch(@RequestBody SearchRequest request) {
        return articleService.articleSearchKey(request);
    }

    /**
     * 根据状态获取用户文章[0:草稿箱 1:审核中 2:已发布]
     */
    @GetMapping("/statusSearch")
    public PageUtils statusSearch(@RequestBody SearchRequest request) {
        return articleService.articleSearchStatus(request);
    }
}
