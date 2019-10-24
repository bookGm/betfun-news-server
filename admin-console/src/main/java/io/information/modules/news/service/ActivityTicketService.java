package io.information.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.news.entity.ActivityTicketEntity;

import java.util.Map;

/**
 *  活动票
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 13:32:16
 */
public interface ActivityTicketService extends IService<ActivityTicketEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

