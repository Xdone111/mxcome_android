package com.fenazola.mxcome.fragment.project;

import android.animation.Animator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Utils;
import com.zss.library.animator.Techniques;
import com.zss.library.animator.YoYo;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.SharedPrefUtils;

/**
 * 工程过度页
 */
public class ProjectReadyFragment extends BaseFragment {

    private Button next;
    private TextView text, text2;
    private Handler mHandler = new Handler();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_project_ready;
    }

    @Override
    public void initView() {
        super.initView();
        next = (Button)findViewById(R.id.next);
        text = (TextView)findViewById(R.id.text);
        text2 = (TextView)findViewById(R.id.text2);
    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startProject(getActivity());
            }
        });
        text2.setVisibility(View.GONE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimtor();
            }
        }, 500);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.action_bar_bg);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.trans));
        toolbar.setTitle("新建工程");
    }

    public void startAnimtor(){
        YoYo.with(Techniques.RubberBand).onEnd(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                text2.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.ZoomInUp)
                        .duration(700)
                        .playOn(text2);
            }
        }).duration(700).playOn(text);
    }

    @Override
    public void onBackStackChanged(String fromTagBack) {
        super.onBackStackChanged(fromTagBack);
        startAnimtor();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPrefUtils prefUtils = new SharedPrefUtils(getActivity(), "common_file");
        prefUtils.put("ProjectReadyInit", true);
    }
}
