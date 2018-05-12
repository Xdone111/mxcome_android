package com.fenazola.mxcome.fragment.festdeal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableChat;
import com.fenazola.mxcome.db.TableTalk;
import com.fenazola.mxcome.fragment.msg.ChatRecyclerFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.widget.NoScrollViewPager;
import com.fenazola.mxcome.widget.RadioGroupLayout;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.badgeview.BadgeView;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 快速处理
 */
public class FastDealFragment extends BaseFragment {

    private NoScrollViewPager mViewPager;
    private List<FastDealPagerFragment> mList = new ArrayList<>();
    private RadioGroupLayout mRadioGroup;
    private ImageView img_red_1, img_red_2, img_red_3;
    private TextView fast_left, fast_right;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_fast_deal;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.topic_text_color1);
        mRadioGroup = (RadioGroupLayout) findViewById(R.id.radioGroup);
        img_red_1 = (ImageView) findViewById(R.id.img_red_1);
        img_red_2 = (ImageView) findViewById(R.id.img_red_2);
        img_red_3 = (ImageView) findViewById(R.id.img_red_3);
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewPager);
        fast_left = (TextView)findViewById(R.id.fast_left);
        fast_right = (TextView)findViewById(R.id.fast_right);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle args1 = new Bundle();
        args1.putInt(Constant.key1, 0);
        FastDealPagerFragment fragment1 = new FastDealPagerFragment();
        fragment1.setArguments(args1);
        mList.add(fragment1);

        Bundle args2 = new Bundle();
        args2.putInt(Constant.key1, 1);
        FastDealPagerFragment fragment2 = new FastDealPagerFragment();
        fragment2.setArguments(args2);
        mList.add(fragment2);

        Bundle args3 = new Bundle();
        args3.putInt(Constant.key1, 2);
        FastDealPagerFragment fragment3 = new FastDealPagerFragment();
        fragment3.setArguments(args3);
        mList.add(fragment3);
        changItem(0);

        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }
            @Override
            public int getCount() {
                return mList.size();
            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroupLayout.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupLayout group, int checkedId) {
                if (checkedId == R.id.rb_fast_1) {
                    mViewPager.setCurrentItem(0);
                } else if (checkedId == R.id.rb_fast_2) {
                    mViewPager.setCurrentItem(1);
                } else if (checkedId == R.id.rb_fast_3) {
                    mViewPager.setCurrentItem(2);
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mRadioGroup.check(R.id.rb_fast_1);
                } else if (position == 1) {
                    mRadioGroup.check(R.id.rb_fast_2);
                } else if (position == 2) {
                    mRadioGroup.check(R.id.rb_fast_3);
                }
                changItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setOffscreenPageLimit(3);
        mRadioGroup.check(R.id.rb_fast_1);

        fast_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDaoImpl talkDao = new BaseDaoImpl(TableTalk.class);
                BaseDaoImpl chatDao = new BaseDaoImpl(TableChat.class);
                TableTalk talk = mList.get(mViewPager.getCurrentItem()).getCurrentTalk();
                if(talk.getGroupId() != -1){
                    try {
                        talk.setUnreadCnt(0);
                        talkDao.update(talk);
                        UpdateBuilder uBuilder = chatDao.getDao().updateBuilder();
                        uBuilder.updateColumnValue("unread", "1").where().eq("groupId", talk.getGroupId()).and().ne("msgType", "2").and().eq("unread", 0);
                        uBuilder.update();
                        mList.get(mViewPager.getCurrentItem()).refreshMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        fast_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableTalk talk = mList.get(mViewPager.getCurrentItem()).getCurrentTalk();
                if(talk.getGroupId() != -1){
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(Constant.key1, talk);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, ChatRecyclerFragment.class.getName());
                    startActivityForResult(intent, 0x01);
                }
            }
        });

    }

    private void changItem(int i) {
        if(i==0){
            fast_left.setText("历史公告");
            fast_right.setText("加入我们");
        }else {
            fast_left.setText(getString(R.string.fast_later_btn));
            fast_right.setText(getString(R.string.fast_deal_btn));
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setBgColor(getColor(R.color.colorBlue));
        //toolbar.setTitle(getString(R.string.fast_deal));
        toolbar.setTitle("消息秘书");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FastDealPagerFragment.UpdateUnreadStatus status){
        if(status.bizType == 0){
            img_red_1.setVisibility(status.visibility);
        }else if(status.bizType == 1){
            img_red_2.setVisibility(status.visibility);
        }else if(status.bizType == 2){
            img_red_3.setVisibility(status.visibility);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TableTalk msg){
        for(FastDealPagerFragment item : mList){
            item.refreshMsg();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x01){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mList.get(mViewPager.getCurrentItem()).refreshMsg();
                }
            }, 500);
        }
    }
}
