package io.information.modules.app.controller;


import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInUserService;
import io.information.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/app/card/base")
@Api(value = "APP帖子_基础表")
public class InCardBaseController extends AbstractController {
    @Autowired
    private IInCardBaseService cardBaseService;
    @Autowired
    private IInUserService iInUserService;

    /**
     * 添加
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增基础帖子",httpMethod = "POST")
    @ApiImplicitParam(name="cardBase",value = "基础帖子信息",required =  true)
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
    @ApiOperation(value = "单个或批量删除基础帖子",httpMethod = "DELETE",notes = "根据cId[数组]删除基础帖子")
    @ApiImplicitParam(name = "cIds",value = "帖子ID",dataType = "Array",required = true)
    public ResponseEntity<Void> delete(@RequestBody Long[] cIds){
        cardBaseService.removeByIds(Arrays.asList(cIds));
        return ResponseEntity.status(HttpStatus.OK).build();
    }



    /**
     * 用户删除
     */
    @DeleteMapping("/deleteList")
    @ApiOperation(value = "删除用户的所有基础帖子",httpMethod = "DELETE",notes = "自动获取用户信息")
    public ResponseEntity<Void> deleteList(){
        cardBaseService.deleteAllCardBase(getUserId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 删除<包含关联表>
     */
    @DeleteMapping("/deleteAll")
    @ApiOperation(value = "同时删除用户的基础帖子、辩论帖子和投票帖子",httpMethod = "DELETE",notes = "自动获取用户信息")
    public ResponseEntity<Void> deleteAll(){
        cardBaseService.deleteAllCard(getUserId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改基础帖子",httpMethod = "PUT")
    @ApiImplicitParam(name = "cardBase",value = "基础帖子信息",required = true)
    public ResponseEntity<Void> update(@RequestBody InCardBase cardBase){
        cardBaseService.updateById(cardBase);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 查询
     */
    @GetMapping("/info/{cId}")
    @ApiOperation(value = "查询单个基础帖子",httpMethod = "GET")
    @ApiImplicitParam(name = "cId",value = "帖子ID",required = true)
    public ResponseEntity<InCardBase> info(@PathVariable("cId") Long cId){
        InCardBase cardBase = cardBaseService.getById(cId);
        return ResponseEntity.ok(cardBase);
    }


    /**
     * 查询<包含关联表>
     */
    @GetMapping("/infoList/{cId}")
    @ApiOperation(value = "同时查询基础帖子、辩论帖子和投票帖子",httpMethod = "GET")
    @ApiImplicitParam(name = "cId",value="帖子ID",required=true)
    public ResponseEntity<InCard> infoList(@PathVariable("cId") Long cId){
        InCard card = cardBaseService.queryCard(cId);
        return ResponseEntity.ok(card);
    }


    /**
     * 用户查询
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询全部基础帖子",httpMethod = "GET")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public ResponseEntity<PageUtils> list(@RequestParam Map<String,Object> map){
        PageUtils page = cardBaseService.queryPage(map);
        return ResponseEntity.ok(page);
    }

    /**
     * 用户查询
     */
    @GetMapping("/uIdList")
    @ApiOperation(value = "查询用户的基础帖子",httpMethod = "GET",notes = "自动获取用户信息")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public ResponseEntity<PageUtils> uIdList(@RequestParam Map<String,Object> map){
        PageUtils page = cardBaseService.queryAllCardBase(map,getUserId());
        return ResponseEntity.ok(page);
    }


    /**
     * 根据正反方ids字符串,查询用户信息
     */
    @GetMapping("/idsUser")
    @ApiOperation(value = "分割查询用户信息",httpMethod = "GET",notes = "根据正反方ids字符串，用 ，分隔")
    @ApiImplicitParam(name = "map",value = "分页数据、正反方ids字符串",required = true)
    public ResponseEntity<PageUtils> idsUser(@RequestParam Map<String,Object> map){
        PageUtils page = iInUserService.queryUsersByArgueIds(map);
        return ResponseEntity.ok(page);
    }
}
