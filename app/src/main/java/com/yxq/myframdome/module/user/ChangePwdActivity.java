package com.yxq.myframdome.module.user;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.crazyhuskar.myandroidsdk.SystemConfig;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilToast;
import com.crazyhuskar.myandroidsdk.util.bean.EventBean;
import com.crazyhuskar.myandroidsdk.view.util.MyViewUtilSVProgressHUB;
import com.yxq.myframdome.AppCache;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.UserVO;
import com.yxq.myframdome.module.user.mvp.ChangePwdContract;
import com.yxq.myframdome.module.user.mvp.ChangePwdPresenterImpl;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class ChangePwdActivity extends MyBaseMvpActivity<ChangePwdContract.ChangePwdPresenter> implements ChangePwdContract.ChangePwdView {


    @BindView(R.id.et_yuan_psw)
    EditText etYuanPsw;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_querenpwd)
    EditText etQuerenpwd;

    @Override
    public int setLayoutID() {
        return R.layout.activity_changepwd;
    }

    @Override
    public AppCompatActivity registerActivity() {
        return this;
    }

    @Override
    public void init() {
        setCommonTitle("修改密码");
        MyViewUtilSVProgressHUB.getInstance().init(this);
        commonTitle.setCommonTitleLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtilActivity.getInstance().finishActivity(ChangePwdActivity.this);
            }
        });
    }

    @Override
    protected ChangePwdContract.ChangePwdPresenter createPresenter() {
        return new ChangePwdPresenterImpl();
    }

    @OnClick(R.id.btn_psw)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etYuanPsw.getText().toString()) || TextUtils.isEmpty(etNewPwd.getText().toString()) || TextUtils.isEmpty(etQuerenpwd.getText().toString())) {
            MyUtilToast.showShort(this, "请填写完整信息");
        } else if (etYuanPsw.getText().toString().length() < 6 || etNewPwd.getText().toString().length() < 6 || etQuerenpwd.getText().toString().length() < 6) {
            MyUtilToast.showShort(this, "密码长度至少6位");
        } else if (etNewPwd.getText().toString().equals(etQuerenpwd.getText().toString())) {
            MyViewUtilSVProgressHUB.getInstance().showProgressHUD("修改中...");
            presenter.summit(etNewPwd.getText().toString(), etYuanPsw.getText().toString(), AppCache.getInstance(this).getUserInfo().getToken());
        }
    }

    @Override
    public void changePwdSuccess(String s) {
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
        EventBus.getDefault().post(new EventBean("", SystemConfig.EXITAPP));
        AppCache.getInstance(this).saveUserInfo(new UserVO());
        AppCache.getInstance(this).saveLoginStatus(false);
        MyUtilActivity.getInstance().startActivity(this, LoginActivity.class);
    }

    @Override
    public void changePwdFail(String s) {
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
        MyUtilToast.showShort(this, s);
    }
}
