package com.yxq.myframdome.module.user.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpFragment;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.crazyhuskar.myandroidsdk.util.glide.MyUtilGlide;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.MyDialogFragment;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.base.BindViewHolder;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.listener.OnBindViewListener;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.listener.OnViewClickListener;
import com.yxq.myframdome.AppCache;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.UserVO;
import com.yxq.myframdome.module.user.AboutUsActivity;
import com.yxq.myframdome.module.user.ChangePwdActivity;
import com.yxq.myframdome.module.user.LoginActivity;
import com.yxq.myframdome.module.user.fragment.mvp.MainFragment_3_Contract;
import com.yxq.myframdome.module.user.fragment.mvp.MainFragment_3_PresenterImpl;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author tangjin
 * @Title: {标题}
 * @Description:{空白页}
 * @date 2017/11/9
 */
public class MainFragment_3 extends MyBaseMvpFragment<MainFragment_3_Contract.MainFragment_3_Presenter> implements MainFragment_3_Contract.MainFragment_3_View {

    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.tv_name_t)
    TextView tvNameT;

    @Override
    protected MainFragment_3_Contract.MainFragment_3_Presenter createPresenter() {
        return new MainFragment_3_PresenterImpl();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.fragment_main_3;
    }

    @Override
    protected Fragment registerFragment() {
        return this;
    }

    @Override
    protected void init() {
        UserVO userVO = AppCache.getInstance(mContext).getUserInfo();
        initView(userVO);
    }

    @OnClick({R.id.ll_password, R.id.ll_clean, R.id.btn_logout, R.id.ll_aboutUs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_password:
                MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, ChangePwdActivity.class);
                break;
            case R.id.ll_clean:
                new MyDialogFragment.Builder(getFragmentManager()).setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder bindViewHolder) {
                        bindViewHolder.setText(R.id.dialogfragmen_title, "清除缓存");
                        bindViewHolder.setText(R.id.dialogfragmen_content, "确认清除缓存么");
                    }
                }).addOnClickListener(R.id.dialogfragmen_ok, R.id.dialogfragmen_cancel).setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder bindViewHolder, View view, MyDialogFragment myDialogFragment) {
                        switch (view.getId()) {
                            case R.id.dialogfragmen_cancel:
                                myDialogFragment.dismiss();
                                break;
                            case R.id.dialogfragmen_ok:
                                myDialogFragment.dismiss();
                                MyUtilGlide.cleanCatchDisk();
                                tvCache.setText(MyUtilGlide.getCacheSize());
                                break;
                            default:
                                break;
                        }
                    }
                }).create().show();
                break;
            case R.id.btn_logout:
                new MyDialogFragment.Builder(getFragmentManager()).setOnBindViewListener(new OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder bindViewHolder) {
                        bindViewHolder.setText(R.id.dialogfragmen_title, "确认退出");
                        bindViewHolder.setText(R.id.dialogfragmen_content, "确认退出此账号么");
                    }
                }).addOnClickListener(R.id.dialogfragmen_ok, R.id.dialogfragmen_cancel).setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder bindViewHolder, View view, MyDialogFragment myDialogFragment) {
                        switch (view.getId()) {
                            case R.id.dialogfragmen_cancel:
                                myDialogFragment.dismiss();
                                break;
                            case R.id.dialogfragmen_ok:
                                myDialogFragment.dismiss();
                                AppCache.getInstance(getContext()).saveUserInfo(new UserVO());
                                AppCache.getInstance(getContext()).saveLoginStatus(false);
                                MyUtilActivity.getInstance().finishActivity((AppCompatActivity) getContext());
                                MyUtilActivity.getInstance().startActivity((AppCompatActivity) getContext(), LoginActivity.class);
                                break;
                            default:
                                break;
                        }
                    }
                }).create().show();
                break;
            case R.id.ll_aboutUs:
                Intent intent = new Intent(getContext(), AboutUsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void initView(UserVO userVO) {
        tvUsername.setText(userVO.getUsername());
        switch (userVO.getType()) {
            case 1:
                tvNameT.setText("企业名称");
                break;
            case 2:
                tvNameT.setText("机构名称");
                break;
            case 3:
                tvNameT.setText("政府名称");
                break;
            default:
                break;
        }
        tvName.setText(userVO.getRoleName());
        tvDepartment.setText(userVO.getDepartment());
        tvPhone.setText(userVO.getPhone());
        tvCache.setText(MyUtilGlide.getCacheSize());
    }

}
