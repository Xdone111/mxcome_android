package com.fenazola.mxcome.fragment.me;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.fragment.ransfer.EncycFragment;
import com.fenazola.mxcome.fragment.ransfer.MediaFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.indicator.UnderlinePageIndicator;
import com.zss.library.toolbar.CommonToolbar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠
 */
public class DiscountFragment extends BaseFragment {

    private ViewPager mViewPager;
    private UnderlinePageIndicator mPageIndicator;
    private List<Fragment> mList = new ArrayList<Fragment>();
    private RadioGroup mRadioGroup;
    private RadioButton rd1,rd2;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_discount;
    }

    @Override
    public void initView() {
        super.initView();
        mRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        mPageIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
        mPageIndicator.setHorizontalScrollBarEnabled(false);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        rd1=(RadioButton)findViewById(R.id.radio1);
        rd2=(RadioButton)findViewById(R.id.radio3);
        rd1.setTextColor(getColor(R.color.colorBlue));
    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        DiscountGoodsFragment fragment1 = new DiscountGoodsFragment();
        mList.add(fragment1);

        CouponFragment fragment3 = new CouponFragment();
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
                if(position == 0){
                    mRadioGroup.check(R.id.radio1);
                }

                else if(position == 1){
                    mRadioGroup.check(R.id.radio3);
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
                }
                else if(checkedId == R.id.radio3){
                    mViewPager.setCurrentItem(1);
                }
            }
        });
    }

    private void changTextColor(int index) {
        rd1.setTextColor(getColor(R.color.colorGrey));
        rd2.setTextColor(getColor(R.color.colorGrey));
        if(index==0){
            rd1.setTextColor(getColor(R.color.colorBlue));

        }else{
            rd2.setTextColor(getColor(R.color.colorBlue));

        }
    }


    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("最新优惠");
        toolbar.setTitleColor(getResources().getColor(R.color.black));
        toolbar.setBgColor(Color.WHITE);
        toolbar.setLeftImage(getResources().getDrawable(R.mipmap.icon_grey_back));
    }

}
