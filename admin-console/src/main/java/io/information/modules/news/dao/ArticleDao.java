package io.information.modules.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.news.entity.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资讯文章表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
@Mapper
public interface ArticleDao extends BaseMapper<ArticleEntity> {

}
