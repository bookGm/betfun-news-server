package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.modules.app.entity.InUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 资讯用户表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInUserService extends IService<InUser> {

    InUser findUser(String username, String password);

    List<InUser> queryLikeByUser(String params);

    InUser queryUserByNick(String nick);

    List<InUser> queryUsersByArgueIds(String userIds);

    public Boolean saveWithCache(InUser inUser);
}
