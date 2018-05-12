package com.fenazola.mxcome.receiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * @Title NetWorkUtil
 * @Description 是检测网络的一个工具包
 */
@SuppressLint("DefaultLocale")
public class NetWorkManager
{

	public enum NetType
	{
		wifi, CMNET, CMWAP, noneNet
	}

	/**
	 * 网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context)
	{
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
		if (info != null && info.getState()== State.CONNECTED)
		{
			return true;
		}
		return false;
	}

	/**
	 * 判断WIFI网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context)
	{
		if (context != null)
		{
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			State state = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
			if (state == State.CONNECTED || state == State.CONNECTING)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断MOBILE网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnected(Context context)
	{
		if (context != null)
		{
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			State state = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
			if (state == State.CONNECTED || state == State.CONNECTING)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前网络连接的类型信息
	 * 
	 * @param context
	 * @return
	 */
	public static int getConnectedType(Context context)
	{
		if (context != null)
		{
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable())
			{
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

	/**
	 * 
	 * @author 白猫
	 * 
	 *         获取当前的网络状态 -1：没有网络 1：WIFI网络2：wap 网络3：net网络
	 * 
	 * @param context
	 * 
	 * @return
	 */
	public static NetType getAPNType(Context context)
	{
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo == null)
		{
			return NetType.noneNet;
		}
		int nType = networkInfo.getType();

		if (nType == ConnectivityManager.TYPE_MOBILE)
		{
			if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet"))
			{
				return NetType.CMNET;
			}

			else
			{
				return NetType.CMWAP;
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI)
		{
			return NetType.wifi;
		}
		return NetType.noneNet;

	}
}
