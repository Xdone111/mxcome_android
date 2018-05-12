package com.fenazola.mxcome.fragment.main.demand;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.LocationEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.project.DecoraEntry;
import com.fenazola.mxcome.entry.project.OrderEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DateUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.SkillUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.LocationItemLayout;
import com.fenazola.mxcome.widget.RadioGroupLayout;
import com.igexin.sdk.PushManager;
import com.zss.library.PermissionCallBack;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.FileUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.viewpager.AdViewPager;
import com.zss.library.wheelview.OnWheelChangedListener;
import com.zss.library.wheelview.OnWheelScrollListener;
import com.zss.library.wheelview.WheelView;
import com.zss.library.wheelview.adapter.ArrayWheelAdapter;
import com.zss.library.widget.CommonWheelDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * 发布需求
 */
public class DemandFragment extends BaseFragment implements View.OnClickListener, GestureDetector.OnGestureListener {

    private LocationItemLayout locationLayout;
    private EditText edit;
    private AdViewPager adViewPager1;
    private AdViewPager adViewPager2;
    private AdViewPager adViewPager3;
    private String fileName;
    private boolean isOpen = false;
    private DrawerLayout drawerLayout;
    private TextView record;

    private RelativeLayout center_layout;
    //    private ImageView iv_record;
    private GestureDetector detector;
    private TextView confirm;
    private TextView timeTimerTv;
    private TextView subscribe;
    private List<String[]> lists = new ArrayList<>();
    private DecoraEntry decora;
    private String skill = "";
    private CommonWheelDialog dialog;

    private String selectTime;
    private TextView selectProject;
    //    private TextView tVselect;
//    private ImageView play;
    private TextView inputText;
//    private LinearLayout showMsgLy;
    /**
     * 七个单选项
     */
    private Button radio_demand1, radio_demand2, radio_demand3, radio_demand4, radio_demand5, radio_demand6, radio_demand7;
    private Handler mHandler = new Handler();

    private LinearLayout showMsgLy1;
    //private TextView toEdit;
    private EditText editMessageEt;
    private ImageView toRecordIv;
    private TextView showRecodrdTv;
    private boolean isStopCount = false;
    private long timer = 0;
    float y1 = 0;
    float y2 = 0;

    private ImageView showRecBg;
    private ImageView showRecIco;
    private RelativeLayout showOrHindRy;

    int heightDiff = 0;
    // SoftKeyboardStateHelper softKeyboardStateHelper;
    private RelativeLayout toFaqRy;
    private TextView toFaqTv;
    private LinearLayout faqLy;
    private ListView showFaqItem;
    ArrayList<String> items = new ArrayList<String>();
    private CommonAdapter<String> mAdapter;
    private OrderEntry order;
    private LinearLayout radioLayout2;
    private ImageView toRecordIv2;
    private LinearLayout show_faq_ly;
    //private TextView toEditFloat;
    private EditText editMessageEtFloat;
    private ImageView toRecordIvFloat;
    private TextView showRecodrdTvFloat;
    private ImageView toRecordIv2Float;
    private ImageView show_logo_iv;
    private TextView show_faq_tv_float;

