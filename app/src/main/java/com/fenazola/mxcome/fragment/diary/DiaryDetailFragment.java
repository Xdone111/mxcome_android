package com.fenazola.mxcome.fragment.diary;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.DiaryReadEntry;
import com.fenazola.mxcome.widget.Panel;
import com.fenazola.mxcome.widget.RadioGroupLayout;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by XDONE on 2017/4/1.
 * 日记详情
 */

public class DiaryDetailFragment extends BaseFragment  {

    private ImageView photo;
    private TextView location;
    private TextView name;
    private TextView houseInfo;
    private RadioGroupLayout radioLayout;
    private TextView content;
    private TextView comment;
    private TextView collect;
    private ImageView image1, image2;
    private Panel leftPanel;
    private View detail_bg;
    private ListView listView;
    private CommonAdapter<DiaryReadEntry> adapter;
    private int pos = -1;
    private View panelHandle2;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_diary_detail;
    }

    @Override
    public void initView() {
        super.initView();
        photo = (ImageView) findViewById(R.id.imageView);
        location = (TextView) findViewById(R.id.tv_location);
        name = (TextView) findViewById(R.id.tv_name);
        houseInfo = (TextView) findViewById(R.id.hourse_info);
        radioLayout = (RadioGroupLayout) findViewById(R.id.radio_layout1);
        content = (TextView) findViewById(R.id.diary_content);
        comment = (TextView) findViewById(R.id.comment);
        collect = (TextView) findViewById(R.id.collect);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        leftPanel = (Panel) findViewById(R.id.leftPanel1);
        detail_bg = findViewById(R.id.detail_bg);
        detail_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftPanel.setOpen(false, true);
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        panelHandle2 = findViewById(R.id.panelHandle2);
        panelHandle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftPanel.setOpen(false, true);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        leftPanel.setOnPanelListener(new Panel.OnPanelListener() {
            @Override
            public void onPanelClosed(Panel panel) {
                detail_bg.setVisibility(View.GONE);
                panelHandle2.setVisibility(View.GONE);
            }

            @Override
            public void onPanelOpened(Panel panel) {
                detail_bg.setVisibility(View.VISIBLE);
                panelHandle2.setVisibility(View.VISIBLE);
            }
        });

        adapter = new CommonAdapter<DiaryReadEntry>(getActivity(), R.layout.listitem_diary_left) {
            @Override
            protected void convert(ViewHolder viewHolder, DiaryReadEntry diaryReadEntry, int i) {
                TextView diaryTitle = viewHolder.findViewById(R.id.diary_title);
                TextView diaryNum = viewHolder.findViewById(R.id.diary_num);
                LinearLayout ll_bg = viewHolder.findViewById(R.id.ll_bg);
                LinearLayout left_bg = viewHolder.findViewById(R.id.left_bg);
                diaryTitle.setText(diaryReadEntry.getTitle());
                diaryNum.setText(diaryReadEntry.getDiaryNum());
                if (pos == i) {
                    ll_bg.setBackgroundColor(getColor(R.color.topic_bg_color1));
                    diaryTitle.setTextColor(getColor(R.color.colorBlue));
                    diaryNum.setTextColor(getColor(R.color.colorBlue));
                    left_bg.setBackgroundColor(getColor(R.color.colorBlue));
                } else {
                    ll_bg.setBackgroundColor(getColor(R.color.white));
                    diaryTitle.setTextColor(getColor(R.color.colorGrey));
                    diaryNum.setTextColor(getColor(R.color.colorGrey));
                    left_bg.setBackgroundColor(getColor(R.color.white));
                }
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                adapter.notifyDataSetChanged();
                leftPanel.setOpen(false, true);
            }
        });
        listView.setAdapter(adapter);
        ArrayList<DiaryReadEntry> datas = new ArrayList<>();
        DiaryReadEntry entry = new DiaryReadEntry();
        entry.setTitle("开工准备");
        entry.setDiaryNum("（3篇）");
        for (int i = 0; i < 8; i++) {
            datas.add(entry);
        }
        adapter.addAll(datas);

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black);
        CommonToolbar toolbar = getToolbar();
        View view = getLayoutInflater(R.layout.layout_tab_diary);
        ImageView collect = (ImageView) view.findViewById(R.id.iv_collect);
        ImageView share = (ImageView) view.findViewById(R.id.iv_share);
        toolbar.setRightView(view);
        toolbar.setBgColor(getColor(R.color.diary_toolbar_bg));
        toolbar.setTitle("日记详情");
        toolbar.setTitleColor(getColor(R.color.white));
    }


}
