package com.example.student_buy_android.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.Toast;

import com.example.student_buy_android.R;
import com.example.student_buy_android.dialog.CustomProgressDialog;

public class BaseActivity extends Activity {

	private MyHandler handler = new MyHandler();
	CustomProgressDialog dialog = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ����ȫ��
	}

	protected void handleOtherMessage(int flag) {
	}

	public void sendMessage(int flag) {
		handler.sendEmptyMessage(flag);
	}

	/**
	 * ��ʼ�ȴ�����
	 * */
	public void beginWaitDialog(String msg, boolean boo) {
		if (boo) {
			dialog = new CustomProgressDialog(this, msg, R.anim.frame);
			dialog.show();
		}
	}

	/**
	 * �����ȴ�����
	 * */
	public void endWaitDialog(boolean boo) {
		if (boo) {
			dialog.dismiss();
		}
	}

	public void sendMessageDely(int flag, long delayMillis) {
		handler.sendEmptyMessageDelayed(flag, delayMillis);
	}

	public void showToast(String toast_message) {
		handler.toast_message = toast_message;
		sendMessage(MyHandler.SHOW_STR_TOAST);
	}

	public void showToast(int res) {
		handler.toast_res = res;
		sendMessage(MyHandler.SHOW_RES_TOAST);
	}

	@SuppressLint("HandlerLeak")
	private class MyHandler extends Handler {
		public static final int SHOW_STR_TOAST = 0;
		public static final int SHOW_RES_TOAST = 1;

		private String toast_message = null;
		private int toast_res;

		@SuppressLint("ShowToast")
		public void handleMessage(Message msg) {
			// �����жϵ�ǰ�߳�״̬�����û���жϣ���һֱִ��
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				case SHOW_STR_TOAST:
					Toast.makeText(getBaseContext(), toast_message, 1).show();
					break;
				case SHOW_RES_TOAST:
					Toast.makeText(getBaseContext(), toast_res, 1).show();
					break;
				default:
					handleOtherMessage(msg.what);
				}
			}
		}
	}

}
