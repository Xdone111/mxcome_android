package com.fenazola.mxcome.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.project.Projectwheel;
import com.zss.library.utils.DPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/7/17.
 */

public class PayStyleDialog extends Dialog implements View.OnClickListener {

    private LinearLayout first_ll;
    private LinearLayout second_ll;
    private LinearLayout layout_1;
    private LinearLayout layout_2;

    private SelectPayType selectPayType = SelectPayType.WAN_MEI_HE;
    private TextSeekbar seekBar;
    private TextView lineBg1;
    private TextView lineBg2;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private RadioGroupLayout radio_layout;
    private LinearLayout pay_style;
    private LinearLayout pay_style1;
    private LinearLayout pay_style2;
    private LinearLayout pay_style3;
    MyListener listener;

    private TextView noTv;
    private TextView yesTv;
    private ImageView ivReduce;
    private ImageView ivAdd;
    private TextView tvNumber1;
    private TextView tvNumber2;
    List<Integer> datas1 = new ArrayList<>();
    private int index = 0;
    private TextView text1;
    private TextView once_pay_text;


    public enum SelectPayType {
        ME_BAO, WAN_MEI_HE;
    }

    public PayStyleDialog(@NonNull Context context, MyListener listener) {
        super(context, R.style.CommonDialog);
        setContentView(R.layout.layout_schedu_rate1);
        this.listener = listener;
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = DPUtils.getScreenW(getContext());
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        initView();
        initChangView();
    }

    private void initView() {
        first_ll = (LinearLayout) findViewById(R.id.first_ll);
        second_ll = (LinearLayout) findViewById(R.id.second_ll);
        layout_1 = (LinearLayout) findViewById(R.id.layout_1);
        layout_2 = (LinearLayout) findViewById(R.id.layout_2);
        seekBar = (TextSeekbar) findViewById(R.id.seekBar);
        lineBg1 = (TextView) findViewById(R.id.line_bg1);
        lineBg2 = (TextView) findViewById(R.id.line_bg2);

        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);

        noTv = (TextView) findViewById(R.id.pop_cancel);
        yesTv = (TextView) findViewById(R.id.pop_sure);
        noTv.setOnClickListener(this);
        yesTv.setOnClickListener(this);

        radio_layout = (RadioGroupLayout) findViewById(R.id.radio_layout);
        pay_style = (LinearLayout) findViewById(R.id.pay_style);
        pay_style1 = (LinearLayout) findViewById(R.id.pay_style1);
        pay_style2 = (LinearLayout) findViewById(R.id.pay_style2);
        pay_style3 = (LinearLayout) findViewById(R.id.pay_style3);

        ivReduce = (ImageView) findViewById(R.id.iv_reduce);
        ivAdd = (ImageView) findViewById(R.id.iv_add);
        tvNumber1 = (TextView) findViewById(R.id.tv_number1);
        tvNumber2 = (TextView) findViewById(R.id.tv_number2);

        text1 = (TextView) findViewById(R.id.text1);
        once_pay_text = (TextView) findViewById(R.id.once_pay_text);


        datas1.add(30);
        datas1.add(40);
        datas1.add(50);
        datas1.add(60);
        datas1.add(70);
        datas1.add(80);
        datas1.add(90);

        tvNumber1.setText(datas1.get(1) + "%");
        tvNumber2.setText((90 - datas1.get(1)) + "%");

        first_ll.setOnClickListener(this);
        second_ll.setOnClickListener(this);

        text1.setText(Html.fromHtml("一次性付 100%<font color='#4BB7FD'>" + " 2017年9.8折" + "</font>"));
        once_pay_text.setText(Html.fromHtml("您将享受<font color='#ff6666'>" + " 9.8折 " + "</font>的至尊优惠!"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_ll:
                selectPayType = SelectPayType.WAN_MEI_HE;
                lineBg1.setBackgroundColor(getContext().getResources().getColor(R.color.colorYellow));
                lineBg2.setBackgroundColor(getContext().getResources().getColor(R.color.colorBlue));
                initChangView();
                break;
            case R.id.second_ll:
                selectPayType = SelectPayType.ME_BAO;
                lineBg1.setBackgroundColor(getContext().getResources().getColor(R.color.colorBlue));
                lineBg2.setBackgroundColor(getContext().getResources().getColor(R.color.colorYellow));
                initChangView();
                break;
            case R.id.pop_sure:
                listener.buyOrder("");
                dismiss();
                break;
            case R.id.pop_cancel:
                dismiss();
                break;
        }
    }

    public void initChangView() {
        if (selectPayType == SelectPayType.WAN_MEI_HE) {
            //展示第一项页面
            layout_1.setVisibility(View.VISIBLE);
            layout_2.setVisibility(View.GONE);

            seekBar.setSeekBarColor(getContext().getResources().getColor(R.color.colorBlue),
                    getContext().getResources().getColor(R.color.msg_light_grey),
                    getContext().getResources().getColor(R.color.colorBlue));
            final List<String> datas = new ArrayList<>();
            datas.add("40%");
            datas.add("60%");
            datas.add("80%");
            datas.add("100%");
            seekBar.setSection(2);
            seekBar.setDatas(datas);

            radio_layout.setOnCheckedChangeListener(new RadioGroupLayout.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroupLayout group, int checkedId) {

                    switch (checkedId) {
                        case R.id.radio1:
                            initBottomView();
                            pay_style.setVisibility(View.GONE);
                            pay_style1.setVisibility(View.VISIBLE);
                            pay_style2.setVisibility(View.GONE);
                            pay_style3.setVisibility(View.GONE);
                            ivReduce.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    tvNumber1.setText(Integer.parseInt(tvNumber1.getText().toString()) - 10 + "");
//                                    tvNumber2.setText(Integer.parseInt(tvNumber2.getText().toString()) + 10 + "");
                                    if (index == 0) {
                                        return;
                                    }
                                    index -= 1;
                                    tvNumber1.setText(datas1.get(index) + "%");
                                    tvNumber2.setText((90 - datas1.get(index)) + "%");
                                }
                            });
                            ivAdd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    tvNumber1.setText(Integer.parseInt(tvNumber1.getText().toString()) + 10 + "");
//                                    tvNumber2.setText(Integer.parseInt(tvNumber2.getText().toString()) - 10 + "");
                                    if (index == datas1.size() - 1) {
                                        return;
                                    }
                                    index += 1;
                                    tvNumber1.setText(datas1.get(index) + "%");
                                    tvNumber2.setText((90 - datas1.get(index)) + "%");
                                }
                            });

                            break;
                        case R.id.radio2:
                            initBottomView();
                            pay_style.setVisibility(View.GONE);
                            pay_style1.setVisibility(View.GONE);
                            pay_style2.setVisibility(View.VISIBLE);
                            pay_style3.setVisibility(View.GONE);
                            break;
                        case R.id.radio3:
                            initBottomView();
                            pay_style.setVisibility(View.GONE);
                            pay_style1.setVisibility(View.GONE);
                            pay_style2.setVisibility(View.GONE);
                            pay_style3.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            });
        } else {
            //展示第二项页面
            layout_1.setVisibility(View.GONE);
            layout_2.setVisibility(View.VISIBLE);
        }
    }

    private void initBottomView() {
        pay_style.setVisibility(View.VISIBLE);
        pay_style1.setVisibility(View.GONE);
        pay_style2.setVisibility(View.GONE);
        pay_style3.setVisibility(View.GONE);

    }

    public interface MyListener {

        public void buyOrder(String text);

    }

}
