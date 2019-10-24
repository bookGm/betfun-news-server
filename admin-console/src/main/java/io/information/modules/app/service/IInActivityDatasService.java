package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InActivityDatas;

import java.util.List;
import java.util.Map;

/**
 * 资讯活动动态表单数据
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-10-24 11:03:00
 */
public interface IInActivityDatasService extends IService<InActivityDatas> {

    PageUtils queryPage(Map<String, Object> params);

    List<InActivityDatas> queryByActId(Long uId);

    PageUtils pass(Map<String, Object> map);
}

