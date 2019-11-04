package io.information.modules.app.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.entity.InNode;
import io.information.modules.app.service.IInNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "/app/article", tags = "APP社区接口")
public class InNodeController {
    @Autowired
    private IInNodeService nodeService;

    /**
     * 查询
     */

    @GetMapping("/search")
    @ApiOperation(value = "根据属性查询节点", httpMethod = "POST")
    @ApiImplicitParam(value = "节点属性", name = "noType", required = true)
    public R search(@RequestParam Long noType) {
        Map<Long, String> map = nodeService.search(noType);
        return R.ok().put("map", map);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{noId}")
    @ApiOperation(value = "查询节点信息", httpMethod = "POST", notes = "节点ID")
    public R info(@PathVariable("noId") Long noId) {
        InNode node = nodeService.getById(noId);
        return R.ok().put("node", node);
    }


    /**
     * 节点社区列表
     */
    @GetMapping("/nodeList")
    @ApiOperation(value = "节点社区列表", httpMethod = "POST", notes = "分页数据，状态码[noType]")
    @ApiImplicitParam(value = "2：项目 3：社区 4：平台 5：资本", name = "noType", required = true)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "2：项目 3：社区 4：平台 5：资本", name = "noType", required = true),
    })
    public R nodeList(@RequestParam Map<String, Object> map) {
        PageUtils page = nodeService.query(map);
        return R.ok().put("page", page);
    }

    /**
     * 节点社区内部帖子列表
     * 根据节点ID和排序方式查询帖子
     */
    @GetMapping("/cardList")
    @ApiOperation(value = "节点社区内部帖子列表", httpMethod = "POST", notes = "分页数据，状态码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "帖子类型（0：投票 1：辩论）", name = "cCategory", required = false),
            @ApiImplicitParam(value = "拍讯方式（0：最新 1：最热）", name = "type", required = false)
    })
    public R cardList(@RequestParam Map<String, Object> map) {
        PageUtils page = nodeService.cardList(map);
        return R.ok().put("page", page);
    }


    /**
     * 人物社区
     */
    @GetMapping("/star")
    @ApiOperation(value = "新增咨讯文章", httpMethod = "POST", notes = "分页数据，状态码")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "类型（0：红人榜 1：黑榜）", name = "type", required = true),
    })
    public R star(@RequestParam Map<String, Object> map) {
        PageUtils page = nodeService.star(map);
        return R.ok().put("page", page);
    }


    /**
     * 专栏社区
     */
    @GetMapping("/special")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
    })
    @ApiOperation(value = "新增咨讯文章", httpMethod = "POST")
    public R special(@RequestParam Map<String, Object> map) {
        Map<String, Object> userMap = nodeService.special(map);
        return R.ok().put("userMap", userMap);
    }

}