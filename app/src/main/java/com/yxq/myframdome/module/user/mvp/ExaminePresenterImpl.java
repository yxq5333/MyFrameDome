package com.yxq.myframdome.module.user.mvp;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenterImpl;
import com.crazyhuskar.myandroidsdk.base.mvp.MyDataModel;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.api_entity.ExamineInfoDTO;
import com.yxq.myframdome.api_entity.ExamineInfoVO;
import com.yxq.myframdome.api_entity.FileDTO;
import com.yxq.myframdome.api_entity.FileVO;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  15:17
 */
public class ExaminePresenterImpl extends MyBaseMvpPresenterImpl<ExamineContract.ExamineView> implements ExamineContract.ExaminePresenter {
    @Override
    public void uploadFiles(String token, final File file) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        MyDataModel.request(FileDTO.class.getName())
                .headers(headers)
                .url(APIConfig.FILEUPLOAD)
                .files(new ArrayList<File>() {{
                    add(file);
                }}).execute(new MyCallback<FileVO>() {
            @Override
            public void onSuccess(FileVO fileVO) {
                if (isViewAttached()) {
                    getView().uploadSuccess(fileVO);
                }
            }

            @Override
            public void onFailure(String s) {
                if (isViewAttached()) {
                    getView().uploadFailure(s);
                }
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
    public void update(int type, String token, ExamineInfoVO examineInfoVO) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        MyDataModel.request(ExamineInfoDTO.class.getName())
                .headers(headers)
                .jsonObject(MyUtilJson.toJSONString(examineInfoVO))
                .url(type == 1 ? APIConfig.COMPANYINFO : APIConfig.INSTITUTEINFO)
                .execute(new MyCallback<ExamineInfoVO>() {
                    @Override
                    public void onSuccess(ExamineInfoVO s) {
                        if (isViewAttached()) {
                            getView().updateSuccess("");
                        }
                    }

                    @Override
                    public void onFailure(String s) {
                        if (isViewAttached()) {
                            getView().updateFailure(s);
                        }
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
    public void getData(String token, int type) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        MyDataModel.request(ExamineInfoDTO.class.getName())
                .headers(headers)
                .url(type == 1 ? APIConfig.GET_COMPANY_INFO : APIConfig.GET_INSTITUTE_INFO)
                .execute(new MyCallback<ExamineInfoVO>() {
                    @Override
                    public void onSuccess(ExamineInfoVO s) {
                        if (isViewAttached()) {
                            getView().getSuccess(s);
                        }
                    }

                    @Override
                    public void onFailure(String s) {
                        if (isViewAttached()) {
                            getView().getFailure(s);
                        }
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

