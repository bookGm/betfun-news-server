package io.information.modules.app.controller;

import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import io.information.common.utils.R;
import io.information.modules.app.service.ArticleEsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * es文章操作
 */
@RestController
@RequestMapping("/app/es/article")
@Api(value = "/app/es/article", tags = "es搜索资讯")
public class EsArticleController {
    @Autowired
    private ArticleEsService articleService;

    /**
     * 搜索资讯 -- 文章
     */
    @PostMapping("/search")
    @ApiOperation(value = "搜索资讯", httpMethod = "POST")
    public PageUtils searchInfo(@RequestBody SearchRequest request) {
        return articleService.searchInfo(request);
    }


    /**
     * 同步
     */
    @PostMapping("/KSIQUXOPELK77")
    public R update() {
        articleService.updateAll();
        return R.ok();
    }
}
