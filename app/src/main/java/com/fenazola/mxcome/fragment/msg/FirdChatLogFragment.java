package com.fenazola.mxcome.fragment.msg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.PersonInfoFragment;
import com.fenazola.mxcome.widget.CommonBDialog;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.widget.CommonDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuhuixiang on 2017/3/15.
 * 查找聊天记录
 */

public class FirdChatLogFragment extends BaseFragment implements View.OnClickListener{

    private EditText editText;
    ListView cityList;
    private CommonAdapter<String> mAdapter;
    private List<String> names=new ArrayList<String>();
    private List<Boolean> checks=new ArrayList<Boolean>();

    private boolean isDelete=false;
    private LinearLayout show_bot;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_frid_chat_log;
    }

    @Override
    public void initView() {
        super.initView();
        editText = (EditText) findViewById(R.id.editText);
        cityList= (ListView) findViewById(R.id.city_rv);
        show_bot=(LinearLayout)findViewById(R.id.show_bot);


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        getDate();

        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_item_fird_chat_log) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry,  int i) {
                TextView time = (TextView) viewHolder.findViewById(R.id.show_time);
                CircleImageView phone=(CircleImageView)viewHolder.findViewById(R.id.photo_l);
                TextView leftText=(TextView) viewHolder.findViewById(R.id.city_list_tv_l);
                LinearLayout ly_1=(LinearLayout)viewHolder.findViewById(R.id.ly_1);

                CircleImageView phoneR=(CircleImageView)viewHolder.findViewById(R.id.photo_r);
                TextView rightText=(TextView) viewHolder.findViewById(R.id.city_list_tv_r);
                LinearLayout ly_2=(LinearLayout)viewHolder.findViewById(R.id.ly_2);
                ImageView ivleft=(ImageView)viewHolder.findViewById(R.id.show_check_iv_left);
                ImageView ivRight=(ImageView)viewHolder.findViewById(R.id.show_check_iv_right);
                phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OtherPersonInfoFragment otherPersonInfoFragment=new OtherPersonInfoFragment();
                        addFragment(otherPersonInfoFragment);
                    }
                });
                phoneR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PersonInfoFragment infoFragment=new PersonInfoFragment();
                        addFragment(infoFragment);
                    }
                });
                if(i%2==0){
                    ly_1.setVisibility(View.VISIBLE);
                    ly_2.setVisibility(View.GONE);
                    leftText.setText(workerListEntry);
                }else{
                    ly_2.setVisibility(View.VISIBLE);
                    ly_1.setVisibility(View.GONE);
                    rightText.setText(workerListEntry);
                }
                if(i%4==0){
                    time.setVisibility(View.VISIBLE);
                }else{
                    time.setVisibility(View.GONE);
                }
                if(isDelete){
                    ivleft.setVisibility(View.VISIBLE);
                    ivRight.setVisibility(View.VISIBLE);
                    if(checks.get(i)){
                        ivleft.setImageResource(R.mipmap.icon_seletct_add);
                        ivRight.setImageResource(R.mipmap.icon_seletct_add);
                    }else{
                        ivleft.setImageResource(R.drawable.corner_shape_grey);
                        ivRight.setImageResource(R.drawable.corner_shape_grey);
                    }
                }else{
                    ivRight.setVisibility(View.GONE);
                    ivleft.setVisibility(View.GONE);
                }
            }
        };
        cityList.setAdapter(mAdapter);
        mAdapter.addAll(names);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checks.set(position,!checks.get(position));
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    private void getDate() {
        names.add("常用联系");
        names.add("最近联系");

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
        for(int i=0;i<26;i++){
            checks.add(false);
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("聊天记录");
       // toolbar.setRightText("取消");
        View view = getLayoutInflater(R.layout.layout_tab_diary);
        final ImageView collect = (ImageView) view.findViewById(R.id.iv_collect);
        ImageView share = (ImageView) view.findViewById(R.id.iv_share);
        collect.setImageResource(R.mipmap.msg_delete);
        share.setImageResource(R.mipmap.canlener_img);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, ChatLogFragment.class.getName());
                startActivity(intent);
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDelete==false){
                    isDelete=true;
                    mAdapter.notifyDataSetChanged();
                    show_bot.setVisibility(View.VISIBLE);
                    //collect.setImageResource(R.mipmap.msg_delete);
                }else{
                    isDelete=false;
                    //collect.setImageResource(R.mipmap.msg_delete);
                    initDialog("风险提示");
                    show_bot.setVisibility(View.GONE);


                }
            }
        });
        toolbar.setRightView(view);
        toolbar.setRightTextColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
    private void  initDialog(String title){
        CommonDialog dialog = new CommonDialog(getActivity());
        dialog.setTitle(title);
        View view=getLayoutInflater(R.layout.dialog_text);
        dialog.setMiddleView(view);
        //dialog.setDisplayMiddleEnable(false);
        dialog.setOnClickCancelListener("取 消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initSection();

            }
        });
        dialog.setOnClickConfirmListener("确 定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        dialog.show();
    }

}
