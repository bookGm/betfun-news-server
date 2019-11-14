package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.exception.ExceptionEnum;
import io.information.common.exception.IMException;
import io.information.common.utils.PageUtils;
import io.information.common.utils.Query;
import io.information.modules.app.config.IdWorker;
import io.information.modules.app.dao.InMenuDao;
import io.information.modules.app.entity.InMenu;
import io.information.modules.app.entity.InMenuSource;
import io.information.modules.app.entity.InMenus;
import io.information.modules.app.entity.InSource;
import io.information.modules.app.service.IInMenuService;
import io.information.modules.app.service.IInMenuSourceService;
import io.information.modules.app.service.IInSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 资讯菜单表 服务实现类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
@Service
public class InMenuServiceImpl extends ServiceImpl<InMenuDao, InMenu> implements IInMenuService {
    @Autowired
    IInMenuSourceService menuSourceService;
    @Autowired
    IInSourceService sourceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMenu(InMenus menus) {
        InMenuSource menuSource = new InMenuSource();

        InMenu menu = menus.getMenu();
        InSource source = menus.getSource();
        if (source != null && !"".equals(source)) {
            List<InMenuSource> sources = menuSourceService.list();
            Set<String> collect = sources.stream().map(InMenuSource::getsUrl).collect(Collectors.toSet());
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
    public void deleteMenu(Long menuId) {
        //删除菜单
        //找到对应的Menu
        QueryWrapper<InMenu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.lambda().eq(InMenu::getmId, menuId);
        InMenu menu = this.getOne(menuQueryWrapper);
        String menuCode = menu.getmCode();

        //确定编码是否为父编码，为父编码时不可删除,需要先删除子编码
        QueryWrapper<InMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(InMenu::getmPcode, menuCode);
        List<InMenu> menus = this.list(queryWrapper);
        if (menus != null && !menus.isEmpty()) {
            throw new IMException(ExceptionEnum.NODE_PARENT_PATH);
        } else {
            //找到Menu对应的MenuSource
            InMenuSource menuSource = this.findMenuSource(menu);
            //找到MenuSource对应的source
            QueryWrapper<InSource> sourceQueryWrapper = new QueryWrapper<>();
            sourceQueryWrapper.lambda().eq(InSource::getsUrl, menuSource.getsUrl())
                    .eq(InSource::getsName, menuSource.getsName());
            //执行删除
            sourceService.remove(sourceQueryWrapper);
            this.removeById(menuId);
            menuSourceService.removeById(menuSource);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(InMenus menus) {
        InMenuSource menuSource = new InMenuSource();
        InMenu menu = menus.getMenu();
        String mCode = menu.getmCode();

        //更新子节点中的父节点信息
        QueryWrapper<InMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(InMenu::getmPcode, mCode);
        List<InMenu> menuList = this.list(queryWrapper);
        if (menuList != null && !menuList.isEmpty()) {
            menuList.forEach(menus1 -> {
                menus1.setmPcode(mCode);
                this.updateById(menus1);
            });
        }

        InSource source = menus.getSource();
        if (source != null && !"".equals(source)) {

            menuSource.setmName(menu.getmName());
            menuSource.setmCode(menu.getmCode());
            menuSource.setsName(source.getsName());
            menuSource.setsUrl(source.getsUrl());
            source.setsUpdateTime(new Date());

            sourceService.updateByUrl(source);
            menuSourceService.updatesUrl(menuSource);
        }
        this.updateById(menu);
    }

    @Override
    public InMenus queryMenuById(Long menuId) {
        InMenu menu = this.getById(menuId);
        InMenuSource menuSource = this.findMenuSource(menu);
        InSource source = this.findSource(menuSource);

        InMenus menus = new InMenus();
        menus.setMenu(menu);
        menus.setMenuSource(menuSource);
        menus.setSource(source);
        return menus;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<InMenu> qw=new LambdaQueryWrapper<InMenu>();
        qw.eq(InMenu::getmDisable,0);
        IPage<InMenu> page = this.page(
                new Query<InMenu>().getPage(params),
                qw
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Long mId) {
        LambdaQueryWrapper<InMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InMenu::getmId, mId);
        InMenu menu = this.getOne(queryWrapper);

        String code = menu.getmCode();

        LambdaQueryWrapper<InMenuSource> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(InMenuSource::getmCode, code);
        InMenuSource menuSource = menuSourceService.getOne(queryWrapper1);

        if (menuSource != null && !"".equals(menuSource)) {
            String url = menuSource.getsUrl();

            LambdaQueryWrapper<InSource> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(InSource::getsUrl, url);
            InSource source = sourceService.getOne(queryWrapper2);

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


    //根据MenuSource获得Source
    private InSource findSource(InMenuSource menuSource) {
        String sourceUrl = menuSource.getsUrl();
        String sourceName = menuSource.getsName();
        QueryWrapper<InSource> sourceQueryWrapper = new QueryWrapper<>();
        sourceQueryWrapper.lambda().eq(InSource::getsComponent, sourceUrl).eq(InSource::getsName, sourceName);
        return sourceService.getOne(sourceQueryWrapper);
    }

    //根据Menu获得MenuSource
    private InMenuSource findMenuSource(InMenu menu) {
        String menuCode = menu.getmCode();
        String menuName = menu.getmName();
        QueryWrapper<InMenuSource> menuSourceQueryWrapper = new QueryWrapper<>();
        menuSourceQueryWrapper.lambda().eq(InMenuSource::getmCode, menuCode).eq(InMenuSource::getmName, menuName);
        return menuSourceService.getOne(menuSourceQueryWrapper);
    }
}
