package com.fenazola.mxcome.fragment.sercentre;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fenazola.iframe.zxing.encode.QRCodeUtils;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.fragment.me.LoginFragment;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
import com.igexin.sdk.PushManager;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.R.attr.path;

/**
 * Created by XDONE on 2017/4/24.
 * 微信服务
 */

public class WeChatServerFragment extends BaseFragment {
    private ImageView image;
    private Button save;
    private Bitmap qrImage;
    private String fileName;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_wechat_server;
    }

    @Override
    public void initView() {
        super.initView();
        image = (ImageView) findViewById(R.id.image);
        save = (Button) findViewById(R.id.save);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (Utils.getUserEntry() == null) {
        } else {
            UserEntry user = Utils.getUserEntry();
            try {
                JSONObject json = new JSONObject();
                json.put("cid", PushManager.getInstance().getClientid(getActivity()));
                json.put("name", user.getUser_name());
                json.put("mxcome", user.getMxcome_no());
                json.put("pic", user.getPic());
                LogUtils.i("------- json --------", json.toString());
                String qrcode = EncryptUtil.AesEncrypt(json.toString());
                //生成二维码
                qrImage = QRCodeUtils.createQRImage(qrcode, DPUtils.dip2px(getActivity(), 120), DPUtils.dip2px(getActivity(), 120));
                image.setImageBitmap(qrImage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveImageToGallery(getActivity(), qrImage);
                }
            });
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

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setTitle("开通服务号");
        toolbar.setTitleColor(getColor(R.color.black));
        toolbar.setLeftImage(getResources().getDrawable(R.mipmap.icon_grey_back));
    }
}
