package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInUserService;
import io.information.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 资讯用户表 前端控制器
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/user")
@Api(value = "APP咨讯用户接口")
public class InUserController extends AbstractController {
    @Autowired
    private IInUserService iInUserService;

    //TODO 微信扫码登录

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "单个或批量删除咨讯用户", httpMethod = "DELETE", notes = "根据uId[数组]删除用户")
    @ApiImplicitParam(name = "uIds", value = "用户ID", dataType = "Array", required = true)
    public ResponseEntity<Boolean> delete(@RequestBody Long[] uIds) {
        boolean flag = iInUserService.removeByIds(Arrays.asList(uIds));
        return ResponseEntity.ok(flag);
    }


    /**
     * 更新
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改咨讯用户信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "user", value = "用户信息", required = true)
    public ResponseEntity update(@RequestBody InUser user) {
        boolean flag = iInUserService.updateById(user);
        return ResponseEntity.ok(flag);
    }


    /**
     * 模糊匹配用户
     */
    @GetMapping("/queryLike")
    @ApiOperation(value = "模糊查询用户", httpMethod = "GET", notes = "根据用户昵称模糊匹配用户")
    @ApiImplicitParam(name = "map", value = "分页数据、用户昵称[uNick]", required = true)
    public ResponseEntity<PageUtils> queryLikeByUser(@RequestParam Map<String, Object> map) {
        PageUtils page = iInUserService.queryLikeByUser(map);
        return ResponseEntity.ok(page);
    }


}
