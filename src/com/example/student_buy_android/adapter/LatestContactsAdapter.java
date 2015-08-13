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
import com.example.student_buy_android.db.MessageDao;
import com.example.student_buy_android.util.BitmapCache;
import com.example.student_buy_android.util.BitmapUtil;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.view.CustomNetworkImageView;

@SuppressLint({ "ViewHolder", "InflateParams" })
public class LatestContactsAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private Context context;
	private List<FriendBean> latestContactLsit;

	private ImageLoader imageLoader;
	private RequestQueue mQueue;

	MessageDao messageDao = new MessageDao();

	public LatestContactsAdapter(List<FriendBean> latestContactLsit,
			Context context) {
		this.layoutInflater = LayoutInflater.from(context);
		this.context = context;
		this.latestContactLsit = latestContactLsit;
	}

	public int getCount() {
		return latestContactLsit.size();
	}

	public FriendBean getItem(int position) {
		return latestContactLsit.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(
					R.layout.latest_contact_listitem, null);// 这个过程相当耗时间
			holder.show_image = (CustomNetworkImageView) convertView
					.findViewById(R.id.show_image);
			holder.show_name = (TextView) convertView
					.findViewById(R.id.show_name);
			holder.tv_message = (TextView) convertView
					.findViewById(R.id.tv_message);
			holder.tv_unread_messages = (TextView) convertView
					.findViewById(R.id.tv_unread_messages);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 如果头像在本地有 就直接去本地加载 没有再去网络加载
		if (BitmapUtil.isExists(latestContactLsit.get(position).getPhotoName())) {
			holder.show_image.setLocalImageBitmap(BitmapUtil
					.getLocalBitmap(latestContactLsit.get(position)
							.getPhotoName()));
		} else {
			// Volley框架 头像加载
			mQueue = Volley.newRequestQueue(context);
			imageLoader = new ImageLoader(mQueue, new BitmapCache(
					Common.photoName[position]));
			holder.show_image.setImageUrl(Common.IMAGES_URL
					+ latestContactLsit.get(position).getPhotoName(),
					imageLoader);
		}

		holder.show_name.setText(latestContactLsit.get(position).getUsername());
		holder.tv_message.setText(latestContactLsit.get(position)
				.getLast_message());
		int count = messageDao
				.get_personal_unread_message_count(latestContactLsit.get(
						position).getUsername());
		if (count == 0) {
			holder.tv_unread_messages.setVisibility(View.INVISIBLE);
		} else {
			holder.tv_unread_messages.setText("" + count);
		}
		return convertView;
	}

	class ViewHolder {
		CustomNetworkImageView show_image;
		TextView show_name, tv_message, tv_unread_messages;
	}

}
