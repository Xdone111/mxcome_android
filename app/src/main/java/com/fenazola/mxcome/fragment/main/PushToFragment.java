package com.fenazola.mxcome.fragment.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.iframe.alipay.AliPayUtils;
import com.fenazola.iframe.alipay.PayResult;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.BaseEntry;
import com.fenazola.mxcome.entry.MsgDataEntry;
import com.fenazola.mxcome.entry.MsgEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.WorkDesignerDetailEntry;
import com.fenazola.mxcome.entry.WorkerEntry;
import com.fenazola.mxcome.fragment.main.designer.DesignerDetailFragment;
import com.fenazola.mxcome.fragment.main.worker.WorkerDetailFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
import com.fenazola.mxcome.widget.PayDialog;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.widget.CommonDialog;
import com.zss.library.widget.CommonWhiteDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by XDONE on 2017/6/21.
 */

public class PushToFragment extends BaseFragment {

    private ListView listView;
    private MsgEntry msgEntry;
    private CommonAdapter<MsgDataEntry> adapter;
    private ArrayList<MsgDataEntry> data = new ArrayList<MsgDataEntry>();
    private PayDialog dialog;
    private String pre_amount;
    private String order_no;
    private ImageView vsImg;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_push_to;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
        vsImg = (ImageView) findViewById(R.id.vs_img);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle bundle = getArguments();
        msgEntry = (MsgEntry) bundle.getSerializable(Constant.key1);
        data = (ArrayList<MsgDataEntry>) msgEntry.getData();
        if (data.size() == 1) {
            vsImg.setVisibility(View.INVISIBLE);
        } else {
            vsImg.setVisibility(View.VISIBLE);
        }
        adapter = new CommonAdapter<MsgDataEntry>(getActivity(), R.layout.listview_push_to) {
            @Override
            protected void convert(ViewHolder viewHolder, final MsgDataEntry dataEntry, int i) {
                TextView tvType = viewHolder.findViewById(R.id.tv_type);
                TextView name = viewHolder.findViewById(R.id.name);
                TextView tvDetail = viewHolder.findViewById(R.id.tv_detail);
                TextView tvConfirm = viewHolder.findViewById(R.id.tv_confirm);
                TextView tvScore = viewHolder.findViewById(R.id.tv_score);
                ImageView photo = viewHolder.findViewById(R.id.photo);
                LinearLayout linear = viewHolder.findViewById(R.id.linear);
                String skill = Utils.numberToString(Integer.parseInt(dataEntry.getSkill()));
                tvType.setText(skill);
                String distance = dataEntry.getDistance();
                String worker_name = dataEntry.getWorker_name();
                String score = dataEntry.getScore();
                tvDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String w_mxcomeno = dataEntry.getW_mxcomeno();
                        HashMap<String, String> map = new HashMap<>();
                        map.put("mxcome_no", w_mxcomeno);
                        map.put("user_id", "");
                        NetWorkUtils.post(getActivity(), "appApi/queryZxProviderDetails.do", map, new NetWorkUtils.IListener() {
                            @Override
                            public void onSuccess(String result, JSONObject resObj) {
                                String res = resObj.optString("res");
                                WorkDesignerDetailEntry entry = GsonUtils.getObjFromJSON(res, WorkDesignerDetailEntry.class);
                                LogUtils.i("base--------------", entry.toString());
                                if (dataEntry.getSkill().equals("800")) {
                                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                                    intent.putExtra(ZFrameActivity.CLASS_NAME, DesignerDetailFragment.class.getName());
                                    intent.putExtra(Constant.key1, entry);
                                    Utils.startLogin(getActivity(), intent);
                                } else {
                                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                                    intent.putExtra(ZFrameActivity.CLASS_NAME, WorkerDetailFragment.class.getName());
                                    intent.putExtra(Constant.key1, entry);
                                    Utils.startLogin(getActivity(), intent);
                                }
                            }

                            @Override
                            public void onError(String result, String code, String msg) {

                            }
                        });
                    }
                });
                name.setText(worker_name + " 距您" + distance + "公里");
                if (!TextUtils.isEmpty(score)) {
                    tvScore.setText("综合" + score);
                }
                if (i % 2 == 0) {
                    linear.setBackgroundResource(R.mipmap.order_blue_bg);
                    Drawable top = getResources().getDrawable(R.mipmap.info_red);
                    tvDetail.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                    photo.setImageResource(R.mipmap.order_icon_blue);
                } else {
                    linear.setBackgroundResource(R.mipmap.order_red_bg);
                    Drawable top = getResources().getDrawable(R.mipmap.info_blue);
                    tvDetail.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                    photo.setImageResource(R.mipmap.order_icon_red);
                }
                tvConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, String> map = new HashMap<>();
                        order_no = dataEntry.getOrder_no();
                        String mxcomeno = dataEntry.getW_mxcomeno();
                        map.put("orderNo", order_no);
                        map.put("pid", "");
                        map.put("workers", mxcomeno);
                        String url = Constant.newBaseUrl + "ordermodule/addworkers.do";
                        NetWorkUtils.postUrl(getActivity(), url, map, new NetWorkUtils.IListener() {

                            @Override
                            public void onSuccess(String result, JSONObject resObj) {
                                JSONObject obj = resObj.optJSONObject("res");
                                pre_amount = obj.optString("pre_amount");
                                String pid = obj.optString("pid");
                                LogUtils.i("-------------", pre_amount + pid);
                                showPayDialog();
                            }

                            @Override
                            public void onError(String result, String code, String msg) {

                            }
                        });
                    }
                });
            }
        };
        listView.setAdapter(adapter);
        adapter.addAll(data);
    }

    public void showPayDialog() {
        dialog = new PayDialog(getActivity(), new PayDialog.OnSelectListener() {
            @Override
            public void onSelect(PayDialog.SelectPayMode mode) {
                if (mode == PayDialog.SelectPayMode.ALIPAY) {
                    getServiceTime("1");
                } else {
                    getServiceTime("2");
                }
            }
        });
        reqData();
        dialog.show();
    }

    private void reqData() {
        HashMap<String, String> map = new HashMap<>();
        UserEntry entry = Utils.getUserEntry();
        String mxcome_no = entry.getMxcome_no();
        map.put("mxcome_no", mxcome_no);
        NetWorkUtils.post(getActivity(), "/hrpc/getAppClientRecharge.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject obj = resObj.optJSONObject("res");
                String acount = obj.optString("acount");
                if (acount != null) {
                    dialog.setAccBal(acount);
                    int data = acount.compareTo(pre_amount);
                    if (data < 0) {
                        dialog.setBgColor(getColor(R.color.text_hint_color));
                    }
                } else {
                    dialog.setAccBal("0");
                }
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public void getServiceTime(final String paytype) {
        HashMap<String, String> map = new HashMap<>();
        NetWorkUtils.post(getActivity(), "hrpc/getServerTime.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject obj = resObj.optJSONObject("res");

                orderPrePay("0.01", "支付定金", paytype, obj.optString("timestamp"));
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public void orderPrePay(String amount, String title, String paytype, String timestamp) {
        UserEntry user = Utils.getUserEntry();
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", user.getUser_id());
        map.put("order_no", order_no);
        map.put("amount", amount);
        map.put("title", title);
        map.put("paytype", paytype); //1支付宝 2微信 3网银
        map.put("timestamp", timestamp);
        LogUtils.i("----------------------", order_no);
        String sign = EncryptUtil.AesEncrypt(new String(order_no + amount + title + paytype + timestamp));
        map.put("sign", sign);
        NetWorkUtils.post(getActivity(), "hrpc/orderOnPrePay.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject obj = resObj.optJSONObject("res");
                String orderInfo = obj.optString("orderInfo");
                AliPayUtils.goPay(getActivity(), orderInfo, new AliPayUtils.OnPayListener() {
                    @Override
                    public void onResult(PayResult result) {
                        String resultStatus = result.getResultStatus();
                        final String memo = result.getMemo();
                        if ("9000".equals(resultStatus)) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showPaySucDialog();
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CommonToastUtils.showInCenterToast(getActivity(), memo);
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public void showPaySucDialog() {
        final CommonWhiteDialog dialog = new CommonWhiteDialog(getActivity());
        dialog.setTitle("支付成功");
        dialog.setContentText("成功支付定金" + pre_amount + "元");
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getActivity().finish();
            }
        });
        dialog.setOnClickConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        dialog.show();
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.push_to_bg));
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setTitle("");
    }
}
