package com.fenazola.mxcome.fragment.msg;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuhuixiang on 2017/3/15.
 * 特别关注
 */

public class FriendDpecialAttentionFragment extends BaseFragment implements View.OnClickListener{
    /**是否编辑状态*/
    private boolean isEdit=false;
    ListView cityList;
    private CommonAdapter<String> mAdapter;
    private List<String> names=new ArrayList<String>();
    private LinearLayout showNoMenLy;
    private RelativeLayout show_add_tv,show_add_tv1;
    CommonToolbar toolbar;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_special_attention;
    }

    @Override
    public void initView() {
        super.initView();
        cityList= (ListView) findViewById(R.id.show_lv);
        showNoMenLy=(LinearLayout)findViewById(R.id.show_no_men);
        show_add_tv=(RelativeLayout)findViewById(R.id.show_add_tv);
        show_add_tv1=(RelativeLayout)findViewById(R.id.show_add_tv1);

        show_add_tv.setOnClickListener(this);


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        getDate();

        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_item_list_item_special_attention) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, final int i) {
                TextView city_tv = (TextView) viewHolder.findViewById(R.id.city_list_tv);
                TextView city_tv1 = (TextView) viewHolder.findViewById(R.id.city_list_tv1);
                TextView city_tv2 = (TextView) viewHolder.findViewById(R.id.city_list_tv2);

                CircleImageView phone=(CircleImageView)viewHolder.findViewById(R.id.photo);
                ImageView iv=(ImageView)viewHolder.findViewById(R.id.list_iv);
                ImageView iv1=(ImageView)viewHolder.findViewById(R.id.list_iv_right);
                //city_tv.setTextColor(getColor(R.color.colorGrey));
                View v=viewHolder.findViewById(R.id.line);
                city_tv.setText(workerListEntry);

                if(i%4==0){
                    iv1.setImageResource(R.mipmap.women_attention_img);
                }else if(i%4==1){
                    iv1.setImageResource(R.mipmap.men_attention_img);
                }else if(i%4==2){
                    iv1.setImageResource(R.mipmap.copy_attention);
                }else {
                    iv1.setImageResource(R.mipmap.f_and_m_attention);
                }
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),"是不是想删除我呀？省省吧！…",Toast.LENGTH_SHORT).show();
                    }
                });
                if(i==names.size()-1){
                    v.setVisibility(View.INVISIBLE);
                }else{
                    v.setVisibility(View.VISIBLE);

                }
                if(isEdit){
                    iv.setVisibility(View.VISIBLE);
                    iv1.setImageResource(R.mipmap.msg_check_detail);
                    city_tv1.setVisibility(View.GONE);
                    city_tv2.setVisibility(View.GONE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(v.getLayoutParams());
                    lp.setMargins(270, 0, 0, 0);
                    v.setLayoutParams(lp);
                }else{
                    iv.setVisibility(View.GONE);
                    //iv1.setVisibility(View.VISIBLE);
                    city_tv1.setVisibility(View.VISIBLE);
                    city_tv2.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(v.getLayoutParams());
                    lp.setMargins(190, 0, 0, 0);
                    v.setLayoutParams(lp);
                }
            }
        };
        cityList.setAdapter(mAdapter);
        mAdapter.addAll(names);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

    }

    private void getDate() {
        names.add("李伟");
        names.add("张三");
        names.add("阿三");
        names.add("阿四");
        names.add("段誉");
        names.add("段正淳");
        names.add("张三丰");
        names.add("陈坤");
        names.add("林俊杰1");
        names.add("陈坤2");
        names.add("王二a");
        names.add("林俊杰a");
        names.add("张四");
        names.add("林俊杰");
        names.add("王二");
        names.add("王二b");
        names.add("赵四");
        names.add("杨坤");
        names.add("赵子龙");
        names.add("杨坤1");
        names.add("李伟1");
        names.add("宋江");
        names.add("宋江1");
        names.add("李伟3");
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        toolbar = getToolbar();
        toolbar.setTitle("特别关注");
        toolbar.setRightText("编辑");
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEdit=!isEdit;
                if(isEdit==false){
                    toolbar.setRightText("编辑");
                    show_add_tv.setVisibility(View.VISIBLE);
                    show_add_tv1.setVisibility(View.GONE);
                }else{
                    toolbar.setRightText("完成");
                    show_add_tv.setVisibility(View.GONE);
                    show_add_tv1.setVisibility(View.VISIBLE);

                }
                mAdapter.notifyDataSetChanged();
            }
        });
        toolbar.setRightTextColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_add_tv:
                if(cityList.getVisibility()==View.VISIBLE){
                    cityList.setVisibility(View.GONE);
                    showNoMenLy.setVisibility(View.VISIBLE);
                    toolbar.getRightView().setVisibility(View.INVISIBLE);
                    show_add_tv.setVisibility(View.VISIBLE);
                    show_add_tv1.setVisibility(View.GONE);
                }else{
                    cityList.setVisibility(View.VISIBLE);
                    showNoMenLy.setVisibility(View.GONE);
                    toolbar.getRightView().setVisibility(View.VISIBLE);

                    if(isEdit==false){
                        toolbar.setRightText("编辑");
                        show_add_tv.setVisibility(View.VISIBLE);
                        show_add_tv1.setVisibility(View.GONE);
                    }else{
                        toolbar.setRightText("完成");
                        show_add_tv.setVisibility(View.GONE);
                        show_add_tv1.setVisibility(View.VISIBLE);

                    }
                    mAdapter.notifyDataSetChanged();
                }
                break;

        }
    }

}
