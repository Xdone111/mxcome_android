package com.fenazola.mxcome.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;

/**
 * 订单用户选择布局
 * @author zm
 *
 */
public class ChoiceItemLayout extends LinearLayout
{

	private TextView num;
	private TextView title;

	public ChoiceItemLayout(Context context) {
		super(context);
		initView();
	}

	public ChoiceItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public ChoiceItemLayout(Context context, AttributeSet attrs, int defStyleAtrr) {
		super(context, attrs, defStyleAtrr);
		initView();
	}

	public void initView() {
		inflate(getContext(), R.layout.layout_chioce_item, this);
		num = (TextView)findViewById(R.id.num);
		title = (TextView)findViewById(R.id.title);
	}

	public ChoiceItemLayout setBgRes(int resId){
		num.setBackgroundResource(resId);
		return this;
	}

	public ChoiceItemLayout setNum(CharSequence text){
		num.setText(text);
		return this;
	}

	public ChoiceItemLayout setTitle(CharSequence text){
		title.setText(text);
		return this;
	}

}

