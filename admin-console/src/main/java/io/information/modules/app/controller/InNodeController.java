package io.information.modules.app.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.common.utils.ResultUtil;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InNode;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInNodeService;
import io.information.modules.app.vo.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 帖子节点表  前端控制器
 * </p>
 *
 * @author zxs
 * @since 2019-11-04
 */
@RestController
@RequestMapping("app/node")
@Api(value = "/app/node", tags = "APP社区接口")
public class InNodeController {
    @Autowired
    private IInNodeService nodeService;

    /**
     * 查询
     */

    @GetMapping("/search")
    @ApiOperation(value = "根据属性查询节点", httpMethod = "GET")
    @ApiImplicitParam(value = "节点属性", name = "noType", required = true)
    public R search(@RequestParam Long noType) {
        Map<Long, String> map = nodeService.search(noType);
        return R.ok().put("map", map);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{noId}")
    @ApiOperation(value = "查询节点信息", httpMethod = "GET", notes = "节点ID", response = InNode.class)
    public R info(@PathVariable("noId") Long noId) {
        InNode node = nodeService.getById(noId);
        return R.ok().put("node", node);
    }


    /**
     * 关注节点 <作者，节点，人物>
     */
    @Login
    @PostMapping("/focus")
    @ApiOperation(value = "关注节点", httpMethod = "POST", notes = "被关注的节点id")
    @ApiImplicitParam(value = "节点id", name = "noId", required = true)
    public R focus(@RequestParam Long noId, @ApiIgnore @LoginUser InUser user) {
        InNode node = nodeService.getById(noId);
        nodeService.focus(user.getuId(), noId, node.getNoType());
        return R.ok();
    }


    /**
     * 节点社区列表
     */
    @GetMapping("/nodeList")
    @ApiOperation(value = "节点社区列表", httpMethod = "GET", notes = "分页数据", response = InNode.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
    })
    @ApiResponse(code = 200, message = "list:{数字：{节点社区数据}}  数字：节点类型  2：项目 3：社区 4：平台 5：资本 ")
    public R nodeList(@RequestParam Map<String, Object> map) {
        Map<Long, List<InNode>> list = nodeService.query(map);
        return R.ok().put("list", list);
    }


    /**
     * 社区 -- 帖子详情 --发布者信息和帖子推荐
     */
    @GetMapping("/cardRecommended")
    @ApiOperation(value = "社区 -- 帖子详情 -- 发布者信息[右上]", httpMethod = "GET", notes = "分页数据，用户ID", response = CardUserVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "用户ID", name = "uId", required = true)
    })
    public ResultUtil<CardUserVo> cardRecommended(@RequestParam Map<String, Object> map) {
        CardUserVo cardUserVo = nodeService.cardRecommended(map);
        return ResultUtil.ok(cardUserVo);
    }


    /**
     * 内部帖子列表
     */
    @GetMapping("/cardList")
    @ApiOperation(value = "节点社区内部帖子列表", httpMethod = "GET", notes = "分页数据，状态码", response = UserCardVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "节点ID", name = "noId", required = true),
            @ApiImplicitParam(value = "帖子类型（0：投票 1：辩论）", name = "cCategory", required = false),
            @ApiImplicitParam(value = "拍讯方式（0：最新 1：最热）", name = "type", required = false)
    })
    public ResultUtil<PageUtils<UserCardVo>> cardList(@RequestParam Map<String, Object> map) {
        PageUtils<UserCardVo> page = nodeService.cardList(map);
        return ResultUtil.ok(page);
    }


    /**
     * 首页 -- 社区
     */
    @GetMapping("/list")
    @ApiOperation(value = "首页 -- 社区", httpMethod = "GET", notes = "分页数据，状态码", response = UserCardVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "帖子类型（0：投票 1：辩论）", name = "cCategory", required = false),
            @ApiImplicitParam(value = "拍讯方式（0：最新 1：最热）", name = "type", required = false)
    })
    public ResultUtil<PageUtils<UserCardVo>> list(@RequestParam Map<String, Object> map) {
        PageUtils<UserCardVo> page = nodeService.cardList(map);
        return ResultUtil.ok(page);
    }


    /**
     * 人物社区
     */
    @GetMapping("/star")
    @ApiOperation(value = "人物社区列表", httpMethod = "GET", notes = "分页数据", response = InUser.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
    })
    @ApiResponse(code = 200, message = "list:{数字:{用户数据}  数字：用户类型 1：红人榜 2：黑榜 ")
    public R star(@RequestParam Map<String, Object> map) {
        Map<Integer, List<InUser>> list = nodeService.star(map);
        return R.ok().put("list", list);
    }


    /**
     * 专栏社区
     */
    @GetMapping("/special")
    @ApiOperation(value = "专栏社区列表", httpMethod = "GET", response = InUser.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
    })
    @ApiResponses(@ApiResponse(code = 200, message = "{\r\n" +
            "    \"aCount\": \"累计文章数\",\r\n" +
            "    \"rCount\": \"累计阅读量\",\r\n" +
            "    \"kCount\": \"累计点赞数\",\r\n" +
            "    \"aCount\": \"累计文章数\",\r\n" +
            "    \"user\": \"用户信息\",\r\n" +
            "    \"totalCount\": \"总条数\",\r\n" +
            "    \"totalPage\": \"总页数\"\r\n" +
            "  }"))
    public R special(@RequestParam Map<String, Object> map) {
        Map<String, Object> userMap = nodeService.special(map);
        return R.ok().put("userMap", userMap);
    }

    /**
     * 社区 -- 推荐节点
     */
    @GetMapping("/recommendNode")
    @ApiOperation(value = "社区 -- 推荐节点[6条]", httpMethod = "GET", response = NodeVo.class)
    public ResultUtil<List<NodeVo>> recommendNode() {
        List<NodeVo> nodeVos = nodeService.recommendNode();
        return ResultUtil.ok(nodeVos);
    }


    /**
     * 社区 -- 热帖榜
     */
    @GetMapping("/heatCard")
    @ApiOperation(value = "社区 -- 热帖榜[5条]", httpMethod = "GET", response = CardBaseVo.class)
    public ResultUtil<List<CardBaseVo>> heatCard() {
        List<CardBaseVo> cardBaseVos = nodeService.heatCard();
        return ResultUtil.ok(cardBaseVos);
    }


    //最新动态 TODO
    //获取5条回复  获取5条发布帖子  按时间排序  再取前5条

    /**
     * 最新动态
     */
    @GetMapping("/newDynamic")
    @ApiOperation(value = "社区 -- 最新动态[5条]", httpMethod = "GET", response = NewDynamicVo.class)
    public ResultUtil<NewDynamicVo> newDynamic() {
        NewDynamicVo newDynamicVo = nodeService.newDynamic();
        return ResultUtil.ok(newDynamicVo);
    }
}