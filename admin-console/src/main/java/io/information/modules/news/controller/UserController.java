package io.information.modules.news.controller;

import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.UserEntity;
import io.information.modules.news.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 资讯用户表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@RestController
@RequestMapping("news/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{uId}")
    @RequiresPermissions("news:user:info")
    public R info(@PathVariable("uId") Long uId){
		UserEntity user = userService.getById(uId);

        return R.ok().put("user", user);
    }


    /**
     * 根据用户昵称查询 <昵称不可重复>
     * @return
     */
    @GetMapping("/exact")
    public R queryUserByNick(String nick){
        PageUtils pageNice = userService.queryUserByNick(nick);
        return R.ok().put("pageNice",pageNice);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:user:save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:user:update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:user:delete")
    public R delete(@RequestBody Long[] uIds){
		userService.removeByIds(Arrays.asList(uIds));

        return R.ok();
    }

}
