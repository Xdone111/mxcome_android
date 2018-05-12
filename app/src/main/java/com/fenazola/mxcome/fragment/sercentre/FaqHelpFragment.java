package com.fenazola.mxcome.fragment.sercentre;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * 使用帮助
 * @author xhx
 */
public class FaqHelpFragment extends BaseFragment{
    //TODO 设置电话地址
    private String telString="10086";
    @Override
    public int getLayoutResId() {
        return R.layout.activity_faq_help;
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        findViewById(R.id.rengong_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getBaseActivity(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    // 没有获得授权，申请授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getBaseActivity(),
                            Manifest.permission.CALL_PHONE)) {
                        // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                        // 弹窗需要解释为何需要该权限，再次请求授权
                        Toast.makeText(getBaseActivity(), "请授权！", Toast.LENGTH_LONG).show();

                        // 帮跳转到该应用的设置界面，让用户手动授权
                        Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent1.setData(uri);
                        startActivity(intent1);
                    }else{
                        // 不需要解释为何需要该权限，直接请求授权
                        ActivityCompat.requestPermissions(getBaseActivity(),
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);
                    }
                }else {
                    // 已经获得授权，可以打电话
                    CallPhone();
                }
            }
        });

    }
    private void CallPhone() {
        Intent intent = new Intent(); // 意图对象：动作 + 数据
        intent.setAction(Intent.ACTION_CALL); // 设置动作
        Uri data = Uri.parse("tel:" + telString); // 设置数据
        intent.setData(data);
        startActivity(intent); // 激活Activity组件
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("使用帮助");
        toolbar.setBgColor(getResources().getColor(R.color.colorBlue));
        toolbar.setTitleColor(Color.WHITE);
       // toolbar.setLeftImage(getResources().getDrawable(R.mipmap.project_left));
        toolbar.setRightImage(R.mipmap.colose_w);
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });
    }
    // 处理权限申请的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                    CallPhone();
                } else {
                    // 授权失败！
                    Toast.makeText(getBaseActivity(), "授权失败！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }

    }
}
