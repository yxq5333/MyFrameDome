package com.yxq.myframdome.sample.adapter;

import android.content.Context;

import com.crazyhuskar.myandroidsdk.adapter.recyclerview.CommonAdapter;
import com.crazyhuskar.myandroidsdk.adapter.recyclerview.base.ViewHolder;
import com.yxq.myframdome.R;

import java.util.List;

public class APIAdapter extends CommonAdapter<String> {

    public APIAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(final ViewHolder viewHolder, String item, int position) {
        viewHolder.setText(R.id.item_title, item);
    }
}
