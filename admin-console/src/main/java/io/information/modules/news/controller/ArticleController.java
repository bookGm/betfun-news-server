package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.config.IdWorker;
import io.information.modules.news.entity.ArticleEntity;
import io.information.modules.news.service.ArticleService;
import io.information.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 资讯文章表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@RestController
@RequestMapping("news/article")
public class ArticleController extends AbstractController {
    @Autowired
    private ArticleService articleService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:article:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = articleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{aId}")
    @RequiresPermissions("news:article:info")
    public R info(@PathVariable("aId") Long aId) {
        ArticleEntity article = articleService.getById(aId);
        return R.ok().put("article", article);
    }


    /**
     * 用户ID查询
     */
    @GetMapping("/infoAll")
    @RequiresPermissions("news:article:infoAll")
    public R infoAll(Map<String, Object> params) {
        PageUtils pageUtils = articleService.queryAllArticle(params, getUserId());
        return R.ok().put("userArticle", pageUtils);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:article:save")
    public R save(@RequestBody ArticleEntity article) {
        article.setaId(new IdWorker().nextId());
        article.setaCreateTime(new Date());
        article.setuId(getUserId());
        articleService.save(article);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:article:update")
    public R update(@RequestBody ArticleEntity article) {
        articleService.updateById(article);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:article:delete")
    public R delete(@RequestBody Long[] aIds) {
        articleService.removeByIds(Arrays.asList(aIds));

        return R.ok();
    }


    /**
     * 用户ID删除
     */
    @PostMapping("/deleteAll")
    @RequiresPermissions("news:article:deleteAll")
    public R deleteAll() {
        articleService.deleteAllArticle(getUserId());
        return R.ok();
    }


    /**
     * 获取审核状态文章
     */
    @GetMapping("/draft")
    public ResponseEntity<PageUtils> draft(@RequestParam Map<String, Object> params) {
        PageUtils page = articleService.audit(params);
        return ResponseEntity.ok(page);
    }







/*    *//**
     * 点赞
     * 文章ID
     * 用户ID
     *//*
    @PostMapping("add/like/{aId}")
    public R addLike(@PathVariable("aId") Long aId) {
        ArticleEntity article = articleService.getById(aId);
        //需要在redis中创建一个Set保存信息
        SetOperations opsForSet = redisTemplate.opsForSet();
        //添加被点赞的文章
        Long articleSet = opsForSet.add("article_set", aId);
        //添加点赞的用户
        Long articleUserSet = opsForSet.add("article_user_set" + "_" + aId, getUserId());
        //将用户和文章的对应关系放入Hash
        HashOperations opsForHash = redisTemplate.opsForHash();
        String hashStr = "article_user_like" + "_" + aId + "_" + getUserId();
        //如果有一样的字段，则添加失败
        Boolean aBoolean = opsForHash.putIfAbsent(hashStr, articleSet, articleUserSet);
        //判断是否收藏(添加是否成功,true代表收藏成功，false代表收藏过)
        if (aBoolean) {
            //同步数据库
            article.setaLike(article.getaLike() + 1);
            articleService.updateById(article);
        } else {
            R.error();
        }
        return R.ok();
    }*/

}
