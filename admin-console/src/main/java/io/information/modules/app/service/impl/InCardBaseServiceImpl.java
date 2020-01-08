package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.common.utils.RedisKeys;
import io.information.modules.app.dao.InCardBaseDao;
import io.information.modules.app.entity.InCardBase;
import io.information.modules.app.service.IInCardBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 资讯帖子基础表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InCardBaseServiceImpl extends ServiceImpl<InCardBaseDao, InCardBase> implements IInCardBaseService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> map) {
        IPage<InCardBase> page = this.page(
                new Query<InCardBase>().getPage(map),
                new QueryWrapper<InCardBase>()
        );
        for (InCardBase base : page.getRecords()) {
            Object number = redisTemplate.opsForValue().get(RedisKeys.CARDBROWSE + base.getcId());
            if (null != number) {
                long readNumber = Long.parseLong(String.valueOf(number));
                base.setcReadNumber(readNumber);
            }
        }
        return new PageUtils(page);
    }

    @Override
    public void updateReadNumber(long number, Long cId) {
        this.baseMapper.addReadNumber(number, cId);
    }
}
