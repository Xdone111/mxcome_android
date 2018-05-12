package com.fenazola.mxcome.fragment.ransfer;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.ransfer.DecorationKnowleEntry;
import com.fenazola.mxcome.entry.ransfer.KonwledgeEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
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
 * 装修知识详情列表页
 */

public class DecorationKnowledgeInfoFragment extends BaseFragment {
    private ListView listView;
    private CommonAdapter<DecorationKnowleEntry> mAdapter;
    private List<DecorationKnowleEntry> names=new ArrayList<DecorationKnowleEntry>();
    private KonwledgeEntry entry=new KonwledgeEntry();
    private String tit="";

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_decoration_info_list;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(ListView) findViewById(R.id.show_lv);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        entry=(KonwledgeEntry) getArguments().get(Constant.key1);
        queryVerifyInfo(entry);

        tit=entry.getCategory_name();

        //模拟数据
        for (int i=0;i<10;i++){
            DecorationKnowleEntry entry=new DecorationKnowleEntry("yyyy-MM-0"+i,"我是装修知识"+tit+i,"10000"+i,"876"+i,"");
            names.add(entry);
        }
        mAdapter = new CommonAdapter<DecorationKnowleEntry>(getActivity(), R.layout.layout_decoration_list_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final DecorationKnowleEntry entry, final int i) {
                TextView date = viewHolder.findViewById(R.id.tv_data);
                TextView title = viewHolder.findViewById(R.id.tv_title);
                TextView look = viewHolder.findViewById(R.id.tv_look);
                TextView coll = viewHolder.findViewById(R.id.tv_coll);
                ImageView iv = viewHolder.findViewById(R.id.iv);
                date.setText(entry.getDate());
                title.setText(entry.getTitle());
                look.setText(entry.getLook());
                coll.setText(entry.getLook());

            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    private void queryVerifyInfo(KonwledgeEntry key) {
        UserEntry user = Utils.getUserEntry();
        Map<String, String> map = new HashMap<>();
        map.put("category_id", key.getId());

        NetWorkUtils.postUrl(getActivity(), Constant.newBaseUrl+"article/queryArticleList", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {

            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle(tit);
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }
}
