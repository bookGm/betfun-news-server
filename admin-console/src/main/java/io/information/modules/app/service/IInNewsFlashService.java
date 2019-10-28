package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InNewsFlash;

import java.util.Map;

/**
 * <p>
 * 资讯文章表 服务类
 * </p>
 *
 * @author LX
 * @since 2019-10-26
 */
public interface IInNewsFlashService extends IService<InNewsFlash> {

    PageUtils queryPage(Map<String, Object> params);
}
