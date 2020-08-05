package com.yxq.myframdome.api_entity;

import android.text.TextUtils;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.base.bean.MyMapper;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseModel;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yxq.myframdome.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-12  11:51
 */
public class MenuDTO extends MyBaseModel<List<MenuVO>> implements MyMapper<MenuVO> {
    private MenuVO menuVO;
    private int menuId;
    private String name = "";
    private List<MenuDTO> list = new ArrayList<>();

    @Override
    public MenuVO transform() {
        menuVO = new MenuVO();
        menuVO.setMenuId(menuId);
        menuVO.setName(name);
        List<MenuVO> menuVOList = new ArrayList<>();
        for (MenuDTO menuDTO : list) {
            menuVOList.add(menuDTO.transform());
        }
        menuVO.setMenuVOList(menuVOList);
        return menuVO;
    }

    @Override
    public void execute(final MyCallback<List<MenuVO>> myCallback) {
        MyRetrofitUtil.getInstance().executeGet(headers, url, new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    Map<String, Object> temp = new Gson().fromJson(MyUtilJson.toJSONString(json.getData()), new TypeToken<Map<String, Object>>() {
                    }.getType());
                    List<String> permissions = MyUtilJson.parseList(MyUtilJson.toJSONString(temp.get("permissions")), String.class);
//                    if (MyApplication.getApp().isCompanyUser()) {
                    permissions = new ArrayList<>();
                    permissions.add("yqyd");
                    permissions.add("xczd");
                    permissions.add("aqtgbg");
                    permissions.add("fxbs");
                    permissions.add("yhpc");
                    permissions.add("aqzrs");
                    permissions.add("zdgc");
                    permissions.add("aqpx");
                    permissions.add("yjyl");
                    permissions.add("zyws");
                    permissions.add("msh");
                    permissions.add("aqtz");
                    permissions.add("tgfwzj");
                    permissions.add("ticket");
//                    }
                    MyApplication.getApp().setPermissions(permissions);
                    List<MenuDTO> menuDTOList = MyUtilJson.parseList(MyUtilJson.toJSONString(temp.get("menuList")), MenuDTO.class);
                    if (menuDTOList.size() > 0) {
                        List<MenuVO> menuVOList = new ArrayList<>();
                        for (MenuDTO menuDTO : menuDTOList) {
                            MenuVO vo = menuDTO.transform();
                            if (TextUtils.equals(menuDTO.name, "业务审核")) {
                                for (int k = 0; k < vo.getMenuVOList().size(); ) {
                                    if (TextUtils.equals(vo.getMenuVOList().get(k).getName(), "查询")) {
                                        vo.getMenuVOList().remove(k);
                                    } else if (TextUtils.equals(vo.getMenuVOList().get(k).getName(), "服务进度")) {
                                        vo.getMenuVOList().remove(k);
                                    } else {
                                        k++;
                                    }
                                }
                            } else if (TextUtils.equals(menuDTO.name, "托管服务")) {
                                for (int k = 0; k < vo.getMenuVOList().size(); ) {
                                    if (TextUtils.equals(vo.getMenuVOList().get(k).getName(), "隐患汇总统计")
                                            || TextUtils.equals(vo.getMenuVOList().get(k).getName(), "历史轨迹")
                                            || TextUtils.equals(vo.getMenuVOList().get(k).getName(), "sos报警")
                                    ) {
                                        vo.getMenuVOList().remove(k);
                                    } else {
                                        k++;
                                    }
                                }
                            }
                            menuVOList.add(vo);
                        }
                        myCallback.onSuccess(menuVOList);
                    } else {
                        myCallback.onSuccess(new ArrayList<>());
                    }

                } else {
                    myCallback.onError();
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
