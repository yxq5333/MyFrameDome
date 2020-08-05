package com.yxq.myframdome.module.test;

import com.crazyhuskar.myandroidsdk.base.MyBaseActivity;
import com.yxq.myframdome.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Hollow Goods on 2019-04-10.
 */
public class TestActivity extends MyBaseActivity {

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
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fl_content, new TestFragment());
        ft.commitAllowingStateLoss();
    }

}
