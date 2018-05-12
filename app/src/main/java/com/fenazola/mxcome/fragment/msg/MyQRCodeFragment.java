package com.fenazola.mxcome.fragment.msg;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
 * Created by XDONE on 2017/3/16.
 */

public class MyQRCodeFragment extends BaseFragment {

    private ImageView photo;
    private ImageView qrcode;
    private TextView name;
    private TextView mxcome_no;
    private TextView verify;
    private TextView pcm;
    private View send_friends;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_qrcode;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.topic_text_color1);
        photo = (ImageView) findViewById(R.id.photo);
        qrcode = (ImageView) findViewById(R.id.qrcode);
        name = (TextView) findViewById(R.id.name);
        mxcome_no = (TextView) findViewById(R.id.mxcome_no);
        verify = (TextView) findViewById(R.id.verify);
        pcm = (TextView) findViewById(R.id.pcm);
        send_friends = findViewById(R.id.send_friends);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        UserEntry user = Utils.getUserEntry();
        try {
            JSONObject json = new JSONObject();
            json.put("cid", PushManager.getInstance().getClientid(getActivity()));
            json.put("name", user.getUser_name());
            json.put("mxcome", user.getMxcome_no());
            json.put("pic", user.getPic());
            LogUtils.i("------- json --------", json.toString());
            String qrCode = EncryptUtil.AesEncrypt(json.toString());
            qrcode.setImageBitmap(QRCodeUtils.createQRImage(qrCode, DPUtils.dip2px(getActivity(), 150), DPUtils.dip2px(getActivity(), 150)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        send_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Glide.with(getActivity()).load(Constant.baseUrl + user.getPic()).error(R.mipmap.me_photo).into(photo);

        name.setText(user.getUser_name());
        mxcome_no.setText("账号："+user.getMxcome_no());
        pcm.setText("中国 "+user.getArea_id());

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("我的二维码名片");
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setRightShow(true);
        toolbar.setRightImage(R.mipmap.me_details);
    }

}
