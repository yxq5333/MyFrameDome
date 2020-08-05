package com.yxq.myframdome.module.user.adapter;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-13  10:07
 */
public class MainFragment_1_Banner_Entity {
    private String title;
    private String linkurl;
    private Object src;

    public MainFragment_1_Banner_Entity(String title, Object src,String linkurl) {
        this.src = src;
        this.title = title;
        this.linkurl = linkurl;
    }

    public String getTitle() {
        return title;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public Object getSrc() {
        return src;
    }
}
