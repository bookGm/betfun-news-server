package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 日志表 接口
 * </p>
 *
 * @author LX
 * @since 2019-11-07
 */
@Component
@Mapper
public interface InLogDao extends BaseMapper<InLog> {
    /**
     * 查询最新5条帖子相关动态
     */
    List<InLog> newDynamic();
}
