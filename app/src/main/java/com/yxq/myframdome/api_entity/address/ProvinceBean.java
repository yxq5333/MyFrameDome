package com.yxq.myframdome.api_entity.address;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/5.
 */

public class ProvinceBean implements Serializable {

    /**
     * id : 11
     * name : 北京市
     * parentId : 0
     * orderNum : 0
     * children : null
     * isParent : 1
     */

    public int id;
    public String name;
    public int parentId;
    public int orderNum;
    public Object children;
    public int isParent;
}
