package com.fenazola.mxcome.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.ransfer.EncycFragment;
import com.fenazola.mxcome.fragment.ransfer.InformationFragment;
import com.fenazola.mxcome.fragment.ransfer.MediaFragment;
import com.fenazola.mxcome.fragment.ransfer.TopicFragment;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.indicator.UnderlinePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 传递
 * //TODO 到时候需要追加的时候 记得把影藏得两个模块加上
 */
public class RansferFragment extends BaseFragment {

    private ViewPager mViewPager;
    private UnderlinePageIndicator mPageIndicator;
    private List<Fragment> mList = new ArrayList<Fragment>();
    private RadioGroup mRadioGroup;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ransfer;
    }

    @Override
    public void initView() {
        super.initView();
        mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        mPageIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
        mPageIndicator.setHorizontalScrollBarEnabled(false);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        //TODO 已注释两个
        EncycFragment fragment1 = new EncycFragment();
        mList.add(fragment1);

//        TopicFragment fragment2 = new TopicFragment();
//        mList.add(fragment2);

        MediaFragment fragment3 = new MediaFragment();
        mList.add(fragment3);

//        InformationFragment fragment4 = new InformationFragment();
//        mList.add(fragment4);
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
                }
//                else if(position == 1){
//                    mRadioGroup.check(R.id.radio2);
//                }
                else if(position == 1){
                    mRadioGroup.check(R.id.radio3);
                }
//                else if(position == 3){
//                    mRadioGroup.check(R.id.radio4);
//                }
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
                }
//                else if(checkedId == R.id.radio2){
//                    mViewPager.setCurrentItem(1);
//                }
                else if(checkedId == R.id.radio3){
                    mViewPager.setCurrentItem(1);
                }
//                else if(checkedId == R.id.radio4){
//                    mViewPager.setCurrentItem(3);
//                }
            }
        });
    }


}
