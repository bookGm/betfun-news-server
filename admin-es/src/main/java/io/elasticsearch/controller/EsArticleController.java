package io.elasticsearch.controller;

import io.elasticsearch.service.EsArticleService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("es/article")
public class EsArticleController {
    @Autowired
    private EsArticleService articleService;

    /**
     * 文章关键字查询
     */
    @RequestMapping("/search")
    //pageSize 每页数量   currPage  当页数
    public PageUtils articleSearch(SearchRequest request) {
        return articleService.articleSearchKey(request);
    }

}
