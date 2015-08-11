package com.example.student_buy_android.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.SysApplication;
import com.example.student_buy_android.bean.Message;
import com.example.student_buy_android.bean.Message.Type;
import com.example.student_buy_android.db.MessageDao;
import com.example.student_buy_android.fragment.ChatFragment;
import com.example.student_buy_android.fragment.HomeFragment;
import com.example.student_buy_android.fragment.MyFragment;
import com.example.student_buy_android.fragment.ExcooFragment;

public class FragmentActivity extends BaseActivity implements OnClickListener {

	// �ĸ�Fragment
	private HomeFragment homeFragment;
	private ExcooFragment excooFragment;
	private ChatFragment chatFragment;
	private MyFragment myFragment;

	// �ĸ�Tab��ÿ��Tab����һ����ť
	private LinearLayout ll_tab_home, ll_tab_excoo, ll_tab_chat, ll_tab_my;
	private ImageButton mHomeImg, mExcooImg, mChatImg, mMyImg;

	private TextView tv_unread_messages;

	private String account;

	/**
	 * ���ڶ�Fragment���й���
	 */
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_main);
		SysApplication.getInstance().addActivity(this);// ����activity��ӵ���������ȥ��

		SharedPreferences sp = getSharedPreferences("user",
				Context.MODE_PRIVATE);
		account = sp.getString("username", "");

		// ��ʼ������Ԫ��
		fragmentManager = getFragmentManager();
		init();

		registerBoradcastReceiver();
		getUnreadMessages();

		// ��һ������ʱѡ�е�0��tab
		setTabSelection(0);
	}

	private void init() {
		// ��ʼ���ĸ�LinearLayout
		ll_tab_home = (LinearLayout) findViewById(R.id.ll_tab_home);
		ll_tab_excoo = (LinearLayout) findViewById(R.id.ll_tab_excoo);
		ll_tab_chat = (LinearLayout) findViewById(R.id.ll_tab_chat);
		ll_tab_my = (LinearLayout) findViewById(R.id.ll_tab_my);
		// ��ʼ���ĸ���ť
		mHomeImg = (ImageButton) findViewById(R.id.ib_tab_home_img);
		mExcooImg = (ImageButton) findViewById(R.id.ib_tab_excoo_img);
		mChatImg = (ImageButton) findViewById(R.id.ib_tab_chat_img);
		mMyImg = (ImageButton) findViewById(R.id.ib_tab_my_img);

		tv_unread_messages = (TextView) findViewById(R.id.tv_unread_messages);

		ll_tab_home.setOnClickListener(this);
		ll_tab_excoo.setOnClickListener(this);
		ll_tab_chat.setOnClickListener(this);
		ll_tab_my.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		// ����һ��Fragment����
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		switch (v.getId()) {
		case R.id.ll_tab_home:
			setTabSelection(0);
			break;
		case R.id.ll_tab_excoo:
			if (!"".equals(account)) {
				setTabSelection(1);
			} else {
				intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			}
			break;
		case R.id.ll_tab_chat:
			if (!"".equals(account)) {
				setTabSelection(2);
			} else {
				intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			}
			break;
		case R.id.ll_tab_my:
			setTabSelection(3);
			break;
		default:
			break;
		}
		transaction.commit();
	}

	public void setTabSelection(int index) {
		resetImg();
		// ����һ��Fragment����
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
		hideFragments(transaction);
		switch (index) {
		case 0:
			hideFragments(transaction);
			mHomeImg.setImageResource(R.drawable.tab_home_pressed);
			if (homeFragment == null) {
				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				homeFragment = new HomeFragment();
				transaction.add(R.id.content, homeFragment);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(homeFragment);
			}
			break;
		case 1:
			mExcooImg.setImageResource(R.drawable.tab_excoo_pressed);
			if (excooFragment == null) {
				// ���ContactsFragmentΪ�գ��򴴽�һ������ӵ�������
				excooFragment = new ExcooFragment();
				transaction.add(R.id.content, excooFragment);
			} else {
				// ���ContactsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(excooFragment);
			}
			break;
		case 2:
			mChatImg.setImageResource(R.drawable.tab_chat_pressed);
			if (chatFragment == null) {
				// ���NewsFragmentΪ�գ��򴴽�һ������ӵ�������
				chatFragment = new ChatFragment();
				transaction.add(R.id.content, chatFragment);
			} else {
				// ���NewsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(chatFragment);
			}
			break;
		case 3:
		default:
			mMyImg.setImageResource(R.drawable.tab_my_pressed);
			if (myFragment == null) {
				// ���SettingFragmentΪ�գ��򴴽�һ������ӵ�������
				myFragment = new MyFragment();
				transaction.add(R.id.content, myFragment);
			} else {
				// ���SettingFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(myFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * ������ͼƬ�䰵
	 */
	private void resetImg() {
		mHomeImg.setImageResource(R.drawable.tab_home_normal);
		mExcooImg.setImageResource(R.drawable.tab_excoo_normal);
		mChatImg.setImageResource(R.drawable.tab_chat_normal);
		mMyImg.setImageResource(R.drawable.tab_my_normal);
	}

	/**
	 * �����е�Fragment����Ϊ����״̬��
	 */
	public void hideFragments(FragmentTransaction transaction) {
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if (excooFragment != null) {
			transaction.hide(excooFragment);
		}
		if (chatFragment != null) {
			transaction.hide(chatFragment);
		}
		if (myFragment != null) {
			transaction.hide(myFragment);
		}
	}

	/**
	 * ע��㲥
	 * */
	private void registerBoradcastReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("message_received_action");
		MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver();
		registerReceiver(broadcastReceiver, intentFilter);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		getUnreadMessages();
	}

	/**
	 * �㲥������
	 * */
	public class MyBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Message message = (Message) intent.getExtras().get("message");
			message.setType(Type.INPUT);
			getUnreadMessages();
		}
	}

	/**
	 * ��ѯδ����Ϣ����
	 * */
	private void getUnreadMessages() {
		MessageDao messageDao = new MessageDao();
		int count = messageDao.get_unread_message_count();
		if (count == 0) {
			tv_unread_messages.setVisibility(View.INVISIBLE);
		} else {
			tv_unread_messages.setText("" + count);
			tv_unread_messages.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * ���ؼ���̨����
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
