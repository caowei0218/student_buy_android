package com.example.student_buy_android.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.student_buy_android.R;

/**
 * ����ҳ
 */
@SuppressLint("InflateParams")
public class GuideActivity extends BaseActivity {

	private ViewPager mViewPager;
	private ArrayList<View> mViews;
	private PopupWindow mPopupWindow;
	private View mPopupView;
	private MyHandler mMyHandler;
	private LinearLayout mIndicatorLayout; // ����װСԲ���Linearlayout
	private ArrayList<ImageView> mIndicatorList;// װСԲ��ļ���

	@SuppressLint("HandlerLeak")
	private class MyHandler extends Handler {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 9:
				// ���������handler���ӳ�������Ȼ�ᱨ�쳣
				mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
				mPopupWindow.showAtLocation(findViewById(R.id.txt_Activity),
						Gravity.CENTER, 0, 0);
				break;
			default:
				break;
			}
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȥ����Ϣ��

		setContentView(R.layout.guide_laylout);

		mMyHandler = new MyHandler();
		mIndicatorList = new ArrayList<ImageView>();

		iniView();
		iniViewPager();// ��ʼ��ViewPager

		new Thread(new Runnable() {
			public void run() {
				mMyHandler.sendEmptyMessageDelayed(9, 200);
			}
		}).start();
	}

	private void iniView() {
		mPopupView = getLayoutInflater().inflate(R.layout.popupwindow, null);
		mViewPager = (ViewPager) mPopupView.findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(5);
		// ����СԲ���LinearLayout
		mIndicatorLayout = (LinearLayout) mPopupView
				.findViewById(R.id.indicatorLayout);

		mPopupWindow = new PopupWindow(mPopupView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
	}

	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	private void iniViewPager() {
		View v1 = getLayoutInflater().inflate(R.layout.layout_1, null);
		View v2 = getLayoutInflater().inflate(R.layout.layout_2, null);
		View v3 = getLayoutInflater().inflate(R.layout.layout_3, null);
		View v4 = getLayoutInflater().inflate(R.layout.layout_4, null);
		View v5 = getLayoutInflater().inflate(R.layout.layout_5, null);

		ImageView img1 = (ImageView) v1.findViewById(R.id.img1);
		ImageView img2 = (ImageView) v2.findViewById(R.id.img2);
		ImageView img3 = (ImageView) v3.findViewById(R.id.img3);
		ImageView img4 = (ImageView) v4.findViewById(R.id.img4);
		ImageView img5 = (ImageView) v5.findViewById(R.id.img5);

		// ����ͼƬ͸����
		img1.setAlpha(180);
		img2.setAlpha(180);
		img3.setAlpha(180);
		img4.setAlpha(180);
		img5.setAlpha(180);

		// ������һ��ͼƬ������������˳�
		Button start = (Button) v5.findViewById(R.id.txt_start);
		start.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(GuideActivity.this,
						LoginActivity.class));
				GuideActivity.this.finish();
				mPopupWindow.dismiss();
			}
		});

		mViews = new ArrayList<View>();
		mViews.add(v1);
		mViews.add(v2);
		mViews.add(v3);
		mViews.add(v4);
		mViews.add(v5);

		// ����������
		mViewPager.setAdapter(new MyPagerAdapter());
		// ���ü����¼�
		mViewPager.setOnPageChangeListener(new MyPagerChangeListener());

		// ����СԲ��
		for (int i = 0; i < mViews.size(); i++) {
			ImageView indicator = new ImageView(this);
			if (i == 0) {
				indicator.setImageResource(R.drawable.point01);
			} else {
				indicator.setImageResource(R.drawable.point02);
			}

			indicator.setLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			mIndicatorLayout.addView(indicator);
			// ��СԲ��ͼƬ���뼯��,�����л�ͼ��Ƭ��ʱ��̬�ı�СԲ�㱳��
			mIndicatorList.add(indicator);
		}
	}

	private class MyPagerAdapter extends PagerAdapter {

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mViews.get(arg1));
		}

		public void finishUpdate(View arg0) {

		}

		public int getCount() {
			return mViews.size();
		}

		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mViews.get(arg1));
			return mViews.get(arg1);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			for (int i = 0; i < mViews.size(); i++) {

			}
		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {

		}
	}

	private class MyPagerChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		public void onPageSelected(int position) {
			for (int i = 0; i < mViews.size(); i++) {
				mIndicatorList.get(i).setImageResource(R.drawable.point02);
			}
			mIndicatorList.get(position).setImageResource(R.drawable.point01);
		}
	}
}
