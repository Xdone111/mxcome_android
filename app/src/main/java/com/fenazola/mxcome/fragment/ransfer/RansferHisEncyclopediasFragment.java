package com.fenazola.mxcome.fragment.ransfer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.WorkerListEntry;
import com.fenazola.mxcome.fragment.project.DesignerCommentFragment;
import com.fenazola.mxcome.fragment.project.DesignerCommentWorkerFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.widget.MyListView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.https.OkHttpUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/6/17.
 * 足迹
 */

public class RansferHisEncyclopediasFragment extends BaseFragment {
    public static final int TYPE_ENC = 1;
    public static final int TYPE_VOIDE = 2;
    private MaterialCalendarView calendarView;
    List<Date> markDates;
    private CommonAdapter<String> mAdapter;
    private MyListView listView;
    private int type;
    private TextView toastTv;
    ArrayList<String> names = new ArrayList<String>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ransfer_history_enc;
    }

    @Override
    public void initView() {
        super.initView();
        //在代码中
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        listView = (MyListView) findViewById(R.id.listView);
        toastTv = (TextView) findViewById(R.id.toast_tv);
        LogUtils.i("XHX", "执行了");
        type = getArguments().getInt("type", TYPE_ENC);
        LogUtils.i("XHX", "执行了1111111");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initDates();

    }

    private void initDates() {
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
        //设置标注日期
        markDates = new ArrayList<Date>();
        markDates.add(new Date());

        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_history_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                name.setText(workerListEntry);
            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
        calendarView.setCurrentDate(Calendar.getInstance());
        //getData(calendarView.getCurrentDate());
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

}
