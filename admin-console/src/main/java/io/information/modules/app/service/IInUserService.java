package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.entity.InCommonReply;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.vo.InLikeVo;
import io.information.modules.app.vo.UserBoolVo;

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
    String focus(Long uId, Integer status, Long fId);

    Boolean saveWithCache(InUser inUser);

    PageUtils<InCommonReply> comment(Map<String, Object> params);

    Map<String, Object> honor(Long uId);

    PageUtils card(Map<String, Object> map);

    PageUtils<InCommonReply> reply(Map<String, Object> map);

    PageUtils<InLikeVo> like(Map<String, Object> params, Long uId);

    PageUtils<InActivity> active(Map<String, Object> map);

    PageUtils fansWriter(Map<String, Object> map);

    PageUtils fansPerson(Map<String, Object> map);

    PageUtils follower(Map<String, Object> map);

    PageUtils favorite(Map<String, Object> map);

    PageUtils fansNode(Map<String, Object> map);

    UserBoolVo userNumber(Long uId, Long tId, Integer type, InUser user);

    Boolean isFocus(Long tId, Long uId);

    PageUtils<InCommonReply> commentUser(Map<String, Object> map);

    List<InUser> all();

}
