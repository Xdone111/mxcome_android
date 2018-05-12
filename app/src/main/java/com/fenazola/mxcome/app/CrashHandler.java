package com.fenazola.mxcome.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.fenazola.mxcome.utils.Constant;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.FileUtils;
import com.zss.library.utils.LogUtils;

/**
 * 全局异常处理
 * 
 * @author zm
 */
public class CrashHandler implements UncaughtExceptionHandler {
	/** Debug Log tag */
	public final String TAG = "CrashHandler";
	/** 程序的Context对象 */
	private Context mContext;
	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	/** CrashHandler实例 */
	private static CrashHandler INSTANCE;
	/** 使用Properties来保存设备的信息和错误堆栈信息 */
	private Properties mDeviceCrashInfo = new Properties();
	private static final String VERSION_NAME = "versionName";
	private static final String VERSION_CODE = "versionCode";
	private static final String STACK_TRACE = "STACK_TRACE";
	/** 错误报告文件的扩展名 */
	private static final String CRASH_REPORTER_EXTENSION = ".txt";

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CrashHandler();
		}
		return INSTANCE;
	}

	/**
	 * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
	 * 
	 * @param ctx
	 */
	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			// Sleep一会后结束程序
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			Process.killProcess(android.os.Process.myPid());
			System.exit(0);
			Runtime.getRuntime().exit(0);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}
		final String msg = ex.getLocalizedMessage();
		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				CommonToastUtils.showInCenterToast(mContext, "抱歉，程序异常退出!");
				Process.killProcess(android.os.Process.myPid());
				System.exit(0);
				Runtime.getRuntime().exit(0);
				Log.i(TAG, msg + "");
				Looper.loop();
			}

		}.start();
		// 收集设备信息
		collectCrashDeviceInfo(mContext);
		// 保存错误报告文件
		String crashFileName = saveCrashInfoToFile(ex);
		Log.i(TAG, "crashFileName->" + crashFileName + "");
		String crashFile = FileUtils.getFileDir(mContext) + crashFileName;
		Log.i(TAG, "crashFileName->" + crashFile);
		if (FileUtils.exists(mContext, crashFile)) { //复制文件到SDCard根目录
			try {
				String c = FileUtils.read(mContext, crashFileName);
				File file = new File(FileUtils.getExtDir(mContext) + Constant.appName + File.separator + "crash");
				if(!file.exists()){
					file.mkdirs();
				}
				FileUtils.saveFile(file.getPath() + File.separator + crashFileName, c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Log.i(TAG, "Crash文件不存在");
		}
		// 发送错误报告到服务器
		return true;
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return
	 */

	private String saveCrashInfoToFile(Throwable ex) {
		Writer info = new StringWriter();
		PrintWriter printWriter = new PrintWriter(info);
		ex.printStackTrace(printWriter);

		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}

		String result = info.toString();
		printWriter.close();
		Log.e(TAG, result + "");
		mDeviceCrashInfo.put(STACK_TRACE, result);
		try {
			long timestamp = Calendar.getInstance().getTimeInMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateStr = sdf.format(new Date(timestamp));
			String fileName = "crash-" + dateStr + CRASH_REPORTER_EXTENSION;
			LogUtils.i(TAG, "fileName = " + fileName);
			FileOutputStream trace = mContext.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			mDeviceCrashInfo.store(trace, null);
			trace.flush();
			trace.close();
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing report file...", e);
		}
		return null;
	}

	/**
	 * 收集程序崩溃的设备信息
	 * 
	 * @param ctx
	 */
	public void collectCrashDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				mDeviceCrashInfo.put(VERSION_NAME,
						pi.versionName == null ? "not set" : pi.versionName);
				mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode + "");
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Error while collect package info", e);
		}
		// 使用反射来收集设备信息.在Build类中包含各种设备信息,
		// 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				mDeviceCrashInfo.put(field.getName(), field.get(null)
						.toString());
				LogUtils.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "Error while collect crash info", e);
			}
		}
	}
}
