package com.example.student_buy_android.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.student_buy_android.R;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.util.BitmapCache;
import com.example.student_buy_android.util.BitmapUtil;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.view.CustomNetworkImageView;

@SuppressLint({ "ViewHolder", "InflateParams" })
public class FriendsAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private Context context;
	private List<FriendBean> friendBeans;

	private ImageLoader imageLoader;
	private RequestQueue mQueue;

	public FriendsAdapter(List<FriendBean> friendBeans, Context context) {
		this.layoutInflater = LayoutInflater.from(context);
		this.context = context;
		this.friendBeans = friendBeans;
	}

	public int getCount() {
		return friendBeans.size();
	}

	public FriendBean getItem(int position) {
		return friendBeans.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(
					R.layout.friends_layout_listitem, null);// 这个过程相当耗时间
			holder.show_image = (CustomNetworkImageView) convertView
					.findViewById(R.id.show_image);
			holder.show_image.setDefaultImageResId(R.drawable.empty_photo);
			holder.show_image.setErrorImageResId(R.drawable.ic_launcher);
			holder.show_name = (TextView) convertView
					.findViewById(R.id.show_name);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.show_name.setText(friendBeans.get(position).getUsername());

		// 如果头像在本地有 就直接去本地加载 没有再去网络加载
		if (BitmapUtil.isExists(friendBeans.get(position).getPhotoName())) {
			holder.show_image.setLocalImageBitmap(BitmapUtil
					.getLocalBitmap(friendBeans.get(position).getPhotoName()));
		} else {
			// Volley框架 头像加载
			mQueue = Volley.newRequestQueue(context);
			imageLoader = new ImageLoader(mQueue, new BitmapCache(
					Common.photoName[position]));
			holder.show_image.setImageUrl(
					Common.IMAGES_URL
							+ friendBeans.get(position).getPhotoName(),
					imageLoader);
		}
		return convertView;
	}

	class ViewHolder {
		CustomNetworkImageView show_image;
		TextView show_name;
	}

}
