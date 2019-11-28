package io.information.modules.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.news.entity.TagEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@Mapper
public interface TagDao extends BaseMapper<TagEntity> {

}
