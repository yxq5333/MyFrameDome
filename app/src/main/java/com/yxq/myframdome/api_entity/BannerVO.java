package com.yxq.myframdome.api_entity;

/**
 * @author CrazyHuskar
 * caeat at 2019-01-16  14:13
 */
public class BannerVO {
    private FileVO file;
    private String linkUrl;
    private String fileTitle;
    public FileVO getFile() {
        return file;
    }

    public void setFile(FileVO file) {
        this.file = file;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }
}
