package io.information.modules.app.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.app.annotation.Login;
import io.information.modules.app.annotation.LoginUser;
import io.information.modules.app.entity.InMessage;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * <p>
 * 资讯系统表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-11-25
 */
@RestController
@RequestMapping("app/message")
@Api(value = "/app/message", tags = "APP资讯消息 -- 系统")
public class InMessageController {
    @Autowired
    private IInMessageService messageService;

    /**
     * 列表
     */
    @Login
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, @LoginUser InUser user) {
        params.put("uId", user.getuId());
        PageUtils page = messageService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @Login
    @RequestMapping("/info/{mId}")
    @ApiOperation(value = "获取系统消息详情", httpMethod = "GET", notes = "消息ID[mId]", response = InMessage.class)
    public R info(@PathVariable("mId") Long mId) {
        InMessage message = messageService.getById(mId);
        return R.ok().put("message", message);
    }

}
