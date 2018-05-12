package com.fenazola.mxcome.fragment.ransfer;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.MainActivity;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.adapter.ImageBrowseAdapter;
import com.fenazola.mxcome.entry.ransfer.InformationEntry;
import com.fenazola.mxcome.widget.MyListView;
import com.othershe.calendarview.DateBean;
import com.othershe.calendarview.listener.OnMonthItemClickListener;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 传递历史记录
 *
 * @author xhx
 */
public class RansferHistoryFragment extends BaseFragment implements View.OnClickListener {
    /**
     * Fragment
     */
    public static final int TYPE_ENC = 1;
    public static final int TYPE_VOIDE = 2;
    List<Date> markDates;
    private CommonAdapter<String> mAdapter;
    private MyListView listView;
    private int type;
    private TextView toastTv;
    ArrayList<String> names = new ArrayList<String>();
    private TextView tv1, tv2, tv1_1, tv2_1;
    private ScrollView scrollView;
    private com.othershe.calendarview.CalendarView calendarView;

    private TextView title;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ransfer_history;
    }

    @Override
    public void initView() {
        super.initView();
        title = (TextView) findViewById(R.id.title);
        calendarView = (com.othershe.calendarview.CalendarView) findViewById(R.id.calendar);
        listView = (MyListView) findViewById(R.id.listView);
        toastTv = (TextView) findViewById(R.id.toast_tv);
        tv1 = (TextView) findViewById(R.id.radio1);
        tv2 = (TextView) findViewById(R.id.radio3);
        tv1_1 = (TextView) findViewById(R.id.radio1_1);
        tv2_1 = (TextView) findViewById(R.id.radio3_1);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        type = TYPE_ENC;
        //设置标注日期
        markDates = new ArrayList<Date>();
        markDates.add(new Date());
        names.clear();
        //TODO 模拟数据
        for (int i = 0; i < 11; i++) {
            if (type == TYPE_ENC)
                names.add("我是咨询" + i);
            else
                names.add("我是咨询我是咨询我是咨询" + i);
        }
        if (type == TYPE_ENC) {
            //toastTv.setText("你浏览了百科类"+names.size()+"内容");
            String cyclopediaStr = getString(R.string.cyclopedia, String.valueOf(names.size()));
            toastTv.setText(StringUtils.parse(cyclopediaStr, 7, cyclopediaStr.length() - 2, getColor(R.color.colorBlue)));
        } else {
            String cyclopediaStr = getString(R.string.media, String.valueOf(names.size()));
            toastTv.setText(StringUtils.parse(cyclopediaStr, 8, cyclopediaStr.length() - 3, getColor(R.color.colorBlue)));
        }
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

            }
        });
        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_history_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                name.setText(workerListEntry);
            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
        scrollView.post(new Runnable() {
            @Override
            public void run() {

                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    private void initChange() {
        if (type == TYPE_ENC) {
            tv1.setTextColor(getColor(R.color.colorBlue));
            tv1_1.setBackgroundResource(R.color.colorBlue);
            tv2.setTextColor(getColor(R.color.colorGrey));
            tv2_1.setBackgroundResource(R.color.white);
        } else {
            tv2.setTextColor(getColor(R.color.colorBlue));
            tv2_1.setBackgroundResource(R.color.colorBlue);
            tv1.setTextColor(getColor(R.color.colorGrey));
            tv1_1.setBackgroundResource(R.color.white);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio1:
                type = TYPE_ENC;
                initChange();
                initDates();
                break;
            case R.id.radio3:
                type = TYPE_VOIDE;
                initChange();
                initDates();
                break;
        }

    }

    private void initDates() {
        names.clear();
        //TODO 模拟数据
        for (int i = 0; i < 11; i++) {
            if (type == TYPE_ENC)
                names.add("我是咨询" + i);
            else
                names.add("我是咨询我是咨询我是咨询" + i);
        }
        if (type == TYPE_ENC) {
            //toastTv.setText("你浏览了百科类"+names.size()+"内容");
            String cyclopediaStr = getString(R.string.cyclopedia, String.valueOf(names.size()));
            toastTv.setText(StringUtils.parse(cyclopediaStr, 7, cyclopediaStr.length() - 2, getColor(R.color.colorBlue)));
        } else {
            String cyclopediaStr = getString(R.string.media, String.valueOf(names.size()));
            toastTv.setText(StringUtils.parse(cyclopediaStr, 8, cyclopediaStr.length() - 3, getColor(R.color.colorBlue)));
        }
        mAdapter.replaceAll(names);


    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("足 迹");

    }
}
