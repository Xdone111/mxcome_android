package com.fenazola.mxcome.fragment.msg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.adapter.FriendAdapter;
import com.fenazola.mxcome.fragment.main.demand.DemandDesign;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.widget.SlideBar;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by xuhuixiang on 2017/3/15.
 * 发送名片用这个
 * 群聊选人 也用这个
 */

public class AddFriendToGroupFragment extends BaseFragment implements View.OnClickListener ,SlideBar.OnTouchLetterChangeListenner{
    /* 0 添加群聊 1 发送名片 2追加群聊 3 发送群名片**/
    private int type;
    private EditText editText;
    FriendAdapter mAdapter;
    TextView float_letter;
    ListView cityList;
    SlideBar slideBar;
    CommonToolbar toolbar;
    GridView gList;
    private CommonAdapter<String> mGAdapter;
    private List<String> names=new ArrayList<String>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_friend_list_add_group;
    }

    @Override
    public void initView() {
        super.initView();
        editText = (EditText) findViewById(R.id.editText);
        mAdapter = new FriendAdapter(getActivity());

        cityList= (ListView) findViewById(R.id.city_rv);
        float_letter = (TextView) findViewById(R.id.float_letter);
        slideBar= (SlideBar) findViewById(R.id.slidebar);
        gList=(GridView)findViewById(R.id.show_gv);
//      cityRv.addHeaderView(v);


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        type=getArguments().getInt(Constant.key1);
        cityList.setAdapter(mAdapter);
        slideBar.setOnTouchLetterChangeListenner(this);
        getDate();
        mGAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_item_list_item_friend) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, final int i) {
                CircleImageView phone=(CircleImageView)viewHolder.findViewById(R.id.list_item_iv);
                ImageView iv=(ImageView)viewHolder.findViewById(R.id.list_iv);


            }
        };
        gList.setAdapter(mGAdapter);
        mGAdapter.addAll(names);

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        toolbar = getToolbar();
        if(type==0||type==2){
            toolbar.setTitle("发起群聊");

        }else {
            toolbar.setTitle("选择");
        }
        toolbar.setRightText("确定");
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==1) {
                    SendBusinessCard dialog = new SendBusinessCard(getActivity());
                    dialog.show();
                }else{
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, MsgGroupSettingFragment.class.getName());
                    startActivity(intent);
                }
            }
        });
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
    private void getDate() {
        names.add("常用联系");
        names.add("最近联系");
        names.add("常用联系");
        names.add("最近联系");
    }
    @Override
    public void onTouchLetterChange(boolean isTouched, String s) {
        float_letter.setText(s);
        //Toast.makeText(getActivity(),"返回"+s,Toast.LENGTH_SHORT).show();
        if (isTouched) {
            float_letter.setVisibility(View.VISIBLE);
        } else {
            float_letter.postDelayed(new Runnable() {
                @Override
                public void run() {
                    float_letter.setVisibility(View.GONE);
                }
            }, 100);
        }
        if(s.equals("#")){
            cityList.setSelectionFromTop(0, 0);
        }else{
            int moveToPosition = mAdapter
                    .getPositionForSection(s.charAt(0));
            if (moveToPosition >= 0) {
                cityList.setSelectionFromTop(moveToPosition, 0);
            }
        }

    }
}
