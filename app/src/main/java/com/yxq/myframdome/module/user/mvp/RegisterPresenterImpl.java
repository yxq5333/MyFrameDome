package com.yxq.myframdome.module.user.mvp;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenterImpl;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.google.gson.JsonObject;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.api_entity.UserDTO;

/**
 * 点击操作后结果
 *
 * @author CrazyHuskar
 * caeat at 2018-12-04  15:17
 */
public class RegisterPresenterImpl extends MyBaseMvpPresenterImpl<RegisterContract.RegisterView> implements RegisterContract.RegisterPresenter {

    @Override
    public void summit(int type, String mobile, String password, String username) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", type);
        jsonObject.addProperty("mobile", mobile);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("username", username);
        UserDTO userDTO = new UserDTO();
        userDTO.url(APIConfig.REGISTER);
        userDTO.jsonObject(MyUtilJson.toJSONString(jsonObject));
        userDTO.register(new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                if (isViewAttached()) {
                    getView().registerSuccess(s);
                }
            }

            @Override
            public void onFailure(String s) {
                if (isViewAttached()) {
                    getView().registerFail(s);
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
}

