package com.example.student_buy_android.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.student_buy_android.R;
import com.example.student_buy_android.adapter.FriendsAdapter;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.util.SysApplication;
import com.example.student_buy_android.webservice.GetFriendListWebservice;

public class FriendsActivity extends BaseActivity implements OnClickListener {
	private DisplayMetrics displayMetrics;
	private LayoutParams para;
	private RelativeLayout rl_title;
	private int window_width;
	private int window_height;
	private ListView friends_list;
	private Button btn_add, btn_my;
	private FriendsAdapter friendsAdapter;
	private List<FriendBean> friendBeans;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends_layout);
		SysApplication.getInstance().addActivity(this);// 将该activity添加到管理类中去。

		init();

		GetFriendListWebservice getFriendListWebservice = new GetFriendListWebservice(
				FriendsActivity.this, this);
		getFriendListWebservice.execute();

		// ListView item点击事件
		friends_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 跳转好友详情页面
				Intent intent = new Intent(FriendsActivity.this,
						FriendInfoActivity.class);
				FriendBean friendBean = friendBeans.get(position);
				intent.putExtra("friendBean", friendBean);
				startActivity(intent);
			}
		});
	}

	private void init() {
		findViewById();
		adaptation();
		setOnClickListener();
	}

	private void adaptation() {
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		window_height = displayMetrics.heightPixels;
		window_width = displayMetrics.widthPixels;

		para = rl_title.getLayoutParams();
		para.height = (int) (window_height * (88.0 / 1334));
		para.width = (int) window_width;
		rl_title.setLayoutParams(para);
	}

	/**
	 * 绑定Adapter
	 * */
	public void setAdapter(List<FriendBean> friendBeans) {
		this.friendBeans = friendBeans;
		friendsAdapter = new FriendsAdapter(friendBeans, FriendsActivity.this);
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
		case R.id.btn_my:
			intent = new Intent(FriendsActivity.this, MyActivity.class);
			startActivity(intent);
			break;
		}
	}

	private void setOnClickListener() {
		btn_add.setOnClickListener(this);
		btn_my.setOnClickListener(this);
	}

	private void findViewById() {
		friends_list = (ListView) findViewById(R.id.friends_list);
		btn_add = (Button) findViewById(R.id.btn_add);
		btn_my = (Button) findViewById(R.id.btn_my);
		rl_title = (RelativeLayout) findViewById(R.id.rl_title);
	}

	/**
	 * 返回键后台运行
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			moveTaskToBack(false);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
