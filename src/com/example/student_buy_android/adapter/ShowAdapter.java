package com.example.student_buy_android.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.student_buy_android.R;
import com.example.student_buy_android.view.NoScrollGridView;

public class ShowAdapter extends BaseAdapter {

	private ArrayList<ArrayList<HashMap<String, Object>>> arrayLists;
	private Context mContext;

	public ShowAdapter(
			ArrayList<ArrayList<HashMap<String, Object>>> arrayLists,
			Context mContext) {
		super();
		this.arrayLists = arrayLists;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		if (arrayLists == null) {
			return 0;
		} else {
			return arrayLists.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if (arrayLists == null) {
			return null;
		} else {
			return arrayLists.get(position);
		}
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
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.show_listview_item, null, false);
			// holder.imageView = (ImageView) convertView
			// .findViewById(R.id.listview_item_imageview);
			holder.gridView = (NoScrollGridView) convertView
					.findViewById(R.id.listview_item_gridview);
			holder.gridView.setNumColumns(arrayLists.get(position).size());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (arrayLists != null) {
			// if (holder.imageView != null) {
			// holder.imageView.setImageDrawable(mContext.getResources()
			// .getDrawable(R.drawable.ic_launcher));
			// }
			if (holder.gridView != null) {
				ArrayList<HashMap<String, Object>> arrayListForEveryGridView = this.arrayLists
						.get(position);
				GridViewAdapter gridViewAdapter = new GridViewAdapter(mContext,
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
