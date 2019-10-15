package io.information.modules.app.controller;


import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInUserService;
import io.information.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯用户表 前端控制器
 * </p>
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
    @ApiOperation(value = "单个或批量删除咨讯用户")
    public ResponseEntity<Boolean> delete(@RequestBody Long[] uIds){
        boolean flag = iInUserService.removeByIds(Arrays.asList(uIds));
        return ResponseEntity.ok(flag);
    }


    /**
     * 更新
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改咨讯用户信息")
    public ResponseEntity update(@RequestBody InUser user){
        boolean flag = iInUserService.updateById(user);
        return ResponseEntity.ok(flag);
    }


    /**
     * 根模糊匹配用户
     */
    @GetMapping("/queryLike")
    @ApiOperation(value = "昵称模糊匹配用户")
    public ResponseEntity<PageUtils> queryLikeByUser(@RequestParam Map<String,Object> params){
        PageUtils page = iInUserService.queryLikeByUser(params);
        return ResponseEntity.ok(page);
    }


}
