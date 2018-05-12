package com.fenazola.mxcome.widget;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.main.worker.WorkerFragment;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.activity.BaseActivity;
import com.zss.library.activity.ZFrameActivity;

/**
 * 设计上传
 * @author zm
 *
 */
public class ScheduUploadLayout extends LinearLayout{

	private ChoiceItemLayout title_item1;
	private ChoiceItemLayout title_item2;
	private ChoiceItemLayout title_item3;
	private TextView choice_item1;
	private TextView choice_item2;
	private TextView choice_item3;
	private TextView tips1;
	private TextView tips2;
	private TextView tips3;

	public ScheduUploadLayout(Context context) {
		super(context);
		initView();
	}

	public ScheduUploadLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public void initView() {
		inflate(getContext(), R.layout.layout_schedu_demand, this);

		title_item1 = (ChoiceItemLayout)findViewById(R.id.title_item1);
		title_item2 = (ChoiceItemLayout)findViewById(R.id.title_item2);
		title_item3 = (ChoiceItemLayout)findViewById(R.id.title_item3);

		choice_item1 = (TextView)findViewById(R.id.choice_item1);
		choice_item2 = (TextView)findViewById(R.id.choice_item2);
		choice_item3 = (TextView)findViewById(R.id.choice_item3);

		tips1 = (TextView)findViewById(R.id.tips1);
		tips2 = (TextView)findViewById(R.id.tips2);
		tips3 = (TextView)findViewById(R.id.tips3);

		title_item1.setNum("1");
		title_item2.setNum("2");
		title_item3.setNum("3");

		title_item1.setTitle("上传平面户型图");
		title_item2.setTitle("上传设计效果图");
		title_item3.setTitle("上传工程施工图");

	}

	public void setTitleItem1(CharSequence text){
		title_item1.setTitle(text);
	}

	public void setTitleItem2(CharSequence text){
		title_item2.setTitle(text);
	}

	public void setTitleItem3(CharSequence text){
		title_item3.setTitle(text);
	}


	public void setUploadSuccess1(){
		tips1.setText("已上传");
		tips1.setTextColor(BaseActivity.getColor(getContext(), R.color.colorBlue));
		choice_item1.setCompoundDrawablesWithIntrinsicBounds(null, null, ActivityCompat.getDrawable(getContext(), R.mipmap.schedu_upload_success), null);
	}

	public void setUploadSuccess2(){
		tips2.setText("已上传");
		tips2.setTextColor(BaseActivity.getColor(getContext(), R.color.colorBlue));
		choice_item2.setCompoundDrawablesWithIntrinsicBounds(null, null, ActivityCompat.getDrawable(getContext(), R.mipmap.schedu_upload_success), null);

	}

	public void setUploadSuccess3(){
		tips3.setText("已上传");
		tips3.setTextColor(BaseActivity.getColor(getContext(), R.color.colorBlue));
		choice_item3.setCompoundDrawablesWithIntrinsicBounds(null, null, ActivityCompat.getDrawable(getContext(), R.mipmap.schedu_upload_success), null);

	}


	public void setOnClickChoice1Listener(OnClickListener listener){
		title_item1.setOnClickListener(listener);
	}

	public void setOnClickChoice2Listener(OnClickListener listener){
		title_item2.setOnClickListener(listener);
	}

	public void setOnClickChoice3Listener(OnClickListener listener){
		title_item3.setOnClickListener(listener);
	}

}

