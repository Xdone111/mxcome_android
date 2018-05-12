package com.fenazola.mxcome.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.SharedUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.CircleMenuLayout;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义菜单
 * Created by xhx on 2017/6/20.
 */

public class CustomFragment extends BaseFragment {

    /**所有菜单项*/
    List<String> lists;
    /**圆圈里面的菜单项*/
    List<String> defautLists=new ArrayList<String>();
    /**与安全外的可选菜单项*/
    List<String> otherLists;
    /**菜单文字颜色*/
    List<Integer> mItemTextColors=new ArrayList<Integer>();
    /**菜单背景颜色*/
    List<Integer> mItemIds=new ArrayList<Integer>();
    /**可选项的列表*/
    private GridView otherLy;
    /**可选菜单适配器*/
    private CommonAdapter<String> mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_custom;
    }

    @Override
    public void initView() {
        super.initView();
        //menuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        setBgColor(R.color.white);
        otherLy=(GridView)findViewById(R.id.show_other_ly);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //获取所有菜单项
        lists= Utils.getMainType();
        //获取已选菜单项
        defautLists=new SharedUtils(getContext()).loadArray(defautLists);
        //得到未选可选的菜单项
        otherLists=lists;
        otherLists.removeAll(defautLists);

        Log.i("XHX",otherLists.toString());
        otherLy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//可选菜单项的点击事件

                initChangeMenu(position,false);
            }
        });
        initGV();
    }

    /***
     * 修改已选和未选菜单项的值
     * @param pos
     * @param isMenu
     */
    private void initChangeMenu(int pos,boolean isMenu) {
        if(defautLists.size()>3) {
            defautLists.set(defautLists.size() - 1, otherLists.get(pos));
        }else{
            defautLists.add(otherLists.get(pos));
        }
        new SharedUtils(getContext()).saveArray(defautLists);
        getActivity().onBackPressed();
    }

    /***
     * 刷新UI
     */
    private void initGV() {
        if(mAdapter!=null)
            mAdapter.clear();
            mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_custom_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String couponEntry, int i) {
                TextView tv1 = viewHolder.findViewById(R.id.list_item_tv);
                String s=" ";
                StringBuffer sb = new StringBuffer(couponEntry);
                sb.insert(1,s);
                tv1.setText(sb.toString());
            }
        };
        otherLy.setAdapter(mAdapter);
        mAdapter.addAll(otherLists);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("按钮自定义");
    }
}
