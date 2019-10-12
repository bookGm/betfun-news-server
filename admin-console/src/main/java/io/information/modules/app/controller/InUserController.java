package io.information.modules.app.controller;


import io.information.common.exception.ExceptionEnum;
import io.information.common.exception.IMException;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资讯用户表 前端控制器
 * </p>
 *  TODO
 * @author ZXS
 * @since 2019-09-24
 */
@RestController
@RequestMapping("/news/user")
public class InUserController {
    @Autowired
    private IInUserService iInUserService;


    //TODO 注册

    /**
     * 根据用户ID删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(Long userId){
        boolean flag = iInUserService.removeById(userId);
        return ResponseEntity.ok(flag);
    }


    /**
     * 更新用户信息<用户ID>
     * @param user
     * @return
     */
    @PutMapping("/update")
    public ResponseEntity update(InUser user){
        boolean flag = iInUserService.updateById(user);
        return ResponseEntity.ok(flag);
    }


    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login")
    public ResponseEntity<InUser> findUser(String username,String password){
        if(username != null || password !=null){
            InUser user = iInUserService.findUser(username,password);
            return ResponseEntity.ok(user);
        }else {
            //返回自定义异常
            throw new IMException(ExceptionEnum.USER_PASSWORD_ERROR);
        }
    }


    /**
     * 根据条件模糊匹配用户信息<部分信息>
     * @param params 条件<用户名，用户昵称，手机号>
     * @return
     */
    @GetMapping("/queryLike")
    public ResponseEntity<List<InUser>> queryLikeByUser(String params){
        List<InUser> users = iInUserService.queryLikeByUser(params);
        return ResponseEntity.ok(users);
    }


    /**
     * 根据用户昵称查询 <昵称不可重复>
     * @return
     */
    @GetMapping("/queryName")
    public ResponseEntity<InUser> queryUserByNick(String nick){
        InUser user = iInUserService.queryUserByNick(nick);
        return ResponseEntity.ok(user);
    }


    //TODO 手机短信验证登录

}
