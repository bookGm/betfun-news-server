package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.entity.InActivityFields;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯活动表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInActivityService extends IService<InActivity> {

    void removeActivity(List<Long> actIds);

    void updateActivity(InActivity activity);

    void saveActivity(InActivity activity);

    List<InActivityFields> apply(Long actId);

    PageUtils queryPage(Map<String, Object> params);

    InActivity details(Long actId);

    String signUp(Long actId, Long getuId);

    boolean isApply(Long uId, Long actId);

    List<InActivity> interested();

}
