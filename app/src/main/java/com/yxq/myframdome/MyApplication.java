package com.yxq.myframdome;

import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.base.MyBaseApplication;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.smtt.sdk.QbSdk;
import com.yxq.myframdome.util.IP.IPConfig;
import com.yxq.myframdome.util.IP.InterfaceConfig;

import java.util.List;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  13:43
 */
public class MyApplication extends MyBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.initX5Environment(this, null);
//        JsContext.ExceptionHandler.getInstance().initConfig(this);
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
            return new MaterialHeader(context);
        });
        switch (AppConfig.APP_COMPANY) {
            case XHTT:
                InterfaceConfig.initIP(

                        new IPConfig().setIp("101.133.143.251").setPort("8002").setRealmName("api")

                );
                break;
            case MAS:
                InterfaceConfig.initIP(
                        new IPConfig().setIp("218.93.5.75").setPort("8002").setRealmName("api")

                );
                break;
            case AQZHPT:
                InterfaceConfig.initIP(
                        new IPConfig().setIp("218.93.5.75").setPort("8002").setRealmName("api")
                );
                break;
            case TaiZhou:
                InterfaceConfig.initIP(
                        new IPConfig().setIp("112.13.194.94").setPort("8002").setRealmName("api")
                );
                break;
        }

        APIConfig.BASEURL = InterfaceConfig.getNowIPConfig().getRequestUrl() + "/";
        MyRetrofitUtil.getInstance().init(APIConfig.BASEURL);
    }

    @Override
    public MyBaseApplication bindApplication() {
        return this;
    }

    public static MyApplication getApp() {
        return (MyApplication) MyBaseApplication.getInstance();
    }

    private List<String> permissions;
    private boolean isCompanyUser;

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public boolean hasPermissions(String code) {

        if (permissions == null) {
            return false;
        }

        return permissions.indexOf(code) != -1;
    }

    public boolean isCompanyUser() {
        return isCompanyUser;
    }

    public void setCompanyUser(boolean companyUser) {
        isCompanyUser = companyUser;
    }
}
