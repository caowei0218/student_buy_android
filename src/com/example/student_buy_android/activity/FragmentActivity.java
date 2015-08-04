package com.example.student_buy_android.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.student_buy_android.R;
import com.example.student_buy_android.SysApplication;
import com.example.student_buy_android.fragment.ChatFragment;
import com.example.student_buy_android.fragment.HomeFragment;
import com.example.student_buy_android.fragment.MyFragment;
import com.example.student_buy_android.fragment.SameSchoolFragment;

public class FragmentActivity extends BaseActivity implements OnClickListener {

	// �ĸ�Fragment
	private HomeFragment homeFragment;
	private SameSchoolFragment sameSchoolFragment;
	private ChatFragment chatFragment;
	private MyFragment myFragment;

	// �ĸ�Tab��ÿ��Tab����һ����ť
	private LinearLayout mTabWeiXin, mTabAddress, mTabFrd, mTabSetting;
	private ImageButton mWeiXinImg, mAddressImg, mFrdImg, mSettingImg;

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
		// ��ʼ������Ԫ��
		fragmentManager = getFragmentManager();
		init();
		// ��һ������ʱѡ�е�0��tab
		setTabSelection(0);
	}

	private void init() {
		// ��ʼ���ĸ�LinearLayout
		mTabWeiXin = (LinearLayout) findViewById(R.id.id_tab_weixin);
		mTabAddress = (LinearLayout) findViewById(R.id.id_tab_address);
		mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);
		mTabSetting = (LinearLayout) findViewById(R.id.id_tab_settings);
		// ��ʼ���ĸ���ť
		mWeiXinImg = (ImageButton) findViewById(R.id.id_tab_weixin_img);
		mAddressImg = (ImageButton) findViewById(R.id.id_tab_address_img);
		mFrdImg = (ImageButton) findViewById(R.id.id_tab_frd_img);
		mSettingImg = (ImageButton) findViewById(R.id.id_tab_settings_img);

		mTabWeiXin.setOnClickListener(this);
		mTabAddress.setOnClickListener(this);
		mTabFrd.setOnClickListener(this);
		mTabSetting.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// ����һ��Fragment����
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		switch (v.getId()) {
		case R.id.id_tab_frd:
			setTabSelection(0);
			break;
		case R.id.id_tab_address:
			setTabSelection(1);
			break;
		case R.id.id_tab_weixin:
			setTabSelection(2);
			break;
		case R.id.id_tab_settings:
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
			mFrdImg.setImageResource(R.drawable.tab_find_frd_pressed);
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
			mAddressImg.setImageResource(R.drawable.tab_address_pressed);
			if (sameSchoolFragment == null) {
				// ���ContactsFragmentΪ�գ��򴴽�һ������ӵ�������
				sameSchoolFragment = new SameSchoolFragment();
				transaction.add(R.id.content, sameSchoolFragment);
			} else {
				// ���ContactsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(sameSchoolFragment);
			}
			break;
		case 2:
			mWeiXinImg.setImageResource(R.drawable.tab_weixin_pressed);
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
			mSettingImg.setImageResource(R.drawable.tab_settings_pressed);
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
		mWeiXinImg.setImageResource(R.drawable.tab_weixin_normal);
		mAddressImg.setImageResource(R.drawable.tab_address_normal);
		mFrdImg.setImageResource(R.drawable.tab_find_frd_normal);
		mSettingImg.setImageResource(R.drawable.tab_settings_normal);
	}

	/**
	 * �����е�Fragment����Ϊ����״̬��
	 */
	public void hideFragments(FragmentTransaction transaction) {
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if (sameSchoolFragment != null) {
			transaction.hide(sameSchoolFragment);
		}
		if (chatFragment != null) {
			transaction.hide(chatFragment);
		}
		if (myFragment != null) {
			transaction.hide(myFragment);
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
