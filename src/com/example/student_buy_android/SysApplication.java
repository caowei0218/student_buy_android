package com.example.student_buy_android;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

/**
 * 处理按下退出键，直接退出应用。
 * */
public class SysApplication extends Application {
	private static List<Activity> mList = new LinkedList<Activity>();
	private static SysApplication instance;

	private SysApplication() {
	}

	public synchronized static SysApplication getInstance() {
		if (null == instance) {
			instance = new SysApplication();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	/**
	 * 结束所有Activity LoginActivity没有添加如管理类中
	 * */
	public static void exitAllActivity() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 结束所有Activity并且退出程序
	 * */
	public static void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}
}
