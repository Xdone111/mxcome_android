package com.fenazola.mxcome.fragment.me;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.ClipImageActivity;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.fragment.msg.MsgVisitorFragment;
import com.fenazola.mxcome.fragment.msg.MyQRCodeFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.ShareUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.PermissionCallBack;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.photopicker.PhotoPickerActivity;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.FileUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.PicUtils;
import com.zss.library.widget.CommonDialog;
import com.zss.library.widget.CommonEditWidget;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by XDONE on 2017/3/25.
 * 个人信息
 */

public class PersonInfoFragment extends BaseFragment implements View.OnClickListener {

    private TextView nikeName;
    private LinearLayout edite1, edite2, edite3, edite4;
    private TextView userSign;
    private TextView userSex;
    private CircleImageView photo;
    private RadioGroup radioSex;
    private CommonEditWidget nickEdit;
    private CommonEditWidget signEdit;
    private TextView nike;
    private LinearLayout images;
    private ImageView editNick;
    private TextView myId;
    private TextView tv_visitor,tv_Number;

    @Override

    public int getLayoutResId() {
        return R.layout.fragment_me_personinfo;
    }

    @Override
    public void initView() {
        super.initView();
        editNick = (ImageView) findViewById(R.id.edit_nick);
        nikeName = (TextView) findViewById(R.id.nikeName);
        userSign = (TextView) findViewById(R.id.userSign);
        userSex = (TextView) findViewById(R.id.sex);
        edite1 = (LinearLayout) findViewById(R.id.ll_edite1);
        edite2 = (LinearLayout) findViewById(R.id.ll_edite2);
        edite3 = (LinearLayout) findViewById(R.id.ll_edite3);
        edite4 = (LinearLayout) findViewById(R.id.ll_edite4);
        photo = (CircleImageView) findViewById(R.id.photo);
        nike = (TextView) findViewById(R.id.nike);
        images = (LinearLayout) findViewById(R.id.images);
        myId = (TextView) findViewById(R.id.my_id);
        tv_visitor = (TextView) findViewById(R.id.tv_visitor);
        tv_Number = (TextView) findViewById(R.id.visitor_num);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        edite1.setOnClickListener(this);
        edite2.setOnClickListener(this);
        edite3.setOnClickListener(this);
        edite4.setOnClickListener(this);
        images.setOnClickListener(this);
        editNick.setOnClickListener(this);
        tv_visitor.setOnClickListener(this);
        tv_Number.setOnClickListener(this);

        UserEntry user = Utils.getUserEntry();
        nikeName.setText(user.getUser_name());
        userSign.setText(user.getUserSign());
        userSex.setText(user.getSex());
        nike.setText(user.getUser_name());

        if (!TextUtils.isEmpty(user.getPic())) {
            Glide.with(getActivity()).load(Constant.baseUrl + user.getPic()).error(R.mipmap.me_photo).into(photo);
        } else {
            photo.setImageResource(R.mipmap.me_photo);
        }
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoPicker();
            }
        });
        if (!TextUtils.isEmpty(Utils.getUserEntry().getMxcome_no())) {
            myId.setText("账号:" + Utils.getUserEntry().getMxcome_no());
        }
    }

    @Override
    public void onClick(View v) {
        final CommonDialog dialog;
        switch (v.getId()) {
            case R.id.edit_nick:
                dialog = new CommonDialog(getActivity());
                dialog.setTitle(getString(R.string.change_nike));
                final View view1 = getLayoutInflater(R.layout.dialog_edit);
                nickEdit = (CommonEditWidget) view1.findViewById(R.id.edit);
                nickEdit.getEditText().setTextColor(getColor(R.color.white));
                nickEdit.setText(nikeName.getText());
                dialog.setMiddleView(view1);
                dialog.setOnClickConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reqData("", "", nickEdit.getText(), "");
                        nikeName.setText(nickEdit.getText());
                        nike.setText(nickEdit.getText());

                    }
                });
                dialog.show();
                break;
            case R.id.ll_edite2:
                dialog = new CommonDialog(getActivity());
                dialog.setTitle(getString(R.string.change_sex));
                final View view2 = getLayoutInflater(R.layout.dialog_sex);
                radioSex = (RadioGroup) view2.findViewById(R.id.radioGroup);
                if (getString(R.string.man).equals(userSex.getText())) {
                    radioSex.check(R.id.man);
                } else {
                    radioSex.check(R.id.woman);
                }
                dialog.setMiddleView(view2);
                dialog.setOnClickConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radioSex = (RadioGroup) view2.findViewById(R.id.radioGroup);
                        if (radioSex.getCheckedRadioButtonId() == R.id.man) {
                            reqData(getString(R.string.man), "", "", "");
                            userSex.setText(getString(R.string.man));
                        } else {
                            reqData(getString(R.string.woman), "", "", "");
                            userSex.setText(getString(R.string.woman));
                        }
                    }
                });
                dialog.show();
                break;
            case R.id.ll_edite3:
                dialog = new CommonDialog(getActivity());
                dialog.setTitle(getString(R.string.my_sign));
                final View view3 = getLayoutInflater(R.layout.dialog_edit);
                signEdit = (CommonEditWidget) view3.findViewById(R.id.edit);
                signEdit.getEditText().setTextColor(getColor(R.color.white));
                signEdit.setText(userSign.getText());
                dialog.setMiddleView(view3);
                dialog.setOnClickConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reqData("", signEdit.getText(), "", "");
                        userSign.setText(signEdit.getText());
                    }
                });
                dialog.show();
                break;
            case R.id.ll_edite4:
                break;

            case R.id.images:
                break;
            case R.id.right_iv_1:
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, MyQRCodeFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.right_iv_2:
                ShareUtils.showShareDialog(getActivity(), "分享了", "http://www.baidu.com","百度","我刚刚在这里领取到了免费装修，棒棒哒！");

                break;
            case R.id.tv_visitor:
            case R.id.visitor_num:
                MsgVisitorFragment fragment = new MsgVisitorFragment();
                addFragment(fragment);
                break;
        }
    }

    public void reqData(final String sex, final String userSign, final String nikeName, final String url) {
        HashMap<String, String> map = new HashMap<>();
        final UserEntry user = Utils.getUserEntry();
        String timestamp = "" + System.currentTimeMillis();
        String sign = EncryptUtil.AesEncrypt(new String(user.getUser_id() + timestamp));
        map.put("user_id", user.getUser_id());
        map.put("timestamp", timestamp);
        map.put("sex", sex);
        map.put("userSign", userSign);
        map.put("sign", sign);
        map.put("nick_name", nikeName);
        map.put("pic", url);
        NetWorkUtils.post(getActivity(), "appApi/updatePerInfoOnUser.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                UserEntry entry = Utils.getUserEntry();
                if (!TextUtils.isEmpty(sex)) {
                    entry.setSex(sex);
                }
                if (!TextUtils.isEmpty(userSign)) {
                    entry.setUserSign(userSign);
                }
                if (!TextUtils.isEmpty(nikeName)) {
                    entry.setUser_name(nikeName);
                }
                if (!TextUtils.isEmpty(url)) {
                    entry.setPic(url);
                }

                Utils.saveUserEntry(entry);
                EventBus.getDefault().post(user);

            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle(getString(R.string.toolbar_info));
        View view = getLayoutInflater(R.layout.layout_tab_main_right_double_iv);
        ImageView collect = (ImageView) view.findViewById(R.id.right_iv_1);
        ImageView share = (ImageView) view.findViewById(R.id.right_iv_2);
        collect.setOnClickListener(this);
        share.setOnClickListener(this);
        toolbar.setRightView(view);
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
                    doUploadImage(fileUrl);
                }
            }
        } else if (resultCode == getActivity().RESULT_OK && requestCode == 2) {
            Uri uri = data.getData();
            if (uri == null) {
                return;
            }
            String cropImagePath = getRealFilePathFromUri(getActivity(), uri);
            Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
            photo.setImageBitmap(bitMap);

            Utils.uploadImage(getActivity(), cropImagePath, new Utils.UrlCallback() {
                @Override
                public void onSuccess(String url) {
                    Glide.with(getActivity()).load(Constant.baseUrl + url).into(photo);
                    reqData("", "", "", url);
                }

                @Override
                public void onError() {

                }
            });
        }
    }

    public void doUploadImage(String fileUrl) {
        File file = new File(FileUtils.getExtDir(getActivity()) + Constant.imageDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPath = file.getPath() + File.separator + "temp.jpg";
        fileUrl = PicUtils.compressImage(fileUrl, targetPath, 30);
        gotoClipActivity(Uri.parse(fileUrl));
    }

    /**
     * 打开截图界面
     *
     * @param uri
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), ClipImageActivity.class);
        intent.putExtra("type", ClipImageActivity.ClipType.CORNER);
        intent.setData(uri);
        startActivityForResult(intent, 2);
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
