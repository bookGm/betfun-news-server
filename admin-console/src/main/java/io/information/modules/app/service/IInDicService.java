package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.modules.app.entity.InDic;

import java.util.List;

/**
 * <p>
 * 资讯字典表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInDicService extends IService<InDic> {

    void deleteDic(Long[] dIds);

    void updateDic(InDic dic);

    List<InDic> queryDicById(Long dicId);

    List<InDic> queryNameDic(String dicName);

    List<InDic> queryAllDic();

    List<InDic> queryDicByCode(String dicCode);
}
