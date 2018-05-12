package com.fenazola.mxcome.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.utils.DPUtils;

/**
 * Created by XDONE on 2017/6/3.
 */

public class ScheduleDialog extends Dialog {

    private OnSelectListener listener;
    private TextView text1;
    private TextView text2;

    public ScheduleDialog(Context context, OnSelectListener listener) {
        super(context, R.style.CommonDialog);
        setContentView(R.layout.dialog_schedule);
        setCanceledOnTouchOutside(true);
        this.listener = listener;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = DPUtils.getScreenW(getContext());
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        initView();
    }

    private void initView() {
        text1 = (TextView) findViewById(R.id.text1);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelect(0);
            }
        });
        text2 = (TextView) findViewById(R.id.text2);
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelect(1);
            }
        });
        findViewById(R.id.text3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelect(1);
            }
        });
    }

    public void setText1(CharSequence text) {
        text1.setText(text);

    }

    public void setText2(CharSequence text) {
        text2.setText(text);
    }


    public interface OnSelectListener {
        void onSelect(int position);
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
}
