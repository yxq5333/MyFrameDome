package com.yxq.myframdome.api_entity;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.base.bean.MyBaseDTO;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.crazyhuskar.myandroidsdk.util.MyUtilLog;
import com.google.gson.Gson;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-14  15:37
 */
public class ExamineInfoDTO extends MyBaseDTO<ExamineInfoVO> {

    private ExamineInfoVO examineInfoVO = new ExamineInfoVO();

    @Override
    public ExamineInfoVO transform() {
        return examineInfoVO;
    }

    @Override
    public void execute(final MyCallback<ExamineInfoVO> myCallback) {
        MyRetrofitUtil.getInstance().executeJson(headers, url, jsonObject, new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                MyUtilLog.e(s);
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);

                if (json.isSuccess()) {
                    Gson gson = new Gson();
                    ExamineInfoVO examineInfoVO = gson.fromJson(gson.toJson(json.getData()), ExamineInfoVO.class);
                    myCallback.onSuccess(examineInfoVO);
                } else {
                    myCallback.onFailure("");
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
