package com.yxq.myframdome.api_entity.bean;

import java.io.Serializable;

/**
 * Created by Hollow Goods on 2019-04-23.
 */
public class WebFile2 implements Serializable {

    private String imgName;
    private String imgUrl;

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
