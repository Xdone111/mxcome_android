package com.fenazola.mxcome.fragment.msg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.othershe.calendarview.CalendarView;
import com.othershe.calendarview.DateBean;
import com.othershe.calendarview.listener.OnMonthItemClickListener;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by zm on 2017/5/17.
 * 聊天记录
 */

public class ChatLogFragment extends BaseFragment {

    private CalendarView calendarView;

    private  TextView title;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_chat_log;
    }

    @Override
    public void initView() {
        super.initView();
        title = (TextView) findViewById(R.id.title);
        calendarView = (CalendarView) findViewById(R.id.calendar);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        calendarView.init();

        DateBean d = calendarView.getDateInit();

        title.setText(d.getSolar()[0] + "." + d.getSolar()[1]/** + "月" + d.getSolar()[2] + "日"*/);

        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "." + date[1]/* + "月" + date[2] + "日"*/);
            }
        });

        calendarView.setOnItemClickListener(new OnMonthItemClickListener() {
            @Override
            public void onMonthItemClick(View view, DateBean date) {
                title.setText(date.getSolar()[0] + "." + date.getSolar()[1]/* + "月" + date.getSolar()[2] + "日"*/);
                getActivity().finish();
            }
        });

    }


    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("查找聊天记录");

    }

}
