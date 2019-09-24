package io.information.modules.app.controller;


import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.service.IInActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 资讯活动表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/news/activity")
public class InActivityController {
    @Autowired
    private IInActivityService activityService;

    /**
     * 添加新活动
     * @param activity
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Void> addActivity(InActivity activity){
        activity.setActId(new IdWorker().nextId());
        activity.setActCreateTime(LocalDateTime.now());

        activityService.save(activity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据活动ID删除活动
     * @param activeIds
     * @return
     */
    @DeleteMapping("/activeId")
    public ResponseEntity<Void> deleteActive(List<Long> activeIds){
        activityService.removeByIds(activeIds);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据用户ID批量删除用户活动
     * @param userId
     * @return
     */
    @DeleteMapping("/userId")
    public ResponseEntity<Void> deleteAllActive(Long userId){
        activityService.deleteAllActive(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改活动信息
     * @param activity
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity<Void> updateActivity(InActivity activity){
        activityService.updateById(activity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据用户ID查询活动信息
     * @param userId
     * @return
     */
    @GetMapping("/userId")
    public ResponseEntity<List<InActivity>> queryActivitiesByUserId(Long userId){
        List<InActivity> activities = activityService.queryActivitiesByUserId(userId);
        return ResponseEntity.ok(activities);
    }


    /**
     * 根据活动ID查询活动信息
     * @param activeId
     * @return
     */
    @GetMapping("/activeId")
    public ResponseEntity<InActivity> queryActiveById(Long activeId){
        InActivity activity = activityService.getById(activeId);
        return ResponseEntity.ok(activity);
    }


    //TODO 获取活动分类<字典>

}
