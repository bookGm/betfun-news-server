package io.information.modules.app.controller;


import com.guansuo.common.JsonUtil;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardVoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 * 资讯投票帖详情（最多30个投票选项） 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/card/vote")
@Api(value = "/app/card/vote", tags = "APP帖子_投票帖子")
public class InCardVoteController {
    @Autowired
    private IInCardVoteService voteService;

    /**
     * 投票
     */
    @Login
    @PostMapping("/vote")
    @ApiOperation(value = "投票", httpMethod = "POST", notes = "根据帖子ID和选择的投票选项，更新投票选项数量")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "帖子ID", name = "cId", required = true),
            @ApiImplicitParam(value = "投票选项索引", name = "optIndexs", dataType = "List<int>", required = true)
    })
    public R vote(@RequestParam("cid") Long cid, @RequestParam(value = "optIndexs", required = false) List<Integer> optIndexs, @ApiIgnore @LoginUser InUser user) {
        List<Integer> vote =JsonUtil.parseList(voteService.vote(cid, user.getuId(), optIndexs),Integer.class);
        return R.ok().put("vote", vote);
    }


}
