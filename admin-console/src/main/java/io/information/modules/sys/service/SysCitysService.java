

package io.information.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.sys.entity.SysCitysEntity;

import java.util.List;
import java.util.Map;


/**
 * 省市县（区）
 *
 * @author LX
 */
public interface SysCitysService extends IService<SysCitysEntity> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取所有城市
     *
     * @return
     */
    Map<String, List<SysCitysEntity>> getListAll(String key);

}
