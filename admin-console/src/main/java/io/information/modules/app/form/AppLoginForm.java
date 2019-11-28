

package io.information.modules.app.form;

import com.guansuo.validgroups.CodeLogin;
import com.guansuo.validgroups.PwdLogin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * App登录表单
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@ApiModel(value = "登录表单")
public class AppLoginForm {
    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "请输入手机号")
    private String uPhone;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "请输入密码", groups = {PwdLogin.class})
    private String uPwd;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "请输入验证码", groups = {CodeLogin.class})
    private String code;

}
