package com.example.student_buy_android.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.adapter.FriendsAdapter;
import com.example.student_buy_android.adapter.LatestContactsAdapter;
import com.example.student_buy_android.adapter.ShowAdapter;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.db.MessageDao;
import com.example.student_buy_android.webservice.GetFriendListWebservice;
import com.example.student_buy_android.webservice.GetMyInfoWebservice;

@SuppressLint({ "InflateParams", "HandlerLeak" })
public class MainActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	private ViewPager mViewPager;// �������ý����л�
	private PagerAdapter mPagerAdapter;// ��ʼ��View������
	private List<View> mViews = new ArrayList<View>();// �������Tab01-04
	// �ĸ�Tab��ÿ��Tab����һ����ť
	private LinearLayout mTabWeiXin, mTabAddress, mTabFrd, mTabSetting;
	// �ĸ���ť
	private ImageButton mWeiXinImg, mAddressImg, mFrdImg, mSettingImg;
	private ImageButton top_add;

	// ����
	private ListView lv_latest_contact;
	private List<FriendBean> latestContactLsit;// ������������ϵ��
	private LatestContactsAdapter latestContactsAdapter;
	// �����б�
	private ListView lv_friends;
	private List<FriendBean> friendBeans;// ������ź����б�
	private FriendsAdapter friendsAdapter;
	// �¿�
	private ListView lv_show;
	private ShowAdapter showAdapter;
	private ArrayList<ArrayList<HashMap<String, Object>>> arrayLists;// ��������¿�չʾ����
	private ViewPager showViewPager;
	private ImageView[] imageViews;
	private ImageView imageView;
	private AtomicInteger what = new AtomicInteger(0);
	private boolean isContinue = true;
	private List<View> advPics;
	// �ҵ�
	private TextView username, nikename, email, description, address, city,
			gender, phoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_layout);
		initView();
		initViewPage();
		initEvent();
		initLatestContacts();
	}

	private void initEvent() {
		mTabWeiXin.setOnClickListener(this);
		mTabAddress.setOnClickListener(this);
		mTabFrd.setOnClickListener(this);
		mTabSetting.setOnClickListener(this);
		top_add.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// ViewPage���һ���ʱ
			@Override
			public void onPageSelected(int arg0) {
				int currentItem = mViewPager.getCurrentItem();
				switch (currentItem) {
				case 0:
					initLatestContacts();
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
					getExcoo();
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

	}

	/**
	 * ��ʼ��ViewPage
	 */
	private void initViewPage() {

		// ���軯�ĸ�����
		LayoutInflater mLayoutInflater = LayoutInflater.from(this);
		View tab01 = mLayoutInflater.inflate(R.layout.tab_recently, null);
		View tab_friends = mLayoutInflater.inflate(R.layout.tab_friends, null);
		View tab03 = mLayoutInflater.inflate(R.layout.tab_show, null);
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
			initLatestContacts();
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
			getExcoo();
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
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			break;
		case R.id.nikename:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("nikename", nikename.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			break;
		case R.id.email:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("email", email.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			break;
		case R.id.description:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("description", description.getText().toString()
					.trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			break;
		case R.id.address:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("address", address.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			break;
		case R.id.city:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("city", city.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			break;
		case R.id.gender:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("gender", gender.getText().toString().trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			break;
		case R.id.phoneNumber:
			intent = new Intent(MainActivity.this, UpdateInfoActivity.class);
			intent.putExtra("phoneNumber", phoneNumber.getText().toString()
					.trim());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
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
	 * ��ʼ�������ϵ��
	 * */
	private void initLatestContacts() {
		latestContactLsit = getLatestContacts();
		setOnItemClickListener();
		setLatestContactsAdapter();
	}

	/**
	 * ��������ϵ��
	 * */
	private List<FriendBean> getLatestContacts() {
		MessageDao messageDao = new MessageDao();
		return messageDao.get_communication_last();
	}

	/**
	 * ListView item����¼�
	 * */
	private void setOnItemClickListener() {
		lv_latest_contact = (ListView) mViews.get(0).findViewById(
				R.id.lv_latest_contact);

		lv_latest_contact.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ��ת��������ҳ��
				Intent intent = new Intent(MainActivity.this,
						ChatActivity.class);
				FriendBean friendBean = latestContactLsit.get(position);
				intent.putExtra("friendBean", friendBean);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			}
		});
	}

	/**
	 * �����ϵ�� ��Adapter
	 * */
	private void setLatestContactsAdapter() {
		latestContactsAdapter = new LatestContactsAdapter(latestContactLsit,
				this);
		lv_latest_contact.setAdapter(latestContactsAdapter);
	}

	/**
	 * ��ú���
	 * */
	private void getFriends() {
		lv_friends = (ListView) mViews.get(1).findViewById(R.id.friends_list);

		GetFriendListWebservice getFriendListWebservice = new GetFriendListWebservice(
				MainActivity.this, this);
		getFriendListWebservice.execute();

		// ListView item����¼�
		lv_friends.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ��ת��������ҳ��
				Intent intent = new Intent(MainActivity.this,
						FriendInfoActivity.class);
				FriendBean friendBean = friendBeans.get(position);
				intent.putExtra("friendBean", friendBean);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			}
		});
	}

	/**
	 * ���Ѽ��� ��Adapter
	 * */
	public void setFriendsListAdapter(List<FriendBean> friendBeans) {
		this.friendBeans = friendBeans;
		friendsAdapter = new FriendsAdapter(friendBeans, MainActivity.this);
		lv_friends.setAdapter(friendsAdapter);
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
	 * �¿�
	 * */
	private void getExcoo() {
		initData();
		showViewPager = (ViewPager) mViews.get(2).findViewById(R.id.adv_pager);
		lv_show = (ListView) mViews.get(2).findViewById(R.id.lv_show);

		initViewPager();

		showAdapter = new ShowAdapter(arrayLists, MainActivity.this);
		lv_show.setAdapter(showAdapter);
	}

	/**
	 * ���λ
	 * */
	private void initViewPager() {
		ViewGroup viewGroup = (ViewGroup) mViews.get(2).findViewById(
				R.id.viewGroup);

		if (imageViews == null) {
			advPics = new ArrayList<View>();
			ImageView img1 = new ImageView(this);
			img1.setBackgroundResource(R.drawable.advertising1);// ���λͼƬ
			advPics.add(img1);
			ImageView img2 = new ImageView(this);
			img2.setBackgroundResource(R.drawable.advertising2);
			advPics.add(img2);
			ImageView img3 = new ImageView(this);
			img3.setBackgroundResource(R.drawable.advertising3);
			advPics.add(img3);
			// ImageView img4 = new ImageView(this);
			// img4.setBackgroundResource(R.drawable.advertising4);
			// advPics.add(img4);
			imageViews = new ImageView[advPics.size()];

			for (int i = 0; i < imageViews.length; i++) {
				imageView = new ImageView(this);
				imageView.setLayoutParams(new LayoutParams(20, 20));
				imageView.setPadding(5, 5, 5, 5);
				imageViews[i] = imageView;
				if (i == 0) {
					imageViews[i]
							.setBackgroundResource(R.drawable.banner_dian_focus);// ���λС��㣨����
				} else {
					imageViews[i]
							.setBackgroundResource(R.drawable.banner_dian_blur);// ���λС��㣨����
				}
				viewGroup.addView(imageViews[i]);
			}
		}

		showViewPager.setAdapter(new AdvAdapter(advPics));
		showViewPager.setOnPageChangeListener(new GuidePageChangeListener());
		showViewPager.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					isContinue = false;
					break;
				case MotionEvent.ACTION_UP:
					isContinue = true;
					break;
				default:
					isContinue = true;
					break;
				}
				return false;
			}
		});
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (isContinue) {
						viewHandler.sendEmptyMessage(what.get());
						whatOption();
					}
				}
			}
		}).start();
	}

	private void whatOption() {
		what.incrementAndGet();
		if (what.get() > imageViews.length - 1) {
			what.getAndAdd(-4);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

		}
	}

	/**
	 * �¿�����Դ
	 * */
	private void initData() {
		arrayLists = new ArrayList<ArrayList<HashMap<String, Object>>>();
		HashMap<String, Object> hashMap = null;
		ArrayList<HashMap<String, Object>> arrayListForEveryGridView;

		for (int i = 0; i < 10; i++) {
			arrayListForEveryGridView = new ArrayList<HashMap<String, Object>>();
			for (int j = 0; j < 3; j++) {
				hashMap = new HashMap<String, Object>();
				hashMap.put("content", "i=" + i + " ,j=" + j);
				arrayListForEveryGridView.add(hashMap);
			}
			arrayLists.add(arrayListForEveryGridView);
		}
	}

	/**
	 * ���λ������
	 * */
	private final class AdvAdapter extends PagerAdapter {
		private List<View> views = null;

		public AdvAdapter(List<View> views) {
			this.views = views;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		public void finishUpdate(View arg0) {
		}

		public int getCount() {
			return views.size();
		}

		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {
		}
	}

	private final class GuidePageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			what.getAndSet(arg0);
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.banner_dian_focus);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.banner_dian_blur);
				}
			}
		}
	}

	private final Handler viewHandler = new Handler() {

		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			showViewPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}
	};

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

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
}