package com.yxq.myframdome.sample.mvp;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenterImpl;
import com.crazyhuskar.myandroidsdk.base.mvp.MyDataModel;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.api_entity.UserDTO;
import com.yxq.myframdome.api_entity.UserVO;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  15:17
 */
public class APIPresenterImpl extends MyBaseMvpPresenterImpl<APIContract.APIView> implements APIContract.APIPresenter {
    @Override
    public void login(UserVO userVO) {
        if (isViewAttached()) {
            MyDataModel.request(UserDTO.class.getName()).url(APIConfig.LOGIN)
                    .jsonObject(MyUtilJson.toJSONString(userVO)).execute(new MyCallback<UserVO>() {
                @Override
                public void onSuccess(UserVO userVO) {
                    getView().loginSuccess(userVO);
                }

                @Override
                public void onFailure(String s) {
                    getView().loginFail(new UserVO());
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

    @Override
    public void wjmm() {

    }
}

