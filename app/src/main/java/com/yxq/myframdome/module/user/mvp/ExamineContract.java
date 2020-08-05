package com.yxq.myframdome.module.user.mvp;

import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpPresenter;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpView;
import com.yxq.myframdome.api_entity.ExamineInfoVO;
import com.yxq.myframdome.api_entity.FileVO;

import java.io.File;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  16:38
 */
public interface ExamineContract {
    interface ExaminePresenter extends MyBaseMvpPresenter<ExamineView> {
        void uploadFiles(String token, File file);

        void update(int type, String token, ExamineInfoVO examineInfoVO);

        void getData(String token, int type);
    }

    interface ExamineView extends MyBaseMvpView {
        void uploadSuccess(FileVO fileVO);

        void uploadFailure(String msg);

        void updateSuccess(String mag);

        void updateFailure(String msg);

        void getSuccess(ExamineInfoVO examineInfoVO);

        void getFailure(String msg);
    }
}
