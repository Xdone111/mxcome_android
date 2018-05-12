package com.fenazola.mxcome.fragment.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.project.OrderEntry;
import com.fenazola.mxcome.fragment.main.demand.DemandFragment;
import com.fenazola.mxcome.fragment.me.LoginFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DataCache;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * 需求选择
 */
public class ProjectDemandChoiceFragment extends BaseFragment implements View.OnClickListener {

    private Button button1, button2, button3;
    private TextView next;
    private OrderEntry order;
    private Bundle args1;
    private int index=-1;
    int width;
    private ImageView adv;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_demand;
    }

    @Override
    public void initView() {
        super.initView();
        WindowManager wm = getActivity().getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        next = (TextView) findViewById(R.id.next);
        adv=(ImageView)findViewById(R.id.adv);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            order = (OrderEntry) args.getSerializable(Constant.key1);
        }
        changeItemH(adv);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==1) {
                    args1 = new Bundle();
                    args1.putSerializable(Constant.key1, order);
                    order.setOne_level_type(DataCache.ENUM_CD_DX);
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtras(args1);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, DemandFragment.class.getName());
                    startActivity(intent);
                }else if(index==2){
                    args1 = new Bundle();
                    order.setOne_level_type(DataCache.ENUM_CD_ZT);
                    ProjectMealChoiceFragment fragment = new ProjectMealChoiceFragment();
                    fragment.setArguments(args1);
                    addFragment(fragment);
                    args1.putSerializable(Constant.key1, order);
                }else if(index==3) {
                    args1 = new Bundle();
                    order.setOne_level_type(DataCache.ENUM_CD_MK);
                    args1.putSerializable(Constant.key1, order);
                    ProjectModuleFragment fragment = new ProjectModuleFragment();
                    fragment.setArguments(args1);
                    addFragment(fragment);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                changeColor(1);
                break;
            case R.id.button2:
                changeColor(2);
                break;
            case R.id.button3:
                changeColor(3);
                break;
        }
    }
    private void changeItemH(ImageView iv){
        RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) iv.getLayoutParams();
        linearParams.height = (int)((float)width*0.7);
        iv.setLayoutParams(linearParams);
    }
    private void changeColor(int ind) {
        index=ind;
        button3.setBackgroundResource(R.mipmap.demand_left_grey);
        button1.setBackgroundResource(R.mipmap.demand_top_grey);
        button2.setBackgroundResource(R.mipmap.demand_right_grey);
        button1.setTextColor(getColor(R.color.colorGrey));
        button2.setTextColor(getColor(R.color.colorGrey));
        button3.setTextColor(getColor(R.color.colorGrey));
        //next.setBackgroundColor(getColor(R.color.colorBlue));
        next.setTextColor(getColor(R.color.colorBlue));
        if (ind == 1) {
            button1.setBackgroundResource(R.mipmap.demand_top_blue);
            button1.setTextColor(getColor(R.color.colorBlue));
        } else if (ind == 2) {
            button2.setBackgroundResource(R.mipmap.demand_right_blue);
            button2.setTextColor(getColor(R.color.colorBlue));
        } else if(ind==3){
            button3.setBackgroundResource(R.mipmap.demand_left_blue);
            button3.setTextColor(getColor(R.color.colorBlue));
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgRes(R.color.trans);
        toolbar.setTitle(getString(R.string.project_demand_choice));
//        toolbar.setRightText(getString(R.string.home_page));
//        toolbar.setOnRightListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().finish();
//            }
//        });
    }
}