    private LinearLayout yinying1,yinying2;
    CommonToolbar toolbar;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_demand_new;
    }

    @Override
    public void initView() {
        super.initView();
        heightDiff = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        locationLayout = (LocationItemLayout) findViewById(R.id.locationLayout);
        adViewPager1 = (AdViewPager) findViewById(R.id.adViewPager1);
        adViewPager2 = (AdViewPager) findViewById(R.id.adViewPager2);
        adViewPager3 = (AdViewPager) findViewById(R.id.adViewPager3);
        edit = (EditText) findViewById(R.id.edit);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        radioLayout1 = (RadioGroupLayout) findViewById(R.id.radio_layout1);
        radioLayout2 = (LinearLayout) findViewById(R.id.radio_layout2);
        record = (TextView) findViewById(R.id.tv_record);
        center_layout = (RelativeLayout) findViewById(R.id.center_layout);
//        iv_record = (ImageView) findViewById(R.id.iv_record);
        confirm = (TextView) findViewById(R.id.confirm);
        timeTimerTv = (TextView) findViewById(R.id.voice_time);
        subscribe = (TextView) findViewById(R.id.subscribe);
        selectProject = (TextView) findViewById(R.id.select_project);
//        tVselect = (TextView) findViewById(R.id.tv_select);
        edit = (EditText) findViewById(R.id.edit);
//        play = (ImageView) findViewById(R.id.iv_play);
        inputText = (TextView) findViewById(R.id.tv_input);
//        showMsgLy = (LinearLayout) findViewById(R.id.show_msg_ly);
        radio_demand1 = (Button) findViewById(R.id.radio_demand1);
        radio_demand2 = (Button) findViewById(R.id.radio_demand2);
        radio_demand3 = (Button) findViewById(R.id.radio_demand3);
        radio_demand4 = (Button) findViewById(R.id.radio_demand4);
        radio_demand5 = (Button) findViewById(R.id.radio_demand5);
        radio_demand6 = (Button) findViewById(R.id.radio_demand6);
        radio_demand7 = (Button) findViewById(R.id.radio_demand7);
        radio_demand1.setOnClickListener(this);
        radio_demand3.setOnClickListener(this);
        radio_demand2.setOnClickListener(this);
        radio_demand4.setOnClickListener(this);
        radio_demand5.setOnClickListener(this);
        radio_demand6.setOnClickListener(this);
        radio_demand7.setOnClickListener(this);

        showMsgLy1 = (LinearLayout) findViewById(R.id.show_msg_ly_1);
        //toEdit = (TextView) findViewById(R.id.tv_input_1);
        editMessageEt = (EditText) findViewById(R.id.edit_1);
        toRecordIv = (ImageView) findViewById(R.id.iv_record_1);
        toRecordIv2 = (ImageView) findViewById(R.id.iv_record_2);
        showRecodrdTv = (TextView) findViewById(R.id.tv_input_2);
        //toEditFloat = (TextView) findViewById(R.id.tv_input_1_float);
        editMessageEtFloat = (EditText) findViewById(R.id.edit_1_float);
        toRecordIvFloat = (ImageView) findViewById(R.id.iv_record_1_float);
        toRecordIv2Float = (ImageView) findViewById(R.id.iv_record_2_float);
        showRecodrdTvFloat = (TextView) findViewById(R.id.tv_input_2_float);
        //toEdit.setOnClickListener(this);
        showRecodrdTv.setOnClickListener(this);
        toRecordIv2.setOnClickListener(this);

        showRecBg = (ImageView) findViewById(R.id.show_is_rec_iv);
        showRecIco = (ImageView) findViewById(R.id.iv_input);
        showOrHindRy = (RelativeLayout) findViewById(R.id.show_or_hind_ry);

        toFaqRy = (RelativeLayout) findViewById(R.id.show_faq_ry);
        toFaqTv = (TextView) findViewById(R.id.show_faq_tv);
        faqLy = (LinearLayout) findViewById(R.id.show_faq_ly);
        showFaqItem = (ListView) findViewById(R.id.listView);
        show_faq_ly = (LinearLayout) findViewById(R.id.show_faq_ly);
        show_logo_iv = (ImageView) findViewById(R.id.show_logo_iv);
        show_faq_tv_float = (TextView) findViewById(R.id.show_faq_tv_float);
        show_faq_tv_float.setOnClickListener(this);

        yinying1=(LinearLayout)findViewById(R.id.show_yinyin);
        yinying2=(LinearLayout)findViewById(R.id.show_yinyin1);
        yinying1.setOnClickListener(this);

        toFaqTv.setOnClickListener(this);
        editMessageEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    toFaqTv.setVisibility(View.VISIBLE);
                } else {
                    toFaqTv.setVisibility(View.INVISIBLE);

                }
            }
        });
        editMessageEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                toFaqTv.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initChangShowOrHind(boolean b) {
        if (b) {
            showOrHindRy.setVisibility(View.GONE);
        } else {
            showOrHindRy.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            order = (OrderEntry) args.getSerializable(Constant.key1);
        }

        items.add("我需要一名师傅帮我拆墙");
        items.add("我需要一名好的水电师傅");
        items.add("我需要一名好的泥工师傅");
        items.add("我需要一名好的木工师傅");
        items.add("我需要一名好的油漆师傅");


        detector = new GestureDetector(getContext(), this);

        record.setOnClickListener(this);
//        iv_record.setOnClickListener(this);
        confirm.setOnClickListener(this);
        subscribe.setOnClickListener(this);
        selectProject.setOnClickListener(this);
//        play.setOnClickListener(this);

        lists.add(getStringArray(R.array.arrays_day));
        lists.add(getStringArray(R.array.arrays_hour));
        lists.add(getStringArray(R.array.arrays_minute));

        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.demand_adv2);
        list.add(R.mipmap.demand_adv2);
        list.add(R.mipmap.demand_adv2);

        List<Integer> list1 = new ArrayList<>();
        list1.add(R.mipmap.demand_adv3);
        list1.add(R.mipmap.demand_adv3);
        list1.add(R.mipmap.demand_adv3);

        adViewPager2.setLocalResIds(list);
        adViewPager2.setHeight(DPUtils.dip2px(getActivity(), 60));
        adViewPager3.setLocalResIds(list1);
        adViewPager3.setHeight(DPUtils.dip2px(getActivity(), 60));

        DemandRightFragment fragment = new DemandRightFragment();
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fl_content, fragment);
        ft.commit();

        toRecordIv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean ret = detector.onTouchEvent(event);
                if (!ret) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        toRecordIv.setVisibility(View.VISIBLE);
