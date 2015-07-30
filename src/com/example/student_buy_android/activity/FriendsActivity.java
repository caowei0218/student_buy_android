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
	private List<FriendBean> friendBeans;// ������ź����б�
	private FriendsAdapter friendsAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends_layout);
		SysApplication.getInstance().addActivity(this);// ����activity��ӵ���������ȥ��

		init();
		getFriends();
	}

	private void init() {
		lv_friends = (ListView) findViewById(R.id.friends_list);
	}

	/**
	 * ��ú���
	 * */
	private void getFriends() {

		GetFriendsWebservice getFriendListWebservice = new GetFriendsWebservice(
				FriendsActivity.this, this);
		getFriendListWebservice.execute();

		// ListView item����¼�
		lv_friends.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ��ת��������ҳ��
				Intent intent = new Intent(FriendsActivity.this,
						FriendInfoActivity.class);
				FriendBean friendBean = friendBeans.get(position);
				intent.putExtra("friendBean", friendBean);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
				finish();
			}
		});
	}

	/**
	 * ���Ѽ��� ��Adapter
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
