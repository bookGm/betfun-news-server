package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.entity.InUser;

import java.util.Map;

public interface IInCardService extends IService<InCardBase> {
    void issueCard(InCard card, InUser user);

    InCard details(Long cId);

    void delete(Long[] cIds);

    PageUtils queryPage(Map<String, Object> map);

    void update(InCard card);
}
