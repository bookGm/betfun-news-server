package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.common.utils.PageUtils;
import io.information.modules.app.entity.InMenu;
import io.information.modules.app.entity.InMenuSource;
import io.information.modules.app.entity.InMenus;
import io.information.modules.app.entity.InSource;

import java.util.Map;

/**
 * <p>
 * 资讯菜单表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInMenuService extends IService<InMenu> {

    void addMenu(InMenus menus);

    void deleteMenu(Long menuId);

    void updateMenu(InMenus menus);

    InMenus queryMenuById(Long menuId);

    InMenus queryLikeMenu(String menuName);

    PageUtils queryPage(Map<String, Object> params);

    void deleteAll(Long mId);
}
