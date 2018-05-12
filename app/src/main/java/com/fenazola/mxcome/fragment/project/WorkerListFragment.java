package com.fenazola.mxcome.fragment.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.WorkerListEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zm on 2017/6/8.
 */

public class WorkerListFragment extends BaseFragment {

    private String pid;
    private ListView listView;
    private CommonAdapter<WorkerListEntry> mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_worker_list;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle args = getArguments();
        pid = args.getString(Constant.key1);
        mAdapter = new CommonAdapter<WorkerListEntry>(getActivity(), R.layout.listitem_worker_list) {
            @Override
            protected void convert(ViewHolder viewHolder, final WorkerListEntry workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.name);
                TextView detail = viewHolder.findViewById(R.id.detail);
                TextView comment = viewHolder.findViewById(R.id.comment);
                comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (workerListEntry.getUser_type().equals("1")) {
                            DesignerCommentWorkerFragment fragment = new DesignerCommentWorkerFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("workerListEntry", workerListEntry);
                            fragment.setArguments(bundle);
                            addFragment(fragment);
                        } else if (workerListEntry.getUser_type().equals("2")) {
                            DesignerCommentFragment fragment = new DesignerCommentFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("workerListEntry", workerListEntry);
                            fragment.setArguments(bundle);
                            addFragment(fragment);
                        }

                    }
                });
                name.setText(workerListEntry.getNick_name());
                detail.setText(workerListEntry.getArea_id());
                comment.setText("评论");
            }
        };
        listView.setAdapter(mAdapter);
        refreshUI();
    }

    public void refreshUI() {
        Map<String, String> map = new HashMap<>();
        UserEntry user = Utils.getUserEntry();
        map.put("mxcome_no", user.getMxcome_no());
        map.put("pid", pid);
        NetWorkUtils.post(getActivity(), "decora/findDecoraAllWorker.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<WorkerListEntry> list = GsonUtils.getListFromJSON(res, WorkerListEntry.class);
                mAdapter.addAll(list);
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
        toolbar.setTitle("施工人员列表");
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }
}
