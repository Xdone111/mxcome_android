package com.fenazola.mxcome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenazola.iframe.zxing.CaptureActivity;
import com.fenazola.mxcome.MainActivity;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.fragment.diary.DecorateDiaryFragment;
import com.fenazola.mxcome.fragment.festdeal.FastDealFragment;
import com.fenazola.mxcome.fragment.main.CustomFragment;
import com.fenazola.mxcome.fragment.main.demand.DemandFragment;
import com.fenazola.mxcome.fragment.main.demand.FreeDesignerFragment;
import com.fenazola.mxcome.fragment.main.designer.DesignerFragment;
import com.fenazola.mxcome.fragment.main.example.ExampleFragment;
import com.fenazola.mxcome.fragment.main.location.LocationFragment;
import com.fenazola.mxcome.fragment.main.tool.CalculateFragment;
import com.fenazola.mxcome.fragment.main.tool.CalendarFragment;
import com.fenazola.mxcome.fragment.main.tool.GradienterFragment;
import com.fenazola.mxcome.fragment.main.worker.WorkerFragment;
import com.fenazola.mxcome.fragment.me.DiscountFragment;
import com.fenazola.mxcome.fragment.me.LoginFragment;
import com.fenazola.mxcome.fragment.me.setting.SettingFragment;
import com.fenazola.mxcome.fragment.sercentre.ServiceCentreFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.SharedUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
import com.fenazola.mxcome.widget.CircleMenuLayout;
import com.fenazola.mxcome.widget.ToolMXLine;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.LogUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {
    /**
     * 菜单
     */
    private CircleMenuLayout menuLayout;
    private ImageView mainMXImg;
    private TextView designer, worker, actTv;
    private ToolMXLine mx_line;
    /**
     * 是否打开了工具
     */
    private boolean toolMode = false;

    /**
     * 存放圈里的菜单
     */
    List<String> defautLists = new ArrayList<String>();
    /**
     * 存放顶部文字菜单
     */
    List<String> otherLists = new ArrayList<String>();
    /**
     * 菜单文字颜色
     */
    List<Integer> mItemTextColors = new ArrayList<Integer>();
    /**
     * 菜单背景颜色
     */
    List<Integer> mItemIds = new ArrayList<Integer>();
    /**
     * 文字菜单容器
     */
    private LinearLayout toOtherpagerLy;
    private int actType = FreeDesignerFragment.INPT_ACT_TYPE;
    int width;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {
        super.initView();
        width = DPUtils.getScreenW(getActivity());
        menuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        designer = (TextView) findViewById(R.id.designer);
        worker = (TextView) findViewById(R.id.worker);
        mainMXImg = (ImageView) findViewById(R.id.mainMXImg);
        mx_line = (ToolMXLine) findViewById(R.id.mx_line);
        toOtherpagerLy = (LinearLayout) findViewById(R.id.to_other_pager_ly);
        actTv = (TextView) findViewById(R.id.act_tv);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (Utils.isLogin()) {
            queryVerifyInfo();
        }
        changeItemH();


        otherLists.add("案例");
        otherLists.add("服务中心");
        otherLists.add("日记");
        designer.setOnClickListener(this);
        worker.setOnClickListener(this);
        actTv.setOnClickListener(this);

        mainMXImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLayout.removeAllItem();

                menuLayout.setMenuItemIconsAndTexts(mItemIds, mItemTextColors, defautLists);
                mx_line.setLineX(-1);
                mainMXImg.setVisibility(View.INVISIBLE);
                designer.setVisibility(View.VISIBLE);
                worker.setVisibility(View.VISIBLE);
                toolMode = false;
            }
        });
        menuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {//菜单的所有子项目都在独立方法中操作
                switch (pos) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        onClickType(defautLists.get(pos));
                        break;
                }
            }

            @Override
            public void itemCenterClick(View view) {
                Utils.startNewProject(getActivity());
            }

            @Override
            public void itemLongClick(View view, int pos) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, CustomFragment.class.getName());
                startActivityForResult(intent, 100);
            }
        });
        //刷新文字菜单
        toOtherpagerLy.removeAllViews();

        for (int i = 0; i < otherLists.size(); i++) {
            //将ImageView添加进Viewpager容器
            final TextView imageView = new TextView(getActivity());
            imageView.setText(otherLists.get(i));
            imageView.setTextColor(getColor(R.color.colorGrey));
            imageView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            lp.setMargins(0, 0, 0, 0);
            imageView.setLayoutParams(lp);
            //imageView.setBackgroundColor(getColor(R.color.msg_light_grey));
            final int index1 = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickType(otherLists.get(index1));
                }
            });
            LogUtils.i("XHX:", "执行了刷新");
            toOtherpagerLy.addView(imageView);

        }
        initRullMenu();
    }

    private void changeItemH() {
        ImageView iv = (ImageView) findViewById(R.id.iv_1);
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) iv.getLayoutParams();
        linearParams.height = (int) ((float) width * 0.53);
        iv.setLayoutParams(linearParams);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100://从自定义菜单页面回调
                initRullMenu();
                break;
            case 101:
                MainActivity activity = (MainActivity) getActivity();
                activity.initRull();
                break;

        }
    }

    /**
     * 刷新菜单UI
     */
    private void initRullMenu() {

        defautLists = new SharedUtils(getContext()).loadArray(defautLists);
        //给初始状态
        if (defautLists == null || defautLists.size() < 1) {
            new SharedUtils(getContext()).saveArray(Utils.getMainTypeDefault());
            defautLists = new SharedUtils(getContext()).loadArray(defautLists);
        }
//        //获取所有的菜单项
//        otherLists = Utils.getMainType();
//        //移除圆圈里面的菜单
//        otherLists.removeAll(defautLists);
        //清空文字颜色和背景 并重新赋颜色背景
        if (defautLists.size() > 3) {
            defautLists.remove("自定义");
        }
        mItemTextColors.clear();
        mItemIds.clear();
        for (int i = 0; i < defautLists.size(); i++) {
            mItemTextColors.add(R.color.white);
            mItemIds.add(R.drawable.main_small_corner_bg_new);
        }
        //重新初始化菜单
        menuLayout.removeAllItem();
        menuLayout.setMenuItemIconsAndTexts(mItemIds, mItemTextColors, defautLists);

    }

    /***
     * 所有菜单项的点击事件都在这里处理啊
     * @param name
     */
    private void onClickType(String name) {
        Intent intent = null;
        if (name.equals("发布")) {
            if (toolMode) { //水平
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, GradienterFragment.class.getName());
                startActivity(intent);
            } else {
                Utils.startLogin(getActivity(), DemandFragment.class.getName());
            }
        } else if (name.equals("自定义")) {
            if (toolMode) { //计算
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, CalculateFragment.class.getName());
                startActivity(intent);
            } else {
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, CustomFragment.class.getName());
                startActivityForResult(intent, 100);
            }

        } else if (name.equals("优惠")) {
            if (toolMode) { //黄历
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, CalendarFragment.class.getName());
                startActivity(intent);
            } else {
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, DiscountFragment.class.getName());
                startActivity(intent);
            }

        } else if (name.equals("案例")) {
            intent = new Intent(getActivity(), ZFrameActivity.class);
            intent.putExtra(ZFrameActivity.CLASS_NAME, ExampleFragment.class.getName());
            startActivity(intent);

        } else if (name.equals("服务中心")) {
            intent = new Intent(getActivity(), ZFrameActivity.class);
            intent.putExtra(ZFrameActivity.CLASS_NAME, ServiceCentreFragment.class.getName());
            startActivity(intent);
        } else if (name.equals("日记")) {
            intent = new Intent(getActivity(), ZFrameActivity.class);
            intent.putExtra(ZFrameActivity.CLASS_NAME, DecorateDiaryFragment.class.getName());
            startActivity(intent);
        } else if (name.equals("设置")) {
            intent = new Intent(getActivity(), ZFrameActivity.class);
            intent.putExtra(ZFrameActivity.CLASS_NAME, SettingFragment.class.getName());
            startActivity(intent);
        } else if (name.equals("好友")) {
            MainActivity parentActivity = (MainActivity) getActivity();
            parentActivity.toPage(2);
        } else if (name.equals("百科")) {
            MainActivity parentActivity = (MainActivity) getActivity();
            parentActivity.toPage(3);
        } else if (name.equals("收藏")) {
            // Utils.startLogin(getActivity(), DecorateDiaryFragment.class.getName());
        }
    }

    private void queryVerifyInfo() {
        UserEntry user = Utils.getUserEntry();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", user.getUser_id());
        map.put("mxcome_no", user.getMxcome_no());
        String timestamp = "" + System.currentTimeMillis();
        String sign = EncryptUtil.AesEncrypt(new String(user.getUser_id() + timestamp));
        map.put("timestamp", timestamp);
        map.put("sign", sign);
        NetWorkUtils.post(getActivity(), "appApi/getAppUserInfo.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                UserEntry user = Utils.getUserEntry();
                user.setUserSign(user.getUserSign());
                user.setSex(user.getSex());
                user.setUser_name(user.getUser_name());
                user.setHasPayPwd(user.isHasPayPwd());
                Utils.saveUserEntry(user);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.designer:
                Utils.startLogin(getActivity(), DesignerFragment.class.getName());
                break;
            case R.id.worker:
                //Utils.startLogin(getActivity(), WorkerFragment.class.getName());
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, WorkerFragment.class.getName());
                getActivity().startActivity(intent);
                break;
            case R.id.act_tv:
                Intent intentz = new Intent(getActivity(), ZFrameActivity.class);
                intentz.putExtra(ZFrameActivity.CLASS_NAME, ActivityFragment.class.getName());
                intentz.putExtra(Constant.key1, actType);
                startActivity(intentz);
                break;
        }
    }

}
