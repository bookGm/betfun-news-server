package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.NodeEntity;
import io.information.modules.news.service.NodeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-11-04 09:11:01
 */
@RestController
@RequestMapping("news/node")
public class NodeController {
    @Autowired
    private NodeService nodeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:node:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = nodeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{noId}")
    @RequiresPermissions("news:node:info")
    public R info(@PathVariable("noId") Long noId){
		NodeEntity node = nodeService.getById(noId);

        return R.ok().put("node", node);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:node:save")
    public R save(@RequestBody NodeEntity node){
		nodeService.save(node);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:node:update")
    public R update(@RequestBody NodeEntity node){
		nodeService.updateById(node);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:node:delete")
    public R delete(@RequestBody Long[] noIds){
		nodeService.removeByIds(Arrays.asList(noIds));

        return R.ok();
    }

}
