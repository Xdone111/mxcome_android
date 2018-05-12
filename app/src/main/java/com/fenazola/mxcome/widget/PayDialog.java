package com.fenazola.mxcome.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.fenazola.mxcome.R;
import com.zss.library.utils.DPUtils;
import com.zss.library.widget.CommonTextWidget;


/**
 * 支付对话框
 *
 * @author zm
 */
public class PayDialog extends Dialog implements View.OnClickListener {

    private CommonTextWidget listRow1;
    private CommonTextWidget listRow2;
    private CommonTextWidget listRow3;
    private CommonTextWidget listRow4;

    private OnSelectListener listener;

    public enum SelectPayMode {
        ME_BAO, ACC_BAL, ALIPAY, WECHAT
    }

    public PayDialog(Context context, OnSelectListener listener) {
        super(context, R.style.CommonDialog);
        setContentView(R.layout.dialog_pay);
        this.listener = listener;
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = DPUtils.getScreenW(getContext());
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
        initView();
    }

    private void initView() {
        listRow1 = (CommonTextWidget) findViewById(R.id.listRow1);
        listRow2 = (CommonTextWidget) findViewById(R.id.listRow2);
        listRow3 = (CommonTextWidget) findViewById(R.id.listRow3);
        listRow4 = (CommonTextWidget) findViewById(R.id.listRow4);

        int padding = DPUtils.dip2px(getContext(), 14);
        listRow1.setCenterPadding(padding, 0, 0, 0);
        listRow2.setCenterPadding(padding, 0, 0, 0);
        listRow3.setCenterPadding(padding, 0, 0, 0);
        listRow4.setCenterPadding(padding, 0, 0, 0);

        listRow1.setOnClickListener(this);
        listRow2.setOnClickListener(this);
        listRow3.setOnClickListener(this);
        listRow4.setOnClickListener(this);

        listRow1.setVisibility(View.VISIBLE);
        listRow2.setVisibility(View.VISIBLE);
    }

    public void setlistRow2Visible() {
        listRow2.setVisibility(View.GONE);
    }

    public void setMeBao(String balance) {
        listRow1.setSummaryText(getContext().getString(R.string.project_able_balance, balance));
    }

    public void setAccBal(String balance) {
        listRow2.setSummaryText(getContext().getString(R.string.project_able_balance, balance));
    }

    public void setBgColor(int color) {
        // listRow2.setBackgroundColor(color);
        listRow2.setLeftTextColor(color);
        listRow2.setSummaryTextColor(color);
        listRow2.setOnClickListener(null);
    }

    public void show() {
        try {
            super.show();
        } catch (Exception e) {
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }
    }

    public interface OnSelectListener {
        void onSelect(SelectPayMode mode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.listRow1:
                if (listener != null) {
                    listener.onSelect(SelectPayMode.ME_BAO);
                }
                dismiss();
                break;
            case R.id.listRow2:
                if (listener != null) {
                    listener.onSelect(SelectPayMode.ACC_BAL);
                }
                dismiss();
                break;
            case R.id.listRow3:
                if (listener != null) {
                    listener.onSelect(SelectPayMode.ALIPAY);
                }
                dismiss();
                break;
            case R.id.listRow4:
                if (listener != null) {
                    listener.onSelect(SelectPayMode.WECHAT);
                }
                dismiss();
                break;

        }
    }
}
