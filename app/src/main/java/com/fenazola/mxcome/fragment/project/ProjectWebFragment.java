package com.fenazola.mxcome.fragment.project;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * 服务条款
 * @author xhx
 * TODO 应该是要写个h5页面来存放服务协议
 */
public class ProjectWebFragment extends BaseFragment{
    private TextView title;
    private String url;
    private ImageView back;
    /**0 服务协议  1 装修流程 TODO */
    private int type=0;
    WebView webView;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_project_webview;
    }

    @Override
    public void initView() {
        super.initView();

        webView= (WebView) this.findViewById(R.id.webview);
        // 加载index.html网页
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webView.setBackgroundColor(Color.TRANSPARENT);
        title=(TextView)findViewById(R.id.show_title_tv);
        back=(ImageView)findViewById(R.id.show_title_left);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        title.setText("装修流程");


    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        type=getArguments().getInt(Constant.key1);
        if(type==1){
            webView.loadUrl("file:///android_asset/c-service.html");

        }else{
            webView.loadUrl("file:///android_asset/index.html");

        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlueDark);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("装修流程");
        toolbar.setBgColor(getColor(R.color.colorBlueDark));
        toolbar.setTitleColor(Color.WHITE);
        toolbar.setVisibility(View.GONE);
       // toolbar.setLeftImage(getResources().getDrawable(R.mipmap.project_left));
    }
}
