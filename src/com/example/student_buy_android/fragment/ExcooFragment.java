package com.example.student_buy_android.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.adapter.ExcooAdapter;

@SuppressLint({ "ResourceAsColor", "InflateParams" })
public class ExcooFragment extends Fragment implements OnClickListener {

	View messageLayout;

	private ViewPager excoo_viewPager;// �������ý����л�
	private PagerAdapter excoo_Adapter;// ��ʼ��View������
	private Button btn_wardrobe, btn_collection;
	private List<View> excoo_view;// ��������¿�ģ�����
	private ListView lv_wardrobe;
	private ListView lv_collection;

	// private ListView listView;
	private ExcooAdapter excooAdapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		messageLayout = inflater.inflate(R.layout.tab_excoo, container, false);

		initView();

		return messageLayout;
	}

	/**
	 * ��ʼ��
	 */
	private void initView() {
		excoo_viewPager = (ViewPager) messageLayout
				.findViewById(R.id.id_viewpage);
		excoo_viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// ViewPage���һ���ʱ
			@Override
			public void onPageSelected(int arg0) {
				int currentItem = excoo_viewPager.getCurrentItem();
				switch (currentItem) {
				case 0:
					resetWardrobeColor();
					initWardrobe();
					break;
				case 1:
					resetCollectionColor();
					initCollection();
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		btn_wardrobe = (Button) messageLayout.findViewById(R.id.btn_wardrobe);
		btn_collection = (Button) messageLayout
				.findViewById(R.id.btn_collection);
		btn_wardrobe.setOnClickListener(this);
		btn_collection.setOnClickListener(this);

		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		View tab_wardrobe = layoutInflater.inflate(R.layout.tab_wardrobe, null);
		View tab_collection = layoutInflater.inflate(R.layout.tab_collection,
				null);

		excoo_view = new ArrayList<View>();
		excoo_view.add(tab_wardrobe);
		excoo_view.add(tab_collection);

		excoo_Adapter = new PagerAdapter() {
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(excoo_view.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = excoo_view.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return excoo_view.size();
			}

		};
		excoo_viewPager.setAdapter(excoo_Adapter);

		resetWardrobeColor();
		initWardrobe();
	}

	/**
	 * ��ʼ���ҵ��³�
	 * */
	private void initWardrobe() {
		lv_wardrobe = (ListView) excoo_view.get(0).findViewById(
				R.id.lv_wardrobe);
		excooAdapter = new ExcooAdapter(getActivity());
		lv_wardrobe.setAdapter(excooAdapter);
	}

	/**
	 * ��ʼ���ҵ��ղ�
	 * */
	private void initCollection() {
		lv_collection = (ListView) excoo_view.get(1).findViewById(
				R.id.lv_collection);
	}

	/**
	 * ���³�tabҳ���ֱ䰵
	 */
	private void resetWardrobeColor() {
		btn_wardrobe.setTextColor(R.color.black);
		btn_collection.setTextColor(R.color.red);
	}

	/**
	 * ���ղ�tabҳ���ֱ䰵
	 */
	private void resetCollectionColor() {
		btn_wardrobe.setTextColor(R.color.red);
		btn_collection.setTextColor(R.color.black);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_wardrobe:
			initWardrobe();
			excoo_viewPager.setCurrentItem(0);
			resetWardrobeColor();
			break;
		case R.id.btn_collection:
			initCollection();
			excoo_viewPager.setCurrentItem(1);
			resetCollectionColor();
			break;
		}
	}
}
