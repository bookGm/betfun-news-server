package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.service.IInArticleService;
import io.information.modules.sys.controller.AbstractController;
import io.mq.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/app/article")
@Api(value = "APP咨讯文章接口")
public class InArticleController extends AbstractController {
    @Autowired
    private IInArticleService articleService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 添加
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增咨讯文章",httpMethod = "POST")
    @ApiImplicitParam(name = "article",value = "文章信息",required = true)
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
    @ApiOperation(value = "单个或批量删除咨讯文章",httpMethod = "DELETE",notes = "根据aId[数组]删除活动")
    @ApiImplicitParam(name = "aIds",value = "文章ID",dataType = "Array",required = true)
    public ResponseEntity<Void> delete(@RequestBody Long[] aIds) {
        articleService.removeByIds(Arrays.asList(aIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 用户删除
     */
    @DeleteMapping("/deleteList")
    @ApiOperation(value = "删除用户发布的咨询文章",httpMethod = "DELETE",notes = "自动获取用户信息")
    public ResponseEntity<Void> deleteList() {
        articleService.deleteAllArticle(getUserId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改咨讯文章",httpMethod = "PUT")
    @ApiImplicitParam(name = "article",value = "文章信息",required = true)
    public ResponseEntity<Void> update(@RequestBody InArticle article) {
        articleService.updateById(article);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取全部咨讯文章",httpMethod = "GET")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public ResponseEntity<PageUtils> list(@RequestParam Map<String, Object> map) {
        PageUtils page = articleService.queryPage(map);
        return ResponseEntity.ok(page);
    }


    /**
     * 查询
     */
    @GetMapping("/info/{aId}")
    @ApiOperation(value = "查询单个咨讯文章",httpMethod = "GET",notes = "根据文章ID查询文章")
    @ApiImplicitParam(name = "aId",value = "文章ID",required = true)
    public ResponseEntity<InArticle> queryArticle(@PathVariable("aId") Long aId) {
        InArticle article = articleService.getById(aId);
        return ResponseEntity.ok(article);
    }


    /**
     * 用户查询
     */
    @GetMapping("/userArticle")
    @ApiOperation(value = "获取用户发布的文章",httpMethod = "GET",notes = "自动获取用户信息")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public ResponseEntity<PageUtils> queryUserArticle(@RequestParam Map<String, Object> map) {
        PageUtils page = articleService.queryAllArticle(map, getUserId());
        return ResponseEntity.ok(page);
    }

    /**
     * 用户查看草稿箱
     */
    @GetMapping("/uDraft")
    @ApiOperation(value = "获取用户草稿箱",httpMethod = "GET",notes = "自动获取用户信息和文章状态")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public ResponseEntity<PageUtils> uDraft(@RequestParam Map<String, Object> map) {
        PageUtils page = articleService.uDraft(map, getUserId());
        return ResponseEntity.ok(page);
    }


    /**
     * 点赞
     */
    @PostMapping("/giveALike/{aId}")
    @ApiOperation(value = "文章点赞",httpMethod = "POST",notes = "根据文章ID点赞")
    @ApiImplicitParam(name = "aId",value = "文章ID",required = true)
    public ResponseEntity giveALike(@PathVariable("aId") Long aId) {
        if (articleService.giveALike(aId, getAppUserId())) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 收藏
     */
    @PostMapping("/collect")
    @ApiOperation(value = "文章收藏",httpMethod = "POST",notes = "根据文章ID收藏")
    @ApiImplicitParam(name = "aId",value = "文章ID",required = true)
    public ResponseEntity collect(@PathVariable("aId") Long aId) {
        if (articleService.collect(aId, getAppUserId())) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
