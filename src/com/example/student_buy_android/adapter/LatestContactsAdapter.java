package com.example.student_buy_android.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.db.MessageDao;

@SuppressLint({ "ViewHolder", "InflateParams" })
public class LatestContactsAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private Context context;
	private List<FriendBean> latestContactLsit;
	MessageDao messageDao = new MessageDao();

	private ImageView show_image;
	private TextView show_name;
	private TextView tv_message;
	private TextView tv_unread_messages;

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
		convertView = layoutInflater.inflate(R.layout.latest_contact_listitem,
				null);// 这个过程相当耗时间
		show_name = (TextView) convertView.findViewById(R.id.show_name);
		show_name.setText(latestContactLsit.get(position).getUsername());
		tv_message = (TextView) convertView.findViewById(R.id.tv_message);
		tv_message.setText(latestContactLsit.get(position).getLast_message());
		tv_unread_messages = (TextView) convertView
				.findViewById(R.id.tv_unread_messages);
		int count = messageDao
				.get_personal_unread_message_count(latestContactLsit.get(
						position).getUsername());
		if (count == 0) {
			tv_unread_messages.setVisibility(View.INVISIBLE);
		} else {
			tv_unread_messages.setText("" + count);
		}
		return convertView;
	}

}
