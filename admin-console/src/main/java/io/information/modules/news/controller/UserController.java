package io.information.modules.news.controller;

import com.guansuo.common.StringUtil;
import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.MessageEntity;
import io.information.modules.news.entity.UserEntity;
import io.information.modules.news.service.MessageService;
import io.information.modules.news.service.UserService;
import io.mq.utils.Constants;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
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
    @Autowired
    private MessageService messageService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

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
        user.setuId(IdGenerator.getId());
        user.setuCreateTime(new Date());
        user.setuPotential(-1);
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
            long uId = Long.parseLong(String.valueOf(map.get("uId")));
            UserEntity userEntity = new UserEntity();
            userEntity.setuId(uId);
            userEntity.setuAuthStatus(2);
            userService.updateById(userEntity);
            MessageEntity message = new MessageEntity();
            message.setmId(IdGenerator.getId());
            //0：个人 1：媒体 2：企业
            String type = "";
            switch (userEntity.getuAuthStatus()) {
                case 0:
                    type = "个人";
                    break;
                case 1:
                    type = "媒体";
                    break;
                case 2:
                    type = "企业";
                    break;
            }
            message.setmContent("您提交的观索专栏" + type + "认证已通过审核");
            message.settId(uId);
            message.setmCreateTime(new Date());
            messageService.save(message);
            rabbitTemplate.convertAndSend(Constants.systemExchange,
                    Constants.system_Save_RouteKey, message);
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
            long uId = Long.parseLong(String.valueOf(map.get("uId")));
            UserEntity userEntity = new UserEntity();
            userEntity.setuId(uId);
            userEntity.setuAuthStatus(0);
            userEntity.setuAuthType(null);
            userService.updateById(userEntity);
            MessageEntity message = new MessageEntity();
            message.setmId(IdGenerator.getId());
            //0：个人 1：媒体 2：企业
            String type = "";
            switch (userEntity.getuAuthStatus()) {
                case 0:
                    type = "个人";
                    break;
                case 1:
                    type = "媒体";
                    break;
                case 2:
                    type = "企业";
                    break;
            }
            message.setmContent("您提交的观索专栏" + type + "认证未通过审核");
            message.settId(uId);
            message.setmCreateTime(new Date());
            messageService.save(message);
            rabbitTemplate.convertAndSend(Constants.systemExchange,
                    Constants.system_Save_RouteKey, message);
            return R.ok();
        }
        return R.error("缺少必要的参数");
    }
}
