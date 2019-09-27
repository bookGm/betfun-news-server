package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.CardBaseEntity;
import io.information.modules.news.service.CardBaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 资讯帖子基础表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@RestController
@RequestMapping("news/cardbase")
public class CardBaseController {
    @Autowired
    private CardBaseService cardBaseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:cardbase:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cardBaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{cId}")
    @RequiresPermissions("news:cardbase:info")
    public R info(@PathVariable("cId") Long cId){
		CardBaseEntity cardBase = cardBaseService.getById(cId);

        return R.ok().put("cardBase", cardBase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:cardbase:save")
    public R save(@RequestBody CardBaseEntity cardBase){
		cardBaseService.save(cardBase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:cardbase:update")
    public R update(@RequestBody CardBaseEntity cardBase){
		cardBaseService.updateById(cardBase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:cardbase:delete")
    public R delete(@RequestBody Long[] cIds){
		cardBaseService.removeByIds(Arrays.asList(cIds));

        return R.ok();
    }

}
