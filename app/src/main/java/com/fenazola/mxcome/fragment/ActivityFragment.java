package com.fenazola.mxcome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.main.act.BelieveSpeakFragment;
import com.fenazola.mxcome.fragment.main.demand.FreeDesignerFragment;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * 主页 活动
 * Created by xhx on 2017/6/20.
 */

public class ActivityFragment extends BaseFragment implements View.OnClickListener {
    private ImageView iv0, iv1, iv2, iv3, iv4;
    int width;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main_act;
    }

    @Override
    public void initView() {
        super.initView();
        WindowManager wm = getActivity().getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        iv0 = (ImageView) findViewById(R.id.iv_0);
        iv1 = (ImageView) findViewById(R.id.iv_1);
        iv3 = (ImageView) findViewById(R.id.iv_3);
        iv2 = (ImageView) findViewById(R.id.iv_2);
        iv4 = (ImageView) findViewById(R.id.iv_4);
        iv0.setOnClickListener(this);
        iv1.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv4.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        changeItemH(iv0);
        changeItemH(iv1);
        changeItemH(iv2);
        changeItemH(iv3);
        changeItemH(iv4);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);

        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("活动专题");
    }

    private void changeItemH(ImageView iv) {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
        linearParams.height = (int) ((float) width * 0.37);
        iv.setLayoutParams(linearParams);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_0:
                break;
            case R.id.iv_1:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, BelieveSpeakFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.iv_2:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FreeDesignerFragment.class.getName());
                intent.putExtra(Constant.key1, FreeDesignerFragment.INPT_ACT_TYPE);
                startActivity(intent);
                break;
            case R.id.iv_3:
                break;
            case R.id.iv_4:
                break;
        }
    }
}
