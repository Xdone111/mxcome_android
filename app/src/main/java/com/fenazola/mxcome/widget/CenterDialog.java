package com.fenazola.mxcome.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.utils.DPUtils;
import com.zss.library.widget.CommonTextWidget;


public class CenterDialog extends Dialog {

    private TextView content;

    public CenterDialog(Context context) {
        super(context, R.style.CommonDialog);
        setContentView(R.layout.dialog_center);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = DPUtils.getScreenW(getContext());
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        initView();
    }

    private void initView() {
        content = (TextView) findViewById(R.id.text);
    }

    public void setContent(CharSequence text) {
        content.setText(text);
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
