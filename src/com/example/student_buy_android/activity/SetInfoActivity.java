package com.example.student_buy_android.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.example.student_buy_android.R;
import com.example.student_buy_android.util.AppInfo;
import com.example.student_buy_android.util.UpdateManager;
import com.example.student_buy_android.webservice.GetNewVersionWebservice;

public class SetInfoActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout rl_account_security, rl_versionName, rl_about,
			rl_exit;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_info_layout);

		init();
	}

	private void init() {
		rl_account_security = (RelativeLayout) findViewById(R.id.rl_account_security);
		rl_versionName = (RelativeLayout) findViewById(R.id.rl_versionName);
		rl_about = (RelativeLayout) findViewById(R.id.rl_about);
		rl_exit = (RelativeLayout) findViewById(R.id.rl_exit);

		rl_account_security.setOnClickListener(this);
		rl_versionName.setOnClickListener(this);
		rl_about.setOnClickListener(this);
		rl_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_account_security:
			break;
		case R.id.rl_versionName:
			// 检查版本更新
			GetNewVersionWebservice getVersion = new GetNewVersionWebservice(
					SetInfoActivity.this, this);
			getVersion.execute();
			break;
		case R.id.rl_about:
			break;
		case R.id.rl_exit:
			break;
		default:
			break;
		}
	}

	/**
	 * 检查更新版本
	 * */
	public void refresh(String newVersion, String link, String fileName) {
		if (!AppInfo.getAppVersionName(SetInfoActivity.this).equals(newVersion)) {
			UpdateManager updateManager = new UpdateManager(
					SetInfoActivity.this, link, fileName);
			updateManager.checkUpdateInfo();

		} else {
			showToast("已经是最新版本！");
		}
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
