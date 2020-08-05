package com.yxq.myframdome.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crazyhuskar.myandroidsdk.api.MyCallback;
import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.util.MyUtilJson;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.AppCache;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.BaseJson;
import com.yxq.myframdome.api_entity.address.AreaBean;
import com.yxq.myframdome.api_entity.address.CityBean;
import com.yxq.myframdome.api_entity.address.ProvinceBean;
import com.yxq.myframdome.api_entity.address.StreetBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;


/**
 * Created by smartTop on 2016/10/19.
 */

public class AddressSelector implements AdapterView.OnItemClickListener {
    private static final int INDEX_TAB_PROVINCE = 0;//省份标志
    private static final int INDEX_TAB_CITY = 1;//城市标志
    private static final int INDEX_TAB_COUNTY = 2;//乡镇标志
    private static final int INDEX_TAB_STREET = 3;//街道标志
    private int tabIndex = INDEX_TAB_PROVINCE; //默认是省份

    private static final int INDEX_INVALID = -1;
    private int provinceIndex = INDEX_INVALID; //省份的下标
    private int cityIndex = INDEX_INVALID;//城市的下标
    private int countyIndex = INDEX_INVALID;//乡镇的下标
    private int streetIndex = INDEX_INVALID;//街道的下标

    private Context context;
    private final LayoutInflater inflater;
    private View view;

    private View indicator;

    private LinearLayout layout_tab;
    private TextView textViewProvince;
    private TextView textViewCity;
    private TextView textViewCounty;
    private TextView textViewStreet;

    private ProgressBar progressBar;

    private ListView listView;
    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private CountyAdapter countyAdapter;
    private StreetAdapter streetAdapter;
    private List<ProvinceBean> provinces;
    private List<CityBean> cities;
    private List<AreaBean> counties;
    private List<StreetBean> streets;
    private OnAddressSelectedListener listener;
    private OnDialogCloseListener dialogCloseListener;
    private onSelectorAreaPositionListener selectorAreaPositionListener;

    private static final int WHAT_PROVINCES_PROVIDED = 0;
    private static final int WHAT_CITIES_PROVIDED = 1;
    private static final int WHAT_COUNTIES_PROVIDED = 2;
    private static final int WHAT_STREETS_PROVIDED = 3;
//    private AddressDictManager addressDictManager;
    private ImageView iv_colse;
    private int selectedColor;
    private int unSelectedColor;
    public int provincePostion;
    public int cityPosition;
    public int countyPosition;
    public int streetPosition;
    @SuppressWarnings("unchecked")
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_PROVINCES_PROVIDED: //更新省份列表
                    provinces = (List<ProvinceBean>) msg.obj;
                    provinceAdapter.notifyDataSetChanged();
                    listView.setAdapter(provinceAdapter);

                    break;

                case WHAT_CITIES_PROVIDED: //更新城市列表
                    cities = (List<CityBean>) msg.obj;
                    cityAdapter.notifyDataSetChanged();
                    if (Lists.notEmpty(cities)) {
                        // 以次级内容更新列表
                        listView.setAdapter(cityAdapter);
                        // 更新索引为次级
                        tabIndex = INDEX_TAB_CITY;
                    } else {
                        // 次级无内容，回调
                        callbackInternal();
                    }

                    break;

                case WHAT_COUNTIES_PROVIDED://更新乡镇列表
                    counties = (List<AreaBean>) msg.obj;
                    countyAdapter.notifyDataSetChanged();
                    if (Lists.notEmpty(counties)) {
                        listView.setAdapter(countyAdapter);
                        tabIndex = INDEX_TAB_COUNTY;
                    } else {
                        callbackInternal();
                    }

                    break;

                case WHAT_STREETS_PROVIDED://更新街道列表
                    streets = (List<StreetBean>) msg.obj;
                    streetAdapter.notifyDataSetChanged();
                    if (Lists.notEmpty(streets)) {
                        listView.setAdapter(streetAdapter);
                        tabIndex = INDEX_TAB_STREET;
                    } else {
                        callbackInternal();
                    }

