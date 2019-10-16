package io.elasticsearch.controller;

import io.elasticsearch.entity.EsCardEntity;
import io.elasticsearch.service.EsCardService;
import io.elasticsearch.utils.PageUtils;
import io.elasticsearch.utils.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * es帖子的操作
 * TODO userId的来源
 */
@RestController
@RequestMapping("es/card")
public class EsCardController {
    @Autowired
    private EsCardService cardService;

    /**
     * 帖子关键字查询
     */
    @GetMapping("/search")
    public PageUtils cardSearch(SearchRequest request) {
        return cardService.cardSearch(request);
    }

    /**
     * 帖子查询[0：普通帖 1：辩论贴 2：投票贴]
     */
    @GetMapping("/statusSearch")
    public PageUtils statusSearch(SearchRequest request) {
        return cardService.statusSearch(request);
    }

    /**
     * 用户帖子查询
     */
    @GetMapping("/userSearch")
    public PageUtils userSearch(SearchRequest request) {
        return cardService.userSearch(request);
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
    public void cardDelete(Long[] cIds) {
        cardService.removeCard(cIds);
    }

    /**
     * 帖子修改
     */
    @RequestMapping("/update")
    public void cardUpdate(EsCardEntity cardEntity) {
        cardService.updatedCard(cardEntity);
    }


}
