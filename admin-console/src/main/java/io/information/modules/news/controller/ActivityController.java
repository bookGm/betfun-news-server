package io.information.modules.news.controller;

import com.guansuo.common.JsonUtil;
import com.guansuo.common.StringUtil;
import io.information.common.utils.IdGenerator;
import io.information.common.utils.PageUtils;
import io.information.common.utils.R;
import io.information.modules.news.entity.ActivityEntity;
import io.information.modules.news.entity.MessageEntity;
import io.information.modules.news.service.ActivityService;
import io.information.modules.news.service.MessageService;
import io.information.modules.sys.controller.AbstractController;
import io.mq.utils.Constants;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 资讯活动表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-08 17:01:27
 */
@RestController
@RequestMapping("news/activity")
public class ActivityController extends AbstractController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("news:activity:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = activityService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 审核
     */
    @RequestMapping("/auditList")
    public R audit(@RequestParam Map<String, Object> params) {
        PageUtils page = activityService.audit(params);
        return R.ok().put("page", page);
    }


    /**
     * 已通过
     */
    @PostMapping("/auditOk")
    @RequiresPermissions("news:activity:update")
    public R auditOk(@RequestBody Map<String, Object> map) {
        if (null != map.get("actId") && StringUtil.isNotBlank(map.get("actId"))) {
            long actId = Long.parseLong(String.valueOf(map.get("actId")));
            ActivityEntity activity = new ActivityEntity();
            activity.setActId(actId);
            activity.setActStatus(2);
            activityService.updateById(activity);
            MessageEntity message = new MessageEntity();
            message.setmId(IdGenerator.getId());
            message.setmContent("恭喜，您发布的活动《" + activity.getActTitle() + "》已通过审核");
            message.settId(activity.getuId());
            message.setmCreateTime(new Date());
            messageService.save(message);
            rabbitTemplate.convertAndSend(Constants.systemExchange,
                    Constants.system_Save_RouteKey, JsonUtil.toJSONString(message));
            return R.ok();
        }
        return R.error("缺少必要的参数");
    }


    /**
     * 未通过
     */
    @PostMapping("/auditNo")
    @RequiresPermissions("news:activity:update")
    public R auditNo(@RequestBody Map<String, Object> map) {
        if (null != map.get("actId") && StringUtil.isNotBlank(map.get("actId"))) {
            long actId = Long.parseLong(String.valueOf(map.get("actId")));
            ActivityEntity activity = new ActivityEntity();
            activity.setActId(actId);
            activity.setActStatus(0);
            activityService.updateById(activity);
            MessageEntity message = new MessageEntity();
            message.setmId(IdGenerator.getId());
            message.setmContent("很遗憾，您发布的活动《" + activity.getActTitle() + "》未通过审核");
            message.settId(activity.getuId());
            message.setmCreateTime(new Date());
            messageService.save(message);
            rabbitTemplate.convertAndSend(Constants.systemExchange,
                    Constants.system_Save_RouteKey, JsonUtil.toJSONString(message));
            return R.ok();
        }
        return R.error("缺少必要的参数");
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{actId}")
    @RequiresPermissions("news:activity:info")
    public R info(@PathVariable("actId") Long actId) {
        ActivityEntity activity = activityService.getById(actId);

        return R.ok().put("activity", activity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("news:activity:save")
    public R save(@RequestBody ActivityEntity activity) {
        activity.setuId(getUserId());
        activity.setActCreateTime(new Date());
        activityService.save(activity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("news:activity:update")
    public R update(@RequestBody ActivityEntity activity) {
        activityService.updateById(activity);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("news:activity:delete")
    public R delete(@RequestBody Long[] actIds) {
        activityService.removeByIds(Arrays.asList(actIds));

        return R.ok();
    }

}
