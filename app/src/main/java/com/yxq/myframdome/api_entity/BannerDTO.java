package com.yxq.myframdome.api_entity;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.base.bean.MyMapper;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseModel;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CrazyHuskar
 * caeat at 2019-01-16  14:14
 */
public class BannerDTO extends MyBaseModel<List<BannerVO>> implements MyMapper<BannerVO> {
    private FileVO file;
    private String linkUrl;
    private String fileTitle;
    private BannerVO bannerVO;

    @Override
    public BannerVO transform() {
        bannerVO = new BannerVO();
        bannerVO.setFile(file);
        bannerVO.setLinkUrl(linkUrl);
        bannerVO.setFileTitle(fileTitle);
        return bannerVO;
    }

    @Override
    public void execute(final MyCallback<List<BannerVO>> callback) {
        MyRetrofitUtil.getInstance().executeGet(headers, url, params, new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {

                    List<BannerDTO> bannerDTOList = MyUtilJson.parseList(MyUtilJson.toJSONString(MyUtilJson.parseObject(MyUtilJson.toJSONString(json.getData()), BaseListJson.class).getList()), BannerDTO.class);
                    List<BannerVO> bannerVOList = new ArrayList<>();
                    for (BannerDTO bannerDTO : bannerDTOList) {
                        bannerVOList.add(bannerDTO.transform());
                    }
                    callback.onSuccess(bannerVOList);
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
