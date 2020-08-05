package com.yxq.myframdome.module.user.mvp;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenterImpl;
import com.crazyhuskar.myandroidsdk.base.mvp.MyDataModel;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.api_entity.MenuDTO;
import com.yxq.myframdome.api_entity.MenuVO;
import com.yxq.myframdome.api_entity.UserDTO;
import com.yxq.myframdome.api_entity.UserVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点击操作后结果
 *
 * @author CrazyHuskar
 * caeat at 2018-12-04  15:17
 */
public class MainPresenterImpl extends MyBaseMvpPresenterImpl<MainContract.MainView> implements MainContract.MainPresenter {

    @Override
    public void getMenu(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        MyDataModel.request(MenuDTO.class.getName())
                .url(APIConfig.MENU)
                .headers(headers)
                .execute(new MyCallback<List<MenuVO>>() {
                    @Override
                    public void onSuccess(List<MenuVO> menuVOList) {
                        if (isViewAttached()) {
                            getView().creatMenu(menuVOList);
                        }
                    }

                    @Override
                    public void onFailure(String s) {
                        if (isViewAttached()) {
                            getView().getMenuError();
                        }
                    }

                    @Override
                    public void onError() {
                        if (isViewAttached()) {
                            getView().expired();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getUserInfo(String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        UserDTO userDTO = new UserDTO();
        userDTO.headers(headers);
        userDTO.url(APIConfig.GETUSERINFO);
        userDTO.getUserInfo(new MyCallback<UserVO>() {
            @Override
            public void onSuccess(UserVO userVO) {
                if (isViewAttached()) {
                    getView().getUserInfo(userVO);
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

