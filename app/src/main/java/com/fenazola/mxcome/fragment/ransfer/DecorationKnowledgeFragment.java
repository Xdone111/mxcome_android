package com.fenazola.mxcome.fragment.ransfer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
import com.zss.library.activity.ZFrameActivity;
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

public class DecorationKnowledgeFragment extends BaseFragment implements View.OnClickListener {

    private List<LinearLayout> tvs = new ArrayList<LinearLayout>();
    private List<ImageView> ivs = new ArrayList<ImageView>();
    private List<TextView> tvNames = new ArrayList<TextView>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_ransfer_decoration_knowledge;
    }

    private List<KonwledgeEntry> konwledgeEntrys = new ArrayList<KonwledgeEntry>();
    KonwledgeEntry entry;
    @Override
    public void initView() {
        super.initView();
        //以后可能会改成列表
        for (int i = 0; i < 8; i++) {
            tvs.add((LinearLayout) findViewById(getViewID("title" + (i + 1))));
            ivs.add((ImageView) findViewById(getViewID("title" + (i + 1) + "_iv")));
            tvNames.add((TextView) findViewById(getViewID("title" + (i + 1) + "_tv")));
            final int index = i;
            tvs.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initToInfo(index);
                }
            });
        }

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        entry=(KonwledgeEntry) getArguments().get(Constant.key1);
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

    public int getViewID(String IDName) {
        return getActivity().getResources().getIdentifier(IDName, "id", "com.fenazola.mxcome");
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
                for (int i = 0; i < tvs.size(); i++) {
                    final int index = i;
                    Glide.with(getActivity()).load(Constant.imageUrl+konwledgeEntrys.get(i).getIcon()).error(R.mipmap.sheji_ico).into(ivs.get(i));
                    Glide.with(getActivity())
                            .load(Constant.imageUrl+konwledgeEntrys.get(i).getBackground())
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>(120, 180) {//设置宽高
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Drawable drawable = new BitmapDrawable(resource);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        tvs.get(index).setBackground(drawable); //设置背景
                                    }
                                }
                            });
                    tvNames.get(i).setText(konwledgeEntrys.get(i).getCategory_name());

                }
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
