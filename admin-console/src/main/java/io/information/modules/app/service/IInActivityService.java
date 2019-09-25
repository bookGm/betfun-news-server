package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.modules.app.entity.InActivity;

import java.util.List;

/**
 * <p>
 * 资讯活动表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInActivityService extends IService<InActivity> {

    List<InActivity> queryActivitiesByUserId(Long userId);

    void deleteAllActive(Long userId);

}
