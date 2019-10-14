package io.information.modules.app.controller;


import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInUserService;
import io.information.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 资讯用户表 前端控制器
 * </p>
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/app/user")
public class InUserController extends AbstractController {
    @Autowired
    private IInUserService iInUserService;

    //TODO 微信扫码登录

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestBody Long[] uIds){
        boolean flag = iInUserService.removeByIds(Arrays.asList(uIds));
        return ResponseEntity.ok(flag);
    }


    /**
     * 更新
     */
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody InUser user){
        boolean flag = iInUserService.updateById(user);
        return ResponseEntity.ok(flag);
    }


    /**
     * 根模糊匹配用户
     */
    @GetMapping("/queryLike")
    public ResponseEntity<List<InUser>> queryLikeByUser(String params){
        List<InUser> users = iInUserService.queryLikeByUser(params);
        return ResponseEntity.ok(users);
    }


    /**
     * 用户昵称查询
     */
    @GetMapping("/queryName")
    public ResponseEntity<InUser> queryUserByNick(String nick){
        InUser user = iInUserService.queryUserByNick(nick);
        return ResponseEntity.ok(user);
    }


}
