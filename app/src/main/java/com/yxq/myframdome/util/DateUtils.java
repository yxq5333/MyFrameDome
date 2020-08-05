package com.yxq.myframdome.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {

    public static long getDateLong(String dateStr, String format) {

        long result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            result = sdf.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

}
