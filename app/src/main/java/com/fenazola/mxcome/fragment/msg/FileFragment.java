package com.fenazola.mxcome.fragment.msg;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.CouponFragment;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.indicator.UnderlinePageIndicator;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠
 */
public class FileFragment extends BaseFragment {

    private ViewPager mViewPager;
    private UnderlinePageIndicator mPageIndicator;
    private List<Fragment> mList = new ArrayList<Fragment>();
    private RadioGroup mRadioGroup;
    private RadioButton rd1,rd2,rd3,rd4;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_file;
    }

    @Override
    public void initView() {
        super.initView();
        mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        mPageIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
        mPageIndicator.setHorizontalScrollBarEnabled(false);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        rd1=(RadioButton)findViewById(R.id.radio1);
        rd2=(RadioButton)findViewById(R.id.radio2);
        rd3=(RadioButton)findViewById(R.id.radio3);
        rd4=(RadioButton)findViewById(R.id.radio4);
        rd1.setTextColor(getColor(R.color.colorBlue));
    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);

        FileInfoFragment fragment1 = new FileInfoFragment();
        mList.add(fragment1);
        FileInfoFragment fragment2 = new FileInfoFragment();

        mList.add(fragment2);
        FileInfoFragment fragment3 = new FileInfoFragment();

        mList.add(fragment3);
        FileInfoFragment fragment4 = new FileInfoFragment();

        mList.add(fragment4);

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
                }else if(position == 2){
                    mRadioGroup.check(R.id.radio3);
                }else if(position == 3){
                    mRadioGroup.check(R.id.radio4);
                }
                changTextColor(position);
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
                }else if(checkedId == R.id.radio3){
                    mViewPager.setCurrentItem(2);
                }else if(checkedId == R.id.radio4){
                    mViewPager.setCurrentItem(3);
                }
            }
        });
    }

    private void changTextColor(int index) {
        rd1.setTextColor(getColor(R.color.colorGrey));
        rd2.setTextColor(getColor(R.color.colorGrey));
        rd3.setTextColor(getColor(R.color.colorGrey));
        rd4.setTextColor(getColor(R.color.colorGrey));
        if(index==0){
            rd1.setTextColor(getColor(R.color.colorBlue));
        }else if(index==1){
            rd2.setTextColor(getColor(R.color.colorBlue));

        }else if(index==2){
            rd3.setTextColor(getColor(R.color.colorBlue));

        }else if(index==3){
            rd4.setTextColor(getColor(R.color.colorBlue));

        }
    }


    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("聊天文件");
        toolbar.setTitleColor(getResources().getColor(R.color.black));
        toolbar.setBgColor(Color.WHITE);
        toolbar.setLeftImage(getResources().getDrawable(R.mipmap.icon_grey_back));
        toolbar.setRightText("选择");
        toolbar.setRightTextColor(getColor(R.color.colorBlue));
    }

}
