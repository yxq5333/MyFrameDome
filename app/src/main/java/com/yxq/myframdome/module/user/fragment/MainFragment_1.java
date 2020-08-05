package com.yxq.myframdome.module.user.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazyhuskar.myandroidsdk.adapter.recyclerview.bean.CommonBean;
import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpFragment;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.crazyhuskar.myandroidsdk.util.MyUtilLog;
import com.crazyhuskar.myandroidsdk.util.MyUtilToast;
import com.crazyhuskar.myandroidsdk.util.bean.EventBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.AppCache;
import com.yxq.myframdome.AppConfig;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.BannerVO;
import com.yxq.myframdome.api_entity.MenuVO;
import com.yxq.myframdome.api_entity.UserDTO;
import com.yxq.myframdome.api_entity.bean.QRCodeData;
import com.yxq.myframdome.api_entity.bean.QRCodeResult;
import com.yxq.myframdome.module.QRCodeScannerActivity;
import com.yxq.myframdome.module.user.adapter.MainFragment_1_Adapter;
import com.yxq.myframdome.module.user.adapter.MainFragment_1_Banner_Entity;
import com.yxq.myframdome.module.user.adapter.MainFragment_1_Entity;
import com.yxq.myframdome.module.user.fragment.mvp.MainFragment_1_Contract;
import com.yxq.myframdome.module.user.fragment.mvp.MainFragment_1_PresenterImpl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yxq.myframdome.AppConfig.EVENTBUS_HIDDENDENGER_IAEDIT;
import static com.yxq.myframdome.AppConfig.SERVICE_ISEDIT;


/**
 * @author tangjin
 * @Title: {标题}
 * @Description:{空白页}
 * @date 2017/11/9
 */
public class MainFragment_1 extends MyBaseMvpFragment<MainFragment_1_Contract.MainFragment_1_Presenter> implements MainFragment_1_Contract.MainFragment_1_View {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<CommonBean<MainFragment_1_Entity>> commonBeanList = new ArrayList<>();
    private MainFragment_1_Adapter mainFragment_1_adapter;

    @Override
    protected MainFragment_1_Contract.MainFragment_1_Presenter createPresenter() {
        return new MainFragment_1_PresenterImpl();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.fragment_main_1;
    }

    @Override
    protected Fragment registerFragment() {
        return this;
    }

    @Override
    protected void init() {
        tvName.setText(AppCache.getInstance(mContext).getUserInfo().getRoleName());
        MainFragment_1_Entity banner = new MainFragment_1_Entity();
        banner.addBanner(new MainFragment_1_Banner_Entity("第一个", R.mipmap.banner1, ""));
        banner.addBanner(new MainFragment_1_Banner_Entity("第二个", R.mipmap.banner2, ""));
        banner.addBanner(new MainFragment_1_Banner_Entity("第三个", R.mipmap.banner3, ""));
        commonBeanList.add(new CommonBean<>(MainFragment_1_Entity.TYPE_1, banner));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        mainFragment_1_adapter = new MainFragment_1_Adapter(mContext, commonBeanList);
        recyclerView.setAdapter(mainFragment_1_adapter);
        presenter.getBanner(AppCache.getInstance(mContext).getUserInfo().getToken(), AppCache.getInstance(mContext).getUserInfo().getType() + "");
    }

    private boolean isShow = false;

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetStickyEvent(EventBean eventBean) {
        if (eventBean.getFlag() == AppConfig.EVENTBUS_MENU && !isShow) {
            isShow = true;
            updatamenu(MyUtilJson.parseList(eventBean.getMsg(), MenuVO.class));
        }
    }

    private void updataBanner(MainFragment_1_Entity fragment1Entity) {
        commonBeanList.set(0, new CommonBean<>(MainFragment_1_Entity.TYPE_1, fragment1Entity));
        mainFragment_1_adapter.refreshData(commonBeanList);
    }

    private void updatamenu(List<MenuVO> menuVOList) {
        for (int i = 0; i < menuVOList.size(); i++) {
            switch (menuVOList.get(i).getMenuId()) {
                case 1:
                case 2:
                case 3:
                case 8:
                case 9:
                case 10:
                    MainFragment_1_Entity mainFragment_1_entity = new MainFragment_1_Entity();
                    mainFragment_1_entity.setMenuVO(menuVOList.get(i));
                    if (commonBeanList.size() > i + 1) {
                        commonBeanList.set(i + 1, new CommonBean<>(MainFragment_1_Entity.TYPE_2, mainFragment_1_entity));
                    } else {
                        commonBeanList.add(new CommonBean<>(MainFragment_1_Entity.TYPE_2, mainFragment_1_entity));
                    }
                    break;
                default:
                    break;
            }
        }
        mainFragment_1_adapter.refreshData(commonBeanList);
    }

