package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.common.utils.RedisKeys;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInArticleService;
import io.mq.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
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
@Api(value = "/app/article", tags = "APP咨讯文章接口")
public class InArticleController {
    @Autowired
    private IInArticleService articleService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 添加 esOK
     */
    @Login
    @PostMapping("/save")
    @ApiOperation(value = "新增咨讯文章", httpMethod = "POST")
    @ApiImplicitParam(name = "article", value = "文章信息", required = true)
    public R save(@RequestBody InArticle article, @ApiIgnore @LoginUser InUser user) {
        if (user.getuAuthStatus() == 2) {
            article.setuId(user.getuId());
            article.setaCreateTime(new Date());
            articleService.save(article);
            rabbitTemplate.convertAndSend(Constants.articleExchange,
                    Constants.article_Save_RouteKey, article);
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }


    /**
     * 删除 esOK
     */
    @Login
    @DeleteMapping("/delete")
    @ApiOperation(value = "单个或批量删除咨讯文章", httpMethod = "DELETE", notes = "根据aId[数组]删除活动")
    @ApiImplicitParam(name = "aIds", value = "文章ID", dataType = "Array", required = true)
    public R delete(@RequestBody Long[] aIds, @ApiIgnore @LoginUser InUser user) {
        if (user.getuAuthStatus() == 2) {
            articleService.removeByIds(Arrays.asList(aIds));
            rabbitTemplate.convertAndSend(Constants.articleExchange,
                    Constants.article_Delete_RouteKey, aIds);
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }



    /**
     * 修改 esOK
     */
    @Login
    @PutMapping("/update")
    @ApiOperation(value = "修改咨讯文章", httpMethod = "PUT")
    @ApiImplicitParam(name = "article", value = "文章信息", required = true)
    public R update(@RequestBody InArticle article, @ApiIgnore @LoginUser InUser user) {
        if (user.getuAuthStatus() == 2) {
            articleService.updateById(article);
            rabbitTemplate.convertAndSend(Constants.articleExchange,
                    Constants.article_Update_RouteKey, article);
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }


    /**
     * 列表 esOK
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取全部咨讯文章", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = articleService.queryPage(map);
        return R.ok().put("page", page);
    }


    /**
     * 查询 esNot
     */
    @GetMapping("/info/{aId}")
    @ApiOperation(value = "查询单个咨讯文章", httpMethod = "GET", notes = "根据文章ID查询文章")
    @ApiImplicitParam(paramType = "query", name = "aId", value = "文章ID", required = true)
    public R queryArticle(@PathVariable("aId") Long aId, HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        InArticle article = articleService.getById(aId);
        Boolean aBoolean = redisTemplate.hasKey(ip + aId);
        if (!aBoolean) {
            redisTemplate.opsForValue().set(ip + aId, aId, 60 * 60 * 2);
            redisTemplate.setValueSerializer(new GenericToStringSerializer<Long>(Long.class));
            Long aLong = redisTemplate.opsForValue().increment(RedisKeys.BROWSE + aId, 1);//如果通过自增1
            if (aLong % 100 == 0) {
                redisTemplate.delete(ip + aId);
                long readNumber = aLong + article.getaReadNumber();
                articleService.updateReadNumber(readNumber, article.getaId());
            }
        }
        return R.ok().put("article", article);
    }


    /**
     * 状态查询 esOK
     */
    @Login
    @GetMapping("/status")
    @ApiOperation(value = "获取已发布文章", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R status(@RequestParam Map<String, Object> map) {
        PageUtils page = articleService.statusOK(map);
        return R.ok().put("page", page);
    }


    /**
     * 状态查询用户 esOK
     */
    @Login
    @GetMapping("/statusUser")
    @ApiOperation(value = "文章状态获取用户文章", httpMethod = "GET", notes = "自动获取用户信息")
    @ApiImplicitParam(name = "map", value = "分页数据，文章状态<aStatus>", required = true)
    public R statusArticleUser(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        PageUtils page = articleService.statusArticleUser(map, user.getuId());
        return R.ok().put("page", page);
    }


    /**
     * 点赞
     */
    @Login
    @PostMapping("/giveALike/{aId}")
    @ApiOperation(value = "文章点赞", httpMethod = "POST", notes = "根据文章ID点赞")
    @ApiImplicitParam(name = "aId", value = "文章ID", required = true)
    public R giveALike(@PathVariable("aId") Long aId, @ApiIgnore @LoginUser InUser user) {
        if (articleService.giveALike(aId, user.getuId())) {
            return R.ok();
        }
        return R.error("网络出错，请稍后重试");
    }

    /**
     * 收藏
     */
    @Login
    @PostMapping("/collect")
    @ApiOperation(value = "文章收藏", httpMethod = "POST", notes = "根据文章ID收藏")
    @ApiImplicitParam(name = "aId", value = "文章ID", required = true)
    public R collect(@PathVariable("aId") Long aId, @ApiIgnore @LoginUser InUser user) {
        if (user.getuId() == null) {
            return R.error("请先登录");
        } else {
            if (articleService.collect(aId, user.getuId())) {
                return R.ok();
            }
            return R.error("网络出错，请稍后重试");
        }
    }


}
