package com.fenazola.mxcome.app;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.multidex.MultiDex;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.DbHelper;
import com.fenazola.mxcome.db.TableArea;
import com.fenazola.mxcome.db.TableChat;
import com.fenazola.mxcome.db.TableTalk;
import com.fenazola.mxcome.entry.MsgEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.project.OrderEntry;
import com.fenazola.mxcome.fragment.main.PushToFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.ShareUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.iframe.BaseApp;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
import com.igexin.sdk.message.GTTransmitMessage;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.https.OkHttpUtils;
import com.zss.library.utils.FileUtils;
import com.zss.library.utils.FontUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.SharedPrefUtils;
import com.zss.library.utils.StringUtils;
import com.zss.library.widget.CommonWhiteDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2017/2/8.
 */

public class BaseApplication extends BaseApp {

    public static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    public DbHelper dbHelper;

    private int activityCounter;

    private Activity currentActivity;

    private Handler mHandler = new Handler();

    public DbHelper getDbHelper() {
        return dbHelper;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashHandler.getInstance().init(this);
        EventBus.getDefault().register(this);
        LogUtils.setDebug(true);
        FontUtils.initFont(this, "fonts/DS-DIGIT.TTF");
        OkHttpUtils.init("MXCOME_1.0_ANDROID");
        String cert = "MIIDHzCCAgcCBFh1i7EwDQYJKoZIhvcNAQELBQAwUzELMAkGA1UEBhMCQ04xCzAJ\n" +
                "BgNVBAgMAmdkMQswCQYDVQQHDAJzejENMAsGA1UECgwEY3NpaTENMAsGA1UECwwE\n" +
                "Y3NpaTEMMAoGA1UEAwwDaHliMCAXDTE3MDExMTAxMzQ0MVoYDzIxMTYxMjE4MDEz\n" +
                "NDQxWjBTMQswCQYDVQQGEwJDTjELMAkGA1UECAwCZ2QxCzAJBgNVBAcMAnN6MQ0w\n" +
                "CwYDVQQKDARjc2lpMQ0wCwYDVQQLDARjc2lpMQwwCgYDVQQDDANoeWIwggEiMA0G\n" +
                "CSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCFhhaKpdDm5A6Rwz7nRxpNwdFLu4Kt\n" +
                "RugpPBPITpXrt+1N7N4cC6MSDS06JhSEDHEwmDgXmKZ1v5KEzgxbrfqTeO2/XF3f\n" +
                "fdekaw/uRw7JjW/4we4EKL/PQWJEhhtvAOI++5nhIPKvh6rZ+4Ia2sK7knC8vmrM\n" +
                "SGDYEMsm191q2+JUrKWcqx8OqbjoJ5QbKIhL/FPbQsTwkVbUJwrRGA+Y0UYQAVvM\n" +
                "rdkcNnOQ8/DRaIH6w3dX9NuJBWwGsLXmVAqMUBOHIYh+8njVjHJnvj/WkCb4hZ8C\n" +
                "t2gAU20dUHRYucJa+N8FVKnVJ6z/uAx9usCMc7JSxJgg+4j8pfePjRd3AgMBAAEw\n" +
                "DQYJKoZIhvcNAQELBQADggEBAIBUyCD+gM7yZDGRyUyWwMMn0vcEUnPRbBxazf7j\n" +
                "MZ39CLEi4fHqkpxJfT52VIyASjZGrRddazdLoV7fmxVHOZVzWIBy29moA6mx69oL\n" +
                "FoE5P79e6HJK7m82UtLwUkhiazsagkniYVY+upTpm6tJ6nEKG4Xl7gXDZIBvDJ2J\n" +
                "GgQ+RKuvndp67ilqsLmeQtU014KHW5TWpmcBqscTq29vYZiFltIxuLS/8gareJAO\n" +
                "9ELGzWQl9IYIThDtCw0D9TW5BllspLl5ZFzUTJw9ukTNa2JGukbkB44OR7e3LEXm\n" +
                "x6kgkjLMeUGfhPsFGajvL6bBE/zz3Fv+00ZmamOLX+/cHfo=";
        List<String> certList = new ArrayList<String>();
        certList.add(cert);
        OkHttpUtils.setCertificates(certList);
        dbHelper = new DbHelper(this);
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityStopped(Activity activity) {
                activityCounter--;
            }

            @Override
            public void onActivityStarted(Activity activity) {
                currentActivity = activity;
                activityCounter++;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }
        });
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                execSql();
            }
        });
    }


    public synchronized void execSql() {
        SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
        try {
            if (!prefUtils.get("AreaInit", false)) {
                prefUtils.put("AreaInit", true);
                BaseDaoImpl dao = new BaseDaoImpl(TableArea.class);
                InputStreamReader inputReader = new InputStreamReader(getAssets().open("area.sql"));
                BufferedReader bufReader = new BufferedReader(inputReader);
                String line;
                long start = SystemClock.currentThreadTimeMillis();
                List<String> list = new ArrayList<>();
                while ((line = bufReader.readLine()) != null) {
                    list.add(line);
                }
                dao.execSql(list);
                long end = SystemClock.currentThreadTimeMillis();
                Log.i("---zss---", "exec time : " + (end - start));
            }
        } catch (Exception e) {
        }
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1, sticky = true)
    public void onEvent(GTTransmitMessage msg) {
        String src1 = EncryptUtil.AesDecrypt(new String(msg.getPayload()));
        LogUtils.i("msg", "------收到透传消息src-----" + msg.getClientId()+":"+src1);
        byte[] payload = msg.getPayload();
        if (payload != null) {
            String src = EncryptUtil.AesDecrypt(new String(msg.getPayload()));
            MsgEntry msgEntry = GsonUtils.getObjFromJSON(src, MsgEntry.class);
            if (msgEntry.getBizType() == 4) { //抢单成功，通知栏，弹出界面
                //TODO
                LogUtils.i("msg", "------收到透传消息src-----" + msgEntry.toString());
                Intent intent = new Intent(getCurrentActivity(), ZFrameActivity.class);
                intent.putExtra(Constant.key1, msgEntry);
                intent.putExtra(ZFrameActivity.CLASS_NAME, PushToFragment.class.getName());
                //问题出在这
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }  else {
                if (msgEntry.getMsgType() == 1) {//消息类型 0：文字，1：图片，2：语音
                    msgEntry.setMsgContent("[图片]");
                } else if (msgEntry.getMsgType() == 2) {
                    msgEntry.setMsgContent("[语音]");
                    try {
                        JSONObject vObj = new JSONObject(msgEntry.getMsgUrl());
                        String url = vObj.getString("url");
                        String time = vObj.getString("time");
                        msgEntry.setMsgUrl(url);
                        msgEntry.setVoiceTime(Integer.parseInt(time));
                    } catch (JSONException e) {
                    }
                }
                LogUtils.i("msg", "------收到透传消息-----" + msgEntry.toString());
                BaseDaoImpl talkDao = new BaseDaoImpl(TableTalk.class);
                BaseDaoImpl chatDao = new BaseDaoImpl(TableChat.class);
                try {
                    TableTalk talk = null;
                    Object obj = talkDao.queryForFirst("mxcomeNo", msgEntry.getSrcMxcomeNo());
                    if (obj == null) {
                        talk = new TableTalk();
                        talk.setCid(msgEntry.getSrcCid());
                        talk.setMxcomeNo(msgEntry.getSrcMxcomeNo());
                        talk.setGroupName(msgEntry.getSrcName());
                        talk.setIcon(msgEntry.getSrcPhotoUrl());
                        talk.setLastMsg(msgEntry.getMsgContent());
                        talk.setLastTime(msgEntry.getMsgTime());
                        talk.setBizType(msgEntry.getBizType());  //业务类型 0:官方，1:客户，2:好友
                        talk.setUnreadCnt(1);
                        talk.setMeMxcomeNo(msgEntry.getDestMxcomeNo());
                        talk.setSpecial("");
                        talkDao.save(talk);
                        obj = talkDao.queryForFirst("mxcomeNo", msgEntry.getSrcMxcomeNo());
                        if (obj != null && obj instanceof TableTalk) {
                            talk = (TableTalk) obj;
                        }
                    } else {
                        talk = (TableTalk) obj;
                        int unreadCnt = talk.getUnreadCnt() + 1;
                        talk.setUnreadCnt(unreadCnt);
                        talk.setGroupName(msgEntry.getSrcName());
                        talk.setLastMsg(msgEntry.getMsgContent());
                        talk.setLastTime(msgEntry.getMsgTime());
                        talk.setIcon(msgEntry.getSrcPhotoUrl());
                        talkDao.update(talk);
                    }
                    if (talk != null) {
                        TableChat chat = new TableChat();
                        chat.setGroupId(talk.getGroupId());
                        chat.setMsgContent(msgEntry.getMsgContent());
                        chat.setName(msgEntry.getSrcName());
                        chat.setMsgType(msgEntry.getMsgType());
                        chat.setUserType(0);
                        chat.setMsgTime(msgEntry.getMsgTime());
                        chat.setMsgUrl(msgEntry.getMsgUrl());
                        chat.setVoiceTime(msgEntry.getVoiceTime());
                        chat.setUnread(0);
                        chatDao.save(chat);
                        if (chat.getMsgType() == 2) {
                            download(chat);
                        }
                        createNotifi(talk);
                        EventBus.getDefault().post(talk);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Utils.queryUnreadCnt();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OrderEntry order) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ShareUtils.showShareDialog(getCurrentActivity(), "分享红包", "123456789");
            }
        }, 800);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
    }

    public void createNotifi(TableTalk talk) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(talk.getGroupName());
        if (talk.getUnreadCnt() > 1) {
            mBuilder.setContentText("[" + talk.getUnreadCnt() + "]" + talk.getLastMsg());
        } else {
            mBuilder.setContentText(talk.getLastMsg());
        }
        mBuilder.setAutoCancel(true);
        mBuilder.setOnlyAlertOnce(false);
        Intent intent = new Intent();
        intent.setClassName(this, "com.fenazola.mxcomemc.MainActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setDefaults(Notification.DEFAULT_ALL);
        NotificationManager nm = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(talk.getGroupId(), mBuilder.build());
    }

    public void download(final TableChat chat) {
        File file = new File(FileUtils.getExtDir(instance) + Constant.recorderDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        OkHttpUtils.download(Constant.imageUrl + chat.getMsgUrl(), file.getPath(), new OkHttpUtils.IDownloadCallback() {
            @Override
            public void onSuccess(String s) {
                BaseDaoImpl chatDao = new BaseDaoImpl(TableChat.class);
                chat.setLocalUrl(s);
                try {
                    chatDao.update(chat);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onProgress(long l, long l1, int i) {

            }

            @Override
            public void onError(String s) {

            }
        });
    }

    public int getActivityCounter() {
        return activityCounter;
    }

    public void setActivityCounter(int activityCounter) {
        this.activityCounter = activityCounter;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
