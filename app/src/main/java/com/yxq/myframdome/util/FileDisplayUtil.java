package com.yxq.myframdome.util;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.crazyhuskar.myandroidsdk.api.MyProgressCallback;
import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilFile;
import com.crazyhuskar.myandroidsdk.util.MyUtilSDCard;
import com.crazyhuskar.myandroidsdk.view.util.MyViewUtilSVProgressHUB;
import com.yxq.myframdome.module.FileDisplayActivity;

import java.io.File;
import java.util.HashMap;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-19  13:06
 */
public class FileDisplayUtil {
    public static void fileDisplay(final Context context, String filename) {
        MyUtilFile.checkFileExist(MyUtilSDCard.getSDCardPath() + "temp");
        if (MyUtilFile.checkFileExist2(MyUtilSDCard.getSDCardPath() + "temp" + File.separator + filename)) {
            Bundle bundle = new Bundle();
            bundle.putString("path", MyUtilSDCard.getSDCardPath() + "temp" + File.separator + filename);
            MyUtilActivity.getInstance().startActivity((AppCompatActivity) context, FileDisplayActivity.class, bundle);
        } else {
            MyViewUtilSVProgressHUB.getInstance().init(context);
            MyViewUtilSVProgressHUB.getInstance().showProgressHUD("下载文件中...");
            MyRetrofitUtil.getInstance().downloadFile(new HashMap<String, String>(), "core/down/" + filename, MyUtilSDCard.getSDCardPath() + "temp", filename, new MyProgressCallback<String>() {
                @Override
                public void onSuccess(String object) {
                    MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();

                    Bundle bundle = new Bundle();
                    bundle.putString("path", object);
                    MyUtilActivity.getInstance().startActivity((AppCompatActivity) context, FileDisplayActivity.class, bundle);
                }

                @Override
                public void onFailure(String msg) {
                    MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();

                }

                @Override
                public void onError() {

                }

                @Override
                public void onComplete() {
                    MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
                }

                @Override
                public void onProgress(long progress, long total, boolean done) {
                }
            });
        }
    }
}
