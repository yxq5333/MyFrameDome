package com.yxq.myframdome.module.user.mvp;

import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenter;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpView;
import com.yxq.myframdome.api_entity.MenuVO;
import com.yxq.myframdome.api_entity.UserVO;

import java.util.List;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  16:38
 */
public interface MainContract {
    interface MainPresenter extends MyBaseMvpPresenter<MainView> {
        /**
         * 获取菜单
         *
         * @param token
         */
        void getMenu(String token);

        void getUserInfo(String token);

    }

    interface MainView extends MyBaseMvpView {
        void getUserInfo(UserVO userVO);

        void creatMenu(List<MenuVO> menuVOList);

        void examine();

        void expired();

        void getMenuError();
    }
}
