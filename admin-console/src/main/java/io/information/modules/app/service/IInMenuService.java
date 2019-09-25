package io.information.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.information.modules.app.entity.InMSource;
import io.information.modules.app.entity.InMenu;
import io.information.modules.app.entity.InMenuSource;
import io.information.modules.app.entity.InSource;

import java.util.List;

/**
 * <p>
 * 资讯菜单表 服务类
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */
public interface IInMenuService extends IService<InMenu> {

    void addMenu(InMenu menu, InMenuSource menuSource, InSource source);

    void deleteMenu(Long menuId);

    void updateMenu(InMenu menu, InMenuSource menuSource, InSource source);

    InMSource queryMenuById(Long menuId);

    InMSource queryLikeMenu(String menuName);

    List<InMSource> queryAllMenu();
}
