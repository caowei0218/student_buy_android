package com.example.student_buy_android.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.student_buy_android.R;
import com.example.student_buy_android.adapter.ShowAdapter;
import com.example.student_buy_android.util.SysApplication;

public class ShowActivity extends BaseActivity implements OnClickListener {
	private DisplayMetrics displayMetrics;
	private LayoutParams para;
	private RelativeLayout rl_title;
	private int window_width;
	private int window_height;

	private Button btn_add, btn_my;

	private ListView lv_show;
	private ShowAdapter showAdapter;
	private ArrayList<ArrayList<HashMap<String, Object>>> arrayLists;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_activity);

		SysApplication.getInstance().addActivity(this);// 将该activity添加到管理类中去。

		init();
	}

	private void init() {
		findViewById();
		adaptation();
		setOnClickListener();

		initData();
		showAdapter = new ShowAdapter(arrayLists, ShowActivity.this);
		lv_show.setAdapter(showAdapter);
	}

	private void findViewById() {
		lv_show = (ListView) findViewById(R.id.lv_show);
		rl_title = (RelativeLayout) findViewById(R.id.rl_title);
		btn_add = (Button) findViewById(R.id.btn_add);
		btn_my = (Button) findViewById(R.id.btn_my);

	}

	private void adaptation() {
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		window_height = displayMetrics.heightPixels;
		window_width = displayMetrics.widthPixels;

		para = rl_title.getLayoutParams();
		para.height = (int) (window_height * (88.0 / 1334));
		para.width = (int) window_width;
		rl_title.setLayoutParams(para);
	}

	private void setOnClickListener() {
		btn_add.setOnClickListener(this);
		btn_my.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_add:
			intent = new Intent(ShowActivity.this, FriendsActivity.class);
			startActivity(intent);
			break;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
		}
	}

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
	 * 返回键不退出程序 后台运行
	 * */
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// moveTaskToBack(false);
	// return true;
	// }
	// return super.onKeyDown(keyCode, event);
	// }

}
