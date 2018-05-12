package com.fenazola.mxcome.fragment.ransfer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.fenazola.mxcome.MediaPlayerActivity;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.ransfer.MediaDiversityEntry;
import com.fenazola.mxcome.entry.ransfer.MediaEntry;
import com.fenazola.mxcome.utils.DateUtils;
import com.fenazola.mxcome.widget.CenterDialog;
import com.fenazola.mxcome.widget.MyListView;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.LogUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 传递 媒体
 * Created by XDONE on 2017/3/18.
 */

public class MediaFragment extends BaseFragment implements View.OnClickListener{
    public static final String IntentFilterAction="com.mxcome.change.player";
    /** 底部评论列表*/
    private MyListView listView;
    /** 评论类表数据*/
    private CommonAdapter<MediaEntry> adapter;
    /** 分集按钮*/
    private TextView diversityTv;
    /**点赞 */
    private ImageView thumbsUpIv;
    /**收藏 */
    private ImageView collectionIv;
    /**编辑评论 */
    private ImageView commentIv;
    /** 视频播放组件*/
    private VideoView videoView;
    /**播放or暂停annual */
    private ImageView playPauseIv;
    /** 播放进度*/
    private TextView nowProgressTv;
    /** 播放进度条 */
    private SeekBar progressBar;
    /** 总进度*/
    private TextView totalLengthTv;
    /** 全屏*/
    private ImageView fullScreenIv;
    /** 选集浮动层*/
    private FrameLayout diversityFy;
    /** 选集列表*/
    private GridView diversityGv;
    /**播放以前的初始化图片*/
    private ImageView videoInitIv;
    /**视频标题*/
    private TextView videoTitleTv;
    /** 分集数据*/
    private CommonAdapter<MediaDiversityEntry> diversityAdapter;
    /**评论数据*/
    List<MediaEntry> datas = new ArrayList<>();
    /**分集评论数据*/
    ArrayList<MediaDiversityEntry> diversityDatas = new ArrayList<>();
    /**是否在播放*/
    private boolean isPlaying;
    /**播放进度*/
    private int currPost=0;
    /**用于做延迟加载*/
    private Handler handler=null;
    /**播放条目的下标*/
    private int diversityIndex=0;
    RelativeLayout show_line_bottom;
    EditText editText_comm;
    TextView show_next;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ransfer_media;
    }

    @Override
    public void initView() {
        super.initView();
        handler=new Handler();
        listView = (MyListView) findViewById(R.id.listView);

        diversityTv=(TextView)findViewById(R.id.to_diversity_tv);
        thumbsUpIv=(ImageView) findViewById(R.id.thumbs_up_iv);
        collectionIv=(ImageView) findViewById(R.id.collection_iv);
        commentIv=(ImageView) findViewById(R.id.comment_iv);
        videoView=(VideoView) findViewById(R.id.video_view);
        playPauseIv=(ImageView) findViewById(R.id.play_pause_iv);
        nowProgressTv=(TextView) findViewById(R.id.now_progress_tv);
        progressBar=(SeekBar) findViewById(R.id.progressBar);
        totalLengthTv=(TextView)findViewById(R.id.total_length_tv);
        fullScreenIv=(ImageView) findViewById(R.id.full_screen_iv);
        diversityFy=(FrameLayout) findViewById(R.id.select_diversity_fy);
        diversityGv=(GridView) findViewById(R.id.diversity_gv);
        videoInitIv=(ImageView)findViewById(R.id.init_video_iv);
        videoTitleTv=(TextView)findViewById(R.id.video_title_tv);
        diversityTv.setOnClickListener(this);
        thumbsUpIv.setOnClickListener(this);
        collectionIv.setOnClickListener(this);
        commentIv.setOnClickListener(this);
        playPauseIv.setOnClickListener(this);
        fullScreenIv.setOnClickListener(this);
        diversityFy.setOnClickListener(this);
        progressBar.setOnSeekBarChangeListener(change);
        IntentFilter intentFilter = new IntentFilter( IntentFilterAction);
        getActivity().registerReceiver( myBroadcastReceiver , intentFilter);
        show_line_bottom=(RelativeLayout)findViewById(R.id.show_line_bottom);
        editText_comm=(EditText)findViewById(R.id.editText_comm);
        show_next=(TextView)findViewById(R.id.show_next);
        show_next.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        adapter = new CommonAdapter<MediaEntry>(getActivity(), R.layout.listitem_ransfer_media) {

            @Override
            protected void convert(ViewHolder viewHolder, MediaEntry mediaEntry, int i) {
                ImageView image = viewHolder.findViewById(R.id.image);
                TextView content = viewHolder.findViewById(R.id.content);
                content.setText(mediaEntry.getContent());
                TextView time = viewHolder.findViewById(R.id.time);
                time.setText(mediaEntry.getTime());
                ImageView icon = viewHolder.findViewById(R.id.icon);
                TextView number = viewHolder.findViewById(R.id.number);
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getItem(position);
            }
        });
        //TODO 模拟评论数据啦
        MediaEntry item = new MediaEntry();
        item.setContent("放心吧，我们是有售后服务保障的...");
        item.setNumber(100);
        item.setNike("地球网友");
        item.setTime("1小时前");
        datas.add(item);
        datas.add(item);
        datas.add(item);
        datas.add(item);
        adapter.addAll(datas);


        diversityAdapter = new CommonAdapter<MediaDiversityEntry>(getActivity(), R.layout.listitem_ransfer_media_diversity) {

            @Override
            protected void convert(ViewHolder viewHolder, MediaDiversityEntry mediaEntry, int i) {
                TextView number = viewHolder.findViewById(R.id.reply);
                number.setText(mediaEntry.getIndex());
                if(i==diversityIndex){
                    number.setBackgroundResource(R.drawable.rect_shape_blue_bg_dis);
                }else{
                    number.setBackgroundColor(getResources().getColor(R.color.diversity_item_color));
                }
            }
        };
        diversityGv.setAdapter(diversityAdapter);
        diversityGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diversityAdapter.getItem(position);
            }
        });
        //TODO 模拟分集数据
        for (int i=0;i<12;i++) {
            //String content, String time, String index, String path
            MediaDiversityEntry mediaDiversityEntry = new MediaDiversityEntry("剧集"+i,"标题"+i,""+i,"http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4");
            diversityDatas.add(mediaDiversityEntry);
        }

        diversityAdapter.addAll(diversityDatas);
        diversityGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(diversityIndex==position){
                    if(videoView != null && videoView.isPlaying()) {
                        return;
                    }
                }
                diversityIndex=position;
                currPost=0;
                initPlayer(diversityIndex,currPost);
                videoInitIv.setVisibility(View.GONE);
                videoTitleTv.setVisibility(View.GONE);

                playPauseIv.setImageResource(R.mipmap.ransfer_media_pause);

                videoTitleTv.setText(diversityDatas.get(diversityIndex).getContent());
                play(diversityDatas.get(diversityIndex).getPath(),currPost);
                diversityAdapter.notifyDataSetChanged();
            }
        });
        if(diversityDatas!=null&&diversityDatas.size()>0){
            videoTitleTv.setText(diversityDatas.get(diversityIndex).getContent());
            //TODO 可能涉及到预加载图片的切换
        }
    }

    private void initPlayer(int diversityIndex, int currPost) {
        videoInitIv.setVisibility(View.GONE);
        videoTitleTv.setVisibility(View.GONE);

        playPauseIv.setImageResource(R.mipmap.ransfer_media_pause);

        videoTitleTv.setText(diversityDatas.get(diversityIndex).getContent());
        play(diversityDatas.get(diversityIndex).getPath(),currPost);
        diversityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {//TODO 未完待续
        switch (v.getId()){
            case R.id.to_diversity_tv:
                if(diversityFy.getVisibility()==View.VISIBLE){
                    diversityFy.setVisibility(View.GONE);
                }else{
                    diversityFy.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.thumbs_up_iv:
                Toast toast=Toast.makeText(getContext(),"点赞成功",Toast.LENGTH_SHORT);
                //toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                break;
            case R.id.collection_iv:
                CenterDialog dialog=new CenterDialog(getContext());
                dialog.setContent("收藏成功");
                dialog.show();
                break;
            case R.id.comment_iv:

                show_line_bottom.setVisibility(View.VISIBLE);
                //editText_comm.setFocusable(true);
//                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.show_next:
                show_line_bottom.setVisibility(View.GONE);
                //editText_comm.setFocusable(false);
                break;
            case R.id.play_pause_iv:
                if(videoView != null && videoView.isPlaying()){
                    playPauseIv.setImageResource(R.mipmap.ransfer_media_play);
                    currPost=videoView.getCurrentPosition();
                    videoView.pause();
                    isPlaying=false;
                }else{
                    videoInitIv.setVisibility(View.GONE);
                    videoTitleTv.setVisibility(View.GONE);
                    playPauseIv.setImageResource(R.mipmap.ransfer_media_pause);
                    play(diversityDatas.get(diversityIndex).getPath(),currPost);
                }
                break;
            case R.id.full_screen_iv:
                if(videoView != null && videoView.isPlaying()) {
                    Intent intent = new Intent(getActivity(), MediaPlayerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("content", diversityDatas);
                    bundle.putInt("index", diversityIndex);
                    currPost=videoView.getCurrentPosition();
                    bundle.putInt("currPost", currPost);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);
                }
                break;
            case R.id.select_diversity_fy:
                diversityFy.setVisibility(View.GONE);
                break;
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver( myBroadcastReceiver);

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
        LogUtils.i("XHX","初始化播放器了");
        //开始播放
        videoView.start();

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
                        progressBar.setMax(videoView.getDuration());
                        progressBar.setProgress(current);
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
                if(diversityIndex<diversityDatas.size()-1){
                    diversityIndex+=1;
                    currPost=0;
                    videoTitleTv.setText(diversityDatas.get(diversityIndex).getContent());
                    play(diversityDatas.get(diversityIndex).getPath(),currPost);
                    diversityAdapter.notifyDataSetChanged();
                }else{
                    videoInitIv.setVisibility(View.VISIBLE);
                    videoTitleTv.setVisibility(View.VISIBLE);
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
            //更新界面播放进度的文字标识
            if(videoView.getDuration()!=-1) {
                totalLengthTv.setText(DateUtils.getTimeString(videoView.getDuration()));
            }
            nowProgressTv.setText(DateUtils.getTimeString(videoView.getCurrentPosition()));

        }

    };
    @Override
    public void onPause() {
        super.onPause();

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1&&requestCode== Activity.RESULT_OK){
//
//        }
//    }
    // 广播接收者 - 广播的接收
    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent data) {
            // 相关处理，如收短信，监听电量变化信息
            //videoView=null;
            diversityIndex=data.getIntExtra("index",0);
            currPost=data.getIntExtra("currPost",0);
            LogUtils.i("XHX","下标："+diversityIndex+";进度："+currPost);
            initPlayer(diversityIndex,currPost);
        }

    };
}
