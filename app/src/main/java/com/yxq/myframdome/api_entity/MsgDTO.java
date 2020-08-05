package com.yxq.myframdome.api_entity;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.base.bean.MyMapper;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseModel;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;

import java.util.ArrayList;
import java.util.List;

public class MsgDTO extends MyBaseModel<List<MsgVO>> implements MyMapper<MsgVO> {

    private Integer id = 0;
    /**
     * 用户名
     */
    private Integer userId;

    // 1待办  2消息
    private Integer resourceType;
    //标题：合同审核  业务受理  托管服务
    private String messageContent = "";
    //描述
    private String memo = "";

    private String createTime = "";

    private String updateTime = "";

    private Integer createUserId;

    private Integer updateUserId;
    //1 已读  0未读
    private Integer status = 0;

    private MsgVO msgVO;

    @Override
    public MsgVO transform() {
        msgVO = new MsgVO();
        msgVO.setId(id);
        msgVO.setUserId(userId);
        msgVO.setResourceType(resourceType);
        msgVO.setMessageContent(messageContent);
        msgVO.setMemo(memo);

        msgVO.setCreateTime(createTime.replace("T", " "));
        msgVO.setUpdateTime(updateTime);
        msgVO.setStatus(status);

        return msgVO;
    }

    @Override
    public void execute(final MyCallback<List<MsgVO>> myCallback) {
        MyRetrofitUtil.getInstance().executeGet(headers, url, new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    BaseListJson strList = MyUtilJson.parseObject(MyUtilJson.toJSONString(json.getData()), BaseListJson.class);
                    List<MsgDTO> msgDTOList = MyUtilJson.parseList(MyUtilJson.toJSONString(strList.getList()), MsgDTO.class);
                    List<MsgVO> msgVOS = new ArrayList<>();
                    for (MsgDTO msgDTO : msgDTOList) {
                        msgVOS.add(msgDTO.transform());
                    }
                    myCallback.onSuccess(msgVOS);
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

    public void updateState(final MyCallback<MsgVO> myCallback) {
        MyRetrofitUtil.getInstance().executeJson(headers, url, jsonObject, new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    MsgVO msgVO = new MsgVO();
                    myCallback.onSuccess(msgVO);
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
