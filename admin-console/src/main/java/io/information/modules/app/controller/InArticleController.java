package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.service.IInArticleService;
import io.information.modules.sys.controller.AbstractController;
import io.mq.utils.Constants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

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
public class InArticleController extends AbstractController {
    @Autowired
    private IInArticleService articleService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 添加
     */
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody InArticle article) {
        article.setuId(getUserId());
        article.setaCreateTime(new Date());
        articleService.save(article);
        //rabbit
        rabbitTemplate.convertAndSend(Constants.defaultExchange,
                Constants.routeKey, article);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody Long[] aIds) {
        articleService.removeByIds(Arrays.asList(aIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 用户删除
     */
    @DeleteMapping("/deleteList")
    public ResponseEntity<Void> deleteList() {
        articleService.deleteAllArticle(getUserId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody InArticle article) {
        articleService.updateById(article);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageUtils> list(@RequestParam Map<String, Object> params) {
        PageUtils page = articleService.queryPage(params);
        return ResponseEntity.ok(page);
    }


    /**
     * 查询
     */
    @GetMapping("/info/{aId}")
    public ResponseEntity<InArticle> queryArticle(@PathVariable("aId") Long aId) {
        InArticle article = articleService.getById(aId);
        return ResponseEntity.ok(article);
    }


    /**
     * 用户查询
     */
    @GetMapping("/userList")
    public ResponseEntity<PageUtils> queryUserArticle(@RequestParam Map<String, Object> params) {
        PageUtils page = articleService.queryAllArticle(params, getUserId());
        return ResponseEntity.ok(page);
    }

    /**
     * 点赞
     */
    @PostMapping("giveALike")
    public ResponseEntity giveALike(Long aid) {
        if (articleService.giveALike(aid, getAppUserId())) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 收藏
     */
    @PostMapping("collect")
    public ResponseEntity collect(Long aid) {
        if (articleService.collect(aid, getAppUserId())) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
