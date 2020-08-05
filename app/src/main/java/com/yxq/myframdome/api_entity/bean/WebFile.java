package com.yxq.myframdome.api_entity.bean;

import java.io.Serializable;

/**
 * Created by Hollow Goods on 2019-04-23.
 */
public class WebFile implements Serializable {

    private String name;
    private String fileOrignalName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileOrignalName() {
        return fileOrignalName;
    }

    public void setFileOrignalName(String fileOrignalName) {
        this.fileOrignalName = fileOrignalName;
    }
}
