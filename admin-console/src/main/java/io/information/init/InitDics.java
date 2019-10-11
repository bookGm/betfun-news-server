package io.information.init;
import io.information.common.utils.DicHelper;
import io.information.modules.news.entity.DicEntity;
import io.information.modules.news.service.DicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InitDics {
    private static final Logger LOG = LoggerFactory.getLogger(InitCitys.class);
    @Autowired
    DicService dicService;
    @Autowired
    private DicHelper dicHelper;
    @PostConstruct
    void init(){
        List<DicEntity> dics=dicService.list();
        try {
            dicHelper.init(dics);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
