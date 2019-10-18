package io.elasticsearch.controller;

import io.elasticsearch.entity.EsArticleEntity;
import io.elasticsearch.service.EsArticleService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping("/searchOK")
    public PageUtils searchOK(@RequestBody SearchRequest request) {
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
     * 关键字查询[标签，标题，内容]<不分页>
     */
    @GetMapping("/search")
    public List<EsArticleEntity> search(String key) {
        return articleService.search(key);
    }

    /**
     * 根据状态获取用户文章[0:草稿箱 1:审核中 2:已发布]
     */
    @GetMapping("/statusSearch")
    public PageUtils statusSearch(@RequestBody SearchRequest request) {
        return articleService.articleSearchStatus(request);
    }
}
