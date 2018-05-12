package com.fenazola.mxcome.fragment.project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 打评成功
 * Created by xuhuixiang on 2017/6/7.
 */

public class ThanksCommentFragment extends BaseFragment implements View.OnClickListener {



    @Override
    public int getLayoutResId() {
        return R.layout.activity_thanks_comment;
    }

    @Override
    public void initView() {
        super.initView();



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
        toolbar.setTitle("感 谢");
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setLeftShow(false);
//        View view=getLayoutInflater(R.layout.layout_comment_right_top);
//        toolbar.setRightView(view);
        toolbar.setRightImage(R.mipmap.colose_w);
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sumbit_tv:
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
    }

}
