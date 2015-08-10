package com.example.student_buy_android.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.student_buy_android.R;

@SuppressLint({ "ViewHolder", "InflateParams" })
public class HomeAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;

	public HomeAdapter(Context context) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
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

	View convertView;

	@Override
	public View getView(int position, final View mconvertView, ViewGroup parent) {
		final ViewHolder holder;
		this.convertView = mconvertView;
		holder = new ViewHolder();
		this.convertView = mInflater.inflate(R.layout.layout_first_item, null);
		this.convertView.setTag(holder);// ∞Û∂®ViewHolder∂‘œÛ

		return convertView;
	}

	class ViewHolder {
		RelativeLayout ll_user;
		TextView tv_save;
	}

}
