package com.fenazola.mxcome.fragment.msg;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.adapter.MsgRecyclerAdapter;
import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableChat;
import com.fenazola.mxcome.db.TableTalk;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.fragment.common.BrowserFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DateUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.SendMsgLayout;
import com.igexin.sdk.PushManager;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.zss.library.PermissionCallBack;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.photopicker.PhotoPickerActivity;
import com.zss.library.photopicker.utils.PhotoUtils;
import com.zss.library.ptr.PtrClassicFrameLayout;
import com.zss.library.ptr.PtrDefaultHandler;
import com.zss.library.ptr.PtrFrameLayout;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.FileUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.PicUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.acl.Group;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  消息列表页
 *  @author zm
 */
public class ChatOnLineFragment extends BaseFragment {

    private PtrClassicFrameLayout mPtrFrame;
    private RecyclerView recyclerView;
    private MsgRecyclerAdapter adapter;
    private TableTalk talk=new TableTalk();
    private SendMsgLayout sendMsgLayout;
    private String tempFileUrl = null;
    private RelativeLayout center_layout;
    private TextView center_voice_time;
    private BaseDaoImpl talkDao = new BaseDaoImpl(TableTalk.class);
    private BaseDaoImpl chatDao = new BaseDaoImpl(TableChat.class);
    private long offset = 0;
    private long limit = 15;
    private Handler mHandler = new Handler();
    private ImageView showRecBg;
    private ImageView showRecIco;
    /**0 好友 1 群聊*/
    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_chat_recycler;
    }

    @Override
    public void initView() {
        super.initView();
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.mPtrFrame);
        recyclerView = (RecyclerView)findViewById(R.id.listView);
        sendMsgLayout = (SendMsgLayout)findViewById(R.id.sendMsgLayout);
        center_layout = (RelativeLayout) findViewById(R.id.center_layout);
        showRecBg = (ImageView) findViewById(R.id.show_is_rec_iv);
        showRecIco = (ImageView) findViewById(R.id.iv_input);
        center_layout.setVisibility(View.GONE);
