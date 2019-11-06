package io.information.modules.app.controller;

import io.elasticsearch.service.EsArticleService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * es文章操作
 */
@RestController
@RequestMapping("/app/es/article")
@Api(value = "/app/es/article", tags = "es搜索咨讯")
public class EsArticleController {
    @Autowired
    private EsArticleService articleService;

    /**
     * 搜索咨讯 -- 文章
     */
    @PostMapping("/search")
    @ApiOperation(value = "搜索咨讯", httpMethod = "POST")
    public PageUtils searchInfo(@RequestBody SearchRequest request) {
        return articleService.searchInfo(request);
    }
}
