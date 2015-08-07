package com.example.student_buy_android.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.student_buy_android.R;
import com.example.student_buy_android.activity.AddFriendActivity;
import com.example.student_buy_android.activity.ChatActivity;
import com.example.student_buy_android.activity.FriendInfoActivity;
import com.example.student_buy_android.adapter.FriendsAdapter;
import com.example.student_buy_android.adapter.LatestContactsAdapter;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.bean.Message;
import com.example.student_buy_android.bean.Message.Type;
import com.example.student_buy_android.db.MessageDao;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.webservice.GetFriendsWebservice;

@SuppressLint({ "ResourceAsColor", "InflateParams" })
public class ChatFragment extends Fragment implements OnClickListener {

	View messageLayout;

	private List<View> recently_contact_view;// 用来存放聊天模块界面
	private PagerAdapter recently_contact_Adapter;// 初始化View适配器
	private ViewPager recently_contact_viewPager;// 用来放置界面切换
	private ListView lv_latest_contact;
	private List<FriendBean> latestContactLsit;// 用来存放最近联系人
	private LatestContactsAdapter latestContactsAdapter;
	private ListView lv_friends;
	private List<FriendBean> friendBeans;// 用来存放好友列表
	private RelativeLayout rl_add_friends;
	private FriendsAdapter friendsAdapter;
	private Button btn_chat, btn_friends;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		messageLayout = inflater.inflate(R.layout.tab_message_layout,
				container, false);

		init();

		registerBoradcastReceiver();

		return messageLayout;
	}

	private void init() {
		recently_contact_viewPager = (ViewPager) messageLayout
				.findViewById(R.id.id_viewpage);
		recently_contact_viewPager
				.setOnPageChangeListener(new OnPageChangeListener() {
					// ViewPage左右滑动时
					@Override
					public void onPageSelected(int arg0) {
						int currentItem = recently_contact_viewPager
								.getCurrentItem();
						switch (currentItem) {
						case 0:
							resetChatColor();
							initLatestContacts();
							break;
						case 1:
							resetFriendsTextColor();
							initFriends();
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

		btn_chat = (Button) messageLayout.findViewById(R.id.btn_chat);
		btn_friends = (Button) messageLayout.findViewById(R.id.btn_friends);
		btn_chat.setOnClickListener(this);
		btn_friends.setOnClickListener(this);

		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		View tab_recently = layoutInflater.inflate(R.layout.tab_recently, null);
		View tab_friends = layoutInflater
				.inflate(R.layout.friends_layout, null);

		recently_contact_view = new ArrayList<View>();
		recently_contact_view.add(tab_recently);
		recently_contact_view.add(tab_friends);

		recently_contact_Adapter = new PagerAdapter() {
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(recently_contact_view.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = recently_contact_view.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return recently_contact_view.size();
			}

		};
		recently_contact_viewPager.setAdapter(recently_contact_Adapter);

		resetChatColor();
		initLatestContacts();
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
		List<FriendBean> friendBeans = new ArrayList<FriendBean>();
		FriendBean friendbean;
		MessageDao messageDao = new MessageDao();
		List<String> friends = messageDao.get_communication_last();
		for (int i = 0; i < friends.size(); i++) {
			friendbean = new FriendBean();
			friendbean.setUsername(friends.get(i));
			friendbean.setLast_message(messageDao.get_last_message(
					Common.userBean.getUsername(), friends.get(i)));
			friendBeans.add(friendbean);
		}
		return friendBeans;
	}

	/**
	 * ListView item点击事件
	 * */
	private void setOnItemClickListener() {
		lv_latest_contact = (ListView) recently_contact_view.get(0)
				.findViewById(R.id.lv_latest_contact);

		lv_latest_contact.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 跳转聊天界面
				Intent intent = new Intent(getActivity(), ChatActivity.class);
				FriendBean friendBean = latestContactLsit.get(position);
				intent.putExtra("friendBean", friendBean);
				// startActivity(intent);
				startActivityForResult(intent, 1);
				getActivity().overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// 实现淡入浅出的效果
			}
		});
	}

	/**
	 * 最近联系人 绑定Adapter
	 * */
	private void setLatestContactsAdapter() {
		latestContactsAdapter = new LatestContactsAdapter(latestContactLsit,
				getActivity());
		lv_latest_contact.setAdapter(latestContactsAdapter);
	}

	/**
	 * 初始化好友列表
	 * */
	private void initFriends() {
		lv_friends = (ListView) recently_contact_view.get(1).findViewById(
				R.id.friends_list);
		rl_add_friends = (RelativeLayout) recently_contact_view.get(1)
				.findViewById(R.id.rl_add_friends);

		rl_add_friends.setOnClickListener(this);

		setFriendsListAdapter();

		GetFriendsWebservice getFriendListWebservice = new GetFriendsWebservice(
				getActivity());
		getFriendListWebservice.execute();

		// ListView item点击事件
		lv_friends.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 跳转好友详情页面
				Intent intent = new Intent(getActivity(),
						FriendInfoActivity.class);
				FriendBean friendBean = friendBeans.get(position);
				intent.putExtra("friendBean", friendBean);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// 实现淡入浅出的效果
			}
		});
	}

	/**
	 * 好友集合 绑定Adapter
	 * */
	private void setFriendsListAdapter() {
		MessageDao messageDao = new MessageDao();
		this.friendBeans = messageDao.get_friend_list();
		friendsAdapter = new FriendsAdapter(this.friendBeans, getActivity());
		lv_friends.setAdapter(friendsAdapter);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_chat:
			initLatestContacts();
			recently_contact_viewPager.setCurrentItem(0);
			resetChatColor();
			break;
		case R.id.btn_friends:
			initFriends();
			recently_contact_viewPager.setCurrentItem(1);
			resetFriendsTextColor();
			break;
		case R.id.rl_add_friends:
			intent = new Intent(getActivity(), AddFriendActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			initLatestContacts();
		}
	}

	/**
	 * 把聊天tab页文字变暗
	 */
	private void resetChatColor() {
		btn_chat.setTextColor(R.color.black);
		btn_friends.setTextColor(R.color.red);
	}

	/**
	 * 把好友tab页文字变暗
	 */
	private void resetFriendsTextColor() {
		btn_chat.setTextColor(R.color.red);
		btn_friends.setTextColor(R.color.black);
	}

	/**
	 * 注册广播
	 * */
	private void registerBoradcastReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("message_received_action");
		MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver();
		getActivity().registerReceiver(broadcastReceiver, intentFilter);
	}

	/**
	 * 广播接收器
	 * */
	public class MyBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Message message = (Message) intent.getExtras().get("message");
			message.setType(Type.INPUT);
			initLatestContacts();
		}
	}

}
