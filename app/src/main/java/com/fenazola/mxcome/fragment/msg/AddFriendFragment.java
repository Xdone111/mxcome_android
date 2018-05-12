package com.fenazola.mxcome.fragment.msg;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.fenazola.iframe.zxing.CaptureActivity;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.TableTalk;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
import com.zss.library.PermissionCallBack;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.widget.CommonTextWidget;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by XDONE on 2017/3/15.
 * 添加好友
 */

public class AddFriendFragment extends BaseFragment implements View.OnClickListener {

    private EditText editText;
    private CommonTextWidget widget1;
    private CommonTextWidget widget2;
    private CommonTextWidget widget3;
    private CommonTextWidget widget4;
    private CommonTextWidget widget5;
    private CommonTextWidget widget6;
    private CommonTextWidget widget7;
    private CommonTextWidget widget8;
    private RelativeLayout widget9;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_add_friend;
    }

    @Override
    public void initView() {
        super.initView();
        editText = (EditText) findViewById(R.id.editText);
        widget1 = (CommonTextWidget) findViewById(R.id.widget1);
        widget2 = (CommonTextWidget) findViewById(R.id.widget2);
        widget3 = (CommonTextWidget) findViewById(R.id.widget3);
        widget4 = (CommonTextWidget) findViewById(R.id.widget4);
        widget5 = (CommonTextWidget) findViewById(R.id.widget5);
        widget6 = (CommonTextWidget) findViewById(R.id.widget6);
        widget7 = (CommonTextWidget) findViewById(R.id.widget7);
        widget8 = (CommonTextWidget) findViewById(R.id.widget8);
        widget9=(RelativeLayout)findViewById(R.id.widget9);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        widget1.setOnClickListener(this);
        widget2.setOnClickListener(this);
        widget3.setOnClickListener(this);
        widget4.setOnClickListener(this);
        widget5.setOnClickListener(this);
        widget6.setOnClickListener(this);
        widget7.setOnClickListener(this);
        widget8.setOnClickListener(this);
        widget9.setOnClickListener(this);

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("添加好友");
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.widget1:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, MyQRCodeFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.widget3:
                verifyPermissions(getActivity(), Manifest.permission.CAMERA,
                        new String[]{Manifest.permission.CAMERA}
                        , 0x01, new PermissionCallBack() {
                            @Override
                            public void onGranted() {
                                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                                startActivityForResult(intent, 0x01);
                            }
                            @Override
                            public void onDenied() {
                            }
                        });
                break;
            case R.id.widget4:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, AcquaintanceFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.widget5:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, NewFriendFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.widget7:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FriendListFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.widget9:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FriendDpecialAttentionFragment.class.getName());
                startActivity(intent);

                break;

            case R.id.widget6:

                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FriendGroupListFragment.class.getName());
                startActivity(intent);

                break;
            case R.id.widget2:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, AddFriendToGroupFragment.class.getName());
                Bundle bundle0=new Bundle();
                bundle0.putInt(Constant.key1,1);
                intent.putExtras(bundle0);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x01 && resultCode == Activity.RESULT_OK){
            String result = data.getStringExtra("result");
            String qrCode = EncryptUtil.AesDecrypt(result);
            try {
                JSONObject json = new JSONObject(qrCode);
                String cid = json.optString("cid");
                String name = json.optString("name");
                String mxcome = json.optString("mxcome");
                String pic = json.optString("pic");
                TableTalk talk = Utils.createTalk(getActivity(), cid, name, mxcome, pic);
                if(talk != null){
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(Constant.key1, talk);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, ChatRecyclerFragment.class.getName());
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
