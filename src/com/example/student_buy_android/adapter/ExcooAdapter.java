package com.example.student_buy_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.student_buy_android.R;

public class ExcooAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;

	public ExcooAdapter(Context context) {
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
		this.convertView = mInflater.inflate(R.layout.layout_excoo_item, null);
		// holder.ll_user = (RelativeLayout)
		// this.convertView.findViewById(R.id.ll_user);
		// holder.tv_save = (TextView)
		// this.convertView.findViewById(R.id.tv_save);
		this.convertView.setTag(holder);// ∞Û∂®ViewHolder∂‘œÛ

		return convertView;
	}

	class ViewHolder {
		RelativeLayout ll_user;
		TextView tv_save;
	}

}
