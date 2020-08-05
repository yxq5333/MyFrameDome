package com.yxq.myframdome.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * 导航工具类——打开第三方APP
 * <p>
 * Created by Hollow Goods on 2020-03-11.
 */
public class GuideUtils {

    public static boolean guideByAMap(Context context, double lat, double lng) {

        Intent intent = new Intent("android.intent.action.VIEW",
                Uri.parse("androidamap://navi?sourceApplication=ADB&lat="
                        + lat
                        + "&lon="
                        + lng
                        + "&dev=0&style=2"));
        intent.setPackage("com.autonavi.minimap");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (isInstallAPP("com.autonavi.minimap")) {
            context.startActivity(intent);
            return true;
        }

        return false;
    }

    public static boolean guideByBaiDuMap(Context context, double lat, double lng) {

        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/navi?location=" + lat + "," + lng));

        if (isInstallAPP("com.baidu.BaiduMap")) {
            // 启动调用
            context.startActivity(intent);
            return true;
        }

        return false;
    }

    private static boolean isInstallAPP(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

}
