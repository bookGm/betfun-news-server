package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InUser;
import io.information.modules.app.vo.CardArgueVo;

import java.util.Map;

public interface IInCardService extends IService<InCardBase> {
    void issueCard(InCard card, InUser user);

    InCard details(Map<String, Object> map);

    void delete(Long[] cIds);

    PageUtils queryPage(Map<String, Object> map);

    void update(InCard card);

    CardArgueVo loginArgue(Long cId, Long uId);
}
