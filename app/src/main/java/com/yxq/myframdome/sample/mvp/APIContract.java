package com.yxq.myframdome.sample.mvp;

import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenter;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpView;
import com.yxq.myframdome.api_entity.UserVO;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  16:38
 */
public interface APIContract {
    interface APIPresenter extends MyBaseMvpPresenter<APIView> {
        void login(UserVO userVO);
        void wjmm();
    }

    interface APIView extends MyBaseMvpView {
        void loginSuccess(UserVO userVO);
        void loginFail(UserVO userVO);
    }
}
