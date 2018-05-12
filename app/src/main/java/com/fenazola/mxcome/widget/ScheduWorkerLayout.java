package com.fenazola.mxcome.widget;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.SchemeEntry;
import com.fenazola.mxcome.fragment.main.designer.DesignerFragment;
import com.fenazola.mxcome.fragment.main.worker.WorkerFragment;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.activity.BaseActivity;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.utils.LogUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 排版项布局
 *
 * @author zm
 */
public class ScheduWorkerLayout extends LinearLayout implements View.OnClickListener {

    private ChoiceItemLayout title_item1;
    private ChoiceItemLayout title_item2;
    private ChoiceItemLayout title_item3;
    private ChoiceItemLayout title_item4;
    private ChoiceItemLayout title_item5;
    private ChoiceItemLayout title_item6;
    private ChoiceItemLayout title_item7;

    private TextView choice_item1;
    private TextView choice_item2;
    private TextView choice_item3;
    private TextView choice_item4;
    private TextView choice_item5;
    private TextView choice_item6;
    private TextView choice_item7;
    private int w_type = 0;

    public ScheduWorkerLayout(Context context) {
        super(context);
        initView();
    }

    public ScheduWorkerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView() {
        inflate(getContext(), R.layout.layout_schedu_worker, this);
        title_item1 = (ChoiceItemLayout) findViewById(R.id.title_item1);
        title_item2 = (ChoiceItemLayout) findViewById(R.id.title_item2);
        title_item3 = (ChoiceItemLayout) findViewById(R.id.title_item3);
        title_item4 = (ChoiceItemLayout) findViewById(R.id.title_item4);
        title_item5 = (ChoiceItemLayout) findViewById(R.id.title_item5);
        title_item6 = (ChoiceItemLayout) findViewById(R.id.title_item6);
        title_item7 = (ChoiceItemLayout) findViewById(R.id.title_item7);

        choice_item1 = (TextView) findViewById(R.id.choice_item1);
        choice_item2 = (TextView) findViewById(R.id.choice_item2);
        choice_item3 = (TextView) findViewById(R.id.choice_item3);
        choice_item4 = (TextView) findViewById(R.id.choice_item4);
        choice_item5 = (TextView) findViewById(R.id.choice_item5);
        choice_item6 = (TextView) findViewById(R.id.choice_item6);
        choice_item7 = (TextView) findViewById(R.id.choice_item7);

        title_item1.setNum("1");
        title_item2.setNum("2");
        title_item3.setNum("3");
        title_item4.setNum("4");
        title_item5.setNum("5");
        title_item6.setNum("6");
        title_item7.setNum("7");

        choice_item1.setOnClickListener(this);
        choice_item2.setOnClickListener(this);
        choice_item3.setOnClickListener(this);
        choice_item4.setOnClickListener(this);
        choice_item5.setOnClickListener(this);
        choice_item6.setOnClickListener(this);
        choice_item7.setOnClickListener(this);

    }

    public void setTitleItem1(CharSequence text) {
        title_item1.setTitle(text);
    }

    public void setTitleItem2(CharSequence text) {
        title_item2.setTitle(text);
    }

    public void setTitleItem3(CharSequence text) {
        title_item3.setTitle(text);
    }

    public void setTitleItem4(CharSequence text) {
        title_item4.setTitle(text);
    }

    public void setTitleItem5(CharSequence text) {
        title_item5.setTitle(text);
    }

    public void setTitleItem6(CharSequence text) {
        title_item6.setTitle(text);
    }

    public void setTitleItem7(CharSequence text) {
        title_item7.setTitle(text);
    }


    public void setChoiceValue1(CharSequence text) {
        choice_item1.setText(text);
    }

    public void setChoiceValue2(CharSequence text) {
        choice_item2.setText(text);
    }

    public void setChoiceValue3(CharSequence text) {
        choice_item3.setText(text);
    }

    public void setChoiceValue4(CharSequence text) {
        choice_item4.setText(text);
    }

    public void setChoiceValue5(CharSequence text) {
        choice_item5.setText(text);
    }

    public void setChoiceValue6(CharSequence text) {
        choice_item6.setText(text);
    }

    public void setChoiceValue7(CharSequence text) {
        choice_item7.setText(text);
    }

