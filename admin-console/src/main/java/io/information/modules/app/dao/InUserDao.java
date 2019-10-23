package io.information.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.information.modules.app.entity.InUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 资讯用户表 Mapper 接口
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Mapper
public interface InUserDao extends BaseMapper<InUser> {
    /**
     * 粉丝+1
     * @param uId
     */
     public void addFans(Long uId);

    /**
     * 关注+1
     * @param uId
     */
     public void addFocus(Long uId);
}
