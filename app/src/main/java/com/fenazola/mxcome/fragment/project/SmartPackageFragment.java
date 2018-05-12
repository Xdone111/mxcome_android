package com.fenazola.mxcome.fragment.project;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.DataCache;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.indicator.UnderlinePageIndicator;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/5/3.
 */

public class SmartPackageFragment extends BaseFragment implements View.OnClickListener{
    private ViewPager mViewPager;
    private UnderlinePageIndicator mPageIndicator;
    private List<Fragment> mList = new ArrayList<Fragment>();
    private RadioGroup mRadioGroup;
    private SmartHomeFurnishedFragment fragment1;
    private SmartApplianceFragment fragment2;
    private CommonToolbar toolbar;
    private String grade = DataCache.ENUM_PW_SHE;
    private RelativeLayout scheme_layout; //方案布局
    private RelativeLayout scheme_bg; //方案背景
    private TextView tv_center; //档次
    private TextView tv_she; //奢
    private TextView tv_hao; //豪
    private TextView tv_shu; //舒
    private TextView tv_jian; //简

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_smart_package;
    }

    @Override
    public void initView() {
        super.initView();
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mPageIndicator = (UnderlinePageIndicator) findViewById(R.id.indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        scheme_layout = (RelativeLayout) findViewById(R.id.scheme_layout);
        scheme_bg = (RelativeLayout) findViewById(R.id.scheme_bg);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_she = (TextView) findViewById(R.id.tv_she);
        tv_hao = (TextView) findViewById(R.id.tv_hao);
        tv_shu = (TextView) findViewById(R.id.tv_shu);
        tv_jian = (TextView) findViewById(R.id.tv_jian);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        scheme_layout.setOnClickListener(this);
        scheme_bg.setOnClickListener(this);
        tv_center.setOnClickListener(this);
        tv_she.setOnClickListener(this);
        tv_hao.setOnClickListener(this);
        tv_shu.setOnClickListener(this);
        tv_jian.setOnClickListener(this);

        fragment1 = new SmartHomeFurnishedFragment();
        mList.add(fragment1);

        fragment2 = new SmartApplianceFragment();
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
                if (position == 0) {
                    mRadioGroup.check(R.id.radio1);
                } else {
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
                if (checkedId == R.id.radio1) {
                    mViewPager.setCurrentItem(0);
                } else {
                    mViewPager.setCurrentItem(1);
                }
                if (mViewPager.getCurrentItem() == 0){
                    fragment1.setGradeCenterView(grade);
                } else {
                    fragment2.setGradeCenterView(grade);
                }
            }
        });
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black_b);
        toolbar = getToolbar();
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setTitleColor(getColor(R.color.black));
        toolbar.setTitle("智能包");
        toolbar.setRightTextBgColor(getColor(R.color.colorBlue));
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheme_bg.setVisibility(View.VISIBLE);
                scheme_layout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setShowRightView(boolean isShow){
        LogUtils.i("---zss---", "------ isShow = "+ isShow);
        toolbar.setRightShow(isShow);
        if(isShow){
            toolbar.setRightText(DataCache.dictMap.get(grade));
        }
    }

    public void setGradeText(String gradeText){
        grade = gradeText;
        toolbar.setRightText(DataCache.dictMap.get(grade));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scheme_layout: //背景阴影点击
                scheme_bg.setVisibility(View.GONE);
                scheme_layout.setVisibility(View.GONE);
                break;
            case R.id.tv_she:
                scheme_bg.setVisibility(View.GONE);
                scheme_layout.setVisibility(View.GONE);
                setGradeText(DataCache.ENUM_PW_SHE);
                if (mViewPager.getCurrentItem() == 0){
                    fragment1.setGradeCenterView(grade);
                } else {
                    fragment2.setGradeCenterView(grade);
                }
                break;
            case R.id.tv_hao:
                scheme_bg.setVisibility(View.GONE);
                scheme_layout.setVisibility(View.GONE);
                setGradeText(DataCache.ENUM_PW_HAO);
                if (mViewPager.getCurrentItem() == 0){
                    fragment1.setGradeCenterView(grade);
                } else {
                    fragment2.setGradeCenterView(grade);
                }
                break;
            case R.id.tv_shu:
                scheme_bg.setVisibility(View.GONE);
                scheme_layout.setVisibility(View.GONE);
                setGradeText(DataCache.ENUM_PW_SHU);
                if (mViewPager.getCurrentItem() == 0){
                    fragment1.setGradeCenterView(grade);
                } else {
                    fragment2.setGradeCenterView(grade);
                }
                break;
            case R.id.tv_jian:
                scheme_bg.setVisibility(View.GONE);
                scheme_layout.setVisibility(View.GONE);
                setGradeText(DataCache.ENUM_PW_JIAN);
                if (mViewPager.getCurrentItem() == 0){
                    fragment1.setGradeCenterView(grade);
                } else {
                    fragment2.setGradeCenterView(grade);
                }
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        if (mViewPager.getCurrentItem() == 0){
            return fragment1.onBackPressed();
        }
        return super.onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DataCache.brand10070001 = fragment1.getSelectBrand();
        DataCache.material10070001.clear();
        DataCache.material10070001.addAll(fragment1.getSelectMaterial());
        DataCache.material10070002.clear();
        DataCache.material10070002.addAll(fragment2.getSelectMaterial());
        Message msg = Message.obtain();
        msg.what = 2;
        EventBus.getDefault().post(msg);
        getActivity().finish();
    }

}
