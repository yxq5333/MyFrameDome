package com.yxq.myframdome.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import androidx.appcompat.app.AlertDialog;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.base.MyBaseActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilLog;
import com.crazyhuskar.myandroidsdk.util.MyUtilToast;
import com.crazyhuskar.myandroidsdk.view.util.MyViewUtilSVProgressHUB;
import com.google.gson.Gson;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.AppVersionDTO;
import com.yxq.myframdome.api_entity.AppVersionVO;
import com.yxq.myframdome.api_entity.BaseJson;

import java.io.File;

/**
 * APP版本更新工具类
 * Created by HG on 2016-11-28.
 */

public class UpdateAPPUtils {

    public static final int UPDATE_APP_UTILS_REQUEST_CODE_INSTALL = 1010;

    private static boolean isFromUser = false;
    private static String URL = "";
    private static MyBaseActivity baseActivity;
    private static File apkFile;
    private static MyViewUtilSVProgressHUB progressHUB;

    private static boolean AVOID_CHECK = false;

    /**
     * 检查更新
     *
     * @param activity   activity
     * @param isFromUser isFromUser
     */
    public static void checkUpdate(MyBaseActivity activity, boolean isFromUser) {

        if (AVOID_CHECK) {
            return;
        }

        baseActivity = activity;
        UpdateAPPUtils.isFromUser = isFromUser;
        progressHUB = MyViewUtilSVProgressHUB.getInstance();
        progressHUB.init(baseActivity);

        if (UpdateAPPUtils.isFromUser) {
            progressHUB.showProgressHUD(baseActivity.getString(R.string.update_app));
        }

        doCheck();
    }

    private static void doCheck() {

        AppVersionDTO dto = new AppVersionDTO();
        dto.url(APIConfig.UPDATE_APP_VERSION);
        dto.doCheck(new MyCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {

                    MyUtilLog.e(result);
                    BaseJson responseInfo = new Gson().fromJson(result, BaseJson.class);

                    if (responseInfo.getCode() == 0) {
                        AppVersionVO appVersion = new Gson().fromJson(
                                new Gson().toJson(responseInfo.getData()),
                                AppVersionVO.class
                        );

                        if (!TextUtils.isEmpty(appVersion.getVersion()) && appVersion.getVersion().compareTo(getVersionName(baseActivity)) > 0) {
                            URL = appVersion.getPackageUrl();

                            StringBuilder tip = new StringBuilder();
                            tip.append("V");
                            tip.append(getVersionName(baseActivity));
                            tip.append(" → ");
                            tip.append("V");
                            tip.append(appVersion.getVersion());
                            tip.append("\n\n");
                            tip.append("发布时间：");
                            tip.append("\n");
                            tip.append(appVersion.getUpdateTime());
                            tip.append("\n\n");
                            tip.append("更新内容：");
                            tip.append("\n");
                            tip.append(appVersion.getContent());

                            showDialog(tip.toString());
                        } else {
                            if (isFromUser) {
                                MyUtilToast.showShort(baseActivity, R.string.update_app_already_new);
                            }
                        }
                    } else {
                        if (isFromUser) {
                            MyUtilToast.showShort(baseActivity, R.string.update_app_already_new);
                        }
                    }

                    if (isFromUser) {
                        progressHUB.dismissProgressHUD();
                    }
                } catch (Exception ignored) {

                }
            }

            @Override
            public void onFailure(String msg) {
                if (isFromUser) {
                    MyUtilToast.showShort(baseActivity, msg);
                    progressHUB.dismissProgressHUD();
                }
            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private static void showDialog(String tip) {

        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        builder.setTitle(R.string.update_app_find_new_version)
                .setMessage(tip)
                .setPositiveButton("立即更新", (dialog, which) -> doDownloadAPK())
                .setNegativeButton("下次再说", (dialog, which) -> {

                })
                .show();
    }

    private static void doDownloadAPK() {

        progressHUB.showProgressHUD(baseActivity.getString(R.string.downloading));

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AnDaiBao/Download/";
        String name = System.currentTimeMillis() + ".apk";

        FileUtils.checkFileExist(path);

        AppVersionDTO dto = new AppVersionDTO();
        dto.url(URL);
        dto.download(path, name, new MyCallback<String>() {
            @Override
            public void onSuccess(String data) {
                progressHUB.dismissProgressHUD();
                apkFile = new File(data);
                if (new SystemAppUtils().canInstallAPK(baseActivity)) {
                    new SystemAppUtils().installAPK(baseActivity, apkFile);
                } else {
                    new SystemAppUtils().requestInstallPermission(baseActivity, UPDATE_APP_UTILS_REQUEST_CODE_INSTALL);
                }
            }

            @Override
            public void onFailure(String msg) {
                MyUtilToast.showShort(baseActivity, msg);
                progressHUB.dismissProgressHUD();
            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public static void onInstallRequestActivityResult(int requestCode, int resultCode) {
        if (requestCode == UPDATE_APP_UTILS_REQUEST_CODE_INSTALL && resultCode == Activity.RESULT_OK) {
            new SystemAppUtils().installAPK(baseActivity, apkFile);
        }
    }

    /**
     * [获取应用程序版本名称信息]
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

}
