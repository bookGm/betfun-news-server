package io.information.modules.app.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guansuo.common.SmsUtil;
import com.guansuo.common.StringUtil;
import com.guansuo.template.SmsTemplate;
import com.guansuo.validgroups.CodeLogin;
import com.guansuo.validgroups.PwdLogin;
import io.information.common.utils.IdGenerator;
import io.information.common.utils.R;
import io.information.common.utils.RedisKeys;
import io.information.common.utils.RedisUtils;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.form.AppLoginForm;
import io.information.modules.app.form.RegisterForm;
import io.information.modules.app.service.IInUserService;
import io.information.modules.app.utils.JwtUtils;
import io.mq.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * APP登录授权
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/app")
@Api(tags = "APP登录接口")
public class AppLoginController {
    @Autowired
    private IInUserService iInUserService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RedisUtils redis;


    @ApiOperation(value = "登录获取手机验证码")
    @PostMapping("getLoginVerificationCode")
    public R getLoginVerificationCode(String phone) {
        String rkey = RedisKeys.LOGIN_PHONECODE + phone;
        if (redis.hasKey(rkey)) return R.error("请稍后发送");
        if (StringUtil.isBlank(phone)) return R.error(HttpStatus.SC_UNAUTHORIZED, "请输入手机号码！");
        LambdaQueryWrapper<InUser> qw = new LambdaQueryWrapper<InUser>();
        qw.eq(InUser::getuPhone, phone);
        InUser user = iInUserService.getOne(qw);
        if (null == user) {
            return R.error("不存在该用户，请先注册!");
        }
        int rand = 100000 + (int) (Math.random() * 899999);
        if (rand > 0) {
            Boolean status = SmsUtil.sendSMS(user.getuPhone(), MessageFormat.format(SmsTemplate.loginCodeTemplate, rand + ""));
            if (status) {
                redis.set(rkey, String.valueOf(rand), 60);
                return R.ok();
            } else {
                return R.error("短信发送失败，请重试");
            }
        }
        return R.error("短信发送失败，请重试");
    }

    @ApiOperation(value = "注册获取手机验证码")
    @PostMapping("getRegistVerificationCode")
    public R getRegistVerificationCode(String phone) {
        String rkey = RedisKeys.LOGIN_PHONECODE + phone;
        if (redis.hasKey(rkey)) return R.error("请稍后发送");
        if (StringUtil.isBlank(phone)) return R.error(HttpStatus.SC_UNAUTHORIZED, "请输入手机号码！");
        LambdaQueryWrapper<InUser> qw = new LambdaQueryWrapper<InUser>();
        qw.eq(InUser::getuPhone, phone);
        InUser user = iInUserService.getOne(qw);
        if (null != user) {
            return R.error("手机号码已被注册");
        }
        int rand = 100000 + (int) (Math.random() * 899999);
        if (rand > 0) {
            String msg = MessageFormat.format(SmsTemplate.loginCodeTemplate, rand + "");
            Boolean status = SmsUtil.sendSMS(phone, msg);
            if (status) {
                redis.set(rkey, String.valueOf(rand), 60);
                return R.ok();
            } else {
                return R.error("短信发送失败，请重试");
            }
        }
        return R.error("短信发送失败，请重试");
    }

    @PostMapping("pwdLogin")
    @ApiOperation("密码登录")
    public R pwdLogin(@RequestBody @Validated(PwdLogin.class) AppLoginForm form) {
        //用户登录
        LambdaQueryWrapper<InUser> qw = new LambdaQueryWrapper<InUser>();
        qw.eq(InUser::getuPhone, form.getUPhone());
        InUser user = iInUserService.getOne(qw);
        if (null == user || !user.getuPwd().equals(new Sha256Hash(form.getUPwd(), user.getuSalt()).toHex())) {
            return R.error("" +
                    "手机号或密码不正确");
        }
        return resultToken(user.getuId(), user.getuAuthStatus());
    }

    @PostMapping("codeLogin")
    @ApiOperation("验证码登录")
    public R codeLogin(@RequestBody @Validated(CodeLogin.class) AppLoginForm form) {
        String rkey = RedisKeys.LOGIN_PHONECODE + form.getUPhone();
        if (!redis.hasKey(rkey)) {
            return R.error("验证码已超时");
        }
        LambdaQueryWrapper<InUser> qw = new LambdaQueryWrapper<InUser>();
        qw.eq(InUser::getuPhone, form.getUPhone());
        InUser user = iInUserService.getOne(qw);
        if (null == user) {
            return R.error("手机号不存在");
        }
        if (redis.get(rkey).equals(form.getCode())) {
            redis.remove(rkey);
            return resultToken(user.getuId(), user.getuAuthStatus());
        } else {
            return R.error("验证码输入错误");
        }

    }

    @PostMapping("codeRegist")
    @ApiOperation("验证码注册")
    public R codeRegist(@RequestBody @Validated(CodeLogin.class) RegisterForm form) {
        String rkey = RedisKeys.LOGIN_PHONECODE + form.getPhone();
        if (!redis.hasKey(rkey)) {
            return R.error("验证码已超时");
        }
        LambdaQueryWrapper<InUser> qw = new LambdaQueryWrapper<InUser>();
        qw.eq(InUser::getuPhone, form.getPhone());
        InUser user = iInUserService.getOne(qw);
        if (null != user) {
            return R.error("手机号码已被注册");
        }
        if (redis.get(rkey).equals(form.getCode())) {
            Long uid = IdGenerator.getId();
            R r = resultToken(uid, 0);
            String salt = RandomStringUtils.randomAlphanumeric(20);
            String phone = form.getPhone();
            user = new InUser();
            user.setuId(uid);
            user.setuAccount(phone);
            user.setuSalt(salt);
            user.setuPhone(phone);
            user.setuSalt(new Sha256Hash(phone.substring(phone.length() - 6), salt).toHex());
            user.setuToken(r.get("token").toString());
            if (iInUserService.saveWithCache(user)) {
                rabbitTemplate.convertAndSend(Constants.userExchange,
                        Constants.user_Save_RouteKey, JSON.toJSON(user));
                return r;
            } else {
                return R.error("注册失败");
            }
        } else {
            return R.error("验证码输入错误");
        }
    }

    public R resultToken(Long userid, int authStatus) {
        //生成token
        String token = jwtUtils.generateToken(userid);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());
        map.put("authStatus", authStatus);
        return R.ok(map);
    }

}
