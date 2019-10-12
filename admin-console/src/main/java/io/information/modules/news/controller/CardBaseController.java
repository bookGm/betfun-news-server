package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.CardBaseEntity;
import io.information.modules.news.entity.CardVo;
import io.information.modules.news.service.CardBaseService;
import io.information.modules.sys.controller.AbstractController;
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
public class CardBaseController extends AbstractController {
    @Autowired
    private CardBaseService cardBaseService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("news:cardbase:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = cardBaseService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{cId}")
    @RequiresPermissions("news:cardbase:info")
    public R info(@PathVariable("cId") Long cId) {
        CardBaseEntity cardBase = cardBaseService.getById(cId);

        return R.ok().put("cardBase", cardBase);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:cardbase:save")
    public R save(@RequestBody CardBaseEntity cardBase) {
        cardBase.setuId(getUserId());
        cardBaseService.save(cardBase);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:cardbase:update")
    public R update(@RequestBody CardBaseEntity cardBase) {
        cardBaseService.updateById(cardBase);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:cardbase:delete")
    public R delete(@RequestBody Long[] cIds) {
        cardBaseService.removeByIds(Arrays.asList(cIds));

        return R.ok();
    }

    /////////////////////////////////////////////////

    /**
     * 添加帖子<包含关联表>
     */
    @PostMapping("/saveAll")
    @RequiresPermissions("news:cardbase:listAll")
    public R addCard(@RequestBody CardVo cardVo) {
        cardBaseService.addCard(cardVo);
        return R.ok();
    }


    /**
     * 删除帖子<包含关联表>
     */
    @PostMapping("/deleteAll")
    @RequiresPermissions("news:cardbase:deleteAll")
    public R deleteCard(@RequestBody Long[] cardIds) {
        cardBaseService.deleteCard(cardIds);
        return R.ok();
    }


    /**
     * 用户帖子<包含关联表>
     */
    @GetMapping("/uIdAll")
    @RequiresPermissions("news:cardbase:listUCard")
    public R queryAllCard(@RequestParam Map<String, Object> params) {
        PageUtils pageAll = cardBaseService.queryAllCard(params, getUserId());
        return R.ok().put("pageAll", pageAll);
    }

}
