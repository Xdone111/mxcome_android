package com.fenazola.mxcome.fragment.project;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.WorkerListEntry;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 工程评分
 * Created by XDONE on 2017/6/7.
 */

public class DesignerCommentWorkerFragment extends BaseFragment implements View.OnClickListener {
    /**
     * 好评中评差评图片
     */
    private ImageView goodIv, commentsIv, badIv;
    /**
     * 好评中评差评文字
     */
    private TextView goodTv, commentsTv, badTv;
    /**
     * 去编辑文本
     */
    private TextView toEditTv;
    /**
     * 去编辑图标
     */
    private ImageView toEidtIv;
    /**
     * 去编辑容器
     */
    private LinearLayout toEidtLy;
    /**
     * 编辑评论内容
     */
    private EditText editCommentEt;
    /**
     * 服务态度评分
     */
    private RatingBar serviceRb;
    /**
     * 工作质量评分
     */
    private RatingBar constructionQualityRb;
    /**
     * 按时完成评分
     */
    private RatingBar completionDegreeRb;
    /**
     * 提交按钮
     */
    private TextView sumbitTv;
    /**
     * 好评标识
     */
    private int goodIsCheck = 1;
    /**
     * 中评标识
     */
    private int midIsCheck = 0;
    /**
     * 差评标识
     */
    private int lowIsCheck = 0;
    /**
     * 用于存放评价
     */
    private int rebValue = 5, rebValuer1 = 5, rebValue2 = 5;
    /**
     * 被评论人ID
     */
    private String pvId;
    /**
     * 被评论人名称
     */
    private TextView nameTv;
    /**
     * 被评论人实体
     */
    private WorkerListEntry workerListEntry;
    /**
     * 被评论人头像
     */
    private CircleImageView photoIv;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_designer_comment_worker;
    }

    @Override
    public void initView() {
        super.initView();
        workerListEntry = (WorkerListEntry) getArguments().getSerializable("workerListEntry");
        pvId = workerListEntry.getPv_id();
        LogUtils.i("XHX", "priv：" + pvId);
        goodIv = (ImageView) findViewById(R.id.good_iv);
        commentsIv = (ImageView) findViewById(R.id.mid_iv);
        badIv = (ImageView) findViewById(R.id.bad_iv);
        goodTv = (TextView) findViewById(R.id.good_tv);
        commentsTv = (TextView) findViewById(R.id.mid_tv);
        badTv = (TextView) findViewById(R.id.bad_tv);
        toEditTv = (TextView) findViewById(R.id.to_edit_tv);
        toEidtIv = (ImageView) findViewById(R.id.to_edit_iv);
        toEidtLy = (LinearLayout) findViewById(R.id.to_edit_ly);
        editCommentEt = (EditText) findViewById(R.id.comment_et);
        serviceRb = (RatingBar) findViewById(R.id.rb_1);
        constructionQualityRb = (RatingBar) findViewById(R.id.rb_2);
        completionDegreeRb = (RatingBar) findViewById(R.id.rb_3);
        sumbitTv = (TextView) findViewById(R.id.sumbit_tv);
        nameTv = (TextView) findViewById(R.id.name_tv);
        photoIv = (CircleImageView) findViewById(R.id.photo);
        goodIv.setOnClickListener(this);
        commentsIv.setOnClickListener(this);
        badIv.setOnClickListener(this);
        goodTv.setOnClickListener(this);
        commentsTv.setOnClickListener(this);
        badTv.setOnClickListener(this);
        toEditTv.setOnClickListener(this);
        toEidtIv.setOnClickListener(this);
        sumbitTv.setOnClickListener(this);
        serviceRb.setRating(rebValue);
        constructionQualityRb.setRating(rebValue);
        completionDegreeRb.setRating(rebValue);
        nameTv.setText(workerListEntry.getNick_name());
        Glide.with(getActivity()).load(workerListEntry.getPic()).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.worker_photo).into(photoIv);

        serviceRb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Toast.makeText(getContext(),"大小："+ratingBar.getRating(),Toast.LENGTH_SHORT).show();
                rebValue = (int) rating;

            }
        });
        constructionQualityRb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Toast.makeText(getContext(),"大小："+ratingBar.getRating(),Toast.LENGTH_SHORT).show();
                rebValuer1 = (int) rating;
            }
        });
        completionDegreeRb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //Toast.makeText(getContext(),"大小："+ratingBar.getRating(),Toast.LENGTH_SHORT).show();
                rebValue2 = (int) rating;
            }
        });

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("评论");
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setLeftImage(R.mipmap.btn_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.good_iv:
            case R.id.good_tv:
                initChangeComments(0);
                break;
            case R.id.mid_iv:
            case R.id.mid_tv:
                initChangeComments(1);
                break;
            case R.id.bad_iv:
            case R.id.bad_tv:
                initChangeComments(2);
                break;
            case R.id.to_edit_iv:
            case R.id.to_edit_tv:
                toEidtLy.setVisibility(View.GONE);
                editCommentEt.setVisibility(View.VISIBLE);
                break;
            case R.id.sumbit_tv:
                sumbit();
                break;
        }

    }

    /***
     * 修改状态
     *
     * @param index
     */
    public void initChangeComments(int index) {
        //重置所有的状态
        goodIsCheck = 0;
        midIsCheck = 0;
        lowIsCheck = 0;
        goodIv.setImageResource(R.mipmap.good_comment_grey);
        goodTv.setTextColor(getColor(R.color.colorGrey));
        commentsIv.setImageResource(R.mipmap.comment_medium_grey);
        commentsTv.setTextColor(getColor(R.color.colorGrey));
        badIv.setImageResource(R.mipmap.comment_negative_grey);
        badTv.setTextColor(getColor(R.color.colorGrey));
        if (index == 0) {
            goodIsCheck = 1;
            goodIv.setImageResource(R.mipmap.good_comment_g);
            goodTv.setTextColor(getColor(R.color.colorBlue));
        } else if (index == 1) {
            midIsCheck = 1;
            commentsIv.setImageResource(R.mipmap.comment_medium_g);
            commentsTv.setTextColor(getColor(R.color.colorBlue));
        } else {
            lowIsCheck = 1;
            badIv.setImageResource(R.mipmap.comment_negative_g);
            badTv.setTextColor(getColor(R.color.colorBlue));
        }

    }

    /**
     * 提交评价
     */
    public void sumbit() {
        Map<String, String> map = new HashMap<>();
        UserEntry user = Utils.getUserEntry();
        map.put("mxcome_no", user.getMxcome_no());
        map.put("message", editCommentEt.getText().toString());
        map.put("pv_id", pvId);
        map.put("service_attitude", "" + rebValue);
        map.put("work_quality", "" + rebValuer1);
        map.put("hp", "" + goodIsCheck);
        map.put("zp", "" + midIsCheck);
        map.put("cp", "" + lowIsCheck);
        LogUtils.i("XHX", "参数：" + map.toString());
        NetWorkUtils.post(getActivity(), "hrpc/commentMxcomeProvider.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                LogUtils.i("XHX", "成功：" + result);
                try {
                    JSONObject object = new JSONObject(result);
                    //{"return_code":1,"error":null,"res":null}
                    if (object.getInt("return_code") == 1) {
                        CommonToastUtils.showInCenterToast(getContext(), getResources().getString(R.string.submit_succ_txt));
                        getBaseActivity().backStackFragment();
                    } else {
                        CommonToastUtils.showInCenterToast(getContext(), getResources().getString(R.string.submit_fail_txt));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result, String code, String msg) {
                LogUtils.i("XHX", "失败" + result);
                CommonToastUtils.showInCenterToast(getContext(), getResources().getString(R.string.submit_fail_txt));
            }
        });
    }


}
