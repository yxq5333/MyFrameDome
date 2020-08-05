package com.yxq.myframdome.module.user.mvp;

import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenter;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpView;
import com.yxq.myframdome.api_entity.UserVO;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  16:38
 */
public interface LoginContract {
    interface LoginPresenter extends MyBaseMvpPresenter<LoginView> {
        /**
         * 登录
         *
         * @param userVO
         */
        void login(UserVO userVO);

    }

    interface LoginView extends MyBaseMvpView {
        /**
         * 登录成功
         *
         * @param userVO
         */
        void loginSuccess(UserVO userVO);

        /**
         * 登录失败
         */
        void loginFail(String msg);

        /**
         * 登录失败
         */
        void loginError(String msg);

    }
}
