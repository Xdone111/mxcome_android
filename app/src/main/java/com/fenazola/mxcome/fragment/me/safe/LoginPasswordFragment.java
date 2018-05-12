package com.fenazola.mxcome.fragment.me.safe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.account.ResetPaymentPasswordFragment;
import com.fenazola.mxcome.fragment.me.mepay.MyPayDialog;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
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

public class LoginPasswordFragment extends BaseFragment implements View.OnClickListener{
    private ListView listView;
    private CommonAdapter<Bean> mAdapter;
    ArrayList<Bean> names=new ArrayList<Bean>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_login_password;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(ListView) findViewById(R.id.show_lv);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        names.add(new Bean(R.mipmap.bind_phone,"已绑定手机号","可通过手机号+短信验证码修改或重置密码"));
        names.add(new Bean(R.mipmap.bind_email,"已绑定邮箱","可通过邮箱地址修改或重置密码"));

        mAdapter = new CommonAdapter<Bean>(getActivity(), R.layout.layout_item_list_login_pass) {
            @Override
            protected void convert(ViewHolder viewHolder, final Bean workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.city_list_tv);
                TextView name1 = viewHolder.findViewById(R.id.city_list_tv1);
                CircleImageView phone=viewHolder.findViewById(R.id.photo);
                View v=viewHolder.findViewById(R.id.show_line);
                name.setText(workerListEntry.title);
                name1.setText(workerListEntry.des);
                phone.setImageResource(workerListEntry.iv);
                if(i==names.size()-1){
                   v.setVisibility(View.INVISIBLE);
                }else{
                    v.setVisibility(View.VISIBLE);

                }


            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("登录密码");
        toolbar.setBgColor(getResources().getColor(R.color.white));
        toolbar.setTitleColor(Color.BLACK);
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
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
    class Bean{
        int iv;
        String title;
        String des;

        public Bean() {
        }

        public Bean(int iv, String title, String des) {
            this.iv = iv;
            this.title = title;
            this.des = des;
        }
    }
}
