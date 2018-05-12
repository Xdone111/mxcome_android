package com.fenazola.mxcome.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.app.BaseApplication;
import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableTalk;
import com.fenazola.mxcome.entry.LocationEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.project.PremiseEntry;
import com.fenazola.mxcome.fragment.me.LoginFragment;
import com.fenazola.mxcome.fragment.me.LoginReadyFragment;
import com.fenazola.mxcome.fragment.project.ProjectChoiceFragment;
import com.fenazola.mxcome.fragment.project.ProjectInputInfoFragment;
import com.fenazola.mxcome.fragment.project.ProjectReadyFragment;
import com.fenazola.mxcome.service.DownloadService;
import com.google.gson.reflect.TypeToken;
import com.zss.library.PermissionCallBack;
import com.zss.library.activity.BaseActivity;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.ptr.PtrClassicFrameLayout;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.FileUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.SharedPrefUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zm on 2017/2/28.
 */

public class Utils {

    public static SharedPrefUtils getSharedPrefSettingFile() {
        return new SharedPrefUtils(BaseApplication.getInstance(), "setting_file");
    }


    public static SharedPrefUtils getSharedPrefCommonFile() {
        return new SharedPrefUtils(BaseApplication.getInstance(), "common_file");
    }

    public static void saveUserEntry(UserEntry user) {
        SharedPrefUtils prefUtils = getSharedPrefCommonFile();
        prefUtils.put("user", user);
    }

    public static UserEntry getUserEntry() {
        SharedPrefUtils prefUtils = getSharedPrefCommonFile();
        Object obj = prefUtils.get("user");
        if (obj != null) {
            return (UserEntry) obj;
        } else {
            return null;
        }
    }

    public static void saveLocationEntry(LocationEntry location) {
        SharedPrefUtils prefUtils = new SharedPrefUtils(BaseApplication.getInstance(), "location_file");
        prefUtils.put("location", location);
    }

    public static LocationEntry getLocationEntry() {
        SharedPrefUtils prefUtils = new SharedPrefUtils(BaseApplication.getInstance(), "location_file");
        Object obj = prefUtils.get("location");
        if (obj != null) {
            return (LocationEntry) obj;
        } else {
            LocationEntry location = new LocationEntry();
            prefUtils.put("location", location);
            return location;
        }
    }

    public static boolean isLogin() {
        return getUserEntry() != null;
    }

    public static void startLogin(Activity activity, String className) {
        if (isLogin()) {
            if (!TextUtils.isEmpty(className)) {
                Intent intent = new Intent(activity, ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, className);
                activity.startActivity(intent);
            }
        } else {
            Intent intent = new Intent(activity, ZFrameActivity.class);
            intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
            activity.startActivity(intent);
        }
    }

    public static void startLogin(Activity activity, Intent goIntent) {
        if (isLogin()) {
            if (goIntent != null) {
                activity.startActivity(goIntent);
            }
        } else {
            Intent intent = new Intent(activity, ZFrameActivity.class);
            intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
            activity.startActivity(intent);
        }
    }

    public static void startNewProject(Activity activity) {
        if (isLogin()) {
            SharedPrefUtils prefUtils = getSharedPrefCommonFile();
            boolean init = prefUtils.get("ProjectReadyInit", false);
            if (init) {
                startProject(activity);
            } else {
                Intent intent = new Intent(activity, ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, ProjectReadyFragment.class.getName());
                activity.startActivity(intent);
            }
        } else {
            Intent intent = new Intent(activity, ZFrameActivity.class);
            intent.putExtra(ZFrameActivity.CLASS_NAME, LoginReadyFragment.class.getName());
            activity.startActivity(intent);
        }
    }

