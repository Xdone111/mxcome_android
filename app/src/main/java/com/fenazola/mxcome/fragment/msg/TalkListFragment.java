package com.fenazola.mxcome.fragment.msg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableTalk;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DateUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.badgeview.BadgeView;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.swipelayout.SwipeRevealLayout;
import com.zss.library.swipelayout.ViewBinderHelper;
import com.zss.library.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息会话页
 */
public class TalkListFragment extends BaseFragment {

    private ListView listView;
    private CommonAdapter<TableTalk> adapter;
    private List<TableTalk> datas;
    private ViewBinderHelper binderHelper;
    private final int REQ_CODE = 0x01;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView)findViewById(R.id.listView);
        binderHelper = new ViewBinderHelper();
        adapter = new CommonAdapter<TableTalk>(getActivity(), R.layout.listitem_msg_talk) {
            @Override
            protected void convert(ViewHolder viewHolder, final TableTalk item, int position) {
                BadgeView badgeView = viewHolder.findViewById(R.id.badgeView);
                badgeView.setBadgeCount(item.getUnreadCnt());
                CircleImageView icon = viewHolder.findViewById(R.id.common_img_icon);
                Glide.with(getContext()).load(Constant.imageUrl + item.getIcon()).placeholder(R.mipmap.msg_default_icon).into(icon);
                TextView title = viewHolder.findViewById(R.id.common_tv_title);
                title.setText(item.getGroupName());
                TextView summary = viewHolder.findViewById(R.id.common_tv_summary);
                summary.setText(item.getLastMsg());
                TextView status = viewHolder.findViewById(R.id.common_tv_status);
                status.setText(DateUtils.getTalkTime(item.getLastTime()));
                SwipeRevealLayout swipe_layout = viewHolder.findViewById(R.id.swipe_layout);
                binderHelper.bind(swipe_layout, String.valueOf(position));
                binderHelper.setOpenOnlyOne(true);
                viewHolder.findViewById(R.id.delete_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            BaseDaoImpl talkDao = new BaseDaoImpl(TableTalk.class);
                            talkDao.delete(item);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        datas.remove(item);
                        adapter.replaceAll(datas);
                    }
                });
                viewHolder.findViewById(R.id.item_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                        intent.putExtra(Constant.key1, item);
                        intent.putExtra(ZFrameActivity.CLASS_NAME, ChatRecyclerFragment.class.getName());
                        startActivityForResult(intent, REQ_CODE);
                    }
                });
            }
        };
        listView.setAdapter(adapter);
        Utils.setEmptyView(listView);
    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        EventBus.getDefault().register(this);
        refreshMsg();
    }

    private void refreshMsg(){
        Bundle args = getArguments();
        String bizType = args.getString(Constant.key1);
        LogUtils.i("test", "----- bizType = " + bizType);
        BaseDaoImpl talkDao = new BaseDaoImpl(TableTalk.class);
        try {
            UserEntry user = Utils.getUserEntry();
            if(user != null){
                datas = talkDao.query(new String[]{"meMxcomeNo", "bizType"}, new String[]{user.getMxcome_no(), bizType});
            }else{
                datas = new ArrayList<>();
            }
        } catch (SQLException e) {
            datas = new ArrayList<>();
        }
        adapter.replaceAll(datas);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TableTalk msg){
        LogUtils.i("test", "-------msg talk = " + msg.getUnreadCnt());
        refreshMsg();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshMsg();
                }
            }, 500);
        }
    }
}
