package com.fenazola.mxcome.fragment.main.demand;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.LocationEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DateUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.SkillUtils;
import com.fenazola.mxcome.utils.TechnicalLevelUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.AlwaysMarqueeTextView;
import com.fenazola.mxcome.widget.AutoTextView;
import com.igexin.sdk.PushManager;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.utils.LogUtils;
import com.zss.library.wheelview.OnWheelChangedListener;
import com.zss.library.wheelview.OnWheelScrollListener;
import com.zss.library.wheelview.WheelView;
import com.zss.library.wheelview.adapter.ArrayWheelAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 发布需求——时间
 */
public class Demandwheel extends Dialog implements View.OnClickListener {

    /**
     * 选择时间
     */
    private FrameLayout selectTimeFy;
    private TextView tvNowTime, tvTimeNo, tvTimeYes;
    // Time changed flag
    private boolean timeChanged = false;

    //
    private boolean timeScrolled = false;
    WheelView hours, mins, days;
    String[] strMins = new String[]{"整", "10分", "20分", "30分", "40分", "50分"};
    String[] strDays = new String[30];
    String[] strHours = new String[]{"0点", "1点", "2点", "3点", "4点", "5点", "6点", "7点", "8点", "9点", "10点", "11点", "12点", "13点", "14点", "15点", "16点", "17点", "18点", "19点", "20点", "21点", "22点", "23点"};
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
    ArrayWheelAdapter mDaysAdapter,mHoursAdapter,mMinsAdapter;

    Activity mContext;

    public Demandwheel(Activity context) {
        super(context, com.zss.library.R.style.CommonDialog);
        setContentView(R.layout.fragment_wheel);
        this.mContext=context;
        int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width, width);
        initView();
        initData();
    }

    public void initView() {
        selectTimeFy = (FrameLayout) findViewById(R.id.show_select_time_fy);
        days = (WheelView) findViewById(R.id.day);
        hours = (WheelView) findViewById(R.id.hour);
        mins = (WheelView) findViewById(R.id.mins);
        tvNowTime = (TextView) findViewById(R.id.tv_now_time);
        tvTimeNo = (TextView) findViewById(R.id.select_time_no);
        tvTimeYes = (TextView) findViewById(R.id.select_time_yes);
        selectTimeFy.setOnClickListener(this);
        tvTimeNo.setOnClickListener(this);
        tvTimeYes.setOnClickListener(this);
        tvNowTime.setOnClickListener(this);


    }


    public void initData() {

        long time = System.currentTimeMillis();
        //获取今天以及以后一个月的时间戳
        for (int i = 0; i < strDays.length; i++) {
            strDays[i] = DateUtils.getTimeDay(time + (long) (i * (long) (24 * 60 * 60 * 1000)));
            intDays[i] = time + (long) (i * (long) (24 * 60 * 60 * 1000));
            // LogUtils.i("XHX","时间戳："+ strDays[i]+";"+intDays[i]);

        }
        mDaysAdapter=new ArrayWheelAdapter(getContext(), strDays);
        days.setViewAdapter(mDaysAdapter);
        mHoursAdapter=new ArrayWheelAdapter(getContext(), strHours);
        hours.setViewAdapter(mHoursAdapter);
        mMinsAdapter=new ArrayWheelAdapter(getContext(), strMins);
        mins.setViewAdapter(mMinsAdapter);
        // add listeners
        addChangingListener(mins);
        addChangingListener(hours);
        addChangingListener(days);
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
        days.addChangingListener(wheelListener);
        hours.addChangingListener(wheelListener);
        mins.addChangingListener(wheelListener);

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

        hours.addScrollingListener(scrollListener);
        mins.addScrollingListener(scrollListener);
        days.addScrollingListener(scrollListener);

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
            }
        });
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.select_time_yes:
                dismiss();
                break;
            case R.id.select_time_no:
            case R.id.show_select_time_fy:
                dismiss();
                break;
        }
    }

}
