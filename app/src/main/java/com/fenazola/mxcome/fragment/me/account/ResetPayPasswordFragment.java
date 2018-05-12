package com.fenazola.mxcome.fragment.me.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.mepay.AccountProblemFragment;
import com.fenazola.mxcome.fragment.me.mepay.MyPayDialog;
import com.fenazola.mxcome.fragment.me.safe.FaqSafeFragment;
import com.fenazola.mxcome.widget.MyGridView;
import com.fenazola.mxcome.widget.MyListView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * Created by xuhuixiang on 2017/8/10.
 * 重置支付密码
 */

public class ResetPayPasswordFragment extends BaseFragment implements View.OnClickListener{
    TextView faqTv;
    private ListView listView;
    private CommonAdapter<String> mAdapter;
    ArrayList<String> names=new ArrayList<String>();
    TextView add_new_card;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_reset_pay_password;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(ListView) findViewById(R.id.show_lv);

        faqTv =(TextView)findViewById(R.id.show_faq_tv);
        add_new_card=(TextView)findViewById(R.id.add_new_card);
        faqTv.setOnClickListener(this);
        add_new_card.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        names.add("建设银行储蓄卡（8888）");
        names.add("招商银行储蓄卡（6666）");
        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_history_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                name.setText(workerListEntry);
                name.setTextColor(getColor(R.color.colorBlue));
            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("重置支付密码");
        toolbar.setBgColor(getResources().getColor(R.color.colorBlue));
        toolbar.setTitleColor(Color.WHITE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_new_card:
                Intent intent0= new Intent(getActivity(), ZFrameActivity.class);
                intent0.putExtra(ZFrameActivity.CLASS_NAME, ResetPaymentPasswordFragment.class.getName());
                startActivity(intent0);
                break;
            case R.id.show_faq_tv:
                Intent intent= new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqSafeFragment.class.getName());
                startActivity(intent);
                 break;
            case R.id.iv_1:
                MyPayDialog dialog = new MyPayDialog(getActivity(),"为了您的资金安全，只能绑定实名用户本人的银行卡",
                        "如需更多帮助，请致电MXCOME客服<font color='#4BB7FD'>400-888-888</font>","持卡人说明");
                dialog.show();
                break;
            case R.id.iv_2:
                MyPayDialog dialog1 = new MyPayDialog(getActivity(),"银行预留手机是你在办理该银行卡的时所填写的手机号。"
                        ,"没有预留、忘记手机号或已停用，可联系银行客服更新处理。","手机号说明");
                dialog1.show();
                break;
        }
    }
}
