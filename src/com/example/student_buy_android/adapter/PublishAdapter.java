package com.example.student_buy_android.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.student_buy_android.R;
import com.example.student_buy_android.view.NoScrollGridView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

@SuppressLint("InflateParams")
public class PublishAdapter extends BaseAdapter {

	private ArrayList<ArrayList<HashMap<String, Object>>> arrayList;
	private Context context;

	public PublishAdapter(
			ArrayList<ArrayList<HashMap<String, Object>>> arrayList,
			Context context) {
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
			holder.gridView = (NoScrollGridView) convertView
					.findViewById(R.id.listview_item_gridview);
			holder.gridView.setNumColumns(arrayList.get(position).size());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (arrayList != null) {
			if (holder.gridView != null) {
				ArrayList<HashMap<String, Object>> arrayListForEveryGridView = arrayList
						.get(position);
				GridViewAdapter gridViewAdapter = new GridViewAdapter(context,
						arrayListForEveryGridView);
				holder.gridView.setAdapter(gridViewAdapter);
			}
		}

		return convertView;
	}

	private class ViewHolder {
		NoScrollGridView gridView;
	}

}
