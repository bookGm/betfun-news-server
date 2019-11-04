package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InUser;
import org.apache.ibatis.annotations.Mapper;

import java.io.IOException;
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
    /**
     * 关注用户
     *
     * @param uId 用户id
     * @param fId 被关注的用户id
     */
    Long focus(Long uId, Long fId, Integer status);

    Boolean saveWithCache(InUser inUser);

    PageUtils comment(Map<String, Object> params);

    Map<String, Object> honor(Long uId);

    PageUtils card(Map<String, Object> map);

    PageUtils reply(Map<String, Object> map);

    PageUtils like(Map<String, Object> params, Long uId);

    PageUtils active(Map<String, Object> map);

    PageUtils fansWriter(Map<String, Object> map);

    PageUtils fansPerson(Map<String, Object> map);

    PageUtils follower(Map<String, Object> map);

    PageUtils favorite(Map<String, Object> map);

    boolean change(String uPwd, String newPwd, InUser user);

    List<Long> searchFocusId(Long uId);

    PageUtils fansNode(Map<String, Object> map);
}
