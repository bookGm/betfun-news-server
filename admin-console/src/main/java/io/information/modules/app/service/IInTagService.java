package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.modules.app.entity.InTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInTagService extends IService<InTag> {

    List<InTag> queryAllTag();
}
