package com.example.student_buy_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.util.SysApplication;

public class FriendInfoActivity extends BaseActivity implements OnClickListener {

	private TextView id, nikename, email, description, address, city, gender,
			phoneNumber, alias;
	private Button btn_send, btn_del;
	private FriendBean friendBean;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_info_layout);
		SysApplication.getInstance().addActivity(this);// 将该activity添加到管理类中去

		init();

	}

	private void init() {
		findViewById();
		setOnClickListener();
	}

	private void setOnClickListener() {
		btn_send.setOnClickListener(this);
		btn_del.setOnClickListener(this);
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
		alias = (TextView) findViewById(R.id.alias);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_del = (Button) findViewById(R.id.btn_del);

		friendBean = (FriendBean) getIntent().getExtras().get("friendBean");
		id.setText(friendBean.getUsername());
		nikename.setText(friendBean.getNickname());
		email.setText(friendBean.getEmail());
		description.setText(friendBean.getDescription());
		address.setText(friendBean.getAddress());
		city.setText(friendBean.getCity());
		gender.setText(friendBean.getGender());
		phoneNumber.setText(friendBean.getPhoneNumber());
		alias.setText(friendBean.getAlias());
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_send:
			intent = new Intent(FriendInfoActivity.this, ChatActivity.class);
			intent.putExtra("friendBean", friendBean);
			startActivity(intent);
			break;
		case R.id.btn_del:
			
			break;
		}
	}
}
