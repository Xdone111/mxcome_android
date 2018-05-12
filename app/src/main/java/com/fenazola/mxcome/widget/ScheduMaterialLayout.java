package com.fenazola.mxcome.widget;


import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.SchemeEntry;
import com.fenazola.mxcome.fragment.project.MaterialFragment;
import com.fenazola.mxcome.fragment.project.SmartPackageFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DataCache;
import com.zss.library.activity.BaseActivity;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.utils.LogUtils;

import java.util.List;

/**
 * 排版项布局
 * @author zm
 *
 */
public class ScheduMaterialLayout extends LinearLayout implements View.OnClickListener{

	private int w_type;

	private ChoiceItemLayout title_item1;
	private ChoiceItemLayout title_item2;
	private ChoiceItemLayout title_item3;
	private ChoiceItemLayout title_item4;
	private ChoiceItemLayout title_item5;
	private ChoiceItemLayout title_item6;
	//private ChoiceItemLayout title_item7;

	private TextView choice_item1;
	private TextView choice_item2;
	private TextView choice_item3;
	private TextView choice_item4;
	private TextView choice_item5;
	private TextView choice_item6;
	//private TextView choice_item7;

	private SparseArray<ChoiceItemLayout> sparseTitle = new SparseArray<>();
	private SparseArray<TextView> sparseChoice = new SparseArray<>();

	public ScheduMaterialLayout(Context context) {
		super(context);
		initView();
	}

	public ScheduMaterialLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public void initView() {
		inflate(getContext(), R.layout.layout_schedu_material, this);

		title_item1 = (ChoiceItemLayout)findViewById(R.id.title_item1);
		title_item2 = (ChoiceItemLayout)findViewById(R.id.title_item2);
		title_item3 = (ChoiceItemLayout)findViewById(R.id.title_item3);
		title_item4 = (ChoiceItemLayout)findViewById(R.id.title_item4);
		title_item5 = (ChoiceItemLayout)findViewById(R.id.title_item5);
		title_item6 = (ChoiceItemLayout)findViewById(R.id.title_item6);
		//title_item7 = (ChoiceItemLayout)findViewById(R.id.title_item7);

		choice_item1 = (TextView)findViewById(R.id.choice_item1);
		choice_item2 = (TextView)findViewById(R.id.choice_item2);
		choice_item3 = (TextView)findViewById(R.id.choice_item3);
		choice_item4 = (TextView)findViewById(R.id.choice_item4);
		choice_item5 = (TextView)findViewById(R.id.choice_item5);
		choice_item6 = (TextView)findViewById(R.id.choice_item6);
		//choice_item7 = (TextView)findViewById(R.id.choice_item7);

		sparseTitle.append(1, title_item1);
		sparseTitle.append(2, title_item2);
		sparseTitle.append(3, title_item3);
		sparseTitle.append(4, title_item4);
		sparseTitle.append(5, title_item5);
		sparseTitle.append(6, title_item6);
		//sparseTitle.append(7, title_item7);

		sparseChoice.append(1, choice_item1);
		sparseChoice.append(2, choice_item2);
		sparseChoice.append(3, choice_item3);
		sparseChoice.append(4, choice_item4);
		sparseChoice.append(5, choice_item5);
		sparseChoice.append(6, choice_item6);
		//sparseChoice.append(7, choice_item7);

	}

	public void setChoiceValue1(CharSequence text){
		choice_item1.setText(text);
	}

	public void setChoiceValue2(CharSequence text){
		choice_item2.setText(text);
	}

	public void setChoiceValue3(CharSequence text){
		choice_item3.setText(text);
	}

	public void setChoiceValue4(CharSequence text){
		choice_item4.setText(text);
	}

	public void setChoiceValue5(CharSequence text){
		choice_item5.setText(text);
	}

	public void setChoiceValue6(CharSequence text){
		choice_item6.setText(text);
	}

	//public void setChoiceValue7(CharSequence text){
	//	choice_item7.setText(text);
	//}

