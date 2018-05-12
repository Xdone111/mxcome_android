package com.fenazola.mxcome.fragment.msg;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.widget.CommonBDialog;
import com.fenazola.mxcome.widget.DialogWMain;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.widget.CommonDialog;
import com.zss.library.widget.CommonSwitchWidget;
import com.zss.library.widget.CommonWhiteDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 群设置
 * Created by xuhuixiang on 2017/8/1.
 */

public class MsgGroupSettingFragment extends BaseFragment implements View.OnClickListener {

    private CommonSwitchWidget widget1;
    private CommonSwitchWidget widget2;
    private CommonSwitchWidget widget3;
    private CommonSwitchWidget widget4;
    private GridView show_lv;
    private TextView nickNameTv;
    private ImageView deleteInfoIv;
    private ImageView deleteFileIv;
    private TextView outGroupTv;
    private CommonAdapter<String> mAdapter;
    private LinearLayout to_qc_ly;
    private List<String> names=new ArrayList<String>();
    private TextView showAdTv,nameTv;

    EditText nickEdit;
    private LinearLayout groupMiddle;
    private TextView tousuoTv;
    LinearLayout to_name_ly;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_group_setting;
    }

    @Override
    public void initView() {
        super.initView();
        show_lv=(GridView)findViewById(R.id.show_lv) ;
        widget1 = (CommonSwitchWidget) findViewById(R.id.sw_1);
        widget2 = (CommonSwitchWidget) findViewById(R.id.sw_2);
        widget3 = (CommonSwitchWidget) findViewById(R.id.sw_3);
        widget4 = (CommonSwitchWidget) findViewById(R.id.sw_4);
        widget1.setLeftTextColor(getColor(R.color.black));
        widget2.setLeftTextColor(getColor(R.color.black));
        widget3.setLeftTextColor(getColor(R.color.black));
        widget4.setLeftTextColor(getColor(R.color.black));

        nickNameTv=(TextView)findViewById(R.id.nike_name_tv);
        deleteInfoIv=(ImageView)findViewById(R.id.delete_info_iv);
        deleteFileIv=(ImageView)findViewById(R.id.delete_file_iv);
        outGroupTv=(TextView) findViewById(R.id.out_group_tv);
        to_qc_ly=(LinearLayout)findViewById(R.id.to_qc_ly);
        showAdTv=(TextView)findViewById(R.id.show_ad_tv);
        nameTv=(TextView)findViewById(R.id.show_ad_name);
        to_name_ly=(LinearLayout)findViewById(R.id.to_name_ly);
        to_name_ly.setOnClickListener(this);
        to_qc_ly.setOnClickListener(this);
        nickNameTv.setOnClickListener(this);
        deleteInfoIv.setOnClickListener(this);
        deleteFileIv.setOnClickListener(this);
        outGroupTv.setOnClickListener(this);
        groupMiddle=(LinearLayout)findViewById(R.id.group_gonggao);
        groupMiddle.setOnClickListener(this);
        tousuoTv=(TextView)findViewById(R.id.tousuo_tv);
        tousuoTv.setOnClickListener(this);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        widget1.setOnClickListener(this);
        widget2.setOnClickListener(this);
        widget3.setOnClickListener(this);
        widget4.setOnClickListener(this);
        getDate();
        names.add("添加成员");
        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_group_setting_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, final int i) {
                TextView city_tv = (TextView) viewHolder.findViewById(R.id.list_item_tv);
                ImageView phone=(ImageView) viewHolder.findViewById(R.id.list_item_iv);
                city_tv.setText(workerListEntry);
                if(i==names.size()-1){
                    phone.setImageResource(R.mipmap.add_group_gv_img);
                    city_tv.setTextColor(getColor(R.color.colorBlue));
                    phone.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }else{
                    phone.setImageResource(R.mipmap.group_setting_default_img);
                    city_tv.setTextColor(getColor(R.color.colorGrey));
                    phone.setScaleType(ImageView.ScaleType.FIT_XY);

                }
            }
        };
        show_lv.setAdapter(mAdapter);
        mAdapter.addAll(names);
        show_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==names.size()-1){
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, AddFriendToGroupFragment.class.getName());
                    Bundle bundle0=new Bundle();
                    bundle0.putInt(Constant.key1,2);
                    intent.putExtras(bundle0);
                    startActivity(intent);
                }
            }
        });
    }
    private void getDate() {
        names.clear();
        names.add("李伟");
        names.add("张三");
        names.add("阿三");
        names.add("阿四");
        names.add("段誉");
        names.add("段正淳");
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("群聊设置");
        View v=getLayoutInflater(R.layout.view_group_setting_right_top);
        TextView textView=(TextView) v.findViewById(R.id.show_tv_title);
        textView.setText("6");
        toolbar.setRightView(v);
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }

    private void  initDialog(String title){
        CommonDialog dialog = new CommonDialog(getActivity());
        dialog.setTitle(title);
        //dialog.setMiddleView(view);
        dialog.setDisplayMiddleEnable(false);
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
    private void  initDialog1(String title){
        CommonBDialog dialog = new CommonBDialog(getActivity());
        dialog.setTitle(title);
        //dialog.setMiddleView(view);
        dialog.setDisplayMiddleEnable(false);
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
    private void editDialog(){
        DialogWMain dialog = new DialogWMain(getActivity());
        dialog.setTitle("修改昵称");
        final View view1 = getLayoutInflater(R.layout.dialog_edit_group);
        nickEdit = (EditText) view1.findViewById(R.id.edit);
        nickEdit.setTextColor(getColor(R.color.colorGrey));
        nickEdit.setText(nickNameTv.getText());
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
                    nickNameTv.setText(nickEdit.getText());
                    nickEdit.setText(nickNameTv.getText());
                }
            }
        });

        dialog.show();
    }
    private void editAd(){
        DialogWMain dialog = new DialogWMain(getActivity());
        dialog.setTitle("修改群公告");
        final View view1 = getLayoutInflater(R.layout.dialog_edit_group);
        nickEdit = (EditText) view1.findViewById(R.id.edit);
        nickEdit.setTextColor(getColor(R.color.colorGrey));
        nickEdit.setText(showAdTv.getText());
        nickEdit.setMaxLines(100);
        TextView toast=(TextView)view1.findViewById(R.id.toast);
        toast.setText(Html.fromHtml("公告内容请在<font color='#4BB7FD'>100</font>字以内"));
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
                    showAdTv.setText(nickEdit.getText());
                    nickEdit.setText(showAdTv.getText());
                }
            }
        });

        dialog.show();
    }
    private void editName(){
        DialogWMain dialog = new DialogWMain(getActivity());
        dialog.setTitle("修改群名称");
        final View view1 = getLayoutInflater(R.layout.dialog_edit_group);
        nickEdit = (EditText) view1.findViewById(R.id.edit);
        nickEdit.setTextColor(getColor(R.color.colorGrey));
        nickEdit.setText(nameTv.getText());
        TextView toast=(TextView)view1.findViewById(R.id.toast);
        toast.setVisibility(View.GONE);
        toast.setText(Html.fromHtml("公告内容请在<font color='#4BB7FD'>100</font>字以内"));
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
                    nameTv.setText(nickEdit.getText());
                    nickEdit.setText(nameTv.getText());
                }
            }
        });

        dialog.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_name_ly:
                editName();
                break;
            case R.id.to_qc_ly:
//                GroupQcFragment groupQcFragment=new GroupQcFragment();
//                addFragment(groupQcFragment);
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, GroupQcFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.nike_name_tv:
                editDialog();
                break;
            case R.id.delete_info_iv:
                initDialog("清空群聊天记录");
                break;
            case R.id.delete_file_iv:
                initDialog("清空群聊天文件");
                break;
            case R.id.out_group_tv:
                initDialog1("退群后，将不再接收此群任何信息");
                break;
            case R.id.group_gonggao:
//                Toast toast=Toast.makeText(getActivity(),"群主大人方可编辑群公告",Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER,0,0);
//                toast.show();
                editAd();
                break;
            case R.id.tousuo_tv:
                ComplaintsGroupFragment groupQcFragment1=new ComplaintsGroupFragment();
                addFragment(groupQcFragment1);
                break;
        }
    }


}
