package com.yxq.myframdome.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yxq.myframdome.R;

import java.util.HashMap;

/**
 * 弹窗工具类
 * Created by HG
 */
public class PopupWinHelper {

    /**
     * 不设置监听
     */
    public static final int TYPE_NO_LISTENER = 0;
    /**
     * 单击监听
     */
    public static final int TYPE_ON_CLICK = 1;
    /**
     * 列表项目单击监听
     */
    public static final int TYPE_ON_ITEM_CLICK = 2;

    private PopupWindow popupWindow;
    private Context context;
    private PopupWinOnClickListener popupWinOnClickListener;
    private PopupWinOnItemClickListener popupWinOnItemClickListener;
    private PopupWinOnAutoCancelListener popupWinOnAutoCancelListener;
    private HashMap<Integer, View> tags = null;
    private HashMap<Integer, BaseAdapter> adapterTags = null;
    private int layoutRes = -10000;

    public PopupWinHelper(Context context) {
        this.context = context;
    }

    public PopupWinHelper(Context context, PopupWinOnClickListener popupWinOnClickListener) {
        this.context = context;
        this.popupWinOnClickListener = popupWinOnClickListener;
    }

    public PopupWinHelper(Context context, PopupWinOnItemClickListener popupWinOnItemClickListener) {
        this.context = context;
        this.popupWinOnItemClickListener = popupWinOnItemClickListener;
    }

    public PopupWinHelper(Context context, PopupWinOnClickListener popupWinOnClickListener, PopupWinOnItemClickListener popupWinOnItemClickListener) {
        this.context = context;
        this.popupWinOnClickListener = popupWinOnClickListener;
        this.popupWinOnItemClickListener = popupWinOnItemClickListener;
    }

    /**
     * 弹窗控件单击监听
     */
    public interface PopupWinOnClickListener {
        public void OnPopupWinClick(View v);
    }

    /**
     * 弹窗控件列表项目单击监听
     */
    public interface PopupWinOnItemClickListener {
        public void OnPopupWinItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    public interface PopupWinOnAutoCancelListener {
        public void OnAutoCancel();
    }

    /**
     * 初始化
     *
     * @param layoutRes      布局资源 例如:R.layout.item 最外层布局需设置为包裹内容
     * @param gravity        弹窗在界面中的位置 例如:Gravity.BOTTOM
     * @param animationStyle 弹窗动画 例如:android.R.style.Animation_Toast
     * @param bgColor        背景颜色 例如:"#AA000000"
     * @param id             弹窗中需要加载的控件的id
     * @param type           上述控件所对应的触摸事件类型 例如:PopupwinUtils.TYPE_NO_LISTENER(不设置监听)
     */
    public void init(int layoutRes, int gravity, int animationStyle, String bgColor, int[] id, int[] type, boolean isDeveloping) {

        if (popupWindow == null || this.layoutRes != layoutRes) {
            View popupWindowView = View.inflate(context, R.layout.popupwin_base, null);

            popupWindow = new PopupWindow(popupWindowView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                    true);
            popupWindow.setAnimationStyle(animationStyle);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            ColorDrawable dw = new ColorDrawable(Color.parseColor(bgColor));
            popupWindow.setBackgroundDrawable(dw);
            // 软键盘不会挡着PopupWindow
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            // 解决 SDK > 21  标题栏没有办法遮罩的问题
            popupWindow.setClippingEnabled(false);

            tags = new HashMap<>();
            this.layoutRes = layoutRes;

            View includer = View.inflate(context, layoutRes, null);
            LinearLayout father = popupWindowView.findViewById(R.id.ll_father);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) father.getLayoutParams();
            lp.gravity = gravity;
            father.addView(includer);

            View bg = popupWindowView.findViewById(R.id.view_popup_window_bg);
            bg.setOnClickListener(v -> {
                popupWindow.dismiss();
                if (popupWinOnAutoCancelListener != null) {
                    popupWinOnAutoCancelListener.OnAutoCancel();
                }
            });
            tags.put(R.id.view_popup_window_bg, bg);

            if (id != null) {
                for (int i = 0; i < id.length; i++) {
                    View v = popupWindowView.findViewById(id[i]);
                    if (type != null && type.length > i) {
                        switch (type[i]) {
                            case TYPE_ON_CLICK:
                                v.setOnClickListener(v1 -> {
                                    if (popupWinOnClickListener != null) {
                                        popupWinOnClickListener.OnPopupWinClick(v1);
                                    }
                                });
                                break;
                            case TYPE_ON_ITEM_CLICK:

                                break;
                            default:
                                break;
                        }
                    }
                    tags.put(id[i], v);
                }
            }
        }
    }

