package io.information.modules.app.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guansuo.common.StringUtil;
import com.guansuo.newsenum.NewsEnum;
import io.elasticsearch.entity.EsArticleEntity;
import io.information.common.utils.*;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.entity.InArticle;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInActivityService;
import io.information.modules.app.service.IInArticleService;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInUserService;
import io.information.modules.app.vo.*;
import io.mq.utils.Constants;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
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
@Api(value = "/app/article", tags = "APP资讯文章接口")
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
    @Autowired
    RedisUtils redisUtils;

    /**
     * 添加 esOK
     */
    @Login
    @PostMapping("/save")
    @ApiOperation(value = "专栏 -- 发布文章", httpMethod = "POST")
    public R save(@RequestBody InArticle article, @ApiIgnore @LoginUser InUser user) {
        if (user.getuAuthStatus() == 2) {
            if (null == article.getaId() || ("").equals(article.getaId())) {
                article.setaId(IdGenerator.getId());
            }
            article.setuId(user.getuId());
            article.setaStatus(1);
            article.setaCreateTime(new Date());
            articleService.save(article);
            EsArticleEntity esArticle = BeanHelper.copyProperties(article, EsArticleEntity.class);
            rabbitTemplate.convertAndSend(Constants.articleExchange,
                    Constants.article_Save_RouteKey, esArticle);
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }


    @Login
    @PostMapping("/saveDraft")
    @ApiOperation(value = "专栏 -- 保存草稿", httpMethod = "POST")
    public R saveDraft(@RequestBody InArticle article, @ApiIgnore @LoginUser InUser user) {
        article.setaId(IdGenerator.getId());
        article.setuId(user.getuId());
        article.setaStatus(0);
        article.setaCreateTime(new Date());
        articleService.save(article);
        return R.ok();
    }


    /**
     * 删除 esOK
     */
    @Login
    @DeleteMapping("/delete")
    @ApiOperation(value = "单个或批量删除资讯文章", httpMethod = "DELETE", notes = "根据aId[数组]删除活动")
    @ApiImplicitParam(value = "文章ID[数组]", name = "aIds", required = true)
    public R delete(@RequestParam Long[] aIds, @ApiIgnore @LoginUser InUser user) {
        if (user.getuAuthStatus() == 2) {
            articleService.removeByIds(Arrays.asList(aIds));
            String join = StringUtils.join(aIds, ",");
            rabbitTemplate.convertAndSend(Constants.articleExchange,
                    Constants.article_Delete_RouteKey, join);
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }


    /**
     * 修改 esOK
     */
    @Login
    @PutMapping("/update")
    @ApiOperation(value = "修改资讯文章", httpMethod = "PUT")
    public R update(@RequestBody InArticle article, @ApiIgnore @LoginUser InUser user) {
        if (user.getuAuthStatus() == 2) {
            articleService.updateById(article);
            if (null != article.getaStatus() && article.getaStatus() == 2) {
                rabbitTemplate.convertAndSend(Constants.articleExchange,
                        Constants.article_Update_RouteKey, JSON.toJSON(article));
            }
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }

    /**
     * 文章轮播图
     */
    @GetMapping("/banner")
    @ApiOperation(value = "文章轮播图", httpMethod = "GET", response = ArticleBannerVo.class)
    public ResultUtil<List<ArticleBannerVo>> banner() {
        List<ArticleBannerVo> bannerVos = articleService.banner();
        return ResultUtil.ok(bannerVos);
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
    @ApiOperation(value = "查询单个资讯文章", httpMethod = "GET", notes = "文章ID[aId]")
    public R queryArticle(@PathVariable("aId") String aId, @ApiIgnore HttpServletRequest request) {
        String ip = IPUtils.getIpAddr(request);
        InArticle article = articleService.getById(aId);
        Boolean aBoolean = redisTemplate.hasKey(RedisKeys.ABROWSEIP + ip + aId);
        if (!aBoolean) {
            redisTemplate.opsForValue().set(RedisKeys.ABROWSEIP + ip + aId, aId, 60 * 60 * 2);
//            redisTemplate.setValueSerializer(new GenericToStringSerializer<Long>(Long.class));
            Long aLong = redisTemplate.opsForValue().increment(RedisKeys.ABROWSE + aId, 1);//如果通过自增1
            if (aLong % 100 == 0) {
                redisTemplate.delete(ip + aId);
                long readNumber = aLong + article.getaReadNumber();
                articleService.updateReadNumber(readNumber, article.getaId());
            }
        }
        return R.ok().put("article", article);
    }


    /**
     * 下一篇
     */
    @GetMapping("/next")
    @ApiOperation(value = "文章内容 -- 下一篇", httpMethod = "GET", response = InArticle.class)
    @ApiImplicitParam(value = "当前文章的作者ID", name = "uId", required = true)
    public ResultUtil next(@RequestParam Long uId) {
        InArticle article = articleService.next(uId);
        return ResultUtil.ok(article);
    }


    /**
     * 感兴趣 -- 文章
     */
    @GetMapping("/interested")
    @ApiOperation(value = "感兴趣 -- 文章", httpMethod = "GET", response = InArticle.class)
    public ResultUtil interested() {
        List<InArticle> articles = articleService.interested();
        return ResultUtil.ok(articles);
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
    public R giveALike(@RequestBody Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        if ((null != map.get("id") && StringUtil.isNotBlank(map.get("id")))
                && (null != map.get("type") && StringUtil.isNotBlank(map.get("type")))) {
            long id = Long.parseLong(String.valueOf(map.get("id")));
            int type = Integer.parseInt(String.valueOf(map.get("type")));
            Long tid = filterId(id, type);
            if (StringUtil.isBlank(tid)) {
                return R.error("点赞失败");
            }
            return R.ok().put("time", articleService.giveALike(id, user.getuId(), type, tid));
        }
        return R.error("点赞失败");
    }


    /**
     * 取消点赞
     */
    @Login
    @PostMapping("/delALike")
    @ApiOperation(value = "取消点赞", httpMethod = "POST", notes = "根据ID取消点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true),
            @ApiImplicitParam(name = "type", value = "(0：文章 1：帖子 2：活动)", required = true)
    })
    public ResultUtil delALike(@RequestBody Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        if ((null != map.get("id") && StringUtil.isNotBlank(map.get("id")))
                && (null != map.get("type") && StringUtil.isNotBlank(map.get("type")))) {
            long id = Long.parseLong(String.valueOf(map.get("id")));
            int type = Integer.parseInt(String.valueOf(map.get("type")));
            //#id-#uid-#tId-#type"
            Long tid = filterId(id, type);
            if (StringUtil.isBlank(tid)) {
                return ResultUtil.error("取消点赞失败");
            }
            String key = id + "-" + user.getuId() + "-" + tid + "-" + type;
            System.out.println(key);
            Long r = redisUtils.hremove(RedisKeys.LIKE, key);
            if (r > 0) {
                return ResultUtil.ok();
            } else {
                return ResultUtil.error("取消点赞失败，请重试");
            }
        }
        return ResultUtil.error("必要参数为空");
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
    public R collect(@RequestBody Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        if ((null != map.get("id") && StringUtil.isNotBlank(map.get("id")))
                && (null != map.get("type") && StringUtil.isNotBlank(map.get("type")))) {
            long id = Long.parseLong(String.valueOf(map.get("id")));
            int type = Integer.parseInt(String.valueOf(map.get("type")));
            Long tid = filterId(id, type);
            if (StringUtil.isBlank(tid)) {
                return R.error("收藏失败");
            }
            return R.ok().put("time", articleService.collect(id, user.getuId(), type, tid));
        }
        return R.error("必要参数为空");
    }


    /**
     * 取消收藏
     */
    @Login
    @PostMapping("/delCollect")
    @ApiOperation(value = "取消收藏", httpMethod = "POST", notes = "根据ID取消收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true),
            @ApiImplicitParam(name = "type", value = "(0：文章 1：帖子 2：活动)", required = true)
    })
    public ResultUtil delCollect(@RequestBody Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        if ((null != map.get("id") && StringUtil.isNotBlank(map.get("id")))
                && (null != map.get("type") && StringUtil.isNotBlank(map.get("type")))) {
            long id = Long.parseLong(String.valueOf(map.get("id")));
            int type = Integer.parseInt(String.valueOf(map.get("type")));
            Long tid = filterId(id, type);
            if (StringUtil.isBlank(tid)) {
                return ResultUtil.error("取消收藏失败");
            }
            String key = id + "-" + user.getuId() + "-" + tid + "-" + type;
            Long r = redisUtils.hremove(RedisKeys.FOCUS, key);
            if (r > 0) {
                return ResultUtil.ok();
            } else {
                return ResultUtil.error("取消收藏失败，请重试");
            }
        }
        return ResultUtil.error("必要参数为空");
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


    /**
     * 行业要闻&技术前沿
     */
    @GetMapping("/doubleArticle")
    @ApiOperation(value = "行业要闻 和 技术前沿", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "1：行业要闻  2：技术前沿", name = "status", required = true),
            @ApiImplicitParam(value = "排序规则 0：最热  1：推荐", name = "type", required = true),
    })
    public ResultUtil<List<InArticle>> doubleArticle(@RequestParam Map<String, Object> map) {
        List<InArticle> articles = articleService.doubleArticle(map);
        return ResultUtil.ok(articles);
    }


    /**
     * 首页 -- 标签
     */
    @GetMapping("/tagArticle")
    @ApiOperation(value = "首页 -- 标签", httpMethod = "GET", response = TagArticleVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "标签ID", name = "tId", required = true),
    })
    public ResultUtil<TagArticleVo> tagArticle(@RequestParam Map<String, Object> map) {
        TagArticleVo tagArticleVo = articleService.tagArticle(map);
        return ResultUtil.ok(tagArticleVo);
    }


    /**
     * 添加ES -- 文章 使用一次
     */
//    @GetMapping("/esSave")
    public R esSave() {
        List<InArticle> articles = articleService.all();
        List<EsArticleEntity> aEsList = BeanHelper.copyWithCollection(articles, EsArticleEntity.class);
        if (null != aEsList) {
            for (EsArticleEntity esArticle : aEsList) {
                rabbitTemplate.convertAndSend(Constants.articleExchange,
                        Constants.article_Save_RouteKey, esArticle);
            }
        }
        return R.ok();
    }

}
