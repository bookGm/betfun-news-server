package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.modules.app.entity.InCardBase;

import java.util.List;

/**
 * <p>
 * 资讯帖子基础表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInCardBaseService extends IService<InCardBase> {

    List<InCardBase> queryAllCardBase(Long userId);

    void deleteAllCardBase(Long userId);
}
