package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.CardArgueEntity;
import io.information.modules.news.entity.CardBaseEntity;
import io.information.modules.news.service.CardArgueService;
import io.information.modules.news.service.CardBaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 资讯帖子辩论表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@RestController
@RequestMapping("news/cardargue")
public class CardArgueController {
    @Autowired
    private CardArgueService argueService;
    @Autowired
    private CardBaseService baseService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:cardargue:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = argueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{cId}")
    @RequiresPermissions("news:cardargue:info")
    public R info(@PathVariable("cId") Long cId) {
        CardArgueEntity argue = argueService.getById(cId);
        CardBaseEntity base = baseService.getById(cId);
//        argue.setuName(base.getuName());
        return R.ok().put("cardArgue", argue);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:cardargue:save")
    public R save(@RequestBody CardArgueEntity cardArgue) {
        argueService.save(cardArgue);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:cardargue:update")
    public R update(@RequestBody CardArgueEntity cardArgue) {
        argueService.updateById(cardArgue);

        return R.ok();
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:cardargue:delete")
    public R delete(@RequestBody Long[] cIds) {
        argueService.removeByIds(Arrays.asList(cIds));

        return R.ok();
    }

}
