package com.yxq.myframdome.api_entity;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.base.bean.MyMapper;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseModel;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;

import java.util.ArrayList;
import java.util.List;

public class BuMen extends MyBaseModel<List<BuMenVO>> implements MyMapper<BuMenVO> {

    private int id;
    private String value;

    @Override
    public BuMenVO transform() {

        BuMenVO buMenVO = new BuMenVO();
        buMenVO.setId(id);
        buMenVO.setValue(value);

        return buMenVO;
    }

    @Override
    public void execute(MyCallback<List<BuMenVO>> callback) {
        MyRetrofitUtil.getInstance().executeGet(headers, url, new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    List<BuMen> bumenList = MyUtilJson.parseList(MyUtilJson.toJSONString(json.getData()), BuMen.class);
                    List<BuMenVO> bumenVO = new ArrayList<>();
                    for (BuMen t : bumenList) {
                        bumenVO.add(t.transform());
                    }
                    callback.onSuccess(bumenVO);
                } else {
                    callback.onFailure(json.getMsg());
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
