package io.information.modules.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.news.entity.DicEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 资讯字典表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-29 13:13:05
 */
@Mapper
public interface DicDao extends BaseMapper<DicEntity> {
    /**
     * 根据父编码，查询子编码
     *
     * @param pId 父编码ID
     */
    List<DicEntity> queryListPId(Long pId);
}
