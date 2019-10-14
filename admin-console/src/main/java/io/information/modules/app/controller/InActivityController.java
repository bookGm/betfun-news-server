package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.service.IInActivityService;
import io.information.modules.sys.controller.AbstractController;
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
public class InActivityController extends AbstractController {
    @Autowired
    private IInActivityService activityService;

    /**
     * 添加
     */
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
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody Long[] actIds) {
        activityService.removeByIds(Arrays.asList(actIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 用户删除
     */
    @DeleteMapping("/deleteList")
    public ResponseEntity<Void> deleteList() {
        activityService.deleteAllActive(getUserId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody InActivity activity) {
        activityService.updateById(activity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 用户查询
     */
    @GetMapping("/list")
    public ResponseEntity<PageUtils> list(@RequestParam Map<String, Object> params) {
        PageUtils pageUtils = activityService.queryActivitiesByUserId(params, getUserId());
        return ResponseEntity.ok(pageUtils);
    }


    /**
     * 查询
     */
    @GetMapping("/info/{actId}")
    public ResponseEntity<InActivity> info(@PathVariable("actId") Long actId) {
        InActivity activity = activityService.getById(actId);
        return ResponseEntity.ok(activity);
    }

}
