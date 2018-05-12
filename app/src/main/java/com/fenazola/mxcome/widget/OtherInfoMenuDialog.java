package com.fenazola.mxcome.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.utils.DPUtils;


/**
 * 支付对话框
 *
 * @author zm
 */
public class OtherInfoMenuDialog extends Dialog implements View.OnClickListener {

    private TextView listRow1;
    private TextView listRow2;
    private TextView listRow3;
    private TextView listRow4;
    private TextView listRow5;
    private TextView listRow6;
    private TextView listRow7;
    private TextView listRow8;


    private OnSelectListener listener;

    public enum SelectTypeMode {
        BEI_ZHU, GUANZHU, MINGPIAN, QUANXIAN, SHANCHU, TOUSU, HEIMINGDAN, ZHUOMIAN
    }

    public OtherInfoMenuDialog(Context context, OnSelectListener listener) {
        super(context, R.style.CommonDialog);
        setContentView(R.layout.dialog_other_person_info);
        this.listener = listener;
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = DPUtils.getScreenW(getContext());
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
        initView();
    }

    private void initView() {
        listRow1 = (TextView) findViewById(R.id.listRow1);
        listRow2 = (TextView) findViewById(R.id.listRow2);
        listRow3 = (TextView) findViewById(R.id.listRow3);
        listRow4 = (TextView) findViewById(R.id.listRow4);
        listRow5 = (TextView) findViewById(R.id.listRow5);
        listRow6 = (TextView) findViewById(R.id.listRow6);
        listRow7 = (TextView) findViewById(R.id.listRow7);
        listRow8 = (TextView) findViewById(R.id.listRow8);

        listRow1.setOnClickListener(this);
        listRow2.setOnClickListener(this);
        listRow3.setOnClickListener(this);
        listRow4.setOnClickListener(this);
        listRow5.setOnClickListener(this);
        listRow6.setOnClickListener(this);
        listRow7.setOnClickListener(this);
        listRow8.setOnClickListener(this);
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
        void onSelect(SelectTypeMode mode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.listRow1:
                if (listener != null) {
                    listener.onSelect(SelectTypeMode.BEI_ZHU);
                }
                dismiss();
                break;
            case R.id.listRow2:
                if (listener != null) {
                    listener.onSelect(SelectTypeMode.GUANZHU);
                }
                dismiss();
                break;
            case R.id.listRow3:
                if (listener != null) {
                    listener.onSelect(SelectTypeMode.MINGPIAN);
                }
                dismiss();
                break;
            case R.id.listRow4:
                if (listener != null) {
                    listener.onSelect(SelectTypeMode.QUANXIAN);
                }
                dismiss();
                break;
            case R.id.listRow5:
                if (listener != null) {
                    listener.onSelect(SelectTypeMode.SHANCHU);
                }
                dismiss();
                break;
            case R.id.listRow6:
                if (listener != null) {
                    listener.onSelect(SelectTypeMode.TOUSU);
                }
                dismiss();
                break;
            case R.id.listRow7:
                if (listener != null) {
                    listener.onSelect(SelectTypeMode.HEIMINGDAN);
                }
                dismiss();
                break;
            case R.id.listRow8:
                if (listener != null) {
                    listener.onSelect(SelectTypeMode.ZHUOMIAN);
                }
                dismiss();
                break;

        }
    }
}
