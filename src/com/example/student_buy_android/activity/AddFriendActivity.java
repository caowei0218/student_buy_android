package com.example.student_buy_android.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.student_buy_android.R;
import com.example.student_buy_android.webservice.AddFriendWebservice;

public class AddFriendActivity extends BaseActivity implements OnClickListener{
	
	private EditText friend_account;
	private Button add_friend;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addfriend_layout);
		
		init();
	}


	private void init() {
		findViewById();
		setOnClickListener();
	}

	private void setOnClickListener() {
		add_friend.setOnClickListener(this);
	}

	private void findViewById() {
		friend_account = (EditText) findViewById(R.id.friend_account);
		add_friend = (Button) findViewById(R.id.add_friend);
	}


	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.add_friend:
			AddFriendWebservice addFriendWebservice = new AddFriendWebservice(AddFriendActivity.this, this, friend_account.getText().toString().trim());
			addFriendWebservice.execute();
			break;
		}
	}
}
