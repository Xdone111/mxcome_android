package com.fenazola.mxcome.fragment.msg;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.VisitorEntry;
import com.fenazola.mxcome.widget.CenterDialog;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.widget.CommonDialog;

import java.util.ArrayList;

/**
 * Created by XDONE on 2017/8/2.
 * 访客
 */

public class MsgVisitorFragment extends BaseFragment implements View.OnClickListener {

    private TextView TVtitle;
    private ImageView backIv;
    private ListView listView;
    private CommonAdapter<VisitorEntry> adapter;
    private ArrayList<VisitorEntry> list;
    private ImageView iv_clear;
    private LinearLayout no_visitor_img;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_visitor;
    }

    @Override
    public void initView() {
        super.initView();
        TVtitle = (TextView) findViewById(R.id.show_title_tv);
        backIv = (ImageView) findViewById(R.id.show_title_left);
        backIv.setOnClickListener(this);
        setBgColor(R.color.white);
        listView = (ListView) findViewById(R.id.listView);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        no_visitor_img = (LinearLayout) findViewById(R.id.no_visitor_img);
        iv_clear.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        TVtitle.setText("访客");
        adapter = new CommonAdapter<VisitorEntry>(getActivity(), R.layout.listitem_msg_visitor) {
            @Override
            protected void convert(ViewHolder viewHolder, VisitorEntry entry, int i) {
                TextView name = viewHolder.findViewById(R.id.name);
                TextView time = viewHolder.findViewById(R.id.time);
                name.setText(entry.getName());
                time.setText(entry.getTime());
                View line = viewHolder.findViewById(R.id.line);
                if (list.size() - 1 == i) {
                    line.setVisibility(View.INVISIBLE);
                }else{
                    line.setVisibility(View.VISIBLE);
                }
            }
        };
        listView.setAdapter(adapter);

        list = new ArrayList<>();
        VisitorEntry entry = new VisitorEntry();
        for (int i = 0; i < 5; i++) {
            entry.setName("奇迹王子");
            entry.setTime("今日");
            list.add(entry);
        }
        adapter.addAll(list);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.GONE);
        toolbar.setBgColor(Color.TRANSPARENT);
        toolbar.setTitleColor(Color.WHITE);
        toolbar.setLeftImage(R.mipmap.project_left);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_title_left:
                getActivity().onBackPressed();
                break;
            case R.id.iv_clear:
                if (list.size() > 0) {
                    final CommonDialog dialog = new CommonDialog(getActivity());
                    dialog.setTitle("清除当前所有访客记录");
                    dialog.setDisplayMiddleEnable(false);
                    dialog.setOnClickConfirmListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            list.clear();
                            listView.setVisibility(View.GONE);
                            no_visitor_img.setVisibility(View.VISIBLE);
                        }
                    });
                    dialog.setOnClickCancelListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {
                    CenterDialog dialog1 = new CenterDialog(getActivity());
                    dialog1.setContent("您已充分捍卫了隐私\n求别再点了！");
                    dialog1.show();
                }
                break;
        }
    }
}
