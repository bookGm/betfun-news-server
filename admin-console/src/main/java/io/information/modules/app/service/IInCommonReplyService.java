package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCommonReply;

import java.util.List;
import java.util.Map;

;

/**
 * 评论回复表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-08 15:11:28
 */
public interface IInCommonReplyService extends IService<InCommonReply> {
    PageUtils<InCommonReply> userMsg(Map<String, Object> params);

    PageUtils<InCommonReply> reply(Map<String, Object> map, List<Long> cIds);

    PageUtils discuss(Map<String, Object> map);

    PageUtils revert(Map<String, Object> map);

    PageUtils discussUser(Map<String, Object> map);
}

