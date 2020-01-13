package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.modules.app.entity.InDic;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯字典表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInDicService extends IService<InDic> {

    List<InDic> queryDidAscList();

    Map<String, List<InDic>> getListAll(String key);

    List<InDic> queryDicById(Long dicId);

    List<InDic> usList();
}
