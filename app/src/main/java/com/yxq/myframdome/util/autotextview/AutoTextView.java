package com.yxq.myframdome.util.autotextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

import java.util.List;

@SuppressLint("AppCompatCustomView")
public class AutoTextView extends AutoCompleteTextView {

    private Context mContext = null;
    private AutoTextViewFilter mAutoTextViewFilter = null;
    public AutoTextView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public AutoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }
    public AutoTextView(Context context, int type) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
//        this.setPadding(10, 15, 10, 15);
//        this.setSingleLine(true);
//        this.setLines(1);
//        this.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {// hasFocus 为 true时获取焦点；为false时失去焦点
//                    InputMethodManager imm = (InputMethodManager) mContext
//                            .getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(
//                            AutoTextView.this.getWindowToken(), 0);
//                }
//            }
//
//        });
        this.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(null!=mAutoTextViewFilter){
                    if (s == null || s.length() == 0) {
                        mAutoTextViewFilter.filter("");
                    } else {
                        mAutoTextViewFilter.filter(s);
                    }
                }
            }

        });
    }
    public void setData(List<String> alist,
                        final OnDataFilterListener alistener, int aSelectedType) {

        mAutoTextViewFilter = new AutoTextViewFilter(alist);
        AutoTextViewFilter.DataFilterListener mDataFilterListener = new AutoTextViewFilter.DataFilterListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void hasFiler(Object aT) {
                alistener.hasFiler((List<String>) aT);
            }

            @Override
            public void noFilter() {
                alistener.noFilter();
            }
            public void clearAll() {

            }
            @Override
            public void setTree() {
                alistener.setTree();
            }

        };
        mAutoTextViewFilter.setDataFilterListener(mDataFilterListener);
    }

    public interface OnDataFilterListener {
        void hasFiler(List<String> aT);

        void noFilter();

        void clearAll();

        void setTree();
    }
}