//        ArcText arc = new ArcText(getActivity());
//        center_layout.addView(arc);
        center_voice_time = (TextView) findViewById(R.id.center_voice_time);
    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle args = getArguments();
        if(args!=null && args.containsKey(Constant.key1)){
            talk = (TableTalk)args.getSerializable(Constant.key1);
        }
        type=args.getInt(Constant.key2);
        adapter = new MsgRecyclerAdapter(getActivity(), talk);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true); //true代表反转显示
        layoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        sendMsgLayout.setOnMsgSendListener(new SendMsgLayout.OnMsgSendListener() {
            @Override
            public void onTextSend(String text) {
                sendTextMsg(text);
            }

            @Override
            public void onClickImage(boolean isCamera) {
                if(isCamera){
                    openCamera();
                } else {
                    openPhoto();
                }

            }

            @Override
            public void onVoiceStart() {
                LogUtils.i("---zss---", "-------onVoiceStart---------");
                center_layout.setVisibility(View.VISIBLE);
                startRecording();
            }

            @Override
            public void onVoiceSend(int voiceTime) {
                LogUtils.i("---zss---", "-------onVoiceSend---------");
                center_layout.setVisibility(View.GONE);
                if(!TextUtils.isEmpty(tempFileUrl)){
                    stopRecording();
                    sendVoiceMsg(voiceTime);
                }
            }

            @Override
            public void onVoiceCancel(boolean isUpFling) {
                LogUtils.i("---zss---", "-------onVoiceCancel---------");
                showRecBg.setImageResource(R.mipmap.rec_can_bg);
                showRecIco.setImageResource(R.mipmap.rec_can_img);
                center_voice_time.setTextColor(getColor(R.color.colorRed));
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        showRecBg.setImageResource(R.mipmap.rec_bg);
                        showRecIco.setImageResource(R.mipmap.rec_img);
                        center_voice_time.setTextColor(getColor(R.color.colorBlue));
                        center_layout.setVisibility(View.GONE);

                    }

                }, 2000);
                if(isUpFling){
                    CommonToastUtils.showInCenterToast(getContext(), "语音已取消");
                } else {
                    CommonToastUtils.showInCenterToast(getContext(), "语音太短");
                }
                stopRecording();
                if(!TextUtils.isEmpty(tempFileUrl)){
                    FileUtils.delete(tempFileUrl);
                    tempFileUrl = null;
                }
            }

            @Override
            public void onVoiceing(int voiceTime) {
                //String timeStr = String .format("%02d", voiceTime);
                //center_voice_time.setText(timeStr);
                center_voice_time.setText(DateUtils.getTimeString(voiceTime*1000));

            }
        });

        adapter.setOnItemClickListener(new MsgRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onClickReSend(View view, final TableChat msg, int position) {
                if(msg.getMsgType() == 0){ //文字重发
                    sendMsg(msg);
                } else if(msg.getMsgType() == 1){ //图片重发
                    Utils.uploadImage(getActivity(), msg.getLocalUrl(), new Utils.UrlCallback() {
                        @Override
                        public void onSuccess(String url) {
                            msg.setMsgUrl(url);
                            try {
                                chatDao.update(msg);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            sendMsg(msg);
                        }
                        @Override
                        public void onError() {
                            msg.setSendStatus(2);
                            try {
                                chatDao.update(msg);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            refreshMsg();
                        }
                    });
                } else {

                }
            }

            @Override
            public void onClickPhoto(View view, TableChat msg, int position) {

            }

            @Override
            public void onClickContent(View view, TableChat msg, int position) {
                if(msg.getMsgType() == 1){
                    String url = "";
                    if(msg.getMsgUrl() != null){
                        url = msg.getMsgUrl();
                    }
                    int pos = 0;
                    ArrayList<String> urls = new ArrayList<>();
                    List<TableChat> array;
                    try {
                        array = chatDao.query(new String[]{"groupId", "msgType"}, new String[]{talk.getGroupId()+"", "1"});
                    } catch (SQLException e) {
                        array = new ArrayList<>();
                    }
                    for(int i = 0; i<array.size(); i++){
                        TableChat chat = array.get(i);
                        if(url.equals(chat.getMsgUrl())){
                            pos = i;
                        }
                        urls.add(Constant.imageUrl + chat.getMsgUrl());
                    }
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, BrowserFragment.class.getName());
                    intent.putExtra(Constant.key1, pos);
                    intent.putStringArrayListExtra(Constant.key2, urls);
                    startActivity(intent);
                }else if(msg.getMsgType() == 2){
                    if(adapter.getVoicePlayPosition() == position){
                        mediaRelease();
                    }else{
                        mediaRelease();
                        startPlayer(msg);
                        adapter.setVoicePlayPosition(position);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void updateReadStatus(View view, TableChat msg, int position) {
                try {
                    chatDao.update(msg);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        mPtrFrame.setKeepHeaderWhenRefresh(false);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                offset++;
                loadMoreMsg();
            }
        });
        refreshMsg();
    }

    public void sendMsg(final TableChat chat){
        UserEntry user = Utils.getUserEntry();
        HashMap<String, String> map = new HashMap<>();
        map.put("destCid", talk.getCid()); //接受CID
        map.put("srcCid", PushManager.getInstance().getClientid(getContext())); //发送CID
        map.put("destName", chat.getName()); //接受昵称
        map.put("srcName", user.getUser_name()); //发送昵称
        map.put("destMxcomeNo", talk.getMxcomeNo()); //接受MXCOME号
        map.put("srcMxcomeNo", user.getMxcome_no()); //发送MXCOME号
        map.put("destPhotoUrl", talk.getIcon()); //接受头像
        map.put("srcPhotoUrl", user.getPic()); //发送头像
        map.put("msgContent", chat.getMsgContent()); //消息内容
        map.put("bizType", "1"); //业务类型 0:  官方  1：客户， 2：好友
        map.put("msgType", ""+chat.getMsgType());  //消息类型 0：文字，1：图片，2：语音
        map.put("msgTime", chat.getMsgTime()); //  消息时间
        if(chat.getMsgType() == 1){
            map.put("msgWidth",  chat.getMsgWidth()+"");
            map.put("msgHeight", chat.getMsgHeight()+"");
            map.put("msgUrl", chat.getMsgUrl());
        }else if(chat.getMsgType() == 2){
            try {
                JSONObject vObj = new JSONObject();
                vObj.put("url", chat.getMsgUrl());
                vObj.put("time", chat.getVoiceTime());
                map.put("msgUrl", vObj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            map.put("msgUrl", chat.getMsgUrl());
        }
        NetWorkUtils.post(getActivity(), "appjrc/doChatFromC2P.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                talk.setLastTime(chat.getMsgTime());
                talk.setLastMsg(chat.getMsgContent());
                EventBus.getDefault().post(talk);
                chat.setSendStatus(1);
                try {
                    talkDao.update(talk);
                    chatDao.update(chat);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                refreshMsg();
            }

            @Override
            public void onError(String result, String code, String msg) {
                talk.setLastTime(chat.getMsgTime());
                talk.setLastMsg(chat.getMsgContent());
                EventBus.getDefault().post(talk);
                chat.setSendStatus(2);
                try {
                    talkDao.update(talk);
                    chatDao.update(chat);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                refreshMsg();
            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.white));
        //toolbar.setTitle(talk.getGroupName());
        toolbar.setTitle("MXCOME");
        toolbar.setTitleColor(getColor(R.color.black));
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setRightImage(R.mipmap.online_right_top1);
//        toolbar.setOnRightListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(type==0) {
//                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
//                    intent.putExtra(ZFrameActivity.CLASS_NAME, ChatSetFragment.class.getName());
//                    startActivity(intent);
//
//                }else{
//
//                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
//                    intent.putExtra(ZFrameActivity.CLASS_NAME, MsgGroupSettingFragment.class.getName());
//                    startActivity(intent);
//                }
//            }
//        });
    }

    public void refreshMsg(){
        offset = 0;
        List<TableChat> list = new ArrayList<>();
        try {
            List<TableChat> datas = chatDao.getDao().queryBuilder()
                    .orderBy("msgTime", false).offset(offset*limit)
                    .limit(limit).where().eq("groupId", talk.getGroupId()).query();
            list.addAll(datas);
            if(talk.getUnreadCnt()>0){
                talk.setUnreadCnt(0);
                talkDao.update(talk);
                UpdateBuilder uBuilder = chatDao.getDao().updateBuilder();
                uBuilder.updateColumnValue("unread", "1").where().eq("groupId", talk.getGroupId()).and().ne("msgType", "2").and().eq("unread", 0);
                uBuilder.update();
            }
        } catch (SQLException e) {
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true); //true代表反转显示
        if(list.size() > 5){
            layoutManager.setStackFromEnd(false);
        }else{
            layoutManager.setStackFromEnd(true);
        }
        recyclerView.setLayoutManager(layoutManager);
        adapter.replaceAll(list);
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.smoothScrollToPosition(0);
                }
            }, 400);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertMsg(TableChat item){
        adapter.insertMsg(item);
    }

    public void loadMoreMsg(){
        List<TableChat> list = new ArrayList<>();
        try {
            List<TableChat> datas = chatDao.getDao().queryBuilder()
                    .orderBy("msgTime", false).offset(offset*limit)
                    .limit(limit).where().eq("groupId", talk.getGroupId()).query();
            list.addAll(datas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(list.size() == 0){
            mPtrFrame.setMode(PtrFrameLayout.Mode.NONE);
        }
        adapter.getData().addAll(list);
        adapter.notifyDataSetChanged();
        mPtrFrame.refreshComplete();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        if(mTmpFile != null && mTmpFile.exists()){
            mTmpFile.delete();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TableTalk msg){
        talk = msg;
        refreshMsg();
    }

    public void openPhoto(){
        verifyPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , 0x01, new PermissionCallBack() {

                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, false);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
                        startActivityForResult(intent, REQUEST_PHOTO);
                    }

                    @Override
                    public void onDenied() {
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i("---zss---", "---requestCode = " + requestCode + "---resultCode = " + resultCode);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PHOTO) {
            if (data.getExtras() != null && data.getExtras().containsKey(PhotoPickerActivity.KEY_RESULT)) {
                List<String> results = (List<String>) data.getExtras().get(PhotoPickerActivity.KEY_RESULT);
                if (results != null && results.size() > 0) {
                    String fileUrl = results.get(0);
                    File file = new File(FileUtils.getExtDir(getActivity()) + Constant.imageDir );
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    String targetPath = file.getPath() + File.separator+ "image_" + System.currentTimeMillis() + ".jpg";
                    fileUrl = PicUtils.compressImage(fileUrl, targetPath, 50);
                    sendImageMsg(fileUrl);
                }
            }
        }else if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA) {
            String fileUrl = mTmpFile.getAbsolutePath();
            File file = new File(FileUtils.getExtDir(getActivity()) + Constant.imageDir );
            if(!file.exists()){
                file.mkdirs();
            }
            String targetPath = file.getPath() + File.separator+ "image_" + System.currentTimeMillis() + ".jpg";
            fileUrl = PicUtils.compressImage(fileUrl, targetPath, 50);
            sendImageMsg(fileUrl);
        }
    }

    public void sendTextMsg(String text){
        UserEntry entry = Utils.getUserEntry();
        TableChat chat = new TableChat();
        chat.setGroupId(talk.getGroupId()); //会话ID
        chat.setMsgContent(text); //消息
        chat.setName(entry.getUser_name()); //名称
        chat.setMsgType(0); //消息类型 0：文字，1：图片，2：语音：3: 视频
        chat.setUserType(1); //用户类型 0：别人，1：自己
        chat.setMsgTime(DateUtils.getCurrentTime()); //时间
        chat.setMsgUrl(""); //图片、语音不为空
        chat.setSendStatus(0);
        try {
            chatDao.save(chat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        insertMsg(chat);
        sendMsg(chat);
    }

    public void sendImageMsg(String fileUrl){
        UserEntry entry = Utils.getUserEntry();
        final TableChat chat = new TableChat();
        chat.setGroupId(talk.getGroupId()); //会话ID
        chat.setMsgContent("[图片]"); //消息
        chat.setName(entry.getUser_name()); //名称
        chat.setMsgType(1); //消息类型 0：文字，1：图片，2：语音
        chat.setUserType(1); //用户类型 0：别人，1：自己
        chat.setMsgTime(DateUtils.getCurrentTime()); //时间
        chat.setLocalUrl(fileUrl); //图片、语音不为空
        chat.setSendStatus(0);
        Bitmap bitmap = PicUtils.getSmallBitmap(fileUrl);
        chat.setMsgWidth(bitmap.getWidth());
        chat.setMsgHeight(bitmap.getHeight());
        bitmap.recycle();
        try {
            chatDao.save(chat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LogUtils.i("---zss---", "--------" + chat.toString());
        insertMsg(chat);

        Utils.uploadImage(getActivity(), fileUrl, new Utils.UrlCallback() {
            @Override
            public void onSuccess(String url) {
                chat.setMsgUrl(url);
                try {
                    chatDao.update(chat);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                sendMsg(chat);
            }

            @Override
            public void onError() {
                chat.setSendStatus(2);
                try {
                    chatDao.update(chat);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                refreshMsg();
            }
        });
    }

    public void sendVoiceMsg(int voiceTime){
        UserEntry entry = Utils.getUserEntry();
        final TableChat chat = new TableChat();
        chat.setGroupId(talk.getGroupId()); //会话ID
        chat.setMsgContent("[语音]"); //消息
        chat.setName(entry.getUser_name()); //名称
        chat.setMsgType(2); //消息类型 0：文字，1：图片，2：语音
        chat.setUserType(1); //用户类型 0：别人，1：自己
        chat.setMsgTime(DateUtils.getCurrentTime()); //时间
        chat.setLocalUrl(tempFileUrl); //图片、语音不为空
        chat.setVoiceTime(voiceTime); //语音时间
        chat.setSendStatus(0);
        try {
            chatDao.save(chat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        insertMsg(chat);
        File file = new File(tempFileUrl);
        Map<String, String> map = new HashMap<>();
        map.put("user_id", entry.getUser_id());
        NetWorkUtils.upload(getActivity(), "appjrc/doUploadMultimedia.do", "file", file, map, new NetWorkUtils.IProgressListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject res = resObj.optJSONObject("res");
                String url = res.optString("fileId");
                chat.setMsgUrl(url);
                try {
                    chatDao.update(chat);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                sendMsg(chat);
            }
            @Override
            public void onProgress(int index, long totalBytes, long surplusBytes, boolean done) {

            }
            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    //******************* 录音 ************************

    private MediaPlayer mPlayer = null;
    private MediaRecorder mRecorder = null;

    //开始录音
    public void startRecording(){
        verifyPermissions(getActivity(), Manifest.permission.RECORD_AUDIO,
                new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , 0x01, new PermissionCallBack() {

                    @Override
                    public void onGranted() {
                        if(center_layout.getVisibility() == View.VISIBLE){
                            File file = new File(FileUtils.getExtDir(getActivity()) + Constant.recorderDir );
                            if(!file.exists()){
                                file.mkdirs();
                            }
                            boolean highQuality = false;
                            tempFileUrl = file.getPath() + File.separator+ "recorder_" + System.currentTimeMillis() + ".amr";
                            int outputfileformat = highQuality ? MediaRecorder.OutputFormat.AMR_WB: MediaRecorder.OutputFormat.AMR_NB;
                            mRecorder = new MediaRecorder();
                            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            mRecorder.setAudioSamplingRate(highQuality ? 16000 : 8000);
                            mRecorder.setOutputFormat(outputfileformat);
                            mRecorder.setAudioEncoder(highQuality ? MediaRecorder.AudioEncoder.AMR_WB: MediaRecorder.AudioEncoder.AMR_NB);
                            mRecorder.setOutputFile(tempFileUrl);
                            try {
                                mRecorder.prepare();
                                mRecorder.start();
                            } catch (IOException e) {
                            }
                        }
                    }
                    @Override
                    public void onDenied() {
                    }
                });

    }

    /**
     * 停止录音
     */
    private void stopRecording() {
        if (mRecorder != null) {
            try {
                mRecorder.stop();
            } catch (Exception e) {
                try {
                    Thread.sleep(100);
                } catch (Exception e1) {
                }
                try {
                    mRecorder.stop();
                } catch (Exception e2) {
                }
            }finally {
                mRecorder.reset();
                mRecorder.release();
                mRecorder = null;
            }
        }
    }

    private void startPlayer(TableChat item){
        LogUtils.i("---zss---", "------- item = " + item.toString());
        mPlayer = new MediaPlayer();
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mediaRelease();
                return true;
            }
        });
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaRelease();
            }
        });
        try {
            if(!TextUtils.isEmpty(item.getLocalUrl())){
                mPlayer.setDataSource(item.getLocalUrl());
            }else{
                mPlayer.setDataSource(Constant.imageUrl + item.getMsgUrl());
            }
            AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mPlayer.setLooping(false);
                mPlayer.prepare();
                mPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mediaRelease(){
        adapter.setVoicePlayPosition(-1);
        adapter.notifyDataSetChanged();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

//    class ArcText extends View {
//        private Paint paint;
//        private Path paths;
//
//        public ArcText(Context context) {
//            super(context);
//            paths = new Path();
//            int width = DPUtils.dip2px(getActivity(), 108);
//
//            int top = DPUtils.dip2px(getActivity(), 20);
//
//            int left = DPUtils.dip2px(getActivity(), 27);
//
//            RectF rectF = new RectF(left, top, width+left/2, width);
//
//            //开始角度，需要画范围
//            paths.addArc(rectF, 210, 120);
//            paint = new Paint();
//            paint.setColor(Color.WHITE);
//            paint.setStrokeWidth(2);
//            paint.setTextSize(DPUtils.dip2px(getActivity(), 12));
//        }
//
//        @Override
//        protected void onDraw(Canvas canvas) {
////            paint.setStyle(Paint.Style.STROKE);
////            // 绘制路径
////            canvas.drawPath(paths, paint);
//            paint.setStyle(Paint.Style.FILL);
//            // 绘制文字按照路径
//            int padding = DPUtils.dip2px(getActivity(), 6);
//            canvas.drawTextOnPath("手指上滑 取消发送", paths, 10, -padding, paint);
//
//        }
//    }

    public File mTmpFile = null;
    public final static int REQUEST_CAMERA = 0x01;
    public final static int REQUEST_PHOTO = 0x02;

    /**
     * 选择相机
     */
    public void openCamera() {
        verifyPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}
                , 0x01, new PermissionCallBack() {

                    @Override
                    public void onGranted() {
                        // 跳转到系统照相机
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(cameraIntent.resolveActivity(getActivity().getPackageManager()) != null){
                            // 设置系统相机拍照后的输出路径
                            // 创建临时文件
                            mTmpFile = PhotoUtils.createFile(getActivity().getApplicationContext());
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                            startActivityForResult(cameraIntent, REQUEST_CAMERA);
                        }else{
                            CommonToastUtils.showInCenterToast(getActivity().getApplicationContext(),
                                    getString(com.zss.library.R.string.msg_no_camera));
                        }
                    }

                    @Override
                    public void onDenied() {
                    }
                });
    }

}
