package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InActivityDatas;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资讯活动动态表单数据
 * 
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 11:03:00
 */
@Component
@Mapper
public interface InActivityDatasDao extends BaseMapper<InActivityDatas> {
    List<InActivityDatas> searchByActId(@Param("actId") Long actId);
}
