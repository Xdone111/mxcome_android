package com.fenazola.mxcome.fragment.project;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.adapter.RecyclerInfoAdapter;
import com.fenazola.mxcome.entry.AdvertsEntry;
import com.fenazola.mxcome.entry.MaterialEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DataCache;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;
import com.zss.library.widget.CommonDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 选材
 */
public class MaterialFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView recycler;
    private RecyclerInfoAdapter adapter;
    private ListView proListView;
    private CommonAdapter<AdvertsEntry> proAdapter;
    private MaterialEntry materialEntry;
    private int w_type = 1001;
    private String grade = DataCache.ENUM_PW_SHE;

    private RelativeLayout scheme_layout; //方案布局
    private RelativeLayout scheme_bg; //方案背景
    private TextView tv_center; //档次
    private TextView tv_she; //奢
    private TextView tv_hao; //豪
    private TextView tv_shu; //舒
    private TextView tv_jian; //简
    private CommonToolbar toolbar;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_material;
    }

    @Override
    public void initView() {
        super.initView();
        recycler = (RecyclerView) findViewById(R.id.recycler);
        proListView = (ListView) findViewById(R.id.listView);

        scheme_layout = (RelativeLayout) findViewById(R.id.scheme_layout);
        scheme_bg = (RelativeLayout) findViewById(R.id.scheme_bg);
        tv_center = (TextView) findViewById(R.id.tv_center);
        tv_she = (TextView) findViewById(R.id.tv_she);
        tv_hao = (TextView) findViewById(R.id.tv_hao);
        tv_shu = (TextView) findViewById(R.id.tv_shu);
        tv_jian = (TextView) findViewById(R.id.tv_jian);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        scheme_layout.setOnClickListener(this);
        scheme_bg.setOnClickListener(this);
        tv_center.setOnClickListener(this);
        tv_she.setOnClickListener(this);
        tv_hao.setOnClickListener(this);
        tv_shu.setOnClickListener(this);
        tv_jian.setOnClickListener(this);

        Bundle args = getArguments();
        if (args.containsKey(Constant.key1)) {
            w_type = args.getInt(Constant.key1);
        }
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(manager);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new RecyclerInfoAdapter(getActivity());
        adapter.setOnItemClickLitener(new RecyclerInfoAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                materialEntry = adapter.getItem(position);
                reqData(materialEntry.getItem_id());
            }
        });
        recycler.setAdapter(adapter);

        proAdapter = new CommonAdapter<AdvertsEntry>(getActivity(), R.layout.listitem_material_product) {
            @Override
            protected void convert(ViewHolder viewHolder, AdvertsEntry entry, final int position) {
                TextView tv_choice = viewHolder.findViewById(R.id.tv_choice);
                ImageView img_choice = viewHolder.findViewById(R.id.img_choice);
                AdvertsEntry item = proAdapter.getItem(position);
                ImageView image = viewHolder.findViewById(R.id.image);
                String advId = getMaterialMap().get(materialEntry.getItem_id());
                LogUtils.i("---zss---", "-----advId = " + advId);
                if (advId != null && advId.equals(entry.getAdvert_id())) {
                    tv_choice.setVisibility(View.GONE);
                    img_choice.setVisibility(View.VISIBLE);
                } else {
                    tv_choice.setVisibility(View.VISIBLE);
                    img_choice.setVisibility(View.GONE);
                }
                String advert_pics = item.getAdvert_pics();
                LogUtils.i("---zss---", "-----url = " + Constant.imageUrl + advert_pics);
                Glide.with(getActivity()).load(Constant.imageUrl + advert_pics).into(image);
                tv_choice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentPos = adapter.getCurrentPos();
                        AdvertsEntry item = proAdapter.getItem(position);
                        LogUtils.i("---zss---", materialEntry.getItem_id() + " = " + item.getAdvert_id());
                        getMaterialMap().put(materialEntry.getItem_id(), item.getAdvert_id());
                        proAdapter.notifyDataSetChanged();
                        if (currentPos < adapter.getItemCount() - 1) {
                            int position = adapter.getCurrentPos() + 1;
                            adapter.setCurrentPos(position);
                            materialEntry = adapter.getItem(position);
                            reqData(materialEntry.getItem_id());
                        } else {
                            CommonDialog dialog = new CommonDialog(getActivity());
                            dialog.setTitle("材料配置");
                            dialog.setContentText(getMaterialPackName(w_type) + "材料配置已完成");
                            dialog.setOnClickConfirmListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    choiceFinish();
                                }
                            });
                            dialog.show();
                        }
                    }
                });
            }
        };
        proListView.setAdapter(proAdapter);
    }

    public int getNextPack(Integer nextType) { //过滤不能选择的包
        if (DataCache.disableList.contains(nextType)) {
            return getNextPack(nextType + 1);
        } else {
            return nextType;
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setTitle(getMaterialPackName(w_type));
        toolbar.setTitleColor(getColor(R.color.black));
        getData(w_type);
        toolbar.setRightTextBgColor(getColor(R.color.colorBlue));
        toolbar.setRightText(DataCache.dictMap.get(grade));
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheme_bg.setVisibility(View.VISIBLE);
                scheme_layout.setVisibility(View.VISIBLE);
            }
        });
    }

    public String getMaterialPackName(int type) {
        if (type == 1001) {
            return getString(R.string.project_schedu_material1);
        } else if (type == 1002) {
            return getString(R.string.project_schedu_material2);
        } else if (type == 1003) {
            //return getString(R.string.project_schedu_material3);
            return "安装包";

        } else if (type == 1004) {
            return getString(R.string.project_schedu_material4);
        } else if (type == 1005) {
            return getString(R.string.project_schedu_material5);
        } else if (type == 1006) {
            return getString(R.string.project_schedu_material6);
        } else {
            return getString(R.string.project_schedu_material7);
        }
    }

    public HashMap<String, String> getMaterialMap() {
        if (w_type == 1001) {
            return DataCache.material1001;
        } else if (w_type == 1002) {
            return DataCache.material1002;
        } else if (w_type == 1003) {
            return DataCache.material1003;
        } else if (w_type == 1004) {
            return DataCache.material1004;
        } else if (w_type == 1005) {
            return DataCache.material1005;
        } else {
            return DataCache.material1006;
        }
    }

    private void getData(int type) {
        HashMap<String, String> map = new HashMap<>();
        UserEntry user = Utils.getUserEntry();
        map.put("user_id", user.getUser_id());
        map.put("pitem_id", type + "");
        NetWorkUtils.post(getActivity(), "advertApi/queryZiNengFunByPID.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<MaterialEntry> data = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<MaterialEntry>>() {
                }.getType());
                adapter.replaceAll(data);
                materialEntry = adapter.getItem(0);
                reqData(materialEntry.getItem_id());
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });

    }

    private void reqData(String clumId) {
        HashMap<String, String> map = new HashMap<>();
        UserEntry user = Utils.getUserEntry();
        map.put("user_id", user.getUser_id());
        map.put("clum_id", clumId); //分类ID
        map.put("dec_grade", grade); //档次
        NetWorkUtils.post(getActivity(), "hrpc/getAvertDataForClum", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<AdvertsEntry> data = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<AdvertsEntry>>() {
                }.getType());
                proAdapter.replaceAll(data);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public void choiceFinish() {
        Message msg = Message.obtain();
        msg.what = 2;
        EventBus.getDefault().post(msg);
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scheme_layout: //背景阴影点击
                scheme_bg.setVisibility(View.GONE);
                scheme_layout.setVisibility(View.GONE);
                break;
            case R.id.tv_she:
                scheme_bg.setVisibility(View.GONE);
                scheme_layout.setVisibility(View.GONE);
                setGradeText(DataCache.ENUM_PW_SHE);
                reqData(materialEntry.getItem_id());
                break;
            case R.id.tv_hao:
                scheme_bg.setVisibility(View.GONE);
                scheme_layout.setVisibility(View.GONE);
                setGradeText(DataCache.ENUM_PW_HAO);
                reqData(materialEntry.getItem_id());
                break;
            case R.id.tv_shu:
                scheme_bg.setVisibility(View.GONE);
                scheme_layout.setVisibility(View.GONE);
                setGradeText(DataCache.ENUM_PW_SHU);
                reqData(materialEntry.getItem_id());
                break;
            case R.id.tv_jian:
                scheme_bg.setVisibility(View.GONE);
                scheme_layout.setVisibility(View.GONE);
                setGradeText(DataCache.ENUM_PW_JIAN);
                reqData(materialEntry.getItem_id());
                break;
        }
    }

    public void setGradeText(String gradeText) {
        grade = gradeText;
        toolbar.setRightText(DataCache.dictMap.get(grade));
    }

}
