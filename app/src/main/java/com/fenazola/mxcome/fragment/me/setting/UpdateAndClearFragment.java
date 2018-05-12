package com.fenazola.mxcome.fragment.me.setting;

import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.app.BaseApplication;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.CenterDialog;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.https.GlideCacheUtils;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.SharedPrefUtils;
import com.zss.library.utils.StringUtils;
import com.zss.library.widget.CommonDialog;
import com.zss.library.widget.CommonEditWidget;
import com.zss.library.widget.CommonSwitchWidget;
import com.zss.library.widget.CommonTextWidget;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by XDONE on 2017/3/28.
 * 更新与清理
 */

public class UpdateAndClearFragment extends BaseFragment {

    private CommonTextWidget tWidget1;
    private CommonTextWidget tWidget2;

    private CommonSwitchWidget sWidget1;
    private CommonSwitchWidget sWidget2;

    private SharedPrefUtils spUtils = Utils.getSharedPrefSettingFile();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_update_clear;
    }

    @Override
    public void initView() {
        super.initView();
        tWidget1 = (CommonTextWidget) findViewById(R.id.tWidget1);
        tWidget2 = (CommonTextWidget) findViewById(R.id.tWidget2);

        sWidget1 = (CommonSwitchWidget) findViewById(R.id.sWidget1);
        sWidget2 = (CommonSwitchWidget) findViewById(R.id.sWidget2);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        tWidget1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqData();
            }
        });

        tWidget2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlideCacheUtils.getInstance().clearDiskCache(BaseApplication.getInstance());
                CommonToastUtils.showInCenterToast(getActivity(), "清理完成");
                tWidget2.setRightText("");
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String cacheSize = GlideCacheUtils.getInstance().getCacheSize(BaseApplication.getInstance());
                tWidget2.post(new Runnable() {
                    @Override
                    public void run() {
                        tWidget2.setRightText(cacheSize);
                    }
                });
            }
        }).start();

        sWidget1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                spUtils.put("wifi_auto_download", isChecked);
                sWidget1.setChecked(isChecked);
            }
        });

        sWidget2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                spUtils.put("cache_auto_clear", isChecked);
                sWidget2.setChecked(isChecked);
            }
        });
        setSwitchUi();
    }

    private void reqData() {
        HashMap<String, String> map = new HashMap<>();
        UserEntry userEntry = Utils.getUserEntry();
        String user_id = userEntry.getUser_id();
        String versionName = StringUtils.getVersionName(getActivity());
        map.put("user_id", user_id);
        map.put("verno", versionName);
        map.put("os_type", "1");
        NetWorkUtils.post(getActivity(), "appjrc/queryAppVersion.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject jsonObject = resObj.optJSONObject("res");
                String verNo = jsonObject.optString("ver_no");
                final String downPath = jsonObject.optString("down_path");
                if (!StringUtils.getVersionName(getActivity()).equals(verNo)) {
                    CommonDialog dialog = new CommonDialog(getActivity());
                    dialog.setTitle("发现新版本");
                    dialog.setContentText("检测到新版本，是否更新？");
                    dialog.setOnClickCancelListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    dialog.setOnClickConfirmListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.downloadApk(getBaseActivity(), Constant.imageUrl + downPath);
                        }
                    });
                    dialog.show();
                } else {
                    CenterDialog dialog = new CenterDialog(getActivity());
                    dialog.setContent(Html.fromHtml("当前已是最新版本<font color='#4BB7FD'>" + " :)" + "</font>"));
                    dialog.show();
                }
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public void setSwitchUi() {
        boolean autoDownload = spUtils.get("wifi_auto_download", false);
        sWidget1.setChecked(autoDownload);

        boolean autoClear = spUtils.get("cache_auto_clear", false);
        sWidget2.setChecked(autoClear);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle(getString(R.string.update_clear));
    }
}
