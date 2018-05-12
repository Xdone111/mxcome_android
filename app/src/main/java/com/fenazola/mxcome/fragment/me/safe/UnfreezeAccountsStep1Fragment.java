package com.fenazola.mxcome.fragment.me.safe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * Created by xuhuixiang on 2017/7/27.
 * 冻结 or解冻账号
 */

public class UnfreezeAccountsStep1Fragment extends BaseFragment {
    private ImageView bigIv;
    private int type;
    private TextView tv1,tv2,to_next;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_un_accounts_step1;
    }
    @Override
    public void initView() {
        super.initView();
        bigIv=(ImageView)findViewById(R.id.big_img) ;
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        to_next=(TextView)findViewById(R.id.to_next);



    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        type=getArguments().getInt(Constant.key1);

        if(type==UnfreezeAccountsFragment.IN_TYPE_UNFREE) {
            bigIv.setImageResource(R.mipmap.dongjie_bg);
            tv1.setText("发现账号被盗后，你可以冻结MXCOME账号");
            tv2.setText("防止坏人窃取您的个人隐私\n防止坏人冒用您的身份诈骗好友");
            to_next.setText("开始冻结");
        }else{
            bigIv.setImageResource(R.mipmap.jiedong_bg);
            tv1.setText("解除安全风险后，你可以解冻MXCOME账号");
            tv2.setText("排查手机没有被植入木马\n不要轻易泄露个人信息\n请尽量不要使用公共空间提供的直连WIFI");
            to_next.setText("开始解冻");

        }
        findViewById(R.id.to_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, UnfreezeAccountsFragment.class.getName());
                intent.putExtra(Constant.key1,type);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);
        CommonToolbar toolbar = getToolbar();
        if(type==UnfreezeAccountsFragment.IN_TYPE_UNFREE) {
            toolbar.setTitle("冻结账号");
        }else{
            toolbar.setTitle("解冻账号");

        }
        toolbar.setTitleColor(getColor(R.color.black));
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
    }
}
