package io.information.modules.app.controller;

import com.guansuo.common.StringUtil;
import io.information.common.utils.*;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInCardService;
import io.information.modules.app.vo.CardArgueVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("/app/card")
@Api(value = "/app/card", tags = "APP帖子公共接口")
public class InCardController {
    @Autowired
    private IInCardService cardService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private IInCardBaseService cardBaseService;

    /**
     * 发布帖子
     */
    @Login
    @PostMapping("/issue")
    @ApiOperation(value = "发布帖子", httpMethod = "POST", response = InCard.class)
    public R issueCard(@RequestBody InCard card, @ApiIgnore @LoginUser InUser user) {
        cardService.issueCard(card, user);
        return R.ok();
    }


    /**
     * 帖子详情
     */
    @GetMapping("/details")
    @ApiOperation(value = "帖子详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "帖子id", name = "cId", required = true),
            @ApiImplicitParam(value = "登录用户id", name = "uId", required = false)
    })
    public R details(@RequestParam Map<String, Object> map, @ApiIgnore HttpServletRequest request) {
        if (null != map.get("cId") && StringUtil.isNotBlank(map.get("cId"))) {
            Long cId = Long.parseLong(String.valueOf(map.get("cId")));
            String ip = IPUtils.getIpAddr(request);
            InCard card = cardService.details(map);
            Boolean aBoolean = redisTemplate.hasKey(RedisKeys.CARDBROWSEIP + ip + cId);
            if (!aBoolean) {
                redisTemplate.opsForValue().set(RedisKeys.CARDBROWSEIP + ip + cId, String.valueOf(cId), 60 * 60 * 2);
                Long aLong = redisTemplate.opsForValue().increment(RedisKeys.CARDBROWSE + cId, 1);//如果通过自增1
                if (aLong % 100 == 0) {
                    redisTemplate.delete(ip + cId);
                    InCardBase base = cardBaseService.getById(cId);
                    long readNumber = aLong + base.getcReadNumber();
                    cardBaseService.updateReadNumber(readNumber, cId);
                }
            }
            return R.ok().put("card", card);
        }
        return R.error("必要参数为空");
    }


    @Login
    @GetMapping("/loginArgue")
    @ApiOperation(value = "查询用户的支持和加入状态", httpMethod = "GET", notes = "帖子ID")
    private ResultUtil<CardArgueVo> loginArgue(@RequestParam Long cId, @ApiIgnore @LoginUser InUser user) {
        CardArgueVo argueVo = cardService.loginArgue(cId, user.getuId());
        return ResultUtil.ok(argueVo);
    }


    /**
     * 帖子删除
     */
    @Login
    @DeleteMapping("/delete")
    @ApiOperation(value = "帖子删除", httpMethod = "DELETE", notes = "帖子ID数组")
    public R delete(@RequestParam Long[] cIds) {
        cardService.delete(cIds);
        return R.ok();
    }


    /**
     * 帖子修改
     */
    @Login
    @PutMapping("/update")
    @ApiOperation(value = "帖子修改", httpMethod = "PUT", notes = "帖子数据")
    public R update(@RequestBody InCard card) {
        cardService.update(card);
        return R.ok();
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取某一类帖子", httpMethod = "GET", notes = "分页数据，帖子分类[type]（0：普通帖  1：辩论帖  2：投票帖）")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "0：普通帖  1：辩论帖  2：投票帖", name = "status", required = true),
            @ApiImplicitParam(value = "0：最新  1：最热", name = "type", required = true)
    })
    public R status(@RequestParam Map<String, Object> map) {
        PageUtils page = cardService.queryPage(map);
        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @Login
    @GetMapping("/listUser")
    @ApiOperation(value = "获取本人发布的某类帖子", httpMethod = "GET", notes = "分页数据，帖子分类[type]（0：普通帖  1：辩论帖  2：投票帖）")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "0：普通帖  1：辩论帖  2：投票帖", name = "type", required = true)
    })
    public R stateUser(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = cardService.queryPage(map);
        return R.ok().put("page", page);
    }
}
