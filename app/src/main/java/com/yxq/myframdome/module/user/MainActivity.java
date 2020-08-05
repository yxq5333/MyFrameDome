package com.yxq.myframdome.module.user;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.Fragment;

import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilFragment;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.crazyhuskar.myandroidsdk.util.MyUtilToast;
import com.crazyhuskar.myandroidsdk.util.bean.EventBean;
import com.crazyhuskar.myandroidsdk.view.util.MyViewUtilSVProgressHUB;
import com.yxq.myframdome.AppCache;
import com.yxq.myframdome.AppConfig;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.MenuVO;
import com.yxq.myframdome.api_entity.UserVO;
import com.yxq.myframdome.module.user.fragment.MainFragment_1;
import com.yxq.myframdome.module.user.fragment.MainFragment_2;
import com.yxq.myframdome.module.user.fragment.MainFragment_3;
import com.yxq.myframdome.module.user.mvp.MainContract;
import com.yxq.myframdome.module.user.mvp.MainPresenterImpl;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 */
public class MainActivity extends MyBaseMvpActivity<MainContract.MainPresenter> implements MainContract.MainView {

    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.group)
    RadioGroup group;
    @BindView(R.id.rb_1)
    AppCompatRadioButton rb1;
    @BindView(R.id.rb_2)
    AppCompatRadioButton rb2;
    @BindView(R.id.rb_3)
    AppCompatRadioButton rb3;

    private List<Fragment> menuList;
    private MyUtilFragment fragmentUtil;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public int setLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public AppCompatActivity registerActivity() {
        return this;
    }

    @Override
    public void init() {

        MyViewUtilSVProgressHUB.getInstance().init(this);
        MyViewUtilSVProgressHUB.getInstance().showProgressHUD("获取菜单中...");
        presenter.getMenu(AppCache.getInstance(this).getUserInfo().getToken());
        presenter.getUserInfo(AppCache.getInstance(this).getUserInfo().getToken());
    }

    @Override
    protected MainContract.MainPresenter createPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    public void onBackPressed() {
        MyUtilActivity.getInstance().outOfApplication(this);
    }

    @Override
    public void creatMenu(final List<MenuVO> menuVOList) {

        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();

        if (menuVOList.size() == 0) {
            MyUtilToast.showShort(this, "请通知管理员分配菜单");
        }


        menuList = new ArrayList<>();
        switch (AppCache.getInstance(this).getUserInfo().getType()) {
            case 1:
            case 3:
                rb2.setVisibility(View.GONE);
                break;
            case 2:
                break;
            default:
                MyUtilActivity.getInstance().finishActivity(this);
                break;
        }
        group.setVisibility(View.VISIBLE);
        menuList.add(new MainFragment_1());
        menuList.add(new MainFragment_2());
        menuList.add(new MainFragment_3());
        fragmentUtil = new MyUtilFragment(getSupportFragmentManager(), R.id.main_content, menuList);
        fragmentUtil.show(0);
        rb1.setChecked(true);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_1:
                        fragmentUtil.show(0);
                        EventBus.getDefault().post(new EventBean(MyUtilJson.toJSONString(menuVOList), AppConfig.EVENTBUS_MENU));
                        break;
                    case R.id.rb_2:

                        fragmentUtil.show(1);
                        break;
                    case R.id.rb_3:
                        fragmentUtil.show(2);
                        break;
                    default:
                        break;
                }
            }
        });
        EventBus.getDefault().postSticky(new EventBean(MyUtilJson.toJSONString(menuVOList), AppConfig.EVENTBUS_MENU));
    }

    @Override
    public void getUserInfo(UserVO userVO_t) {
        UserVO userVO = AppCache.getInstance(this).getUserInfo();
        userVO.setPhone(userVO_t.getPhone());
        userVO.setName(userVO_t.getName());
        userVO.setDepartment(userVO_t.getDepartment());
        userVO.setRoleName(userVO_t.getRoleName());
        AppCache.getInstance(this).saveUserInfo(userVO);
    }

    @Override
    public void examine() {
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
        MyUtilActivity.getInstance().finishActivity(this);
        MyUtilActivity.getInstance().startActivity(this, ExamineActivity.class);
    }

    @Override
    public void expired() {
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
        MyUtilActivity.getInstance().finishActivity(this);
        MyUtilActivity.getInstance().startActivity(this, LoginActivity.class);
        MyUtilToast.showShort(this, "登录过期");
    }

    @Override
    public void getMenuError() {
        MyViewUtilSVProgressHUB.getInstance().dismissProgressHUD();
        MyUtilToast.showShort(this, "请通知管理员分配菜单");
    }
}
