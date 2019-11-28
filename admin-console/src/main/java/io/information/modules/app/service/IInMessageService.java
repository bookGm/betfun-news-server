package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InMessage;

import java.util.Map;

/**
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-11-25 10:56:47
 */
public interface IInMessageService extends IService<InMessage> {
    PageUtils queryPage(Map<String, Object> params);
}

