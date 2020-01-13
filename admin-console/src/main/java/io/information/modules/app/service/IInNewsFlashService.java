package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InNewsFlash;
import io.information.modules.app.vo.FlashVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯文章表 服务类
 * </p>
 *
 * @author LX
 * @since 2019-10-26
 */
public interface IInNewsFlashService extends IService<InNewsFlash> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 利好利空
     *
     * @param nId 快讯id
     * @param uId 用户id
     * @param bId 0：利空 1：利好
     */
    String attitude(Long nId, Long uId, Integer bId);

    /**
     * 利空-1
     */
    void delNBad(long nId);

    /**
     * 利好-1
     */
    void delNBull(long nId);

    List<InNewsFlash> all();

    FlashVo details(Long nId);

    void addNBad(long nId);

    void addNBull(long nId);
}
