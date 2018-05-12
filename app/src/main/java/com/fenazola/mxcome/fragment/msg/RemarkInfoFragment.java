package com.fenazola.mxcome.fragment.msg;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.PermissionCallBack;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.photopicker.PhotoPickerActivity;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.FileUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.PicUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/8/1.
 */

public class RemarkInfoFragment extends BaseFragment {

    private TextView see_image;
    private ImageView small_image;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_remark_info;
    }

    @Override
    public void initView() {
        super.initView();
        see_image = (TextView) findViewById(R.id.see_image_tv);
        small_image = (ImageView) findViewById(R.id.small_image);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        see_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoPicker();
            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setTitle("备注信息");
    }

    public void openPhotoPicker() {
        verifyPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}
                , 0x01, new PermissionCallBack() {

                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
                        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, 9);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
                        startActivityForResult(intent, 0x01);
                    }

                    @Override
                    public void onDenied() {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (data.getExtras() != null && data.getExtras().containsKey(PhotoPickerActivity.KEY_RESULT)) {
                List<String> results = (List<String>) data.getExtras().get(PhotoPickerActivity.KEY_RESULT);
                if (results != null && results.size() > 0) {
                    for (final String fileUrl : results) {
                        File file = new File(FileUtils.getExtDir(getActivity()) + Constant.imageDir);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        // String targetPath = file.getPath() + File.separator + "image_" + System.currentTimeMillis() + ".jpg";
//                        final Bitmap bm = BitmapFactory.decodeFile(fileUrl);
//                        small_image.setImageBitmap(bm);

                        see_image.setVisibility(View.GONE);
                        small_image.setVisibility(View.VISIBLE);
                        Glide.with(getActivity()).load(fileUrl).error(R.mipmap.module_bookroom).into(small_image);
                        small_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                                intent.putExtra(ZFrameActivity.CLASS_NAME, SeeImageFragment.class.getName());
                                intent.putExtra(Constant.key1, fileUrl);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        }
    }

}
