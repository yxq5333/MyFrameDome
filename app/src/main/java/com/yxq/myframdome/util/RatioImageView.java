package com.yxq.myframdome.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.yxq.myframdome.R;

/**
 * 可设置宽高比的图片，默认16:9<br/>
 * Created by Administrator
 */
@SuppressLint("AppCompatCustomView")
public class RatioImageView extends ImageView {

    /**
     * 是否以宽度为基准
     */
    private boolean isWidthStandard = true;
    private float width_ratio = 16.0f;
    private float height_ratio = 9.0f;

    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Ratio);
        width_ratio = a.getFloat(R.styleable.Ratio_width_ratio, 16.0f);
        height_ratio = a.getFloat(R.styleable.Ratio_height_ratio, 9.0f);
        isWidthStandard = a.getBoolean(R.styleable.Ratio_widthStandard, true);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isWidthStandard) {
            setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredWidth() * height_ratio / width_ratio));
        }else {
            setMeasuredDimension((int) (getMeasuredHeight() * width_ratio / height_ratio), getMeasuredHeight());
        }
    }

    /**
     * 设置宽高比
     * @param ratio 宽高比
     */
    public void setRatio(float ratio) {
        width_ratio = 100.0f;
        height_ratio = width_ratio / ratio;
    }

    /**
     * 设置是否以宽度为基准
     * @param widthStandard 是否以宽度为基准<ul><li>true：表示高度通过宽度和比例计算</li>
     *                      <li>false：表示宽度通过高度和比例计算</li></ul>
     */
    public void setWidthStandard(boolean widthStandard) {
        isWidthStandard = widthStandard;
    }

}
