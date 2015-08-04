package com.example.student_buy_android.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止ViewPager左右滑动
 * */
@SuppressLint("ClickableViewAccessibility")
public class CustomViewPager extends ViewPager {

	private boolean scroll = true;

	public CustomViewPager(Context context) {
		super(context);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScroll(boolean scroll) {
		this.scroll = scroll;
	}

	@Override
	public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}

	/**
	 * 事件分发器，false忽略当前优先级的点击时间
	 * */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (scroll) {
			return false;
		} else {
			return super.onInterceptTouchEvent(arg0);
		}
	}

	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		super.setCurrentItem(item, smoothScroll);
	}

	@Override
	public void setCurrentItem(int item) {
		super.setCurrentItem(item);
	}

}
