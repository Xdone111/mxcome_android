package com.fenazola.mxcome.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

/**
 * 用于存放首页的数据啊
 * Created by xhx on 2017/6/20.
 */

public class SharedUtils {
    public  String fileName="index_type";

    public Context mContext;
    public SharedUtils(Context mContext) {
        this.mContext=mContext;

    }
    public boolean saveArray(List<String> list) {
        SharedPreferences sp = mContext.getSharedPreferences(fileName, mContext.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1= sp.edit();
        mEdit1.putInt("Status_size",list.size());
        for(int i=0;i<list.size();i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, list.get(i));
        }
        return mEdit1.commit();
    }
    public List<String> loadArray(List<String> list) {

        SharedPreferences mSharedPreference1 = mContext.getSharedPreferences(fileName, mContext.MODE_PRIVATE);
        list.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);
        for(int i=0;i<size;i++) {
            list.add(mSharedPreference1.getString("Status_" + i, null));
        }
        return list;
    }
}
