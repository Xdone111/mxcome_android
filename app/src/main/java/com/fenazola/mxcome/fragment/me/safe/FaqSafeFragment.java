package com.fenazola.mxcome.fragment.me.safe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.FeedbackFragment;
import com.fenazola.mxcome.fragment.sercentre.FaqInputFragment;
import com.fenazola.mxcome.widget.MyGridView;
import com.fenazola.mxcome.widget.MyListView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * 常见问题 安全中心
 * @author xhx
 */
public class FaqSafeFragment extends BaseFragment implements View.OnClickListener{
    private MyListView listView;
    private MyGridView listView1;
    private CommonAdapter<String> mAdapter,mAdapter1;
    ArrayList<String> names=new ArrayList<String>();
    ArrayList<String> names1=new ArrayList<String>();
    TextView fandback_tv;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_faq_safe;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(MyListView)findViewById(R.id.listView);
        listView1=(MyGridView) findViewById(R.id.listView_1);
        fandback_tv=(TextView)findViewById(R.id.fandback_tv);

    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        //TODO
        names.add("注册MXCOME的方法？");
        names.add("MXCOME密码忘记了如何找回？");
        names.add("忘记了MXCOME账号怎么办？");
        names.add("怎么冻结MXCOME账号？");
        names.add("登录提示短信验证（手机不在身边）？");

        names1.add("账号密码");
        names1.add("账号保护");
        names1.add("注册账号");
        names1.add("绑定/解绑");
        names1.add("冻结/解冻/封号");
        names1.add("举报恶意行为");

        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_history_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                name.setText(workerListEntry);
            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);

        mAdapter1 = new CommonAdapter<String>(getActivity(), R.layout.layout_history_item_gv) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                name.setText(workerListEntry);
            }
        };
        listView1.setAdapter(mAdapter1);
        mAdapter1.addAll(names1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqInputFragment.class.getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fandback_tv:
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FeedbackFragment.class.getName());
                startActivity(intent);
                break;

        }
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("常见问题");
        toolbar.setBgColor(getResources().getColor(R.color.colorBlue));
        toolbar.setTitleColor(Color.WHITE);
        //toolbar.setLeftImage(getResources().getDrawable(R.mipmap.project_left));
        //toolbar.setRightImage(R.mipmap.seach_right_img);
    }
}
