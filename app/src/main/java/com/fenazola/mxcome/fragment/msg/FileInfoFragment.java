package com.fenazola.mxcome.fragment.msg;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.CouponEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.swipelayout.SwipeRevealLayout;
import com.zss.library.swipelayout.ViewBinderHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券
 * Created by xhx on 2017/6/17.
 */

public class FileInfoFragment extends BaseFragment {


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_coupon;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

    }



}
