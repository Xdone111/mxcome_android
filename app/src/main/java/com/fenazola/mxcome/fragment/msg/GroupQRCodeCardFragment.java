package com.fenazola.mxcome.fragment.msg;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.iframe.zxing.encode.QRCodeUtils;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
import com.igexin.sdk.PushManager;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xuhuixiang on 2017/3/16.
 * 群二维码
 */

public class GroupQRCodeCardFragment extends BaseFragment implements View.OnClickListener{

    private ImageView photo;
    private ImageView qrcode;
    private TextView name;
    private TextView mxcome_no;
    private TextView numberTv,numberSumTv,levelTv,cityTv,groupInfoTv,groupSpackTv,adminTv,scendAdminTv;
    private LinearLayout sendLy,joinGroupLy;
    private Bitmap qrBitmap;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_group_msg_qrcode;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.colorBlue);
        photo = (ImageView) findViewById(R.id.photo);
        qrcode = (ImageView) findViewById(R.id.qrcode);
        name = (TextView) findViewById(R.id.group_name_tv);
        mxcome_no = (TextView) findViewById(R.id.group_mxcome_tv);
        numberTv=(TextView)findViewById(R.id.group_number_tv);
        numberSumTv=(TextView)findViewById(R.id.group_sum_number_tv);
        levelTv=(TextView)findViewById(R.id.group_level_tv);
        cityTv=(TextView)findViewById(R.id.group_city_tv);
        groupInfoTv=(TextView)findViewById(R.id.group_info);
        groupSpackTv=(TextView)findViewById(R.id.group_speack_tv);
        adminTv=(TextView)findViewById(R.id.admin_tv);
        scendAdminTv=(TextView)findViewById(R.id.scend_admin_tv);
        sendLy=(LinearLayout)findViewById(R.id.show_send_card);
        joinGroupLy=(LinearLayout)findViewById(R.id.show_in_group);
        sendLy.setOnClickListener(this);
        joinGroupLy.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        qrBitmap=QRCodeUtils.createQRImage("100888", DPUtils.dip2px(getActivity(), 150), DPUtils.dip2px(getActivity(), 150));
        qrcode.setImageBitmap(qrBitmap);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("群二维码名片");
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setRightShow(true);
        toolbar.setRightImage(R.mipmap.me_details);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_send_card:
                break;
            case R.id.show_in_group:
                break;
        }
    }
}
