package com.example.student_buy_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student_buy_android.R;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.util.SysApplication;

public class MyInfoActivity extends BaseActivity implements OnClickListener {

	private RelativeLayout rl_avatar, rl_nikename, rl_erweima, rl_email,
			rl_description, rl_address, rl_city, rl_gender, rl_phoneNumber;
	private TextView tv_username, tv_nikename, tv_email, tv_description,
			tv_address, tv_city, tv_gender, tv_phoneNumber;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_info_layout);
		SysApplication.getInstance().addActivity(this);// 将该activity添加到管理类中去。

		init();
		updateData();
	}

	private void init() {
		rl_avatar = (RelativeLayout) findViewById(R.id.rl_avatar);
		rl_nikename = (RelativeLayout) findViewById(R.id.rl_nikename);
		rl_erweima = (RelativeLayout) findViewById(R.id.rl_erweima);
		rl_email = (RelativeLayout) findViewById(R.id.rl_email);
		rl_description = (RelativeLayout) findViewById(R.id.rl_description);
		rl_address = (RelativeLayout) findViewById(R.id.rl_address);
		rl_city = (RelativeLayout) findViewById(R.id.rl_city);
		rl_gender = (RelativeLayout) findViewById(R.id.rl_gender);
		rl_phoneNumber = (RelativeLayout) findViewById(R.id.rl_phoneNumber);

		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_nikename = (TextView) findViewById(R.id.tv_nikename);
		tv_email = (TextView) findViewById(R.id.tv_email);
		tv_description = (TextView) findViewById(R.id.tv_description);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_gender = (TextView) findViewById(R.id.tv_gender);
		tv_phoneNumber = (TextView) findViewById(R.id.tv_phoneNumber);

		rl_avatar.setOnClickListener(this);
		rl_nikename.setOnClickListener(this);
		rl_erweima.setOnClickListener(this);
		rl_email.setOnClickListener(this);
		rl_description.setOnClickListener(this);
		rl_address.setOnClickListener(this);
		rl_city.setOnClickListener(this);
		rl_gender.setOnClickListener(this);
		rl_phoneNumber.setOnClickListener(this);
	}

	/**
	 * 修改页面数据
	 * */
	private void updateData() {
		tv_username.setText(Common.userBean.getUsername());
		tv_nikename.setText(Common.userBean.getNickname());
		tv_email.setText(Common.userBean.getEmail());
		tv_description.setText(Common.userBean.getDescription());
		tv_address.setText(Common.userBean.getAddress());
		tv_city.setText(Common.userBean.getCity());
		tv_gender.setText(Common.userBean.getGender());
		tv_phoneNumber.setText(Common.userBean.getPhoneNumber());
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.rl_avatar:
			Toast.makeText(this, "待开发", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_nikename:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("nikename", tv_nikename.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			finish();
			break;
		case R.id.rl_erweima:
			intent = new Intent(MyInfoActivity.this, QRCodeActivity.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_email:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("email", tv_email.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_description:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("description", tv_description.getText().toString()
					.trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_address:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("address", tv_address.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_city:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("city", tv_city.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_gender:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("gender", tv_gender.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_phoneNumber:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("phoneNumber", tv_phoneNumber.getText().toString()
					.trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		default:
			break;
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
