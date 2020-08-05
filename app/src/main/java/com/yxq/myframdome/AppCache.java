package com.yxq.myframdome;

import android.content.Context;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.crazyhuskar.myandroidsdk.util.MyUtilSharedPreferences;
import com.yxq.myframdome.api_entity.UserVO;

import java.util.List;

public class AppCache {

    private Context mContext;

    private AppCache(Context context) {
        this.mContext = context;
    }

    private static AppCache instance = null;

    public static AppCache getInstance(Context context) {
        if (null == instance) {
            instance = new AppCache(context);
        }
        return instance;
    }


    /**
     * 获取登录状态
     *
     * @return
     */
    public boolean getLoginStatus() {
        return Boolean.parseBoolean(MyUtilSharedPreferences.getInstance((AppCompatActivity) mContext).get(AppConfig.KEY_LOGIN_STATUS, false).toString());
    }

    /**
     * 保存用户登录状态
     *
     * @param status
     * @return
     */
    public void saveLoginStatus(boolean status) {
        MyUtilSharedPreferences.getInstance((AppCompatActivity) mContext).put(AppConfig.KEY_LOGIN_STATUS, status);
    }

    /**
     * 保存用户信息
     *
     * @param userVO
     */
    public void saveUserInfo(UserVO userVO) {
        try {
            MyUtilSharedPreferences.getInstance((AppCompatActivity) mContext).put(AppConfig.KEY_USER_INFO, MyUtilJson.toJSONString(userVO));
        } catch (Exception e) {

        }
    }

    /**
     * 保存用户登录状态
     *
     * @param username
     * @param password
     * @return
     */
    public void saveUsernameAndPassword(String username, String password) {
        MyUtilSharedPreferences.getInstance((AppCompatActivity) mContext).put("app.username", username);
        MyUtilSharedPreferences.getInstance((AppCompatActivity) mContext).put("app.password", password);
    }

    /**
     * @return
     */
    public String getUsername() {
        return String.valueOf(MyUtilSharedPreferences.getInstance((AppCompatActivity) mContext).get("app.username", ""));
    }

    /**
     * @return
     */
    public String getPassword() {
        return String.valueOf(MyUtilSharedPreferences.getInstance((AppCompatActivity) mContext).get("app.password", ""));
    }

    /**
     * 获取登录用户的信息
     *
     * @return
     */
    public UserVO getUserInfo() {
        UserVO userEntity = new UserVO();
        try {
            String strUserInfo = MyUtilSharedPreferences.getInstance((AppCompatActivity) mContext).get(AppConfig.KEY_USER_INFO, "").toString();
            userEntity = MyUtilJson.parseObject(strUserInfo, UserVO.class);
        } catch (Exception e) {

        }
        if (null == userEntity) {
            userEntity = new UserVO();
        }
        return userEntity;
    }

    /**
     * 缓存用户的登录名称记录
     *
     * @return
     */
    public void saveLoginName(String loginName) {

        if (TextUtils.isEmpty(loginName)) {
            return;
        }

        boolean isIn = false;
        List<String> nameList = getLoginName();
        for (int i = 0; i < nameList.size(); i++) {
            if (loginName.equals(nameList.get(i))) {
                isIn = true;
                break;
            }
        }
        if (isIn) {
            return;
        }
        if (nameList.size() >= 5) {
            nameList.remove(nameList.size() - 1);
        }
        nameList.add(loginName);
        MyUtilSharedPreferences.getInstance((AppCompatActivity) mContext).put(AppConfig.KEY_LOGIN_NAME, MyUtilJson.toJSONString(nameList));

    }


    /**
     * 保存登录记录
     *
     * @param nameList
     */
    public void saveLoginNameList(List<String> nameList) {

        if (null == nameList) {
            return;
        }
        MyUtilSharedPreferences.getInstance((AppCompatActivity) mContext).put(AppConfig.KEY_LOGIN_NAME, MyUtilJson.toJSONString(nameList));
    }

    /**
     * 获取用户的登录名称列表
     *
     * @return
     */
    public List<String> getLoginName() {
        List<String> nameList;
        String jsonData = MyUtilSharedPreferences.getInstance((AppCompatActivity) mContext).get(AppConfig.KEY_LOGIN_NAME, "").toString();
        nameList = MyUtilJson.parseList(jsonData, String.class);
        return nameList;
    }

}
