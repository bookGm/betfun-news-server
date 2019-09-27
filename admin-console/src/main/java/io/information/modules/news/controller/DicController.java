package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.DicEntity;
import io.information.modules.news.service.DicService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 资讯字典表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@RestController
@RequestMapping("news/dic")
public class DicController {
    @Autowired
    private DicService dicService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:dic:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dicService.queryPage(params);

        return R.ok().put("page", page);
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
    @PostMapping("/delete")
    @RequiresPermissions("news:dic:delete")
    public R delete(@RequestBody Long[] dIds){
		dicService.removeByIds(Arrays.asList(dIds));

        return R.ok();
    }

}
