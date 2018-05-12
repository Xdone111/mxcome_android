package com.fenazola.mxcome.fragment.project;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.DateUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.wheelview.OnWheelChangedListener;
import com.zss.library.wheelview.OnWheelScrollListener;
import com.zss.library.wheelview.WheelView;
import com.zss.library.wheelview.adapter.ArrayWheelAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 发布需求——时间
 */
public class Projectwheel extends Dialog implements View.OnClickListener {

    /**
     * 选择时间
     */
    private FrameLayout selectTimeFy;
    private TextView tvTimeNo, tvTimeYes;
    // Time changed flag
    private boolean timeChanged = false;

    //
    private boolean timeScrolled = false;
    WheelView mYears,mMonths,mDays;
    String[] strMins;
    String[] strDays = new String[2];
    String[] strHours = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
    long[] intDays = new long[30];

    private LinearLayout showMsgLy1;
    private TextView toEdit;
    private EditText editMessageEt;
    private ImageView toRecordIv;
    private TextView showRecodrdTv;
    private boolean isStopCount = false;
    private long timer = 0;
    float y1 = 0;
    float y2 = 0;
    int mYear;
    private int lastDaysIndex=0;
    ArrayWheelAdapter arrayWheelAdapter;

    Activity mContext;
    MyListener listener;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Projectwheel(Activity context,MyListener listener) {

        super(context, com.zss.library.R.style.CommonDialog);
        this.listener=listener;
        setContentView(R.layout.fragment_project_wheel);
        this.mContext=context;
        int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width, width);
        initView();
        initData();
    }

    public void initView() {
        selectTimeFy = (FrameLayout) findViewById(R.id.show_select_time_fy);
        mYears = (WheelView) findViewById(R.id.day);
        mMonths = (WheelView) findViewById(R.id.hour);
        mDays = (WheelView) findViewById(R.id.mins);
        tvTimeNo = (TextView) findViewById(R.id.select_time_no);
        tvTimeYes = (TextView) findViewById(R.id.select_time_yes);
        selectTimeFy.setOnClickListener(this);
        tvTimeNo.setOnClickListener(this);
        tvTimeYes.setOnClickListener(this);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initData() {

        long time = System.currentTimeMillis();
        //获取今天以及以后一个月的时间戳
        for (int i = 0; i < strDays.length; i++) {
            strDays[i] = DateUtils.getTimeDay(time + (long) (i * (long) (24 * 60 * 60 * 1000)));
            intDays[i] = time + (long) (i * (long) (24 * 60 * 60 * 1000));
            // LogUtils.i("XHX","时间戳："+ strDays[i]+";"+intDays[i]);

        }

        Calendar c = Calendar.getInstance();//首先要获取日历对象
         mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
        strDays[0]=mYear+"年";
        intDays[0]=mYear;
        strDays[1]=(mYear+1)+"年";
        intDays[1]=mYear+1;

        if(mMonth==4||mMonth==6||mMonth==9||mMonth==11){
            strMins=new String[30];
            for (int i=0;i<30;i++){
                strMins[i]=(i+1)+"日";
            }

        }else if (mMonth==2){
            if(mYear % 4 == 0 && mYear % 100 != 0 || mYear % 400 == 0){
                strMins=new String[29];
                for (int i=0;i<29;i++){
                    strMins[i]=(i+1)+"日";
                }

            }else{
                strMins=new String[28];
                for (int i=0;i<28;i++){
                    strMins[i]=(i+1)+"日";
                }

            }
        }else{
            strMins=new String[31];
            for (int i=0;i<31;i++){
                strMins[i]=(i+1)+"日";
            }

        }



        mYears.setViewAdapter(new ArrayWheelAdapter(getContext(), strDays));

        mMonths.setViewAdapter(new ArrayWheelAdapter(getContext(), strHours));
        arrayWheelAdapter=new ArrayWheelAdapter(getContext(), strMins);
        mDays.setViewAdapter(arrayWheelAdapter);
        int number=0;
        for (int i=0;i<strHours.length;i++){
            if((mMonth+"月").equals(strHours[i])){
                number=i;
            }
        }
        int dayss=0;
        for (int i=0;i<strMins.length;i++){
            if((mDay+"日").equals(strMins[i])){
                dayss=i;
            }
        }
        mMonths.setCurrentItem(number);
        mDays.setCurrentItem(dayss);
        lastDaysIndex=dayss;
        // add listeners
        addChangingListener(mYears);
        addChangingListener(mMonths);
        addChangingListener(mDays);
        OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!timeScrolled) {
                    timeChanged = true;
//                    picker.setCurrentHour(hours.getCurrentItem());
//                    picker.setCurrentMinute(mins.getCurrentItem());
                    timeChanged = false;
                }
            }
        };
        mYears.addChangingListener(wheelListener);
        mMonths.addChangingListener(wheelListener);
        mDays.addChangingListener(wheelListener);

        OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                timeScrolled = true;
            }

            public void onScrollingFinished(WheelView wheel) {
                timeScrolled = false;
                timeChanged = true;
//                picker.setCurrentHour(hours.getCurrentItem());
//                picker.setCurrentMinute(mins.getCurrentItem());
                timeChanged = false;
            }
        };

        mYears.addScrollingListener(scrollListener);
        mMonths.addScrollingListener(scrollListener);
        mDays.addScrollingListener(scrollListener);

    }
    /**
     * Adds changing listener for wheel that updates the wheel label
     *
     * @param wheel the wheel
     */
    private void addChangingListener(final WheelView wheel) {
        wheel.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                //wheel.setLabel(newValue != 1 ? label + "s" : label);
                LogUtils.i("XHX", "下标：" + newValue);
                if(wheel==mMonths){
                    //1 3 5 7 8 10 12
                    //2
                    //4 6 9 11
                    if(newValue==3||newValue==5||newValue==8||newValue==10){
                        strMins=new String[30];
                        for (int i=0;i<30;i++){
                            strMins[i]=(i+1)+"日";
                        }
                    }else if (newValue==1){
                        if(mYear % 4 == 0 && mYear % 100 != 0 || mYear % 400 == 0){
                            strMins=new String[29];
                            for (int i=0;i<29;i++){
                                strMins[i]=(i+1)+"日";
                            }
                        }else{
                            strMins=new String[28];
                            for (int i=0;i<28;i++){
                                strMins[i]=(i+1)+"日";
                            }
                        }
                    }else{
                        strMins=new String[31];
                        for (int i=0;i<31;i++){
                            strMins[i]=(i+1)+"日";
                        }
                    }
                    arrayWheelAdapter=new ArrayWheelAdapter(getContext(), strMins);
                    mDays.setViewAdapter(arrayWheelAdapter);
                    if(lastDaysIndex>=strMins.length){
                        lastDaysIndex=strMins.length-1;
                        mDays.setCurrentItem(lastDaysIndex);
                    }

                }else if(wheel==mDays){
                    lastDaysIndex=newValue;
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.select_time_yes:
                String mo=strHours[mMonths.getCurrentItem()].length()<3?"0"+strHours[mMonths.getCurrentItem()]:strHours[mMonths.getCurrentItem()];
                String da=strMins[mDays.getCurrentItem()].length()<3?"0"+strMins[mDays.getCurrentItem()]:strMins[mDays.getCurrentItem()];
                listener.refreshActivity(strDays[mYears.getCurrentItem()]+mo+da);
                dismiss();
                break;
            case R.id.select_time_no:
            case R.id.show_select_time_fy:
                dismiss();
                break;
        }
    }
    public interface MyListener{

        public void refreshActivity(String text);

    }
}
