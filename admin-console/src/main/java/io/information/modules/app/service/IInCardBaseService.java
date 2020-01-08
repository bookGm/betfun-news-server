package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCardBase;

import java.util.Map;

/**
 * <p>
 * 资讯帖子基础表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInCardBaseService extends IService<InCardBase> {
    PageUtils queryPage(Map<String, Object> map);

    void updateReadNumber(long number, Long cId);
}
