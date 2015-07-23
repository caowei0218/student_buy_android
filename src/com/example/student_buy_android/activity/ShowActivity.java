package com.example.student_buy_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.student_buy_android.R;
import com.example.student_buy_android.util.SysApplication;

public class ShowActivity extends BaseActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_activity);

		SysApplication.getInstance().addActivity(this);// 将该activity添加到管理类中去。

		init();
	}

	private void init() {
		findViewById();
		adaptation();
		setOnClickListener();
	}

	private void findViewById() {
	}

	private void adaptation() {
	}

	private void setOnClickListener() {
	}

	public void onClick(View v) {
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
		}
	}

	/**
	 * 返回键不退出程序 后台运行
	 * */
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// moveTaskToBack(false);
	// return true;
	// }
	// return super.onKeyDown(keyCode, event);
	// }
}
