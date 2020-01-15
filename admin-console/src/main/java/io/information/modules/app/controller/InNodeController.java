package io.information.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guansuo.common.StringUtil;
import io.information.common.utils.*;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InNode;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardBaseService;
import io.information.modules.app.service.IInNodeService;
import io.information.modules.app.vo.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
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
    @Autowired
    private IInCardBaseService baseService;
    @Autowired
    RedisUtils redisUtils;

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
        if (null != node) {
            //获取节点对应的帖子集合求出总数(size)
            int cardNumber = baseService.count(new LambdaQueryWrapper<InCardBase>().eq(InCardBase::getNoId, node.getNoId()));
            //将节点和对应帖子的总数放入集合
            node.setCardNumber(cardNumber);
        }
        return R.ok().put("node", node);
    }


    /**
     * 关注节点 <作者，节点，人物>
     */
    @Login
    @PostMapping("/focus")
    @ApiOperation(value = "关注节点", httpMethod = "POST", notes = "被关注的节点id")
    @ApiImplicitParam(value = "节点id", name = "noId", required = true)
    public R focus(@RequestBody Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        if (null != map.get("noId") && StringUtil.isNotBlank(map.get("noId"))) {
            long noId = Long.parseLong(String.valueOf(map.get("noId")));
            InNode node = nodeService.getById(noId);
            if (null != node) {
                String key = user.getuId() + "-" + node.getNoType() + "-" + noId;
                Object o = redisUtils.hget(RedisKeys.NODES, key);
                if (null != o) {
                    return R.error("已关注，请不要重复关注");
                }
                nodeService.focus(user.getuId(), noId, node.getNoType());
                return R.ok();
            }
            return R.error("不存在该节点");
        }
        return R.error("必要参数不存在");
    }


    /**
     * 取消关注节点
     */
    @Login
    @PostMapping("/delFocus")
    @ApiOperation(value = "取消关注节点", httpMethod = "POST", notes = "关注的节点id")
    @ApiImplicitParam(value = "节点id", name = "noId", required = true)
    public ResultUtil delFocus(@RequestBody Map<String, Object> map, @ApiIgnore @LoginUser InUser user) {
        if (null != map.get("noId") && StringUtil.isNotBlank(map.get("noId"))) {
            long noId = Long.parseLong(String.valueOf(map.get("noId")));
            InNode node = nodeService.getById(noId);
            if (null != node) {
                String key = user.getuId() + "-" + node.getNoType() + "-" + noId;
                Long r = redisUtils.hremove(RedisKeys.NODES, key);
                if (r > 0) {
                    nodeService.delFocus(user.getuId(), noId);
                    return ResultUtil.ok();
                } else {
                    return ResultUtil.error("取消关注失败，请重试");
                }
            }
            return ResultUtil.error("不存在该节点");
        }
        return ResultUtil.error("必要参数不存在");
    }


    /**
     * 是否已关注节点
     */
    @Login
    @GetMapping("/isFocus")
    @ApiOperation(value = "是否已关注节点", httpMethod = "GET", notes = "true：已关注  false：未关注")
    @ApiImplicitParam(value = "目标节点id", name = "noId", required = true)
    public boolean isFocus(@RequestParam Long noId, @ApiIgnore @LoginUser InUser user) {
        return nodeService.isFocus(user.getuId(), noId);
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
        HashMap<Integer, List<InNode>> hashMap = new HashMap<>();
        for (Map.Entry<Long, List<InNode>> entry : list.entrySet()) {
            hashMap.put(entry.getKey().intValue(), entry.getValue());
        }
        return R.ok().put("list", hashMap);
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
    @ApiOperation(value = "节点社区 -- 详情", httpMethod = "GET", notes = "分页数据，状态码", response = UserCardVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "节点ID", name = "noId", required = true),
            @ApiImplicitParam(value = "帖子类型（1：辩论 2：投票）", name = "cCategory", required = true),
            @ApiImplicitParam(value = "拍讯方式（1：最新 2：最热）", name = "type", required = true)
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
            @ApiImplicitParam(value = "帖子类型（1：辩论 2：投票）", name = "cCategory", required = true),
            @ApiImplicitParam(value = "拍讯方式（1：最新 2：最热）", name = "type", required = true)
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
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true)
    })
    @ApiResponse(code = 200, message = "list:{数字:{用户数据}  数字：用户类型 1：红人榜 2：黑榜 ")
    public R star(@RequestParam Map<String, Object> map) {
        Map<Integer, List<InUser>> list = nodeService.star(map);
        return R.ok().put("list", list);
    }


    /**
     * 人物社区 -- 详情
     */
    @GetMapping("/articleList")
    @ApiOperation(value = "人物社区 -- 详情", httpMethod = "GET", notes = "分页数据", response = UserArticleVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "用户ID", name = "uId", required = true),
    })
    public ResultUtil<UserArticleVo> articleList(@RequestParam Map<String, Object> map) {
        UserArticleVo userArticleVo = nodeService.articleList(map);
        return ResultUtil.ok(userArticleVo);
    }


    /**
     * 专栏社区
     */
    @GetMapping("/special")
    @ApiOperation(value = "专栏社区列表", httpMethod = "GET", response = UserNodeVo.class)
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
    public ResultUtil special(@RequestParam Map<String, Object> map) {
        PageUtils<List<UserNodeVo>> page = nodeService.special(map);
        return ResultUtil.ok(page);
    }


    /**
     * 专栏社区 -- 详情
     */
    @GetMapping("/specialList")
    @ApiOperation(value = "专栏社区 -- 详情", httpMethod = "GET", response = UserSpecialVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "每页显示条数", name = "pageSize", required = true),
            @ApiImplicitParam(value = "当前页数", name = "currPage", required = true),
            @ApiImplicitParam(value = "用户ID", name = "uId", required = true)
    })
    public ResultUtil<UserSpecialVo> specialList(@RequestParam Map<String, Object> map) {
        UserSpecialVo userSpecialVo = nodeService.specialList(map);
        return ResultUtil.ok(userSpecialVo);
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


    /**
     * 最新动态
     */
    @GetMapping("/dynamic")
    @ApiOperation(value = "社区 -- 最新动态[5条]", httpMethod = "GET", response = DynamicVo.class)
    public ResultUtil newDynamic() {
        List<DynamicVo> list = nodeService.newDynamic();
        return ResultUtil.ok(list);
    }

}