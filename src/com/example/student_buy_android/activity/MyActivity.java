package com.example.student_buy_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.webservice.GetMyInfoWebservice;

public class MyActivity extends BaseActivity implements OnClickListener {

	private TextView id, nikename, email, description, address, city, gender,
			phoneNumber;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_info_layout);

		init();
		GetMyInfoWebservice getMyInfoWebservice = new GetMyInfoWebservice(
				MyActivity.this, this);
		getMyInfoWebservice.execute();
	}

	private void init() {
		findViewById();
		setOnClickListener();
	}

	private void setOnClickListener() {
		nikename.setOnClickListener(this);
		email.setOnClickListener(this);
		description.setOnClickListener(this);
		address.setOnClickListener(this);
		city.setOnClickListener(this);
		gender.setOnClickListener(this);
		phoneNumber.setOnClickListener(this);
	}

	private void findViewById() {
		id = (TextView) findViewById(R.id.id);
		nikename = (TextView) findViewById(R.id.nikename);
		email = (TextView) findViewById(R.id.email);
		description = (TextView) findViewById(R.id.description);
		address = (TextView) findViewById(R.id.address);
		city = (TextView) findViewById(R.id.city);
		gender = (TextView) findViewById(R.id.gender);
		phoneNumber = (TextView) findViewById(R.id.phoneNumber);
	}

	@Override
	public void onClick(View v) {
		Intent intent;

		switch (v.getId()) {
		case R.id.nikename:
			intent = new Intent(MyActivity.this, MyActivity.class);
			startActivity(intent);
			break;
		case R.id.email:
			break;
		case R.id.description:
			break;
		case R.id.address:
			break;
		case R.id.city:
			break;
		case R.id.gender:
			break;
		case R.id.phoneNumber:
			break;
		}
	}

	/**
	 * 修改页面数据
	 * */
	public void updateData(UserBean userBean) {
		id.setText(userBean.getAccount());
		nikename.setText(userBean.getNickname());
		email.setText(userBean.getEmail());
		description.setText(userBean.getDescription());
		address.setText(userBean.getAddress());
		city.setText(userBean.getCity());
		gender.setText(userBean.getGender());
		phoneNumber.setText(userBean.getPhoneNumber());
	}

}
