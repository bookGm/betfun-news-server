package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCardArgue;
import io.information.modules.app.service.IInCardArgueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯帖子辩论表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/news/card/argue")
public class InCardArgueController {
    @Autowired
    private IInCardArgueService cardArgueService;

    /**
     * 添加辩论帖子
     */
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody InCardArgue cardArgue){
        cardArgueService.save(cardArgue);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据帖子ID删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody Long[] cIds){
        cardArgueService.removeByIds(Arrays.asList(cIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改辩论帖子
     */
    @PutMapping("/update")
    public ResponseEntity<Void> updateCardArgue(@RequestBody InCardArgue cardArgue){
        cardArgueService.updateById(cardArgue);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据帖子ID查询
     */
    @GetMapping("/info/{cId}")
    public ResponseEntity<InCardArgue> queryCardArgue(@PathVariable("cId") Long cId){
        InCardArgue cardArgue = cardArgueService.getById(cId);
        return ResponseEntity.ok(cardArgue);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseEntity<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = cardArgueService.queryPage(params);
        return ResponseEntity.ok(page);
    }

}
