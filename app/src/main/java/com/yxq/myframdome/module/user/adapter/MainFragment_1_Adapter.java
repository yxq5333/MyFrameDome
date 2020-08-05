package com.yxq.myframdome.module.user.adapter;

import android.content.Context;

import com.crazyhuskar.myandroidsdk.adapter.recyclerview.MultiItemTypeAdapter;
import com.crazyhuskar.myandroidsdk.adapter.recyclerview.bean.CommonBean;

import java.util.List;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-13  9:30
 */
public class MainFragment_1_Adapter extends MultiItemTypeAdapter<CommonBean<MainFragment_1_Entity>> {

    public MainFragment_1_Adapter(Context context, List<CommonBean<MainFragment_1_Entity>> datas) {
        super(context, datas);
        addItemViewDelegate(MainFragment_1_Entity.TYPE_1, new MainFragment_1_Adapter_1(context));
        addItemViewDelegate(MainFragment_1_Entity.TYPE_2, new MainFragment_1_Adapter_2(context));
    }

    public void refreshData(List<CommonBean<MainFragment_1_Entity>> datas) {
        super.mDatas = datas;
        notifyDataSetChanged();
    }

}
