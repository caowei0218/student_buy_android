package com.example.student_buy_android.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student_buy_android.R;
import com.example.student_buy_android.activity.MyInfoActivity;
import com.example.student_buy_android.activity.PublishActivity;
import com.example.student_buy_android.activity.SetInfoActivity;
import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.webservice.GetMyInfoWebservice;

public class MyFragment extends Fragment implements OnClickListener {

	View messageLayout;

	private TextView tv_nickname, tv_username, tv_publish_number;
	private RelativeLayout rl_info, rl_publish, rl_sell, rl_buy, rl_collect,
			rl_setting;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		messageLayout = inflater
				.inflate(R.layout.tab_setting, container, false);

		init();
		getMyInfo();

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

		rl_info.setOnClickListener(this);
		rl_publish.setOnClickListener(this);
		rl_sell.setOnClickListener(this);
		rl_buy.setOnClickListener(this);
		rl_collect.setOnClickListener(this);
		rl_setting.setOnClickListener(this);
	}

	/**
	 * ����ҵĸ�����Ϣ
	 * */
	private void getMyInfo() {
		GetMyInfoWebservice getMyInfoWebservice = new GetMyInfoWebservice(
				MyFragment.this, this.getActivity());
		getMyInfoWebservice.execute();
	}

	/**
	 * �޸�ҳ������
	 * */
	public void updateData(UserBean userBean) {
		tv_username.setText(userBean.getUsername());
		tv_nickname.setText(userBean.getNickname());
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.rl_info:
			intent = new Intent(getActivity(), MyInfoActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			break;
		case R.id.rl_publish:
			intent = new Intent(getActivity(), PublishActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			break;
		case R.id.rl_sell:
			Toast.makeText(getActivity(), "������", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_buy:
			Toast.makeText(getActivity(), "������", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_collect:
			Toast.makeText(getActivity(), "������", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_setting:
			intent = new Intent(getActivity(), SetInfoActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			break;
		default:
			break;
		}
	}

}