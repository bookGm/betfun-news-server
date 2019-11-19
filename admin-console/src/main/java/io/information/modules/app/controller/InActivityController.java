package io.information.modules.app.controller;


import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.common.utils.ResultUtil;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.entity.InActivityDatas;
import io.information.modules.app.entity.InActivityFields;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInActivityDatasService;
import io.information.modules.app.service.IInActivityService;
import io.information.modules.sys.entity.SysCitysEntity;
import io.information.modules.sys.service.SysCitysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
    @Autowired
    private IInActivityDatasService datasService;
    @Autowired
    SysCitysService sysCitysService;


    /**
     * 添加
     */
    @Login
    @PostMapping("/save")
    @ApiOperation(value = "新增咨讯活动", httpMethod = "POST", response = InActivity.class)
    public ResultUtil<InActivity> save(@RequestBody InActivity activity, @ApiIgnore @LoginUser InUser user) {
        activity.setActId(IdGenerator.getId());
        activity.setActCreateTime(new Date());
        activity.setuId(user.getuId());
        activity.setActStatus(1);
        activityService.saveActivity(activity);
        return ResultUtil.ok();
    }


    /**
     * 删除
     */
    @Login
    @ApiOperation(value = "删除咨讯活动", httpMethod = "DELETE", notes = "根据actId[数组]删除活动")
    @ApiImplicitParam(value = "活动ID[数组]", name = "actIds", required = true)
    @DeleteMapping("/delete")
    public R delete(@RequestParam Long[] actIds) {
        activityService.removeActivity(Arrays.asList(actIds));
        return R.ok();
    }


    /**
     * 修改
     */
    @Login
    @PutMapping("/update")
    @ApiOperation(value = "修改咨讯活动", httpMethod = "PUT")
    public R update(@RequestBody InActivity activity) {
        activityService.updateActivity(activity);
        return R.ok();
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取已审核的咨讯活动", httpMethod = "GET", notes = "分页数据", response = InActivity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "分类（-1：最新 0：全部 1：峰会 2：线上 3:其他）", name = "type", required = true)
    })
    public ResultUtil listOk(@RequestParam Map<String, Object> map) {
        PageUtils page = activityService.queryPage(map);
        return ResultUtil.ok(page);
    }

    /**
     * 列表
     */
    @Login
    @GetMapping("/userActivity")
    @ApiOperation(value = "获取本人发布的活动", httpMethod = "GET", notes = "分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    public R userActivity(@RequestParam Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        map.put("uId", user.getuId());
        PageUtils page = activityService.queryPage(map);
        return R.ok().put("page", page);
    }

    /**
     * 查询
     */
    @GetMapping("/info/{actId}")
    @ApiOperation(value = "查询单个活动信息", httpMethod = "GET", notes = "活动ID[actId]")
    public R info(@PathVariable("actId") Long actId) {
        InActivity activity = activityService.getById(actId);
        String[] split = activity.getActAddr().split("-");
        StringBuilder actAddName = new StringBuilder();
        for (String s : split) {
            long id = Long.parseLong(s);
            String name = sysCitysService.getById(id).getName();
            actAddName.append(name).append("-");
        }
        activity.setActAddrName(actAddName.toString());
        return R.ok().put("activity", activity);
    }


    /**
     * 获取报名活动的用户
     */
    @GetMapping("/pass")
    @ApiOperation(value = "获取报名活动的用户", httpMethod = "GET", notes = "分页数据，活动ID")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "活动ID", name = "actId", required = true)
    })
    public R pass(@RequestParam Map<String, Object> map) {
        PageUtils page = datasService.pass(map);
        return R.ok().put("page", page);
    }


    /**
     * 获取活动报名数据
     */
    @ApiOperation(value = "活动报名数据", httpMethod = "GET", notes = "活动ID[actId]")
    @GetMapping("/apply/{actId}")
    public R apply(@PathVariable("actId") Long actId) {
        List<InActivityFields> fieldsList = activityService.apply(actId);
        return R.ok().put("fieldsList", fieldsList);
    }

    /**
     * 获取所有地区信息
     */
    @ApiOperation(value = "所有地区信息", httpMethod = "GET")
    @GetMapping("/listAll")
    public R list() {
        Map<String, List<SysCitysEntity>> listAll = sysCitysService.getListAll("citys");
        return R.ok().put("citys", listAll);
    }


    /**
     * 活动报名
     */
    @Login
    @ApiOperation(value = "活动报名", httpMethod = "POST", notes = "活动数据集合[datasList]", response = InActivityDatas.class)
    @PostMapping("/join")
    public R join(@RequestBody List<InActivityDatas> datasList, @ApiIgnore @LoginUser InUser user) {
        InActivityDatas data = datasList.get(0);
        Long actId = data.getActId();
        InActivity activity = activityService.getById(actId);
        Long actNum = activity.getActNum();
        Long actInNum = activity.getActInNum();
        if (actInNum >= actNum) {
            return R.error("报名失败，报名人数已达上限");
        } else {
            activity.setActInNum(activity.getActInNum() + 1);
            activityService.updateById(activity);
            for (InActivityDatas datas : datasList) {
                datas.setdId(IdGenerator.getId());
                datas.setuId(user.getuId());
                datas.setdTime(new Date());
                datasService.save(datas);
            }
            return R.ok();
        }
    }

}
