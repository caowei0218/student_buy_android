package com.example.student_buy_android.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.student_buy_android.R;
import com.example.student_buy_android.activity.LoginActivity;
import com.example.student_buy_android.activity.MyInfoActivity;
import com.example.student_buy_android.activity.PublishActivity;
import com.example.student_buy_android.activity.SetInfoActivity;
import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.util.BitmapCache;
import com.example.student_buy_android.util.BitmapUtil;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.view.CustomNetworkImageView;
import com.example.student_buy_android.webservice.GetMyInfoWebservice;

public class MyFragment extends Fragment implements OnClickListener {

	View messageLayout;

	private TextView tv_nickname, tv_username, tv_publish_number;
	private RelativeLayout rl_info, rl_publish, rl_sell, rl_buy, rl_collect,
			rl_setting;
	private CustomNetworkImageView iv_avatar;

	private ImageLoader imageLoader;
	private RequestQueue mQueue;

	private String account;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		messageLayout = inflater.inflate(R.layout.tab_my, container, false);

		init();
		if (!"".equals(account)) {
			getMyInfo();
		}

		return messageLayout;
	}

	private void init() {
		rl_info = (RelativeLayout) messageLayout.findViewById(R.id.rl_info);
		rl_publish = (RelativeLayout) messageLayout
				.findViewById(R.id.rl_publish);
		rl_sell = (RelativeLayout) messageLayout.findViewById(R.id.rl_sell);
		rl_buy = (RelativeLayout) messageLayout.findViewById(R.id.rl_buy);
		rl_collect = (RelativeLayout) messageLayout
				.findViewById(R.id.rl_collect);
		rl_setting = (RelativeLayout) messageLayout
				.findViewById(R.id.rl_setting);
		tv_nickname = (TextView) messageLayout.findViewById(R.id.tv_nickname);
		tv_username = (TextView) messageLayout.findViewById(R.id.tv_username);
		tv_publish_number = (TextView) messageLayout
				.findViewById(R.id.tv_publish_number);
		iv_avatar = (CustomNetworkImageView) messageLayout
				.findViewById(R.id.iv_avatar);

		rl_info.setOnClickListener(this);
		rl_publish.setOnClickListener(this);
		rl_sell.setOnClickListener(this);
		rl_buy.setOnClickListener(this);
		rl_collect.setOnClickListener(this);
		rl_setting.setOnClickListener(this);

		SharedPreferences sp = getActivity().getSharedPreferences("user",
				Context.MODE_PRIVATE);
		account = sp.getString("username", "");
	}

	/**
	 * 获得我的个人信息
	 * */
	private void getMyInfo() {
		GetMyInfoWebservice getMyInfoWebservice = new GetMyInfoWebservice(
				MyFragment.this, this.getActivity());
		getMyInfoWebservice.execute();
	}

	/**
	 * 修改页面数据
	 * */
	public void updateData(UserBean userBean) {
		tv_username.setText(userBean.getUsername());
		tv_nickname.setText(userBean.getNickname());

		// 如果头像在本地有 就直接去本地加载 没有再去网络加载
		if (BitmapUtil.isExists(userBean.getPhotoName())) {
			iv_avatar.setLocalImageBitmap(BitmapUtil.getLocalBitmap(userBean
					.getPhotoName()));
		} else {
			// Volley框架 头像加载
			mQueue = Volley.newRequestQueue(getActivity());
			imageLoader = new ImageLoader(mQueue, new BitmapCache(
					userBean.getPhotoName()));
			iv_avatar.setImageUrl(Common.IMAGES_URL + userBean.getPhotoName(),
					imageLoader);
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.rl_info:
			if (!"".equals(account)) {
				intent = new Intent(getActivity(), MyInfoActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// 实现淡入浅出的效果
			} else {
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);// 实现淡入浅出的效果
			}
			break;
		case R.id.rl_publish:
			intent = new Intent(getActivity(), PublishActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.rl_sell:
			Toast.makeText(getActivity(), "待开发", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_buy:
			Toast.makeText(getActivity(), "待开发", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_collect:
			Toast.makeText(getActivity(), "待开发", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_setting:
			intent = new Intent(getActivity(), SetInfoActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		default:
			break;
		}
	}
}
