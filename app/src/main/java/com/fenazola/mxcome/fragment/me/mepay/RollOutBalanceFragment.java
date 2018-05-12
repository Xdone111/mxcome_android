package com.fenazola.mxcome.fragment.me.mepay;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.LoginFragment;
import com.fenazola.mxcome.fragment.me.account.InputBankCardInfoFragment;
import com.fenazola.mxcome.fragment.me.account.ResetPaymentPasswordFragment;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.PayDialog;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.LogUtils;

/**
 * Created by XDONE on 2017/4/5.
 * 转到账户
 */

public class RollOutBalanceFragment extends BaseFragment implements View.OnClickListener {


    private TextView confirm;
    private EditText editText;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_balance_rollout;
    }

    @Override
    public void initView() {
        super.initView();
        confirm = (TextView) findViewById(R.id.confirm);
        editText = (EditText) findViewById(R.id.editText);
        confirm.setOnClickListener(this);
        Utils.setEditTextSize(getActivity(), editText, "可转出到账户2000.00元");

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    confirm.setTextColor(getColor(R.color.colorGrey));
                    confirm.setBackgroundResource(R.drawable.rect_shape_grey_btn);
                } else {
                    confirm.setTextColor(getColor(R.color.white));
                    confirm.setBackgroundResource(R.drawable.rect_shape_blue_btn);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, InputBankCardInfoFragment.class.getName());
                startActivity(intent);
                break;

        }
    }


}
