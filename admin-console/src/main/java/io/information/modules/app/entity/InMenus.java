package io.information.modules.app.entity;

import java.io.Serializable;

/**
 * 菜单资源前端字段
 */
public class InMenus implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单表
     */
    private InMenu menu;
    /**
     * 菜单资源中间表
     */
    private InMenuSource menuSource;
    /**
     * 资源表
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
