package com.example.student_buy_android.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

public class TimeCountUtil extends CountDownTimer {
	private Activity mActivity;
	private Button btn;// ��ť
	private TextView tv;

	// ��������췽������Ҫ��������������һ����Activity��һ�����ܵ�ʱ��millisInFuture��һ����countDownInterval��Ȼ����������ĸ���ť��������ǣ��Ͱ������ť�������Ϳ�����
	public TimeCountUtil(Activity mActivity, long millisInFuture,
			long countDownInterval, Button btn, TextView tv) {
		super(millisInFuture, countDownInterval);
		this.mActivity = mActivity;
		this.btn = btn;
		this.tv = tv;
	}

	@SuppressLint("NewApi")
	@Override
	public void onTick(long millisUntilFinished) {
		btn.setClickable(false);// ���ò��ܵ��
		tv.setText(millisUntilFinished / 1000 + "��");// ���õ���ʱʱ��

		// ���ð�ťΪ��ɫ����ʱ�ǲ��ܵ����
		btn.setBackgroundColor(Color.parseColor("#cccccc"));
		// Spannable span = new SpannableString(btn.getText().toString());//
		// ��ȡ��ť������
		// span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2,
		// Spannable.SPAN_INCLUSIVE_EXCLUSIVE);// ������ʱʱ����ʾΪ��ɫ
		btn.setText("�ѷ���");

	}

	@SuppressLint("NewApi")
	@Override
	public void onFinish() {
		btn.setText("���»�ȡ");
		tv.setText("60");
		btn.setClickable(true);// ���»�õ��
		btn.setBackgroundColor(Color.parseColor("#f33400"));// ��ԭ����ɫ

	}

}