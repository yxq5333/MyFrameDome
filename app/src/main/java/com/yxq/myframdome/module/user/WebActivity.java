package com.yxq.myframdome.module.user;

import android.view.View;
import android.webkit.WebView;

import com.crazyhuskar.myandroidsdk.base.MyBaseActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.yxq.myframdome.R;
import com.yxq.myframdome.module.user.adapter.MainFragment_1_Banner_Entity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

/**
 * @author CrazyHuskar
 * caeat at 2019-01-16  16:24
 */
public class WebActivity extends MyBaseActivity {
    @BindView(R.id.webview)
    WebView webview;

    @Override
    public int setLayoutID() {
        return R.layout.activity_web;
    }

    @Override
    public AppCompatActivity registerActivity() {
        return this;
    }

    @Override
    public void init() {
        MainFragment_1_Banner_Entity mainFragment_1_banner_entity = MyUtilJson.parseObject(getIntent().getStringExtra("bean"), MainFragment_1_Banner_Entity.class);
        setCommonTitle(mainFragment_1_banner_entity.getTitle());
        commonTitle.setCommonTitleLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtilActivity.getInstance().finishActivity(WebActivity.this);
            }
        });
        webview.loadUrl(mainFragment_1_banner_entity.getLinkurl());
    }
}
