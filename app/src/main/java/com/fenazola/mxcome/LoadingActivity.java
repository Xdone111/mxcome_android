package com.fenazola.mxcome;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableArea;
import com.fenazola.mxcome.entry.InitEntry;
import com.fenazola.mxcome.entry.ParamsEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushManager;
import com.zss.library.activity.BaseActivity;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.SharedPrefUtils;
import com.zss.library.utils.StringUtils;
import com.zss.library.widget.CommonDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 启动页
 */
public class LoadingActivity extends BaseActivity {

    private Handler mHadler = new Handler();
    private TextView versionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.GONE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_loading;
    }

    @Override
    public void initView() {
        super.initView();
        versionId = (TextView) findViewById(R.id.versionId);
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);



        versionId.setText("Copyright © 2017 V" + StringUtils.getVersionName(this));
        initData();
    }

    public void initData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("app_type", "1");
        map.put("clientid", PushManager.getInstance().getClientid(getActivity()));
        map.put("os_type", "1");
        NetWorkUtils.post(getActivity(), "baseApi/initData.do", map, false, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<InitEntry> list = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<InitEntry>>() {
                }.getType());
                for (InitEntry item : list) {
                    if ("appver".equals(item.getItem_name())) {
                        versionUpdate(item);
                    }
                    if ("city_db".equals(item.getItem_name())){
                        databaseUpdate(item);
                    }
                }
            }

            @Override
            public void onError(String result, String code, String msg) {
                startPage();
            }
        });

    }

    public void versionUpdate(InitEntry item){
        ParamsEntry params = item.getParams();
        final String down_path = params.getDown_path();
        String ver_no = params.getVer_no();
        LogUtils.i("xdone---------------", Constant.imageUrl + down_path);
        if (!StringUtils.getVersionName(getActivity()).equals(ver_no)) {
            CommonDialog dialog = new CommonDialog(getActivity());
            dialog.setTitle("版本更新");
            dialog.setContentText("检测到新版本，是否更新？");
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    startPage();
                }
            });
            dialog.setOnClickConfirmListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.downloadApk(LoadingActivity.this, Constant.imageUrl + down_path);
                    startPage();
                }
            });
            dialog.show();
        } else {
            startPage();
        }
    }

    public void databaseUpdate(InitEntry item){
        String city_db = item.getItem_value();
        SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
        String local_db = prefUtils.get("city_db", "20170220");
        if (!TextUtils.isEmpty(local_db) && local_db.equals(city_db)) {
            LogUtils.i("---zss---", "---- database version same ----");
            prefUtils.put("city_db", city_db);
        } else {
            LogUtils.i("---zss---", "---- database update ----");
            if (prefUtils.get("AreaInit", false)) { //第一次是否插入完成
                pullDataBase(city_db);
            }
        }
    }

    public void startPage() {
        mHadler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
                boolean init = prefUtils.get("GuideInit", false);
                if (init) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getActivity(), GuideActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }

    public void pullDataBase(final String versionCode) {
        HashMap<String, String> map = new HashMap<>();
        map.put("version_code", versionCode);
        NetWorkUtils.post(getActivity(), "baseApi/pullServerArea.do", map, false, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<TableArea> areaList = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<TableArea>>() {
                }.getType());
                if (areaList != null && areaList.size() > 0) {
                    BaseDaoImpl dao = new BaseDaoImpl(TableArea.class);
                    for (int i = 0; i < areaList.size(); i++) {
                        TableArea areaItem = areaList.get(i);
                        if ("c".equalsIgnoreCase(areaItem.getOp_type())) {
                            try {
                                String sql = "insert into tb_area(areaid,area,parentid,areatype,keyword) values ('" + areaItem.getAreaid() + "', '" + areaItem.getArea() + "', '" + areaItem.getParentid() + "', '" + areaItem.getAreatype() + "', '" + areaItem.getKeyword() + "')";
                                LogUtils.i("---zss---", "-------- insert " + sql);
                                dao.execSql(sql);
                            } catch (SQLException e) {
                            }
                        } else if ("u".equalsIgnoreCase(areaItem.getOp_type())) {
                            try {
                                String sql = "update tb_area set area = '" + areaItem.getArea() + "', parentid = '" + areaItem.getParentid() + "', areatype = '" + areaItem.getAreatype() + "', keyword = '" + areaItem.getKeyword() + "' WHERE areaid = '" + areaItem.getAreaid() + "'";
                                LogUtils.i("---zss---", "-------- update " + sql);
                                dao.execSql(sql);
                            } catch (SQLException e) {
                            }
                        } else if ("d".equalsIgnoreCase(areaItem.getOp_type())) {
                            try {
                                String sql = "delete from tb_area where areaid = '" + areaItem.getAreaid() + "'";
                                LogUtils.i("---zss---", "-------- delete " + sql);
                                dao.execSql(sql);
                            } catch (SQLException e) {
                            }
                        }
                    }
                }
                SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
                prefUtils.put("city_db", versionCode);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }


}

