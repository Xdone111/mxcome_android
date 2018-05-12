package com.fenazola.mxcome.fragment.msg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.project.CommissionerCommentFragment;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索消息页面
 * Created by xuhuixiang on 2017/7/28.
 */

public class MsgSeachFragment extends BaseFragment implements View.OnClickListener{
    private TextView cancelListTv;
    private ImageView okIv,backIv;
    private LinearLayout isMesage,isNotMsg;
    private EditText seachEdit;
    private ListView listView;
    private CommonAdapter<String> mAdapter;
    private List<String> names=new ArrayList<String>();
    private String lastTxt="";
    private TextView tab3;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_seach;
    }

    @Override
    public void initView() {
        super.initView();
        backIv=(ImageView)findViewById(R.id.back_iv);
        okIv=(ImageView)findViewById(R.id.cancel_tv) ;
        isMesage=(LinearLayout)findViewById(R.id.is_ok);
        isNotMsg=(LinearLayout)findViewById(R.id.is_no_msg);

        listView=(ListView) findViewById(R.id.show_lv);
        cancelListTv=(TextView) findViewById(R.id.canel_list_tv);
        seachEdit=(EditText) findViewById(R.id.show_edit);

        tab3=(TextView)findViewById(R.id.tab_3);
        tab3.setOnClickListener(this);
        cancelListTv.setOnClickListener(this);
        okIv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        findViewById(R.id.end_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, CommissionerCommentFragment.class.getName());
                startActivity(intent);
            }
        });
    }

    private void initRullUI() {
        if(names!=null&&names.size()>0){
            isMesage.setVisibility(View.VISIBLE);
            isNotMsg.setVisibility(View.GONE);
        }else{
            isMesage.setVisibility(View.GONE);
            isNotMsg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //seachEdit.setOnKeyListener(onKey);
        loadArray(names);
        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_seach_history_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry,final int i) {
                final TextView name = viewHolder.findViewById(R.id.list_item_tv);
                ImageView iv = viewHolder.findViewById(R.id.list_item_iv);
                name.setText(workerListEntry);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        names.remove(i);
                        saveArray(names);
                        mAdapter.replaceAll(names);
                        initRullUI();
                    }
                });
            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        initRullUI();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.back_iv:
                getActivity().finish();
                break;
            case R.id.city_tv:
                break;
            case R.id.cancel_tv:
                if(!seachEdit.getText().equals("")){
                    LogUtils.i("XHX","执行了几遍？");
                    if(!lastTxt.equals(seachEdit.getText().toString())) {
                        lastTxt=seachEdit.getText().toString();
                        names.add(0,seachEdit.getText().toString());
                        saveArray(names);
                        mAdapter.replaceAll(names);
                        initRullUI();
                    }
                    //TODO
                }
                break;
            case R.id.canel_list_tv:
                lastTxt="";
                names.clear();
                saveArray(names);
                mAdapter.replaceAll(names);
                initRullUI();
                break;
            case R.id.tab_3:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FirdChatLogFragment.class.getName());
                startActivity(intent);
                break;
        }
    }
    public boolean saveArray(List<String> list) {
        SharedPreferences sp = getActivity().getSharedPreferences("ingoreList", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor mEdit1= sp.edit();
        mEdit1.putInt("msg_Status_size",list.size());

        for(int i=0;i<list.size();i++) {
            mEdit1.remove("msg_Status_" + i);
            mEdit1.putString("msg_Status_" + i, list.get(i));
        }
        return mEdit1.commit();
    }

    public void loadArray(List<String> list) {

        SharedPreferences mSharedPreference1 = getActivity().getSharedPreferences("ingoreList", getActivity().MODE_PRIVATE);
        list.clear();
        int size = mSharedPreference1.getInt("msg_Status_size", 0);
        for(int i=0;i<size;i++) {
            list.add(mSharedPreference1.getString("msg_Status_" + i, null));

        }
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
       toolbar.setVisibility(View.GONE);
    }
}
