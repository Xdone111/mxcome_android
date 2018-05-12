package com.fenazola.mxcome;

import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.ShareUtils;
import com.zss.library.activity.BaseActivity;
import com.zss.library.toolbar.CommonToolbar;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * WebView
 *
 * @author zm
 */
public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private ProgressBar pBar;
    private ImageView back;
    private ImageView share;
    private ImageView like;
    private Handler mHandler = new Handler();

    @Override
    public int getLayoutResId() {
        return R.layout.activity_webview;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        super.initView();
        pBar = (ProgressBar) findViewById(R.id.pBar);
        mWebView = (WebView) findViewById(R.id.webView);
        back = (ImageView) findViewById(R.id.back);
        share = (ImageView) findViewById(R.id.share);
        like = (ImageView) findViewById(R.id.like);
        mWebView.setBackgroundColor(0);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    if (pBar.getVisibility() == View.INVISIBLE) {
                        pBar.setVisibility(View.VISIBLE);
                        pBar.setProgress(newProgress);
                    }
                } else {
                    pBar.setProgress(100);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pBar.setVisibility(View.INVISIBLE);
                        }
                    }, 500);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        Bundle args = getIntent().getExtras();
        mWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = mWebView.getSettings();
        //自适应屏幕
        webSettings.setSupportZoom(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);//这个很关键
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据
        mWebView.loadUrl(args.getString(Constant.key1));
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        share = (ImageView) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.showShareDialog(getActivity(), "分享了", "分享了");
            }
        });
        like = (ImageView) findViewById(R.id.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            handler.proceed();// 接受所有网站的证书
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        setStatusBarColor(R.color.circle_toolbar_color);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.GONE);
    }
}
