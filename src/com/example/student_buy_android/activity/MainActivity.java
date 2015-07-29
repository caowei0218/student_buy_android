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
import android.util.DisplayMetrics;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student_buy_android.R;
import com.example.student_buy_android.adapter.FriendsAdapter;
import com.example.student_buy_android.adapter.LatestContactsAdapter;
import com.example.student_buy_android.adapter.ShowAdapter;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.db.MessageDao;
import com.example.student_buy_android.util.SysApplication;
import com.example.student_buy_android.webservice.GetFriendListWebservice;
import com.example.student_buy_android.webservice.GetMyInfoWebservice;

@SuppressLint({ "InflateParams", "HandlerLeak" })
public class MainActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	private ViewPager mViewPager;// 用来放置界面切换
	private PagerAdapter mPagerAdapter;// 初始化View适配器
	private List<View> mViews = new ArrayList<View>();// 用来存放Tab01-04
	// 四个Tab，每个Tab包含一个按钮
	private LinearLayout mTabWeiXin, mTabAddress, mTabFrd, mTabSetting;
	// 四个按钮
	private ImageButton mWeiXinImg, mAddressImg, mFrdImg, mSettingImg;
	private ImageButton top_add;

	// 聊天
	private ListView lv_latest_contact;
	private List<FriendBean> latestContactLsit;// 用来存放最近联系人
	private LatestContactsAdapter latestContactsAdapter;
	// 好友列表
	private ListView lv_friends;
	private List<FriendBean> friendBeans;// 用来存放好友列表
	private FriendsAdapter friendsAdapter;
	// 衣酷
	private ListView lv_show;
	private ShowAdapter showAdapter;
	private ArrayList<ArrayList<HashMap<String, Object>>> arrayLists;// 用来存放衣酷展示内容
	private ViewPager showViewPager;
	private ImageView[] imageViews;
	private ImageView imageView;
	private AtomicInteger what = new AtomicInteger(0);
	private boolean isContinue = true;
	private List<View> advPics;
	private DisplayMetrics displayMetrics;
	private int window_width;
	private int window_height;
	private LayoutParams para;
	private ImageView seasons, coat, footwear, other;
	// 我的
	private TextView tv_nickname, tv_username;
	private RelativeLayout rl_info, rl_friends, rl_publish, rl_sell, rl_buy,
			rl_collect, rl_setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_layout);
		SysApplication.getInstance().addActivity(this);// 将该activity添加到管理类中去。
		initView();
		initViewPage();
		initEvent();
		getExcoo();
	}

	private void initEvent() {
		mTabWeiXin.setOnClickListener(this);
		mTabAddress.setOnClickListener(this);
		mTabFrd.setOnClickListener(this);
		mTabSetting.setOnClickListener(this);
		top_add.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// ViewPage左右滑动时
			@Override
			public void onPageSelected(int arg0) {
				int currentItem = mViewPager.getCurrentItem();
				switch (currentItem) {
				case 0:
					getExcoo();
					resetImg();
					mFrdImg.setImageResource(R.drawable.tab_find_frd_pressed);
					break;
				case 1:
					getFriends();
					resetImg();
					mAddressImg
							.setImageResource(R.drawable.tab_address_pressed);
					break;
				case 2:
					initLatestContacts();
					resetImg();
					mWeiXinImg.setImageResource(R.drawable.tab_weixin_pressed);
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
	 * 初始化设置
	 */
	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpage);
		// 初始化四个LinearLayout
		mTabWeiXin = (LinearLayout) findViewById(R.id.id_tab_weixin);
		mTabAddress = (LinearLayout) findViewById(R.id.id_tab_address);
		mTabFrd = (LinearLayout) findViewById(R.id.id_tab_frd);
		mTabSetting = (LinearLayout) findViewById(R.id.id_tab_settings);
		// 初始化四个按钮
		mWeiXinImg = (ImageButton) findViewById(R.id.id_tab_weixin_img);
		mAddressImg = (ImageButton) findViewById(R.id.id_tab_address_img);
		mFrdImg = (ImageButton) findViewById(R.id.id_tab_frd_img);
		mSettingImg = (ImageButton) findViewById(R.id.id_tab_settings_img);

		top_add = (ImageButton) findViewById(R.id.top_add);

	}

	/**
	 * 初始化ViewPage
	 */
	private void initViewPage() {

		// 初始化四个布局
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View tab_show = layoutInflater.inflate(R.layout.tab_show, null);
		View tab_friends = layoutInflater.inflate(R.layout.tab_friends, null);
		View tab_recently = layoutInflater.inflate(R.layout.tab_recently, null);
		View tab_setting = layoutInflater.inflate(R.layout.tab_setting, null);

		mViews.add(tab_show);
		mViews.add(tab_friends);
		mViews.add(tab_recently);
		mViews.add(tab_setting);

		// 适配器初始化并设置
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
	 * 判断哪个要显示，及设置按钮图片
	 */
	@Override
	public void onClick(View arg0) {
		Intent intent;
		switch (arg0.getId()) {
		case R.id.id_tab_frd:
			getExcoo();
			mViewPager.setCurrentItem(0);
			resetImg();
			mFrdImg.setImageResource(R.drawable.tab_find_frd_pressed);
			break;
		case R.id.id_tab_address:
			getFriends();
			mViewPager.setCurrentItem(1);
			resetImg();
			mAddressImg.setImageResource(R.drawable.tab_address_pressed);
			break;
		case R.id.id_tab_weixin:
			initLatestContacts();
			mViewPager.setCurrentItem(2);
			resetImg();
			mWeiXinImg.setImageResource(R.drawable.tab_weixin_pressed);
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
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_info:
			intent = new Intent(MainActivity.this, MyInfoActivity.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_friends:
			intent = new Intent(MainActivity.this, FriendsActivity.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_publish:
			intent = new Intent(MainActivity.this, PublishActivity.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_sell:
			Toast.makeText(this, "待开发", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_buy:
			Toast.makeText(this, "待开发", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_collect:
			Toast.makeText(this, "待开发", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_setting:
			intent = new Intent(MainActivity.this, SetInfoActivity.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		default:
			break;
		}
	}

	/**
	 * 把所有图片变暗
	 */
	private void resetImg() {
		mWeiXinImg.setImageResource(R.drawable.tab_weixin_normal);
		mAddressImg.setImageResource(R.drawable.tab_address_normal);
		mFrdImg.setImageResource(R.drawable.tab_find_frd_normal);
		mSettingImg.setImageResource(R.drawable.tab_settings_normal);
	}

	/**
	 * 获得好友
	 * */
	private void getFriends() {
		lv_friends = (ListView) mViews.get(1).findViewById(R.id.friends_list);

		GetFriendListWebservice getFriendListWebservice = new GetFriendListWebservice(
				MainActivity.this, this);
		getFriendListWebservice.execute();

		// ListView item点击事件
		lv_friends.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 跳转好友详情页面
				Intent intent = new Intent(MainActivity.this,
						FriendInfoActivity.class);
				FriendBean friendBean = friendBeans.get(position);
				intent.putExtra("friendBean", friendBean);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// 实现淡入浅出的效果
			}
		});
	}

	/**
	 * 好友集合 绑定Adapter
	 * */
	public void setFriendsListAdapter(List<FriendBean> friendBeans) {
		this.friendBeans = friendBeans;
		friendsAdapter = new FriendsAdapter(friendBeans, MainActivity.this);
		lv_friends.setAdapter(friendsAdapter);
	}

	/**
	 * 初始化最近联系人
	 * */
	private void initLatestContacts() {
		latestContactLsit = getLatestContacts();
		setOnItemClickListener();
		setLatestContactsAdapter();
	}

	/**
	 * 获得最近联系人
	 * */
	private List<FriendBean> getLatestContacts() {
		MessageDao messageDao = new MessageDao();
		return messageDao.get_communication_last();
	}

	/**
	 * ListView item点击事件
	 * */
	private void setOnItemClickListener() {
		lv_latest_contact = (ListView) mViews.get(2).findViewById(
				R.id.lv_latest_contact);

		lv_latest_contact.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 跳转好友详情页面
				Intent intent = new Intent(MainActivity.this,
						ChatActivity.class);
				FriendBean friendBean = latestContactLsit.get(position);
				intent.putExtra("friendBean", friendBean);
				startActivity(intent);
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// 实现淡入浅出的效果
			}
		});
	}

	/**
	 * 最近联系人 绑定Adapter
	 * */
	private void setLatestContactsAdapter() {
		latestContactsAdapter = new LatestContactsAdapter(latestContactLsit,
				this);
		lv_latest_contact.setAdapter(latestContactsAdapter);
	}

	/**
	 * 获得我的个人信息
	 * */
	private void getMyInfo() {
		rl_info = (RelativeLayout) mViews.get(3).findViewById(R.id.rl_info);
		rl_friends = (RelativeLayout) mViews.get(3).findViewById(
				R.id.rl_friends);
		rl_publish = (RelativeLayout) mViews.get(3).findViewById(
				R.id.rl_publish);
		rl_sell = (RelativeLayout) mViews.get(3).findViewById(R.id.rl_sell);
		rl_buy = (RelativeLayout) mViews.get(3).findViewById(R.id.rl_buy);
		rl_collect = (RelativeLayout) mViews.get(3).findViewById(
				R.id.rl_collect);
		rl_setting = (RelativeLayout) mViews.get(3).findViewById(
				R.id.rl_setting);
		tv_nickname = (TextView) mViews.get(3).findViewById(R.id.tv_nickname);
		tv_username = (TextView) mViews.get(3).findViewById(R.id.tv_username);

		rl_info.setOnClickListener(this);
		rl_friends.setOnClickListener(this);
		rl_publish.setOnClickListener(this);
		rl_sell.setOnClickListener(this);
		rl_buy.setOnClickListener(this);
		rl_collect.setOnClickListener(this);
		rl_setting.setOnClickListener(this);

		GetMyInfoWebservice getMyInfoWebservice = new GetMyInfoWebservice(
				MainActivity.this, this);
		getMyInfoWebservice.execute();
	}

	/**
	 * 修改页面数据
	 * */
	public void updateData(UserBean userBean) {
		tv_username.setText(userBean.getUsername());
		tv_nickname.setText(userBean.getNickname());
	}

	/**
	 * 衣酷
	 * */
	private void getExcoo() {
		initData();

		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		window_width = displayMetrics.widthPixels;
		window_height = displayMetrics.heightPixels;

		showViewPager = (ViewPager) mViews.get(0).findViewById(R.id.adv_pager);
		seasons = (ImageView) mViews.get(0).findViewById(R.id.seasons);
		coat = (ImageView) mViews.get(0).findViewById(R.id.coat);
		footwear = (ImageView) mViews.get(0).findViewById(R.id.footwear);
		other = (ImageView) mViews.get(0).findViewById(R.id.other);
		lv_show = (ListView) mViews.get(0).findViewById(R.id.lv_show);

		adaptation();

		initViewPager();

		showAdapter = new ShowAdapter(arrayLists, MainActivity.this);
		lv_show.setAdapter(showAdapter);
	}

	/**
	 * 屏幕比例适配
	 * */
	private void adaptation() {
		para = seasons.getLayoutParams();
		para.height = (int) (window_height * (120.0 / 1334));
		para.width = (int) (window_width * (120.0 / 750));
		seasons.setLayoutParams(para);

		para = coat.getLayoutParams();
		para.height = (int) (window_height * (120.0 / 1334));
		para.width = (int) (window_width * (120.0 / 750));
		coat.setLayoutParams(para);

		para = footwear.getLayoutParams();
		para.height = (int) (window_height * (120.0 / 1334));
		para.width = (int) (window_width * (120.0 / 750));
		footwear.setLayoutParams(para);

		para = other.getLayoutParams();
		para.height = (int) (window_height * (120.0 / 1334));
		para.width = (int) (window_width * (120.0 / 750));
		other.setLayoutParams(para);

		para = showViewPager.getLayoutParams();
		para.height = (int) (window_height * (200.0 / 1334));
		showViewPager.setLayoutParams(para);
	}

	/**
	 * 广告位
	 * */
	private void initViewPager() {
		ViewGroup viewGroup = (ViewGroup) mViews.get(0).findViewById(
				R.id.viewGroup);

		if (imageViews == null) {
			advPics = new ArrayList<View>();
			ImageView img1 = new ImageView(this);
			img1.setBackgroundResource(R.drawable.advertising1);// 广告位图片
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
							.setBackgroundResource(R.drawable.banner_dian_focus);// 广告位小点点（亮）
				} else {
					imageViews[i]
							.setBackgroundResource(R.drawable.banner_dian_blur);// 广告位小点点（暗）
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
	 * 衣酷数据源
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
	 * 广告位适配器
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