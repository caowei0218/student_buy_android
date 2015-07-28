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
	private TextView username, nikename, email, description, address, city,
			gender, phoneNumber;

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

		username = (TextView) findViewById(R.id.id);
		nikename = (TextView) findViewById(R.id.nikename);
		email = (TextView) findViewById(R.id.email);
		description = (TextView) findViewById(R.id.description);
		address = (TextView) findViewById(R.id.address);
		city = (TextView) findViewById(R.id.city);
		gender = (TextView) findViewById(R.id.gender);
		phoneNumber = (TextView) findViewById(R.id.phoneNumber);

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
		username.setText(Common.userBean.getUsername());
		nikename.setText(Common.userBean.getNickname());
		email.setText(Common.userBean.getEmail());
		description.setText(Common.userBean.getDescription());
		address.setText(Common.userBean.getAddress());
		city.setText(Common.userBean.getCity());
		gender.setText(Common.userBean.getGender());
		phoneNumber.setText(Common.userBean.getPhoneNumber());
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
			intent.putExtra("nikename", nikename.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_erweima:
			intent = new Intent(MyInfoActivity.this, QRCodeActivity.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_email:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("email", email.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_description:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("description", description.getText().toString()
					.trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_address:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("address", address.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_city:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("city", city.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_gender:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("gender", gender.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_phoneNumber:
			intent = new Intent(MyInfoActivity.this, UpdateInfoActivity.class);
			intent.putExtra("phoneNumber", phoneNumber.getText().toString()
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
