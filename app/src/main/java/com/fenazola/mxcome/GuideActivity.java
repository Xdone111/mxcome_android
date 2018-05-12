package com.fenazola.mxcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.adapter.GuidePagerAdapter;
import com.fenazola.mxcome.fragment.main.FreeDecorateFragment;
import com.fenazola.mxcome.utils.Utils;
import com.zss.library.activity.BaseActivity;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.indicator.CirclePageIndicator;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.SharedPrefUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 新功能引导界面
 */
public class GuideActivity extends BaseActivity {

    private ViewPager viewPager;

    private GuidePagerAdapter mAdapter;
    private CirclePageIndicator mIndicator;
    private int[] array = {R.mipmap.guide_011, R.mipmap.guide_02, R.mipmap.guide_03};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.GONE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        super.initView();
        viewPager = (ViewPager) findViewById(R.id.pager);
        List<ViewGroup> views = new ArrayList<ViewGroup>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                ViewGroup view = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_guide_last, null);
                view.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setGuideStatus();
                        Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                        intent.putExtra(ZFrameActivity.CLASS_NAME, FreeDecorateFragment.class.getName());
                        startActivity(intent);
                        finish();
                    }
                });
                TextView btn = (TextView) view.findViewById(R.id.btn2);
                if (Utils.isLogin()) {
                    btn.setText("立即装修");
                } else {
                    btn.setText("注册登录");
                }
                view.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setGuideStatus();
                        Utils.startNewProject(getActivity());
                    }
                });
                view.findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setGuideStatus();
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        startActivity(intent);
//                        finish();
                        Toast.makeText(getActivity(), "正在开发中", Toast.LENGTH_SHORT).show();
                    }
                });
                view.setLayoutParams(mParams);
                view.setBackgroundResource(array[i]);
                views.add(view);
            } else {
                ViewGroup view = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_guide_item, null);
                view.setLayoutParams(mParams);
                view.setBackgroundResource(array[i]);
                views.add(view);
            }

        }
        mAdapter = new GuidePagerAdapter(views);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mIndicator.setVisibility(View.VISIBLE);
                    mIndicator.setFillColor(getColor(getActivity(), R.color.colorBlue));
                } else if (position == 1) {
                    mIndicator.setVisibility(View.VISIBLE);
                    mIndicator.setFillColor(getColor(getActivity(), R.color.white));
                } else {
                    mIndicator.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    public void onBackPressed() {

    }

    public void setGuideStatus() {
        SharedPrefUtils prefUtils = new SharedPrefUtils(getActivity(), "common_file");
        prefUtils.put("GuideInit", true);
    }

}

