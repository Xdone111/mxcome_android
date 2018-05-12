package com.fenazola.mxcome.fragment.main.worker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableArea;
import com.fenazola.mxcome.db.TableChat;
import com.fenazola.mxcome.db.TableTalk;
import com.fenazola.mxcome.entry.LocationEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.WorkDesignerDetailEntry;
import com.fenazola.mxcome.entry.WorkerEntry;
import com.fenazola.mxcome.fragment.main.location.LocationFragment;
import com.fenazola.mxcome.fragment.msg.ChatRecyclerFragment;
import com.fenazola.mxcome.fragment.project.ProjectProgressFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.LocationItemLayout;
import com.fenazola.mxcome.widget.ScrollPicker;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.stmt.Where;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.ptr.PtlmDefaultHandler;
import com.zss.library.ptr.PtrClassicFrameLayout;
import com.zss.library.ptr.PtrFrameLayout;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 工厂圈
 */
public class WorkerFragment extends BaseFragment implements View.OnClickListener {

    private ScrollPicker mScrollPicker;
    private PtrClassicFrameLayout mPtrFrame;
    private CommonAdapter<WorkerEntry> adapter;
    private RadioGroup radioGroup;
    private FrameLayout classify_layout;
    private ImageView image1, image2, image3, image4, image5;
    private List<WorkerEntry> datas;
    private List<TableArea> areaList = new ArrayList<>();
    private int pageIndex = 1;
    private int pos = -1;
    private CommonAdapter<TableArea> mAdapter;
    private String w_type = "";
    private View contentView;
    private int indexPager = -1;
    private LocationItemLayout locationLayout;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private RadioButton radio5;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_worker;
    }

    @Override
    public void initView() {
        super.initView();
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.mPtrFrame);
        mScrollPicker = (ScrollPicker) findViewById(R.id.mScrollPicker);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        classify_layout = (FrameLayout) findViewById(R.id.classify_layout);
        locationLayout = (LocationItemLayout) findViewById(R.id.locationLayout);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        image5 = (ImageView) findViewById(R.id.image5);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        radio4 = (RadioButton) findViewById(R.id.radio4);
        radio5 = (RadioButton) findViewById(R.id.radio5);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle args = getArguments();
        if (args.containsKey(Constant.key1)) {
            w_type = args.getString(Constant.key1);
        }
        adapter = new CommonAdapter<WorkerEntry>(getActivity(), R.layout.listitem_worker_circle) {
            @Override
            protected void convert(ViewHolder viewHolder, WorkerEntry item, int position) {
                TextView city = viewHolder.findViewById(R.id.city);
                TextView name = viewHolder.findViewById(R.id.name);
                TextView exper = viewHolder.findViewById(R.id.exper);
                TextView missto = viewHolder.findViewById(R.id.missto);
                TextView order_no = viewHolder.findViewById(R.id.order_no);
                TextView distance = viewHolder.findViewById(R.id.distance);
                RatingBar score = viewHolder.findViewById(R.id.ratingBar);
                ImageView logo = (CircleImageView) viewHolder.findViewById(R.id.logo);
                ImageView shield = viewHolder.findViewById(R.id.shield);
                TextView authen = viewHolder.findViewById(R.id.authen);
                TextView status = viewHolder.findViewById(R.id.status);
                TextView skill1 = viewHolder.findViewById(R.id.skill1);
                TextView skill2 = viewHolder.findViewById(R.id.skill2);
                TextView skill3 = viewHolder.findViewById(R.id.skill3);
                TextView skill4 = viewHolder.findViewById(R.id.skill4);
                skill1.setVisibility(View.GONE);
                skill2.setVisibility(View.GONE);
                skill3.setVisibility(View.GONE);
                skill4.setVisibility(View.GONE);
                List<String> skills = item.getSkill_style();
                if (skills != null && skills.size() > 0) {
                    if (skills.size() >= 4) {
                        skill1.setText(skills.get(0));
                        skill1.setVisibility(View.VISIBLE);
                        skill2.setText(skills.get(1));
                        skill2.setVisibility(View.VISIBLE);
                        skill3.setText(skills.get(2));
                        skill3.setVisibility(View.VISIBLE);
                        skill4.setText(skills.get(3));
                        skill4.setVisibility(View.VISIBLE);
                    } else if (skills.size() == 3) {
                        skill1.setText(skills.get(0));
                        skill1.setVisibility(View.VISIBLE);
                        skill2.setText(skills.get(1));
                        skill2.setVisibility(View.VISIBLE);
                        skill3.setText(skills.get(2));
                        skill3.setVisibility(View.VISIBLE);
                    } else if (skills.size() == 2) {
                        skill1.setText(skills.get(0));
                        skill1.setVisibility(View.VISIBLE);
                        skill2.setText(skills.get(1));
                        skill2.setVisibility(View.VISIBLE);
                    } else if (skills.size() == 1) {
                        skill1.setText(skills.get(0));
                        skill1.setVisibility(View.VISIBLE);
                    }
                }

                TextView lable1 = viewHolder.findViewById(R.id.label1);
                TextView lable2 = viewHolder.findViewById(R.id.label2);
                TextView lable3 = viewHolder.findViewById(R.id.label3);
                TextView lable4 = viewHolder.findViewById(R.id.label4);
                lable1.setVisibility(View.GONE);
                lable2.setVisibility(View.GONE);
                lable3.setVisibility(View.GONE);
                lable4.setVisibility(View.GONE);

                List<String> labels = item.getLabel();
                if (labels != null && labels.size() > 0) {
                    if (labels.size() >= 4) {
                        lable1.setText(labels.get(0));
                        lable1.setVisibility(View.VISIBLE);
                        lable2.setText(labels.get(1));
                        lable2.setVisibility(View.VISIBLE);
                        lable3.setText(labels.get(2));
                        lable3.setVisibility(View.VISIBLE);
                        lable4.setText(labels.get(3));
                        lable4.setVisibility(View.VISIBLE);
                    } else if (labels.size() == 3) {
                        lable1.setText(labels.get(0));
                        lable1.setVisibility(View.VISIBLE);
                        lable2.setText(labels.get(1));
                        lable2.setVisibility(View.VISIBLE);
                        lable3.setText(labels.get(2));
                        lable3.setVisibility(View.VISIBLE);
                    } else if (labels.size() == 2) {
                        lable1.setText(labels.get(0));
                        lable1.setVisibility(View.VISIBLE);
                        lable2.setText(labels.get(1));
                        lable2.setVisibility(View.VISIBLE);
                    } else if (labels.size() == 1) {
                        lable1.setText(labels.get(0));
                        lable1.setVisibility(View.VISIBLE);
                    }
                }

                String is_rz = item.getIs_rz();
                if ("55".equals(is_rz)) {
                    authen.setText("已认证");
                } else {
                    authen.setText("未认证");
                }

                String statu = item.getStatus();
                if ("3".equals(statu)) {
                    status.setText("黑名单");
                } else if ("2".equals(statu)) {
                    status.setText("忙碌");
                } else {
                    status.setText("正常");
                }

                name.setText(item.getNick_name());
                exper.setText(item.getGz_year() + "年经验");

                city.setText(item.getArea_id());
                missto.setText(item.getMissto_l() + "咨询");
                order_no.setText("接单量 " + item.getGetorder_l());
                distance.setText(item.getDistance());
                if (!TextUtils.isEmpty(item.getScore())) {
                    score.setRating(Float.parseFloat(item.getScore()));
                }
                if (item.getmShield()=="1") {
                    shield.setImageResource(R.mipmap.schedu_shield);
                } else {
                    shield.setImageDrawable(null);
                }
                Glide.with(getActivity()).load(item.getPic()).error(R.mipmap.worker_list_img).into(logo);
            }
        };

        mScrollPicker.setAdapter(adapter);
        mScrollPicker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkerEntry entry = adapter.getItem(position);