    public static void startProject(final Activity activity) {
        if (isLogin()) {
            UserEntry entry = Utils.getUserEntry();
            Map<String, String> map = new HashMap<>();
            map.put("user_id", entry.getUser_id());
            NetWorkUtils.post(activity, "appApi/fDecoraInfo.do", map, new NetWorkUtils.IListener() {
                @Override
                public void onSuccess(String result, JSONObject resObj) {
                    String res = resObj.optString("res");
                    List<PremiseEntry> datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<PremiseEntry>>() {
                    }.getType());
                    if (datas != null && datas.size() > 0) {
                        Intent intent = new Intent(activity, ZFrameActivity.class);
                        intent.putExtra(ZFrameActivity.CLASS_NAME, ProjectChoiceFragment.class.getName());
                        intent.putExtra(Constant.key1, res);
                        activity.startActivity(intent);
                    } else {
                        Intent intent = new Intent(activity, ZFrameActivity.class);
                        intent.putExtra(ZFrameActivity.CLASS_NAME, ProjectInputInfoFragment.class.getName());
                        activity.startActivity(intent);
                    }
                }

                @Override
                public void onError(String result, String code, String msg) {

                }
            });
        } else {
            Intent intent = new Intent(activity, ZFrameActivity.class);
            intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
            activity.startActivity(intent);
        }
    }

    public static void commonError(Activity activity, String result, String code, String msg) {
        if (TextUtils.isEmpty(code) && TextUtils.isEmpty(msg)) {
            CommonToastUtils.showInCenterToast(activity, activity.getResources().getString(R.string.tips_connect_server_timeout));
        } else {
            CommonToastUtils.showInCenterToast(activity, msg + "(" + code + ")");
        }
    }


    public static void showDateDialog(final Activity mContext, final TextView date, final DatePickerDialog.OnDateSetListener listener) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = format.parse(date.getText().toString());
            now.setTime(d);
        } catch (ParseException e) {
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.DatePickerDialog,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String fDate = formatDate(year, monthOfYear + 1,
                                dayOfMonth);
                        date.setText(fDate);
                        if (listener != null) {
                            listener.onDateSet(view, year, monthOfYear, dayOfMonth);
                        }
                    }
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle(mContext.getString(R.string.common_choice_data));
        datePickerDialog.show();
    }

    /**
     * 格式化日期
     *
     * @return yyyy-MM-dd
     */
    public static String formatDate(int y, int m, int d) {
        StringBuffer buf = new StringBuffer();
        buf.append(y).append("-");
        if (m < 10) {
            buf.append("0" + m);
        } else {
            buf.append(m);
        }
        buf.append("-");
        if (d < 10) {
            buf.append("0" + d);
        } else {
            buf.append(d);
        }
        return buf.toString();
    }

    public static TableTalk createTalk(Context mContext, String cid, String name, String mxcome, String pic) {
        try {
            UserEntry user = Utils.getUserEntry();
            if (user != null) {
                BaseDaoImpl talkDao = new BaseDaoImpl(TableTalk.class);
                Object obj = talkDao.queryForFirst("mxcomeNo", mxcome);
                if (obj == null) {
                    TableTalk talk = new TableTalk();
                    talk.setCid(cid);
                    talk.setMxcomeNo(mxcome);
                    talk.setGroupName(name);
                    talk.setIcon(pic);
                    talk.setLastMsg("");
                    talk.setLastTime("");
                    talk.setBizType(1);  //业务类型 0:官方，1:客户，2:好友
                    talk.setUnreadCnt(0);
                    talk.setMeMxcomeNo(user.getMxcome_no());
                    talk.setSpecial("");
                    talkDao.save(talk);
                    obj = talkDao.queryForFirst("mxcomeNo", mxcome);
                }
                TableTalk talk = (TableTalk) obj;
                talk.setCid(cid);
                talk.setGroupName(name);
                talk.setIcon(pic);
                talkDao.update(talk);
                return talk;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void uploadImage(Activity mContext, String fileUrl, final UrlCallback callback) {
        File file = new File(fileUrl);
        Map<String, String> map = new HashMap<>();
        UserEntry entry = Utils.getUserEntry();
        map.put("user_id", entry.getUser_id());
        NetWorkUtils.upload(mContext, "appjrc/doUploadImgForApp.do", "file", file, map, new NetWorkUtils.IProgressListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject res = resObj.optJSONObject("res");
                String url = res.optString("head_img");
                if (callback != null) {
                    callback.onSuccess(url);
                }
            }

            @Override
            public void onProgress(int index, long totalBytes, long surplusBytes, boolean done) {

            }

            @Override
            public void onError(String result, String code, String msg) {
                if (callback != null) {
                    callback.onError();
                }
            }
        });
    }

    public static void downloadApk(final BaseActivity activity, final String url) {
        activity.verifyPermissions(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}
                , 0x01, new PermissionCallBack() {
                    @Override
                    public void onGranted() {
                        LogUtils.i("test", "-------url = " + url);
                        File dir = new File(FileUtils.getExtDir(activity) + Constant.appName);
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        Intent intent = new Intent(activity, DownloadService.class);
                        intent.putExtra(Constant.key1, url);
                        intent.putExtra(Constant.key2, dir.getAbsolutePath());
                        activity.startService(intent);

                    }

                    @Override
                    public void onDenied() {
                    }
                });
    }

    public interface UrlCallback {
        public void onSuccess(String url);

        public void onError();
    }

    public static void setTextLine(TextView view) {
        view.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        view.getPaint().setAntiAlias(true);//抗锯齿
    }

    public static void setEmptyView(ListView listView) {
        setEmptyView(listView, 0, null, null);
    }

    public static void setEmptyViewProject(ListView listView, Activity activity) {
        setEmptyViewPorject(listView, 0, null, null, activity);
    }

    public static void setEmptyView(ListView listView, int resId, String text, View.OnClickListener listener) {
        View emptyView = LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout.listview_empty_page, null);
        ViewGroup parentView = (ViewGroup) listView.getParent();
        if (parentView instanceof PtrClassicFrameLayout) {
            parentView = (ViewGroup) parentView.getParent();
        }
        parentView.addView(emptyView);
        if (parentView instanceof LinearLayout) {
            emptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        } else if (parentView instanceof FrameLayout) {
            emptyView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
        } else if (parentView instanceof RelativeLayout) {
            emptyView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT));
        }
        ImageView tips_img = (ImageView) emptyView.findViewById(R.id.tips_img);
        TextView tips_text = (TextView) emptyView.findViewById(R.id.tips_text);
        if (resId > 0) {
            tips_img.setImageResource(resId);
        }
        if (!TextUtils.isEmpty(text)) {
            tips_text.setText(text);
        }
        emptyView.setOnClickListener(listener);
        listView.setEmptyView(emptyView);
    }

    public static void setEmptyViewPorject(ListView listView, int resId, String text, View.OnClickListener listener, final Activity activity) {
        View emptyView = LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout.listview_empty_project_page, null);
        TextView new_project = (TextView) emptyView.findViewById(R.id.new_project);
        new_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, ProjectInputInfoFragment.class.getName());
                activity.startActivity(intent);
            }
        });
        ViewGroup parentView = (ViewGroup) listView.getParent();
        if (parentView instanceof PtrClassicFrameLayout) {
            parentView = (ViewGroup) parentView.getParent();
        }
        parentView.addView(emptyView);
        if (parentView instanceof LinearLayout) {
            emptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        } else if (parentView instanceof FrameLayout) {
            emptyView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
        } else if (parentView instanceof RelativeLayout) {
            emptyView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT));
        }
        ImageView tips_img = (ImageView) emptyView.findViewById(R.id.tips_img);
        TextView tips_text = (TextView) emptyView.findViewById(R.id.tips_text);
        if (resId > 0) {
            tips_img.setImageResource(resId);
        }
        if (!TextUtils.isEmpty(text)) {
            tips_text.setText(text);
        }
        emptyView.setOnClickListener(listener);
        listView.setEmptyView(emptyView);
    }

    public static void queryUnreadCnt() {
        Integer sum = 0;
        BaseDaoImpl talkDao = new BaseDaoImpl(TableTalk.class);
        try {
            List<TableTalk> datas;
            UserEntry user = Utils.getUserEntry();
            if (user != null) {
                datas = talkDao.getDao().queryBuilder().where().eq("meMxcomeNo", user.getMxcome_no()).and().gt("unreadCnt", "0").query();
            } else {
                datas = new ArrayList<>();
            }
            for (TableTalk item : datas) {
                sum += item.getUnreadCnt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EventBus.getDefault().post(sum);
        }
    }

    public static String getFirstJSONArray(String str) {
        String ret = "";
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONArray arrs = new JSONArray(str);
                ret = arrs.getString(0);
            } catch (Exception e) {
            }
        }
        return ret;
    }

    public static int letterToNumber(String letter) {
        if (letter.equals(DataCache.ENUM_FG_JY)) {
            return 901;
        } else if (letter.equals(DataCache.ENUM_FG_ZS)) {
            return 902;
        } else if (letter.equals(DataCache.ENUM_FG_RS)) {
            return 903;
        } else if (letter.equals(DataCache.ENUM_FG_XD)) {
            return 904;
        } else if (letter.equals(DataCache.ENUM_FG_XGD)) {
            return 905;
        } else if (letter.equals(DataCache.ENUM_FG_TY)) {
            return 906;
        } else if (letter.equals(DataCache.ENUM_FG_OS)) {
            return 907;
        } else if (letter.equals(DataCache.ENUM_FG_YJ)) {
            return 908;
        } else if (letter.equals(DataCache.ENUM_FG_HD)) {
            return 909;
        } else if (letter.equals(DataCache.ENUM_FG_BO)) {
            return 910;
        } else if (letter.equals(DataCache.ENUM_FG_DNY)) {
            return 911;
        } else if (letter.equals(DataCache.ENUM_FG_JO)) {
            return 912;
        } else if (letter.equals(DataCache.ENUM_FG_MS)) {
            return 913;
        } else if (letter.equals(DataCache.ENUM_FG_ZZH)) {
            return 914;
        } else if (letter.equals(DataCache.ENUM_PW_SHE)) {
            return 401;
        } else if (letter.equals(DataCache.ENUM_PW_HAO)) {
            return 402;
        } else if (letter.equals(DataCache.ENUM_PW_SHU)) {
            return 403;
        } else if (letter.equals(DataCache.ENUM_PW_JIAN)) {
            return 404;
        } else {
            return 0;
        }

    }

    public static String numberToletter(int number) {
        if (number == 901) {
            return DataCache.ENUM_FG_JY;
        } else if (number == 902) {
            return DataCache.ENUM_FG_ZS;
        } else if (number == 903) {
            return DataCache.ENUM_FG_RS;
        } else if (number == 904) {
            return DataCache.ENUM_FG_XD;
        } else if (number == 905) {
            return DataCache.ENUM_FG_XGD;
        } else if (number == 906) {
            return DataCache.ENUM_FG_TY;
        } else if (number == 907) {
            return DataCache.ENUM_FG_OS;
        } else if (number == 908) {
            return DataCache.ENUM_FG_YJ;
        } else if (number == 909) {
            return DataCache.ENUM_FG_HD;
        } else if (number == 910) {
            return DataCache.ENUM_FG_BO;
        } else if (number == 911) {
            return DataCache.ENUM_FG_DNY;
        } else if (number == 912) {
            return DataCache.ENUM_FG_JO;
        } else if (number == 913) {
            return DataCache.ENUM_FG_MS;
        } else if (number == 914) {
            return DataCache.ENUM_FG_ZZH;
        } else if (number == 401) {
            return DataCache.ENUM_PW_SHE;
        } else if (number == 402) {
            return DataCache.ENUM_PW_HAO;
        } else if (number == 403) {
            return DataCache.ENUM_PW_SHU;
        } else if (number == 404) {
            return DataCache.ENUM_PW_JIAN;
        } else {
            return "";
        }
    }

    public static String numberToString(int number) {
        if (number == 901) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_JY);
        } else if (number == 902) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_ZS);
        } else if (number == 903) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_RS);
        } else if (number == 904) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_XD);
        } else if (number == 905) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_XGD);
        } else if (number == 906) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_TY);
        } else if (number == 907) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_OS);
        } else if (number == 908) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_YJ);
        } else if (number == 909) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_HD);
        } else if (number == 910) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_BO);
        } else if (number == 911) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_DNY);
        } else if (number == 912) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_JO);
        } else if (number == 913) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_MS);
        } else if (number == 914) {
            return DataCache.dictMap.get(DataCache.ENUM_FG_ZZH);
        } else if (number == 401) {
            return DataCache.dictMap.get(DataCache.ENUM_PW_SHE);
        } else if (number == 402) {
            return DataCache.dictMap.get(DataCache.ENUM_PW_HAO);
        } else if (number == 403) {
            return DataCache.dictMap.get(DataCache.ENUM_PW_SHU);
        } else if (number == 404) {
            return DataCache.dictMap.get(DataCache.ENUM_PW_JIAN);
        } else if (number == 404) {
            return DataCache.dictMap.get(DataCache.ENUM_PW_JIAN);
        } else if (number == 800) {
            return DataCache.dictMap.get(DataCache.SHEJI);
        } else if (number == 801) {
            return DataCache.dictMap.get(DataCache.CHAIGAI);
        } else if (number == 802) {
            return DataCache.dictMap.get(DataCache.SHUIDIAN);
        } else if (number == 803) {
            return DataCache.dictMap.get(DataCache.NIGONG);
        } else if (number == 804) {
            return DataCache.dictMap.get(DataCache.MUGONG);
        } else if (number == 805) {
            return DataCache.dictMap.get(DataCache.YOUQI);
        } else if (number == 806) {
            return DataCache.dictMap.get(DataCache.DIBAN);
        } else if (number == 807) {
            return DataCache.dictMap.get(DataCache.FANGSHUI);
        } else {
            return "";
        }
    }

    /**
     * 获取首页的所有显示入口啊
     */
    public static List<String> getMainType() {
        List<String> strings = new ArrayList<String>();
        //自定义 必须放在第一个  人家是钉子户
        strings.add("自定义");
        strings.add("发布");
        strings.add("优惠");
        strings.add("设置");
        strings.add("好友");
        strings.add("百科");
        strings.add("收藏");

        return strings;
    }

    /**
     * 获取首页的
     */
    public static List<String> getMainTypeDefault() {
        List<String> strings = new ArrayList<String>();
        //自定义 必须放在第一个  人家是钉子户
        strings.add("自定义");
        strings.add("发布");
        strings.add("优惠");
        return strings;
    }


    public static void setEditTextSize(Context context, EditText editText, String text) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(text);
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }
}
