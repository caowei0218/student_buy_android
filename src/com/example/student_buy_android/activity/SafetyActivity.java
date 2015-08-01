package com.example.student_buy_android.activity;

import com.example.student_buy_android.R;
import com.example.student_buy_android.SysApplication;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SafetyActivity extends BaseActivity implements OnClickListener{

	private RelativeLayout rl_phone,rl_email;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.safety_layout);
		SysApplication.getInstance().addActivity(this);// ����activity��ӵ���������ȥ��

		init();
	}
	
	private void init() {
		rl_phone = (RelativeLayout)findViewById(R.id.rl_phone);
		rl_email = (RelativeLayout)findViewById(R.id.rl_email);
		
		rl_phone.setOnClickListener(this);
		rl_email.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_phone:
			Toast.makeText(this, "������", Toast.LENGTH_SHORT).show();
			break;
		case R.id.rl_email:
			Toast.makeText(this, "������", Toast.LENGTH_SHORT).show();
			break;
		}
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

}