//                        toRecordIv2.setVisibility(View.GONE);
                        y1 = event.getY();
                        center_layout.setVisibility(View.VISIBLE);
                        //toEdit.setVisibility(View.GONE);
                        editMessageEt.setVisibility(View.GONE);
                        //toEditFloat.setVisibility(View.GONE);
                        editMessageEtFloat.setVisibility(View.GONE);

                        timer = 0;
                        countTimer();
                        isStopCount = false;
                        startRecording();
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        y2 = event.getY();
                        isStopCount = true;
                        stopRecording();
                        //  inputText.setVisibility(View.VISIBLE);
                        //  edit.setVisibility(View.GONE);
                        //  play.setVisibility(View.VISIBLE);

                        LogUtils.i("XHX", "间距：" + (y1 - y2));
                        if (y1 - y2 > 50) {
                            showRecBg.setImageResource(R.mipmap.rec_can_bg);
                            showRecIco.setImageResource(R.mipmap.rec_can_img);
                            timeTimerTv.setTextColor(getColor(R.color.colorRed));
                            mHandler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    showRecBg.setImageResource(R.mipmap.rec_bg);
                                    showRecIco.setImageResource(R.mipmap.rec_img);
                                    timeTimerTv.setTextColor(getColor(R.color.colorBlue));
                                    center_layout.setVisibility(View.GONE);
                                    //toEdit.setVisibility(View.VISIBLE);
                                    //toEditFloat.setVisibility(View.VISIBLE);
                                    confirm.setTextColor(getColor(R.color.colorGrey));
                                    confirm.setBackgroundColor(getColor(R.color.white));

                                }

                            }, 2000);
                        } else {
                            center_layout.setVisibility(View.GONE);
                            //TODO 设置时长
                            try {
                                LogUtils.i("XHX", "长度：" + getAmrDuration(fileName) + ";wenjian:" + fileName);
                                showRecodrdTv.setVisibility(View.VISIBLE);
                                showRecodrdTv.setText(getAmrDuration(fileName) / 1000 + "s");
                                showRecodrdTvFloat.setVisibility(View.VISIBLE);
                                showRecodrdTvFloat.setText(getAmrDuration(fileName) / 1000 + "s");
                                confirm.setTextColor(getColor(R.color.colorBlue));
                                confirm.setBackgroundColor(getColor(R.color.colorReset));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                return true;
            }
        });


        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_history_item_gv) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                name.setText(workerListEntry);
            }
        };
        showFaqItem.setAdapter(mAdapter);
        mAdapter.addAll(items);
        showFaqItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //toEdit.setVisibility(View.GONE);
                editMessageEt.setVisibility(View.VISIBLE);
                toRecordIv.setVisibility(View.GONE);
                toRecordIv2.setVisibility(View.VISIBLE);
                editMessageEt.setText(items.get(position));
                editMessageEtFloat.setText(items.get(position));
                faqLy.setVisibility(View.GONE);
                showOrHindRy.setVisibility(View.VISIBLE);
                //toEditFloat.setVisibility(View.GONE);
                editMessageEtFloat.setVisibility(View.VISIBLE);
                toRecordIvFloat.setVisibility(View.GONE);
                toRecordIv2Float.setVisibility(View.VISIBLE);
                editMessageEtFloat.setText(items.get(position));
                toFaqTv.setVisibility(View.VISIBLE);


                yinying1.setVisibility(View.GONE);
                yinying2.setVisibility(View.GONE);
                toolbar.setBgColor(getColor(R.color.white));
                confirm.setEnabled(true);


            }
        });
        editMessageEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    confirm.setTextColor(getColor(R.color.colorBlue));
                    confirm.setBackgroundColor(getColor(R.color.colorReset));

                } else {
                    confirm.setTextColor(getColor(R.color.colorGrey));
                    confirm.setBackgroundColor(getColor(R.color.white));
                }
                //editMessageEtFloat.setText(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editMessageEtFloat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    confirm.setTextColor(getColor(R.color.colorBlue));
                    confirm.setBackgroundColor(getColor(R.color.colorReset));
                } else {
                    confirm.setTextColor(getColor(R.color.colorGrey));
                    confirm.setBackgroundColor(getColor(R.color.white));
                }

                //editMessageEt.setText(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_record:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, ReleaseRecordFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.confirm:


                if (decora == null) {
                    if (order != null) {
                        reqData();
                    } else {
                        CommonToastUtils.showInCenterToast(getActivity(), "请选择房屋信息");
                        return;
                    }
                }
                if (TextUtils.isEmpty(skill)) {
                    CommonToastUtils.showInCenterToast(getActivity(), "请选择发布需求类型");
                    return;
                }
                String hid5;
                if (decora == null) {
                    if (order != null) {
                        hid5 = order.getHouseId();
                    } else {
                        return;
                    }
                } else {
                    hid5 = decora.getHouseId();
                }
                DemandOrder dialog5 = new DemandOrder(getActivity(), hid5);
                dialog5.show();
                reqData();
                break;
            case R.id.subscribe:
                //showWheelDialog(lists);
                // selectTimeFy.setVisibility(View.VISIBLE);
                editMessageEt.setText("");
                editMessageEtFloat.setText("");
                break;

            case R.id.select_project:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, SelectHouseFragment.class.getName());
                intent.putExtra(Constant.key1, order);
                startActivity(intent);
                break;
            case R.id.radio_demand1:
                //LogUtils.i("XHX","skill："+skill);
                if (skill.equals(SkillUtils.SKILL_CHAIGAI)) {
                    if (showMsgLy1.getVisibility() == View.VISIBLE) {
                        showMsgLy1.setVisibility(View.GONE);
                        show_logo_iv.setVisibility(View.VISIBLE);
                        toFaqTv.setVisibility(View.INVISIBLE);
                        initChangeItemBack(radio_demand1, false);
                        radioLayout2.setVisibility(View.VISIBLE);
                        //show_logo_iv.setVisibility(View.VISIBLE);

                        skill = "";
                        break;
                    } else {
                        showMsgLy1.setVisibility(View.VISIBLE);
                        show_logo_iv.setVisibility(View.INVISIBLE);

                        toFaqTv.setVisibility(View.INVISIBLE);
                        initChangeItemBack(radio_demand1, true);
                        radioLayout2.setVisibility(View.GONE);
                        //show_logo_iv.setVisibility(View.GONE);

                    }
                } else {
                    showMsgLy1.setVisibility(View.VISIBLE);
                    show_logo_iv.setVisibility(View.INVISIBLE);

                    toFaqTv.setVisibility(View.INVISIBLE);
                    initChangeItemBack(radio_demand1, true);
                    radioLayout2.setVisibility(View.GONE);
                    //show_logo_iv.setVisibility(View.GONE);


                }
                skill = SkillUtils.SKILL_CHAIGAI;
