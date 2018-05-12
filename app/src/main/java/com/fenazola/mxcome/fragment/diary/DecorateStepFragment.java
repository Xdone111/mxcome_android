package com.fenazola.mxcome.fragment.diary;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.DecorateStepEntry;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/7/29.
 */

public class DecorateStepFragment extends BaseFragment {

    private GridView gridView;
    private int[] mIcons1 = new int[]{R.mipmap.decorate_grey_img1, R.mipmap.decorate_grey_img2, R.mipmap.decorate_grey_img3,
            R.mipmap.decorate_grey_img4, R.mipmap.decorate_grey_img5, R.mipmap.decorate_grey_img6,
            R.mipmap.decorate_grey_img7, R.mipmap.decorate_grey_img8};
    private int[] mIcons2 = new int[]{R.mipmap.decorate_blue_img1, R.mipmap.decorate_blue_img2, R.mipmap.decorate_blue_img3,
            R.mipmap.decorate_blue_img4, R.mipmap.decorate_blue_img5, R.mipmap.decorate_blue_img6,
            R.mipmap.decorate_blue_img7, R.mipmap.decorate_blue_img8};
    private int[] mTitles = new int[]{R.string.decorate_text1, R.string.decorate_text2,
            R.string.decorate_text3, R.string.decorate_text4, R.string.decorate_text5, R.string.decorate_text6,
            R.string.decorate_text7, R.string.decorate_text8};
    private CommonAdapter<DecorateStepEntry> adapter;
    private List<DecorateStepEntry> datas;
    private int pos = 1;
    private DecorateStepEntry item;
    private TextView tvConfirm;
    private TextView tvType;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_decorate_step;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.white);
        gridView = (GridView) findViewById(R.id.gridView);
        tvType = (TextView) findViewById(R.id.tv_type);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        adapter = new CommonAdapter<DecorateStepEntry>(getActivity(), R.layout.gridview_decorate_step) {
            @Override
            protected void convert(ViewHolder viewHolder, DecorateStepEntry itemEntry, int i) {
                ImageView icon = viewHolder.findViewById(R.id.icon);
                TextView title = viewHolder.findViewById(R.id.title);
                title.setText(itemEntry.getTitle());
                TextView tvBg = viewHolder.findViewById(R.id.tv_bg);
                icon.setBackgroundResource(itemEntry.getIcon());
                if (pos == i) {
                    title.setTextColor(getColor(R.color.colorBlue));
                    tvBg.setBackgroundResource(R.drawable.corner_shape_reset);
                    icon.setImageResource(mIcons2[i]);
                    tvType.setText(mTitles[i]);
                } else {
                    title.setTextColor(getColor(R.color.colorGrey));
                    tvBg.setBackgroundResource(R.drawable.corner_shape_grey);
                    icon.setImageResource(mIcons1[i]);
                }
            }
        };
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                adapter.notifyDataSetChanged();
            }
        });

        datas = new ArrayList<>();
        for (int i = 0; i < mIcons1.length; i++) {
            item = new DecorateStepEntry();
            item.setIcon(mIcons1[i]);
            item.setTitle(getString(mTitles[i]));
            datas.add(item);
        }
        adapter.addAll(datas);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setTitleColor(getColor(R.color.grey));
        toolbar.setTitle("装修阶段");
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
    }
}
