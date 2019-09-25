package io.information.modules.app.controller;


import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.service.IInArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    /**
     * 添加文章
     * @param article
     * @return
     */
    @PostMapping("/addArticle")
    public ResponseEntity<Void> addArticle(InArticle article){
        article.setAId(new IdWorker().nextId());
        article.setACreateTime(LocalDateTime.now());
        articleService.save(article);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据文章ID删除文章
     * @param articleIds
     * @return
     */
    @DeleteMapping("/deleteArticle")
    public ResponseEntity<Void> deleteArticle(List<Long> articleIds){
        articleService.removeByIds(articleIds);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据用户ID删除文章
     * @param userId
     * @return
     */
    @DeleteMapping("/deleteAllArticle")
    public ResponseEntity<Void> deleteAllArticle(Long userId){
        articleService.deleteAllArticle(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改文章信息
     * @param article
     * @return
     */
    @PutMapping("/updateArticle")
    public ResponseEntity<Void> updateArticle(InArticle article){
        articleService.updateById(article);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据文章ID查询
     * @param articleId
     * @return
     */
    @GetMapping("/queryArticle")
    public ResponseEntity<InArticle> queryArticle(Long articleId){
        InArticle article = articleService.getById(articleId);
        return ResponseEntity.ok(article);
    }


    /**
     * 根据用户ID查询
     * @param userId
     * @return
     */
    @GetMapping("/queryAllArticle")
    public ResponseEntity<List<InArticle>> queryAllArticle(Long userId){
        List<InArticle> articleList = articleService.queryAllArticle(userId);
        return ResponseEntity.ok(articleList);
    }
}
