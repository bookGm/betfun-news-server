package io.information.modules.news.controller;

import com.guansuo.common.StringUtil;
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
    @GetMapping("/list")
    @RequiresPermissions("news:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{uId}")
    @RequiresPermissions("news:user:info")
    public R info(@PathVariable("uId") Long uId) {
        UserEntity user = userService.getById(uId);

        return R.ok().put("user", user);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("news:user:save")
    public R save(@RequestBody UserEntity user) {
        userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("news:user:update")
    public R update(@RequestBody UserEntity user) {
        userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("news:user:delete")
    public R delete(@RequestBody Long[] uIds) {
        userService.removeByIds(Arrays.asList(uIds));

        return R.ok();
    }


    /**
     * 审核
     */
    @RequestMapping("/auditList")
    @RequiresPermissions("news:user:list")
    public R audit(@RequestParam Map<String, Object> params) {
        PageUtils page = userService.audit(params);
        return R.ok().put("page", page);
    }


    /**
     * 已通过
     */
    @PostMapping("/auditOk")
    @RequiresPermissions("news:user:update")
    public R auditOk(@RequestBody Map<String, Object> map) {
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            long actId = Long.parseLong(String.valueOf(map.get("uId")));
            UserEntity userEntity = new UserEntity();
            userEntity.setuId(actId);
            userEntity.setuAuthStatus(2);
            userService.updateById(userEntity);
            return R.ok();
        }
        return R.error("缺少必要的参数");
    }


    /**
     * 未通过
     */
    @PostMapping("/auditNo")
    @RequiresPermissions("news:user:update")
    public R auditNo(@RequestBody Map<String, Object> map) {
        if (null != map.get("uId") && StringUtil.isNotBlank(map.get("uId"))) {
            long actId = Long.parseLong(String.valueOf(map.get("uId")));
            UserEntity userEntity = new UserEntity();
            userEntity.setuId(actId);
            userEntity.setuAuthStatus(0);
            userEntity.setuAuthType(null);
            userService.updateById(userEntity);
            return R.ok();
        }
        return R.error("缺少必要的参数");
    }
}
