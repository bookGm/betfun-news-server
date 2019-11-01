package io.information.modules.app.controller;


import io.information.common.utils.R;
import io.information.common.utils.RedisKeys;
import io.information.modules.app.entity.InDic;
import io.information.modules.app.service.IInDicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯字典表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/dic")
@Api(value = "/app/dic", tags = "APP字典表")
public class InDicController {
    @Autowired
    private IInDicService dicService;


    /**
     * 所有字典列表
     */
    @GetMapping("/listAll")
    public R listAll() {
        Map<String, List<InDic>> listAll = dicService.getListAll("dics");
        return R.ok().put("dict", listAll);
    }


    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取所有字典节点信息", httpMethod = "GET", notes = "不需要任何参数")
    public R list() {
        List<InDic> dicList = dicService.list();
        return R.ok().put("dicList", dicList);
    }


    /**
     * 选择字典(添加、修改字典)
     */
    @GetMapping("/select")
    public R select() {
        //查询列表数据
        List<InDic> dicList = dicService.queryDidAscList();

        //添加顶级字典节点
        InDic root = new InDic();
        root.setdId(0L);
        root.setdName("顶级节点");
        root.setpId(-1L);
        root.setOpen(true);
        dicList.add(root);

        return R.ok().put("dicList", dicList);
    }


    /**
     * 查询
     */
    @GetMapping("/info/{dId}")
    @ApiOperation(value = "查询节点信息", httpMethod = "GET", notes = "根据字典ID查询节点及其子节点")
    @ApiImplicitParam(name = "dId", value = "字典ID", dataType = "Array")
    public R info(@PathVariable("dId") Long dId) {
        List<InDic> dics = dicService.queryDicById(dId);
        return R.ok().put("dicList", dics);
    }



}
