package com.example.student_buy_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student_buy_android.R;
import com.example.student_buy_android.adapter.FriendsAdapter;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.webservice.GetFriendListWebservice;
import com.example.student_buy_android.webservice.GetMyInfoWebservice;

@SuppressLint("InflateParams")
public class MainActivity extends BaseActivity implements
		android.view.View.OnClickListener {

	private ViewPager mViewPager;// �������ý����л�
	private PagerAdapter mPagerAdapter;// ��ʼ��View������
	private List<View> mViews = new ArrayList<View>();// �������Tab01-04
	// �ĸ�Tab��ÿ��Tab����һ����ť
	private LinearLayout mTabWeiXin, mTabAddress, mTabFrd, mTabSetting;
	// �ĸ���ť
	private ImageButton mWeiXinImg, mAddressImg, mFrdImg, mSettingImg;

	private ListView friends_list;
	private ImageButton top_add, top_search;
	private TextView username, nikename, email, description, address, city,
			gender, phoneNumber;
	private List<FriendBean> friendBeans;// ������ź����б�
	private FriendsAdapter friendsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_layout);
		initView();
		initViewPage();
		initEvent();
	}

	private void initEvent() {
		mTabWeiXin.setOnClickListener(this);
		mTabAddress.setOnClickListener(this);
		mTabFrd.setOnClickListener(this);
		mTabSetting.setOnClickListener(this);
		top_add.setOnClickListener(this);
		top_search.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// ViewPage���һ���ʱ
			@Override
			public void onPageSelected(int arg0) {
				int currentItem = mViewPager.getCurrentItem();
				switch (currentItem) {
				case 0:
					resetImg();
					mWeiXinImg.setImageResource(R.drawable.tab_weixin_pressed);
					break;
				case 1:
					getFriends();
					resetImg();
					mAddressImg
							.setImageResource(R.drawable.tab_address_pressed);
					break;
				case 2:
					resetImg();
					mFrdImg.setImageResource(R.drawable.tab_find_frd_pressed);
					break;
				case 3:
					getMyInfo();
					resetImg();
					mSettingImg
							.setImageResource(R.drawable.tab_settings_pressed);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	/**
	 * ��ʼ������
	 */
	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpage);
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

		top_add = (ImageButton) findViewById(R.id.top_add);
		top_search = (ImageButton) findViewById(R.id.top_search);

	}

	/**
	 * ��ʼ��ViewPage
	 */
	private void initViewPage() {

		// ���軯�ĸ�����
		LayoutInflater mLayoutInflater = LayoutInflater.from(this);
		View tab01 = mLayoutInflater.inflate(R.layout.tab_recently, null);
		View tab_friends = mLayoutInflater.inflate(R.layout.tab_friends, null);
		View tab03 = mLayoutInflater.inflate(R.layout.tab03, null);
		View tab_my = mLayoutInflater.inflate(R.layout.tab_my, null);

		mViews.add(tab01);
		mViews.add(tab_friends);
		mViews.add(tab03);
		mViews.add(tab_my);

		// ��������ʼ��������
		mPagerAdapter = new PagerAdapter() {

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(mViews.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = mViews.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return mViews.size();
			}
		};
		mViewPager.setAdapter(mPagerAdapter);
	}

	/**
	 * �ж��ĸ�Ҫ��ʾ�������ð�ťͼƬ
	 */
	@Override
	public void onClick(View arg0) {
		Intent intent;
		switch (arg0.getId()) {
		case R.id.id_tab_weixin:
			mViewPager.setCurrentItem(0);
			resetImg();
			mWeiXinImg.setImageResource(R.drawable.tab_weixin_pressed);
			break;
		case R.id.id_tab_address:
			getFriends();
			mViewPager.setCurrentItem(1);
			resetImg();
			mAddressImg.setImageResource(R.drawable.tab_address_pressed);
			break;
		case R.id.id_tab_frd:
			mViewPager.setCurrentItem(2);
			resetImg();
			mFrdImg.setImageResource(R.drawable.tab_find_frd_pressed);
			break;
		case R.id.id_tab_settings:
			getMyInfo();
			mViewPager.setCurrentItem(3);
			resetImg();
			mSettingImg.setImageResource(R.drawable.tab_settings_pressed);
			break;
		case R.id.top_add:
			intent = new Intent(MainActivity.this, AddFriendActivity.class);
			startActivity(intent);
			break;
		case R.id.top_search:
			Toast.makeText(MainActivity.this, "������", Toast.LENGTH_SHORT).show();
			break;
		case R.id.nikename:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("nikename", nikename.getText().toString().trim());
			startActivity(intent);
			break;
		case R.id.email:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("email", email.getText().toString().trim());
			startActivity(intent);
			break;
		case R.id.description:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("description", description.getText().toString()
					.trim());
			startActivity(intent);
			break;
		case R.id.address:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("address", address.getText().toString().trim());
			startActivity(intent);
			break;
		case R.id.city:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("city", city.getText().toString().trim());
			startActivity(intent);
			break;
		case R.id.gender:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("gender", gender.getText().toString().trim());
			startActivity(intent);
			break;
		case R.id.phoneNumber:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("phoneNumber", phoneNumber.getText().toString()
					.trim());
			startActivity(intent);
			break;
		default:
			break;
		}
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
	 * ���Ѽ��� ��Adapter
	 * */
	public void setFriendsListAdapter(List<FriendBean> friendBeans) {
		this.friendBeans = friendBeans;
		friendsAdapter = new FriendsAdapter(friendBeans, MainActivity.this);
		friends_list.setAdapter(friendsAdapter);
	}

	/**
	 * ��ú���
	 * */
	private void getFriends() {
		friends_list = (ListView) mViews.get(1).findViewById(R.id.friends_list);

		GetFriendListWebservice getFriendListWebservice = new GetFriendListWebservice(
				MainActivity.this, this);
		getFriendListWebservice.execute();

		// ListView item����¼�
		friends_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ��ת��������ҳ��
				Intent intent = new Intent(MainActivity.this,
						FriendInfoActivity.class);
				FriendBean friendBean = friendBeans.get(position);
				intent.putExtra("friendBean", friendBean);
				startActivity(intent);
			}
		});
	}

	/**
	 * ����ҵĸ�����Ϣ
	 * */
	private void getMyInfo() {
		username = (TextView) mViews.get(3).findViewById(R.id.id);
		nikename = (TextView) mViews.get(3).findViewById(R.id.nikename);
		email = (TextView) mViews.get(3).findViewById(R.id.email);
		description = (TextView) mViews.get(3).findViewById(R.id.description);
		address = (TextView) mViews.get(3).findViewById(R.id.address);
		city = (TextView) mViews.get(3).findViewById(R.id.city);
		gender = (TextView) mViews.get(3).findViewById(R.id.gender);
		phoneNumber = (TextView) mViews.get(3).findViewById(R.id.phoneNumber);

		nikename.setOnClickListener(this);
		email.setOnClickListener(this);
		description.setOnClickListener(this);
		address.setOnClickListener(this);
		city.setOnClickListener(this);
		gender.setOnClickListener(this);
		phoneNumber.setOnClickListener(this);

		GetMyInfoWebservice getMyInfoWebservice = new GetMyInfoWebservice(
				MainActivity.this, this);
		getMyInfoWebservice.execute();
	}

	/**
	 * �޸�ҳ������
	 * */
	public void updateData(UserBean userBean) {
		username.setText(userBean.getUsername());
		nikename.setText(userBean.getNickname());
		email.setText(userBean.getEmail());
		description.setText(userBean.getDescription());
		address.setText(userBean.getAddress());
		city.setText(userBean.getCity());
		gender.setText(userBean.getGender());
		phoneNumber.setText(userBean.getPhoneNumber());
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