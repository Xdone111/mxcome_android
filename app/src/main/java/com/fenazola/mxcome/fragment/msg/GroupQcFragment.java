package com.fenazola.mxcome.fragment.msg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenazola.iframe.zxing.encode.QRCodeUtils;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.ShareUtils;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.DPUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.path;

/**
 * Created by xuhuixiang on 2017/8/1.
 * 群二维码
 */

public class GroupQcFragment extends BaseFragment implements View.OnClickListener{
    private ImageView qcIv;
    private ImageView groupIv;
    private TextView titleTv;
    private RelativeLayout showMenu;
    private TextView shareTv;
    private TextView saveTv;
    private String fileName;
    private Bitmap qrImage;
    CommonToolbar toolbar;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_group_qc;
    }

    @Override
    public void initView() {
        super.initView();
        qcIv=(ImageView)findViewById(R.id.qc_iv);
        groupIv=(ImageView)findViewById(R.id.show_group_iv);
        titleTv=(TextView)findViewById(R.id.show_title);
        showMenu=(RelativeLayout)findViewById(R.id.show_menu);
        shareTv=(TextView)findViewById(R.id.share_tv);
        saveTv=(TextView)findViewById(R.id.save_tv);
        showMenu.setOnClickListener(this);
        shareTv.setOnClickListener(this);
        saveTv.setOnClickListener(this);


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        qrImage=QRCodeUtils.createQRImage("徐辉祥好帅徐辉祥好帅徐辉祥好帅徐辉祥好帅", DPUtils.dip2px(getActivity(), 300), DPUtils.dip2px(getActivity(), 300));
        qcIv.setImageBitmap(qrImage);



    }


    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);
        toolbar = getToolbar();
        toolbar.setTitle("群二维码");
        toolbar.setTitleColor(getColor(R.color.black));
        toolbar.setRightImage(R.mipmap.group_info_gray);
        //toolbar.setRightTextColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu.setVisibility(View.VISIBLE);
                toolbar.setBgColor(getColor(R.color.title_color11));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_menu:
                showMenu.setVisibility(View.GONE);
                toolbar.setBgColor(getColor(R.color.white));

                break;
            case R.id.share_tv:
                ShareUtils.showShareDialog(getActivity(), "分享了", "http://www.baidu.com","随便写的分享","我刚刚在这里领取到了免费装修，棒棒哒！");
                showMenu.setVisibility(View.GONE);

                break;
            case R.id.save_tv:
                saveImageToGallery(getActivity(),qrImage);
                showMenu.setVisibility(View.GONE);

                break;
        }
    }
    /**
     *
     * @param context
     * @param bmp
     * 保存图片
     */
    public void saveImageToGallery(Context context, Bitmap bmp) {
        if (!TextUtils.isEmpty(fileName)) {
            CommonToastUtils.showInCenterToast(getActivity(), "图片已存在");
            return;
        }
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        CommonToastUtils.showInCenterToast(getActivity(), "保存成功");
    }


}
