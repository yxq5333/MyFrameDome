package com.yxq.myframdome.module.user.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.crazyhuskar.myandroidsdk.adapter.recyclerview.base.ItemViewDelegate;
import com.crazyhuskar.myandroidsdk.adapter.recyclerview.base.ViewHolder;
import com.crazyhuskar.myandroidsdk.adapter.recyclerview.bean.CommonBean;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.crazyhuskar.myandroidsdk.util.glide.MyUtilGlide;
import com.yxq.myframdome.R;
import com.yxq.myframdome.module.user.WebActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-13  9:30
 */
public class MainFragment_1_Adapter_1 implements ItemViewDelegate<CommonBean<MainFragment_1_Entity>> {
    private Context context;

    public MainFragment_1_Adapter_1(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_main_1_fragment_1;
    }

    @Override
    public boolean isForViewType(CommonBean<MainFragment_1_Entity> item, int position) {
        return item.itemType == MainFragment_1_Entity.TYPE_1;
    }

    @Override
    public void convert(ViewHolder holder, final CommonBean<MainFragment_1_Entity> item, int position) {
        List<Object> src = new ArrayList<>();
        List<String> title = new ArrayList<>();
        for (MainFragment_1_Banner_Entity banner_entity : item.data.getBannerEntityList()) {
            src.add(banner_entity.getSrc());
            title.add(banner_entity.getTitle());
        }
        final Banner banner = holder.getView(R.id.banner);
//        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                MyUtilGlide.load(context, imageView, path);
            }
        });
        banner.update(src);
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (!TextUtils.isEmpty(item.data.getBannerEntityList().get(position).getLinkurl())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("bean", MyUtilJson.toJSONString(item.data.getBannerEntityList().get(position)));
                    MyUtilActivity.getInstance().startActivity((AppCompatActivity) context,WebActivity.class,bundle);
                }
            }
        });
        banner.isAutoPlay(true);
        banner.setDelayTime(5000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
    }
}
