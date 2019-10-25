package io.information.modules.app.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.util.*;
import java.util.stream.Collectors;

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
    public R save(@RequestBody InArticle article, @ApiIgnore @LoginUser InUser user) {
        if (user.getuAuthStatus() == 2) {
            article.setuId(user.getuId());
            article.setaCreateTime(new Date());
            articleService.save(article);
            rabbitTemplate.convertAndSend(Constants.articleExchange,
                    Constants.article_Save_RouteKey, JSON.toJSON(article));
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
                    Constants.article_Update_RouteKey, JSON.toJSON(article));
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取已发布的文章", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据，状态码", required = true)
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = articleService.queryPage(map);
        return R.ok().put("page", page);
    }
    /**
     * 列表
     */
    @Login
    @GetMapping("/loginedList")
    @ApiOperation(value = "获取本人发布的文章", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据，状态码", required = true)
    public R loginedList(@RequestParam Map<String, Object> map,@ApiIgnore @LoginUser InUser user) {
        map.put("uId",user.getuId());
        PageUtils page = articleService.queryPage(map);
        return R.ok().put("page", page);
    }


    /**
     * 查询
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

    /**
     * 专栏主页文章统计
     */
    @Login
    @GetMapping("getArticleStatistics")
    @ApiOperation(value = "获取专栏主页文章统计数据", httpMethod = "GET")
    public R getArticleStatistics(@ApiIgnore @LoginUser InUser user) {
        Map<String,Object> rm=new HashMap<>();
        List<InArticle> list=articleService.list(new LambdaQueryWrapper<InArticle>().eq(InArticle::getuId,user.getuId()));
        //累计文章数
        rm.put("aCount",list.size());
        //累计阅读量
        LongSummaryStatistics readNumber=list.stream().collect(Collectors.summarizingLong((n)->n.getaReadNumber()==null?0L:n.getaReadNumber()));
        rm.put("rCount",readNumber.getSum());
        //累计粉丝数
        rm.put("fCount",user.getuFans());
        return R.ok(rm);
    }
}
