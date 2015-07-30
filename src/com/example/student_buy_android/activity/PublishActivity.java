package com.example.student_buy_android.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.SysApplication;
import com.example.student_buy_android.adapter.PublishAdapter;

public class PublishActivity extends BaseActivity implements OnClickListener {

	private ListView lv_publish;

	private PublishAdapter publishAdapter;

	private ArrayList<ArrayList<HashMap<String, Object>>> list;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish_layout);
		SysApplication.getInstance().addActivity(this);// ����activity��ӵ���������ȥ��

		init();

		initData();
		publishAdapter = new PublishAdapter(list, PublishActivity.this);
		lv_publish.setAdapter(publishAdapter);
	}

	private void init() {
		lv_publish = (ListView) findViewById(R.id.lv_publish);
	}

	@Override
	public void onClick(View v) {

	}

	/**
	 * ����
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			moveTaskToBack(false);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �¿�����Դ
	 * */
	private void initData() {
		list = new ArrayList<ArrayList<HashMap<String, Object>>>();
		HashMap<String, Object> hashMap = null;
		ArrayList<HashMap<String, Object>> arrayListForEveryGridView;

		for (int i = 0; i < 2; i++) {
			arrayListForEveryGridView = new ArrayList<HashMap<String, Object>>();
			for (int j = 0; j < 3; j++) {
				hashMap = new HashMap<String, Object>();
				hashMap.put("content", "i=" + i + " ,j=" + j);
				arrayListForEveryGridView.add(hashMap);
			}
			list.add(arrayListForEveryGridView);
		}
	}
}
