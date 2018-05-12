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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.BankCardEntry;
import com.fenazola.mxcome.fragment.main.demand.DemandDesign;
import com.fenazola.mxcome.fragment.me.account.ResetPayPasswordFragment;
import com.fenazola.mxcome.fragment.me.account.ResetPaymentPasswordFragment;
import com.fenazola.mxcome.fragment.sercentre.FaqTermsFragment;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.PayDialog;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.StringUtils;
import com.zss.library.widget.CommonDialog;
import com.zss.library.widget.CommonTextWidget;
import com.zss.library.widget.CommonWhiteDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by XDONE on 2017/4/5.
 * 转到银行卡
 */

public class RollOutBankFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout ll_bank_select;
    private EditText editText;
    private ImageView clear;
    private TextView all;
    private TextView content;
    private TextView reason;
    private TextView way;
    private RadioGroup radioGroup;
    private TextView explain1;
    private TextView explain2;
    private TextView confirm;
    private ImageView ivBankcard;
    private TextView tvBankName;
    private int index=0;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_bank_rollout;
    }

    @Override
    public void initView() {
        super.initView();
        ll_bank_select = (LinearLayout) findViewById(R.id.ll_bank_select);
        editText = (EditText) findViewById(R.id.editText);
        clear = (ImageView) findViewById(R.id.iv_clear);
        all = (TextView) findViewById(R.id.tv_all);
        content = (TextView) findViewById(R.id.tv_content);
        reason = (TextView) findViewById(R.id.tv_reason);
        way = (TextView) findViewById(R.id.tv_way);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        explain1 = (TextView) findViewById(R.id.tv_explain1);
        explain2 = (TextView) findViewById(R.id.tv_explain2);
        confirm = (TextView) findViewById(R.id.confirm);
        ivBankcard = (ImageView) findViewById(R.id.iv_bankcard);
        tvBankName = (TextView) findViewById(R.id.tv_bank_name);
        way.setOnClickListener(this);
        all.setOnClickListener(this);
        confirm.setOnClickListener(this);
        Utils.setEditTextSize(getActivity(), editText, "可转出到卡2000.00");

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        clear.setOnClickListener(this);
        reason.setOnClickListener(this);
        ll_bank_select.setOnClickListener(this);

        EventBus.getDefault().register(this);

        String numStr1 = getString(R.string.account_num, "98000");
        int color = getColor(R.color.colorRed);
        content.setText(StringUtils.parse(numStr1, 1, numStr1.length() - 16, color));

        String numStr2 = getString(R.string.text_explain1, "2小时");
        explain1.setText(StringUtils.parse(numStr2, 2, numStr2.length() - 26, color));

        String numStr3 = getString(R.string.text_explain2, "01月26日");
        explain2.setText(StringUtils.parse(numStr3, 2, numStr3.length() - 35, color));


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
                    clear.setVisibility(View.GONE);
                } else {
                    confirm.setTextColor(getColor(R.color.white));
                    clear.setVisibility(View.VISIBLE);
                    confirm.setBackgroundResource(R.drawable.rect_shape_blue_btn);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.confirm:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, ResetPayPasswordFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.tv_all:
                editText.setText("2000");

                break;
            case R.id.tv_way:
                intent= new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqTermsFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.iv_clear:
                editText.setText(null);
                break;
            case R.id.tv_reason:
//               final CommonDialog dialog = new CommonDialog(getActivity());
//                dialog.setTitle("提示");
//                View view = getLayoutInflater(R.layout.layout_me_pay_dialog);
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.setMiddleView(view);
//                dialog.setOnClickConfirmListener("我知道了", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//                });
//                dialog.show();
                MyPayDialog dialog = new MyPayDialog(getActivity());
                dialog.show();
                break;
            case R.id.ll_bank_select:
                SelectBankCardFragment fragment = new SelectBankCardFragment();

                addFragment(fragment);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BankCardEntry event) {
        LogUtils.e("xdone------", "event---->" + event);
        tvBankName.setText(event.getName() + " " + event.getNumber());
        ivBankcard.setImageResource(event.getIcon());
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
