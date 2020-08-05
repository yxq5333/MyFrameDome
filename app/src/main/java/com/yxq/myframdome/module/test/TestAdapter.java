package com.yxq.myframdome.module.test;

import android.content.Context;

import com.crazyhuskar.myandroidsdk.adapter.recyclerview.CommonAdapter;
import com.crazyhuskar.myandroidsdk.adapter.recyclerview.base.ViewHolder;
import com.yxq.myframdome.R;

import java.util.List;

/**
 * Created by Hollow Goods on 2019-04-10.
 */
public class TestAdapter extends CommonAdapter<Test> {

    private int checkedPosition = -1;

    public TestAdapter(Context context, int layoutId, List<Test> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Test item, int position) {
        holder.setText(R.id.tv_text, "家具" + (position + 1));
        holder.setBackgroundRes(R.id.tv_text, checkedPosition == position ? R.color.blue_500 : R.color.grey_500);
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }
}
