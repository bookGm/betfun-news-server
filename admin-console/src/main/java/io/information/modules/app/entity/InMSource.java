package io.information.modules.app.entity;


import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 资讯菜单总和
 * </p>
 *
 * @author ZXS
 * @since 2019-09-24
 */

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InMSource implements Serializable {
    private static final long serialVersion = 1L;

    /**
     * 资讯菜单
     */
    private InMenu menu;
    /**
     * 资讯菜单资源关系
     */
    private InMenuSource menuSource;
    /**
     * 咨讯资源
     */
    private InSource source;

    public InMenu getMenu() {
        return menu;
    }

    public void setMenu(InMenu menu) {
        this.menu = menu;
    }

    public InMenuSource getMenuSource() {
        return menuSource;
    }

    public void setMenuSource(InMenuSource menuSource) {
        this.menuSource = menuSource;
    }

    public InSource getSource() {
        return source;
    }

    public void setSource(InSource source) {
        this.source = source;
    }
}
