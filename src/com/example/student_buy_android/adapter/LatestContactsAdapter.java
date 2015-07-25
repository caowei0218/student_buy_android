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

@SuppressLint({ "ViewHolder", "InflateParams" })
public class LatestContactsAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private Context context;
	private List<FriendBean> latestContactLsit;

	private ImageView show_image;
	private TextView show_name;

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
		return convertView;
	}

}
