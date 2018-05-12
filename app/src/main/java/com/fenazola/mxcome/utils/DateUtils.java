package com.fenazola.mxcome.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.zss.library.utils.LogUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zm on 2017/3/24.
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {

    public static String getTimeStamp(){
       return "" + System.currentTimeMillis();
    }

    public static String getTalkTime(String time) {
        if(TextUtils.isEmpty(time)){
            return "";
        }
        String show_time = time.substring(11, 16);
        String getDay = getDay(time);
        if (show_time != null && getDay != null)
            show_time = getDay + " " + show_time;
        return show_time;
    }

    public static String getTime(String time, String before) {
        String show_time = null;
        if (before != null) {
            try {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date now = df.parse(time);
                java.util.Date date = df.parse(before);
                long l = now.getTime() - date.getTime();
                long day = l / (24 * 60 * 60 * 1000);
                long hour = (l / (60 * 60 * 1000) - day * 24);
                long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
                if (min >= 2) {
                    show_time = time.substring(11, 16);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            show_time = time.substring(11, 16);
        }
        String getDay = getDay(time);
        if (show_time != null && getDay != null)
            show_time = getDay + " " + show_time;
        return show_time;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date;
    }

    public static String getTime(long time) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date(time));
    }

    public static String getDay(String time) {
        String showDay = null;
        String nowTime = getCurrentTime();
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date now = df.parse(nowTime);
            java.util.Date date = df.parse(time);
            Calendar nowCal = Calendar.getInstance();
            nowCal.setTime(now);
            Calendar dateCal = Calendar.getInstance();
            dateCal.setTime(date);
            int nowYear = nowCal.get(Calendar.YEAR);
            int dateYear = dateCal.get(Calendar.YEAR);
            int nowDay = nowCal.get(Calendar.DAY_OF_MONTH);
            int dateDay = dateCal.get(Calendar.DAY_OF_MONTH);
            if (nowYear > dateYear) {
                showDay = time.substring(0, 10);
            } else if (nowDay  >dateDay) {
                showDay = time.substring(5, 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showDay;
    }
    public static String getTimeString(long time) {
        time/=1000;
        String mm=time/60<10?"0"+time/60:""+time/60;
        String ss=time%60<10?"0"+time%60:""+time%60;
        return mm+":"+ss;
    }
    public static String getTimeDay(long time) {
        DateFormat df = new SimpleDateFormat("MM月dd日");
        return df.format(new Date(time));
    }
}
