package com.fenazola.mxcome.fragment.msg;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.core.PoiInfo;
import com.fenazola.iframe.zxing.CaptureActivity;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.adapter.FriendAdapter;
import com.fenazola.mxcome.db.TableTalk;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
import com.fenazola.mxcome.widget.SlideBar;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.PermissionCallBack;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.widget.CommonTextWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/3/15.
 */

public class FriendListFragment extends BaseFragment implements View.OnClickListener{

    private EditText editText;
    ListView cityList;
    private CommonAdapter<String> mAdapter;
    private List<String> names=new ArrayList<String>();
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_friend_list;
    }

    @Override
    public void initView() {
        super.initView();
        editText = (EditText) findViewById(R.id.editText);
        cityList= (ListView) findViewById(R.id.city_rv);


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        getDate();

        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_item_list_item_friend) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, final int i) {
                TextView city_tv = (TextView) viewHolder.findViewById(R.id.city_list_tv);
                CircleImageView phone=(CircleImageView)viewHolder.findViewById(R.id.photo);
                ImageView iv=(ImageView)viewHolder.findViewById(R.id.list_iv);
                View v=viewHolder.findViewById(R.id.show_line);

                city_tv.setTextColor(getColor(R.color.colorGrey));
                if(i==0){
                    iv.setVisibility(View.VISIBLE);
                }else if(i==1){
                    iv.setVisibility(View.VISIBLE);
                    iv.setImageResource(R.mipmap.pull_down);
                    city_tv.setTextColor(getColor(R.color.colorBlue));
                }else{
                    iv.setVisibility(View.INVISIBLE);

                }
                if(i==names.size()-1){
                    v.setVisibility(View.INVISIBLE);
                }else{
                    v.setVisibility(View.VISIBLE);

                }
                phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                        intent.putExtra(ZFrameActivity.CLASS_NAME, OtherPersonInfoFragment.class.getName());
                        startActivity(intent);
                    }
                });
                city_tv.setText(workerListEntry);
            }
        };
        cityList.setAdapter(mAdapter);
        mAdapter.addAll(names);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==2){
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, ChatSetDesignFragment.class.getName());
                    startActivity(intent);

                }else if(position==3){
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, ChatSetFragment.class.getName());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, ChatRecyclerNewTypeFragment.class.getName());
                    intent.putExtra(Constant.key2,0);

                    startActivity(intent);
                }
            }
        });

    }

    private void getDate() {
        names.add("常用联系");
        names.add("最近联系");
        names.add("聊天设置 设计");
        names.add("聊天设置 个人");


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
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("通讯录");
       // toolbar.setRightText("取消");
        toolbar.setRightTextColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

}