    @Override
    public void getBannerSuccess(List<BannerVO> bannerVOList) {
        if (bannerVOList.size() > 0) {
            MainFragment_1_Entity banner = new MainFragment_1_Entity();
            for (BannerVO bannerVO : bannerVOList) {
                banner.addBanner(new MainFragment_1_Banner_Entity(bannerVO.getFileTitle(), APIConfig.BASEURL + APIConfig.FILEDOWN + bannerVO.getFile().getName(), bannerVO.getLinkUrl()));
            }
            updataBanner(banner);
        }
    }

    @Override
    public void faliure(String msg) {
        MyUtilToast.showShort(mContext, msg);
    }

    @OnClick({R.id.iv_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:
                doScan();
                break;
            default:
                break;
        }
    }

    private void doScan() {

        String[] result = {
                Manifest.permission.CAMERA
        };

        if (ActivityCompat.checkSelfPermission(getActivity(), result[0]) != PackageManager.PERMISSION_GRANTED) {
            // 在这里面向系统请求权限,如果没有在这里面处理,不会执行下面的方法了.
            // 这里就是向系统请求权限了,这里我还做了一个判断.
            // SDK是M(M = 23 android L)才做这个请求,否则就不做.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(result, 3366);
            }
        } else {
            MyUtilActivity.getInstance().startActivity((AppCompatActivity) getActivity(), QRCodeScannerActivity.class);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case 3366:
                    doScan();
                    break;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private QRCodeData qrCodeData;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventUI(QRCodeResult qrCodeResult) {

        String result = qrCodeResult.getResult();

        if (result.contains("?")) {
            result = result.substring(result.indexOf("?") + 1);
            result = result.replaceAll("&", ",\"");
            result = result.replaceAll("=", "\":");
            result = "{\"" + result + "}";

            MyUtilLog.e(result);
            qrCodeData = new Gson().fromJson(result, QRCodeData.class);

            MyUtilToast.showShort(getContext(), "权限验证中，请稍候……");
            checkRole();
        } else {
            MyUtilToast.showShort(getContext(), "请重新扫描");
        }
    }

    private void checkRole() {

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", AppCache.getInstance(getContext()).getUserInfo().getToken());

        Map<String, String> param = new HashMap<>();
        param.put("contractId", qrCodeData.getContractId() + "");
        param.put("riskId", qrCodeData.getRiskId() + "");

        UserDTO userDTO = new UserDTO();
        userDTO.url(APIConfig.CHECK_ROLE);
        userDTO.headers(header);
        userDTO.params(param);
        userDTO.checkRole(new MyCallback<String>() {
            @Override
            public void onSuccess(String data) {

                Map<String, Integer> temp = new Gson().fromJson(data, new TypeToken<Map<String, Integer>>() {
                }.getType());

                if (temp != null) {
                    Integer canService = temp.get("canService");
                    Integer riskCheckId = temp.get("riskCheckId");

                    qrCodeData.setRiskCheckId(riskCheckId == null ? 0 : riskCheckId);
                    showQRDialog(canService != null && canService == 1);
                }
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

    private void showQRDialog(boolean canCheck) {

        String[] items = {
                "查看企业基本信息",
                canCheck ? "检查风险点" : "查看风险点",
        };

        new AlertDialog.Builder(getContext())
                .setTitle("请选择以下操作：")
                .setItems(items, (dialog12, which) -> {
                    switch (which) {
                        case 0:
                            Bundle data = new Bundle();
                            data.putInt(AppConfig.SERVICE_TIMEID, qrCodeData.getCompanyId());
                            data.putBoolean(SERVICE_ISEDIT, false);
//                            Intent intent = new Intent(getContext(), CompanyDetailActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.putExtras(data);
//                            startActivity(intent);
                            break;
                        case 1:
                            if (qrCodeData.getRiskCheckId() == 0) {
                                MyUtilToast.showShort(getContext(), "请先提交\"风险辨识管控\"，并等待通过审核");
                            } else {
//                                Intent intent2 = new Intent(getContext(), RiskPointListActivity.class);
//                                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent2.putExtra(AppConfig.ID, qrCodeData.getRiskCheckId());
//                                intent2.putExtra(AppConfig.SERVICE_ID, Integer.valueOf(qrCodeData.getServiceId()));
//                                intent2.putExtra(EVENTBUS_HIDDENDENGER_IAEDIT, canCheck);
//                                startActivity(intent2);
                            }
                            break;
                    }
                })
                .setNegativeButton("取消", (dialog1, which) -> {
                }).show();
    }

}
