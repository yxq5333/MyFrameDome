package com.yxq.myframdome.api_entity;

import java.io.Serializable;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-14  15:37
 */
public class FileVO implements Serializable {
    private String fileOrignalName;
    private String fileName;

    private String furl;
    private String name;

    public String getFileOrignalName() {
        return fileOrignalName;
    }

    public void setFileOrignalName(String fileOrignalName) {
        this.fileOrignalName = fileOrignalName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFurl() {
        return furl;
    }

    public void setFurl(String furl) {
        this.furl = furl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
