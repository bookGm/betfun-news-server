package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.modules.app.dao.InActivityDao;
import io.information.modules.app.entity.InActivity;
import io.information.modules.app.service.IInActivityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资讯活动表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InActivityServiceImpl extends ServiceImpl<InActivityDao, InActivity> implements IInActivityService {

    @Override
    public PageUtils queryActivitiesByUserId(Long userId) {
        Page<InActivity> page = new Page<>(1, 10);
        QueryWrapper<InActivity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InActivity::getUId,userId);
        IPage<InActivity> inActivityIPage = this.baseMapper.selectPage(page, queryWrapper);
        return new PageUtils(inActivityIPage);
    }


    @Override
    public void deleteAllActive(Long userId) {
        QueryWrapper<InActivity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InActivity::getUId,userId);
        List<InActivity> activities = this.list(queryWrapper);
        List<Long> activeIds = activities.stream().map(InActivity::getActId).collect(Collectors.toList());
        this.removeByIds(activeIds);
    }

}
