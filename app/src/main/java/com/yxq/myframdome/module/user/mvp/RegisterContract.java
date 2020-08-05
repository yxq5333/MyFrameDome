package com.yxq.myframdome.module.user.mvp;

import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenter;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpView;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  16:38
 */
public interface RegisterContract {
    interface RegisterPresenter extends MyBaseMvpPresenter<RegisterView> {
        void summit(int type, String mobile, String password, String username);
    }

    interface RegisterView extends MyBaseMvpView {
        void registerSuccess(String msg);

        void registerFail(String msg);
    }
}
