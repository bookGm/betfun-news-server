package io.information.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.news.entity.TagEntity;

import java.util.Map;

/**
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
public interface TagService extends IService<TagEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

