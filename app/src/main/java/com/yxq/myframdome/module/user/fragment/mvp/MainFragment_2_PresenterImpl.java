package com.yxq.myframdome.module.user.fragment.mvp;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenterImpl;
import com.crazyhuskar.myandroidsdk.base.mvp.MyDataModel;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.api_entity.MsgDTO;
import com.yxq.myframdome.api_entity.MsgVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点击操作后结果
 *
 * @author CrazyHuskar
 * caeat at 2018-12-04  15:17
 */
public class MainFragment_2_PresenterImpl extends MyBaseMvpPresenterImpl<MainFragment_2_Contract.MainFragment_2_View> implements MainFragment_2_Contract.MainFragment_2_Presenter {

    @Override
    public void loadData(String token, String key, Map<String, Object> param) {
        Map<String, Object> header = new HashMap<>();
        header.put("Authorization", token);

        MyDataModel.request(MsgDTO.class.getName())
                .url(APIConfig.LIST + "?resourceType="+ "&page=" + param.get("page") + "&limit=" + param.get("limit"))
                .headers(header).execute(new MyCallback<List<MsgVO>>() {
            @Override
            public void onSuccess(List<MsgVO> listVO) {
                if (isViewAttached()) {
                    getView().getSuccess(listVO);
                }
            }

            @Override
            public void onFailure(String s) {
                getView().getFail(s);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void updateState(String token, Integer key) {

            Map<String, String> header = new HashMap<>();
            header.put("Authorization", token);

            List<Integer> ss=new ArrayList<>();
            ss.add(key);

            MsgDTO infoDTO = new MsgDTO();
            infoDTO.headers(header);
            infoDTO.url(APIConfig.UPDATESTATUS);
            infoDTO.jsonObject(MyUtilJson.toJSONString(ss));

            infoDTO.updateState(new MyCallback<MsgVO>() {
                @Override
                public void onSuccess(MsgVO heTongInfoVO) {
                    if (isViewAttached()) {
                        getView().Success("成功");
                    }
                }

                @Override
                public void onFailure(String s) {
                    getView().Fail(s);
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

