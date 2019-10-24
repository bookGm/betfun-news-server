package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InActivityTicket;

import java.util.Map;

/**
 *  活动票
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 13:32:16
 */
public interface IInActivityTicketService extends IService<InActivityTicket> {

    PageUtils queryPage(Map<String, Object> params);
}

