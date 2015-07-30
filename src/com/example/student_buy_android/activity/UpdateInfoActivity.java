package com.example.student_buy_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.SysApplication;
import com.example.student_buy_android.webservice.UpdateInfoWebservice;

public class UpdateInfoActivity extends BaseActivity implements OnClickListener {

	private String nikename;
	private String email;
	private String description;
	private String address;
	private String city;
	private String gender;
	private String phoneNumber;

	private Intent intent;

	private Button btn_back;
	private TextView tv_title, tv_update;
	private EditText et_content;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_personal_info_layout);
		SysApplication.getInstance().addActivity(this);// ����activity��ӵ���������ȥ��

		init();
	}

	private void init() {
		findViewById();
		getData();
		setOnClickListener();
	}

	private void getData() {
		intent = getIntent();
		nikename = intent.getStringExtra("nikename");
		email = intent.getStringExtra("email");
		description = intent.getStringExtra("description");
		address = intent.getStringExtra("address");
		city = intent.getStringExtra("city");
		gender = intent.getStringExtra("gender");
		phoneNumber = intent.getStringExtra("phoneNumber");

		if (nikename != null) {
			tv_title.setText("�ǳ�");
			et_content.setText(nikename);
		}
		if (email != null) {
			tv_title.setText("����");
			et_content.setText(email);
		}
		if (description != null) {
			tv_title.setText("����");
			et_content.setText(description);
		}
		if (address != null) {
			tv_title.setText("��ַ");
			et_content.setText(address);
		}
		if (city != null) {
			tv_title.setText("����");
			et_content.setText(city);
		}
		if (gender != null) {
			tv_title.setText("�Ա�");
			et_content.setText(gender);
		}
		if (phoneNumber != null) {
			tv_title.setText("�绰");
			et_content.setText(phoneNumber);
		}
	}

	private void setOnClickListener() {
		btn_back.setOnClickListener(this);
		tv_update.setOnClickListener(this);
	}

	private void findViewById() {
		btn_back = (Button) findViewById(R.id.btn_back);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_update = (TextView) findViewById(R.id.tv_update);
		et_content = (EditText) findViewById(R.id.et_content);
	}

	@Override
	public void onClick(View v) {

		UpdateInfoWebservice updateInfoWebservice;

		switch (v.getId()) {
		case R.id.tv_update:
			if (nikename != null) {
				updateInfoWebservice = new UpdateInfoWebservice(
						UpdateInfoActivity.this, this, "nickname", et_content
								.getText().toString().trim());
				updateInfoWebservice.execute();
			}
			if (description != null) {
				updateInfoWebservice = new UpdateInfoWebservice(
						UpdateInfoActivity.this, this, "description",
						et_content.getText().toString().trim());
				updateInfoWebservice.execute();
			}
			if (email != null) {
				updateInfoWebservice = new UpdateInfoWebservice(
						UpdateInfoActivity.this, this, "email", et_content
								.getText().toString().trim());
				updateInfoWebservice.execute();
			}
			if (address != null) {
				updateInfoWebservice = new UpdateInfoWebservice(
						UpdateInfoActivity.this, this, "address", et_content
								.getText().toString().trim());
				updateInfoWebservice.execute();
			}
			if (city != null) {
				updateInfoWebservice = new UpdateInfoWebservice(
						UpdateInfoActivity.this, this, "city", et_content
								.getText().toString().trim());
				updateInfoWebservice.execute();
			}
			if (gender != null) {
				updateInfoWebservice = new UpdateInfoWebservice(
						UpdateInfoActivity.this, this, "gender", et_content
								.getText().toString().trim());
				updateInfoWebservice.execute();
			}
			if (phoneNumber != null) {
				updateInfoWebservice = new UpdateInfoWebservice(
						UpdateInfoActivity.this, this, "phoneNumber", et_content
								.getText().toString().trim());
				updateInfoWebservice.execute();
			}
			break;
		case R.id.btn_back:
			finish();
			break;
		}
	}

	/**
	 * ����
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			moveTaskToBack(false);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
