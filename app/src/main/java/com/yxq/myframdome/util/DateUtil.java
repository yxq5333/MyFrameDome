package com.yxq.myframdome.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author tangjin
 * @Title: {标题}
 * @Description:{日期工具}
 * @date 2017/12/4
 */
public class DateUtil {

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;

    /**
     * 08-30 20:27
     *
     * @param remind_date 2018-08-30
     * @param remind_time 20:27
     * @return
     */
    public String getRemidDateTime(String remind_date, String remind_time) {
        if (TextUtils.isEmpty(remind_date)) {
            return "添加到日历";
        }

        Date date = DateUtil.StrToDate(remind_date, "yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String newDate = dateFormat.format(date);
        return newDate + " " + remind_time;
    }

    /**
     * 今天07月04日星期五
     *
     * @return
     */
    public static String getNoticeTime(Date date) {

        String dateTime = isToday(date) ? "今天" : "";
        if (isYear(date)) {
            SimpleDateFormat sf = new SimpleDateFormat("MM月dd日");
            //07月04日
            String day = sf.format(date);
            String week = getWeekOfDate(date);
            dateTime = dateTime.concat(day);
            dateTime = dateTime.concat(week);
        } else {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
            //07月04日
            String day = sf.format(date);
            String week = getWeekOfDate(date);
            dateTime = dateTime.concat(day);
            dateTime = dateTime.concat(week);
        }
        return dateTime;

    }

    public static void showtimeone(String title, final TextView view, Context context) {
        DatePickDialog dialog = new DatePickDialog(context);
        //设置上下年分限制
        dialog.setYearLimt(10);
        //设置标题
        dialog.setTitle(title);
        //设置类型
        dialog.setType(DateType.TYPE_YMD);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                view.setText(DateUtil.getDateYear(date));
            }
        });
        dialog.show();
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * 今天07月04日星期五
     *
     * @return
     */
    public static String getNoticeTime(String createTime) {
        if (TextUtils.isEmpty(createTime)) {
            return getNoticeTime(new Date());
        }
        return getNoticeTime(StrToDate2(createTime));
    }


    /**
     * 2018-09-05 08:46:29
     *
     * @param time
     * @return
     */
    public static String getReleaseTime(String time) {
        return getReleaseTime(StrToDate2(time));
    }

    /**
     * new Date(long)
     * 根据给定时间获得 时间字符串：几秒前、几分钟前、今天HH:mm、昨天HH:mm、一年之内(MM-dd HH:mm)、yyyy-MM-dd HH:mm
     * <p>
     * DateUtil.getReleaseTime(new Date(commentItem.getCreate_time()*1000))
     *
     * @param date
     * @return String
     */
    public static String getReleaseTime(Date date) {
        Date now = new Date();//获取当前时间
        long delta = now.getTime() - date.getTime();
        if (delta < ONE_MINUTE) {
            long seconds = toSeconds(delta);//(seconds <= 0 ? 1 : seconds) + "秒前";
            return "刚刚";
        }
        if (delta < ONE_HOUR) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + "分钟前";
        }
        if (delta < (now.getTime() - getTodayDateAtZeroAM(now).getTime())) {
            return "今天" + DateToStr(date, "HH:mm");
        }
        if (delta > (now.getTime() - getTodayDateAtZeroAM(now).getTime()) && delta < (now.getTime() - getYestodayDateAtZeroAM(now).getTime())) {
            return "昨天" + DateToStr(date, "HH:mm");
        }
        if (delta > (now.getTime() - getYestodayDateAtZeroAM(now).getTime()) && delta < (now.getTime() - getCurrentYearAtZeroAM(now).getTime())) {
            return DateToStr(date, "MM-dd HH:mm");
        } else {
            return DateToStr(date, "yyyy-MM-dd");
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

    /**
     * 两个时间相差距离多少天多少小时
     *
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return 返回值为：{天, 时, 分, 秒}
     * @throws ParseException
     */
    public static String getDistanceTimesForHour(String str1, String str2)
            throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String timsString = null;
        if (day > 0 && hour > 0) {
            timsString = day + "天" + hour + "小时";
        } else {
            timsString = day + "天";
        }
        return timsString;
    }


    public static boolean IsToday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }


    /**
     * 判断是否为昨天(效率比较高)
     *
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return true今天 false不是
     * @throws ParseException
     */
    public static boolean IsTomorrowday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 1) {
                return true;
            }
        }
        return false;
    }

    public static String getWeek(String pTime) {


        String Week = "";


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }

        return Week;
    }

    /**
     * 6 -> 06
     *
     * @param one
     * @return
     */
    public static String oneToTwo(String one) {
        if (TextUtils.isEmpty(one))
            return "00";
        if (one.length() == 1) {
            return "0" + one;
        }
        return one;

    }

    /**
     * 字符串转日期()
     */
    public static Date StrToDate2(String str) {
        return StrToDate(str, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 秒数转换成格式化的String时间
     *
     * @param datetime Stirng型时间戳（秒数）
     * @return
     */
    public static String sec2Date(String datetime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Long time = new Long(datetime);
        String d = sdf.format(time * 1000);
        return d;
    }

    /**
     * 毫秒数转换成格式化的String时间
     *
     * @param datetime Stirng型时间戳（毫秒数）
     * @return
     */
    public static String hSec2Date(String datetime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Long time = new Long(datetime);
        String d = sdf.format(time);
        return d;
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 得到当前时间之后的几个小时时间
     *
     * @param differhour
     * @return
     */
    public static String getCurrentHourAfter(int differhour) {
        long currenttime = new Date().getTime();
        Date dat = new Date(currenttime + 1000 * 60 * 60 * differhour);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(dat);
    }

    /**
     * 得到当前时间之前的几个小时时间
     *
     * @param differhour
     * @return
     */
    public static String getCurrentHourBefor(int differhour) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        long currenttime = new Date().getTime();
        Date dat = new Date(currenttime - 1000 * 60 * 60 * differhour);
        // format.parse(format.format(dat))
        return format.format(dat);
    }

    /**
     * 字符串转日期
     */
    public static Date StrToDate(String str, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间转化
     *
     * @param time
     * @param type 类型
     * @return
     */
    public static String DateToStr(Date time, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(type);
        String date = dateFormat.format(time);
        return date;
    }

    /**
     * 获取当前时间，格式为 :yyyy-MM
     *
     * @return
     */
    public static String getCurrentDate() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String date = dateFormat.format(now);
        return date;
    }

    public static String getCurreDateFormat(String format) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String date = dateFormat.format(now);
        return date;
    }

    /**
     * 获取当前时间，格式为 :MM
     *
     * @return
     */
    public static Integer getCurrentDate_MM() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        String date = dateFormat.format(now);
        return isInteger(date);
    }

    /**
     * 获取当前时间，格式为 :yyyy-MM-dd
     *
     * @return
     */
    public static Integer getCurrentDate_MM(Date now) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        String date = dateFormat.format(now);
        return isInteger(date);
    }

    /**
     * 获取当前时间，格式为 :yyyy-MM-dd
     *
     * @return
     */
    public static Integer getCurrentDate_dd() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        String date = dateFormat.format(now);
        return isInteger(date);
    }

    /**
     * 获取当前时间，格式为 :yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDate2() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(now);
        return date;
    }

    /**
     * 获取当前时间，格式为 :yyyy-MM-dd HH_mm_ss
     *
     * @return
     */
    public static String getCurrentDate2filename() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
        String date = dateFormat.format(now);
        return date;
    }

    /**
     * 获取当前时间，格式为 :yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDates() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(now);
        return date;
    }

    /**
     * 获取时间，格式为 :yyyy-MM-dd
     *
     * @return
     */
    public static String getDateYear(Date now) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(now);
        return date;
    }

    /**
     * 获取时间，格式为 :yyyy-MM-dd
     *
     * @return
     */
    public static String getDateYearHour(Date now) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = dateFormat.format(now);
        return date;
    }

    /**
     * 获取时间，格式为 :yyyy-MM-dd
     *
     * @return
     */
    public static String getDate(Date now) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年 MM月dd日 HH:mm");
        String date = dateFormat.format(now);
        return date;
    }

    /**
     * 获取时间，格式为 :yyyy-MM-dd
     *
     * @return
     */
    public static String getDateTwo(Date now) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年 MM月dd日");
        String date = dateFormat.format(now);
        return date;
    }

    /**
     * 获取时间，格式为 :yyyy-MM
     *
     * @return
     */
    public static String getDateYue() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String date = dateFormat.format(now);
        return date;
    }

    /**
     * 获取时间，格式为 :yyyy-MM
     *
     * @return
     */
    public static String getDateYue(Date now) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String date = dateFormat.format(now);
        return date;
    }


    /**
     * 获取时间，格式为 :HH:mm
     *
     * @return
     */
    public static String getDateTime(Date now) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String date = dateFormat.format(now);
        return date;
    }

    /**
     * str 转换成Date
     *
     * @param date
     * @param format
     * @return
     */
    public static Date str2Date(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取给定Date当天前一天零点时间，格式为 :yyyy-MM-dd HH:mm：ss
     *
     * @return
     */
    public static Date getYestodayDateAtZeroAM(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        Date zeroDate = null;
        try {
            zeroDate = formatter.parse(dateString.substring(0, dateString.length() - 8) + "00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return zeroDate;
        }
        return zeroDate;
    }

    /**
     * 获取给定Date当天零点时间，格式为 :yyyy-MM-dd HH:mm：ss
     *
     * @return Date
     */
    public static Date getTodayDateAtZeroAM(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        Date zeroDate = null;
        try {
            zeroDate = formatter.parse(dateString.substring(0, dateString.length() - 8) + "00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return zeroDate;
        }
        return zeroDate;
    }

    /**
     * 获取给定Date该年份第一天零点时间，格式为 :yyyy-MM-dd HH:mm：ss
     *
     * @return Date
     */
    public static Date getCurrentYearAtZeroAM(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        Date zeroDate = null;
        try {
            zeroDate = formatter.parse(dateString.substring(0, dateString.length() - 14) + "01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
            return zeroDate;
        }
        return zeroDate;
    }

    /**
     * 获取明天零点时间，格式为 :yyyy-MM-dd HH:mm：ss
     *
     * @return
     */
    public static String getTomorrowDateAtZeroAM() {
        Date date = new Date();// 取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString.substring(0, dateString.length() - 8) + "00:00:00";
    }

    /**
     * 比较两个 yyyy-MM-dd 格式的日期字符串时间前后
     *
     * @param date1
     * @param date2
     * @return true:"date1在date2后" , false:"date1在date2前"
     */
    public static boolean dateComparator(String date1, String date2) {
        return dateComparator(date1, date2, "yyyy-MM-dd");
    }

    /**
     * 比较两个 yyyy-MM-dd HH:mm:ss 格式的日期字符串时间前后
     *
     * @param date1
     * @param date2
     * @return true:"date1在date2后" , false:"date1在date2前"
     */
    public static boolean dateComparator2(String date1, String date2) {
        return dateComparator(date1, date2, "yyyy-MM-dd HH:mm:ss");
    }

    public static boolean dateComparator(String date1, String date2, String str) {
        DateFormat df = new SimpleDateFormat(str);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return false;
            } else if (dt1.getTime() < dt2.getTime()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * 获取两个日期的差 yyyy-MM-dd
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long dateDifference1(String date1, String date2) {
        return dateDifference(date1, date2, "yyyy-MM-dd");
    }

    /**
     * 获取两个日期的差 yyyy-MM-dd HH:mm:ss
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long dateDifference2(String date1, String date2) {
        return dateDifference(date1, date2, "yyyy-MM-dd HH:mm:ss");

    }

    /**
     * 获取两个日期的差
     *
     * @param date1
     * @param date2
     * @param str
     * @return
     */
    public static long dateDifference(String date1, String date2, String str) {
        DateFormat df = new SimpleDateFormat(str);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            long temp = dt2.getTime() - dt1.getTime();
            long result = temp / (1000 * 60);
            return result;
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    /**
     * 得到两个日期的差
     *
     * @param fDate 秒数
     * @param oDate 秒数
     * @return 天数
     */
    public static int daysOfTwo(long fDate, long oDate) {
        Date dt1 = new Date(fDate * 1000);
        Date dt2 = new Date(oDate * 1000);
        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(dt1);

        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

        aCalendar.setTime(dt2);

        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;
    }

    /**
     * 比较两个数的大小
     *
     * @param num1
     * @param num2
     * @return
     */
    public static boolean numComparator(String num1, String num2) {
        int int1 = Integer.parseInt(num1.trim());
        int int2 = Integer.parseInt(num2.trim());
        return int1 > int2;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param time 需要获取的日期
     * @return 当前日期是星期几，(从0开始，周日、周一.....)
     */
    public static int getWeekOfDate(String time) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt;
        int week = 0;
        try {
            dt = df.parse(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            week = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (week < 0) {
                week = 0;
            }
        } catch (ParseException e) {
        }
        return week;
    }

    /**
     * 判断时间是不是今天
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    private static boolean isToday(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);

        //对比的时间
        String day = sf.format(date);

        return day.equals(nowDay);

    }

    /**
     * 判断时间是不是今年
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    private static boolean isYear(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);

        return day.equals(nowDay);

    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param date 需要获取的日期
     * @return 当前日期是星期几，(从0开始，周日、周一.....)
     */
    public static String getWeekOfDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = date;
        int week = 0;
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        try {
//            dt = df.parse(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            week = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (week < 0) {
                week = 0;
            }
        } catch (Exception e) {
        }
        return weekDays[week];
    }

    /**
     * 判断是否为double类型
     *
     * @param str
     * @return
     */
    public static boolean isDoubleNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) { // 不是数字
            return false;
        }
    }

    /**
     * 判断是否为float类型
     *
     * @param str
     * @return
     */
    public static boolean isFloatNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) { // 不是数字
            return false;
        }
    }

    public static boolean isLongNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) { // 不是数字
            return false;
        }
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) { // 不是数字
            return false;
        }
    }

    /**
     * 检查Integer数据
     */
    public static Integer isInteger(Integer num) {
        return isInteger(num + "");
    }

    /**
     * 检查Double数据
     */
    public static Double isDouble(Double num) {
        return isDouble(num + "");
    }

    /**
     * String检查Integer数据
     */
    public static Integer isInteger(String num) {
        return Integer.parseInt(checkInt(num));
    }

    /**
     * String检查Double数据
     */
    public static Double isDouble(String num) {
        return Double.parseDouble(checkDouble(num));
    }

    /**
     * String检查Float数据
     */
    public static Float isFloat(String num) {
        return Float.parseFloat(checkFloat(num));
    }

    /**
     * String检查Long数据
     */
    public static long isLong(String num) {
        return Long.parseLong(checkLong(num));
    }

    /**
     * 检查String数据
     */
    public static String isString(String str) {
        if (null == str) {
            return "";
        }
        return str;

    }

    public static String checkInt(String num) {
        return (num == null || !isNumeric(num)) ? "0" : num;
    }

    public static String checkDouble(String num) {
        return (num == null || !isDoubleNumeric(num)) ? "0" : num;
    }

    public static String checkFloat(String num) {
        return (num == null || !isFloatNumeric(num)) ? "0" : num;
    }

    public static String checkLong(String num) {
        return (num == null || !isDoubleNumeric(num)) ? "0" : num;
    }

    public static String checkStr(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    public static String getSimpleTime(String time) {
        if (TextUtils.isEmpty(time) || time.length() <= 3) {
            return "";
        } else {
            return time.substring(0, time.length() - 3);
        }
    }

}
