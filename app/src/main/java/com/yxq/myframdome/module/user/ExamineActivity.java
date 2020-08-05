package com.yxq.myframdome.module.user;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.crazyhuskar.myandroidsdk.util.MyUtilToast;
import com.crazyhuskar.myandroidsdk.view.util.MyViewUtilSVProgressHUB;
import com.google.gson.Gson;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.AppCache;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.BaseJson;
import com.yxq.myframdome.api_entity.ExamineInfoVO;
import com.yxq.myframdome.api_entity.FileVO;
import com.yxq.myframdome.api_entity.UserVO;
import com.yxq.myframdome.api_entity.address.AreaBean;
import com.yxq.myframdome.api_entity.address.CityBean;
import com.yxq.myframdome.api_entity.address.ProvinceBean;
import com.yxq.myframdome.api_entity.address.StreetBean;
import com.yxq.myframdome.module.user.mvp.ExamineContract;
import com.yxq.myframdome.module.user.mvp.ExaminePresenterImpl;
import com.yxq.myframdome.util.AddressSelector;
import com.yxq.myframdome.util.OnAddressSelectedListener;
import com.yxq.myframdome.util.dialog.BottomDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vincent.filepicker.activity.ImagePickActivity.IS_NEED_CAMERA;
import static com.vincent.filepicker.activity.ImagePickActivity.IS_TAKEN_AUTO_SELECTED;

/**
 * 注册信息
 * @author CrazyHuskar
 * caeat at 2018-12-10  14:01
 */
public class ExamineActivity extends MyBaseMvpActivity<ExamineContract.ExaminePresenter> implements ExamineContract.ExamineView {

    @BindView(R.id.tv_qiyemingcheng_t)
    TextView tvQiyemingchengT;
    @BindView(R.id.tv_1)
    EditText tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    EditText tv3;
    @BindView(R.id.tv_4)
    EditText tv4;
    @BindView(R.id.tv_5)
    EditText tv5;
    @BindView(R.id.tv_6)
    EditText tv6;
    @BindView(R.id.tv_7)
    EditText tv7;
    @BindView(R.id.tv_8)
    EditText tv8;
    @BindView(R.id.tv_9)
    EditText tv9;
    @BindView(R.id.tv_10)
    EditText tv10;
    @BindView(R.id.tv_11)
    EditText tv11;
    @BindView(R.id.tv_12)
    EditText tv12;
    @BindView(R.id.tv_13)
    EditText tv13;
    @BindView(R.id.tv_14)
    EditText tv14;
    @BindView(R.id.tv_15)
    EditText tv15;
    @BindView(R.id.tv_16)
    EditText tv16;
    @BindView(R.id.tv_17)
    EditText tv17;
    @BindView(R.id.tv_18)
    EditText tv18;
    @BindView(R.id.tv_status)
    TextView status;
    @BindView(R.id.btn_commit)
    Button commit;
    @BindView(R.id.image_yy)
    ImageView takePhoto;
    @BindView(R.id.ll_address)
    LinearLayout addressLayout;

    private String imgPath = "";

    private ExamineInfoVO examineInfoVO = new ExamineInfoVO();
    private BottomDialog dialog;
    private String text = "";

    @Override
    protected ExamineContract.ExaminePresenter createPresenter() {
        return new ExaminePresenterImpl();
    }

    @Override
    public int setLayoutID() {
        return R.layout.activity_examine;
    }

    @Override
    public AppCompatActivity registerActivity() {
        return this;
    }

