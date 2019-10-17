package io.information.modules.app.controller;


import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInUserService;
import io.information.modules.sys.controller.AbstractController;
import io.mq.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
@Api(value = "/app/card/base",tags = "APP帖子_基础表")
public class InCardBaseController extends AbstractController {
    @Autowired
    private IInCardBaseService cardBaseService;
    @Autowired
    private IInUserService iInUserService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 添加
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增基础帖子",httpMethod = "POST")
    @ApiImplicitParam(name="cardBase",value = "基础帖子信息",required =  true)
    public R save(@RequestBody InCardBase cardBase){
        Long id= IdGenerator.getId();
        cardBase.setcId(id);
        cardBaseService.save(cardBase);
        //rabbit
        rabbitTemplate.convertAndSend(Constants.cardExchange,
                Constants.card_Save_RouteKey, cardBase);
        return R.ok();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "单个或批量删除基础帖子",httpMethod = "DELETE",notes = "根据cId[数组]删除基础帖子")
    @ApiImplicitParam(name = "cIds",value = "帖子ID",dataType = "Array",required = true)
    public R delete(@RequestBody Long[] cIds){
        cardBaseService.removeByIds(Arrays.asList(cIds));
        //rabbit
        rabbitTemplate.convertAndSend(Constants.cardExchange,
                Constants.card_Delete_RouteKey, cIds);
        return R.ok();
    }



    /**
     * 用户删除
     */
    @DeleteMapping("/deleteList")
    @ApiOperation(value = "删除用户的所有基础帖子",httpMethod = "DELETE",notes = "自动获取用户信息")
    public R deleteList(){
        cardBaseService.deleteAllCardBase(getUserId());
        return R.ok();
    }


    /**
     * 用户删除<包含关联表>
     */
    @DeleteMapping("/deleteAll")
    @ApiOperation(value = "同时删除用户的基础帖子、辩论帖子和投票帖子",httpMethod = "DELETE",notes = "自动获取用户信息")
    public R deleteAll(){
        cardBaseService.deleteAllCard(getUserId());
        return R.ok();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改基础帖子",httpMethod = "PUT")
    @ApiImplicitParam(name = "cardBase",value = "基础帖子信息",required = true)
    public R update(@RequestBody InCardBase cardBase){
        cardBaseService.updateById(cardBase);
        //rabbit
        rabbitTemplate.convertAndSend(Constants.cardExchange,
                Constants.card_Update_RouteKey, cardBase);
        return R.ok();
    }

    /**
     * 查询
     */
    @GetMapping("/info/{cId}")
    @ApiOperation(value = "查询单个基础帖子",httpMethod = "GET")
    @ApiImplicitParam(name = "cId",value = "帖子ID",required = true)
    public R info(@PathVariable("cId") Long cId){
        InCardBase cardBase = cardBaseService.getById(cId);
        return R.ok().put("cardBase",cardBase);
    }


    /**
     * 查询<包含关联表>
     */
    @GetMapping("/infoList/{cId}")
    @ApiOperation(value = "同时查询基础帖子、辩论帖子和投票帖子",httpMethod = "GET")
    @ApiImplicitParam(name = "cId",value="帖子ID",required=true)
    public R infoList(@PathVariable("cId") Long cId){
        InCard card = cardBaseService.queryCard(cId);
        return R.ok().put("card",card);
    }


    /**
     * 用户查询
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询全部基础帖子",httpMethod = "GET")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public R list(@RequestParam Map<String,Object> map){
        PageUtils page = cardBaseService.queryPage(map);
        return R.ok().put("page",page);
    }

    /**
     * 用户查询
     */
    @GetMapping("/uIdList")
    @ApiOperation(value = "查询用户的基础帖子",httpMethod = "GET",notes = "自动获取用户信息")
    @ApiImplicitParam(name = "map",value = "分页数据",required = true)
    public R uIdList(@RequestParam Map<String,Object> map){
        PageUtils page = cardBaseService.queryAllCardBase(map,getUserId());
        return R.ok().put("page",page);
    }


    /**
     * 根据正反方ids字符串,查询用户信息
     */
    @GetMapping("/idsUser")
    @ApiOperation(value = "分割查询用户信息",httpMethod = "GET",notes = "根据正反方ids字符串，用 ，分隔")
    @ApiImplicitParam(name = "map",value = "分页数据、正反方ids字符串",required = true)
    public R idsUser(@RequestParam Map<String,Object> map){
        PageUtils page = iInUserService.queryUsersByArgueIds(map);
        return R.ok().put("page",page);
    }
}
