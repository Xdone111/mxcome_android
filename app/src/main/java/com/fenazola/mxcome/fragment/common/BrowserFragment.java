package com.fenazola.mxcome.fragment.common;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.adapter.BrowserPageAdapter;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.viewpager.BrowserPager;

import java.util.List;

/**
 * Created by zm on 2017/3/29.
 * 图片浏览页面
 */

public class BrowserFragment extends BaseFragment {

    private TextView hint;
    private BrowserPager viewPager;
    private BrowserPageAdapter adapter;
    /**预浏览图片的开始下标*/
    private int pos;
    /**图片地址列表*/
    private List<String> datas;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_browser;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.black);
        hint = (TextView)findViewById(R.id.hint);
        viewPager = (BrowserPager)findViewById(R.id.viewPager);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle args = getArguments();
        pos = args.getInt(Constant.key1);
        datas = args.getStringArrayList(Constant.key2);
        adapter = new BrowserPageAdapter(getActivity(), datas, BrowserPageAdapter.LoadType.URL);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
                hint.setText(pos + 1 + "/" + datas.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        hint.setText(pos + 1 + "/" + datas.size());
    }

}
