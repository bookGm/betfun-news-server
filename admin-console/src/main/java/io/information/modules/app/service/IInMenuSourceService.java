package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InMenuSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * 资讯菜单资源关系表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-25
 */
public interface IInMenuSourceService extends IService<InMenuSource> {

    PageUtils queryPage(Map<String, Object> params);

    void updatesUrl(InMenuSource menuSource);
}
