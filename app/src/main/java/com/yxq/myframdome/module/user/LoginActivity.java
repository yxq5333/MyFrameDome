package com.yxq.myframdome.module.user;

import android.content.Intent;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.crazyhuskar.myandroidsdk.util.MyUtilLog;
import com.crazyhuskar.myandroidsdk.util.MyUtilToast;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.MyDialogFragment;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.base.BindViewHolder;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.listener.OnBindViewListener;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.listener.OnViewClickListener;
import com.crazyhuskar.myandroidsdk.view.util.MyViewUtilSVProgressHUB;
import com.google.gson.Gson;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.AppCache;
import com.yxq.myframdome.AppConfig;
import com.yxq.myframdome.MyApplication;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.BaseJson;
import com.yxq.myframdome.api_entity.SystemInfo;
import com.yxq.myframdome.api_entity.UserVO;
import com.yxq.myframdome.module.user.mvp.LoginContract;
import com.yxq.myframdome.module.user.mvp.LoginPresenterImpl;
import com.yxq.myframdome.util.IP.InterfaceConfig;
import com.yxq.myframdome.util.UpdateAPPUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends MyBaseMvpActivity<LoginContract.LoginPresenter> implements LoginContract.LoginView {

    @BindView(R.id.ed_username)
    AutoCompleteTextView edUsername;
    @BindView(R.id.ed_userpwd)
    EditText edUserpwd;
    @BindView(R.id.imvCode)
    ImageView imvCode;
    @BindView(R.id.tvKey1)
    TextView tvKey1;

    @Override
    public int setLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    public AppCompatActivity registerActivity() {
        return this;
    }

    @Override
    public void init() {

        getSystemInfo();

        switch (AppConfig.APP_COMPANY) {
            case XHTT:
                imvCode.setVisibility(View.VISIBLE);
                tvKey1.setText(R.string.app_name);
                break;
            case MAS:
                imvCode.setVisibility(View.GONE);
                tvKey1.setText("智慧安全生产一体化\n公共服务平台");
                tvKey1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
                tvKey1.setGravity(Gravity.CENTER_HORIZONTAL);
                break;
            case AQZHPT:
                imvCode.setVisibility(View.GONE);
                tvKey1.setText("智慧安全生产平台");
                tvKey1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
                tvKey1.setGravity(Gravity.CENTER_HORIZONTAL);
                break;
            case TaiZhou:
                imvCode.setVisibility(View.GONE);
                tvKey1.setText("云升安全");
                tvKey1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
                break;
        }

        initImmersionBar();
        MyViewUtilSVProgressHUB.getInstance().init(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AppCache.getInstance(this).getLoginName());
        edUsername.setAdapter(arrayAdapter);

        if (AppCache.getInstance(this).getLoginStatus()) {
            String username = AppCache.getInstance(this).getUsername();
            String password = AppCache.getInstance(this).getPassword();
            edUsername.setText(username);
            edUserpwd.setText(password);

            if (loginCheckParams(username, password)) {
                UserVO userVO = new UserVO();
                userVO.setUsername(username);
                userVO.setPassword(password);
                MyViewUtilSVProgressHUB.getInstance().showProgressHUD("登录中...");
                presenter.login(userVO);
            } else {
                UpdateAPPUtils.checkUpdate(this, false);
            }
        } else {
            UpdateAPPUtils.checkUpdate(this, false);
        }

        imvCode.setOnLongClickListener(v -> {
            new InterfaceConfig().showIPDialog(LoginActivity.this);
            return true;
        });

        tvKey1.setOnLongClickListener(v -> {
            new InterfaceConfig().showIPDialog(LoginActivity.this);
            return true;
        });

//        edUsername.setText("000");
//        edUserpwd.setText("123456");
    }

    @Override
    protected LoginContract.LoginPresenter createPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    public void loginSuccess(UserVO userVO) {

        AppCache.getInstance(this).saveUsernameAndPassword(
                edUsername.getText().toString(),
                edUserpwd.getText().toString()
        );
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();

        MyApplication.getApp().setCompanyUser(userVO.getType() == 1);

        switch (userVO.getStatus()) {
            case 0:
                forbidden();
                break;
            case 1:
            case 2:
            case 3:
                AppCache.getInstance(this).saveUserInfo(userVO);
                AppCache.getInstance(this).saveLoginName(userVO.getUsername());
                AppCache.getInstance(this).saveLoginStatus(true);
                examining();
                break;
            case 11:
                AppCache.getInstance(this).saveUserInfo(userVO);
                AppCache.getInstance(this).saveLoginName(userVO.getUsername());
                AppCache.getInstance(this).saveLoginStatus(true);
                MyUtilActivity.getInstance().finishActivity(LoginActivity.this);
                MyUtilActivity.getInstance().startActivity(LoginActivity.this, MainActivity.class);
                break;
            default:
                MyUtilActivity.getInstance().finishActivity(this);
                break;
        }


    }

    @Override
    public void loginFail(String msg) {
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
        MyUtilToast.showShort(this, msg);
    }

    @Override
    public void loginError(String msg) {
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
        MyUtilToast.showShort(this, "网络错误");
    }

    public void forbidden() {
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
        new MyDialogFragment.Builder(getSupportFragmentManager()).setOnBindViewListener(new OnBindViewListener() {
            @Override
            public void bindView(BindViewHolder bindViewHolder) {
                bindViewHolder.setText(R.id.dialogfragmen_title, "账号已被封禁");
                bindViewHolder.setText(R.id.dialogfragmen_content, "您的账户已被管理员封禁");
                bindViewHolder.getView(R.id.dialogfragmen_cancel).setVisibility(View.GONE);
            }
        }).addOnClickListener(R.id.dialogfragmen_ok, R.id.dialogfragmen_cancel).setOnViewClickListener(new OnViewClickListener() {
            @Override
            public void onViewClick(BindViewHolder bindViewHolder, View view, MyDialogFragment myDialogFragment) {
                switch (view.getId()) {
                    case R.id.dialogfragmen_ok:
                        myDialogFragment.dismiss();
                        break;
                    default:
                        break;
                }
            }
        }).create().show();
    }

    public void examining() {
        MyUtilActivity.getInstance().finishActivity(LoginActivity.this);
        MyUtilActivity.getInstance().startActivity(LoginActivity.this, ExamineActivity.class);
    }

    @OnClick({/*R.id.tv_new_register, */R.id.btn_login, R.id.tv_forget_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.tv_new_register:
//                MyUtilActivity.getInstance().startActivity(this, RegisterActivity.class);
//                break;
            case R.id.btn_login:
                String aPhoto = edUsername.getText().toString().trim();
                String aPwd = edUserpwd.getText().toString().trim();
                if (loginCheckParams(aPhoto, aPwd)) {
                    UserVO userVO = new UserVO();
                    userVO.setPassword(aPwd);
                    userVO.setUsername(aPhoto);
                    MyViewUtilSVProgressHUB.getInstance().showProgressHUD("登录中...");
                    presenter.login(userVO);
                }
                break;
            case R.id.tv_forget_password:
                new MyDialogFragment.Builder(getSupportFragmentManager()).setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder bindViewHolder) {
                        bindViewHolder.setText(R.id.dialogfragmen_title, "忘记密码");
                        bindViewHolder.setText(R.id.dialogfragmen_content, "请联系管理员");
                        bindViewHolder.getView(R.id.dialogfragmen_cancel).setVisibility(View.GONE);
                    }
                }).addOnClickListener(R.id.dialogfragmen_ok, R.id.dialogfragmen_cancel).setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder bindViewHolder, View view, MyDialogFragment myDialogFragment) {
                        switch (view.getId()) {
                            case R.id.dialogfragmen_ok:
                                myDialogFragment.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).create().show();
                break;
            default:
                break;
        }
    }

    private boolean loginCheckParams(String username, String pwd) {

        if (TextUtils.isEmpty(username)) {
            MyUtilToast.showShort(this, "请输入用户名!");
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
            MyUtilToast.showShort(this, "请输入密码!");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UpdateAPPUtils.onInstallRequestActivityResult(requestCode, resultCode);
    }

    private void getSystemInfo() {

        Map<String, String> header = new HashMap<>();

        MyRetrofitUtil.getInstance().executeGet(header, APIConfig.GET_SYSTEM_INFO, new MyCallback<String>() {
            @Override
            public void onSuccess(String data) {
                BaseJson json = MyUtilJson.parseObject(data, BaseJson.class);
                if (json.isSuccess()) {
                    SystemInfo systemInfo = new Gson().fromJson(
                            new Gson().toJson(json.getData()),
                            SystemInfo.class
                    );

                    if (systemInfo != null) {
                        if (!TextUtils.isEmpty(systemInfo.getSystemName())) {
                            tvKey1.setText(systemInfo.getSystemName());
                        }

                        if (systemInfo.getSystemLogoFile() != null && isAlive) {
                            RequestOptions options = new RequestOptions()
                                    .priority(Priority.NORMAL)
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                    .centerInside();
                            Glide.with(LoginActivity.this).load(APIConfig.BASEURL + APIConfig.FILEDOWN + systemInfo.getSystemLogoFile().getName()).apply(options)
                                    .transition(new DrawableTransitionOptions().crossFade())
                                    .into(imvCode);
                        }
                    }
                }
                MyUtilLog.e(data);
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private boolean isAlive = true;

    @Override
    protected void onDestroy() {
        isAlive = false;
        super.onDestroy();
    }
}
