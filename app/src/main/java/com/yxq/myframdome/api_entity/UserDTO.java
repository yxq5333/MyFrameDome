package com.yxq.myframdome.api_entity;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.base.bean.MyBaseDTO;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.crazyhuskar.myandroidsdk.util.MyUtilLog;
import com.google.gson.Gson;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  15:23
 */
public class UserDTO extends MyBaseDTO<UserVO> {
    private UserVO userVO;
    private String id;
    private String username = "";
    private String name = "";
    private String token = "";
    private int type;
    private int status;
    private String roleName = "";
    private String currentRoleName = "";
    private String mobile = "";
    private String rejectReason = "";

    @Override
    public UserVO transform() {
        userVO = new UserVO();
        userVO.setId(id);
        userVO.setUsername(username);
        userVO.setName(name);
        userVO.setToken(token);
        userVO.setType(type);
        userVO.setStatus(status);
        userVO.setRoleName(roleName);
        userVO.setDepartment(currentRoleName);
        userVO.setPhone(mobile);
        userVO.setRejectReason(rejectReason);
        return userVO;
    }

    @Override
    public void execute(final MyCallback<UserVO> myCallback) {
        MyRetrofitUtil.getInstance().executeJson(headers, url, jsonObject, new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    MyUtilLog.e("Token:" + s);
                    myCallback.onSuccess(MyUtilJson.parseObject(MyUtilJson.toJSONString(json.getData()), UserDTO.class).transform());
                } else {
                    myCallback.onFailure(json.getMsg());
                }
            }

            @Override
            public void onFailure(String s) {
                myCallback.onError();
            }

            @Override
            public void onError() {
                myCallback.onError();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void register(final MyCallback<String> myCallback) {
        MyRetrofitUtil.getInstance().executeJson(headers, url, jsonObject, new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    myCallback.onSuccess(MyUtilJson.toJSONString(json.getData()));
                } else {
                    myCallback.onFailure(json.getMsg());
                }
            }

            @Override
            public void onFailure(String s) {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getUserInfo(final MyCallback<UserVO> myCallback) {
        MyRetrofitUtil.getInstance().executeJson(headers, url, jsonObject, new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    myCallback.onSuccess(MyUtilJson.parseObject(MyUtilJson.toJSONString(json.getData()), UserDTO.class).transform());
                } else {
                    myCallback.onFailure(json.getMsg());
                }
            }

            @Override
            public void onFailure(String s) {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void changepassword(final MyCallback<String> myCallback) {
        MyRetrofitUtil.getInstance().executeJson(headers, url, jsonObject, new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    myCallback.onSuccess("");
                } else {
                    myCallback.onFailure(json.getMsg());
                }
            }

            @Override
            public void onFailure(String s) {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void checkRole(MyCallback<String> myCallback) {
        MyRetrofitUtil.getInstance().executeGet(headers, url, params, new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                MyUtilLog.e(s);
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    myCallback.onSuccess(new Gson().toJson(json.getData()));
                } else {
                    myCallback.onFailure(json.getMsg());
                }
            }

            @Override
            public void onFailure(String s) {

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