	public void setClickEnable(List<SchemeEntry> data){
		for(int i=0; i < sparseTitle.size(); i++){
			ChoiceItemLayout item = sparseTitle.valueAt(i);
			item.setVisibility(INVISIBLE);
		}
		for(int i=0; i < sparseChoice.size(); i++){
			TextView item = sparseChoice.valueAt(i);
			item.setVisibility(INVISIBLE);
		}
		int[] template = getTemplate(DataCache.enableList.size()-1);
		for(int i = 0; i<template.length; i++){
			int key = template[i];
			LogUtils.i("---zss---", "---- key = "+ key);
			ChoiceItemLayout title_item = sparseTitle.get(key);
			TextView choice_item = sparseChoice.get(key);
			SchemeEntry item = DataCache.enableList.get(i);
			title_item.setVisibility(VISIBLE);
			title_item.setNum(String.valueOf(i+1));
			title_item.setBgRes(R.drawable.corner_shape_blue_pj_worker);
			title_item.setTitle(getMaterialPackName(item.getClum_id()));
			choice_item.setVisibility(VISIBLE);
			choice_item.setOnClickListener(this);
			choice_item.setTextColor(BaseActivity.getColor(getContext(), R.color.white));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.choice_item1:
				w_type = 1001;
				Intent intent = new Intent(getContext(), ZFrameActivity.class);
				intent.putExtra(Constant.key1, w_type);
				intent.putExtra(ZFrameActivity.CLASS_NAME, MaterialFragment.class.getName());
				getContext().startActivity(intent);
				break;
			case R.id.choice_item2:
				w_type = 1005;
				intent = new Intent(getContext(), ZFrameActivity.class);
				intent.putExtra(Constant.key1, w_type);
				intent.putExtra(ZFrameActivity.CLASS_NAME, MaterialFragment.class.getName());
				getContext().startActivity(intent);

				break;
			case R.id.choice_item3:
				w_type = 1003;
				intent = new Intent(getContext(), ZFrameActivity.class);
				intent.putExtra(Constant.key1, w_type);
				intent.putExtra(ZFrameActivity.CLASS_NAME, MaterialFragment.class.getName());
				getContext().startActivity(intent);
				break;
			case R.id.choice_item4:
				w_type = 1004;
				intent = new Intent(getContext(), ZFrameActivity.class);
				intent.putExtra(Constant.key1, w_type);
				intent.putExtra(ZFrameActivity.CLASS_NAME, MaterialFragment.class.getName());
				getContext().startActivity(intent);
				break;
			case R.id.choice_item5:
				w_type = 1006;
				intent = new Intent(getContext(), ZFrameActivity.class);
				intent.putExtra(Constant.key1, w_type);
				intent.putExtra(ZFrameActivity.CLASS_NAME, MaterialFragment.class.getName());
				getContext().startActivity(intent);

				break;
			case R.id.choice_item6:
				w_type = 1007;
				intent = new Intent(getContext(), ZFrameActivity.class);
				intent.putExtra(Constant.key1, w_type);
				intent.putExtra(ZFrameActivity.CLASS_NAME, SmartPackageFragment.class.getName());
				getContext().startActivity(intent);
				break;
			case R.id.choice_item7:
				w_type = 1002;
				intent = new Intent(getContext(), ZFrameActivity.class);
				intent.putExtra(Constant.key1, w_type);
				intent.putExtra(ZFrameActivity.CLASS_NAME, MaterialFragment.class.getName());
				getContext().startActivity(intent);
				break;

		}
	}

	public int getWtype(){
		return w_type;
	}

	public int[] getTemplate(int size){
		if(size==1){
			return new int[]{4};
		}else if(size == 2){
			return new int[]{3, 5};
		}else if(size == 3){
			return new int[]{2, 4, 6};
		}else if(size == 4){
			return new int[]{1, 3, 5, 7};
		}else if(size == 5){
			return new int[]{2, 3, 4, 5, 6};
		}else{
			return new int[]{1, 2, 3, 4, 5, 6};
		}
//		else{
//			return new int[]{1, 2, 3, 4, 5, 6, 7};
//		}
	}

	public String getMaterialPackName(int clumid){
//		if (clumid == 1001) {
//			return getContext().getString(R.string.project_schedu_material1);
//		} else if (clumid == 1002) {
//			return getContext().getString(R.string.project_schedu_material2);
//		} else if (clumid == 1003) {
//			return getContext().getString(R.string.project_schedu_material3);
//		} else if (clumid == 1004) {
//			return getContext().getString(R.string.project_schedu_material4);
//		} else if (clumid == 1005) {
//			return getContext().getString(R.string.project_schedu_material5);
//		} else if (clumid == 1006) {
//			return getContext().getString(R.string.project_schedu_material6);
//		} else {
//			return getContext().getString(R.string.project_schedu_material7);
//		}

		if (clumid == 1001) {
			return "基装包";
		} else if (clumid == 1002) {
			return "家具包";
		} else if (clumid == 1003) {
			return "安装包";
		} else if (clumid == 1004) {
			return "软配包";
		} else if (clumid == 1005) {
			return "铺贴包";
		}
//		else if (clumid == 1006) {
//			return getContext().getString(R.string.project_schedu_material6);
//		}
		else {
			return "智能包";
		}
	}
}

