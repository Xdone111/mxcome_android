package com.fenazola.mxcome;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableArea;
import com.fenazola.mxcome.entry.InitEntry;
import com.fenazola.mxcome.entry.ParamsEntry;
import com.fenazola.mxcome.entry.ransfer.MediaDiversityEntry;
import com.fenazola.mxcome.fragment.ransfer.MediaFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DateUtils;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushManager;
import com.zss.library.activity.BaseActivity;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.SharedPrefUtils;
import com.zss.library.utils.StringUtils;
import com.zss.library.widget.CommonDialog;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 播放界面
 * Created by xhx on 2017/6/7.
 */

public class MediaPlayerActivity extends BaseActivity {
    /**视频播放容器*/
    private VideoView videoView;
    /**播放控制器*/
    private MediaController mController;
    /**传递过来的下标*/
    private int index;
    /**传递过来的进度*/
    private int currPost;
    /**是否在播放*/
    private boolean isPlaying;
    /**处理延迟加载的东西*/
    private Handler handler=null;
    /**分集数据*/
    List<MediaDiversityEntry> diversityDatas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.GONE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_media_player;
    }

    @Override
    public void initView() {
        super.initView();
        handler=new Handler();
        mController = new MediaController(this);
        videoView=(VideoView)findViewById(R.id.videoView);
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        initData();
    }

    public void initData() {
        diversityDatas=getIntent().getParcelableArrayListExtra("content");
        index=getIntent().getIntExtra("index",0);
        currPost=getIntent().getIntExtra("currPost",0);
        //将videoView与mediaController建立关联
        videoView.setMediaController(mController);
        //将mediaController与videoView建立关联
        mController.setMediaPlayer(videoView);
        play(diversityDatas.get(index).getPath(),currPost);

    }
    private SeekBar.OnSeekBarChangeListener change=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 当进度条停止修改的时候触发
            // 取得当前进度条的刻度
            int progress = seekBar.getProgress();
            if (videoView != null && videoView.isPlaying()) {
                // 设置当前播放的位置
                videoView.seekTo(progress);
                //修改时间
            }
        }
    };

    /**
     * 播放咯
     * @param path 地址
     * @param msec 播放进度
     */
    protected void play(String path,int msec) {
        //获取视频文件地址

        videoView.setVideoURI(Uri.parse(path));
        videoView.setMediaController(mController);
        //开始播放
        videoView.start();
        videoView.requestFocus();
        // 按照初始位置播放
        videoView.seekTo(msec);

        //修改时间
        // 开始线程，更新进度条的刻度
        new Thread() {
            @Override
            public void run() {
                try {
                    isPlaying = true;
                    while (isPlaying) {
                        // 如果正在播放，没0.5.毫秒更新一次进度条
                        int current = videoView.getCurrentPosition();

                        handler.post(runnableUi);
                        sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // 在播放完毕被回调 续播还是结束播放
                if(index<diversityDatas.size()-1){
                    index+=1;
                    currPost=0;
                    play(diversityDatas.get(index).getPath(),currPost);
                }
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                //TODO  发生错误重新播放
                play(diversityDatas.get(0).getPath(),0);
                isPlaying = false;
                return false;
            }
        });
    }
    // 构建Runnable对象，在runnable中更新界面
    Runnable   runnableUi=new  Runnable(){
        @Override
        public void run() {
            //更新界面
            if(videoView.getDuration()!=-1) {
            }
        }

    };

    @Override
    protected void onPause() {
        super.onPause();
//        currPost=videoView.getCurrentPosition();
//        videoView.pause();
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        currPost=videoView.getCurrentPosition();
        videoView.pause();

        Intent intent=new Intent();
        intent.putExtra("index",index);
        intent.putExtra("currPost",currPost);
        //setResult(RESULT_OK,intent);
        LogUtils.i("XHX","下标："+index+";进度："+currPost);
        intent.setAction(MediaFragment.IntentFilterAction);
        sendBroadcast(intent);
        finish();
    }

}
