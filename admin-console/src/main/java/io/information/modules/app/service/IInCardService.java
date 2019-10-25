package io.information.modules.app.service;

import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InUser;

import java.util.Map;

public interface IInCardService {
    void issueCard(InCard card, InUser user);

    InCard details(Long cId);

    void delete(Long[] cIds);

    PageUtils queryPage(Map<String, Object> map);
}
