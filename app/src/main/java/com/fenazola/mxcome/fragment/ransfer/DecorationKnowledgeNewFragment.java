package com.fenazola.mxcome.fragment.ransfer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.ransfer.KonwledgeEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.MyGridView;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xuhuixiang on 2017/7/28.
 * 装修知识
 */

public class DecorationKnowledgeNewFragment extends BaseFragment implements View.OnClickListener {

    private MyGridView glist;
    private CommonAdapter<KonwledgeEntry> adapter;
    private List<KonwledgeEntry> konwledgeEntrys = new ArrayList<KonwledgeEntry>();
    KonwledgeEntry entry;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ransfer_decoration_knowledge_new;
    }

    @Override
    public void initView() {
        super.initView();
        glist=(MyGridView)findViewById(R.id.show_list);
        glist.setNumColumns(3);


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        entry=(KonwledgeEntry) getArguments().get(Constant.key1);
        adapter = new CommonAdapter<KonwledgeEntry>(getActivity(), R.layout.griditem_encyc_info) {
            @Override
            protected void convert(ViewHolder viewHolder, KonwledgeEntry item, int position) {
                final LinearLayout bg=(LinearLayout)viewHolder.findViewById(R.id.show_bg);
                CircleImageView icon = (CircleImageView)viewHolder.findViewById(R.id.title_iv);
                Glide.with(getActivity()).load(Constant.imageUrl+konwledgeEntrys.get(position).getIcon()).error(R.mipmap.sheji_ico).into(icon);
                final TextView title = (TextView)viewHolder.findViewById(R.id.title_tv);
                title.setText(item.getCategory_name());
                Glide.with(getActivity())
                        .load(Constant.imageUrl+konwledgeEntrys.get(position).getBackground())
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(120, 180) {//设置宽高
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                Drawable drawable = new BitmapDrawable(resource);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    bg.setBackground(drawable); //设置背景
                                }
                            }
                        });
            }
        };
        glist.setAdapter(adapter);
        adapter.addAll(konwledgeEntrys);
        glist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if(position==0){
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, DecorationKnowledgeInfoFragment.class.getName());
                intent.putExtra(Constant.key1,konwledgeEntrys.get(position));
                startActivity(intent);
                //}
            }
        });
        queryVerifyInfo();

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("装修知识");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void queryVerifyInfo() {
        UserEntry user = Utils.getUserEntry();
        Map<String, String> map = new HashMap<>();
        map.put("parent_id", entry.getId());

        NetWorkUtils.postUrl(getActivity(), Constant.newBaseUrl + "article/queryArticleCategoryByPId", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                konwledgeEntrys = GsonUtils.getListFromJSON(res, KonwledgeEntry.class);
                adapter.replaceAll(konwledgeEntrys);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    private void initToInfo(int index) {
        Intent intent = new Intent(getActivity(), ZFrameActivity.class);
        intent.putExtra(ZFrameActivity.CLASS_NAME, DecorationKnowledgeInfoFragment.class.getName());
        intent.putExtra(Constant.key1, konwledgeEntrys.get(index));
        startActivity(intent);
    }
}
