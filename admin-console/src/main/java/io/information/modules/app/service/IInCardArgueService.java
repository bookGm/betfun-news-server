package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCardArgue;

import java.util.Map;

/**
 * <p>
 * 资讯帖子辩论表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInCardArgueService extends IService<InCardArgue> {
    PageUtils queryPage(Map<String, Object> params);

    String support(Long cid, Long uid, Integer sIndex);

    String join(Long cid, Long uid, Integer jIndex);
}
