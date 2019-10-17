package io.information.modules.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.news.entity.MenuEntity;
import io.information.modules.news.entity.MenusEntity;

import java.util.List;
import java.util.Map;

/**
 * 资讯菜单表
 *
 * @author zxs
 * @email zhangxiaos@163.com
 * @date 2019-09-26 12:06:25
 */
public interface MenuService extends IService<MenuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    MenuEntity getByCode(String mPcode);

    void saveList(MenusEntity menus);

    void deleteAll(Long mId);

    void updateAll(MenusEntity menus);

    MenusEntity queryMenusById(Long mId);

    String getMaxCode(String pcode);
}

