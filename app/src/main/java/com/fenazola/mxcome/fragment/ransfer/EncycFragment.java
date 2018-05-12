package com.fenazola.mxcome.fragment.ransfer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.EncycEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.ransfer.KonwledgeEntry;
import com.fenazola.mxcome.fragment.main.example.ExampleFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 百科
 */
public class EncycFragment extends BaseFragment {

    private GridView gridView;
    private CommonAdapter<KonwledgeEntry> adapter;
    //private List<EncycEntry> datas;
//    private int [] mIcons = new int[] { R.mipmap.ransfer_icon1, R.mipmap.ransfer_icon2, R.mipmap.ransfer_icon3, R.mipmap.ransfer_icon4 };
//    private int [] mTitles = new int[]{ R.string.ransfer_item1, R.string.ransfer_item2, R.string.ransfer_item3, R.string.ransfer_item4 };
    private List<KonwledgeEntry> konwledgeEntrys = new ArrayList<KonwledgeEntry>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_grid;
    }

    @Override
    public void initView() {
        super.initView();
        gridView = (GridView)findViewById(R.id.gridView);
        gridView.setNumColumns(2);

    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        adapter = new CommonAdapter<KonwledgeEntry>(getActivity(), R.layout.griditem_encyc) {
            @Override
            protected void convert(ViewHolder viewHolder, KonwledgeEntry item, int position) {
                ImageView icon = (ImageView)viewHolder.findViewById(R.id.icon);
                Glide.with(getActivity()).load(Constant.imageUrl+konwledgeEntrys.get(position).getBackground()).error(R.mipmap.ransfer_icon1).into(icon);
                TextView title = (TextView)viewHolder.findViewById(R.id.title);
                title.setText(item.getCategory_name());
            }
        };
        gridView.setAdapter(adapter);
        adapter.addAll(konwledgeEntrys);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if(position==0){
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, DecorationKnowledgeNewFragment.class.getName());
                intent.putExtra(Constant.key1,konwledgeEntrys.get(position));
                startActivity(intent);
                //}
            }
        });
        queryVerifyInfo();
    }
    private void queryVerifyInfo() {
        UserEntry user = Utils.getUserEntry();
        Map<String, String> map = new HashMap<>();
        map.put("parent_id", "0");

        NetWorkUtils.postUrl(getActivity(), Constant.newBaseUrl + "article/queryArticleCategoryByPId", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                konwledgeEntrys = GsonUtils.getListFromJSON(res, KonwledgeEntry.class);
                for (int i=0;i<konwledgeEntrys.size();i++){
                    String name=konwledgeEntrys.get(i).getCategory_name();
                    if(name.length()>=4){
                        konwledgeEntrys.get(i).setCategory_name((name.substring(0,2)+"\n"+name.substring(2,4)));
                    }
                }
                adapter.replaceAll(konwledgeEntrys);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

}
