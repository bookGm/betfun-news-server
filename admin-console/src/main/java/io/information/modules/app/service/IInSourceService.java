package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯资源表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInSourceService extends IService<InSource> {
    PageUtils queryPage(Map<String, Object> params);

    InSource getByUrl(String sUrl);

    void updateByUrl(InSource source);

    void removeByUrl(List<String> urlList);
}
