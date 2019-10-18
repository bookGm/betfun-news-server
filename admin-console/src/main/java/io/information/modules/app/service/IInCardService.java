package io.information.modules.app.service;

import io.information.modules.app.entity.InCard;
import io.information.modules.app.entity.InUser;

public interface IInCardService {
    void issueCard(InCard card, InUser user);

    InCard details(Long cId);

    void delete(Long[] cIds);
}
