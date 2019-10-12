package io.information.modules.app.controller;


import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInUserService;
import io.information.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯帖子基础表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/news/card/base")
public class InCardBaseController extends AbstractController {
    @Autowired
    private IInCardBaseService cardBaseService;
    @Autowired
    private IInUserService iInUserService;

    /**
     * 添加
     */
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody InCardBase cardBase){
        Long id= IdGenerator.getId();
        cardBase.setcId(id);
        cardBaseService.save(cardBase);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody Long[] cIds){
        cardBaseService.removeByIds(Arrays.asList(cIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }



    /**
     * 用户删除
     */
    @DeleteMapping("/deleteList")
    public ResponseEntity<Void> deleteList(){
        cardBaseService.deleteAllCardBase(getUserId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除<包含关联表>
     */
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll(){
        cardBaseService.deleteAllCard(getUserId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody InCardBase cardBase){
        cardBaseService.updateById(cardBase);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 查询
     */
    @GetMapping("/info/{cId}")
    public ResponseEntity<InCardBase> info(@PathVariable("cId") Long cId){
        InCardBase cardBase = cardBaseService.getById(cId);
        return ResponseEntity.ok(cardBase);
    }


    /**
     * 查询<包含关联表>
     */
    @GetMapping("/infoList/{cId}")
    public ResponseEntity<InCard> infoList(@PathVariable("cId") Long cId){
        InCard card = cardBaseService.queryCard(cId);
        return ResponseEntity.ok(card);
    }


    /**
     * 用户查询
     */
    @GetMapping("/uIdList")
    public ResponseEntity<List<InCardBase>> uIdList(){
        List<InCardBase> cardBaseList = cardBaseService.queryAllCardBase(getUserId());
        return ResponseEntity.ok(cardBaseList);
    }


    /**
     * 用户帖子<包含关联表>
     */
    @GetMapping("/uIdAll")
    public ResponseEntity<PageUtils> uIdAll(@RequestParam Map<String,Object> params){
        PageUtils page = cardBaseService.queryAllCard(params,getUserId());
        return ResponseEntity.ok(page);
    }


    /**
     * 根据正反方ids字符串,查询用户信息
     * TODO
     */
    @GetMapping("/idsUser")
    public ResponseEntity<List<InUser>> idsUser(String userIds){
        List<InUser> userList = iInUserService.queryUsersByArgueIds(userIds);
        return ResponseEntity.ok(userList);
    }
}
