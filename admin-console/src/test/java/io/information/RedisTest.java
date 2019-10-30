package io.information;

import com.guansuo.common.JsonUtil;
import io.information.common.utils.RedisUtils;
import io.information.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	@Autowired
	private RedisUtils redisUtils;

	@Test
	public void contextLoads() {
		SysUserEntity user = new SysUserEntity();
		user.setEmail("qqq@qq.com");
		redisUtils.set("user", JsonUtil.toJSONString(user));
		System.out.println(ToStringBuilder.reflectionToString(JsonUtil.parseObject(JsonUtil.toJSONString(redisUtils.get("user")),SysUserEntity.class)));
	}

}