    public void setClickEnable(List<SchemeEntry> data) {
        for (SchemeEntry item : data) {
            if (800 == item.getClum_id()) {
                if (item.isEnable()) {
                    title_item1.setBgRes(R.drawable.corner_shape_blue_pj_des);
                    choice_item1.setOnClickListener(this);
                    choice_item1.setTextColor(BaseActivity.getColor(getContext(), R.color.white));
                } else {
                    title_item1.setBgRes(R.drawable.corner_shape_blue_pj_des);
                    choice_item1.setOnClickListener(null);
                    choice_item1.setTextColor(BaseActivity.getColor(getContext(), R.color.worker_title_color));
                }
//            } else if (801 == item.getClum_id()) {
//                if (item.isEnable()) {
//                    title_item2.setBgRes(R.drawable.corner_shape_blue_pj_worker);
//                    choice_item2.setOnClickListener(this);
//                    choice_item2.setTextColor(BaseActivity.getColor(getContext(), R.color.white));
//                } else {
                    title_item2.setBgRes(R.drawable.corner_shape_blue_pj_worker);
                    choice_item2.setOnClickListener(null);
                    choice_item2.setTextColor(BaseActivity.getColor(getContext(), R.color.worker_title_color));
                }
//            } else if (802 == item.getClum_id()) {
//                if (item.isEnable()) {
//                    title_item3.setBgRes(R.drawable.corner_shape_blue_pj_worker);
//                    choice_item3.setOnClickListener(this);
//                    choice_item3.setTextColor(BaseActivity.getColor(getContext(), R.color.white));
//                } else {
                    title_item3.setBgRes(R.drawable.corner_shape_blue_pj_worker);
                    choice_item3.setOnClickListener(null);
                    choice_item3.setTextColor(BaseActivity.getColor(getContext(), R.color.worker_title_color));
//                }
//            } else if (803 == item.getClum_id()) {
//                if (item.isEnable()) {
//                    title_item4.setBgRes(R.drawable.corner_shape_blue_pj_worker);
//                    choice_item4.setOnClickListener(this);
//                    choice_item4.setTextColor(BaseActivity.getColor(getContext(), R.color.white));
//                } else {
                    title_item4.setBgRes(R.drawable.corner_shape_blue_pj_worker);
                    choice_item4.setOnClickListener(null);
                    choice_item4.setTextColor(BaseActivity.getColor(getContext(), R.color.worker_title_color));
//                }
//            } else if (804 == item.getClum_id()) {
//                if (item.isEnable()) {
//                    title_item5.setBgRes(R.drawable.corner_shape_blue_pj_worker);
//                    choice_item5.setOnClickListener(this);
//                    choice_item5.setTextColor(BaseActivity.getColor(getContext(), R.color.white));
//                } else {
                    title_item5.setBgRes(R.drawable.corner_shape_blue_pj_worker);
                    choice_item5.setOnClickListener(null);
                    choice_item5.setTextColor(BaseActivity.getColor(getContext(), R.color.worker_title_color));
//                }
//            } else if (805 == item.getClum_id()) {
//                if (item.isEnable()) {
//                    title_item6.setBgRes(R.drawable.corner_shape_blue_pj_worker);
//                    choice_item6.setOnClickListener(this);
//                    choice_item6.setTextColor(BaseActivity.getColor(getContext(), R.color.white));
//                } else {
                    title_item6.setBgRes(R.drawable.corner_shape_blue_pj_worker);
                    choice_item6.setOnClickListener(null);
                    choice_item6.setTextColor(BaseActivity.getColor(getContext(), R.color.worker_title_color));
//                }
//            } else if (806 == item.getClum_id()) {
//                if (item.isEnable()) {
//                    title_item7.setBgRes(R.drawable.corner_shape_blue_pj_worker);
//                    choice_item7.setOnClickListener(this);
//                    choice_item7.setTextColor(BaseActivity.getColor(getContext(), R.color.white));
//                } else {
                    title_item7.setBgRes(R.drawable.corner_shape_blue_pj_worker);
                    choice_item7.setOnClickListener(null);
                    choice_item7.setTextColor(BaseActivity.getColor(getContext(), R.color.worker_title_color));
//                }
//            }
        }
    }

    ScheduleDialog dialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choice_item1:
                dialog = new ScheduleDialog(getContext(), new ScheduleDialog.OnSelectListener() {
                    @Override
                    public void onSelect(int pos) {
                        dialog.dismiss();
                        if(pos == 0){
                            w_type = 800;
                            Intent intent = new Intent(getContext(), ZFrameActivity.class);
                            intent.putExtra(Constant.key1, w_type);
                            intent.putExtra(ZFrameActivity.CLASS_NAME, DesignerFragment.class.getName());
                            getContext().startActivity(intent);
                        }
                        if(listener != null){
                            listener.onSelect(pos);
                        }
                    }
                });
                dialog.setText1("自选设计");
                dialog.setText2("官方设计");
                dialog.show();
                break;
            case R.id.choice_item2:

                break;
            case R.id.choice_item3:

                break;
            case R.id.choice_item4:

                break;
            case R.id.choice_item5:

                break;
            case R.id.choice_item6:

                break;
            case R.id.choice_item7:

                break;
        }
    }

    public int getWtype() {
        return w_type;
    }

    public ScheduleDialog.OnSelectListener listener;

    public void setOnSelectListener(ScheduleDialog.OnSelectListener listener){
        this.listener = listener;
    }

}

