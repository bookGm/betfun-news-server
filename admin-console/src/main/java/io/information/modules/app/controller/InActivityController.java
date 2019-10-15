package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.service.IInActivityService;
import io.information.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "APP咨讯活动接口")
public class InActivityController extends AbstractController {
    @Autowired
    private IInActivityService activityService;

    /**
     * 添加
     */
    @ApiOperation(value = "新增咨讯活动", httpMethod = "POST")
    @ApiImplicitParam(name = "activity", value = "活动信息", required = true)
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestParam InActivity activity) {
        activity.setActCreateTime(new Date());
        activity.setuId(getUserId());
        activityService.save(activity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除
     */
    @ApiOperation(value = "单个批量删除咨讯活动", httpMethod = "DELETE", notes = "根据actId[数组]删除活动")
    @ApiImplicitParam(name = "actIds", value = "活动ID", required = true, dataType = "Array")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody Long[] actIds) {
        activityService.removeByIds(Arrays.asList(actIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 用户删除
     */
    @DeleteMapping("/deleteList")
    @ApiOperation(value = "删除用户发布的所有活动",httpMethod = "DELETE",notes = "自动获取当前用户")
    public ResponseEntity<Void> deleteList() {
        activityService.deleteAllActive(getUserId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改咨讯活动",httpMethod = "PUT")
    @ApiImplicitParam(name = "activity", value = "活动信息", required = true)
    public ResponseEntity<Void> update(@RequestBody InActivity activity) {
        activityService.updateById(activity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取全部咨讯活动",httpMethod = "GET")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public ResponseEntity<PageUtils> list(@RequestParam Map<String, Object> map) {
        PageUtils pageUtils = activityService.queryPage(map);
        return ResponseEntity.ok(pageUtils);
    }

    /**
     * 用户查询
     */
    @GetMapping("/userActivity")
    @ApiOperation(value = "获取用户发布的活动",httpMethod = "GET",notes = "自动获取用户信息")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public ResponseEntity<PageUtils> userActivity(@RequestParam Map<String, Object> params) {
        PageUtils pageUtils = activityService.queryActivitiesByUserId(params, getUserId());
        return ResponseEntity.ok(pageUtils);
    }

    /**
     * 查询
     */
    @GetMapping("/info/{actId}")
    @ApiOperation(value = "查询单个活动信息", httpMethod = "GET",notes = "根据actId查询活动信息")
    @ApiImplicitParam(name = "actId",value = "活动ID",required = true)
    public ResponseEntity<InActivity> info(@PathVariable("actId") Long actId) {
        InActivity activity = activityService.getById(actId);
        return ResponseEntity.ok(activity);
    }

}
