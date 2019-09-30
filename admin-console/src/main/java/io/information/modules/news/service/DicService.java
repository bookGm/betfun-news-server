package io.information.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.news.entity.DicEntity;

import java.util.List;
import java.util.Map;

/**
 * 资讯字典表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-29 13:13:05
 */
public interface DicService extends IService<DicEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<DicEntity> queryDidAscList();

    /**
     * 获取字典列表
     */
    Map<String,List<DicEntity>> getListAll(String key);
}