//                Bundle args = new Bundle();
//                args.putSerializable(Constant.key1, entry);
//                WorkerDetailFragment fragment = new WorkerDetailFragment();
//                fragment.setArguments(args);
//                addFragment(fragment);
                //getData(entry.getPv_id());
            }
        });

        mScrollPicker.setOnItemSelectedListener(new ScrollPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelectedSchedu(ScrollPicker picker, int position) {
                WorkerEntry worker = datas.get(position);
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = worker;
                EventBus.getDefault().post(msg);
                getActivity().finish();
            }

            @Override
            public void onItemSelectedTalk(ScrollPicker picker, int position) {
                try {
                    WorkerEntry worker = datas.get(position);
                    UserEntry user = Utils.getUserEntry();
                    if (user != null) {
                        BaseDaoImpl talkDao = new BaseDaoImpl(TableTalk.class);
                        Object obj = talkDao.queryForFirst("mxcomeNo", worker.getMxcome_no());
                        if (obj == null) {
                            TableTalk talk = new TableTalk();
                            talk.setCid(worker.getCid());
                            talk.setMxcomeNo(worker.getMxcome_no());
                            talk.setGroupName(worker.getNick_name());
                            talk.setIcon(worker.getPic());
                            talk.setLastMsg("");
                            talk.setLastTime("");
                            talk.setBizType(1);  //业务类型 0:官方，1:客户，2:好友
                            talk.setUnreadCnt(0);
                            talk.setMeMxcomeNo(user.getMxcome_no());
                            talk.setSpecial("");
                            talkDao.save(talk);
                            obj = talkDao.queryForFirst("mxcomeNo", worker.getMxcome_no());
                        }
                        TableTalk talk = (TableTalk) obj;
                        Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                        intent.putExtra(Constant.key1, talk);
                        intent.putExtra(ZFrameActivity.CLASS_NAME, ChatRecyclerFragment.class.getName());
                        startActivityForResult(intent, 0x01);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtlmDefaultHandler() {
            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, mScrollPicker.getListView(), footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, mScrollPicker.getListView(), header);

            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mPtrFrame.refreshComplete();
                pageIndex++;
                queryData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.refreshComplete();
                pageIndex = 1;
                queryData();
            }
        });
        mPtrFrame.setPullToRefresh(false);
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                createPopWin(checkedId);
            }
        });
        //queryData();
        String cityStr = getChoiceCity();
        refreshLocation(cityStr);
    }

    private void getData(String pv_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", pv_id);
        NetWorkUtils.post(getActivity(), "appApi/getPiducomentShow.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                WorkDesignerDetailEntry entry = GsonUtils.getObjFromJSON(res, WorkDesignerDetailEntry.class);
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, WorkerDetailFragment.class.getName());
                intent.putExtra(Constant.key1, entry);
                startActivityForResult(intent, 102);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }


    public void queryData() {
        HashMap<String, String> map = new HashMap<>();
        UserEntry user = Utils.getUserEntry();
        map.put("user_id", user.getUser_id());
        map.put("mxcomeno", user.getMxcome_no());
        map.put("w_type", w_type);
        map.put("page_index", "" + pageIndex);
        map.put("page_size", "" + Constant.pageSize);
        LocationEntry location = Utils.getLocationEntry();
        map.put("longitude", "" + location.getLongitude());
        map.put("latitude", "" + location.getLatitude());
        NetWorkUtils.post(getActivity(), "appApi/getWorkCircle.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                int rows = resObj.optInt("rows");
                if (pageIndex * Constant.pageSize >= rows) { //最后一页
                    mPtrFrame.setMode(PtrFrameLayout.Mode.REFRESH);
                } else {
                    mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
                }
                if (pageIndex == 1) {
                    datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<WorkerEntry>>() {
                    }.getType());
                    adapter.replaceAll(datas);
                } else {
                    datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<WorkerEntry>>() {
                    }.getType());
                    adapter.addAll(datas);
                }
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        ImageView imgView = new ImageView(getActivity());
        imgView.setImageResource(R.mipmap.worker_title);
        toolbar.setRightImage(R.mipmap.seach_w_img);
        toolbar.setRightShow(true);
        toolbar.setMiddleView(imgView);
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, WorkerSeachFragment.class.getName());
                startActivity(intent);
            }
        });

    }

    public void createPopWin(int checkedId) {
        switch (checkedId) {
            case R.id.radio1:
                initFilter();
                break;
            case R.id.radio2:
                initArea();
                break;
            case R.id.radio3:
                initSkill();
                break;
            case R.id.radio4:
                initHeat();
                break;
            case R.id.radio5:
                initSort();
                break;
        }
    }

    private void initSort() {
        initImageAndTextColor();
        image5.setVisibility(View.VISIBLE);
        radio5.setTextColor(getColor(R.color.white));
        if (indexPager != 4) {
            indexPager = 4;
            classify_layout.removeAllViews();
            contentView = getLayoutInflater(R.layout.popwin_sort);
            RadioGroup rbSort = (RadioGroup) contentView.findViewById(R.id.rb_sort);
            LinearLayout bottom = (LinearLayout) contentView.findViewById(R.id.ll_bottom);
            bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            rbSort.check(R.id.button2);
            contentView.setOnClickListener(this);
            classify_layout.addView(contentView);
            setView();
        } else {
            initImageAndTextColor();
            classify_layout.removeAllViews();
            radioGroup.clearCheck();
            indexPager = -1;
        }
    }


    private void initHeat() {
        initImageAndTextColor();
        image4.setVisibility(View.VISIBLE);
        radio4.setTextColor(getColor(R.color.white));
        if (indexPager != 3) {
            indexPager = 3;
            classify_layout.removeAllViews();
            contentView = getLayoutInflater(R.layout.popwin_heat);
            RadioGroup rbHeat = (RadioGroup) contentView.findViewById(R.id.rb_heat);
            rbHeat.check(R.id.button3);
            contentView.setOnClickListener(this);
            classify_layout.addView(contentView);
            setView();
        } else {
            initImageAndTextColor();
            classify_layout.removeAllViews();
            radioGroup.clearCheck();
            indexPager = -1;
        }
    }

    private void initSkill() {
        initImageAndTextColor();
        image3.setVisibility(View.VISIBLE);
        radio3.setTextColor(getColor(R.color.white));

        if (indexPager != 2) {
            indexPager = 2;
            classify_layout.removeAllViews();
            contentView = getLayoutInflater(R.layout.popwin_skill);
            contentView.setOnClickListener(this);
            classify_layout.addView(contentView);
            setView();
        } else {
            initImageAndTextColor();
            classify_layout.removeAllViews();
            radioGroup.clearCheck();
            indexPager = -1;
        }
    }

    private void initArea() {
        initImageAndTextColor();
        image2.setVisibility(View.VISIBLE);
        radio2.setTextColor(getColor(R.color.white));

        if (indexPager != 1) {
            indexPager = 1;
            classify_layout.removeAllViews();
            contentView = getLayoutInflater(R.layout.popwin_area);
            GridView gridView = (GridView) contentView.findViewById(R.id.gridView);
            mAdapter = new CommonAdapter<TableArea>(getActivity(), R.layout.griditem_worker_area) {
                @Override
                protected void convert(ViewHolder viewHolder, TableArea tableArea, int i) {
                    TextView area = viewHolder.findViewById(R.id.area);
                    area.setText(tableArea.getArea());
                    if (pos == i) {
                        area.setTextColor(getColor(R.color.colorBlue));
                    } else {
                        area.setTextColor(getColor(R.color.colorGrey));
                    }
                }
            };
            gridView.setAdapter(mAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                    mAdapter.notifyDataSetChanged();
                }
            });
            mAdapter.replaceAll(areaList);
            contentView.setOnClickListener(this);
            TextView currentCity = (TextView) contentView.findViewById(R.id.current_city);
            String city = getChoiceCity();
            currentCity.setText("当前选择：" + city);
            TextView changeCity = (TextView) contentView.findViewById(R.id.change_city);
            changeCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LocationFragment.class.getName());
                    startActivityForResult(intent, 101);
                }
            });
            classify_layout.addView(contentView);
            setView();
        } else {
            initImageAndTextColor();
            classify_layout.removeAllViews();
            radioGroup.clearCheck();
            indexPager = -1;
        }
    }

    private void initFilter() {
        initImageAndTextColor();
        image1.setVisibility(View.VISIBLE);
        radio1.setTextColor(getColor(R.color.white));

        if (indexPager != 0) {
            indexPager = 0;
            classify_layout.removeAllViews();
            contentView = getLayoutInflater(R.layout.popwin_filter);
            contentView.setOnClickListener(this);
            classify_layout.addView(contentView);
            setView();
        } else {
            initImageAndTextColor();
            classify_layout.removeAllViews();
            radioGroup.clearCheck();
            indexPager = -1;
        }
    }

    private void setView() {
        LinearLayout ll_bottom = (LinearLayout) contentView.findViewById(R.id.ll_bottom);
        ll_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initImageAndTextColor();
                classify_layout.removeAllViews();
                indexPager = -1;
            }
        });
        radioGroup.clearCheck();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            TextView currentCity = (TextView) contentView.findViewById(R.id.current_city);
            String cityStr = getChoiceCity();
            currentCity.setText("当前选择：" + cityStr);
            locationLayout.setLocationText(cityStr);
            refreshLocation(cityStr);
            mAdapter.replaceAll(areaList);
        } else if (requestCode == 102 && resultCode == 404) {
            getActivity().finish();
        }
    }

    public String getChoiceCity() {
        String city = "";
        LocationEntry entry = Utils.getLocationEntry();
        if (!TextUtils.isEmpty(entry.getUserSelectCity())) {
            city = entry.getUserSelectCity();
        } else {
            city = entry.getCity();
        }
        return city;
    }

    public void refreshLocation(String cityStr) {
        BaseDaoImpl dao = new BaseDaoImpl(TableArea.class);
        try {
//                TableArea area = (TableArea) dao.queryForFirst("area", city);
            //由于百度地图定位数据不相同，这里会存在一个北京市，天津市，上海市，重庆市问题。
            if (cityStr.equals("北京市") || cityStr.equals("天津市")
                    || cityStr.equals("上海市") || cityStr.equals("重庆市")) {
                Object provObj = dao.queryForFirst("area", cityStr);
                if (provObj != null) {
                    TableArea prov = (TableArea) provObj;
                    Object cityObj = dao.queryForFirst("parentid", prov.getAreaid() + "");
                    if (cityObj != null) {
                        TableArea city = (TableArea) cityObj;
                        areaList = dao.query("parentid", city.getAreaid() + "");
                    }
                }
            } else {
                List<TableArea> datas = dao.getDao().queryBuilder().where().eq("areatype", 2).and().like("area", '%' + cityStr + '%').query();
                if (datas != null && datas.size() > 0) {
                    areaList = dao.query("parentid", datas.get(0).getAreaid() + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LocationEntry entry) {
        String cityStr = "";
        if (!TextUtils.isEmpty(entry.getUserSelectCity())) {
            cityStr = entry.getUserSelectCity();
        } else {
            cityStr = entry.getCity();
        }
        refreshLocation(cityStr);
    }

    private void initImageAndTextColor() {
        image1.setVisibility(View.INVISIBLE);
        image2.setVisibility(View.INVISIBLE);
        image3.setVisibility(View.INVISIBLE);
        image4.setVisibility(View.INVISIBLE);
        image5.setVisibility(View.INVISIBLE);

        radio1.setTextColor(getColor(R.color.worker_title_color));
        radio2.setTextColor(getColor(R.color.worker_title_color));
        radio3.setTextColor(getColor(R.color.worker_title_color));
        radio4.setTextColor(getColor(R.color.worker_title_color));
        radio5.setTextColor(getColor(R.color.worker_title_color));

    }
}
