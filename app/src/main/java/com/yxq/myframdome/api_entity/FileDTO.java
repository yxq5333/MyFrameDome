package com.yxq.myframdome.api_entity;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.base.bean.MyBaseDTO;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-14  15:37
 */
public class FileDTO extends MyBaseDTO<FileVO> {

    private String fileOrignalName;
    private String fileName;
    private String name;
    private String fileUrl;

    private FileVO fileVO = new FileVO();

    @Override
    public FileVO transform() {
        fileVO.setFileOrignalName(fileOrignalName);
        fileVO.setFileName(fileName);
        fileVO.setName(fileName);
        fileVO.setFurl(fileName);

        return fileVO;
    }

    @Override
    public void execute(final MyCallback<FileVO> myCallback) {
        MyRetrofitUtil.getInstance().uploadFiles(headers, url, "", files, new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    FileDTO fileDTO = MyUtilJson.parseObject(MyUtilJson.toJSONString(json.getData()), FileDTO.class);
                    myCallback.onSuccess(fileDTO.transform());
                } else {
                    myCallback.onFailure(json.getMsg());
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
