package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InTag;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInTagService extends IService<InTag> {
    PageUtils queryPage(Map<String, Object> params);
}
