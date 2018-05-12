package com.fenazola.mxcome.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;


import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.VersionEntry;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.utils.LogUtils;
import com.zss.library.https.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zm on 2017/3/21.
 */

public class DownloadService extends IntentService{

    private int notifId = 0x01;
    private NotificationManager mgr = null;
    private Notification notification = null;
    private NotificationCompat.Builder builder;
    private long beforeTime;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what != 0) {
                builder.setProgress(100, msg.what, false);
                builder.setContentText("下载进度:" + msg.what + "%");
                notification = builder.build();
                mgr.notify(notifId, notification);
            }
        }
    };

    public DownloadService(){
        super("com.fenazola.mxcomemc.service.DownloadService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("正在更新...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(false)
                .setContentText("下载进度:" + "0%")
                .setProgress(100, 0, false);
        notification = builder.build();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra(Constant.key1);
        String destDir = intent.getStringExtra(Constant.key2);

        OkHttpUtils.download(url, destDir, new OkHttpUtils.IDownloadCallback() {
            @Override
            public void onSuccess(String result) {
                LogUtils.i("---zss---", "onSuccess = " + result);
                mgr.cancel(notifId);
                VersionEntry entry = new VersionEntry();
                entry.setFileName(result);
                EventBus.getDefault().post(entry);
            }

            @Override
            public void onProgress(long totalBytes, long downloadBytes, int progress) {
                LogUtils.i("---zss---", "onProgress = " + progress);
                if (System.currentTimeMillis() - beforeTime > 500 || progress == 100) {
                    mHandler.sendEmptyMessage(progress);
                    beforeTime = System.currentTimeMillis();
                }
            }

            @Override
            public void onError(String result) {
                mgr.cancel(notifId);
            }
        });
    }

}
