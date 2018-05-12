package com.fenazola.mxcome.fragment.me;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.BaseEntry;
import com.fenazola.mxcome.entry.CommentDatailEntry;
import com.fenazola.mxcome.entry.CommentEntry;
import com.fenazola.mxcome.entry.WorkDesignerDetailEntry;
import com.fenazola.mxcome.utils.DateUtils;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/5/23.
 */

public class CommentDetailFragment extends BaseFragment implements View.OnClickListener {

    private ListView listView;
    private CommonAdapter<CommentEntry> adapter;
    private TextView mark;
    private TextView orderNo;
    private TextView skill1, skill2;
    private TextView joinTime;
    private TextView tv_comment1, tv_comment2, tv_comment3, tv_comment4;
    private TextView tvt_comment1, tvt_comment2, tvt_comment3, tvt_comment4;
    private LinearLayout ll_comment1, ll_comment2, ll_comment3, ll_comment4;
    List<CommentEntry> commentDatailEntries = new ArrayList<CommentEntry>();
    ArrayList<CommentEntry> commentListHp = new ArrayList<CommentEntry>();
    ArrayList<CommentEntry> commentListZp = new ArrayList<CommentEntry>();
    ArrayList<CommentEntry> commentListCp = new ArrayList<CommentEntry>();
    WorkDesignerDetailEntry baseEntry = new WorkDesignerDetailEntry();
    List<CommentEntry> commentDatailEntriesType = new ArrayList<CommentEntry>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_comment_detail;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
        mark = (TextView) findViewById(R.id.mark);
        orderNo = (TextView) findViewById(R.id.order_no);
        skill1 = (TextView) findViewById(R.id.skill1);
        skill2 = (TextView) findViewById(R.id.skill2);
        joinTime = (TextView) findViewById(R.id.join_time);
        tv_comment1 = (TextView) findViewById(R.id.tv_comment1);
        tv_comment2 = (TextView) findViewById(R.id.tv_comment2);
        tv_comment3 = (TextView) findViewById(R.id.tv_comment3);
        tv_comment4 = (TextView) findViewById(R.id.tv_comment4);
        tvt_comment1 = (TextView) findViewById(R.id.tvt_comment1);
        tvt_comment2 = (TextView) findViewById(R.id.tvt_comment2);
        tvt_comment3 = (TextView) findViewById(R.id.tvt_comment3);
        tvt_comment4 = (TextView) findViewById(R.id.tvt_comment4);
        ll_comment1 = (LinearLayout) findViewById(R.id.ll_comment1);
        ll_comment2 = (LinearLayout) findViewById(R.id.ll_comment2);
        ll_comment3 = (LinearLayout) findViewById(R.id.ll_comment3);
        ll_comment4 = (LinearLayout) findViewById(R.id.ll_comment4);
        ll_comment1.setOnClickListener(this);
        ll_comment2.setOnClickListener(this);
        ll_comment3.setOnClickListener(this);
        ll_comment4.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        commentDatailEntries = getActivity().getIntent().getParcelableArrayListExtra("comment");
        baseEntry = (WorkDesignerDetailEntry) getActivity().getIntent().getSerializableExtra("base");
        commentDatailEntriesType = commentDatailEntries;
        for (int i = 0; i < commentDatailEntries.size(); i++) {
            if (commentDatailEntries.get(i).getHp().equals("1")) {
                commentListHp.add(commentDatailEntries.get(i));
            }
            if (commentDatailEntries.get(i).getZp().equals("1")) {
                commentListZp.add(commentDatailEntries.get(i));
            }
            if (commentDatailEntries.get(i).getCp().equals("1")) {
                commentListCp.add(commentDatailEntries.get(i));
            }
        }
        if (orderNo != null && baseEntry != null && baseEntry.getBase() != null) {
            orderNo.setText(baseEntry.getBase().getGetorder_l());
            joinTime.setText(DateUtils.getTime(Long.parseLong(baseEntry.getBase().getCreate_time())));
            tv_comment1.setText(baseEntry.getCommentNum());
            tv_comment2.setText(baseEntry.getTotal_hp());
            tv_comment3.setText(baseEntry.getTotal_zp());
            tv_comment4.setText(baseEntry.getTotal_cp());
        }

