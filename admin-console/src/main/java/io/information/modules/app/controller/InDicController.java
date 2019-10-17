package io.information.modules.app.controller;


import io.information.common.utils.R;
import io.information.common.utils.RedisKeys;
import io.information.modules.app.config.IdWorker;
import io.information.modules.app.entity.InDic;
import io.information.modules.app.service.IInDicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(value = "/app/dic", tags = "APP咨讯字典接口")
public class InDicController {
    @Autowired
    private IInDicService dicService;


    /**
     * 所有字典列表
     */
    @GetMapping("/listAll")
    public R listAll() {
        Map<String, List<InDic>> listAll = dicService.getListAll(RedisKeys.CONSTANT);
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
     * 新增
     */
    @PostMapping("/save")
    @ApiOperation(value = "新增字典节点", httpMethod = "POST")
    @ApiImplicitParam(name = "dic", value = "字典信息", required = true)
    public R save(@RequestBody InDic dic) {
        dic.setdId(new IdWorker().nextId());
        dicService.save(dic);
        return R.ok();
    }


    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "单个或批量删除字典节点", httpMethod = "DELETE", notes = "根据dId[数组]删除字典节点")
    @ApiImplicitParam(name = "dIds", value = "字典ID", required = true)
    public R delete(@RequestBody Long[] dIds) {
        dicService.deleteDic(dIds);
        return R.ok();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改字典节点", httpMethod = "PUT")
    @ApiImplicitParam(name = "dic", value = "字典信息", required = true)
    public R update(@RequestBody InDic dic) {
        dicService.updateDic(dic);
        return R.ok();
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


    /**
     * 编码查询
     */
    @GetMapping("/infoCode/{dCode}")
    @ApiOperation(value = "dCode查询节点信息", httpMethod = "GET", notes = "根据编码查询节点及其子节点")
    public R infoCode(@PathVariable("dCode") String dCode) {
        List<InDic> dics = dicService.queryDicByCode(dCode);
        return R.ok().put("dicList", dics);
    }


    /**
     * 名称模糊查询
     */
    @GetMapping("/infoLike/{dName}")
    @ApiOperation(value = "dName查询节点信息", httpMethod = "GET", notes = "根据名称模糊查询节点及其子节点")
    public R infoLike(@PathVariable("dName") String dName) {
        List<InDic> dicList = dicService.queryNameDic(dName);
        return R.ok().put("dicList", dicList);
    }


}
