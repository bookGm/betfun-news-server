

package io.information.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.RedisKeys;
import io.information.common.utils.RedisUtils;
import io.information.modules.sys.dao.SysCitysDao;
import io.information.modules.sys.entity.SysCitysEntity;
import io.information.modules.sys.service.SysCitysService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SysCitysServiceImpl extends ServiceImpl<SysCitysDao, SysCitysEntity> implements SysCitysService {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        String key = (String)params.get("key");
//
//        IPage<SysLogEntity> page = this.page(
//            new Query<SysLogEntity>().getPage(params),
//            new QueryWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key),"username", key)
//        );
//
//        return new PageUtils(page);
        return null;
    }

    @Override
    @Cacheable(value= RedisKeys.CONSTANT,key = "#key")
    public Map<String, List<SysCitysEntity>> getListAll(String key) {
        List<SysCitysEntity> citys = this.list();
        Map<String, List<SysCitysEntity>> cs = citys.stream().collect(Collectors.groupingBy(n -> {
            switch (n.getLevel()) {
                case 1:
                    return "province";
                case 2:
                    return "city";
                default:
                    return "region";
            }
        }));
        return cs;
    }
}
