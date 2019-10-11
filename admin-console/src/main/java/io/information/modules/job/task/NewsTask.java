

package io.information.modules.job.task;

import io.information.common.utils.RedisKeys;
import io.information.common.utils.RedisUtils;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.service.IInUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component("newsTask")
public class NewsTask{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IInUserService iInUserService;
	@Autowired
	RedisUtils redisUtils;

	/**
	 * 定时同步app用户
	 */
	public void synchronizeInUser(){
		List<InUser> us=iInUserService.list();
		Map<Long,InUser> m=us.stream().collect(Collectors.toMap(InUser::getUId, u->u));
		redisUtils.hmset(RedisKeys.INUSER,m);
	}
}
