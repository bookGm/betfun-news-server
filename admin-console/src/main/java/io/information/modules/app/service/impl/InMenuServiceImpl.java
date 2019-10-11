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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        menu.setmId(new IdWorker().nextId());
        menuSource.setMsId(new IdWorker().nextId());
        source.setsCreateTime(new Date());

        menuSource.setmName(menu.getmName());
        menuSource.setmCode(menu.getmCode());
        menuSource.setsName(source.getsName());
        menuSource.setsUrl(source.getsUrl());

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
        menuQueryWrapper.lambda().eq(InMenu::getmId,menuId);
        InMenu menu = this.getOne(menuQueryWrapper);
        String menuCode = menu.getmCode();

        //确定编码是否为父编码，为父编码时不可删除,需要先删除子编码
        QueryWrapper<InMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(InMenu::getmPcode,menuCode);
        List<InMenu> menus = this.list(queryWrapper);
        if(menus != null && !menus.isEmpty()){
            throw new IMException(ExceptionEnum.NODE_PARENT_PATH);
        }else{
            //找到Menu对应的MenuSource
            InMenuSource menuSource = this.findMenuSource(menu);
            //找到MenuSource对应的source
            QueryWrapper<InSource> sourceQueryWrapper = new QueryWrapper<>();
            sourceQueryWrapper.lambda().eq(InSource::getsUrl,menuSource.getsUrl())
                                       .eq(InSource::getsName,menuSource.getsName());
            //执行删除
            sourceService.remove(sourceQueryWrapper);
            this.removeById(menuId);
            menuSourceService.removeById(menuSource);
        }
    }

    @Override
    @Transactional
    public void updateMenu(InMenu menu, InMenuSource menuSource, InSource source) {
        source.setsUpdateTime(new Date());
        String mCode = menu.getmCode();

        //更新子节点中的父节点信息
        QueryWrapper<InMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(InMenu::getmPcode,mCode);
        List<InMenu> menus = this.list(queryWrapper);
        if(menus!=null && !menus.isEmpty()){
            menus.forEach(menus1 ->{
                menus1.setmPcode(mCode);
                this.updateById(menus1);
            });
        }

        InMenuSource menuSource1 = this.findMenuSource(menu);
        menuSource1.setmCode(mCode);

        UpdateWrapper<InSource> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(InSource::getsUrl,source.getsUrl());
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
        queryWrapper.lambda().like(InMenu::getmName,menuName);
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
        List<InMenu> menus = this.list();
        menus.forEach(menu -> {
            InMSource mSource = this.queryMenuById(menu.getmId());
            mSources.add(mSource);
        });

        return mSources;
    }


    //根据MenuSource获得Source
    private InSource findSource(InMenuSource menuSource){
        String sourceUrl = menuSource.getsUrl();
        String sourceName = menuSource.getsName();
        QueryWrapper<InSource> sourceQueryWrapper = new QueryWrapper<>();
        sourceQueryWrapper.lambda().eq(InSource::getsComponent,sourceUrl).eq(InSource::getsName,sourceName);
        return sourceService.getOne(sourceQueryWrapper);
    }
    //根据Menu获得MenuSource
    private InMenuSource findMenuSource(InMenu menu){
        String menuCode = menu.getmCode();
        String menuName = menu.getmName();
        QueryWrapper<InMenuSource> menuSourceQueryWrapper = new QueryWrapper<>();
        menuSourceQueryWrapper.lambda().eq(InMenuSource::getmCode,menuCode).eq(InMenuSource::getmName,menuName);
        return menuSourceService.getOne(menuSourceQueryWrapper);
    }
}
