package com.example.student_buy_android.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.adapter.FriendsAdapter;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.util.SysApplication;
import com.example.student_buy_android.webservice.GetFriendsWebservice;

public class FriendsActivity extends BaseActivity implements OnClickListener {

	private ListView lv_friends;
	private List<FriendBean> friendBeans;// 用来存放好友列表
	private FriendsAdapter friendsAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends_layout);
		SysApplication.getInstance().addActivity(this);// 将该activity添加到管理类中去。

		init();
		getFriends();
	}

	private void init() {
		lv_friends = (ListView) findViewById(R.id.friends_list);
	}

	/**
	 * 获得好友
	 * */
	private void getFriends() {

		GetFriendsWebservice getFriendListWebservice = new GetFriendsWebservice(
				FriendsActivity.this, this);
		getFriendListWebservice.execute();

		// ListView item点击事件
		lv_friends.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 跳转好友详情页面
				Intent intent = new Intent(FriendsActivity.this,
						FriendInfoActivity.class);
				FriendBean friendBean = friendBeans.get(position);
				intent.putExtra("friendBean", friendBean);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// 实现淡入浅出的效果
				finish();
			}
		});
	}

	/**
	 * 好友集合 绑定Adapter
	 * */
	public void setFriendsListAdapter(List<FriendBean> friendBeans) {
		this.friendBeans = friendBeans;
		friendsAdapter = new FriendsAdapter(friendBeans, this);
		lv_friends.setAdapter(friendsAdapter);
	}

	@Override
	public void onClick(View v) {

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
