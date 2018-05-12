package com.fenazola.mxcome.fragment.me.mepay;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.FundDetailsEntry;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * Created by xuhuixiang on 2017/7/27.
 * 账户问题
 */

public class AccountProblemFragment extends BaseFragment {
    private ListView listView;
    private View contentView;
    private CommonAdapter<String> mAdapter;
    private ArrayList<String> names=new ArrayList<String>();
    @Override
    public int getLayoutResId() {
        return R.layout.fund_details_fragment;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(ListView) findViewById(R.id.show_lv);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //TODO
        initDateList();
        //names=names1;
        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_history_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                name.setText(workerListEntry);


            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);


    }
    //模拟数据

    private void initDateList() {
        names.add("如何找回MXCOME登录密码？");
        names.add("如何修改支付密码？");
        names.add("疑似被盗，怎么紧急冻结账号？");
        names.add("账户绑定他人银行卡，如何解绑及重新绑定？");
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("账户问题");

    }
}
