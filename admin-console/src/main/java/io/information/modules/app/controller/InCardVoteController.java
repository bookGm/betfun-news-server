package io.information.modules.app.controller;


import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInCardVoteService;
import io.information.modules.app.vo.VoteVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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
            @ApiImplicitParam(value = "投票选项索引", name = "optIndexs", dataType = "int[]", required = true)
    })
    public R vote(@RequestBody VoteVo v, @ApiIgnore @LoginUser InUser user) {
        voteService.vote(v.getcId(), user.getuId(), v.getOptIndexs());
        return R.ok();
    }


}
