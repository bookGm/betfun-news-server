package io.information.modules.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.config.IdWorker;
import io.information.modules.news.dao.MenuDao;
import io.information.modules.news.entity.MenuEntity;
import io.information.modules.news.entity.MenuSourceEntity;
import io.information.modules.news.entity.MenusEntity;
import io.information.modules.news.entity.SourceEntity;
import io.information.modules.news.service.MenuService;
import io.information.modules.news.service.MenuSourceService;
import io.information.modules.news.service.SourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {
    @Autowired
    private MenuSourceService menuSourceService;
    @Autowired
    private SourceService sourceService;
    @Autowired
    MenuDao menuDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MenuEntity> page = this.page(
                new Query<MenuEntity>().getPage(params),
                new QueryWrapper<MenuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public MenuEntity getByCode(String mPcode) {
        LambdaQueryWrapper<MenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuEntity::getmCode, mPcode);
        return this.getOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveList(MenusEntity menus) {
        MenuSourceEntity menuSource = new MenuSourceEntity();

        MenuEntity menu = menus.getMenu();
        SourceEntity source = menus.getSource();
        if (source != null && !"".equals(source)) {
            List<MenuSourceEntity> sources = menuSourceService.list();
            Set<String> collect = sources.stream().map(MenuSourceEntity::getsUrl).collect(Collectors.toSet());
            if (collect.add(source.getsUrl())) {
                menuSource.setMsId(new IdWorker().nextId());
                source.setsCreateTime(new Date());

                menuSource.setmName(menu.getmName());
                menuSource.setmCode(menu.getmCode());
                menuSource.setsName(source.getsName());
                menuSource.setsUrl(source.getsUrl());

                sourceService.save(source);
                menuSourceService.save(menuSource);
            }
        }
        this.save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Long mId) {
        LambdaQueryWrapper<MenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MenuEntity::getmId, mId);
        MenuEntity menu = this.getOne(queryWrapper);

        String code = menu.getmCode();

        LambdaQueryWrapper<MenuSourceEntity> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(MenuSourceEntity::getmCode, code);
        MenuSourceEntity menuSource = menuSourceService.getOne(queryWrapper1);

        if (menuSource != null && !"".equals(menuSource)) {
            String url = menuSource.getsUrl();

            LambdaQueryWrapper<SourceEntity> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(SourceEntity::getsUrl, url);
            SourceEntity source = sourceService.getOne(queryWrapper2);

            if (source != null && !"".equals(source)) {
                String getsUrl = source.getsUrl();
                ArrayList<String> list = new ArrayList<>();
                list.add(getsUrl);
                sourceService.removeByUrl(list);
            }
            menuSourceService.removeById(menuSource.getMsId());
        }
        this.removeById(mId);
    }

    @Override
    public void updateAll(MenusEntity menus) {
        MenuSourceEntity menuSource = new MenuSourceEntity();
        MenuEntity menu = menus.getMenu();
        String mCode = menu.getmCode();

        //更新子节点中的父节点信息
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(MenuEntity::getmPcode, mCode);
        List<MenuEntity> menuList = this.list(queryWrapper);
        if (menuList != null && !menuList.isEmpty()) {
            menuList.forEach(menus1 -> {
                menus1.setmPcode(mCode);
                this.updateById(menus1);
            });
        }

        SourceEntity source = menus.getSource();
        if (source != null && !"".equals(source)) {
            source.setsUpdateTime(new Date());

            menuSource.setmName(menu.getmName());
            menuSource.setmCode(menu.getmCode());
            menuSource.setsName(source.getsName());
            menuSource.setsUrl(source.getsUrl());

            sourceService.updateByUrl(source);
            menuSourceService.updatesUrl(menuSource);
        }
        this.updateById(menu);
    }

    @Override
    public MenusEntity queryMenusById(Long mId) {
        MenuEntity menu = this.getById(mId);
        MenuSourceEntity menuSource = this.findMenuSource(menu);
        SourceEntity source = this.findSource(menuSource);

        MenusEntity menus = new MenusEntity();
        menus.setMenu(menu);
        menus.setMenuSource(menuSource);
        menus.setSource(source);
        return menus;
    }


    //根据MenuSource获得Source
    private SourceEntity findSource(MenuSourceEntity menuSource) {
        String sourceUrl = menuSource.getsUrl();
        String sourceName = menuSource.getsName();
        QueryWrapper<SourceEntity> sourceQueryWrapper = new QueryWrapper<>();
        sourceQueryWrapper.lambda().eq(SourceEntity::getsComponent, sourceUrl).eq(SourceEntity::getsName, sourceName);
        return sourceService.getOne(sourceQueryWrapper);
    }

    //根据Menu获得MenuSource
    private MenuSourceEntity findMenuSource(MenuEntity menu) {
        String menuCode = menu.getmCode();
        String menuName = menu.getmName();
        QueryWrapper<MenuSourceEntity> menuSourceQueryWrapper = new QueryWrapper<>();
        menuSourceQueryWrapper.lambda().eq(MenuSourceEntity::getmCode, menuCode).eq(MenuSourceEntity::getmName, menuName);
        return menuSourceService.getOne(menuSourceQueryWrapper);
    }
    @Override
    public String getMaxCode(String pcode) {
        return menuDao.getMaxCode(pcode);
    }

}