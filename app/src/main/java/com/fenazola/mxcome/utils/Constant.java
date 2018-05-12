package com.fenazola.mxcome.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.fenazola.mxcome.fragment.project.ProjectChoiceFragment;
import com.fenazola.mxcome.fragment.project.ProjectReadyFragment;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.utils.SharedPrefUtils;

import java.io.File;

/**
 * Created by zm on 2017/2/23.
 */

public class Constant {

    public static String appName = "mxcome";

    public static String recorderDir = appName + File.separator + "recorder";

    public static String imageDir = appName + File.separator + "image";

//    public static String baseUrl = "http://m2c.mxcome.com/mxcome/";

    public static String baseUrl = "http://119.23.16.167:9681/mxcome/";

    public static String newBaseUrl = "http://119.23.16.167:9681/decora/";

    public static String imageUrl = "http://119.23.16.167:9681/mxcome";


//    public static String baseUrl = "http://59.110.164.174:6859/mxcome/";
//
//    public static String newBaseUrl = "http://59.110.164.174:6859/decora/";
//
//    public static String imageUrl = "http://59.110.164.174:6859/mxcome";


    // public static String baseUrl = "http://192.168.1.97:8080/HSApp/";


    //public static String juheUrl = "http://v.juhe.cn/calendar/day?date=%s&key=1ec4818a63be54999181d6c10e532d8c";
    public static String juheUrl = "http://v.juhe.cn/calendar/day?date=%s&key=61157db0731ce0e1b57e6062696616c9";
    public static int pageSize = 10;

    public static String SMS_APPKEY = "1b82f9ab7f4c0";
    public static String SMS_APPSECRET = "2a4b310a4610ab63a795107a6930c705";

    public static String key1 = "key1";
    public static String key2 = "key2";
    public static String key3 = "key3";
    public static String key4 = "key4";
    public static String key5 = "key5";
    public static String key6 = "key6";
    public static String key7 = "key7";
    public static String key8 = "key8";
    public static String key9 = "key9";

}
