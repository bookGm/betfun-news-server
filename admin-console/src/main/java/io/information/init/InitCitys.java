package io.information.init;

import io.information.common.utils.RedisKeys;
import io.information.common.utils.RedisUtils;
import io.information.modules.sys.entity.SysCitysEntity;
import io.information.modules.sys.service.SysCitysService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class InitCitys {
    private static final Logger LOG = LoggerFactory.getLogger(InitCitys.class);
    @Autowired
    SysCitysService sysCitysService;
    @Autowired
    RedisUtils redis;

    @PostConstruct
    void init(){
        List<SysCitysEntity> citys=sysCitysService.list();
        Map<String,List<SysCitysEntity>> cs=citys.stream().collect(Collectors.groupingBy(n->{
            switch(n.getLevel()){
                case 1:
                    return "province";
                case 2:
                    return "city";
                default:
                    return "region";
            }
        }));
        redis.set(RedisKeys.CONSTANT_CITYS,cs);
    }
}
