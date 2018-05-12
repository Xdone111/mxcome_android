package com.fenazola.mxcome.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 按钮状态监控
 */
public class InputManager {

	private List<SoftReference<TextView>> mInputs;
	private List<SoftReference<TextView>> mSubmits;
	private OnCheckListener listener;

	public static void checkEmptyListener(List<TextView> inputs, List<TextView> submits, OnCheckListener listener){

		new InputManager(inputs, submits, listener);
	}

	public InputManager(List<TextView> inputs, List<TextView> submit, OnCheckListener listener) {
		super();
		this.listener = listener;
		mInputs = new ArrayList<SoftReference<TextView>>();
		for (TextView mTextView : inputs) {
			mInputs.add(new SoftReference<TextView>(mTextView));
		}
		mSubmits = new ArrayList<SoftReference<TextView>>();
		for (TextView mTextView : submit) {
			mSubmits.add(new SoftReference<TextView>(mTextView));
		}
		for (int i = 0; i < inputs.size(); i++) {
			TextWatcher watcher = new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					innerCheck();
				}
			};
			inputs.get(i).addTextChangedListener(watcher);
		}
		innerCheck();
	}
	
	//添加输入框监听
	public void addTextViews(TextView[] TextViews){
		for (TextView mTextView : TextViews) {
			mInputs.add(new SoftReference<TextView>(mTextView));
		}
		for (int i = 0; i < TextViews.length; i++) {
			TextWatcher watcher = new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
					innerCheck();
				}
			};
			TextViews[i].addTextChangedListener(watcher);
		}
		innerCheck();
	}

	public void addTextView(TextView mTextView){
		mInputs.add(new SoftReference<TextView>(mTextView));
		innerCheck();
	}
	
	//移除输入框监听
	public void removeTextViews(TextView[] TextViews){
		for (TextView mTextView : TextViews) {
			for (int i = 0; i < mInputs.size(); i++) {
				if (mInputs.get(i).get().equals(mTextView)) {
					mInputs.remove(i);
				}
			}
		}
		innerCheck();
	}
	/**
	 * 检查格式
	 * */
	public void innerCheck() {
		if (isInputEmpty()) {
			setTextView(false);
		} else {
			if(listener != null){
				setTextView(listener.onCheck());
			}
		}
	}

	private void setTextView(boolean b) {
		for (SoftReference<TextView> softTextView : mSubmits) {
			TextView TextView = softTextView.get();
			if (TextView != null) {
				TextView.setEnabled(b);
			}
		}

	}

	/**
	 * 如果TextView为空返回true,否则返回false
	 * */
	private boolean isInputEmpty() {
		for (SoftReference<TextView> softTextView : mInputs) {
			TextView TextView = softTextView.get();
			if (TextView != null) {
				if (TextUtils.isEmpty(TextView.getText())) {
					return true;
				}
			}
		}
		return false;
	}

	public interface OnCheckListener{
		public boolean onCheck();
	}
}
