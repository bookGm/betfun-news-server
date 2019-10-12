package io.elasticsearch.controller;

import io.elasticsearch.entity.EsArticleEntity;
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
    public PageUtils articleSearch(SearchRequest request) {
        return articleService.articleSearchKey(request);
    }

    /**
     * 文章新增
     */
    @RequestMapping("/save")
    public void articleSave(EsArticleEntity articleEntity) {
        articleService.saveArticle(articleEntity);
    }

    /**
     * 文章删除
     */
    @RequestMapping("/delete")
    public void articleDelete(EsArticleEntity articleEntity) {
        articleService.removeArticle(articleEntity);
    }

    /**
     * 文章修改
     */
    @RequestMapping("/update")
    public void articleUpdate(EsArticleEntity articleEntity) {
        articleService.updatedArticle(articleEntity);
    }
}
