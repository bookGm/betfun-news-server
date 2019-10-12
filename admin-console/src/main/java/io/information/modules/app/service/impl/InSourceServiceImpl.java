package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.dao.InSourceDao;
import io.information.modules.app.entity.InSource;
import io.information.modules.app.service.IInSourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资讯资源表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InSourceServiceImpl extends ServiceImpl<InSourceDao, InSource> implements IInSourceService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InSource> page = this.page(
                new Query<InSource>().getPage(params),
                new QueryWrapper<InSource>()
        );

        return new PageUtils(page);
    }

    @Override
    public InSource getByUrl(String sUrl) {
        LambdaQueryWrapper<InSource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InSource::getsUrl, sUrl);
        return this.getOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByUrl(InSource source) {
        LambdaUpdateWrapper<InSource> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(InSource::getsUrl, source.getsUrl());
        this.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByUrl(List<String> urlList) {
        LambdaQueryWrapper<InSource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(InSource::getsUrl, urlList);
        this.remove(queryWrapper);
    }
}
