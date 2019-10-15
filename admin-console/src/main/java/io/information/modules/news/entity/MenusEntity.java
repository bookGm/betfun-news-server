package io.information.modules.news.entity;

import java.io.Serializable;

/**
 * 菜单资源前端字段
 */
public class MenusEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单表
     */
    private MenuEntity menu;
    /**
     * 菜单资源中间表
     */
    private MenuSourceEntity menuSource;
    /**
     * 资源表
     */
    private SourceEntity source;


    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }

    public MenuSourceEntity getMenuSource() {
        return menuSource;
    }

    public void setMenuSource(MenuSourceEntity menuSource) {
        this.menuSource = menuSource;
    }

    public SourceEntity getSource() {
        return source;
    }

    public void setSource(SourceEntity source) {
        this.source = source;
    }
}
