package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InLog;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LX
 * @since 2019-11-07
 */
public interface IInLogService extends IService<InLog> {
    PageUtils queryPage(Map<String, Object> params);
}
