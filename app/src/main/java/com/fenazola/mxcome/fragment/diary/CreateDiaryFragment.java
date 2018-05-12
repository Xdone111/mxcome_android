package com.fenazola.mxcome.fragment.diary;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.DiaryPhotoEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.Utils;
import com.zss.library.PermissionCallBack;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
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
 * Created by XDONE on 2017/4/4.
 * 写日记
 */

public class CreateDiaryFragment extends BaseFragment implements View.OnClickListener {

    private EditText diaryName;
    private EditText diaryContent;
    private LinearLayout linear1, linear2, linear3, linear4;
    private TextView stage;
    private EditText title;
    private TextView dateTime;
    private GridView gridView;
    private CommonAdapter<String> adapter;
    private List<DiaryPhotoEntry> datas;
    private ArrayList<String> photos;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_create_diary;
    }

    @Override
    public void initView() {
        super.initView();
        diaryName = (EditText) findViewById(R.id.diary_name);
        diaryContent = (EditText) findViewById(R.id.diary_content);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        linear4 = (LinearLayout) findViewById(R.id.linear4);
        stage = (TextView) findViewById(R.id.stage);
        title = (EditText) findViewById(R.id.title);
        dateTime = (TextView) findViewById(R.id.date_time);
        gridView = (GridView) findViewById(R.id.gridView);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        dateTime.setOnClickListener(this);
        linear1.setOnClickListener(this);
        stage.setOnClickListener(this);

        adapter = new CommonAdapter<String>(getActivity(), R.layout.griditem_project_image) {
            @Override
            protected void convert(ViewHolder viewHolder, final String photoUrl, int i) {
                ImageView imageView =viewHolder. findViewById(R.id.imageView);
                Glide.with(getActivity()).load(photoUrl).error(R.mipmap.me_photo).into(imageView);
            }
        };
        gridView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_time:
                Utils.showDateDialog(getActivity(), dateTime, null);
                break;
            case R.id.linear1:
                openPhotoPicker();
                break;
            case R.id.stage:
                DecorateStepFragment fragment = new DecorateStepFragment();
                addFragment(fragment);
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
                        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, 9);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
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
                    for (String fileUrl : results) {
                        File file = new File(FileUtils.getExtDir(getActivity()) + Constant.imageDir);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        String targetPath = file.getPath() + File.separator + "image_" + System.currentTimeMillis() + ".jpg";
                        photos = new ArrayList<>();
                        photos.add(fileUrl);
                        adapter.addAll(photos);
                    }
                }
            }
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("写日记");
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setTitleColor(getColor(R.color.black));
    }
}
