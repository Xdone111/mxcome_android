package com.fenazola.mxcome.fragment.diary;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.DiaryEntry;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/4/1.
 * 装修日记
 */

public class DecorateDiaryFragment extends BaseFragment implements View.OnClickListener {

    private ListView listView;
    private CommonAdapter<DiaryEntry> adapter;
    private TextView tvNew;
    private TextView tvHot;
    private ImageView ivAdd;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_decorate_diary;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
        tvNew = (TextView) findViewById(R.id.tv_new);
        tvHot = (TextView) findViewById(R.id.tv_hot);
        ivAdd = (ImageView) findViewById(R.id.iv_add);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        ivAdd.setOnClickListener(this);

        adapter = new CommonAdapter<DiaryEntry>(getActivity(), R.layout.listitem_diary) {
            @Override
            protected void convert(ViewHolder viewHolder, DiaryEntry diaryEntry, int i) {

                ImageView photo = viewHolder.findViewById(R.id.diary_photo);
                ImageView share = viewHolder.findViewById(R.id.iv_share);
                ImageView collect = viewHolder.findViewById(R.id.iv_collect);
                TextView comment = viewHolder.findViewById(R.id.tv_comment);
                TextView info = viewHolder.findViewById(R.id.tv_info);
                TextView location = viewHolder.findViewById(R.id.tv_location);
                TextView look = viewHolder.findViewById(R.id.tv_look);

                comment.setText(diaryEntry.getComment());
                info.setText(diaryEntry.getInfo());
                location.setText(diaryEntry.getLocation());
                look.setText(diaryEntry.getLook());
            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DiaryDetailFragment fragment = new DiaryDetailFragment();
                addFragment(fragment);

            }
        });
        List<DiaryEntry> datas = new ArrayList<>();
        DiaryEntry item = new DiaryEntry();
        item.setInfo("20万/140m²/三居/北欧");
        item.setComment("450");
        item.setLocation("南方时代宜家风格");
        item.setLook("24500");
        datas.add(item);
        datas.add(item);
        datas.add(item);
        datas.add(item);
        adapter.addAll(datas);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_new:

                break;
            case R.id.tv_hot:

                break;
            case R.id.iv_add:
                InfoEditFragment fragment1 = new InfoEditFragment();
                addFragment(fragment1);
                break;
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setTitle("装修日记");
        toolbar.setTitleColor(getColor(R.color.black));
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
    }
}
