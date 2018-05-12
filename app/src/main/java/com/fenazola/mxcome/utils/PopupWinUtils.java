package com.fenazola.mxcome.utils;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.fenazola.mxcome.R;

/**
 * 显示PopupWindow
 */
public class PopupWinUtils {

	public static PopupWindow popWin;

	public static void showPopupWindow(View contentView, View anchor, int x, int y) {
		dismiss();
		// 创建PopupWindow对象
		popWin = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
		popWin.setBackgroundDrawable(new ColorDrawable());
		// 设置点击窗口外边窗口消失
		popWin.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		popWin.setFocusable(true);
		// 显示窗口
		popWin.showAtLocation(anchor, Gravity.CENTER, 0, 0);

	}
	
	public static boolean dismiss(){
		if(popWin!=null){
			popWin.dismiss();
			popWin = null;
			return true;
		}
		return false;
	}

}