    /**
     * 弹窗，在init之后调用
     *
     * @param parent parent
     */
    public void showPopupWin(View parent) {
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    /**
     * 关闭弹窗
     */
    public void closePopupWin() {

        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    /**
     * 在init前调用
     *
     * @param popupWinOnClickListener popupWinOnClickListener
     */
    public void setPopupWinOnClickListener(PopupWinOnClickListener popupWinOnClickListener) {
        this.popupWinOnClickListener = popupWinOnClickListener;
    }

    /**
     * 在init前调用
     *
     * @param popupWinOnItemClickListener popupWinOnItemClickListener
     */
    public void setPopupWinOnItemClickListener(PopupWinOnItemClickListener popupWinOnItemClickListener) {
        this.popupWinOnItemClickListener = popupWinOnItemClickListener;
    }

    /**
     * 设置文字
     *
     * @param id  id
     * @param txt 可以是string资源id，可以直接是字符串
     */
    public void setText(int id, Object txt) {

        String str = "";

        if (txt instanceof Integer) {
            str = context.getString((Integer) txt);
        } else if (txt instanceof String) {
            str = (String) txt;
        }

        TextView v = (TextView) tags.get(id);
        v.setText(str);
    }

    /**
     * 设置文字颜色
     *
     * @param id    id
     * @param color 可以是color资源id，可以直接是颜色字符串
     */
    public void setTextColor(int id, Object color) {

        int c = 0;

        if (color instanceof Integer) {
            c = context.getResources().getColor((Integer) color);
        } else if (color instanceof String) {
            c = Color.parseColor((String) color);
        }

        TextView v = (TextView) tags.get(id);
        v.setTextColor(c);
    }

    /**
     * 设置CheckBox是否选中
     *
     * @param id
     * @param isChecked
     */
    public void setCheckBoxChecked(int id, boolean isChecked) {

        CheckBox v = (CheckBox) tags.get(id);
        v.setChecked(isChecked);
    }

    /**
     * 设置列表的适配器
     *
     * @param id
     * @param adapter
     */
    public void setAbsListViewAdapter(int id, BaseAdapter adapter) {

        if (adapterTags == null) {
            adapterTags = new HashMap<Integer, BaseAdapter>();
        }

        AbsListView lv = (AbsListView) tags.get(id);
        lv.setAdapter(adapter);
        adapterTags.put(id, adapter);
    }

    /**
     * 获取AbsListView适配器
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends BaseAdapter> T getAdapter(int id) {
        return (T) adapterTags.get(id);
    }

    /**
     * 获取控件
     *
     * @param id
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int id) {
        return (T) tags.get(id);
    }

    /**
     * 获取控件上的文字
     *
     * @param id
     * @return
     */
    public String getText(int id) {

        String str = "";
        View v = null;

        try {
            v = getView(id);

            if (v instanceof EditText) {
                str = ((EditText) v).getText().toString();
            } else if (v instanceof TextView) {
                str = ((TextView) v).getText().toString();
            } else if (v instanceof Button) {
                str = ((Button) v).getText().toString();
            } else if (v instanceof CheckBox) {
                str = ((CheckBox) v).getText().toString();
            } else if (v instanceof RadioButton) {
                str = ((RadioButton) v).getText().toString();
            }
        } catch (Exception e) {
            str = "";
        }

        return str;
    }

}
