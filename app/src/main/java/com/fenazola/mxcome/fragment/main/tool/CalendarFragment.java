package com.fenazola.mxcome.fragment.main.tool;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.othershe.calendarview.CalendarView;
import com.othershe.calendarview.DateBean;
import com.othershe.calendarview.listener.OnMonthItemClickListener;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.https.OkHttpUtils;
import com.zss.library.toolbar.CommonToolbar;

import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by zm on 2017/5/17.
 */

public class CalendarFragment extends BaseFragment {

    private TextView lunar_month;
    private TextView lunar_year;
    private RatingBar ratingBar;
    private TextView lunar_should;
    private TextView lunar_bogey;
    private CalendarView calendarView;

    private  TextView title;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_calendar;
    }

    @Override
    public void initView() {
        super.initView();
        lunar_month = (TextView) findViewById(R.id.lunar_month);
        lunar_year = (TextView) findViewById(R.id.lunar_year);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        lunar_should = (TextView) findViewById(R.id.lunar_should);
        lunar_bogey = (TextView) findViewById(R.id.lunar_bogey);
        title = (TextView) findViewById(R.id.title);
        calendarView = (CalendarView) findViewById(R.id.calendar);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        calendarView.init();

        DateBean d = calendarView.getDateInit();

        title.setText(d.getSolar()[0] + "." + d.getSolar()[1]/** + "月" + d.getSolar()[2] + "日"*/);
        getData(d.getSolar()[0] + "-" + d.getSolar()[1]+"-"+d.getSolar()[2]);

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
                getData(date.getSolar()[0] + "-" + date.getSolar()[1]+"-"+date.getSolar()[2]);

            }
        });
    }

    public void getData(String date) {

        String url = String.format(Constant.juheUrl, date);
        OkHttpUtils.get(url, new OkHttpUtils.ICallback() {
            @Override
            public void onSuccess(String str) {
                refreshUI(str);
            }

            @Override
            public void onError(String str) {

            }
        });
    }

    public void refreshUI(final String str) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject obj = new JSONObject(str);
                    String error_code = obj.optString("error_code");
                    if ("0".equals(error_code)) {
                        JSONObject result = obj.optJSONObject("result");
                        JSONObject data = result.getJSONObject("data");
                        if (data != null) {
                            lunar_month.setText(data.optString("lunar"));
                            lunar_year.setText(data.optString("lunarYear"));
                            lunar_should.setText(data.optString("suit"));
                            lunar_should.setSelected(true);
                            lunar_bogey.setText(data.optString("avoid"));
                            lunar_bogey.setSelected(true);
                        }
                    }
                } catch (Exception e) {
                }
            }
        });

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setRightImage(R.mipmap.weather_icon);
        toolbar.setTitle("黄历");
    }

}
