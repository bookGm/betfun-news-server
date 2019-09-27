package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.CardVoteEntity;
import io.information.modules.news.service.CardVoteService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 资讯投票帖详情（最多30个投票选项）
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:24
 */
@RestController
@RequestMapping("news/cardvote")
public class CardVoteController {
    @Autowired
    private CardVoteService cardVoteService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:cardvote:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = cardVoteService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{cId}")
    @RequiresPermissions("news:cardvote:info")
    public R info(@PathVariable("cId") Long cId){
		CardVoteEntity cardVote = cardVoteService.getById(cId);

        return R.ok().put("cardVote", cardVote);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:cardvote:save")
    public R save(@RequestBody CardVoteEntity cardVote){
		cardVoteService.save(cardVote);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:cardvote:update")
    public R update(@RequestBody CardVoteEntity cardVote){
		cardVoteService.updateById(cardVote);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:cardvote:delete")
    public R delete(@RequestBody Long[] cIds){
		cardVoteService.removeByIds(Arrays.asList(cIds));

        return R.ok();
    }

}
