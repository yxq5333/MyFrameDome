package com.yxq.myframdome.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxq.myframdome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 实现标题栏,页面多个page展示
 *
 * */
public class FragmentPagesUtil {

    private static FragmentPagesUtil pagesUtil;
    private static Context mContext;
    public FragmentPagesUtil(){

    }
    public static FragmentPagesUtil getInstant(Context context){
        if(pagesUtil==null){
            pagesUtil=new FragmentPagesUtil();
        }
        mContext=context;

        return pagesUtil;
    }

    /**
    * list<> 的泛型函数，用来获取 要显示的布局集合
    *
    * */
    public static  <T extends View> List<T> getList(T... veiws){
        List<T> conViews=new ArrayList<>();
        int length=veiws.length;
        for(int i=0;i<length;i++){
            conViews.add(veiws[i]);
        }
        return conViews;
    }

    private <b>  void getString (b view){

    }

    /**
     * TextView 和 ImageView 对应绑定的 Map 泛型函数
     *
     * */
    public static  <K,V> Map<K,V> getMap(K tv,V iv){
        Map<K,V> itemMap=new HashMap<>();
        itemMap.put(tv,iv);
        return itemMap;
    }

    public static <T extends View> void configItemView(
            int index, List<T> contentViews, Map<TextView, ImageView>... views){
        int length=views.length;
        int status;
        TextView key = null;
        ImageView value = null;
        Set set;
        for(int i=0;i<length;i++){
            set=views[i].keySet();
            for(Object aSet:set){
                key= (TextView) aSet;
                value=views[i].get(key);
            }
            if(i==index){
                key.setTextColor(mContext.getResources().getColor(R.color.blue));
                status=View.VISIBLE;
            }else{
                key.setTextColor(mContext.getResources().getColor(R.color.gray));
                status=View.GONE;
            }
            value.setVisibility(status);
            contentViews.get(i).setVisibility(status);
        }
    }
    /**
     * 使用列子
     *
     * */
//    private void a(Context context){
//        FragmentPagesUtil.getInstant(context).configItemView(
//                2,
//                getList(view1,view2,view3),
//                getMap(first,icon1),getMap(second,icon2),getMap(three,icon3));
//    }

    private <T> void a(T a){

    }

}
