package com.yxq.myframdome.api_entity;

import java.util.List;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-12  11:51
 */
public class MenuVO {
    private int menuId;
    private String name;
    private int iconId;
    private List<MenuVO> menuVOList;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public List<MenuVO> getMenuVOList() {
        return menuVOList;
    }

    public void setMenuVOList(List<MenuVO> menuVOList) {
        this.menuVOList = menuVOList;
    }
}
