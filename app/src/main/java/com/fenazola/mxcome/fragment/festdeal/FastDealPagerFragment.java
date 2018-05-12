package com.fenazola.mxcome.fragment.festdeal;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableTalk;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.Utils;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 快速处理左右滑动
 */
public class FastDealPagerFragment extends BaseFragment {

    private List<TableTalk> talks  = new ArrayList<>();
    private List<Fragment> mList = new ArrayList<Fragment>();
    private ViewPager msgViewPager;
    private FragmentPagerAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_fast_deal_pager;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.topic_text_color1);
        msgViewPager = (ViewPager) findViewById(R.id.msgViewPager);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        adapter =  new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
        msgViewPager.setAdapter(adapter);
        refreshMsg();
    }

    public class UpdateUnreadStatus{
        public int bizType; //业务类型
        public int visibility; //显示或者隐藏
    }

    public TableTalk getCurrentTalk() {
        return talks.get(msgViewPager.getCurrentItem());
    }

    public void refreshMsg(){

        FragmentManager fm = getChildFragmentManager();
        if (mList.size() > 0) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.mList) {
                ft.remove(f);
            }
            ft.commit();
            fm.executePendingTransactions();
        }

        talks.clear();
        mList.clear();
        Bundle args = getArguments();
        int bizType = args.getInt(Constant.key1);
        BaseDaoImpl talkDao = new BaseDaoImpl(TableTalk.class);
        try {
            UserEntry user = Utils.getUserEntry();
            if(user != null){
                talks = talkDao.getDao().queryBuilder().where().eq("meMxcomeNo", user.getMxcome_no()).and().eq("bizType", bizType).and().gt("unreadCnt", "0").query();
            }else{
                talks = new ArrayList<>();
            }
        } catch (SQLException e) {
            talks = new ArrayList<>();
        }
        if(talks.size() == 0){
            TableTalk talk = new TableTalk();
            talk.setGroupId(-1);
            talk.setBizType(bizType);
            talks.add(talk);
            UpdateUnreadStatus status = new UpdateUnreadStatus();
            status.bizType = bizType;
            status.visibility = View.GONE;
            EventBus.getDefault().post(status);
        } else {
            UpdateUnreadStatus status = new UpdateUnreadStatus();
            status.bizType = bizType;
            status.visibility = View.VISIBLE;
            EventBus.getDefault().post(status);
        }
        LogUtils.i("---zss---", "------talks size = "+ talks.size());
        for (int i = 0; i < talks.size(); i++) {
            Bundle args1 = new Bundle();
            args1.putSerializable(Constant.key1, talks.get(i));
            FastDealPagerListFragment fragment = new FastDealPagerListFragment();
            fragment.setArguments(args1);
            mList.add(fragment);
        }
        LogUtils.i("---zss---", "------fragment size = "+ mList.size());
        adapter.notifyDataSetChanged();
    }

}

