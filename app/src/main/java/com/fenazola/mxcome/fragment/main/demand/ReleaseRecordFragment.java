package com.fenazola.mxcome.fragment.main.demand;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.indicator.UnderlinePageIndicator;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/3/30.
 * 我的发布
 */

public class ReleaseRecordFragment extends BaseFragment {

    private ViewPager mViewPager;
    private UnderlinePageIndicator mPageIndicator;
    private List<Fragment> mList = new ArrayList<Fragment>();
    private RadioGroup mRadioGroup;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_release_record;
    }

    @Override
    public void initView() {
        super.initView();
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mPageIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
        mPageIndicator.setHorizontalScrollBarEnabled(false);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        CurrentDemandFragment fragment1 = new CurrentDemandFragment();
        mList.add(fragment1);

        CurrentDemandFragment fragment2 = new CurrentDemandFragment();
        mList.add(fragment2);

        CurrentDemandFragment fragment3 = new CurrentDemandFragment();
        mList.add(fragment3);

        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

        });
        mPageIndicator.setViewPager(mViewPager);
        mPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mList.get(position).setUserVisibleHint(true);
                if (position == 0) {
                    mRadioGroup.check(R.id.radio1);
                } else if (position == 1) {
                    mRadioGroup.check(R.id.radio2);
                } else if (position == 2) {
                    mRadioGroup.check(R.id.radio3);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio1) {
                    mViewPager.setCurrentItem(0);
                } else if (checkedId == R.id.radio2) {
                    mViewPager.setCurrentItem(1);
                } else if (checkedId == R.id.radio3) {
                    mViewPager.setCurrentItem(2);
                }
            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("我的发布");
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }
}
