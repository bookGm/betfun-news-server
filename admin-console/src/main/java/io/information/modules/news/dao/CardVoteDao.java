package io.information.modules.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.news.entity.CardVoteEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资讯投票帖详情（最多30个投票选项）
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:24
 */
@Mapper
public interface CardVoteDao extends BaseMapper<CardVoteEntity> {

}
