package com.fenazola.mxcome.fragment.msg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.sercentre.FaqTermsFragment;
import com.fenazola.mxcome.widget.DialogWMain;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.widget.CommonEditWidget;
import com.zss.library.widget.CommonWhiteDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置招呼
 * @author xhx
 */
public class SetHelloFragment extends BaseFragment implements View.OnClickListener{
    private ListView listView;

    private CommonAdapter<String> mAdapter;
    ArrayList<String> names=new ArrayList<String>();
    private ImageView addHello,editHello,deleteHello;
    private EditText nickEdit;
    private int pos=0;
    private boolean isCheck=false;
    CommonToolbar toolbar;
    List<Boolean> checks=new ArrayList<Boolean>();
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_set_hello;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(ListView) findViewById(R.id.listView);
        addHello=(ImageView)findViewById(R.id.add_hello_iv);
        editHello=(ImageView)findViewById(R.id.edit_hello_iv);
        deleteHello=(ImageView)findViewById(R.id.delete_hello_iv);
        addHello.setOnClickListener(this);
        editHello.setOnClickListener(this);
        deleteHello.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        //TODO
        initDateList();
        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_set_hello_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry,final int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                TextView now=viewHolder.findViewById(R.id.list_now);
                ImageView iv=viewHolder.findViewById(R.id.iv);
                name.setText((i+1)+"."+workerListEntry);
                if(pos==i){
                    now.setVisibility(View.VISIBLE);
                }else{
                    now.setVisibility(View.INVISIBLE);
                }
                if(isCheck){
                    iv.setVisibility(View.VISIBLE);
                }else{
                    iv.setVisibility(View.GONE);
                }
                if(checks.get(i)){
                    iv.setImageResource(R.mipmap.icon_seletct_add);
                }else{
                    iv.setImageResource(R.drawable.corner_shape_grey);
                }
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checks.set(i,!checks.get(i));
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initDateList() {
        names.add("您好，我暂时有事，一会与您联系。");
        names.add("工作中，请勿打扰");
        names.add("我外出了，一会联系");
        checks.add(false);
        checks.add(false);
        checks.add(false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_hello_iv:
                editDialog(true);
                break;
            case R.id.edit_hello_iv:
                editDialog(false);
                break;
            case R.id.delete_hello_iv:
                isCheck=true;
                toolbar.setRightText("完成");
                toolbar.setRightShow(true);
                toolbar.setOnRightListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i=0;i<names.size();i++){
                            if(checks.get(i)){
                                names.remove(i);
                                checks.remove(i);
                            }
                        }
                        checks.clear();
                        for (int i=0;i<names.size();i++){
                            checks.add(false);
                        }
                        isCheck=false;
                        mAdapter.replaceAll(names);
                        toolbar.setRightShow(false);
                    }
                });
                mAdapter.notifyDataSetChanged();

                break;
        }
    }
    /**添加 or修改招呼
     * @param isAdd  true 添加  false 修改
     */
    private void editDialog(final boolean isAdd){
        DialogWMain dialog = new DialogWMain(getActivity());
        if(isAdd) {
            dialog.setTitle("添加招呼");
        }else{
            dialog.setTitle("修改招呼");
        }
        View view1 = getLayoutInflater(R.layout.dialog_edit_set_hello);
        nickEdit = (EditText) view1.findViewById(R.id.edit);
        if(isAdd){
            nickEdit.setText("");
        }else{
            nickEdit.setText(names.get(pos));
            nickEdit.setSelection(nickEdit.getText().length());
        }
        dialog.setMiddleView(view1);
        dialog.setOnClickCancelListener("取 消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initSection();

            }
        });
        dialog.setOnClickConfirmListener("确 定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                if(!TextUtils.isEmpty(nickEdit.getText().toString())) {
                    if(isAdd){
                        names.add(nickEdit.getText().toString());
                        checks.add(false);
                    }else{
                        names.set(pos,nickEdit.getText().toString());
                        checks.set(pos,false);
                    }


                }
                mAdapter.replaceAll(names);
            }
        });

        dialog.show();
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);

        toolbar = getToolbar();
        toolbar.setTitle("设置招呼");
        toolbar.setBgColor(getResources().getColor(R.color.colorBlue));
        toolbar.setTitleColor(Color.WHITE);
    }
}