        //TODO 有可能会是图文的
//        adapter = new CommonAdapter<CommentEntry>(getActivity(), R.layout.listitem_comment_detail) {
//            @Override
//            protected void convert(ViewHolder viewHolder, CommentEntry entry, int i) {
//                TextView nikeName = viewHolder.findViewById(R.id.nikeName);
//                TextView label1 = viewHolder.findViewById(R.id.label1);
//                TextView label2 = viewHolder.findViewById(R.id.label2);
//                TextView label3 = viewHolder.findViewById(R.id.label3);
//                TextView time = viewHolder.findViewById(R.id.time);
//                TextView msg = viewHolder.findViewById(R.id.show_msg);
//                ImageView image1 = viewHolder.findViewById(R.id.image1);
//                ImageView image2 = viewHolder.findViewById(R.id.image2);
//                RatingBar ratingBar1 = viewHolder.findViewById(R.id.ratingBar1);
//                RatingBar ratingBar2 = viewHolder.findViewById(R.id.ratingBar2);
//                RatingBar ratingBar3 = viewHolder.findViewById(R.id.ratingBar3);
//
//                nikeName.setText(entry.getUser_name());
//                msg.setText(entry.getMessage());
//                Glide.with(context).load(entry.getPic_url()).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.msg_photo).into(image1);
//                ratingBar2.setRating(Float.parseFloat(entry.getWork_quality()));
//                ratingBar1.setRating(Float.parseFloat(entry.getService_attitude()));
//                //TODO 暂缺 完成度
//                ratingBar3.setRating((float)5.0);
//
//            }
//        };
        initAdapter();
    }

    private void initAdapter() {
        if (adapter != null) {
            adapter.clear();
            adapter = null;
        }
        adapter = new CommonAdapter<CommentEntry>(getActivity(), R.layout.comment_item_new) {
            @Override
            protected void convert(ViewHolder viewHolder, CommentEntry entry, int i) {
                TextView nikeName = viewHolder.findViewById(R.id.nikeName);
                TextView time = viewHolder.findViewById(R.id.time);
                TextView msg = viewHolder.findViewById(R.id.show_msg);
                ImageView image1 = viewHolder.findViewById(R.id.image1);
                RatingBar ratingBar1 = viewHolder.findViewById(R.id.ratingBar1);
                RatingBar ratingBar2 = viewHolder.findViewById(R.id.ratingBar2);

                nikeName.setText(entry.getUser_name());
                msg.setText(entry.getMessage());
                Glide.with(context).load(entry.getPic_url()).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.msg_photo).into(image1);
                ratingBar2.setRating(Float.parseFloat(entry.getWork_quality()));
                ratingBar1.setRating(Float.parseFloat(entry.getService_attitude()));
                //TODO 暂缺 完成度
                //ratingBar3.setRating((float)5.0);

            }
        };
        listView.setAdapter(adapter);
        adapter.addAll(commentDatailEntriesType);
    }

    private void initSumComment() {
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("评论详情");
        toolbar.setTitleColor(getColor(R.color.white));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_comment1:
                initChange(1);
                break;
            case R.id.ll_comment2:
                initChange(2);
                break;
            case R.id.ll_comment3:
                initChange(3);
                break;
            case R.id.ll_comment4:
                initChange(4);
                break;
        }

    }

    private void initChange(int i) {
        tv_comment1.setTextColor(getActivity().getResources().getColor(R.color.colorGrey));
        tvt_comment1.setTextColor(getActivity().getResources().getColor(R.color.colorGrey));
        tv_comment2.setTextColor(getActivity().getResources().getColor(R.color.colorGrey));
        tvt_comment2.setTextColor(getActivity().getResources().getColor(R.color.colorGrey));
        tv_comment3.setTextColor(getActivity().getResources().getColor(R.color.colorGrey));
        tvt_comment3.setTextColor(getActivity().getResources().getColor(R.color.colorGrey));
        tv_comment4.setTextColor(getActivity().getResources().getColor(R.color.colorGrey));
        tvt_comment4.setTextColor(getActivity().getResources().getColor(R.color.colorGrey));
        if (i == 1) {
            tv_comment1.setTextColor(getActivity().getResources().getColor(R.color.colorBlue));
            tvt_comment1.setTextColor(getActivity().getResources().getColor(R.color.colorBlue));
            commentDatailEntriesType = commentDatailEntries;
        } else if (i == 2) {
            tv_comment2.setTextColor(getActivity().getResources().getColor(R.color.colorBlue));
            tvt_comment2.setTextColor(getActivity().getResources().getColor(R.color.colorBlue));
            commentDatailEntriesType = commentListHp;
        } else if (i == 3) {
            tv_comment3.setTextColor(getActivity().getResources().getColor(R.color.colorBlue));
            tvt_comment3.setTextColor(getActivity().getResources().getColor(R.color.colorBlue));
            commentDatailEntriesType = commentListZp;
        } else if (i == 4) {
            tv_comment4.setTextColor(getActivity().getResources().getColor(R.color.colorBlue));
            tvt_comment4.setTextColor(getActivity().getResources().getColor(R.color.colorBlue));
            commentDatailEntriesType = commentListCp;
        }
        initAdapter();
    }
}