//                tVselect.setText("您选择了分类图标 拆改");

                break;
            case R.id.radio_demand2:
                if (skill.equals(SkillUtils.SKILL_SHUIDIAN)) {
                    if (showMsgLy1.getVisibility() == View.VISIBLE) {
                        showMsgLy1.setVisibility(View.GONE);
                        show_logo_iv.setVisibility(View.VISIBLE);

                        toFaqTv.setVisibility(View.INVISIBLE);
                        initChangeItemBack(radio_demand2, false);
                        radioLayout2.setVisibility(View.VISIBLE);
                        //show_logo_iv.setVisibility(View.VISIBLE);


                        skill = "";
                        break;
                    } else {
                        showMsgLy1.setVisibility(View.VISIBLE);
                        show_logo_iv.setVisibility(View.INVISIBLE);

                        toFaqTv.setVisibility(View.INVISIBLE);
                        initChangeItemBack(radio_demand2, true);
                        radioLayout2.setVisibility(View.GONE);
                        //show_logo_iv.setVisibility(View.GONE);


                    }
                } else {
                    showMsgLy1.setVisibility(View.VISIBLE);
                    show_logo_iv.setVisibility(View.INVISIBLE);

                    toFaqTv.setVisibility(View.INVISIBLE);
                    initChangeItemBack(radio_demand2, true);
                    radioLayout2.setVisibility(View.GONE);
                    //show_logo_iv.setVisibility(View.GONE);


                }
                skill = SkillUtils.SKILL_SHUIDIAN;
