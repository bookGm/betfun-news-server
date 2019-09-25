package io.information.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.information.common.exception.ExceptionEnum;
import io.information.common.exception.IMException;
import io.information.modules.app.config.IdWorker;
import io.information.modules.app.dao.InMenuDao;
import io.information.modules.app.entity.InMSource;
import io.information.modules.app.entity.InMenu;
import io.information.modules.app.entity.InMenuSource;
import io.information.modules.app.entity.InSource;
import io.information.modules.app.service.IInMenuService;
import io.information.modules.app.service.IInMenuSourceService;
import io.information.modules.app.service.IInSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private IInMenuSourceService menuSourceService;
    @Autowired
    private IInSourceService sourceService;

    @Override
    @Transactional
    public void addMenu(InMenu menu, InMenuSource menuSource, InSource source) {
        menu.setMId(new IdWorker().nextId());
        menuSource.setMsId(new IdWorker().nextId());
        source.setSCreateTime(LocalDateTime.now());

        menuSource.setMName(menu.getMName());
        menuSource.setMCode(menu.getMCode());
        menuSource.setSName(source.getSName());
        menuSource.setSUrl(source.getSUrl());

        this.save(menu);
        sourceService.save(source);
        menuSourceService.save(menuSource);
    }

    @Override
    @Transactional
    public void deleteMenu(Long menuId) {
        //删除菜单
        //找到对应的Menu
        QueryWrapper<InMenu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.lambda().eq(InMenu::getMId,menuId);
        InMenu menu = this.getOne(menuQueryWrapper);
        String menuCode = menu.getMCode();

        //确定编码是否为父编码，为父编码时不可删除,需要先删除子编码
        QueryWrapper<InMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InMenu::getMPcode,menuCode);
        List<InMenu> menus = this.list(queryWrapper);
        if(menus != null && !menus.isEmpty()){
            throw new IMException(ExceptionEnum.MENU_PARENT_PATH);
        }else{
            //找到Menu对应的MenuSource
            InMenuSource menuSource = this.findMenuSource(menu);
            //找到MenuSource对应的source
            QueryWrapper<InSource> sourceQueryWrapper = new QueryWrapper<>();
            sourceQueryWrapper.lambda().eq(InSource::getSUrl,menuSource.getSUrl())
                                       .eq(InSource::getSName,menuSource.getSName());
            //执行删除
            sourceService.remove(sourceQueryWrapper);
            this.removeById(menuId);
            menuSourceService.removeById(menuSource);
        }
    }

    @Override
    @Transactional
    public void updateMenu(InMenu menu, InMenuSource menuSource, InSource source) {
        source.setSUpdateTime(LocalDateTime.now());

        UpdateWrapper<InSource> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(InSource::getSUrl,source.getSUrl());
        sourceService.update(updateWrapper);

        this.updateById(menu);
        menuSourceService.updateById(menuSource);
    }

    @Override
    public InMSource queryMenuById(Long menuId) {
        InMenu menu = this.getById(menuId);
        InMenuSource menuSource = this.findMenuSource(menu);
        InSource source = this.findSource(menuSource);

        InMSource mSource = new InMSource();
        mSource.setMenu(menu);
        mSource.setMenuSource(menuSource);
        mSource.setSource(source);
        return mSource;
    }

    @Override
    public InMSource queryLikeMenu(String menuName) {
        InMSource mSource = new InMSource();
        QueryWrapper<InMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(InMenu::getMName,menuName);
        List<InMenu> menus = this.list(queryWrapper);
        menus.forEach(menu -> {
            InMenuSource menuSource = this.findMenuSource(menu);
            InSource source = this.findSource(menuSource);
            mSource.setMenu(menu);
            mSource.setMenuSource(menuSource);
            mSource.setSource(source);
        });
        return mSource;
    }

    @Override
    public List<InMSource> queryAllMenu() {
        ArrayList<InMSource> mSources = new ArrayList<>();
        QueryWrapper<InMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("select * from in_menu");
        List<InMenu> menus = this.list(queryWrapper);
        menus.forEach(menu -> {
            InMSource mSource = this.queryMenuById(menu.getMId());
            mSources.add(mSource);
        });

        return mSources;
    }


    //根据MenuSource获得Source
    private InSource findSource(InMenuSource menuSource){
        String sourceUrl = menuSource.getSUrl();
        String sourceName = menuSource.getSName();
        QueryWrapper<InSource> sourceQueryWrapper = new QueryWrapper<>();
        sourceQueryWrapper.lambda().eq(InSource::getSComponent,sourceUrl).eq(InSource::getSName,sourceName);
        return sourceService.getOne(sourceQueryWrapper);
    }
    //根据Menu获得MenuSource
    private InMenuSource findMenuSource(InMenu menu){
        String menuCode = menu.getMCode();
        String menuName = menu.getMName();
        QueryWrapper<InMenuSource> menuSourceQueryWrapper = new QueryWrapper<>();
        menuSourceQueryWrapper.lambda().eq(InMenuSource::getMCode,menuCode).eq(InMenuSource::getMName,menuName);
        return menuSourceService.getOne(menuSourceQueryWrapper);
    }
}
