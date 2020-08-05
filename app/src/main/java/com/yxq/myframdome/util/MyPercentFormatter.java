package com.yxq.myframdome.util;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class MyPercentFormatter extends PercentFormatter {

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        if (value == 0f) {
            return "";
        } else {
            return super.getFormattedValue(value, entry, dataSetIndex, viewPortHandler);
        }
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (value == 0f) {
            return "";
        } else {
            return super.getFormattedValue(value, axis);
        }
    }

}
