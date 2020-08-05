package com.yxq.myframdome.module.test;

import androidx.appcompat.app.AppCompatActivity;

import com.crazyhuskar.myandroidsdk.base.MyBaseActivity;
import com.yxq.myframdome.R;
import com.yxq.myframdome.util.FragmentPagesUtil;
import com.yxq.myframdome.util.RatioImageView;
import com.yxq.myframdome.util.TimelineView;
import com.yxq.myframdome.util.UninterceptableListView;

public class FragmentPagesTestActivity extends MyBaseActivity {

    @Override
    public int setLayoutID() {
        return R.layout.activity_test;
    }

    @Override
    public AppCompatActivity registerActivity() {
        return this;
    }

    @Override
    public void init() {
        FragmentPagesUtil.getInstant(this).getList(
                new TimelineView(this),
                new UninterceptableListView(this),
                new RatioImageView(this));

    }
}
