

package io.information.modules.sys.controller;

import io.information.common.annotation.SysLog;
import io.information.common.utils.*;
import io.information.common.validator.Assert;
import io.information.common.validator.ValidatorUtils;
import io.information.common.validator.group.AddGroup;
import io.information.common.validator.group.UpdateGroup;
import io.information.modules.sys.entity.SysCitysEntity;
import io.information.modules.sys.entity.SysUserEntity;
import io.information.modules.sys.form.PasswordForm;
import io.information.modules.sys.service.SysCitysService;
import io.information.modules.sys.service.SysUserRoleService;
import io.information.modules.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.Cacheable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 省市县（区）
 *
 * @author LX
 */
@RestController
@RequestMapping("/sys/citys")
public class SysCitysController extends AbstractController {
    @Autowired
	SysCitysService sysCitysService;
	@Autowired
	RedisUtils redis;

	/**
	 * 所有城市列表
	 */
	@GetMapping("/listAll")
	public R list(String key){
		Map<String, List<SysCitysEntity>> listAll = sysCitysService.getListAll(RedisKeys.CONSTANT_CITYS);
		return R.ok().put("citys",listAll );
	}

}
