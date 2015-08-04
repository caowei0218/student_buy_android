package com.example.student_buy_android.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.activity.ChatActivity;
import com.example.student_buy_android.activity.FriendInfoActivity;
import com.example.student_buy_android.adapter.FriendsAdapter;
import com.example.student_buy_android.adapter.LatestContactsAdapter;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.db.MessageDao;
import com.example.student_buy_android.webservice.GetFriendsWebservice;

@SuppressLint({ "ResourceAsColor", "InflateParams" })
public class ChatFragment extends Fragment implements OnClickListener {

	View messageLayout;

	private List<View> recently_contact_view;// �����������ģ�����
	private PagerAdapter recently_contact_Adapter;// ��ʼ��View������
	private ViewPager recently_contact_viewPager;// �������ý����л�
	private LinearLayout latest_contact, friends;// �������ý����л�
	private ListView lv_latest_contact;
	private List<FriendBean> latestContactLsit;// ������������ϵ��
	private LatestContactsAdapter latestContactsAdapter;
	private ListView lv_friends;
	private List<FriendBean> friendBeans;// ������ź����б�
	private FriendsAdapter friendsAdapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		messageLayout = inflater.inflate(R.layout.tab_message_layout,
				container, false);

		init();

		return messageLayout;
	}

	private void init() {
		recently_contact_viewPager = (ViewPager) messageLayout
				.findViewById(R.id.id_viewpage);
		recently_contact_viewPager
				.setOnPageChangeListener(new OnPageChangeListener() {
					// ViewPage���һ���ʱ
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

		latest_contact = (LinearLayout) messageLayout
				.findViewById(R.id.latest_contact);
		friends = (LinearLayout) messageLayout.findViewById(R.id.friends);

		latest_contact.setOnClickListener(this);
		friends.setOnClickListener(this);

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
		lv_latest_contact = (ListView) recently_contact_view.get(0)
				.findViewById(R.id.lv_latest_contact);

		lv_latest_contact.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ��ת��������ҳ��
				Intent intent = new Intent(getActivity(), ChatActivity.class);
				FriendBean friendBean = latestContactLsit.get(position);
				intent.putExtra("friendBean", friendBean);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			}
		});
	}

	/**
	 * �����ϵ�� ��Adapter
	 * */
	private void setLatestContactsAdapter() {
		latestContactsAdapter = new LatestContactsAdapter(latestContactLsit,
				getActivity());
		lv_latest_contact.setAdapter(latestContactsAdapter);
	}

	/**
	 * ��ʼ�������б�
	 * */
	private void initFriends() {
		lv_friends = (ListView) recently_contact_view.get(1).findViewById(
				R.id.friends_list);

		GetFriendsWebservice getFriendListWebservice = new GetFriendsWebservice(
				this, getActivity());
		getFriendListWebservice.execute();

		// ListView item����¼�
		lv_friends.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ��ת��������ҳ��
				Intent intent = new Intent(getActivity(),
						FriendInfoActivity.class);
				FriendBean friendBean = friendBeans.get(position);
				intent.putExtra("friendBean", friendBean);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			}
		});
	}

	/**
	 * ���Ѽ��� ��Adapter
	 * */
	public void setFriendsListAdapter(List<FriendBean> friendBeans) {
		this.friendBeans = friendBeans;
		friendsAdapter = new FriendsAdapter(friendBeans, getActivity());
		lv_friends.setAdapter(friendsAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.latest_contact:
			initLatestContacts();
			recently_contact_viewPager.setCurrentItem(0);
			resetChatColor();
			break;
		case R.id.friends:
			initFriends();
			recently_contact_viewPager.setCurrentItem(1);
			resetFriendsTextColor();
			break;
		}
	}

	/**
	 * ������tabҳ���ֱ䰵
	 */
	private void resetChatColor() {
		latest_contact.setBackgroundColor(R.color.grey);
		friends.setBackgroundColor(R.color.red);
	}

	/**
	 * �Ѻ���tabҳ���ֱ䰵
	 */
	private void resetFriendsTextColor() {
		latest_contact.setBackgroundColor(R.color.red);
		friends.setBackgroundColor(R.color.grey);
	}

}
