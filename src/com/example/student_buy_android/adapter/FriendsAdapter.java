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

@SuppressLint({ "ViewHolder", "InflateParams" })
public class FriendsAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private Context context;
	private List<String> data;

	private ImageView show_image;
	private TextView show_name;

	public FriendsAdapter(List<String> data, Context context) {
		this.layoutInflater = LayoutInflater.from(context);
		this.context = context;
		this.data = data;
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = layoutInflater.inflate(
				R.layout.friends_layout_listitem, null);// 这个过程相当耗时间
		show_name = (TextView) convertView.findViewById(R.id.show_name);
		show_name.setText(data.get(position));
		return convertView;
	}

}
