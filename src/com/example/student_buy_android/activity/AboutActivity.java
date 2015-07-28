package com.example.student_buy_android.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.util.AppInfo;
import com.example.student_buy_android.util.SysApplication;

public class AboutActivity extends BaseActivity {

	private TextView version;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_layout);
		SysApplication.getInstance().addActivity(this);// 将该activity添加到管理类中去。

		init();

		version.setText(AppInfo.getAppVersionName(this));
	}

	private void init() {
		version = (TextView) findViewById(R.id.version);
	}

	/**
	 * 返回
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			moveTaskToBack(false);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