                    break;
            }

            updateTabsVisibility();
            updateProgressVisibility();
            updateIndicator();

            return true;
        }
    });


    public AddressSelector(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
//        addressDictManager = new AddressDictManager(context);
        initViews();
        initAdapters();
        retrieveProvinces(0);
    }

    /**
     * 得到数据库管理者
     * @return
     */
//    public AddressDictManager getAddressDictManager(){
//        return addressDictManager;
//    }
    /**
     * 初始化布局
     */
    private void initViews() {
        view = inflater.inflate(R.layout.address_selector, null);
        this.progressBar =  view.findViewById(R.id.progressBar);//进度条
        this.iv_colse = view.findViewById(R.id.iv_colse);
        this.listView =  view.findViewById(R.id.listView);//listview
        this.indicator = view.findViewById(R.id.indicator); //指示器
        this.layout_tab =  view.findViewById(R.id.layout_tab);
        this.textViewProvince =  view.findViewById(R.id.textViewProvince);//省份
        this.textViewCity =  view.findViewById(R.id.textViewCity);//城市
        this.textViewCounty =  view.findViewById(R.id.textViewCounty);//区 乡镇
        this.textViewStreet =  view.findViewById(R.id.textViewStreet);//街道

        this.textViewProvince.setOnClickListener(new OnProvinceTabClickListener());
        this.textViewCity.setOnClickListener(new OnCityTabClickListener());
        this.textViewCounty.setOnClickListener(new onCountyTabClickListener());
        this.textViewStreet.setOnClickListener(new OnStreetTabClickListener());

        this.listView.setOnItemClickListener(this);
        this.iv_colse.setOnClickListener(new onCloseClickListener());
        updateIndicator();
    }

    /**
     *设置字体选中的颜色
     */
     public void setTextSelectedColor(int selectedColor){
            this.selectedColor = selectedColor;
     }

    /**
     *设置字体没有选中的颜色
     */
    public void setTextUnSelectedColor(int unSelectedColor){
            this.unSelectedColor = unSelectedColor;
    }
    /**
     * 设置字体的大小
     */
   public void setTextSize(float dp){
       textViewProvince.setTextSize(dp);
       textViewCity.setTextSize(dp);
       textViewCounty.setTextSize(dp);
       textViewStreet.setTextSize(dp);
   }

    /**
     * 设置字体的背景
     */
    public void setBackgroundColor(int colorId){
        layout_tab.setBackgroundColor(context.getResources().getColor(colorId));
    }

    /**
     * 设置指示器的背景
     */
    public void setIndicatorBackgroundColor(int colorId){
        indicator.setBackgroundColor(context.getResources().getColor(colorId));
    }
    /**
     * 设置指示器的背景
     */
    public void setIndicatorBackgroundColor(String color){
        indicator.setBackgroundColor(Color.parseColor(color));
    }
    /**
     * 初始化adapter
     */
    private void initAdapters() {
        provinceAdapter = new ProvinceAdapter();
        cityAdapter = new CityAdapter();
        countyAdapter = new CountyAdapter();
        streetAdapter = new StreetAdapter();
    }

    /**
     * 更新tab 指示器
     */
    private void updateIndicator() {
        view.post(new Runnable() {
            @Override
            public void run() {
                switch (tabIndex) {
                    case INDEX_TAB_PROVINCE: //省份
                        buildIndicatorAnimatorTowards(textViewProvince).start();
                        break;
                    case INDEX_TAB_CITY: //城市
                        buildIndicatorAnimatorTowards(textViewCity).start();
                        break;
                    case INDEX_TAB_COUNTY: //乡镇
                        buildIndicatorAnimatorTowards(textViewCounty).start();
                        break;
                    case INDEX_TAB_STREET: //街道
                        buildIndicatorAnimatorTowards(textViewStreet).start();
                        break;
                }
            }
        });
    }

    /**
     * tab 来回切换的动画
     *
     * @param tab
     * @return
     */
    private AnimatorSet buildIndicatorAnimatorTowards(TextView tab) {
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(indicator, "X", indicator.getX(), tab.getX());

        final ViewGroup.LayoutParams params = indicator.getLayoutParams();
        ValueAnimator widthAnimator = ValueAnimator.ofInt(params.width, tab.getMeasuredWidth());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (int) animation.getAnimatedValue();
                indicator.setLayoutParams(params);
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.playTogether(xAnimator, widthAnimator);

        return set;
    }

    /**
     * 更新tab显示
     */
    private void updateTabsVisibility() {
        textViewProvince.setVisibility(Lists.notEmpty(provinces) ? View.VISIBLE : View.GONE);
        textViewCity.setVisibility(Lists.notEmpty(cities) ? View.VISIBLE : View.GONE);
        textViewCounty.setVisibility(Lists.notEmpty(counties) ? View.VISIBLE : View.GONE);
        textViewStreet.setVisibility(Lists.notEmpty(streets) ? View.VISIBLE : View.GONE);
        //按钮能不能点击 false 不能点击 true 能点击
        textViewProvince.setEnabled(tabIndex != INDEX_TAB_PROVINCE);
        textViewCity.setEnabled(tabIndex != INDEX_TAB_CITY);
        textViewCounty.setEnabled(tabIndex != INDEX_TAB_COUNTY);
        textViewStreet.setEnabled(tabIndex != INDEX_TAB_STREET);
        if(selectedColor!=0 && unSelectedColor!=0){
            updateTabTextColor();
        }
    }

    /**
     * 更新字体的颜色
     */
    private void updateTabTextColor(){
    if(tabIndex != INDEX_TAB_PROVINCE){
        textViewProvince.setTextColor(context.getResources().getColor(selectedColor));
    }else{
        textViewProvince.setTextColor(context.getResources().getColor(unSelectedColor));
    }
    if(tabIndex != INDEX_TAB_CITY){
        textViewCity.setTextColor(context.getResources().getColor(selectedColor));
    }else{
        textViewCity.setTextColor(context.getResources().getColor(unSelectedColor));
    }
    if(tabIndex != INDEX_TAB_COUNTY){
        textViewCounty.setTextColor(context.getResources().getColor(selectedColor));
    }else{
        textViewCounty.setTextColor(context.getResources().getColor(unSelectedColor));
    }
    if(tabIndex != INDEX_TAB_STREET){
        textViewStreet.setTextColor(context.getResources().getColor(selectedColor));
    }else{
        textViewStreet.setTextColor(context.getResources().getColor(unSelectedColor));
    }

}

    /**
     * 点击省份的监听
     */
    class OnProvinceTabClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            tabIndex = INDEX_TAB_PROVINCE;
            listView.setAdapter(provinceAdapter);

            if (provinceIndex != INDEX_INVALID) {
                listView.setSelection(provinceIndex);
            }

            updateTabsVisibility();
            updateIndicator();
        }
    }

    /**
     * 点击城市的监听
     */
    class OnCityTabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            tabIndex = INDEX_TAB_CITY;
            listView.setAdapter(cityAdapter);

            if (cityIndex != INDEX_INVALID) {
                listView.setSelection(cityIndex);
            }

            updateTabsVisibility();
            updateIndicator();
        }
    }

    /**
     * 点击区 乡镇的监听
     */
    class onCountyTabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            tabIndex = INDEX_TAB_COUNTY;
            listView.setAdapter(countyAdapter);

            if (countyIndex != INDEX_INVALID) {
                listView.setSelection(countyIndex);
            }

            updateTabsVisibility();
            updateIndicator();
        }
    }

    /**
     * 点击街道的监听
     */
    class OnStreetTabClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            tabIndex = INDEX_TAB_STREET;
            listView.setAdapter(streetAdapter);

            if (streetIndex != INDEX_INVALID) {
                listView.setSelection(streetIndex);
            }

            updateTabsVisibility();
            updateIndicator();
        }
    }

    /**
     * 点击右边关闭dialog监听
     */
    class onCloseClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(dialogCloseListener!=null){
                dialogCloseListener.dialogclose();
            }
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (tabIndex) {
            case INDEX_TAB_PROVINCE: //省份
                ProvinceBean province = provinceAdapter.getItem(position);
                provincePostion = position;
                // 更新当前级别及子级标签文本
                textViewProvince.setText(province.name);
                textViewCity.setText("请选择");
                textViewCounty.setText("请选择");
                textViewStreet.setText("请选择");
                //根据省份的id,从数据库中查询城市列表
                retrieveCitiesWith(province.id);

                // 清空子级数据
                cities = null;
                counties = null;
                streets = null;
                cityAdapter.notifyDataSetChanged();
                countyAdapter.notifyDataSetChanged();
                streetAdapter.notifyDataSetChanged();
                // 更新已选中项
                this.provinceIndex = position;
                this.cityIndex = INDEX_INVALID;
                this.countyIndex = INDEX_INVALID;
                this.streetIndex = INDEX_INVALID;
                // 更新选中效果
                provinceAdapter.notifyDataSetChanged();
                break;
            case INDEX_TAB_CITY://城市
                CityBean city = cityAdapter.getItem(position);
                cityPosition = position;
                textViewCity.setText(city.name);
                textViewCounty.setText("请选择");
                textViewStreet.setText("请选择");
                //根据城市的id,从数据库中查询城市列表
                retrieveCountiesWith(city.id);
                // 清空子级数据
                counties = null;
                streets = null;
                countyAdapter.notifyDataSetChanged();
                streetAdapter.notifyDataSetChanged();
                // 更新已选中项
                this.cityIndex = position;
                this.countyIndex = INDEX_INVALID;
                this.streetIndex = INDEX_INVALID;
                // 更新选中效果
                cityAdapter.notifyDataSetChanged();
                break;
            case INDEX_TAB_COUNTY:
                AreaBean county = countyAdapter.getItem(position);
                countyPosition = position;
                textViewCounty.setText(county.name);
                textViewStreet.setText("请选择");
                retrieveStreetsWith(county.id);

                streets = null;
                streetAdapter.notifyDataSetChanged();

                this.countyIndex = position;
                this.streetIndex = INDEX_INVALID;

                countyAdapter.notifyDataSetChanged();
                break;
            case INDEX_TAB_STREET:
                StreetBean street = streetAdapter.getItem(position);
                streetPosition = position;
                textViewStreet.setText(street.name);

                this.streetIndex = position;

                streetAdapter.notifyDataSetChanged();

                callbackInternal();
                if(selectorAreaPositionListener!=null){
                    selectorAreaPositionListener.selectorAreaPosition(provincePostion,cityPosition,countyPosition,streetPosition);
                }

                break;
        }
    }

    /**
     * 查询省份列表
     */
    private void retrieveProvinces(int parentId) {

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", AppCache.getInstance(this.context).getUserInfo().getToken());

        MyRetrofitUtil.getInstance().executeGet(header, (APIConfig.LISTBYPARENTID+parentId), new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    List<ProvinceBean> data = MyUtilJson.parseList(MyUtilJson.toJSONString(json.getData()), ProvinceBean.class);
                    respListData(data);
                } else {

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

    private void respListData(List<ProvinceBean> provinceBean) {
        progressBar.setVisibility(View.VISIBLE);
        List<ProvinceBean> provinceList = provinceBean;
        handler.sendMessage(Message.obtain(handler, WHAT_PROVINCES_PROVIDED, provinceList));
    }



    /**
     * 查询城市列表
     * @param provinceId  城市id
     */
    private void retrieveCitiesWith(int provinceId) {

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", AppCache.getInstance(this.context).getUserInfo().getToken());

        MyRetrofitUtil.getInstance().executeGet(header, (APIConfig.LISTBYPARENTID+provinceId), new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    List<CityBean> data = MyUtilJson.parseList(MyUtilJson.toJSONString(json.getData()), CityBean.class);
                    respCityData(data);
                } else {

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

    private void respCityData(List<CityBean> result) {
        progressBar.setVisibility(View.VISIBLE);
        List<CityBean> cityList = result;
        handler.sendMessage(Message.obtain(handler, WHAT_CITIES_PROVIDED, cityList));
    }

    /**
     * 查询乡镇列表
     * @param provinceId 乡镇id
     */
    private void retrieveCountiesWith(int provinceId){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", AppCache.getInstance(this.context).getUserInfo().getToken());

        MyRetrofitUtil.getInstance().executeGet(header, (APIConfig.LISTBYPARENTID+provinceId), new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    List<AreaBean> data = MyUtilJson.parseList(MyUtilJson.toJSONString(json.getData()), AreaBean.class);
                    respAreaData(data);
                } else {

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

    private void respAreaData(List<AreaBean> result) {
        progressBar.setVisibility(View.VISIBLE);
        List<AreaBean> countyList = result;
        handler.sendMessage(Message.obtain(handler, WHAT_COUNTIES_PROVIDED, countyList));
    }

    /**
     * 查询街道列表
     * @param provinceId 街道id
     */
    private void retrieveStreetsWith(int provinceId) {

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", AppCache.getInstance(this.context).getUserInfo().getToken());

        MyRetrofitUtil.getInstance().executeGet(header, (APIConfig.LISTBYPARENTID+provinceId), new MyCallback<String>() {
            @Override
            public void onSuccess(String s) {
                BaseJson json = MyUtilJson.parseObject(s, BaseJson.class);
                if (json.isSuccess()) {
                    List<StreetBean> data = MyUtilJson.parseList(MyUtilJson.toJSONString(json.getData()), StreetBean.class);
                    respStreetData(data);
                } else {

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

    private void respStreetData(List<StreetBean> result) {
        progressBar.setVisibility(View.VISIBLE);
        List<StreetBean> streetList = result;
        handler.sendMessage(Message.obtain(handler, WHAT_STREETS_PROVIDED, streetList));

    }

    /**
     * 省份 城市 乡镇 街道 都选中完 后的回调
     */
    private void callbackInternal() {
        if (listener != null) {
            ProvinceBean province = provinces == null || provinceIndex == INDEX_INVALID ? null : provinces.get(provinceIndex);
            CityBean city = cities == null || cityIndex == INDEX_INVALID ? null : cities.get(cityIndex);
            AreaBean county = counties == null || countyIndex == INDEX_INVALID ? null : counties.get(countyIndex);
            StreetBean street = streets == null || streetIndex == INDEX_INVALID ? null : streets.get(streetIndex);

            listener.onAddressSelected(province, city, county, street);
        }
    }

    /**
     * 更新进度条
     */
    private void updateProgressVisibility() {
        ListAdapter adapter = listView.getAdapter();
        int itemCount = adapter.getCount();
        progressBar.setVisibility(itemCount > 0 ? View.GONE : View.VISIBLE);
    }

    /**
     * 获得view
     * @return
     */
    public View getView() {
        return view;
    }
    /**
     * 省份的adapter
     */
    class ProvinceAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return provinces == null ? 0 : provinces.size();
        }

        @Override
        public ProvinceBean getItem(int position) {
            return provinces.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

                holder = new Holder();
                holder.textView = convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark =  convertView.findViewById(R.id.imageViewCheckMark);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            ProvinceBean item = getItem(position);
            holder.textView.setText(item.name);

            boolean checked = provinceIndex != INDEX_INVALID && provinces.get(provinceIndex).id == item.id;
            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }

    /**
     * 城市的adaoter
     */
    class CityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cities == null ? 0 : cities.size();
        }

        @Override
        public CityBean getItem(int position) {
            return cities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            CityBean item = getItem(position);
            holder.textView.setText(item.name);

            boolean checked = cityIndex != INDEX_INVALID && cities.get(cityIndex).id == item.id;
            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }

    /**
     * 乡镇的adapter
     */
    class CountyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return counties == null ? 0 : counties.size();
        }

        @Override
        public AreaBean getItem(int position) {
            return counties.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            AreaBean item = getItem(position);
            holder.textView.setText(item.name);

            boolean checked = countyIndex != INDEX_INVALID && counties.get(countyIndex).id == item.id;
            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }

    /**
     * 街道的adapter
     */
    class StreetAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return streets == null ? 0 : streets.size();
        }

        @Override
        public StreetBean getItem(int position) {
            return streets.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area, parent, false);

                holder = new Holder();
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.imageViewCheckMark = (ImageView) convertView.findViewById(R.id.imageViewCheckMark);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            StreetBean item = getItem(position);
            holder.textView.setText(item.name);

            boolean checked = streetIndex != INDEX_INVALID && streets.get(streetIndex).id == item.id;
            holder.textView.setEnabled(!checked);
            holder.imageViewCheckMark.setVisibility(checked ? View.VISIBLE : View.GONE);

            return convertView;
        }

        class Holder {
            TextView textView;
            ImageView imageViewCheckMark;
        }
    }


    public OnAddressSelectedListener getOnAddressSelectedListener() {
        return listener;
    }

    /**
     * 设置地址监听
     * @param listener
     */
    public void setOnAddressSelectedListener(OnAddressSelectedListener listener) {
        this.listener = listener;
    }
    public interface OnDialogCloseListener{
        void dialogclose();
    }
    /**
     * 设置close监听
     */
    public void setOnDialogCloseListener(OnDialogCloseListener listener) {
        this.dialogCloseListener = listener;
    }
    public interface onSelectorAreaPositionListener{
        void selectorAreaPosition(int provincePosition, int cityPosition, int countyPosition, int streetPosition);
    }
    public void setOnSelectorAreaPositionListener(onSelectorAreaPositionListener listener){
        this.selectorAreaPositionListener = listener;
    }

    /**
     * 根据code 来显示选择过的地区
     */
    public void getSelectedArea(String provinceCode, int provincePostion, String cityCode, int cityPosition, String countyCode, int countyPosition, String streetCode, int streetPosition){
//        LogUtil.LogD("数据", "getSelectedArea省份id=" + provinceCode);
//        LogUtil.LogD("数据", "getSelectedArea城市id=" + cityCode);
//        LogUtil.LogD("数据", "getSelectedArea乡镇id=" + countyCode);
//        LogUtil.LogD("数据", "getSelectedArea 街道id=" + streetCode);
//        if(!TextUtils.isEmpty(provinceCode)){
//            ProvinceBean province = addressDictManager.getProvinceBean(provinceCode);
//            textViewProvince.setText(province.name);
//            LogUtil.LogD("数据", "省份=" + province);
//            // 更新当前级别及子级标签文本
//            //根据省份的id,从数据库中查询城市列表
//            retrieveCitiesWith(province.id);
//
//            // 清空子级数据
//            cities = null;
//            counties = null;
//            streets = null;
//            cityAdapter.notifyDataSetChanged();
//            countyAdapter.notifyDataSetChanged();
//            streetAdapter.notifyDataSetChanged();
//            // 更新已选中项
//            this.provinceIndex = provincePostion;
//            this.cityIndex = INDEX_INVALID;
//            this.countyIndex = INDEX_INVALID;
//            this.streetIndex = INDEX_INVALID;
//            // 更新选中效果
//            provinceAdapter.notifyDataSetChanged();
//        }
//        if(!TextUtils.isEmpty(cityCode)){
//            CityBean city = addressDictManager.getCityBean(cityCode);
//            textViewCity.setText(city.name);
//            LogUtil.LogD("数据", "城市=" + city.name);
//            //根据城市的id,从数据库中查询城市列表
//            retrieveCountiesWith(city.id);
//            // 清空子级数据
//            counties = null;
//            streets = null;
//            countyAdapter.notifyDataSetChanged();
//            streetAdapter.notifyDataSetChanged();
//            // 更新已选中项
//            this.cityIndex = cityPosition;
//            this.countyIndex = INDEX_INVALID;
//            this.streetIndex = INDEX_INVALID;
//            // 更新选中效果
//            cityAdapter.notifyDataSetChanged();
//
//        }
//        if(!TextUtils.isEmpty(countyCode)){
//            AreaBean county = addressDictManager.getCountyBean(countyCode);
//            textViewCounty.setText(county.name);
//            LogUtil.LogD("数据", "乡镇=" + county.name);
//            retrieveStreetsWith(county.id);
//
//            streets = null;
//            streetAdapter.notifyDataSetChanged();
//
//            this.countyIndex = countyPosition;
//            this.streetIndex = INDEX_INVALID;
//
//            countyAdapter.notifyDataSetChanged();
//        }
//        if(!TextUtils.isEmpty(streetCode)){
//            StreetBean street = addressDictManager.getStreetBean(streetCode);
//            textViewStreet.setText(street.name);
//            LogUtil.LogD("数据", "街道=" + street.name);
//
//            this.streetIndex = streetPosition;
//
//            streetAdapter.notifyDataSetChanged();
//
//
//
//        }
        callbackInternal();
    }

}
