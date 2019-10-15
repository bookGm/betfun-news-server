package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InMenuSourceDao;
import io.information.modules.app.entity.InMenuSource;
import io.information.modules.app.service.IInMenuSourceService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 资讯菜单资源关系表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-25
 */
@Service
public class InMenuSourceServiceImpl extends ServiceImpl<InMenuSourceDao, InMenuSource> implements IInMenuSourceService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InMenuSource> page = this.page(
                new Query<InMenuSource>().getPage(params),
                new QueryWrapper<InMenuSource>()
        );

        return new PageUtils(page);
    }

    @Override
    public void updatesUrl(InMenuSource menuSource) {
        LambdaUpdateWrapper<InMenuSource> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(InMenuSource::getsUrl,menuSource.getsUrl());
        this.update(updateWrapper);
    }
}
