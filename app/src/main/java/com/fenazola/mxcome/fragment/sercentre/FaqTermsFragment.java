package com.fenazola.mxcome.fragment.sercentre;

import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * 服务条款
 * @author xhx
 * TODO 应该是要写个h5页面来存放服务协议
 */
public class FaqTermsFragment extends BaseFragment{
    private String title;
    private String url;
    /**0 服务协议  1 装修流程 TODO */
    private int type=0;
    WebView webView;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_terms;
    }

    @Override
    public void initView() {
        super.initView();

        webView= (WebView) this.findViewById(R.id.webview);
        // 加载index.html网页
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webView.setBackgroundColor(Color.WHITE);



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
        getBaseActivity().setStatusBarColor(R.color.colorBlue);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("服务条款");
        toolbar.setBgColor(getResources().getColor(R.color.colorBlue));
        toolbar.setTitleColor(Color.WHITE);
       // toolbar.setLeftImage(getResources().getDrawable(R.mipmap.project_left));
    }
}
