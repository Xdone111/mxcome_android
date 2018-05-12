package com.fenazola.mxcome.fragment.me.loan;

import android.os.Bundle;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.DPUtils;
import com.zss.library.viewpager.AdViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/4/12.
 * 消费贷款
 */

public class ConsumeFragment extends BaseFragment {
    private AdViewPager ad_viewpage;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_loan_consume;
    }

    @Override
    public void initView() {
        super.initView();
        ad_viewpage = (AdViewPager) findViewById(R.id.ad_viewpage);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.loan_adv);
        list.add(R.mipmap.loan_adv);
        list.add(R.mipmap.loan_adv);
        ad_viewpage.setLocalResIds(list);
        ad_viewpage.setHeight(DPUtils.dip2px(getActivity(), 169));
    }

}
