package com.yxq.myframdome.module;

import android.Manifest;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.crazyhuskar.myandroidsdk.base.MyBaseActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilPermission;
import com.yxq.myframdome.AppConfig;
import com.yxq.myframdome.R;
import com.yxq.myframdome.module.user.LoginActivity;

import butterknife.BindView;
/**
 * 欢迎界面
 */
public class IndexActivity extends MyBaseActivity {

    @BindView(R.id.start_bg)
    ImageView startBg;
    private Animation mFadeIn;

    @Override
    public int setLayoutID() {
        return R.layout.activity_index;
    }

    @Override
    public AppCompatActivity registerActivity() {
        return this;
    }

    @Override
    public void init() {

        switch (AppConfig.APP_COMPANY) {
            case XHTT:
                startBg.setImageResource(R.mipmap.bg_index);
                break;
            case MAS:
            case TaiZhou:
                startBg.setImageResource(R.mipmap.bg_index);
                break;
            default:
                startBg.setImageResource(R.mipmap.bg_index);
                break;
        }

        initImmersionBar();
        mFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        mFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                MyUtilPermission.getInstance().init(IndexActivity.this, new MyUtilPermission.PermissionCallback() {
                    @Override
                    public void onSucceed() {
                        MyUtilActivity.getInstance().finishActivity(IndexActivity.this);
                        MyUtilActivity.getInstance().startActivity(IndexActivity.this, LoginActivity.class);
//                        MyUtilActivity.getInstance().startActivity(StartActivity.this, PerformanceStatisticsActivity.class);
                    }

                    @Override
                    public void onFail() {

                    }
                }).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        startBg.startAnimation(mFadeIn);
    }
}
