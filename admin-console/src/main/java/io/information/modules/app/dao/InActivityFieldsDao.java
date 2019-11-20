package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InActivityFields;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资讯活动动态表单属性
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 10:53:16
 */
@Component
@Mapper
public interface InActivityFieldsDao extends BaseMapper<InActivityFields> {

    List<InActivityFields> searchByActId(@Param("actId") Long actId);
}
