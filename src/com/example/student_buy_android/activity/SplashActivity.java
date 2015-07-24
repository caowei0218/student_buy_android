package com.example.student_buy_android.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.example.student_buy_android.R;
import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.util.SysApplication;

/**
 * 启动Activity
 * */
public class SplashActivity extends BaseActivity {
	private static final String SHAREDPREFERENCES_NAME = "FIRST_PREF";
	private boolean isFirstIn = false;
	private Handler mHandler = new Handler();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// app启动界面
		SysApplication.getInstance().addActivity(this);// 将该activity添加到管理类中去。
		View view = View.inflate(this, R.layout.start_layout, null);
		setContentView(view);

		getFirstIn();
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		view.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation arg0) {
			}

			public void onAnimationRepeat(Animation arg0) {
			}

			public void onAnimationEnd(Animation arg0) {
				mHandler.postDelayed(new Runnable() {
					public void run() {
						if (isFirstIn) {// 不是第一次进入
							goHome();
						} else {// 是第一次进入该应用
							goGuide();
						}
					}
				}, 2000);
			}
		});
	}

	/**
	 * 进入引导页面
	 * */
	private void goGuide() {
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("isFirstIn", true);
		editor.commit();
		isFirstIn = preferences.getBoolean("isFirstIn", false);
		Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}

	/**
	 * 获得是否为第一次打开APP标识
	 * */
	private void getFirstIn() {
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		// 取得相应的值，如果没有该值，说明还未写入，用false作为默认值
		isFirstIn = preferences.getBoolean("isFirstIn", false);
	}

	/**
	 * 进入主界面
	 * */
	private void goHome() {
		String account = getAccount();
		String password = getPassword();
		String id = getId();
		SharedPreferences sp = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		String email = sp.getString("email", "");
		String phoneNumber = sp.getString("phoneNumber", "");
		String nickName = sp.getString("nickName", "");
		String gender = sp.getString("gender", "");
		String age = sp.getString("age_group", "");
		if ("".equals(account)) {
			Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
			startActivity(intent);
			SplashActivity.this.finish();
		} else if (account != null && !"".equals(account)) {
			UserBean userBean = new UserBean();
			userBean.setUsername(account);
			userBean.setPassword(password);
			userBean.setUser_id(id);
			userBean.setEmail(email);
			userBean.setPhoneNumber(phoneNumber);
			userBean.setNickname(nickName);
			userBean.setGender(gender);
			userBean.setAge(age);
			Common.userCommon = userBean;
			// 登陆异步处理
			// LoginAgainWebservice loginWebservice = new LoginAgainWebservice(
			// SplashActivity.this, this, userBean);
			// loginWebservice.execute();

			Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);//实现淡入浅出的效果
			SplashActivity.this.finish();
		}
	}

	/**
	 * 从SharedPreferences中获取用户名
	 * */
	public String getAccount() {
		String account = null;
		SharedPreferences sp = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		account = sp.getString("username", "");
		return account;
	}

	/**
	 * 从SharedPreferences中获取密码
	 * */
	public String getPassword() {
		String password = null;
		SharedPreferences sp = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		password = sp.getString("password", "");
		return password;
	}

	public String getId() {
		String id = null;
		SharedPreferences sp = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		id = sp.getString("id", "");
		return id;
	}
}