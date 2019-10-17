package io.information.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.news.entity.CommonReplyEntity;

import java.util.List;
import java.util.Map;

/**
 * 评论回复表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-08 15:11:28
 */
public interface CommonReplyService extends IService<CommonReplyEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CommonReplyEntity> search(Long crIds);
}

