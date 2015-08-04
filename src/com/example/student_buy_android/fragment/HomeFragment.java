package com.example.student_buy_android.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.adapter.ShowAdapter;

@SuppressLint("HandlerLeak")
public class HomeFragment extends Fragment implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	View messageLayout;

	private ListView lv_show;
	private ShowAdapter showAdapter;
	private ArrayList<ArrayList<HashMap<String, Object>>> arrayLists;// 用来存放衣酷展示内容
	private ViewPager showViewPager;
	private ImageView[] imageViews;
	private ImageView imageView;
	private AtomicInteger what = new AtomicInteger(0);
	private boolean isContinue = true;
	private List<View> advPics;
	private DisplayMetrics displayMetrics;
	private int window_width;
	private int window_height;
	private LayoutParams para;
	private ImageView seasons, coat, footwear, other;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		messageLayout = inflater.inflate(R.layout.tab_show, container, false);

		init();
		initData();

		return messageLayout;
	}

	private void init() {
		displayMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(displayMetrics);
		window_width = displayMetrics.widthPixels;
		window_height = displayMetrics.heightPixels;

		showViewPager = (ViewPager) messageLayout.findViewById(R.id.adv_pager);
		seasons = (ImageView) messageLayout.findViewById(R.id.seasons);
		coat = (ImageView) messageLayout.findViewById(R.id.coat);
		footwear = (ImageView) messageLayout.findViewById(R.id.footwear);
		other = (ImageView) messageLayout.findViewById(R.id.other);
		lv_show = (ListView) messageLayout.findViewById(R.id.lv_show);

		adaptation();

		initViewPager();

		showAdapter = new ShowAdapter(arrayLists, this.getActivity());
		lv_show.setAdapter(showAdapter);
	}

	/**
	 * 衣酷数据源
	 * */
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
	 * 屏幕比例适配
	 * */
	private void adaptation() {
		para = seasons.getLayoutParams();
		para.height = (int) (window_height * (120.0 / 1334));
		para.width = (int) (window_width * (120.0 / 750));
		seasons.setLayoutParams(para);

		para = coat.getLayoutParams();
		para.height = (int) (window_height * (120.0 / 1334));
		para.width = (int) (window_width * (120.0 / 750));
		coat.setLayoutParams(para);

		para = footwear.getLayoutParams();
		para.height = (int) (window_height * (120.0 / 1334));
		para.width = (int) (window_width * (120.0 / 750));
		footwear.setLayoutParams(para);

		para = other.getLayoutParams();
		para.height = (int) (window_height * (120.0 / 1334));
		para.width = (int) (window_width * (120.0 / 750));
		other.setLayoutParams(para);

		para = showViewPager.getLayoutParams();
		para.height = (int) (window_height * (200.0 / 1334));
		showViewPager.setLayoutParams(para);
	}

	/**
	 * 广告位
	 * */
	private void initViewPager() {
		ViewGroup viewGroup = (ViewGroup) messageLayout
				.findViewById(R.id.viewGroup);

		if (imageViews == null) {
			advPics = new ArrayList<View>();
			ImageView img1 = new ImageView(getActivity());
			img1.setBackgroundResource(R.drawable.advertising1);// 广告位图片
			advPics.add(img1);
			ImageView img2 = new ImageView(getActivity());
			img2.setBackgroundResource(R.drawable.advertising2);
			advPics.add(img2);
			ImageView img3 = new ImageView(getActivity());
			img3.setBackgroundResource(R.drawable.advertising3);
			advPics.add(img3);
			imageViews = new ImageView[advPics.size()];

			for (int i = 0; i < imageViews.length; i++) {
				imageView = new ImageView(getActivity());
				imageView.setLayoutParams(new LayoutParams(20, 20));
				imageView.setPadding(5, 5, 5, 5);
				imageViews[i] = imageView;
				if (i == 0) {
					imageViews[i]
							.setBackgroundResource(R.drawable.banner_dian_focus);// 广告位小点点（亮）
				} else {
					imageViews[i]
							.setBackgroundResource(R.drawable.banner_dian_blur);// 广告位小点点（暗）
				}
				viewGroup.addView(imageViews[i]);
			}
		}

		showViewPager.setAdapter(new AdvAdapter(advPics));
		showViewPager.setOnPageChangeListener(new GuidePageChangeListener());
		showViewPager.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					isContinue = false;
					break;
				case MotionEvent.ACTION_UP:
					isContinue = true;
					break;
				default:
					isContinue = true;
					break;
				}
				return false;
			}
		});
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (isContinue) {
						viewHandler.sendEmptyMessage(what.get());
						whatOption();
					}
				}
			}
		}).start();
	}

	/**
	 * 广告位适配器
	 * */
	private final class AdvAdapter extends PagerAdapter {
		private List<View> views = null;

		public AdvAdapter(List<View> views) {
			this.views = views;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		public void finishUpdate(View arg0) {
		}

		public int getCount() {
			return views.size();
		}

		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {
		}
	}

	private final class GuidePageChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			what.getAndSet(arg0);
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.banner_dian_focus);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.banner_dian_blur);
				}
			}
		}
	}

	private final Handler viewHandler = new Handler() {

		public void handleMessage(Message msg) {
			showViewPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}
	};

	private void whatOption() {
		what.incrementAndGet();
		if (what.get() > imageViews.length - 1) {
			what.getAndAdd(-4);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onClick(View v) {

	}

}
