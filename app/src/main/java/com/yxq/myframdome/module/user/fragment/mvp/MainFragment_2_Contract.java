package com.yxq.myframdome.module.user.fragment.mvp;

import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenter;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpView;
import com.yxq.myframdome.api_entity.MsgVO;

import java.util.List;
import java.util.Map;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  16:38
 */
public interface MainFragment_2_Contract {
    interface MainFragment_2_Presenter extends MyBaseMvpPresenter<MainFragment_2_View> {
        void loadData(String token,String key, Map<String, Object> param);
        void updateState(String token,Integer key);

    }

    interface MainFragment_2_View extends MyBaseMvpView {
        void getSuccess(List<MsgVO> listVO);
        void getFail(String s);
        void Success(String s);
        void Fail(String s);
    }
}
