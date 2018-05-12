package com.fenazola.mxcome.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 短信验证码倒计时工具
 * @author zm
 *
 */
public class SmsSendHelper extends CountDownTimer {

	private TextView mTextView;

	public SmsSendHelper(TextView view, long millisInFuture, long countDownInterval, final ICallback callback) {
		super(millisInFuture, countDownInterval);
		this.mTextView = view;
		this.mTextView.setText("获取验证码");
		this.mTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isStart = callback.onStartTimer();
				if(isStart){
					mTextView.setEnabled(false);
					startCountTime();
				}
			}
		});
	}
	
	public void startCountTime(){
		super.start();
		mTextView.setEnabled(false);
	}

	@Override
	public void onFinish() {
		cancel();
		mTextView.setEnabled(true);
		mTextView.setText("重新发送");
	}

	@Override
	public void onTick(long millisUntilFinished) {
		mTextView.setText(millisUntilFinished / 1000 + "秒");
	}
	
	public interface ICallback{
		public boolean onStartTimer();
	}

}
