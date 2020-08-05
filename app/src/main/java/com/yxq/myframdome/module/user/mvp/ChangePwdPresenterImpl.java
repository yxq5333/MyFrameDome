package com.yxq.myframdome.module.user.mvp;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenterImpl;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.google.gson.JsonObject;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.api_entity.UserDTO;

import java.util.HashMap;
import java.util.Map;

/**  点击操作后结果
 * @author CrazyHuskar
 * caeat at 2018-12-04  15:17
 */
public class ChangePwdPresenterImpl extends MyBaseMvpPresenterImpl<ChangePwdContract.ChangePwdView> implements ChangePwdContract.ChangePwdPresenter {

    @Override
    public void summit(String n, String o,String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("newPassword", n);
        jsonObject.addProperty("password", o);
        UserDTO userDTO = new UserDTO();
        userDTO.url(APIConfig.CHANGEPASSWORD);
        userDTO.headers(headers);
        userDTO.jsonObject(MyUtilJson.toJSONString(jsonObject));
        userDTO.changepassword(new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                if (isViewAttached()) {
                    getView().changePwdSuccess(s);
                }
            }

            @Override
            public void onFailure(String s) {
                if (isViewAttached()) {
                    getView().changePwdFail(s);
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

