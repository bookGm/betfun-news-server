package io.information.modules.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.news.entity.NewsFlash;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 资讯快讯表 Mapper 接口
 * </p>
 *
 * @author LX
 * @since 2019-10-26
 */
@Mapper
public interface NewsFlashDao extends BaseMapper<NewsFlash> {

}
