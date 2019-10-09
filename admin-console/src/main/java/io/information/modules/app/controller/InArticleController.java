package io.information.modules.app.controller;


import io.mq.utils.Constants;
import io.information.common.utils.PageUtils;
import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.service.IInArticleService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 资讯文章表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/news/article")
public class InArticleController {
    @Autowired
    private IInArticleService articleService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 添加
     * @param article
     * @return
     */
    @PostMapping("/addArticle")
    public ResponseEntity<Void> addArticle(InArticle article){
        article.setAId(new IdWorker().nextId());
        article.setACreateTime(LocalDateTime.now());
        articleService.save(article);
        rabbitTemplate.convertAndSend(Constants.defaultExchange,
                Constants.routeKey, "es同步数据");
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除
     * @param articleIds
     * @return
     */
    @DeleteMapping("/deleteArticle")
    public ResponseEntity<Void> deleteArticle(Long[] articleIds){
        articleService.removeByIds(Arrays.asList(articleIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 用户删除
     * @param userId
     * @return
     */
    @DeleteMapping("/deleteAllArticle")
    public ResponseEntity<Void> deleteAllArticle(Long userId){
        articleService.deleteAllArticle(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     * @param article
     * @return
     */
    @PutMapping("/updateArticle")
    public ResponseEntity<Void> updateArticle(InArticle article){
        articleService.updateById(article);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 列表
     * @return
     */
    @GetMapping("/queryAllArticle")
    public ResponseEntity<PageUtils> queryAllArticle(@RequestParam(defaultValue = "1")int curPage,
                                                     @RequestParam(defaultValue = "10")int size){
        PageUtils page = articleService.queryPage(curPage,size);
        return ResponseEntity.ok(page);
    }


    /**
     * 查询
     * @param articleId
     * @return
     */
    @GetMapping("/queryArticle")
    public ResponseEntity<InArticle> queryArticle(Long articleId){
        InArticle article = articleService.getById(articleId);
        return ResponseEntity.ok(article);
    }


    /**
     * 用户查询
     * @param userId
     * @return
     */
    @GetMapping("/queryUserArticle")
    public ResponseEntity<PageUtils> queryUserArticle(Long userId,
                                                           @RequestParam(defaultValue = "1")int curPage,
                                                           @RequestParam(defaultValue = "10")int size){
        List<InArticle> articles = articleService.queryAllArticle(userId);
        return ResponseEntity.ok(new PageUtils(articles,articles.size(),size,curPage));
    }
}
