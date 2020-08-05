package com.yxq.myframdome.module.user.mvp;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenterImpl;
import com.crazyhuskar.myandroidsdk.base.mvp.MyDataModel;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.api_entity.UserDTO;
import com.yxq.myframdome.api_entity.UserVO;

/**
 * 点击操作后结果
 *
 * @author CrazyHuskar
 * caeat at 2018-12-04  15:17
 */
public class LoginPresenterImpl extends MyBaseMvpPresenterImpl<LoginContract.LoginView> implements LoginContract.LoginPresenter {
    @Override
    public void login(UserVO userVO) {
        MyDataModel.request(UserDTO.class.getName())
                .url(APIConfig.LOGIN)
                .jsonObject(MyUtilJson.toJSONString(userVO))
                .execute(new MyCallback<UserVO>() {
                    @Override
                    public void onSuccess(UserVO userVO) {
                        if (isViewAttached()) {
                            getView().loginSuccess(userVO);
                        }
                    }

                    @Override
                    public void onFailure(String s) {
                        if (isViewAttached()) {
                            getView().loginFail(s);
                        }
                    }

                    @Override
                    public void onError() {
                        if (isViewAttached()) {
                            getView().loginError("");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

