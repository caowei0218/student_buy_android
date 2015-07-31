package com.example.student_buy_android.activity;

import java.util.ArrayList;

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

	private ArrayList<String> arrayList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish_layout);
		SysApplication.getInstance().addActivity(this);// 将该activity添加到管理类中去。

		init();

		initData();

		publishAdapter = new PublishAdapter(arrayList, PublishActivity.this);
		lv_publish.setAdapter(publishAdapter);

	}

	private void init() {
		lv_publish = (ListView) findViewById(R.id.lv_publish);
	}

	@Override
	public void onClick(View v) {

	}

	/**
	 * 返回
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			moveTaskToBack(false);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 衣酷数据源
	 * */
	private void initData() {
		arrayList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			arrayList.add("");
		}
	}

}
