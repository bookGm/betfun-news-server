package io.information.modules.app.controller;

import com.guansuo.common.JsonUtil;
import com.guansuo.newsenum.NewsEnum;
import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.*;
import io.information.modules.app.service.*;
import io.mq.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.Map;


/**
 * <p>
 * 评论回复表
 * </p>
 *
 * @author zxs
 * @since 2019-10-08 15:11:28
 */
@Api(value = "/app/commonreply", tags = "APP评论与回复")
@RestController
@RequestMapping("/app/commonreply")
public class InCommonReplyController {
    @Autowired
    private IInCommonReplyService commonReplyService;
    @Autowired
    private IInArticleService articleService;
    @Autowired
    private IInCardBaseService cardBaseService;
    @Autowired
    private IInActivityService activityService;
    @Autowired
    IInUserService iInUserService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 信息
     */
    @GetMapping("/info/{crId}")
    @ApiOperation(value = "单个评论信息", httpMethod = "GET", notes = "评论ID[crId]")
    public R info(@PathVariable("crId") Long crId) {
        InCommonReply commonReply = commonReplyService.getById(crId);

        return R.ok().put("commonReply", commonReply);
    }

    /**
     * 保存
     */
    @Login
    @PostMapping("/save")
    @ApiOperation(value = "保存评论信息", httpMethod = "POST", notes = "目标类型（0文章，1帖子，2活动，3用户）")
    public R save(@RequestBody InCommonReply commonReply, @ApiIgnore @LoginUser InUser user) {
        commonReply.setCrId(IdGenerator.getId());
        commonReply.setCrTime(new Date());
        commonReply.setcId(user.getuId());
        commonReplyService.save(commonReply);
        addCritic(commonReply);
        commonReply.setcName(user.getuNick());
        commonReply.setcPhoto(user.getuPhoto());
        commonReply.setCrSimpleTime("刚刚");
        if (commonReply.gettType().equals(NewsEnum.评论_帖子)) {
            logOperate(user.getuId(), commonReply.gettId(), commonReply.gettName(), NewsEnum.操作_评论);
        }
        return R.ok().put("crObj", commonReply);
    }

    /**
     * 记录操作日志
     */
    void logOperate(Long uId, Long tId, String tName, NewsEnum e) {
        InLog log = new InLog();
        log.setlOperateId(uId);
        log.setlTargetId(tId);
        log.setlTargetType(1);
        log.setlTargetName(tName);
        log.setlDo(Integer.parseInt(e.getCode()));
        log.setlTime(new Date());
        rabbitTemplate.convertAndSend(Constants.logExchange, Constants.log_Save_RouteKey, JsonUtil.toJSONString(log));
    }

    //添加评论数量
    @Async
    public void addCritic(InCommonReply commonReply) {
        Integer type = commonReply.gettType();
        if (null != type) {
            switch (type) {
                case 0:
                    InArticle article = articleService.getById(commonReply.gettId());
                    if (null != article) {
                        article.setaCritic(article.getaCritic() + 1);
                        articleService.updateById(article);
                    }
                    break;
                case 1:
                    InCardBase cardBase = cardBaseService.getById(commonReply.gettId());
                    if (null != cardBase) {
                        cardBase.setcCritic(cardBase.getcCritic() + 1);
                        cardBaseService.updateById(cardBase);
                    }
                    break;
                case 2:
                    InActivity activity = activityService.getById(commonReply.gettId());
                    if (null != activity) {
                        activity.setActCritic(activity.getActCritic() + 1);
                        activityService.updateById(activity);
                    }
                    break;
            }
        }
    }


    /**
     * 评论列表
     */
    @GetMapping("/discuss")
    @ApiOperation(value = "查询某个页面的评论列表", httpMethod = "GET", notes = "分页数据，目标类型[tType]，ID[id]")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "目标类型（0文章，1帖子，2活动，3用户）", name = "tType", required = true),
            @ApiImplicitParam(value = "页面ID", name = "tId", required = true)
    })
    public R discuss(@RequestParam Map<String, Object> map) {
        if (null == map || !map.containsKey("tId") || !map.containsKey("tType")) {
            return R.error("缺少必要参数");
        }
        PageUtils discuss = commonReplyService.discuss(map);
        return R.ok().put("discuss", discuss);
    }


    /**
     * 回复列表 <根据 评论ID信息 查询回复>
     */
    @GetMapping("/revert")
    @ApiOperation(value = "查询某个评论的回复信息", httpMethod = "GET", notes = "分页数据，评论ID[id]")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "评论ID", name = "id", required = true)
    })
    public R revert(@RequestParam Map<String, Object> map) {
        PageUtils revert = commonReplyService.revert(map);
        return R.ok().put("revert", revert);
    }
}
