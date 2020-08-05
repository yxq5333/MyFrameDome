package com.yxq.myframdome.module.user;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.crazyhuskar.myandroidsdk.base.MyBaseActivity;
import com.yxq.myframdome.R;
import com.yxq.myframdome.util.UpdateAPPUtils;


/**
 * 关于我们界面
 * <p>
 * Created by Hollow Goods on 2019-05-22
 */

public class AboutUsActivity extends MyBaseActivity {

    private ImageView logo;
    private Button update;

    @Override
    public int setLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    public AppCompatActivity registerActivity() {
        return this;
    }

    @Override
    public void init() {

        setCommonTitle("关于我们");
        commonTitle.setCommonTitleLeftListener(v -> finish());

        logo = findViewById(R.id.iv_logo);
        update = findViewById(R.id.btn_update);
        TextView version = findViewById(R.id.tv_version);
        version.setText("V");
        version.append(UpdateAPPUtils.getVersionName(this));
        version.append("-");
        version.append("release");
        version.append(" ");
        version.append("2019");

        logo.setImageResource(R.mipmap.logo);

        setListener();
    }


    public void setListener() {
        update.setOnClickListener(v -> UpdateAPPUtils.checkUpdate(AboutUsActivity.this, true));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UpdateAPPUtils.onInstallRequestActivityResult(requestCode, resultCode);
    }

}
