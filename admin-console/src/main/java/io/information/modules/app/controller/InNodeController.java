package io.information.modules.app.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.entity.InNode;
import io.information.modules.app.service.IInNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * <p>
 * 帖子节点表
 * </p>
 *
 * @author zxs
 * @since 2019-11-04
 */
@RestController
@RequestMapping("app/node")
public class InNodeController {
    @Autowired
    private IInNodeService nodeService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = nodeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 根据节点属性查询对应的节点信息
     */
    @GetMapping("/search")
    public R search(@RequestParam Long noType){
        Map<Long,String> map = nodeService.search(noType);
        return R.ok().put("map",map);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{noId}")
    public R info(@PathVariable("noId") Long noId) {
        InNode node = nodeService.getById(noId);
        return R.ok().put("node", node);
    }

}
