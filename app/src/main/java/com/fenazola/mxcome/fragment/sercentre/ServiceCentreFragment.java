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
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.main.demand.DemandOrder;
import com.fenazola.mxcome.fragment.msg.ChatOnLineFragment;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * 服务中心
 * @author xhx
 */
public class ServiceCentreFragment extends BaseFragment implements View.OnClickListener{
    //TODO 设置电话地址
    private String telString="10086";
    /**在线服务 */
    private LinearLayout onLineLy;
    /** 上门服务*/
    private LinearLayout upDoorLy;
    /** 电话服务*/
    private LinearLayout telLy;
    /**服务条款 */
    private LinearLayout termsLy;
    /**使用手册 */
    private LinearLayout manualLy;
    /**常见问题 */
    private LinearLayout faqLy;
    /**角色体验 */
    private LinearLayout roleExperienceLy;
    /**开通服务号 */
    private LinearLayout wechatLy;

   //
    private FrameLayout doorFy;
    private LinearLayout doorLeftLy,doorRightLy;
    private TextView doorLeftTv,doorRightTv;
    private FrameLayout telFy;
    private TextView telLeftTv,telRightTv;
    private ImageView backIv;
    private TextView titleTv;
    private ImageView bg;
    private ImageView telBg;
    int width;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_service_centre;
    }

    @Override
    public void initView() {
        super.initView();
        WindowManager wm = getActivity().getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        onLineLy=(LinearLayout)findViewById(R.id.online_ly);
        upDoorLy=(LinearLayout)findViewById(R.id.up_door_ly);
        telLy=(LinearLayout)findViewById(R.id.tel_ser_ly);
        termsLy=(LinearLayout)findViewById(R.id.terms_of_service_ly);
        manualLy=(LinearLayout)findViewById(R.id.manual_ly);
        faqLy=(LinearLayout)findViewById(R.id.faq_ly);
        roleExperienceLy=(LinearLayout)findViewById(R.id.role_experience_ly);
        wechatLy=(LinearLayout)findViewById(R.id.open_wechat_ly);
        doorFy=(FrameLayout)findViewById(R.id.fl_content_2);
        doorLeftLy=(LinearLayout)findViewById(R.id.show_left_door_ly);
        doorRightLy=(LinearLayout)findViewById(R.id.show_right_door_ly);
        doorLeftTv=(TextView) findViewById(R.id.show_left_door_tv);
        doorRightTv=(TextView) findViewById(R.id.show_right_door_tv);

        telFy=(FrameLayout)findViewById(R.id.fl_content_3);
        telLeftTv=(TextView) findViewById(R.id.tel_no);
        telRightTv=(TextView) findViewById(R.id.tel_now);
        backIv=(ImageView)findViewById(R.id.show_title_left);
        titleTv=(TextView)findViewById(R.id.show_title_tv);
        bg=(ImageView)findViewById(R.id.show_bg);
        telBg=(ImageView)findViewById(R.id.show_tel_bg) ;
        onLineLy.setOnClickListener(this);
        upDoorLy.setOnClickListener(this);
        telLy.setOnClickListener(this);
        termsLy.setOnClickListener(this);
        manualLy.setOnClickListener(this);
        faqLy.setOnClickListener(this);
        roleExperienceLy.setOnClickListener(this);
        wechatLy.setOnClickListener(this);
        doorLeftLy.setOnClickListener(this);
        doorRightLy.setOnClickListener(this);
        doorFy.setOnClickListener(this);
        telFy.setOnClickListener(this);
        telLeftTv.setOnClickListener(this);
        telRightTv.setOnClickListener(this);

        titleTv.setText("服务中心");
        titleTv.setTextColor(getColor(R.color.white));
        backIv.setOnClickListener(this);
        doorLeftTv.setText(Html.fromHtml("展开<font color='#4BB7FD'>净网行动</font>"));

    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        changeItemH(bg);
       changeTelH();

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.online_ly:
                //TODO 在线服务 先不整

                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, ChatOnLineFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.up_door_ly:
                doorFy.setVisibility(View.VISIBLE);

                break;
            case R.id.tel_ser_ly:
                telFy.setVisibility(View.VISIBLE);

                break;
            case R.id.terms_of_service_ly:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqTermsFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.manual_ly:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, ManualFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.faq_ly:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.role_experience_ly:
                DemandRoleExperimentation dialog5 = new DemandRoleExperimentation(getActivity());
                dialog5.show();

                break;
            case R.id.open_wechat_ly:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, WeChatServerFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.fl_content_2:
                doorFy.setVisibility(View.GONE);
                break;
            case R.id.fl_content_3:
                telFy.setVisibility(View.GONE);
                break;
            case R.id.tel_no:
                telFy.setVisibility(View.GONE);
                break;
            case R.id.tel_now:
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

                break;
            case R.id.show_left_door_ly:
                doorFy.setVisibility(View.GONE);
                break;
            case R.id.show_right_door_ly:
                doorFy.setVisibility(View.GONE);
                break;
            case R.id.show_title_left:
                getActivity().onBackPressed();
                break;

        }
    }
    private void changeItemH(ImageView iv){
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) iv.getLayoutParams();
        linearParams.height = (int)((float)width*0.67);
        iv.setLayoutParams(linearParams);
    }
    private void changeTelH(){
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) telBg.getLayoutParams();
        linearParams.height = (int)((float)(width-40)*0.60);
        telBg.setLayoutParams(linearParams);
    }
    private void CallPhone() {
       Intent intent = new Intent(); // 意图对象：动作 + 数据
        intent.setAction(Intent.ACTION_CALL); // 设置动作
        Uri data = Uri.parse("tel:" + telString); // 设置数据
        intent.setData(data);
        startActivity(intent); // 激活Activity组件
        telFy.setVisibility(View.GONE);
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.GONE);
        toolbar.setTitle("服务中心");
        toolbar.setBgColor(Color.TRANSPARENT);
        toolbar.setTitleColor(Color.WHITE);
        toolbar.setLeftImage(getResources().getDrawable(R.mipmap.project_left));
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

    @Override
    public boolean onBackPressed() {
        if(doorFy.getVisibility()==View.VISIBLE||telFy.getVisibility()==View.VISIBLE){
            doorFy.setVisibility(View.GONE);
            telFy.setVisibility(View.GONE);
            return true;
        }
        return super.onBackPressed();
    }
}
