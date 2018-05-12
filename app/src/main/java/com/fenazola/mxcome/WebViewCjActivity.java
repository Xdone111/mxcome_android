package com.fenazola.mxcome;

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

import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.ShareUtils;
import com.zss.library.activity.BaseActivity;
import com.zss.library.toolbar.CommonToolbar;

/**
 * WebView 目前主要用于抽奖
 * @author xhx
 * 
 */
public class WebViewCjActivity extends BaseActivity {

	private WebView mWebView;
	private ProgressBar pBar;
//	private ImageView back;
//	private ImageView share;
//	private ImageView like;
	private String urls;
	private String title="";
	private Handler mHandler = new Handler();

	@Override
	public int getLayoutResId() {
		return R.layout.activity_webview_cj;
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void initView() {
		super.initView();
		pBar = (ProgressBar) findViewById(R.id.pBar);
		mWebView = (WebView) findViewById(R.id.webView);
//		back = (ImageView) findViewById(R.id.back);
//		share = (ImageView) findViewById(R.id.share);
//		like = (ImageView) findViewById(R.id.like);
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
		urls=args.getString(Constant.key1);
		mWebView.loadUrl(urls);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		super.initData(savedInstanceState);
	}

	class MyWebViewClient extends WebViewClient {

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			handler.proceed();// 接受所有网站的证书
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			title=view.getTitle();
		}
	}


	@Override
	public void setTopBar() {
		super.setTopBar();
		setStatusBarColor(R.color.colorBlue);
		CommonToolbar toolbar = getToolbar();
		toolbar.setBgColor(getResources().getColor(R.color.colorBlue));
		toolbar.setRightImage(R.mipmap.w_share);
		toolbar.setOnRightListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareUtils.showShareDialog(getActivity(), "分享了", urls,title,"我刚刚在这里领取到了免费装修，棒棒哒！");
			}
		});
		toolbar.setTitle("抽奖");
	}

}
