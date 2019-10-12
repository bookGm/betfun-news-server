package io.elasticsearch.controller;

import io.elasticsearch.service.EsCardService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("es/card")
public class EsCardController {
    @Autowired
    private EsCardService cardService;

    /**
     * 帖子关键字查询
     */
    @RequestMapping("/search")
    //pageSize 每页数量   currPage  当页数
    public PageUtils cardSearch(SearchRequest request) {
        return cardService.cardSearch(request);
    }

}
