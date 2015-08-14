package com.example.student_buy_android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.student_buy_android.R;
import com.example.student_buy_android.util.BitmapCache;
import com.example.student_buy_android.util.BitmapUtil;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.view.CustomNetworkImageView;

@SuppressLint({ "ViewHolder", "InflateParams" })
public class HomeAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;

	private ImageLoader imageLoader;
	private RequestQueue mQueue;

	public HomeAdapter(Context context) {
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.layout_first_item, null);
			holder.home_img1 = (CustomNetworkImageView) convertView
					.findViewById(R.id.home_img1);
			holder.home_img2 = (CustomNetworkImageView) convertView
					.findViewById(R.id.home_img2);
			holder.home_img3 = (CustomNetworkImageView) convertView
					.findViewById(R.id.home_img3);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 如果图片本地有 就直接去本地加载 没有再去网络加载
		if (BitmapUtil.isExists(Common.photoName[position])) {
			holder.home_img1.setLocalImageBitmap(BitmapUtil
					.getLocalBitmap(Common.photoName[position]));
		} else {
			// Volley框架 头像加载
			mQueue = Volley.newRequestQueue(context);
			imageLoader = new ImageLoader(mQueue, new BitmapCache(
					Common.photoName[position]));
			holder.home_img1.setImageUrl(Common.IMAGES_URL
					+ Common.photoName[position], imageLoader);
		}

		// 如果图片本地有 就直接去本地加载 没有再去网络加载
		if (BitmapUtil.isExists(Common.photoName[position + 1])) {
			holder.home_img2.setLocalImageBitmap(BitmapUtil
					.getLocalBitmap(Common.photoName[position + 1]));
		} else {
			// Volley框架 头像加载
			mQueue = Volley.newRequestQueue(context);
			imageLoader = new ImageLoader(mQueue, new BitmapCache(
					Common.photoName[position + 1]));
			holder.home_img2.setImageUrl(Common.IMAGES_URL
					+ Common.photoName[position + 1], imageLoader);
		}

		// 如果图片本地有 就直接去本地加载 没有再去网络加载
		if (BitmapUtil.isExists(Common.photoName[position + 2])) {
			holder.home_img3.setLocalImageBitmap(BitmapUtil
					.getLocalBitmap(Common.photoName[position + 2]));
		} else {
			// Volley框架 头像加载
			mQueue = Volley.newRequestQueue(context);
			imageLoader = new ImageLoader(mQueue, new BitmapCache(
					Common.photoName[position + 2]));
			holder.home_img3.setImageUrl(Common.IMAGES_URL
					+ Common.photoName[position + 2], imageLoader);
		}

		convertView.setTag(holder);// 绑定ViewHolder对象

		return convertView;
	}

	class ViewHolder {
		CustomNetworkImageView home_img1, home_img2, home_img3;
		RelativeLayout ll_user;
		TextView tv_save;
	}

}