//                tVselect.setText("您选择了分类图标 水电");

                break;
            case R.id.radio_demand3:
                if (skill.equals(SkillUtils.SKILL_NIGONG)) {
                    if (showMsgLy1.getVisibility() == View.VISIBLE) {
                        showMsgLy1.setVisibility(View.GONE);
                        show_logo_iv.setVisibility(View.VISIBLE);

                        toFaqTv.setVisibility(View.INVISIBLE);
                        initChangeItemBack(radio_demand3, false);
                        radioLayout2.setVisibility(View.VISIBLE);
                        //show_logo_iv.setVisibility(View.VISIBLE);


                        skill = "";
                        break;
                    } else {
                        showMsgLy1.setVisibility(View.VISIBLE);
                        show_logo_iv.setVisibility(View.INVISIBLE);

                        toFaqTv.setVisibility(View.INVISIBLE);
                        initChangeItemBack(radio_demand3, true);
                        radioLayout2.setVisibility(View.GONE);
                        //show_logo_iv.setVisibility(View.GONE);


                    }
                } else {
                    showMsgLy1.setVisibility(View.VISIBLE);
                    show_logo_iv.setVisibility(View.INVISIBLE);

                    toFaqTv.setVisibility(View.INVISIBLE);
                    initChangeItemBack(radio_demand3, true);
                    radioLayout2.setVisibility(View.GONE);
                    //show_logo_iv.setVisibility(View.GONE);


                }
                skill = SkillUtils.SKILL_NIGONG;
//                tVselect.setText("您选择了分类图标 泥工");

                break;
            case R.id.radio_demand4:
                if (skill.equals(SkillUtils.SKILL_MUGONG)) {
                    if (showMsgLy1.getVisibility() == View.VISIBLE) {
                        showMsgLy1.setVisibility(View.GONE);
                        show_logo_iv.setVisibility(View.VISIBLE);

                        toFaqTv.setVisibility(View.INVISIBLE);
                        initChangeItemBack(radio_demand4, false);
                        radioLayout2.setVisibility(View.VISIBLE);
                        //show_logo_iv.setVisibility(View.VISIBLE);


                        skill = "";
                        break;
                    } else {
                        showMsgLy1.setVisibility(View.VISIBLE);
                        show_logo_iv.setVisibility(View.INVISIBLE);

                        toFaqTv.setVisibility(View.INVISIBLE);
                        initChangeItemBack(radio_demand4, true);
                        radioLayout2.setVisibility(View.GONE);
                        //show_logo_iv.setVisibility(View.GONE);


                    }
                } else {
                    showMsgLy1.setVisibility(View.VISIBLE);
                    show_logo_iv.setVisibility(View.INVISIBLE);

                    toFaqTv.setVisibility(View.INVISIBLE);
                    initChangeItemBack(radio_demand4, true);
                    radioLayout2.setVisibility(View.GONE);
                    //show_logo_iv.setVisibility(View.GONE);


                }
                skill = SkillUtils.SKILL_MUGONG;
