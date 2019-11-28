package io.information.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.news.entity.ActivityDatasEntity;

import java.util.Map;

/**
 * 资讯活动动态表单数据
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 11:03:00
 */
public interface ActivityDatasService extends IService<ActivityDatasEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

