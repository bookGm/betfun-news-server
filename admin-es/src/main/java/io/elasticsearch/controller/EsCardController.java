package io.elasticsearch.controller;

import io.elasticsearch.entity.EsCardEntity;
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
    public PageUtils cardSearch(SearchRequest request) {
        return cardService.cardSearch(request);
    }

    /**
     * 帖子新增
     */
    @RequestMapping("/save")
    public void cardSave(EsCardEntity cardEntity) {
        cardService.saveCard(cardEntity);
    }

    /**
     * 帖子删除
     */
    @RequestMapping("/delete")
    public void cardDelete(EsCardEntity cardEntity) {
        cardService.removeCard(cardEntity);
    }

    /**
     * 帖子修改
     */
    @RequestMapping("/update")
    public void cardUpdate(EsCardEntity cardEntity) {
        cardService.updatedCard(cardEntity);
    }


}
