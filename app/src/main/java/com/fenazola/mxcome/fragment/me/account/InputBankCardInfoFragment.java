package com.fenazola.mxcome.fragment.me.account;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.mepay.AccountProblemFragment;
import com.fenazola.mxcome.fragment.me.mepay.MyPayDialog;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.Utils;
import com.zss.library.PermissionCallBack;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.photopicker.PhotoPickerActivity;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import java.util.List;

/**
 * Created by xuhuixiang on 2017/7/28.
 */

public class InputBankCardInfoFragment extends BaseFragment implements View.OnClickListener{
    ImageView iv1,iv2;
    TextView nextTv;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_input_bank_card_info;
    }

    @Override
    public void initView() {
        super.initView();
        iv1=(ImageView)findViewById(R.id.iv_1);
        iv2=(ImageView)findViewById(R.id.iv_2);
        nextTv=(TextView)findViewById(R.id.show_next);
        nextTv.setOnClickListener(this);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
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
        toolbar.setTitle("填写银行卡信息");
        toolbar.setBgColor(getResources().getColor(R.color.colorBlue));
        toolbar.setTitleColor(Color.WHITE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_1:
                MyPayDialog dialog = new MyPayDialog(getActivity(),"为了您的资金安全，只能绑定实名用户的银行卡，如需解绑，请更换实名信息。",
                        "","持卡人说明");
                dialog.show();
                break;
            case R.id.iv_2:
                openPhotoPicker();
                break;
            case R.id.show_next:
                Intent intent= new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, AccountProblemFragment.class.getName());
                startActivity(intent);
                break;
        }
    }
    public void openPhotoPicker() {
        verifyPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}
                , 0x01, new PermissionCallBack() {

                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
                        startActivityForResult(intent, 1);
                    }

                    @Override
                    public void onDenied() {
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i("onActivityResult", "---requestCode = " + requestCode + "---resultCode = " + resultCode);
        if (resultCode == getActivity().RESULT_OK && requestCode == 1) {
            if (data.getExtras() != null && data.getExtras().containsKey(PhotoPickerActivity.KEY_RESULT)) {
                List<String> results = (List<String>) data.getExtras().get(PhotoPickerActivity.KEY_RESULT);
                LogUtils.i("onActivityResult", "---result = " + results);
                if (results != null && results.size() > 0) {
                    String fileUrl = results.get(0);
                    //doUploadImage(fileUrl);
                }
            }
        }
    }

}
