package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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

    PageUtils queryUsersByArgueIds(Map<String,Object> params);

    Boolean saveWithCache(InUser inUser);

    void change(Map<String, Object> map, InUser user);

    /**
     * 关注用户
     * @param uId 用户id
     * @param fId 被关注的用户id
     * @return
     */
    Long focus(Long uId,Long fId);

    PageUtils comment(Map<String,Object> params);

    Map<String, Object> honor(Long uId);

    PageUtils card(Map<String, Object> map);

    PageUtils reply(Map<String, Object> map);

}
