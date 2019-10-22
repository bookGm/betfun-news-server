package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInActivityService;
import io.information.modules.app.service.IInUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 资讯活动表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/activity")
@Api(value = "/app/activity", tags = "APP咨讯活动接口")
public class InActivityController {
    @Autowired
    private IInActivityService activityService;


    /**
     * 添加
     */
    @Login
    @ApiOperation(value = "新增咨讯活动", httpMethod = "POST")
    @ApiImplicitParam(name = "activity", value = "活动信息", required = true)
    @PostMapping("/save")
    public R save(@RequestParam InActivity activity, @ApiIgnore @LoginUser InUser user) {
        if (user.getuAuthStatus() == 2) {
            activity.setActCreateTime(new Date());
            activity.setuId(user.getuId());
            activityService.save(activity);
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }


    /**
     * 删除
     */
    @Login
    @ApiOperation(value = "删除咨讯活动", httpMethod = "DELETE", notes = "根据actId[数组]删除活动")
    @ApiImplicitParam(name = "actIds", value = "活动ID", required = true, dataType = "Array")
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] actIds,@ApiIgnore @LoginUser InUser user) {
        if (user.getuAuthStatus() == 2) {
            activityService.removeByIds(Arrays.asList(actIds));
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }


    /**
     * 修改
     */
    @Login
    @PutMapping("/update")
    @ApiOperation(value = "修改咨讯活动", httpMethod = "PUT")
    @ApiImplicitParam(name = "activity", value = "活动信息", required = true)
    public R update(@RequestBody InActivity activity,@ApiIgnore @LoginUser InUser user) {
        if (user.getuAuthStatus() == 2) {
            activityService.updateById(activity);
            return R.ok();
        }
        return R.error("此操作需要认证通过");
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取全部咨讯活动", httpMethod = "GET")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R list(@RequestParam Map<String, Object> map) {
        PageUtils page = activityService.queryPage(map);
        return R.ok().put("page", page);
    }

    /**
     * 用户查询
     */
    @Login
    @GetMapping("/userActivity")
    @ApiOperation(value = "获取用户发布的活动", httpMethod = "GET", notes = "自动获取用户信息")
    @ApiImplicitParam(name = "map", value = "分页数据", required = true)
    public R userActivity(@RequestParam Map<String, Object> params,@ApiIgnore @LoginUser InUser user) {
        PageUtils page = activityService.queryActivitiesByUserId(params, user.getuId());
        return R.ok().put("page", page);
    }

    /**
     * 查询
     */
    @GetMapping("/info/{actId}")
    @ApiOperation(value = "查询单个活动信息", httpMethod = "GET", notes = "根据actId查询活动信息")
    @ApiImplicitParam(name = "actId", value = "活动ID", required = true)
    public R info(@PathVariable("actId") Long actId) {
        InActivity activity = activityService.getById(actId);
        return R.ok().put("activity", activity);
    }

}
