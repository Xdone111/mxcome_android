package com.fenazola.mxcome.fragment.me.collaboration;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuhuixiang on 2017/7/29.
 * 在线合作
 */

public class OnlineCollaborationStepNextFragment extends BaseFragment implements View.OnClickListener {
    /**
     * 13个加盟条目下拉图片
     */
    private List<ImageView> ivs = new ArrayList<ImageView>();
    /**
     * 13个加盟条目标题
     */
    private List<LinearLayout> lyTitles = new ArrayList<LinearLayout>();
    /**
     * 13个加盟详情容器
     */
    private List<LinearLayout> lys = new ArrayList<LinearLayout>();
    /**
     * 13个加盟文本
     */
    private List<TextView> tvs = new ArrayList<TextView>();
    /**
     * 13个加盟按钮
     */
    private List<TextView> tvJoins = new ArrayList<TextView>();
    /**
     * 默认都不显示
     */
    List<Boolean> isShows = new ArrayList<Boolean>();

    private TextView tv1, tv1_1, tv2, tv2_2;

    private int indexItem = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_online_collaboration_step_next;
    }

    @Override
    public void initView() {
        super.initView();
        tv1 = (TextView) findViewById(R.id.tv1);
        tv1_1 = (TextView) findViewById(R.id.tv_line_bm_1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv2_2 = (TextView) findViewById(R.id.tv_line_bm_2);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        for (int i = 0; i < 13; i++) {
            ivs.add((ImageView) findViewById(getViewID("title_iv_" + (i + 1))));
            isShows.add(false);
            lys.add((LinearLayout) findViewById(getViewID("info_" + (i + 1))));
            lyTitles.add((LinearLayout) findViewById(getViewID("info_title_" + (i + 1))));
            tvs.add((TextView) findViewById(getViewID("info_tv_" + (i + 1))));
            tvJoins.add((TextView) findViewById(getViewID("info_join_" + (i + 1))));
            final int index = i;
            lyTitles.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeInfo(index);
                }
            });
            tvJoins.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnlineCollaborationFragment fragment = new OnlineCollaborationFragment();
                    addFragment(fragment);
                }
            });

        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        changeInfo(-1);

    }

    private void changeInfo(int index) {
        if (index > -1)
            isShows.set(index, !isShows.get(index));
        for (int i = 0; i < ivs.size(); i++) {
            if (!isShows.get(i)) {
                lys.get(i).setVisibility(View.GONE);
                ivs.get(i).setImageResource(R.mipmap.online_down_right);
            } else {
                lys.get(i).setVisibility(View.VISIBLE);
                ivs.get(i).setImageResource(R.mipmap.online_up_right);
            }
        }

    }

    private void changeItem(int index) {
        indexItem = index;
        if (index == 0) {
            tv1.setTextColor(getColor(R.color.online_lin_bg));
            tv1_1.setBackgroundResource(R.color.online_lin_bg);
            tv2.setTextColor(getColor(R.color.colorGrey));
            tv2_2.setBackgroundResource(R.color.white);
        } else {
            tv2.setTextColor(getColor(R.color.online_lin_bg));
            tv2_2.setBackgroundResource(R.color.online_lin_bg);
            tv1.setTextColor(getColor(R.color.colorGrey));
            tv1_1.setBackgroundResource(R.color.white);
        }


    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("加盟合作");
        toolbar.setBgColor(getColor(R.color.trans));
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setLeftImage(getResources().getDrawable(R.mipmap.icon_grey_back));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                changeItem(0);
                break;
            case R.id.tv2:
                changeItem(1);
                break;
        }
    }

    public int getViewID(String IDName) {
        return getActivity().getResources().getIdentifier(IDName, "id", "com.fenazola.mxcome");
    }
}