//                tVselect.setText("您选择了分类图标 木工");

                break;
            case R.id.radio_demand5:

                if (skill.equals(SkillUtils.SKILL_YOUQI)) {
                    if (showMsgLy1.getVisibility() == View.VISIBLE) {
                        showMsgLy1.setVisibility(View.GONE);
                        show_logo_iv.setVisibility(View.VISIBLE);

                        toFaqTv.setVisibility(View.INVISIBLE);
                        initChangeItemBack(radio_demand5, false);
                        radioLayout2.setVisibility(View.VISIBLE);
                        //show_logo_iv.setVisibility(View.VISIBLE);

                        skill = "";
                        break;
                    } else {
                        showMsgLy1.setVisibility(View.VISIBLE);
                        show_logo_iv.setVisibility(View.INVISIBLE);

                        toFaqTv.setVisibility(View.INVISIBLE);
                        initChangeItemBack(radio_demand5, true);
                        radioLayout2.setVisibility(View.GONE);
                        //show_logo_iv.setVisibility(View.GONE);


                    }
                } else {
                    showMsgLy1.setVisibility(View.VISIBLE);
                    show_logo_iv.setVisibility(View.INVISIBLE);

                    toFaqTv.setVisibility(View.INVISIBLE);
                    initChangeItemBack(radio_demand5, true);
                    radioLayout2.setVisibility(View.GONE);
                    //show_logo_iv.setVisibility(View.GONE);


                }
                skill = SkillUtils.SKILL_YOUQI;
//                tVselect.setText("您选择了分类图标 油漆");
                break;
            case R.id.radio_demand6:

                if (decora == null) {
                    if (order != null) {
                    } else {
                        CommonToastUtils.showInCenterToast(getActivity(), "请选择房屋信息");
                        return;
                    }
                }
                skill = SkillUtils.SKILL_SHEJI;
                initChangeItemBack(radio_demand6, true);
                if (showMsgLy1.getVisibility() == View.VISIBLE) {
                    showMsgLy1.setVisibility(View.GONE);
                    show_logo_iv.setVisibility(View.VISIBLE);

                    toFaqTv.setVisibility(View.INVISIBLE);
                    faqLy.setVisibility(View.GONE);
                }

                String hid1;
                if (decora == null) {
                    if (order != null) {
                        hid1 = order.getHouseId();
                    } else {
                        return;
                    }
                } else {
                    hid1 = decora.getHouseId();
                }

                DemandDesign dialog = new DemandDesign(getActivity(), hid1);
                dialog.show();
                break;
            case R.id.radio_demand7:
                if (decora == null) {
                    if (order != null) {
                    } else {
                        CommonToastUtils.showInCenterToast(getActivity(), "请选择房屋信息");
                        return;
                    }
                }

                skill = SkillUtils.SKILL_FANGSHUI;
                initChangeItemBack(radio_demand7, true);
                if (showMsgLy1.getVisibility() == View.VISIBLE) {
                    showMsgLy1.setVisibility(View.GONE);
                    show_logo_iv.setVisibility(View.VISIBLE);

                    toFaqTv.setVisibility(View.INVISIBLE);
                    faqLy.setVisibility(View.GONE);
                }

                String hid;
                if (decora == null) {
                    if (order != null) {
                        hid = order.getHouseId();
                    } else {
                        return;
                    }
                } else {
                    hid = decora.getHouseId();
                }

                DemandWaterproof dialog1 = new DemandWaterproof(getActivity(), hid);
                dialog1.show();
                break;

            case R.id.select_time_yes:
                break;
            case R.id.select_time_no:
            case R.id.show_select_time_fy:
                //TODO
                break;
            case R.id.tv_now_time:
                break;
            case R.id.tv_input_1:
                //toEdit.setVisibility(View.GONE);
                //toEditFloat.setVisibility(View.GONE);

//                editMessageEt.setVisibility(View.VISIBLE);
//                toRecordIv.setVisibility(View.GONE);
//                toRecordIv2.setVisibility(View.VISIBLE);
//                editMessageEtFloat.setVisibility(View.VISIBLE);
//                toRecordIvFloat.setVisibility(View.GONE);
//                toRecordIv2Float.setVisibility(View.VISIBLE);
//                toFaqTv.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_input_2:
                startPlayer(fileName);
                break;
            case R.id.show_faq_tv:
                //TODO 设置常用语
                show_faq_ly.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(editMessageEt.getText()))
                    editMessageEtFloat.setText(editMessageEt.getText());
