package com.yxq.myframdome.module.user.fragment.mvp;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenterImpl;
import com.crazyhuskar.myandroidsdk.base.mvp.MyDataModel;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.api_entity.BannerDTO;
import com.yxq.myframdome.api_entity.BannerVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点击操作后结果
 *
 * @author CrazyHuskar
 * caeat at 2018-12-04  15:17
 */
public class MainFragment_1_PresenterImpl extends MyBaseMvpPresenterImpl<MainFragment_1_Contract.MainFragment_1_View> implements MainFragment_1_Contract.MainFragment_1_Presenter {
    @Override
    public void getBanner(String token, String type) {
        Map<String, Object> header = new HashMap<>();
        header.put("Authorization", token);
        Map<String, String> params = new HashMap<>();
        params.put("site", type);
        MyDataModel.request(BannerDTO.class.getName())
                .url(APIConfig.BANNERLIST)
                .params(params)
                .headers(header).execute(new MyCallback<List<BannerVO>>() {
            @Override
            public void onSuccess(List<BannerVO> listVO) {
                if (isViewAttached()) {
                    getView().getBannerSuccess(listVO);
                }
            }

            @Override
            public void onFailure(String s) {
                getView().faliure(s);
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

