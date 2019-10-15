package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InCardArgue;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InCardVote;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯帖子基础表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInCardBaseService extends IService<InCardBase> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryAllCardBase(Map<String,Object> map,Long userId);

    InCard queryCard(Long cardId);

    void deleteAllCardBase(Long userId);

    void deleteAllCard(Long userId);
}
