package com.example.student_buy_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.adapter.FriendsAdapter;
import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.util.SysApplication;

public class FriendsActivity extends BaseActivity implements OnClickListener {

	private ListView friends_list;
	private Button btn_add;
	private FriendsAdapter friendsAdapter;
	List<String> data;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends_layout);
		SysApplication.getInstance().addActivity(this);// 将该activity添加到管理类中去。

		init();

		// GetFriendListWebservice getFriendListWebservice = new
		// GetFriendListWebservice(
		// FriendsActivity.this, this);
		// getFriendListWebservice.execute();

		// ListView item点击事件
		friends_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(FriendsActivity.this,
						ChatActivity.class);

				String account = data.get(position);
				UserBean userBean = new UserBean();
				userBean.setAccount(account);

				intent.putExtra("userBean", userBean);
				startActivity(intent);
			}
		});
	}

	private void init() {
		findViewById();
		setOnClickListener();

		data = new ArrayList<String>();
		data.add("caowei0218");
		data.add("chenxinchun");
		data.add("liushun");
		setAdapter(data);
	}

	/**
	 * 绑定Adapter
	 * */
	public void setAdapter(List<String> data) {
		friendsAdapter = new FriendsAdapter(data, FriendsActivity.this);
		friends_list.setAdapter(friendsAdapter);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_add:
			intent = new Intent(FriendsActivity.this, AddFriendActivity.class);
			startActivity(intent);
			break;
		}
	}

	private void setOnClickListener() {
		btn_add.setOnClickListener(this);
	}

	private void findViewById() {
		friends_list = (ListView) findViewById(R.id.friends_list);
		btn_add = (Button) findViewById(R.id.btn_add);
	}

}
