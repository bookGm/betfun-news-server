

package io.information.modules.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.information.modules.app.entity.UserEntity;
import io.information.modules.app.form.LoginForm;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	long login(LoginForm form);
}
