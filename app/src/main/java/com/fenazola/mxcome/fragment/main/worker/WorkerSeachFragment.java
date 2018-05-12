package com.fenazola.mxcome.fragment.main.worker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索工人页面
 * Created by xuhuixiang on 2017/7/28.
 */

public class WorkerSeachFragment extends BaseFragment implements View.OnClickListener{
    private TextView cityTv,cancelTv,cancelListTv;
    private EditText seachEdit;
    private ListView listView;
    private CommonAdapter<String> mAdapter;
    private List<String> names=new ArrayList<String>();
    private String lastTxt="";
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_worker_seach;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(ListView) findViewById(R.id.show_lv);
        cityTv=(TextView) findViewById(R.id.city_tv);
        cancelTv=(TextView) findViewById(R.id.cancel_tv);
        cancelListTv=(TextView) findViewById(R.id.canel_list_tv);
        seachEdit=(EditText) findViewById(R.id.show_edit);
        cancelTv.setOnClickListener(this);
        cancelListTv.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        seachEdit.setOnKeyListener(onKey);
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
    }
    View.OnKeyListener onKey=new View.OnKeyListener() {

        @Override

        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if(keyCode == KeyEvent.KEYCODE_ENTER){

                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                if(imm.isActive()){

                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0 );
                    if(!seachEdit.getText().equals("")){
                        LogUtils.i("XHX","执行了几遍？");
                        if(!lastTxt.equals(seachEdit.getText().toString())) {
                            lastTxt=seachEdit.getText().toString();
                            names.add(0,seachEdit.getText().toString());
                            saveArray(names);
                            mAdapter.replaceAll(names);
                        }
                        //TODO
                    }

                }

                return true;

            }

            return false;

        }

    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.city_tv:
                break;
            case R.id.cancel_tv:
                lastTxt="";
                seachEdit.setText("");
                break;
            case R.id.canel_list_tv:
                lastTxt="";
                names.clear();
                saveArray(names);
                mAdapter.replaceAll(names);
                break;
        }
    }
    public boolean saveArray(List<String> list) {
        SharedPreferences sp = getActivity().getSharedPreferences("ingoreList", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor mEdit1= sp.edit();
        mEdit1.putInt("Status_size",list.size());

        for(int i=0;i<list.size();i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, list.get(i));
        }
        return mEdit1.commit();
    }

    public void loadArray(List<String> list) {

        SharedPreferences mSharedPreference1 = getActivity().getSharedPreferences("ingoreList", getActivity().MODE_PRIVATE);
        list.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);
        for(int i=0;i<size;i++) {
            list.add(mSharedPreference1.getString("Status_" + i, null));

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