//                confirm.setBackgroundColor(getColor(R.color.colorGrey));
//                confirm.setTextColor(getColor(R.color.grey));
                yinying1.setVisibility(View.VISIBLE);
                yinying2.setVisibility(View.VISIBLE);
                toolbar.setBgColor(getColor(R.color.circle_b1_color));
                confirm.setEnabled(false);

//                if (faqLy.getVisibility() == View.GONE) {
//                    faqLy.setVisibility(View.VISIBLE);
//                    showOrHindRy.setVisibility(View.GONE);
//                } else {
//                    faqLy.setVisibility(View.GONE);
//                    showOrHindRy.setVisibility(View.VISIBLE);
//                }
                break;
            case R.id.show_faq_tv_float:
                //TODO 设置常用语
                show_faq_ly.setVisibility(View.GONE);
                toFaqTv.setVisibility(View.VISIBLE);
//                if (!TextUtils.isEmpty(editMessageEtFloat.getText())) {
//                    editMessageEt.setText(editMessageEtFloat.getText());
//                    confirm.setTextColor(getColor(R.color.colorBlue));
//                    confirm.setBackgroundColor(getColor(R.color.colorReset));
//                } else {
//                    confirm.setBackgroundColor(getColor(R.color.white));
//                    confirm.setTextColor(getColor(R.color.colorGrey));
//                }
                yinying1.setVisibility(View.GONE);
                yinying2.setVisibility(View.GONE);
                toolbar.setBgColor(getColor(R.color.white));
                confirm.setEnabled(true);

                break;
            case R.id.iv_record_2:
                toRecordIv.setVisibility(View.VISIBLE);
                toRecordIv2.setVisibility(View.GONE);
                toFaqTv.setVisibility(View.INVISIBLE);
                break;
            case R.id.show_yinyin1:
                break;
        }
    }

    private void initChangeItemBack(Button radio_deman, boolean isTrue) {
        radio_demand1.setBackground(getResources().getDrawable(R.mipmap.demand_unselect_circle));
        radio_demand2.setBackground(getResources().getDrawable(R.mipmap.demand_unselect_circle));
        radio_demand3.setBackground(getResources().getDrawable(R.mipmap.demand_unselect_circle));
        radio_demand4.setBackground(getResources().getDrawable(R.mipmap.demand_unselect_circle));
        radio_demand5.setBackground(getResources().getDrawable(R.mipmap.demand_unselect_circle));
        radio_demand1.setTextColor(getColor(R.color.colorGrey));
        radio_demand2.setTextColor(getColor(R.color.colorGrey));
        radio_demand3.setTextColor(getColor(R.color.colorGrey));
        radio_demand4.setTextColor(getColor(R.color.colorGrey));
        radio_demand5.setTextColor(getColor(R.color.colorGrey));

        if (radio_deman == radio_demand6 || radio_deman == radio_demand7) {
            subscribe.setVisibility(View.GONE);
            return;
        }
        if (isTrue) {
            radio_deman.setBackground(getResources().getDrawable(R.mipmap.demand_selected_circle));
            radio_deman.setTextColor(getColor(R.color.colorBlue));
            subscribe.setVisibility(View.VISIBLE);

        } else {
            radio_deman.setBackground(getResources().getDrawable(R.mipmap.demand_unselect_circle));
            radio_deman.setTextColor(getColor(R.color.colorGrey));
            subscribe.setVisibility(View.INVISIBLE);


        }
    }

    private void reqData() {
        HashMap<String, String> map = new HashMap<>();
        UserEntry user = Utils.getUserEntry();
        LocationEntry location = Utils.getLocationEntry();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String mxcome_no = user.getMxcome_no();
        map.put("user_cid", PushManager.getInstance().getClientid(getContext()));
        map.put("mxcome_no", mxcome_no);
        map.put("user_id", user.getUser_id());
        if (decora == null) {
            if (order != null) {
                map.put("asset_id", order.getHouseId());
            } else {
                return;
            }
        } else {
            map.put("asset_id", decora.getHouseId());
        }
        map.put("longitude", longitude + "");
        map.put("latitude", latitude + "");
        map.put("skill", skill);
        map.put("message", editMessageEt.getText().toString());
        String url = Constant.newBaseUrl + "mainFunc/pushSingleBiz.do";
        NetWorkUtils.postUrl(getActivity(), url, map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                LogUtils.i("XHX", "成功：" + result);
            }

            @Override
            public void onError(String result, String code, String msg) {
                LogUtils.i("XHX", "失败：" + result + ";" + code + ";" + msg);

            }
        });
    }

    public void showWheelDialog(List<String[]> lists) {
        dialog = new CommonWheelDialog(getActivity(), lists);
        dialog.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ymd = "";
                int hour = 0, min = 0;
                List<Integer> items = dialog.getCurrentItems();
                for (int i = 0; i < items.size(); i++) {
                    int position = items.get(i);
                    if (i == 0) {
                        Calendar cal;
                        if (position == 0) {
                            cal = Calendar.getInstance();
                        } else if (position == 1) {
                            cal = Calendar.getInstance();
                            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
                        } else {
                            cal = Calendar.getInstance();
                            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 2);
                        }
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        ymd = format.format(cal.getTime());
                    } else if (i == 1) {
                        hour = position;
                    } else {
                        min = position * 10;
                    }
                }
                String hourStr = String.format("%02d", hour);
                String minStr = String.format("%02d", min);
                selectTime = ymd + " " + hourStr + ":" + minStr;
            }
        });
        dialog.show();
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);

        CommonToolbar parentToolbar = getToolbar();
        parentToolbar.setVisibility(View.GONE);
        toolbar = (CommonToolbar) findViewById(R.id.topBar);
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setRightText("筛选");
        toolbar.setRightTextColor(getColor(R.color.colorBlue));
        toolbar.setTitle("单项业务");
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setTitleColor(getColor(R.color.black));
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
                isOpen = !isOpen;
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setHouse(DecoraEntry decora) {
        this.decora = decora;
        // LogUtils.i("ORDER", "本地：" + order.getHouseId());
        if (order == null) {
            order = new OrderEntry();
        }
        order.setHouseId(decora.getHouseId());
        LogUtils.i("ORDER", "返回修改本地：" + order.getHouseId() + "；decora：" + decora.getHouseId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        mediaRelease();

    }
    //******************* 录音 ************************

    private MediaPlayer mPlayer = null;
    private MediaRecorder mRecorder = null;

    //开始录音
    public void startRecording() {
        verifyPermissions(getActivity(), Manifest.permission.RECORD_AUDIO,
                new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , 0x01, new PermissionCallBack() {

                    @Override
                    public void onGranted() {
                        File file = new File(FileUtils.getExtDir(getActivity()) + Constant.appName);
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        boolean highQuality = true;
                        fileName = file.getPath() + File.separator + "recorder_" + System.currentTimeMillis() + ".amr";
                        int outputfileformat = highQuality ? MediaRecorder.OutputFormat.AMR_WB : MediaRecorder.OutputFormat.AMR_NB;
                        mRecorder = new MediaRecorder();
                        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mRecorder.setAudioSamplingRate(highQuality ? 16000 : 8000);
                        mRecorder.setOutputFormat(outputfileformat);
                        mRecorder.setAudioEncoder(highQuality ? MediaRecorder.AudioEncoder.AMR_WB : MediaRecorder.AudioEncoder.AMR_NB);
                        mRecorder.setOutputFile(fileName);
                        LogUtils.i("XHX", "语音地址：" + fileName);
                        try {
                            mRecorder.prepare();
                            mRecorder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
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
            } finally {
                mRecorder.reset();
                mRecorder.release();
                mRecorder = null;
            }
        }
    }

    private void startPlayer(String item) {
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
            mPlayer.setDataSource(item);
            final AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
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

    /**
     * 得到amr的时长
     *
     * @param file
     * @return amr文件时间长度
     * @throws IOException
     */
    public static int getAmrDuration(String file) throws IOException {
        int leng = 0;
        MediaPlayer player = new MediaPlayer();
        player.setDataSource(file);
        player.prepare();
        leng = player.getDuration();
        player.release();
        player = null;
        return leng;
    }

    private void mediaRelease() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


    private Runnable TimerRunnable = new Runnable() {

        @Override
        public void run() {
            if (!isStopCount) {
                timer += 1000;
                //timeStr = TimeUtil.getFormatTime(timer);
                timeTimerTv.setText(DateUtils.getTimeString(timer));
            }
            countTimer();
        }
    };

    private void countTimer() {
        mHandler.postDelayed(TimerRunnable, 1000);
    }


}
