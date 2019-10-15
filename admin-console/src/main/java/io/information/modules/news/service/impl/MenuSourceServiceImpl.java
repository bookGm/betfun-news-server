package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.news.dao.MenuSourceDao;
import io.information.modules.news.entity.MenuSourceEntity;
import io.information.modules.news.service.MenuSourceService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("menuSourceService")
public class MenuSourceServiceImpl extends ServiceImpl<MenuSourceDao, MenuSourceEntity> implements MenuSourceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MenuSourceEntity> page = this.page(
                new Query<MenuSourceEntity>().getPage(params),
                new QueryWrapper<MenuSourceEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public MenuSourceEntity infoUrl(String sUrl) {
        LambdaQueryWrapper<MenuSourceEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuSourceEntity::getsUrl,sUrl);
        return this.getOne(queryWrapper);
    }

    @Override
    public void updatesUrl(MenuSourceEntity menuSource) {
        LambdaUpdateWrapper<MenuSourceEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(MenuSourceEntity::getsUrl,menuSource.getsUrl());
        this.update(updateWrapper);
    }

}