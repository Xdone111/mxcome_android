package com.fenazola.mxcome.fragment.festdeal;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.adapter.FastDealAdapter;
import com.fenazola.mxcome.adapter.MsgRecyclerAdapter;
import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableChat;
import com.fenazola.mxcome.db.TableTalk;
import com.fenazola.mxcome.fragment.msg.ComplainFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.EmptyRecyclerView;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.LogUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 快速处理消息列表
 */
public class FastDealPagerListFragment extends BaseFragment implements View.OnClickListener {

    private TableTalk talk;
    private RecyclerView recyclerView;
    private FastDealAdapter adapter;
    private TextView tv_title;
    private TextView tv_complaint;
    private ImageView img_photo;
    private View empty_page;
    private BaseDaoImpl chatDao = new BaseDaoImpl(TableChat.class);

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_fast_deal_pager_list;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.topic_text_color1);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_complaint = (TextView) findViewById(R.id.tv_complaint);
        img_photo = (ImageView) findViewById(R.id.img_photo);
        empty_page = findViewById(R.id.empty_page);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        tv_title.setOnClickListener(this);
        tv_complaint.setOnClickListener(this);
        img_photo.setOnClickListener(this);
        Bundle args = getArguments();
        if(args!=null && args.containsKey(Constant.key1)){
            talk = (TableTalk)args.getSerializable(Constant.key1);
            if(talk.getBizType() == 0){
                tv_title.setText("官方公告");
                img_photo.setImageResource(R.mipmap.fast_dispose_official);
                if(talk.getGroupId() != -1){
                    empty_page.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else{
                    empty_page.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }else if(talk.getBizType() == 1){
                if(talk.getGroupId() != -1){
                    tv_title.setText(talk.getGroupName());
                    Glide.with(getContext()).load(Constant.imageUrl + talk.getIcon()).placeholder(R.mipmap.msg_default_icon).into(img_photo);
                    empty_page.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    //TODO
                }else{
                    tv_title.setText("");
                    img_photo.setImageResource(R.mipmap.no_msg_to_service);
                    empty_page.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    //
                }
            }else {
                if(talk.getGroupId() != -1){
                    tv_title.setText(talk.getGroupName());
                    Glide.with(getContext()).load(Constant.imageUrl + talk.getIcon()).placeholder(R.mipmap.msg_default_icon).into(img_photo);
                    empty_page.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else{
                    tv_title.setText("");
                    img_photo.setImageResource(R.mipmap.msg_default_icon_msg);
                    empty_page.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
            adapter = new FastDealAdapter(getActivity());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new FastDealAdapter.OnItemClickListener() {
                @Override
                public void onClickContent(View view, TableChat msg, int position) {
                    if(msg.getMsgType() == 2){
                        mediaRelease();
                        if(adapter.getVoicePlayPosition() != position){
                            startPlayer(msg);
                        }
                    }
                }

                @Override
                public void updateReadStatus(View view, TableChat msg, int position) {
//                    try {
//                        chatDao.update(msg);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
                }
            });
            refreshMsg();
        }
    }

    @Override
    public void onClick(View v) {
        ComplainFragment fragment = new ComplainFragment();
        addFragment(fragment);
    }

    public void refreshMsg(){
        try {
            List<TableChat> datas = chatDao.query(new String[]{"groupId", "userType", "unread"}, new String[]{talk.getGroupId()+"", "0", "0"});
            if(datas != null){
                adapter.replaceAll(datas);
            }
        } catch (SQLException e) {
        }
    }

    private MediaPlayer mPlayer = null;

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
                mPlayer.setDataSource(Constant.imageUrl + item);
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
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

}

