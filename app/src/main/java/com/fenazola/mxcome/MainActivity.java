package com.fenazola.mxcome;


import android.content.Intent;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fenazola.iframe.baidu.LocationService;
import com.fenazola.mxcome.entry.LocationEntry;
import com.fenazola.mxcome.fragment.MainFragment;
import com.fenazola.mxcome.fragment.ProjectFragment;
import com.fenazola.mxcome.fragment.MsgFragment;
import com.fenazola.mxcome.fragment.RansferFragment;
import com.fenazola.mxcome.fragment.MeFragment;
import com.fenazola.mxcome.fragment.festdeal.FastDealFragment;
import com.fenazola.mxcome.fragment.main.location.LocationFragment;
import com.fenazola.mxcome.fragment.me.setting.SettingFragment;
import com.fenazola.mxcome.fragment.msg.AddFriendFragment;
import com.fenazola.mxcome.fragment.msg.FriendDpecialAttentionFragment;
import com.fenazola.mxcome.fragment.msg.MsgSeachFragment;
import com.fenazola.mxcome.fragment.ransfer.RansferHistoryFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.Utils;
import com.zss.library.PermissionCallBack;
import com.zss.library.activity.BaseActivity;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.badgeview.BadgeView;
import com.zss.library.tabhost.FragmentTabHost;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.LogUtils;


import android.os.Handler;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 主界面
 *
 * @author zm
 */
