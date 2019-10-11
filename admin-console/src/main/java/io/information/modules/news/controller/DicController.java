package io.information.modules.news.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.information.common.utils.R;
import io.information.common.utils.RedisKeys;
import io.information.modules.news.entity.DicEntity;
import io.information.modules.news.service.DicService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 资讯字典表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-29 13:13:05
 */
@RestController
@RequestMapping("news/dic")
public class DicController {
    @Autowired
    private DicService dicService;


    /**
     * 所有字典列表
     */
    @GetMapping("/listAll")
    public R listAll(String key){
        Map<String, List<DicEntity>> listAll = dicService.getListAll(RedisKeys.CONSTANT);
        return R.ok().put("dict", listAll);
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:dic:list")
    public List<DicEntity> list(){
        List<DicEntity> dicList = dicService.list();
        return dicList;
    }


    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("news:dic:select")
    public R select(){
        //查询列表数据
        List<DicEntity> dicList = dicService.queryDidAscList();

        //添加顶级菜单
        DicEntity root = new DicEntity();
        root.setdId(0L);
        root.setdName("顶级编码");
        root.setpId(-1L);
        root.setOpen(true);
        dicList.add(root);

        return R.ok().put("dicList", dicList);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{dId}")
    @RequiresPermissions("news:dic:info")
    public R info(@PathVariable("dId") Long dId){
		DicEntity dic = dicService.getById(dId);

        return R.ok().put("dic", dic);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:dic:save")
    public R save(@RequestBody DicEntity dic){
		dicService.save(dic);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:dic:update")
    public R update(@RequestBody DicEntity dic){
		dicService.updateById(dic);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{dId}")
    @RequiresPermissions("news:dic:delete")
    public R delete(@PathVariable("dId") Long[] dIds){
        //删除编码先判断是包含子编码
        QueryWrapper<DicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(DicEntity::getpId,Arrays.asList(dIds));
        List<DicEntity> dicEntities = dicService.list(queryWrapper);
        if(dicEntities.isEmpty()){
            dicService.removeByIds(Arrays.asList(dIds));
        }else {
            return R.error("请先删除子编码");
        }

        return R.ok();
    }

}
