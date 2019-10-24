package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InActivityFields;

import java.util.Map;

/**
 * 资讯活动动态表单属性
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 10:53:16
 */
public interface IInActivityFieldsService extends IService<InActivityFields> {

    PageUtils queryPage(Map<String, Object> params);
}

