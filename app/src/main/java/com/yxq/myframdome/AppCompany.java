package com.yxq.myframdome;

/**
 * Created by Hollow Goods on 2019-05-06.
 */
public enum AppCompany {

    /**** 欣华天泰 ****/
    XHTT(1),
    /**** 马鞍山 ****/
    MAS(2),
    /**** 安全平台 ****/
    AQZHPT(3),
    /**** 台州（云升安全） ****/
    TaiZhou(4),
    //
    ;

    private int code;

    AppCompany(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
