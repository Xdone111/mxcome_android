package com.fenazola.mxcome.fragment.msg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.iframe.zxing.Contents;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.PersonInfoFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuhuixiang on 2017/3/15.
 * 群聊
 */

public class FriendGroupListFragment extends BaseFragment implements View.OnClickListener{

    private EditText editText;
    ListView cityList;
    private CommonAdapter<String> mAdapter;
    private List<String> names=new ArrayList<String>();
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_friend_group_list;
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
//                if(i==0){
//                    iv.setVisibility(View.VISIBLE);
//                }else if(i==1){
//                    iv.setVisibility(View.VISIBLE);
//                    iv.setImageResource(R.mipmap.pull_down);
//                    city_tv.setTextColor(getColor(R.color.colorBlue));
//                }else{
                    iv.setVisibility(View.INVISIBLE);

                //}
                if(i==names.size()-1){
                    v.setVisibility(View.INVISIBLE);
                }else{
                    v.setVisibility(View.VISIBLE);

                }
                city_tv.setText(workerListEntry);
                phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                        intent.putExtra(ZFrameActivity.CLASS_NAME, GroupQRCodeCardFragment.class.getName());
                        startActivity(intent);
                    }
                });
            }
        };
        cityList.setAdapter(mAdapter);
        mAdapter.addAll(names);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(position==0){
//
//                }else{
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, ChatRecyclerNewTypeFragment.class.getName());
                    intent.putExtra(Constant.key2,2);
                    startActivity(intent);
                //}

            }
        });

    }

    private void getDate() {
        names.add("群聊1");
        names.add("群聊2");
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("群聊");
       // toolbar.setRightText("取消");
//        ImageView imageView=new ImageView(getActivity());
//        imageView.setImageResource(R.mipmap.add_group_img);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
//                intent.putExtra(ZFrameActivity.CLASS_NAME, AddFriendToGroupFragment.class.getName());
//                Bundle bundle0=new Bundle();
//                bundle0.putInt(Constant.key1,0);
//                intent.putExtras(bundle0);
//                startActivity(intent);
//
//            }
//        });
        toolbar.setRightImage(R.mipmap.add_group_img);
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, AddFriendToGroupFragment.class.getName());
                Bundle bundle0=new Bundle();
                bundle0.putInt(Constant.key1,0);
                intent.putExtras(bundle0);
                startActivity(intent);
            }
        });
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

}
