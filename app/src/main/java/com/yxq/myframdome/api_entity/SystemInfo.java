package com.yxq.myframdome.api_entity;

/**
 * <p>
 * Created by Hollow Goods on 2020-04-27.
 */
public class SystemInfo {

    private String systemName;
    private FileVO systemLogoFile;

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public FileVO getSystemLogoFile() {
        return systemLogoFile;
    }

    public void setSystemLogoFile(FileVO systemLogoFile) {
        this.systemLogoFile = systemLogoFile;
    }
}