public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {

    private FragmentTabHost mTabHost;
    private View tab1, tab2, tab3, tab4, tab5;
    private CommonToolbar toolbar;
    private BadgeView badgeView;
    int currentTab = -1;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();

        toolbar = getToolbar();

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getSupportFragmentManager(), R.id.realtabcontent);

        tab1 = getLayoutInflater(R.layout.layout_tab_item);
        TextView textTab = (TextView) tab1.findViewById(R.id.text_tab);
        textTab.setText("主页");
        tab2 = getLayoutInflater(R.layout.layout_tab_item);
        textTab = (TextView) tab2.findViewById(R.id.text_tab);
        textTab.setText("工程");
        tab3 = getLayoutInflater(R.layout.layout_tab_item);
        textTab = (TextView) tab3.findViewById(R.id.text_tab);
        textTab.setText("消息");
        tab4 = getLayoutInflater(R.layout.layout_tab_item);
        textTab = (TextView) tab4.findViewById(R.id.text_tab);
        textTab.setText("传递");
        tab5 = getLayoutInflater(R.layout.layout_tab_item);
        textTab = (TextView) tab5.findViewById(R.id.text_tab);
        textTab.setText("我");

        ImageView imgTab1 = (ImageView) tab1.findViewById(R.id.img_tab);
        imgTab1.setImageResource(R.drawable.tab1);
        ImageView imgTab2 = (ImageView) tab2.findViewById(R.id.img_tab);
        imgTab2.setImageResource(R.drawable.tab2);
        ImageView imgTab3 = (ImageView) tab3.findViewById(R.id.img_tab);
        imgTab3.setImageResource(R.drawable.tab3);
        ImageView imgTab4 = (ImageView) tab4.findViewById(R.id.img_tab);
        imgTab4.setImageResource(R.drawable.tab4);
        ImageView imgTab5 = (ImageView) tab5.findViewById(R.id.img_tab);
        imgTab5.setImageResource(R.drawable.tab5);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(tab1), MainFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(tab2), ProjectFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator(tab3), MsgFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator(tab4), RansferFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab5").setIndicator(tab5), MeFragment.class, null);

        mTabHost.setOnTabChangedListener(this);

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        setTopTab1();
        Utils.queryUnreadCnt();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        EventBus.getDefault().register(this);
        badgeView = new BadgeView(this);
        badgeView.setTargetView(toolbar);
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        badgeView.setBadgeMargin(0, 6, 6, 0);
        badgeView.setTextColor(getColor(getActivity(), R.color.colorBlue));
        badgeView.setBackground(10, getColor(getActivity(), R.color.white));

        locatioinPermissions();
    }

    @Override
    public void onTabChanged(String tabId) {
        if (tabId.equals("tab1")) {
            setTopTab1();
        } else if (tabId.equals("tab2")) {
            setTopTab2();
        } else if (tabId.equals("tab3")) {
            setTopTab3();
        } else if (tabId.equals("tab4")) {
            setTopTab4();
        } else if (tabId.equals("tab5")) {
            setTopTab5();
        }
    }

    public void toPage(int pager) {
        mTabHost.setCurrentTab(pager);
    }

    public void setTopTab1() {
        tab5.setBackgroundResource(0);
        setStatusBarColor(R.color.black);
        if (badgeView.getBadgeCount() > 0) {
            badgeView.setVisibility(View.VISIBLE);
        } else {
            badgeView.setVisibility(View.GONE);
        }
        TextView title = (TextView) getLayoutInflater(R.layout.layout_tab_main_title);
        title.setText(getString(R.string.main_title));
        toolbar.setMiddleView(title);
        toolbar.setLeftShow(true);
        TextView city = (TextView) getLayoutInflater(R.layout.layout_tab_main_city);
        LocationEntry entry = Utils.getLocationEntry();
        if(TextUtils.isEmpty(entry.getCity()) && TextUtils.isEmpty(entry.getUserSelectCity())){
            toolbar.setLeftImage(R.mipmap.main_location);
        }else{
            if(!TextUtils.isEmpty(entry.getUserSelectCity())){
                city.setText(entry.getUserSelectCity());
            }else{
                city.setText(entry.getCity());
            }
            toolbar.setLeftView(city);
        }
        toolbar.setRightImage(R.mipmap.main_message);
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FastDealFragment.class.getName());
                startActivityForResult(intent, 101);
            }
        });
        toolbar.setOnLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, LocationFragment.class.getName());
                startActivityForResult(intent, 102);
            }
        });
        toolbar.setBgRes(R.color.trans);
    }

    public void setTopTab2() {
        tab5.setBackgroundResource(0);
        setStatusBarColor(R.color.black);
        if (badgeView.getBadgeCount() > 0) {
            badgeView.setVisibility(View.VISIBLE);
        } else {
            badgeView.setVisibility(View.GONE);
        }
        TextView title = (TextView) getLayoutInflater(R.layout.layout_tab_main_title);
        title.setText(getString(R.string.project_title));
        toolbar.setMiddleView(title);
        toolbar.setLeftShow(false);
        toolbar.setRightImage(R.mipmap.main_message);
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FastDealFragment.class.getName());
                startActivityForResult(intent, 101);
            }
        });
        toolbar.setBgRes(R.color.trans);
    }

    public void setTopTab3() {
        tab5.setBackgroundResource(0);
        setStatusBarColor(R.color.colorBlue);
        badgeView.setVisibility(View.GONE);
        View view = getLayoutInflater(R.layout.layout_tab_msg_title);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(getString(R.string.msg_title));
        toolbar.setMiddleView(view);
        toolbar.setBgRes(R.color.msg_top_bg);
        toolbar.setLeftShow(false);
        toolbar.setRightImage(R.mipmap.msg_search);
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, MsgSeachFragment.class.getName());
                startActivity(intent);
            }
        });
    }

    public void setTopTab4() {
        tab5.setBackgroundResource(0);
        setStatusBarColor(R.color.colorBlue);
        badgeView.setVisibility(View.GONE);
        TextView title = (TextView) getLayoutInflater(R.layout.layout_tab_main_title);
        title.setText(getString(R.string.ransfer_title));
        toolbar.setMiddleView(title);
        toolbar.setBgRes(R.color.msg_top_bg);
        toolbar.setLeftShow(true);
        ImageView image = new ImageView(getActivity());
        int padding = DPUtils.dip2px(getActivity(), 10);
        image.setPadding(padding, 0, 0, 0);
        image.setImageResource(R.mipmap.seach_w_img);
        toolbar.setLeftView(image);
        toolbar.setRightImage(R.mipmap.ransfer_foot);
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, RansferHistoryFragment.class.getName());
                startActivity(intent);
            }
        });
        toolbar.setOnLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    public void setTopTab5() {
        tab5.setBackground(getResources().getDrawable(R.mipmap.me_tab));
        setStatusBarColor(R.color.colorBlue);
        badgeView.setVisibility(View.GONE);
        ImageView title = new ImageView(this);
        title.setImageResource(R.mipmap.me_title);
        toolbar.setMiddleView(title);
        toolbar.setBgRes(R.color.msg_top_bg);
        ImageView image = new ImageView(getActivity());
        int padding = DPUtils.dip2px(getActivity(), 10);
        image.setPadding(padding, 0, 0, 0);
        image.setImageResource(R.mipmap.me_set);
        toolbar.setLeftShow(true);
        toolbar.setLeftView(image);
        if (Utils.isLogin()) {
            toolbar.setRightImage(R.mipmap.me_add_friend);
        } else {
            toolbar.setRightImage(R.mipmap.me_add_friend_white);
        }
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, AddFriendFragment.class.getName());
                startActivity(intent);
            }
        });

        toolbar.setOnLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, SettingFragment.class.getName());
                startActivity(intent);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setUnreadCnt(Integer unreadCnt) {
        LogUtils.i("---zss---", "----unreadCnt = " + unreadCnt);
        if (mTabHost.getCurrentTab() == 0 || mTabHost.getCurrentTab() == 1) {
            if (unreadCnt > 0) {
                badgeView.setVisibility(View.VISIBLE);
            } else {
                badgeView.setVisibility(View.GONE);
            }
            badgeView.setBadgeCount(unreadCnt);
        }
    }

        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){ //更新未读消息
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                   Utils.queryUnreadCnt();
                }
            }, 500);
        }else if(requestCode == 102){ //切换城市
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setTopTab1();
                }
            }, 500);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentTab", mTabHost.getCurrentTab());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String tabId = mTabHost.getCurrentTabTag();
        if (tabId.equals("tab1")) {
            setTopTab1();
        } else if (tabId.equals("tab2")) {
            setTopTab2();
        } else if (tabId.equals("tab3")) {
            setTopTab3();
        } else if (tabId.equals("tab4")) {
            setTopTab4();
        } else if (tabId.equals("tab5")) {
            setTopTab5();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentTab = savedInstanceState.getInt("currentTab");
        if(mTabHost != null){
            mTabHost.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (currentTab){
                        case 0:
                            setTopTab1();
                            break;
                        case 1:
                            setTopTab2();
                            break;
                        case 2:
                            setTopTab3();
                            break;
                        case 3:
                            setTopTab4();
                            break;
                        case 4:
                            setTopTab5();
                            break;
                    }
                    currentTab = -1;
                }
            }, 200);
        }
    }

    public void initRull() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.queryUnreadCnt();
            }
        }, 500);

    }

    @Override
    public void onBackPressed() {
        CommonToastUtils.exitClient(this, true);
    }

    // **************************** 定位相关代码 **************************** //
    private LocationService locationService;

    private BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                String province = location.getProvince();
                String city = location.getCity();
                String district = location.getDistrict();
                String street = location.getStreet();
                String addrStr = location.getAddrStr();
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                LocationEntry entry = Utils.getLocationEntry();
                entry.setProvince(province);
                entry.setCity(city);
                entry.setDistrict(district);
                entry.setStreet(street);
                entry.setAddrStr(addrStr);
                entry.setLongitude(longitude);
                entry.setLatitude(latitude);
                Utils.saveLocationEntry(entry);
            }
        }

        public void onConnectHotSpotMessage(String s, int i) {
        }
    };

    public void locatioinPermissions() {
        verifyPermissions(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}
                , 0x01, new PermissionCallBack() {
                    @Override
                    public void onGranted() {
                        locationService = new LocationService(getActivity());
                        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                        locationService.registerListener(mListener);
                        locationService.start();
                    }

                    @Override
                    public void onDenied() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (locationService != null) {
            locationService.stop();
            locationService.unregisterListener(mListener);
        }
    }

    // **************************** 定位相关代码 **************************** //
}

