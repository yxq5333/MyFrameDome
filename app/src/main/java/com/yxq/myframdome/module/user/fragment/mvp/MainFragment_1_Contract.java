package com.yxq.myframdome.module.user.fragment.mvp;

import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenter;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpView;
import com.yxq.myframdome.api_entity.BannerVO;

import java.util.List;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  16:38
 */
public interface MainFragment_1_Contract {
    interface MainFragment_1_Presenter extends MyBaseMvpPresenter<MainFragment_1_View> {
        void getBanner(String token,String type);
    }

    interface MainFragment_1_View extends MyBaseMvpView {
        void getBannerSuccess(List<BannerVO> bannerVOList);
        void faliure(String msg);
    }
}
