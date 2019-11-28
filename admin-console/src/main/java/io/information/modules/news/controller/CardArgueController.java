package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.CardArgueEntity;
import io.information.modules.news.service.CardArgueService;
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
    private CardArgueService cardArgueService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:cardargue:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cardArgueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{cId}")
    @RequiresPermissions("news:cardargue:info")
    public R info(@PathVariable("cId") Long cId) {
        CardArgueEntity cardArgue = cardArgueService.getById(cId);

        return R.ok().put("cardArgue", cardArgue);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:cardargue:save")
    public R save(@RequestBody CardArgueEntity cardArgue) {
        cardArgueService.save(cardArgue);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:cardargue:update")
    public R update(@RequestBody CardArgueEntity cardArgue) {
        cardArgueService.updateById(cardArgue);

        return R.ok();
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:cardargue:delete")
    public R delete(@RequestBody Long[] cIds) {
        cardArgueService.removeByIds(Arrays.asList(cIds));

        return R.ok();
    }

}
