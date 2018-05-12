package com.fenazola.mxcome.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.opengl.ETC1;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用帮助
 * @author xhx
 */
public class TestCheckFragment extends BaseFragment implements View.OnClickListener{
    //TODO 设置电话地址
    private String telString="10086";

    private TextView yesTv,NoTv;
    private Spinner spinner;
    private EditText yuanyinEt;
    private LinearLayout isOkLy;
    private TextView lastTv,nextTv;
    private TextView titleTv,desTv;
    List<Bean> beens=new ArrayList<>();
    private List<String> list = new ArrayList<String>();
    int index=0;
    private ArrayAdapter<String> adapter;
    CommonToolbar toolbar;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_test_check;
    }

    @Override
    public void initView() {
        super.initView();
        toolbar = getToolbar();
        yesTv=(TextView)findViewById(R.id.yes_tv);
        NoTv=(TextView)findViewById(R.id.no_tv);
        spinner=(Spinner)findViewById(R.id.yuanyin_tv);
        isOkLy=(LinearLayout)findViewById(R.id.layout_2);
        yuanyinEt=(EditText)findViewById(R.id.edit_tv);
        lastTv=(TextView)findViewById(R.id.last_tv);
        nextTv=(TextView)findViewById(R.id.next_tv);
        titleTv=(TextView)findViewById(R.id.show_more);
        desTv=(TextView)findViewById(R.id.des_tv);
        yesTv.setOnClickListener(this);
        NoTv.setOnClickListener(this);
        lastTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
        for (int i=0;i<5;i++){
            Bean bean=new Bean();
            bean.title="验收项"+i;
            bean.des="验收项说明验收项说明验收项说明验收项说明验收项" +
                    "说明验收项说明验收项说明验收项说明验收项说明验收" +
                    "项说明验收项说明验收项说明验收项说明验收项说明验" +
                    "收项说明验收项说明验收项说明验收项说明验收项说明" +
                    "验收项说明验收项说明验收项说明验收项说明验收项说" +
                    "明验收项说明验收项说明验收项说明验收项说明验收项" +
                    "说明验收项说明"+i;
            list.add("原因"+i);
            beens.add(bean);

        }
        //第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinner.setAdapter(adapter);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
               beens.get(index).beizhu=list.get(arg2);
                arg0.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                beens.get(index).beizhu="";
                arg0.setVisibility(View.VISIBLE);
            }
        });

        initChang(index);

    }
    private void initChang(int i){
        titleTv.setText(beens.get(i).title);
        desTv.setText(beens.get(i).des);
        yuanyinEt.setText(beens.get(i).yuanyin);

        if(beens.get(i).isOk==true){
            isOkLy.setVisibility(View.GONE);
        }else{
            isOkLy.setVisibility(View.VISIBLE);

        }
        toolbar.setRightText((index+1)+"/"+beens.size()+"项目");


    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);


    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);

        toolbar.setTitle("验收报告");
        toolbar.setBgColor(getResources().getColor(R.color.colorBlue));
        toolbar.setTitleColor(Color.WHITE);
       // toolbar.setLeftImage(getResources().getDrawable(R.mipmap.project_left));
        //toolbar.setRightImage(R.mipmap.colose_w);
        toolbar.setRightText("1/5项");
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yes_tv:
                beens.get(index).isOk=true;
                beens.get(index).yuanyin="";
                isOkLy.setVisibility(View.GONE);

                break;
            case R.id.no_tv:
                beens.get(index).isOk=false;
                isOkLy.setVisibility(View.VISIBLE);

                break;
            case R.id.last_tv:
                beens.get(index).beizhu=yuanyinEt.getText().toString();

                if(beens.get(index).isOk&&index>0){
                    index-=1;
                }
                initChang(index);
                break;
            case R.id.next_tv:
                beens.get(index).beizhu=yuanyinEt.getText().toString();

                if(beens.get(index).isOk&&index<beens.size()-1){
                    index+=1;
                }
                initChang(index);

                break;
        }
    }

    class Bean {
        String title;
        String des;
        boolean isOk=true;
        String yuanyin;
        String beizhu;
    }
}
