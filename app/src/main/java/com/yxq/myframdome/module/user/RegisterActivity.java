package com.yxq.myframdome.module.user;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilToast;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.MyDialogFragment;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.base.BindViewHolder;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.listener.OnBindViewListener;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.listener.OnViewClickListener;
import com.crazyhuskar.myandroidsdk.view.util.MyViewUtilSVProgressHUB;
import com.yxq.myframdome.AppCache;
import com.yxq.myframdome.Constant;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.UserVO;
import com.yxq.myframdome.module.user.mvp.RegisterContract;
import com.yxq.myframdome.module.user.mvp.RegisterPresenterImpl;
import com.yxq.myframdome.util.VerifeCode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 */
public class RegisterActivity extends MyBaseMvpActivity<RegisterContract.RegisterPresenter> implements RegisterContract.RegisterView {

    @BindView(R.id.rgWorkTime)
    RadioGroup rgWorkTime;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_userphone)
    EditText edUserphone;
    @BindView(R.id.tvGetPwd)
    ImageView tvGetPwd;
    @BindView(R.id.ed_userpwd)
    EditText edUserpwd;
    @BindView(R.id.ed_set_pwd)
    EditText edSetPwd;
    @BindView(R.id.cb_file_declarations)
    CheckBox cbFileDeclarations;
    @BindView(R.id.tvNext)
    TextView tvNext;
    @BindView(R.id.tvKeyName)
    TextView tvKeyName;

    @Override
    public int setLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    public AppCompatActivity registerActivity() {
        return this;
    }

    @Override
    public void init() {
        tvGetPwd.setImageBitmap(VerifeCode.getInstance().createBitmap());
        setCommonTitle("注册");
        commonTitle.setCommonTitleLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtilActivity.getInstance().finishActivity(RegisterActivity.this);
            }
        });
        MyViewUtilSVProgressHUB.getInstance().init(this);
        tvNext.setBackgroundResource(R.drawable.bg_btn_login_blue);
        cbFileDeclarations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvNext.setBackgroundResource(R.drawable.bg_btn_login_blue);
                } else {
                    tvNext.setBackgroundResource(R.color.lightslategray);
                }
            }
        });
        rgWorkTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.confirm1:
                        tvKeyName.setText("企业名称");
                        edName.setHint("请输入企业名称");
                        break;
                    case R.id.refuse1:
                        tvKeyName.setText("机构名称");
                        edName.setHint("请输入机构名称");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected RegisterContract.RegisterPresenter createPresenter() {
        return new RegisterPresenterImpl();
    }

    @Override
    public void registerSuccess(String msg) {
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
        UserVO userVO = new UserVO();
        userVO.setToken(msg);
        AppCache.getInstance(this).saveUserInfo(userVO);
        MyUtilActivity.getInstance().finishActivity(this);
    }

    @Override
    public void registerFail(String msg) {
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
        MyUtilToast.showShort(this, msg);
    }

    @OnClick({R.id.tvGetPwd, R.id.tvCopyright, R.id.tvPrivacy, R.id.tvNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvGetPwd:
                tvGetPwd.setImageBitmap(VerifeCode.getInstance().createBitmap());
                break;
            case R.id.tvCopyright:
                new MyDialogFragment.Builder(getSupportFragmentManager())
                        .setLayoutRes(R.layout.register_show_textview)
                        .setScreenWidthAspect(RegisterActivity.this, 0.8f)
                        .setScreenHeightAspect(RegisterActivity.this, 0.8f)
                        .setOnBindViewListener(new OnBindViewListener() {
                            @Override
                            public void bindView(BindViewHolder viewHolder) {
                                String html = getResources().getString(R.string.bbsq);
                                html = String.format(html, "<br><br>", "<br><br>");
                                viewHolder.setText(R.id.tv, Html.fromHtml(html));
                            }
                        })
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder bindViewHolder, View view, MyDialogFragment myDialogFragment) {
                                myDialogFragment.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.tvPrivacy:
                new MyDialogFragment.Builder(getSupportFragmentManager())
                        .setLayoutRes(R.layout.register_show_textview)
                        .setScreenWidthAspect(RegisterActivity.this, 0.8f)
                        .setScreenHeightAspect(RegisterActivity.this, 0.8f)
                        .setOnBindViewListener(new OnBindViewListener() {
                            @Override
                            public void bindView(BindViewHolder viewHolder) {
                                String html = getResources().getString(R.string.ysbh);
                                Object[] br = new Object[65];
                                for (int i = 0; i < br.length; i++) {
                                    br[i] = "<br><br>";
                                }
                                html = String.format(html, br);
                                viewHolder.setText(R.id.tv, Html.fromHtml(html));
                            }
                        })
                        .setOnViewClickListener(new OnViewClickListener() {
                            @Override
                            public void onViewClick(BindViewHolder bindViewHolder, View view, MyDialogFragment myDialogFragment) {
                                myDialogFragment.dismiss();
                            }
                        }).create().show();
                break;
            case R.id.tvNext:
                if (!cbFileDeclarations.isChecked()) {
                } else if (TextUtils.isEmpty(edName.getText().toString()) || TextUtils.isEmpty(edUserphone.getText().toString()) || TextUtils.isEmpty(edSetPwd.getText().toString())) {
                    MyUtilToast.showShort(this, "信息未填写完整");
                } else if (!Constant.isMobileNO(edUserphone.getText().toString().trim())) {
                    MyUtilToast.showShort(this, "请输入正确的手机号");
                } else if (edSetPwd.getText().toString().trim().length() < 6) {
                    MyUtilToast.showShort(this, "密码长度至少6位");
                } else if (TextUtils.isEmpty(edUserpwd.getText().toString())) {
                    MyUtilToast.showShort(this, "请填写验证码");
                } else if (!VerifeCode.getInstance().getCode().equals(edUserpwd.getText().toString())) {
                    MyUtilToast.showShort(this, "验证码不正确");
                } else {
                    MyViewUtilSVProgressHUB.getInstance().showProgressHUD("注册中...");
                    if (rgWorkTime.getCheckedRadioButtonId() == R.id.confirm1) {
                        presenter.summit(1, edUserphone.getText().toString(), edSetPwd.getText().toString(), edName.getText().toString());
                    } else if (rgWorkTime.getCheckedRadioButtonId() == R.id.refuse1) {
                        presenter.summit(2, edUserphone.getText().toString(), edSetPwd.getText().toString(), edName.getText().toString());
                    }
                }
                break;
            default:
                break;
        }
    }
}
