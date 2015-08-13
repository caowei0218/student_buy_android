package com.example.student_buy_android.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.student_buy_android.R;
import com.example.student_buy_android.util.BitmapCache;
import com.example.student_buy_android.util.Common;

@SuppressLint("InflateParams")
public class PublishAdapter extends BaseAdapter {
	private ImageLoader imageLoader;
	private RequestQueue mQueue;

	private ArrayList<String> arrayList;
	private Context context;

	public PublishAdapter(ArrayList<String> arrayList, Context context) {
		this.arrayList = arrayList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.publish_listview_item, null, false);
			holder.iv_image1 = (NetworkImageView) convertView
					.findViewById(R.id.iv_image1);
			holder.iv_image2 = (NetworkImageView) convertView
					.findViewById(R.id.iv_image2);
			holder.iv_image3 = (NetworkImageView) convertView
					.findViewById(R.id.iv_image3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.iv_image1.setDefaultImageResId(R.drawable.empty_photo);
		holder.iv_image1.setErrorImageResId(R.drawable.ic_launcher);
		// VolleyøÚº‹ Õº∆¨º”‘ÿ
		mQueue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(mQueue, new BitmapCache());
		holder.iv_image1.setImageUrl(Common.IMAGES_URL + Common.photoName[0],
				imageLoader);

		holder.iv_image2.setDefaultImageResId(R.drawable.empty_photo);
		holder.iv_image2.setErrorImageResId(R.drawable.ic_launcher);
		// VolleyøÚº‹ Õº∆¨º”‘ÿ
		mQueue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(mQueue, new BitmapCache());
		holder.iv_image2.setImageUrl(Common.IMAGES_URL + Common.photoName[1],
				imageLoader);

		holder.iv_image3.setDefaultImageResId(R.drawable.empty_photo);
		holder.iv_image3.setErrorImageResId(R.drawable.ic_launcher);
		// VolleyøÚº‹ Õº∆¨º”‘ÿ
		mQueue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(mQueue, new BitmapCache());
		holder.iv_image3.setImageUrl(Common.IMAGES_URL + Common.photoName[2],
				imageLoader);

		return convertView;
	}

	private class ViewHolder {
		NetworkImageView iv_image1, iv_image2, iv_image3;
	}

}
