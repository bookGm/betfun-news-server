package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.config.IdWorker;
import io.information.modules.news.entity.ArticleEntity;
import io.information.modules.news.service.ArticleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
public class ArticleController {
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
    @GetMapping("/infoAll/{uId}")
    @RequiresPermissions("news:article:infoAll")
    public R infoAll(@PathVariable("uId") Long uId) {
        PageUtils pageUtils = articleService.queryAllArticle(uId);
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
    @PostMapping("/deleteAll/{uId}")
    @RequiresPermissions("news:article:deleteAll")
    public R deleteAll(@PathVariable("uId") Long uId) {
        articleService.deleteAllActive(uId);
        return R.ok();
    }


}
