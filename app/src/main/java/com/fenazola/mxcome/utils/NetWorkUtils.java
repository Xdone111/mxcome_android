package com.fenazola.mxcome.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;

import com.fenazola.mxcome.receiver.NetWorkManager;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.https.OkHttpUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.widget.CommonProgressDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

public class NetWorkUtils {

	private static CommonProgressDialog dialog;
	private static String RET_CODE = "return_code";
	private static String RET_MSG = "error";
	private static String SUC_FLAG = "1";

	public static void get(final Activity mContext, String actionDo,
							final IListener listener) {
		get(mContext, actionDo, true, listener);
	}

	public static void post(final Activity mContext, String actionDo, Map<String, String> params,
							final IListener listener) {
		post(mContext, actionDo, params, true, listener);
	}


	public static void getUrl(final Activity mContext, String url,
						   final IListener listener) {
		getUrl(mContext, url, true, listener);
	}

	public static void postUrl(final Activity mContext, String url, Map<String, String> params,
							final IListener listener) {
		postUrl(mContext, url, params, true, listener);
	}

	public static void get(final Activity mContext, String actionDo, final boolean isLock, final IListener listener) {
		if(!NetWorkManager.isNetworkAvailable(mContext)){
			CommonToastUtils.showInCenterToast(mContext, "您当前网络不可用");
			listener.onError("您当前网络不可用", "", "");
			return;
		}
		if(isLock)
			showProgressDialog(mContext, "");
		OkHttpUtils.get(Constant.baseUrl + actionDo, new OkHttpUtils.ICallback(){
			@Override
			public void onSuccess(final String result) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							JSONObject resObj = new JSONObject(result);
							String code = resObj.optString(RET_CODE);
							if(SUC_FLAG.equals(code)){
								listener.onSuccess(result, resObj);
							}else{
								String msg = resObj.optString(RET_MSG);
								listener.onError(result, code, msg);
								Utils.commonError(mContext, result, code, msg);
							}
						}catch (Exception e){
							e.printStackTrace();
						}finally {
							if(isLock)
								dismissProgressDialog(mContext);
						}
					}
				});

			}
			@Override
			public void onError(final String result) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						listener.onError(result, "", "");
						Utils.commonError(mContext, result, "", "");
						if(isLock)
							dismissProgressDialog(mContext);
					}
				});
			}
		});
	}

	public static void getUrl(final Activity mContext, String url, final boolean isLock, final IListener listener) {
		if(!NetWorkManager.isNetworkAvailable(mContext)){
			CommonToastUtils.showInCenterToast(mContext, "您当前网络不可用");
			listener.onError("您当前网络不可用", "", "");
			return;
		}
		if(isLock)
			showProgressDialog(mContext, "");
		OkHttpUtils.get(url, new OkHttpUtils.ICallback(){
			@Override
			public void onSuccess(final String result) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							JSONObject resObj = new JSONObject(result);
							String code = resObj.optString(RET_CODE);
							if(SUC_FLAG.equals(code)){
								listener.onSuccess(result, resObj);
							}else{
								String msg = resObj.optString(RET_MSG);
								listener.onError(result, code, msg);
								Utils.commonError(mContext, result, code, msg);
							}
						}catch (Exception e){
							e.printStackTrace();
						}finally {
							if(isLock)
								dismissProgressDialog(mContext);
						}
					}
				});

			}
			@Override
			public void onError(final String result) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						listener.onError(result, "", "");
						Utils.commonError(mContext, result, "", "");
						if(isLock)
							dismissProgressDialog(mContext);
					}
				});
			}
		});
	}

	public static void getWechatUrl(final Activity mContext, String url, final boolean isLock, final IListener listener) {
		if(!NetWorkManager.isNetworkAvailable(mContext)){
			CommonToastUtils.showInCenterToast(mContext, "您当前网络不可用");
			listener.onError("您当前网络不可用", "", "");
			return;
		}
		if(isLock)
			showProgressDialog(mContext, "");
		OkHttpUtils.get(url, new OkHttpUtils.ICallback(){
			@Override
			public void onSuccess(final String result) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							JSONObject resObj = new JSONObject(result);
							String code = resObj.optString(RET_CODE);
							//if(SUC_FLAG.equals(code)){
							//因为微信和我们后台不一样啦 所以不走这个流程

							listener.onSuccess(result, resObj);
//							}else{
//								String msg = resObj.optString(RET_MSG);
//								listener.onError(result, code, msg);
//								//因为微信和我们后台不一样啦 所以不走这个流程
//								//Utils.commonError(mContext, result, code, msg);
//							}
						}catch (Exception e){
							e.printStackTrace();
						}finally {
							if(isLock)
								dismissProgressDialog(mContext);
						}
					}
				});

			}
			@Override
			public void onError(final String result) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						listener.onError(result, "", "");
						Utils.commonError(mContext, result, "", "");
						if(isLock)
							dismissProgressDialog(mContext);
					}
				});
			}
		});
	}
	public static void post(final Activity mContext, String actionDo, Map<String, String> params, final boolean isLock,
							 final IListener listener) {
		if(!NetWorkManager.isNetworkAvailable(mContext)){
			CommonToastUtils.showInCenterToast(mContext, "您当前网络不可用");
			listener.onError("您当前网络不可用", "", "");
			return;
		}
		if(isLock)
			showProgressDialog(mContext, "");
		OkHttpUtils.post(Constant.baseUrl + actionDo, params, new OkHttpUtils.ICallback(){
			@Override
			public void onSuccess(final String result) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							JSONObject resObj = new JSONObject(result);
							String code = resObj.optString(RET_CODE);
							if(SUC_FLAG.equals(code)){
								listener.onSuccess(result, resObj);
							}else{
								String msg = resObj.optString(RET_MSG);
								listener.onError(result, code, msg);
								Utils.commonError(mContext, result, code, msg);
							}
						}catch (Exception e){
							e.printStackTrace();
						}finally {
							if(isLock)
								dismissProgressDialog(mContext);
						}
					}
				});

			}
			@Override
			public void onError(final String result) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						listener.onError(result, "", "");
						Utils.commonError(mContext, result, "", "");
						if(isLock)
							dismissProgressDialog(mContext);
					}
				});
			}
		});
	}


	public static void postUrl(final Activity mContext, String url, Map<String, String> params, final boolean isLock,
							final IListener listener) {
		if(!NetWorkManager.isNetworkAvailable(mContext)){
			CommonToastUtils.showInCenterToast(mContext, "您当前网络不可用");
			listener.onError("您当前网络不可用", "", "");
			return;
		}
		if(isLock)
			showProgressDialog(mContext, "");

		OkHttpUtils.post(url, params, new OkHttpUtils.ICallback(){
			@Override
			public void onSuccess(final String result) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							JSONObject resObj = new JSONObject(result);
							String code = resObj.optString(RET_CODE);
							if(SUC_FLAG.equals(code)){
								listener.onSuccess(result, resObj);
							}else{
								String msg = resObj.optString(RET_MSG);
								listener.onError(result, code, msg);
								Utils.commonError(mContext, result, code, msg);
							}
						}catch (Exception e){
							e.printStackTrace();
						}finally {
							if(isLock)
								dismissProgressDialog(mContext);
						}
					}
				});

			}
			@Override
			public void onError(final String result) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						listener.onError(result, "", "");
						Utils.commonError(mContext, result, "", "");
						if(isLock)
							dismissProgressDialog(mContext);
					}
				});
			}
		});
	}

	public static void upload(final Activity mContext, String actionDo, String fileKey, File file,
							  Map<String, String> map, final IProgressListener listener) {
		if(!NetWorkManager.isNetworkAvailable(mContext)){
			CommonToastUtils.showInCenterToast(mContext, "您当前网络不可用");
			listener.onError("您当前网络不可用", "", "");
			return;
		}
		OkHttpUtils.upload(Constant.baseUrl + actionDo, fileKey, file, map, new OkHttpUtils.IUploadCallback() {
			@Override
			public void onSuccess(final String result) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							JSONObject resObj = new JSONObject(result);
							String code = resObj.optString(RET_CODE);
							if(SUC_FLAG.equals(code)){
								listener.onSuccess(result, resObj);
							}else{
								String msg = resObj.optString(RET_MSG);
								listener.onError(result, code, msg);
								Utils.commonError(mContext, result, code, msg);
							}
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				});
			}

			@Override
			public void onProgress(final int index, final long totalBytes, final long residueBytes, final boolean done) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						listener.onProgress(index, totalBytes, residueBytes, done);
					}
				});
			}

			@Override
			public void onError(final String result) {
				mContext.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						listener.onError(result, "", "");
						Utils.commonError(mContext, result, "", "");
						dismissProgressDialog(mContext);
					}
				});
			}
		});
	}

	public interface IProgressListener {

		public void onSuccess(String result, JSONObject resObj);

		public void onProgress(int index, long totalBytes, long surplusBytes, boolean done);

		public void onError(String result, String code, String msg);
	}

	public interface IListener {

		public void onSuccess(String result, JSONObject resObj);

		public void onError(String result, String code, String msg);
	}

	public static void showProgressDialog(Context mContext, final String showStr) {
		if (mContext != null) {
			if (dialog == null) {
				dialog = new CommonProgressDialog(mContext);
				dialog.setTitle(showStr);
				dialog.setCancelable(true);
				dialog.setOnKeyListener(new OnKeyListener() {
					@Override
					public boolean onKey(DialogInterface d, int keyCode, KeyEvent event) {
						if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
							dialog.dismiss();
							dialog = null;
						}
						return false;
					}
				});
				dialog.show();
			} else {
				if (!dialog.isShowing()) {
					dialog.show();
				}
			}
		}
	}

	public static void dismissProgressDialog(Context mContext) {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

}
