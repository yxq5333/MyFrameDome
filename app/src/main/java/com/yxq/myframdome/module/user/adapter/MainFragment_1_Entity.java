package com.yxq.myframdome.module.user.adapter;

import com.yxq.myframdome.api_entity.MenuVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-13  10:07
 */
public class MainFragment_1_Entity {
    public static int TYPE_1 = 1;
    public static int TYPE_2 = 2;

    private List<MainFragment_1_Banner_Entity> bannerEntityList = new ArrayList<>();
    private MenuVO menuVO;

    public List<MainFragment_1_Banner_Entity> getBannerEntityList() {
        return bannerEntityList;
    }

    public void addBanner(MainFragment_1_Banner_Entity entity) {
        bannerEntityList.add(entity);
    }

    public void setBannerEntityList(List<MainFragment_1_Banner_Entity> bannerEntityList) {
        this.bannerEntityList.clear();
        this.bannerEntityList.addAll(bannerEntityList);
    }

    public MenuVO getMenuVO() {
        return menuVO;
    }

    public void setMenuVO(MenuVO menuVO) {
        this.menuVO = menuVO;
    }
}
