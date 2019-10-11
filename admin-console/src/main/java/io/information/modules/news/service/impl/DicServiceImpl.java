package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.modules.news.dao.DicDao;
import io.information.modules.news.entity.DicEntity;
import io.information.modules.news.service.DicService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("dicService")
public class DicServiceImpl extends ServiceImpl<DicDao, DicEntity> implements DicService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DicEntity> page = this.page(
                new Query<DicEntity>().getPage(params),
                new QueryWrapper<DicEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<DicEntity> queryDidAscList() {
        QueryWrapper<DicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(DicEntity::getdId);
        return this.list(queryWrapper);
    }

    @Override
    @Cacheable(value= "dics",key = "#key")
    public Map<String,List<DicEntity>> getListAll(String key) {
        HashMap<String, List<DicEntity>> map = new HashMap<>();
        QueryWrapper<DicEntity> queryWrapper = new QueryWrapper<>();
        List<DicEntity> dicts = this.list(queryWrapper);
        map.put("dict",dicts);
        return map;
    }

}