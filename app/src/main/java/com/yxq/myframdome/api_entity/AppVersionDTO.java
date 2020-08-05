package com.yxq.myframdome.api_entity;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.api.MyProgressCallback;
import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.base.bean.MyBaseDTO;

/**
 * Created by Hollow Goods on 2019-05-17.
 */
public class AppVersionDTO extends MyBaseDTO<AppVersionDTO> {

    @Override
    public AppVersionDTO transform() {
        return null;
    }

    @Override
    public void execute(MyCallback<AppVersionDTO> callback) {

    }

    public void doCheck(MyCallback<String> callback) {
        MyRetrofitUtil.getInstance().executeGet(headers, url, new MyCallback<String>() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(String s) {
                callback.onFailure(s);
            }

            @Override
            public void onError() {
                callback.onFailure("网络错误");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void download(String path, String filename, MyCallback<String> callback) {
        MyRetrofitUtil.getInstance().downloadFile(headers, url, path, filename, new MyProgressCallback<String>() {
            @Override
            public void onSuccess(String object) {
                callback.onSuccess(object);
            }

            @Override
            public void onFailure(String msg) {
                callback.onFailure(msg);
            }

            @Override
            public void onError() {
                callback.onFailure("网络错误");
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onProgress(long progress, long total, boolean done) {

            }
        });
    }

}