    @Override
    public void init() {

        setCommonTitle("");
        UserVO userVO = AppCache.getInstance(this).getUserInfo();
        tv1.setText(userVO.getDepartment());
        StringBuilder str = new StringBuilder("状态：");
        switch (userVO.getStatus()) {
            case 1:
                str.append("待提交");
                break;
            case 3:
                str.append("待审核");
                setDisableView();
                break;
            case 2:
                str.append("审核不通过");
                str.append("\n");
                str.append("驳回理由：");
                if (!TextUtils.isEmpty(userVO.getRejectReason())) {
                    str.append(userVO.getRejectReason());
                }
                break;
        }
        status.setText(str.toString());

        switch (userVO.getType()) {
            case 1:
                tvQiyemingchengT.setText("企业名称");
                setCommonTitle("退出", "完善企业信息", null, null, null, null, null);
                break;
            case 2:
                tvQiyemingchengT.setText("机构名称");
                setCommonTitle("退出", "完善机构信息", null, null, null, null, null);
                break;
            default:
                MyUtilActivity.getInstance().finishActivity(this);
                break;
        }
        commonTitle.setCommonTitleLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCache.getInstance(ExamineActivity.this).saveLoginStatus(false);
                MyUtilActivity.getInstance().finishActivity(ExamineActivity.this);
            }
        });
        tv3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(tv4.getText().toString()) || text.equals(tv4.getText().toString())) {
                    text = tv2.getText().toString() + tv3.getText().toString();
                    tv4.setText(text);
                }
            }
        });
        MyViewUtilSVProgressHUB.getInstance().init(this);

        presenter.getData(AppCache.getInstance(this).getUserInfo().getToken(), AppCache.getInstance(this).getUserInfo().getType());
    }

    @OnClick({R.id.imageView_yy, R.id.image_yy, R.id.btn_commit, R.id.ll_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_yy:
                if (TextUtils.isEmpty(imgPath)) {
                    MyUtilToast.showShort(this, "还未选择营业执照");
                } else {
                    com.yxq.myframdome.Constant.seeFileByPath(this, imgPath);
                }
                break;
            case R.id.image_yy:
                Intent intent1 = new Intent(this, ImagePickActivity.class);
                intent1.putExtra(IS_NEED_CAMERA, true);
                intent1.putExtra(IS_TAKEN_AUTO_SELECTED, false);

                intent1.putExtra(Constant.MAX_NUMBER, 1);
                startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);
                break;
            case R.id.btn_commit:
                setExamineInfoVO();
                break;
            case R.id.ll_address:
                showdialog();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    for (ImageFile image : list) {
                        imgPath = image.getPath();
                    }
                    presenter.uploadFiles(AppCache.getInstance(this).getUserInfo().getToken(), new File(imgPath));
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void uploadSuccess(FileVO fileVO) {
        MyUtilToast.showShort(this, "上传成功");
        imgPath = fileVO.getName();
        examineInfoVO.setBusinessLicence(MyUtilJson.toJSONString(fileVO));
    }

    @Override
    public void uploadFailure(String msg) {
        MyUtilToast.showShort(this, "图片获取失败，请重新选择");
    }

    @Override
    public void updateSuccess(String mag) {
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
        MyUtilToast.showShort(this, "保存成功，请等待审核");
        status.setText("状态：审核中");
        setDisableView();
    }

    @Override
    public void updateFailure(String msg) {
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
        MyUtilToast.showShort(this, "保存失败，请重试");
    }

    @Override
    public void getSuccess(ExamineInfoVO examineInfoVO) {

        if (examineInfoVO != null) {
            this.examineInfoVO = examineInfoVO;

            // 企业名称
            tv1.setText(this.examineInfoVO.getName());
            // 注册地址
            tv2.setText("");
            // 详细地址
            tv3.setText(this.examineInfoVO.getAddress());
            // 实际生产地址
            tv4.setText(this.examineInfoVO.getAddressProduct());
            // 统一社会信用代码
            tv5.setText(this.examineInfoVO.getCreditCode());
            // 经营范围
            tv6.setText(this.examineInfoVO.getBusinessScope());

            // 法人联系方式
            tv7.setText(this.examineInfoVO.getCorporationName());
            tv8.setText(this.examineInfoVO.getCorporationMobile());
            tv9.setText(this.examineInfoVO.getCorporationEmail());
            tv10.setText(this.examineInfoVO.getCorporationPhone());
            tv11.setText(this.examineInfoVO.getCorporationFax());
            tv12.setText(this.examineInfoVO.getCorporationQq());

            // 主要联系人联系方式
            tv13.setText(this.examineInfoVO.getContactsName());
            tv14.setText(this.examineInfoVO.getContactsMobile());
            tv15.setText(this.examineInfoVO.getContactsEmail());
            tv16.setText(this.examineInfoVO.getContactsPhone());
            tv17.setText(this.examineInfoVO.getContactsFax());
            tv18.setText(this.examineInfoVO.getContactsQq());

            // 附件
            if (this.examineInfoVO.getBusinessLicenceFile() != null) {
                imgPath = this.examineInfoVO.getBusinessLicenceFile().getName();
            }

            if (this.examineInfoVO.getProvinceId() != null) {
                retrieveProvinces(0);
            }
        }
    }

    @Override
    public void getFailure(String msg) {

    }

    private void setExamineInfoVO() {
        if (TextUtils.isEmpty(tv1.getText().toString())) {
            MyUtilToast.showShort(this, "请输入名称");
        } else if (examineInfoVO.getStreetId() == null) {
            MyUtilToast.showShort(this, "请选择注册地址");
        } else if (TextUtils.isEmpty(tv4.getText().toString())) {
            MyUtilToast.showShort(this, "请填写实际经营地址");
        } else if (TextUtils.isEmpty(examineInfoVO.getBusinessLicence())) {
            MyUtilToast.showShort(this, "请选择上传营业执照");
        } else if (TextUtils.isEmpty(tv7.getText().toString())) {
            MyUtilToast.showShort(this, "请填写法人代表");
        } else if (TextUtils.isEmpty(tv8.getText().toString())) {
            MyUtilToast.showShort(this, "请填写法人代表手机");
        } else if (TextUtils.isEmpty(tv13.getText().toString())) {
            MyUtilToast.showShort(this, "请填写主要联系人");
        } else if (TextUtils.isEmpty(tv14.getText().toString())) {
            MyUtilToast.showShort(this, "请填写主要联系人手机");
        } else {
            examineInfoVO.setName(tv1.getText().toString());
            examineInfoVO.setAddress(tv3.getText().toString());
            examineInfoVO.setAddressProduct(tv4.getText().toString());
            examineInfoVO.setCreditCode(tv5.getText().toString());
            examineInfoVO.setBusinessScope(tv6.getText().toString());
            examineInfoVO.setCorporationName(tv7.getText().toString());
            examineInfoVO.setCorporationPhone(tv10.getText().toString());
            examineInfoVO.setCorporationEmail(tv9.getText().toString());
            examineInfoVO.setCorporationMobile(tv8.getText().toString());
            examineInfoVO.setCorporationFax(tv11.getText().toString());
            examineInfoVO.setCorporationQq(tv12.getText().toString());
            examineInfoVO.setContactsName(tv13.getText().toString());
            examineInfoVO.setContactsPhone(tv16.getText().toString());
            examineInfoVO.setContactsEmail(tv15.getText().toString());
            examineInfoVO.setContactsMobile(tv14.getText().toString());
            examineInfoVO.setContactsFax(tv17.getText().toString());
            examineInfoVO.setContactsQq(tv18.getText().toString());
            if (!TextUtils.isEmpty(examineInfoVO.getBusinessLicence())) {
                examineInfoVO.setBusinessLicenceFile(new Gson().fromJson(examineInfoVO.getBusinessLicence(), FileVO.class));
            }

            MyViewUtilSVProgressHUB.getInstance().showProgressHUD("保存中...");
            presenter.update(AppCache.getInstance(this).getUserInfo().getType(), AppCache.getInstance(this).getUserInfo().getToken(), examineInfoVO);
        }
    }

    private void showdialog() {
        if (dialog != null) {
            dialog.show();
        } else {
            dialog = new BottomDialog(this);
            dialog.setOnAddressSelectedListener(new OnAddressSelectedListener() {
                @Override
                public void onAddressSelected(ProvinceBean province, CityBean city, AreaBean county, StreetBean street) {
                    String provinceCode = (province == null ? "" : province.id + "");
                    String cityCode = (city == null ? "" : city.id + "");
                    String countyCode = (county == null ? "" : county.id + "");
                    String streetCode = (street == null ? "" : street.id + "");
                    examineInfoVO.setProvinceId(Integer.valueOf(provinceCode));
                    examineInfoVO.setCityId(Integer.valueOf(cityCode));
                    examineInfoVO.setAreaId(Integer.valueOf(countyCode));
                    examineInfoVO.setStreetId(Integer.valueOf(streetCode));
                    String s = (province == null ? "" : province.name) + (city == null ? "" : city.name) + (county == null ? "" : county.name) +
                            (street == null ? "" : street.name);
                    tv2.setText(s);
                    if (TextUtils.isEmpty(tv4.getText().toString()) || text.equals(tv4.getText().toString())) {
                        text = tv2.getText().toString() + tv3.getText().toString();
                        tv4.setText(text);
                    }
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });
            dialog.setDialogDismisListener(new AddressSelector.OnDialogCloseListener() {
                @Override
                public void dialogclose() {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });
            dialog.setTextSize(14);//设置字体的大小
            dialog.setIndicatorBackgroundColor(android.R.color.holo_orange_light);//设置指示器的颜色
            dialog.setTextSelectedColor(android.R.color.holo_orange_light);//设置字体获得焦点的颜色
            dialog.setTextUnSelectedColor(android.R.color.holo_blue_light);//设置字体没有获得焦点的颜色
            dialog.show();
        }
    }

    private ProvinceBean provinceBean;
    private CityBean cityBean;
    private AreaBean areaBean;
    private StreetBean streetBean;

    /**
     * 查询省份列表
     */
    private void retrieveProvinces(int parentId) {

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", AppCache.getInstance(this).getUserInfo().getToken());

        MyRetrofitUtil.getInstance().executeGet(header, (APIConfig.LISTBYPARENTID + parentId), new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    List<ProvinceBean> data = MyUtilJson.parseList(MyUtilJson.toJSONString(json.getData()), ProvinceBean.class);
                    for (ProvinceBean t : data) {
                        if (t.id == examineInfoVO.getProvinceId()) {
                            provinceBean = t;
                            retrieveCitiesWith();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(String s) {
            }

            @Override
            public void onError() {
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void retrieveCitiesWith() {

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", AppCache.getInstance(this).getUserInfo().getToken());

        MyRetrofitUtil.getInstance().executeGet(header, (APIConfig.LISTBYPARENTID + provinceBean.id), new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    List<CityBean> data = MyUtilJson.parseList(MyUtilJson.toJSONString(json.getData()), CityBean.class);
                    for (CityBean t : data) {
                        if (t.id == examineInfoVO.getCityId()) {
                            cityBean = t;
                            retrieveAreasWith();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(String s) {
            }

            @Override
            public void onError() {
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void retrieveAreasWith() {

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", AppCache.getInstance(this).getUserInfo().getToken());

        MyRetrofitUtil.getInstance().executeGet(header, (APIConfig.LISTBYPARENTID + cityBean.id), new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    List<AreaBean> data = MyUtilJson.parseList(MyUtilJson.toJSONString(json.getData()), AreaBean.class);
                    for (AreaBean t : data) {
                        if (t.id == examineInfoVO.getAreaId()) {
                            areaBean = t;
                            retrieveStreetsWith();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(String s) {
            }

            @Override
            public void onError() {
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void retrieveStreetsWith() {

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", AppCache.getInstance(this).getUserInfo().getToken());

        MyRetrofitUtil.getInstance().executeGet(header, (APIConfig.LISTBYPARENTID + areaBean.id), new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    List<StreetBean> data = MyUtilJson.parseList(MyUtilJson.toJSONString(json.getData()), StreetBean.class);
                    for (StreetBean t : data) {
                        if (t.id == examineInfoVO.getStreetId()) {
                            streetBean = t;
                            setAddressData();
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(String s) {
            }

            @Override
            public void onError() {
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void setAddressData() {
        // 注册地址
        tv2.setText("");
        tv2.append(provinceBean.name);
        tv2.append(cityBean.name);
        tv2.append(areaBean.name);
        tv2.append(streetBean.name);
    }

    private void setDisableView() {

        tv1.setEnabled(false);
        tv2.setEnabled(false);
        tv3.setEnabled(false);
        tv4.setEnabled(false);
        tv5.setEnabled(false);
        tv6.setEnabled(false);
        tv7.setEnabled(false);
        tv8.setEnabled(false);
        tv9.setEnabled(false);
        tv10.setEnabled(false);
        tv11.setEnabled(false);
        tv12.setEnabled(false);
        tv13.setEnabled(false);
        tv14.setEnabled(false);
        tv15.setEnabled(false);
        tv16.setEnabled(false);
        tv17.setEnabled(false);
        tv18.setEnabled(false);
        addressLayout.setEnabled(false);

        takePhoto.setVisibility(View.GONE);
        commit.setVisibility(View.GONE);
    }

}
