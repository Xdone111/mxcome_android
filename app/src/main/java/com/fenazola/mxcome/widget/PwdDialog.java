package com.fenazola.mxcome.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.utils.DPUtils;

/**
 * 密码对话框
 *
 * @author zm
 */
public class PwdDialog extends Dialog implements View.OnClickListener{

    private ImageView img_cancel;
    private TextView tv_title;
    private TextView tv_pass1, tv_pass2, tv_pass3, tv_pass4, tv_pass5, tv_pass6;
    private TextView tv_forget_pwd;
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private ImageButton btn_del, btn_sure;
    private OnPwdListener listener;
    private String mStrPwd = "";
    private boolean mPwdMode = true;

    public PwdDialog(Context context, OnPwdListener listener) {
       this(context, R.style.CommonDialog, listener);
    }

    public PwdDialog(Context context, int style, OnPwdListener listener) {
        super(context, style);
        setContentView(R.layout.dialog_pay_pwd);
        this.listener = listener;
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = DPUtils.getScreenW(getContext());
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
        initView();
    }

    private void initView() {
        img_cancel = (ImageView) findViewById(R.id.img_cancel);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_pass1 = (TextView) findViewById(R.id.tv_pass1);
        tv_pass2 = (TextView) findViewById(R.id.tv_pass2);
        tv_pass3 = (TextView) findViewById(R.id.tv_pass3);
        tv_pass4 = (TextView) findViewById(R.id.tv_pass4);
        tv_pass5 = (TextView) findViewById(R.id.tv_pass5);
        tv_pass6 = (TextView) findViewById(R.id.tv_pass6);
        tv_forget_pwd = (TextView) findViewById(R.id.tv_forget_pwd);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn_del = (ImageButton) findViewById(R.id.btn_del);
        btn_sure = (ImageButton) findViewById(R.id.btn_sure);

        img_cancel.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_cancel:
                dismiss();
                break;
            case R.id.tv_forget_pwd:

                break;
            case R.id.btn0:
                setPwdText("0");
                break;
            case R.id.btn1:
                setPwdText("1");
                break;
            case R.id.btn2:
                setPwdText("2");
                break;
            case R.id.btn3:
                setPwdText("3");
                break;
            case R.id.btn4:
                setPwdText("4");
                break;
            case R.id.btn5:
                setPwdText("5");
                break;
            case R.id.btn6:
                setPwdText("6");
                break;
            case R.id.btn7:
                setPwdText("7");
                break;
            case R.id.btn8:
                setPwdText("8");
                break;
            case R.id.btn9:
                setPwdText("9");
                break;
            case R.id.btn_del:
                delPwdText();
                break;
            case R.id.btn_sure:
                dismiss();
                break;
        }
    }

    private void setPwdText(String value){
        if(mPwdMode){
            if(mStrPwd.length() == 6){
                return;
            }
            if(mStrPwd.length() == 0){
                tv_pass1.setText(value);
            }else if(mStrPwd.length() == 1){
                tv_pass2.setText(value);
            }else if(mStrPwd.length() == 2){
                tv_pass3.setText(value);
            }else if(mStrPwd.length() == 3){
                tv_pass4.setText(value);
            }else if(mStrPwd.length() == 4){
                tv_pass5.setText(value);
            }else if(mStrPwd.length() == 5){
                tv_pass6.setText(value);
            }
        }
        mStrPwd += value;
        if(listener != null){
            listener.onTextChanged(mStrPwd);
        }
    }

    private void delPwdText(){
        if(mPwdMode){
            if(mStrPwd.length()==0){
                return;
            }if(mStrPwd.length() == 1){
                tv_pass1.setText("");
                mStrPwd = "";
            }else if(mStrPwd.length() == 2){
                tv_pass2.setText("");
                mStrPwd = mStrPwd.substring(0, mStrPwd.length()-1);
            }else if(mStrPwd.length() == 3){
                tv_pass3.setText("");
                mStrPwd = mStrPwd.substring(0, mStrPwd.length()-1);
            }else if(mStrPwd.length() == 4){
                tv_pass4.setText("");
                mStrPwd = mStrPwd.substring(0, mStrPwd.length()-1);
            }else if(mStrPwd.length() == 5){
                tv_pass5.setText("");
                mStrPwd = mStrPwd.substring(0, mStrPwd.length()-1);
            }else if(mStrPwd.length() == 6){
                tv_pass6.setText("");
                mStrPwd = mStrPwd.substring(0, mStrPwd.length()-1);
            }
            if(listener != null){
                listener.onTextChanged(mStrPwd);
            }
        }else{
            if(mStrPwd.length()<=1){
                mStrPwd = "";
            }else{
                mStrPwd = mStrPwd.substring(0, mStrPwd.length()-1);
            }
            if(listener != null){
                listener.onTextChanged(mStrPwd);
            }
        }
    }

    public void setDisplayTopEnable(boolean enable){
        mPwdMode = enable;
        if(enable){
            findViewById(R.id.top_layout).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.top_layout).setVisibility(View.GONE);
        }
    }

    public void setTitle(CharSequence text){
        tv_title.setText(text);
    }

    public interface OnPwdListener {
         void onTextChanged(String mStrPwd);
         void refreshActivity();
    }

    /**
     * 显示对话框
     */
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
        }
    }

    public void dismiss() {
        try {
            listener.refreshActivity();
            super.dismiss();
        } catch (Exception e) {
        }
    }


}