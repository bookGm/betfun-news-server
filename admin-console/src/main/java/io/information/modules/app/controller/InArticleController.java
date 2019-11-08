package io.information.modules.app.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.elasticsearch.entity.EsArticleEntity;
import io.information.common.utils.*;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInActivityService;
import io.information.modules.app.service.IInArticleService;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInUserService;
import io.information.modules.app.vo.ArticleUserVo;
import io.information.modules.app.vo.ArticleVo;
import io.information.modules.app.vo.InArticleUserDetailVo;
import io.mq.utils.Constants;
import io.swagger.annotations.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private IInActivityService activityService;
    @Autowired
    private IInCardBaseService baseService;
    @Autowired
    private IInUserService inUserService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 添加 esOK
     */
    @Login
    @PostMapping("/save")
    @ApiOperation(value = "新增咨讯文章", httpMethod = "POST")
    public R save(@RequestBody InArticle article, @ApiIgnore @LoginUser InUser user) {
        if (user.getuAuthStatus() == 2) {
            article.setaId(IdGenerator.getId());
            article.setuId(user.getuId());
            article.setaCreateTime(new Date());
            articleService.save(article);
            EsArticleEntity esArticle = BeanHelper.copyProperties(article, EsArticleEntity.class);
            rabbitTemplate.convertAndSend(Constants.articleExchange,
                    Constants.article_Save_RouteKey, esArticle);
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
    @ApiImplicitParam(value = "文章ID[数组]", name = "aIds", required = true)
    public R delete(@RequestParam Long[] aIds, @ApiIgnore @LoginUser InUser user) {
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
    @ApiOperation(value = "获取已发布的文章", httpMethod = "GET", notes = "分页数据，文章状态[aStatus]，状态码[type]")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "0：草稿箱 1：审核中 2：已发布", name = "aStatus", required = true),
            @ApiImplicitParam(value = "0：时间排序 1：浏览量排序 2：综合排序", name = "type", required = false)
    })
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = articleService.queryPage(map);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @Login
    @GetMapping("/loginedList")
    @ApiOperation(value = "获取本人发布的文章", httpMethod = "GET", notes = "分页数据，文章状态[aStatus]，状态码[type]")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "0：草稿箱 1：审核中 2：已发布", name = "aStatus", required = true),
            @ApiImplicitParam(value = "0：时间排序 1：浏览量排序 2：综合排序", name = "type", required = false)
    })
    public R loginedList(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = articleService.queryPage(map);
        return R.ok().put("page", page);
    }


    /**
     * 查询
     */
    @GetMapping("/info/{aId}")
    @ApiOperation(value = "查询单个咨讯文章", httpMethod = "GET", notes = "文章ID[aId]")
    public R queryArticle(@PathVariable("aId") String aId, HttpServletRequest request) {
        String ip = IPUtils.getIpAddr(request);
        InArticle article = articleService.getById(aId);
        Boolean aBoolean = redisTemplate.hasKey(RedisKeys.BROWSEIP + ip + aId);
        if (!aBoolean) {
            redisTemplate.opsForValue().set(RedisKeys.BROWSEIP + ip + aId, aId, 60 * 60 * 2);
//            redisTemplate.setValueSerializer(new GenericToStringSerializer<Long>(Long.class));
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
    @PostMapping("/giveALike")
    @ApiOperation(value = "点赞", httpMethod = "POST", notes = "根据ID点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true),
            @ApiImplicitParam(name = "type", value = "(0：文章 1：帖子 2：活动)", required = true)
    })
    public R giveALike(@RequestParam("id") Long id, @RequestParam("type") int type, @ApiIgnore @LoginUser InUser user) {
        Long tid = filterId(id, type);
        if(StringUtil.isBlank(tid)){
            return R.error("点赞失败");
        }
        return R.ok().put("time", articleService.giveALike(id, user.getuId(), type, tid));
    }

    /**
     * 收藏
     */
    @Login
    @PostMapping("/collect")
    @ApiOperation(value = "收藏", httpMethod = "POST", notes = "根据ID收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true),
            @ApiImplicitParam(name = "type", value = "(0：文章 1：帖子 2：活动)", required = true)
    })
    public R collect(@RequestParam("id") Long id, @RequestParam("type") int type, @ApiIgnore @LoginUser InUser user) {
        Long tid = filterId(id, type);
        if(StringUtil.isBlank(tid)){
            return R.error("收藏失败");
        }
        return R.ok().put("time", articleService.collect(id, user.getuId(), type, tid));
    }

    /**
     * 专栏主页文章统计
     */
    @Login
    @GetMapping("/getArticleStatistics")
    @ApiOperation(value = "获取专栏主页文章统计数据", httpMethod = "GET")
    @ApiResponse(code = 200, message = "aCount：文章数  rCount：阅读量  fCount：粉丝数")
    public R getArticleStatistics(@ApiIgnore @LoginUser InUser user) {
        Map<String, Object> rm = new HashMap<>();
        List<InArticle> list = articleService.list(new LambdaQueryWrapper<InArticle>().eq(InArticle::getuId, user.getuId()));
        //累计文章数
        rm.put("aCount", list.size());
        //累计阅读量
        LongSummaryStatistics readNumber = list.stream().collect(Collectors.summarizingLong((n) -> n.getaReadNumber() == null ? 0L : n.getaReadNumber()));
        rm.put("rCount", readNumber.getSum());
        //累计粉丝数
        rm.put("fCount", user.getuFans());
        return R.ok(rm);
    }


    /**
     * 社区 -- 帖子详情 --发布者信息和帖子推荐
     */
    @GetMapping("/articleRecommended")
    @ApiOperation(value = "文章详情 -- 发布者信息[右上]", httpMethod = "GET", notes = "分页数据，用户ID", response = ArticleUserVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "用户ID", name = "uId", required = true)
    })
    public ResultUtil<ArticleUserVo> articleRecommended(@RequestParam Map<String, Object> map) {
        ArticleUserVo articleUserVo = articleService.articleRecommended(map);
        return ResultUtil.ok(articleUserVo);
    }


    /**
     * 热门资讯
     */
    @GetMapping("/hotTopic")
    @ApiOperation(value = "热门资讯", httpMethod = "GET", notes = "返回10条数据")
    public ResultUtil<List<ArticleVo>> hotTopic() {
        List<ArticleVo> articleVos = articleService.hotTopic();
        return ResultUtil.ok(articleVos);
    }


    private Long filterId(Long id, Integer type) {
        Long tid = null;
        if (NewsEnum.点赞_文章.getCode().equals(String.valueOf(type))) {
            tid = articleService.getById(id).getuId();
        }
        if (NewsEnum.点赞_帖子.getCode().equals(String.valueOf(type))) {
            tid = baseService.getById(id).getuId();
        }
        if (NewsEnum.点赞_活动.getCode().equals(String.valueOf(type))) {
            tid = activityService.getById(id).getuId();
        }
        return tid;
    }

    @GetMapping("/articleUserDetail")
    @ApiOperation(value = "查询文章作者及详情", httpMethod = "GET", notes = "文章id")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "文章id", name = "aId", required = true),
            @ApiImplicitParam(value = "文章所属用户id", name = "uId", required = true),
    })
    public ResultUtil<InArticleUserDetailVo> getArticleUserDetail(@RequestParam("aId") Long aId, @RequestParam(value = "uId",required = false) Long uId) {
        InArticle a = articleService.getById(aId);
        if (null == a) {
            return ResultUtil.error("不存在此文章");
        }
        InArticleUserDetailVo av = new InArticleUserDetailVo();
        if(StringUtil.isNotBlank(uId)){
            InUser u = inUserService.getById(uId);
            av.setuNick(u.getuNick());
            av.setuPhoto(u.getuPhoto());
            av.setLiked(redisTemplate.opsForHash().hasKey(RedisKeys.LIKE, aId + "-" + uId + "-" + u.getuId() + "-" + NewsEnum.点赞_文章.getCode()));
            av.setCollected(redisTemplate.opsForHash().hasKey(RedisKeys.COLLECT, aId + "-" + uId + "-" + u.getuId() + "-" + NewsEnum.收藏_文章.getCode()));
        }
        av.setaLike(a.getaLike());
        av.setaCollect(a.getaCollect());
        return ResultUtil.ok(av);
    }
}
