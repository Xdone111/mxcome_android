package com.fenazola.mxcome.fragment.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.WorkerListEntry;
import com.fenazola.mxcome.fragment.me.IdentifyFragment;
import com.fenazola.mxcome.fragment.msg.OtherPersonInfoFragment;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专员评分 客服评分
 * Created by xuhuixiang on 2017/6/7.
 */

public class CommissionerCommentFragment extends BaseFragment implements View.OnClickListener {

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
     * 提交按钮
     */
    private ImageView sumbitTv;

    /**
     * 用于存放评价
     */
    private int rebValue = 5, rebValuer1 = 5;

    /**
     * 被评论人ID
     */
    private String pvId;
    /**
     * 被评论人名称
     */
    private TextView nameTv;
    /**
     * 被评论人头像
     */
    private CircleImageView photoIv;

    GridView cityList;
    private CommonAdapter<String> mAdapter;
    private List<String> names=new ArrayList<String>();
    private List<Boolean> checks=new ArrayList<Boolean>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_designer_comment_zhuanyuan;
    }

    @Override
    public void initView() {
        super.initView();
        LogUtils.i("XHX", "priv：" + pvId);
        setBgColor(R.color.colorBlue);
        editCommentEt = (EditText) findViewById(R.id.comment_et);
        serviceRb = (RatingBar) findViewById(R.id.rb_1);
        constructionQualityRb = (RatingBar) findViewById(R.id.rb_2);
        sumbitTv = (ImageView) findViewById(R.id.image_ok);
        nameTv = (TextView) findViewById(R.id.name_tv);
        photoIv = (CircleImageView) findViewById(R.id.photo);

        sumbitTv.setOnClickListener(this);
        serviceRb.setRating(rebValue);
        constructionQualityRb.setRating(rebValue);
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
        cityList=(GridView)findViewById(R.id.g_list);


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getDate();
        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_item_list_item_tag) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, final int i) {
                TextView city_tv = (TextView) viewHolder.findViewById(R.id.city_list_tv);
                city_tv.setText(workerListEntry);
                if(i<4){
                   if(checks.get(i)){
                       city_tv.setBackgroundResource(R.drawable.rect_shape_green);
                       city_tv.setTextColor(getColor(R.color.white));

                   } else{
                        city_tv.setBackgroundResource(R.drawable.rect_shape_green_bg);
                       city_tv.setTextColor(getColor(R.color.colorGreen));

                   }
                }else if(i<7){
                    if(checks.get(i)){
                        city_tv.setBackgroundResource(R.drawable.rect_shape_red);
                        city_tv.setTextColor(getColor(R.color.white));

                    } else{
                        city_tv.setBackgroundResource(R.drawable.rect_shape_red_bg);
                        city_tv.setTextColor(getColor(R.color.colorRed));

                    }
                }else{
                    city_tv.setBackgroundResource(R.drawable.rect_shape_grey_comment);
                    city_tv.setTextColor(getColor(R.color.colorGrey));


                }

            }
        };
        cityList.setAdapter(mAdapter);
        mAdapter.addAll(names);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checks.set(position,!checks.get(position));
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    private void getDate() {
        names.add("专业");
        names.add("耐心");
        names.add("亲切");
        names.add("礼貌");
        names.add("low");
        names.add("不耐烦");
        names.add("粗鲁");
        names.add("自定义");
        for (int i=0;i<8;i++){
            checks.add(false);
        }
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("专员打评");
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setLeftShow(false);
        View view=getLayoutInflater(R.layout.layout_comment_right_top);
        toolbar.setRightView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_ok:
                sumbit();
                break;
        }

    }



    /**
     * 提交评价
     */
    public void sumbit() {
//        Map<String, String> map = new HashMap<>();
//        UserEntry user = Utils.getUserEntry();
//        map.put("mxcome_no", user.getMxcome_no());
//        map.put("message", editCommentEt.getText().toString());
//        map.put("pv_id", pvId);
//        map.put("service_attitude", "" + rebValue);
//        map.put("work_quality", "" + rebValuer1);
//       map.put("hp", "" + goodIsCheck);
//        map.put("zp", "" + midIsCheck);
//        map.put("cp", "" + lowIsCheck);
//        LogUtils.i("XHX", "参数：" + map.toString());
//        NetWorkUtils.post(getActivity(), "hrpc/commentMxcomeProvider.do", map, new NetWorkUtils.IListener() {
//            @Override
//            public void onSuccess(String result, JSONObject resObj) {
//                LogUtils.i("XHX", "成功：" + result);
//                try {
//                    JSONObject object = new JSONObject(result);
//                    //{"return_code":1,"error":null,"res":null}
//                    if (object.getInt("return_code") == 1) {
//                        CommonToastUtils.showInCenterToast(getContext(), getResources().getString(R.string.submit_succ_txt));
//                        getBaseActivity().backStackFragment();
//                    } else {
//                        CommonToastUtils.showInCenterToast(getContext(), getResources().getString(R.string.submit_fail_txt));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(String result, String code, String msg) {
//                LogUtils.i("XHX", "失败" + result);
//                CommonToastUtils.showInCenterToast(getContext(), getResources().getString(R.string.submit_fail_txt));
//            }
//        });

        Intent intent = new Intent(getActivity(), ZFrameActivity.class);
        intent.putExtra(ZFrameActivity.CLASS_NAME, ThanksCommentFragment.class.getName());
        startActivity(intent);
    }

}
