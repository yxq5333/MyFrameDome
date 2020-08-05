package com.yxq.myframdome.module.user.mvp;

import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenter;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpView;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  16:38
 */
public interface ChangePwdContract {
    interface ChangePwdPresenter extends MyBaseMvpPresenter<ChangePwdView> {
        void summit(String n, String o, String token);
    }

    interface ChangePwdView extends MyBaseMvpView {
        void changePwdSuccess(String s);

        void changePwdFail(String s);
    }
}
