package com.fenazola.mxcome.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.msg.TalkListFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.widget.NoScrollViewPager;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.indicator.UnderlinePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息
 */
public class MsgFragment extends BaseFragment {

    private NoScrollViewPager mViewPager;
    private UnderlinePageIndicator mPageIndicator;
    private List<Fragment> mList = new ArrayList<Fragment>();
    private RadioGroup mRadioGroup;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg;
    }

    @Override
    public void initView() {
        super.initView();
        mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        mPageIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
        mPageIndicator.setHorizontalScrollBarEnabled(false);
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);

        TalkListFragment fragment1 = new TalkListFragment();
        Bundle args1 = new Bundle();
        args1.putString(Constant.key1, "1");
        fragment1.setArguments(args1);
        mList.add(fragment1);

        TalkListFragment fragment2 = new TalkListFragment();
        Bundle args2 = new Bundle();
        args2.putString(Constant.key1, "2");
        fragment2.setArguments(args2);
        mList.add(fragment2);

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
                if(position == 0){
                    mRadioGroup.check(R.id.radio1);
                }else if(position == 1){
                    mRadioGroup.check(R.id.radio2);
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
                if(checkedId == R.id.radio1){
                    mViewPager.setCurrentItem(0);
                }else if(checkedId == R.id.radio2){
                    mViewPager.setCurrentItem(1);
                }
            }
        });
    }

}
