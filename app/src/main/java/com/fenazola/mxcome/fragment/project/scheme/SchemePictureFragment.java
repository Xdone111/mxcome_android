package com.fenazola.mxcome.fragment.project.scheme;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.adapter.ImageBrowseAdapter;
import com.fenazola.mxcome.adapter.PicturePagerAdapter;
import com.fenazola.mxcome.entry.DesignPlanEntry;
import com.fenazola.mxcome.entry.ProjectEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.WatiPayEntry;
import com.fenazola.mxcome.entry.WorkerListEntry;
import com.fenazola.mxcome.fragment.common.BrowserFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.zss.library.PermissionCallBack;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.photopicker.PhotoPickerActivity;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.FileUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.PicUtils;
import com.zss.library.utils.StringUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XDONE on 2017/3/24.
 * 进度方案
 */

public class SchemePictureFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager viewPager1;
    private ViewPager viewPager2;
    private ViewPager viewPager3;

    private PicturePagerAdapter adapter1;
    private PicturePagerAdapter adapter2;
    private PicturePagerAdapter adapter3;

    private TextView tips1;
    private TextView tips2;
    private TextView tips3;

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    private WatiPayEntry project=null;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_scheme_picture;
    }

    @Override
    public void initView() {
        super.initView();

        viewPager1 = (ViewPager) findViewById(R.id.viewPager1);
        viewPager2 = (ViewPager) findViewById(R.id.viewPager2);
        viewPager3 = (ViewPager) findViewById(R.id.viewPager3);

        tips1 = (TextView) findViewById(R.id.tips1);
        tips2 = (TextView) findViewById(R.id.tips2);
        tips3 = (TextView) findViewById(R.id.tips3);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            project = (WatiPayEntry) args.get(Constant.key1);
        }
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);

        adapter1 = new PicturePagerAdapter(getContext(), new PicturePagerAdapter.OnImageClickListener() {
            @Override
            public void click(View item, List<String> urls, int position) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, BrowserFragment.class.getName());
                intent.putExtra(Constant.key1, position);
                intent.putStringArrayListExtra(Constant.key2, (ArrayList<String>) urls);
                startActivity(intent);
            }
        });
        viewPager1.setAdapter(adapter1);

        adapter2 = new PicturePagerAdapter(getContext(), new PicturePagerAdapter.OnImageClickListener() {
            @Override
            public void click(View item, List<String> urls, int position) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, BrowserFragment.class.getName());
                intent.putExtra(Constant.key1, position);
                intent.putStringArrayListExtra(Constant.key2, (ArrayList<String>) urls);
                startActivity(intent);
            }
        });
        viewPager2.setAdapter(adapter2);

        adapter3 = new PicturePagerAdapter(getContext(), new PicturePagerAdapter.OnImageClickListener() {
            @Override
            public void click(View item, List<String> urls, int position) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, BrowserFragment.class.getName());
                intent.putExtra(Constant.key1, position);
                intent.putStringArrayListExtra(Constant.key2, (ArrayList<String>) urls);
                startActivity(intent);
            }
        });
        viewPager3.setAdapter(adapter3);


        loadData();
        //selectPicture();
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("工程方案");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView1:
                openPhotoPicker(0x01);
                break;
            case R.id.textView2:

                break;
            case R.id.textView3:
                break;
        }
    }

    public void openPhotoPicker(final int requestCode) {
        verifyPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}
                , 0x01, new PermissionCallBack() {

                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
                        startActivityForResult(intent, requestCode);
                    }

                    @Override
                    public void onDenied() {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == 0x01) {
            if (data.getExtras() != null && data.getExtras().containsKey(PhotoPickerActivity.KEY_RESULT)) {
                List<String> results = (List<String>) data.getExtras().get(PhotoPickerActivity.KEY_RESULT);
                if (results != null && results.size() > 0) {
                    String fileUrl = results.get(0);
                    File file = new File(FileUtils.getExtDir(getActivity()) + Constant.imageDir );
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    String targetPath = file.getPath() + File.separator+ "temp.jpg";
                    fileUrl = PicUtils.compressImage(fileUrl, targetPath, 50);
                    uploadScheme(fileUrl, "1");
                }
            }
        }
    }

    public void uploadScheme(final String fileUrl, final String type) {
        Utils.uploadImage(getActivity(), fileUrl, new Utils.UrlCallback() {
            @Override
            public void onSuccess(String url) {
                Map<String, String> map = new HashMap<>();
                UserEntry entry = Utils.getUserEntry();
                map.put("user_id", entry.getUser_id());
                map.put("mxcome_no", entry.getMxcome_no());
                map.put("project_id", project.getPid());
               // map.put("project_id","1");
                map.put("pics", url);
                map.put("type", type);
                NetWorkUtils.post(getActivity(), "appjrc/saveDesignPlan.do", map, new NetWorkUtils.IListener() {
                    @Override
                    public void onSuccess(String result, JSONObject resObj) {
                        LogUtils.i("XHX","返回："+result);
                        FileUtils.delete(fileUrl);
                        loadData();
                    }

                    @Override
                    public void onError(String result, String code, String msg) {

                    }
                });
            }

            @Override
            public void onError() {

            }
        });
    }

    public void loadData() {
        Map<String, String> map = new HashMap<>();
        UserEntry entry = Utils.getUserEntry();
        map.put("user_id", entry.getUser_id());
        map.put("mxcome_no", entry.getMxcome_no());
        map.put("project_id", project.getPid());
       // map.put("project_id", ""+1);
        NetWorkUtils.post(getActivity(), "appjrc/queryDesignPlanForZx.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                LogUtils.i("XHX","返回参数："+result);
                List<DesignPlanEntry> datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<DesignPlanEntry>>(){}.getType());
                if (datas != null && datas.size() > 0) {
                    List<String> data1 = new ArrayList<>();
                    List<String> data2 = new ArrayList<>();
                    List<String> data3 = new ArrayList<>();
                    for (DesignPlanEntry item : datas) {
                        if ("1".equals(item.getType())) {
                            data1.add(Constant.imageUrl+item.getPics());
                        } else if ("2".equals(item.getType())) {
                            data2.add(Constant.imageUrl+item.getPics());
                        } else {
                            data3.add(Constant.imageUrl+item.getPics());
                        }
                    }
                    adapter1.addAll(data1);
                    adapter2.addAll(data2);
                    adapter3.addAll(data3);
                    tips1.setText(StringUtils.parse(data1.size()+"张", "[0-9]*$", getColor(R.color.colorBlue)));
                    tips2.setText(StringUtils.parse(data2.size()+"张", "[0-9]*$", getColor(R.color.colorBlue)));
                    tips3.setText(StringUtils.parse(data3.size()+"张", "[0-9]*$", getColor(R.color.colorBlue)));
                }
            }
            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }
//    public void selectPicture() {
//        // String user_id; //登录用户id(√)
//        //String mxcome_no; //用户mx号(√)
//        //String project_id; //装修工程id
//
//        Map<String, String> map = new HashMap<>();
//        UserEntry user = Utils.getUserEntry();
//        map.put("mxcome_no", user.getMxcome_no());
//        map.put("user_id",user.getUser_id());
//        map.put("project_id", project.getPid());
//        NetWorkUtils.post(getActivity(), "appjrc/queryDesignPlanForZx.do", map, new NetWorkUtils.IListener() {
//            @Override
//            public void onSuccess(String result, JSONObject resObj) {
//                String res = resObj.optString("res");
//                LogUtils.i("XHX","返回参数："+result);
//
//            }
//
//            @Override
//            public void onError(String result, String code, String msg) {
//
//            }
//        });
//    }

}
