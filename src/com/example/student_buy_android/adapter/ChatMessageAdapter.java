package com.example.student_buy_android.adapter;

import java.util.List;

import com.example.student_buy_android.R;
import com.example.student_buy_android.bean.Message;
import com.example.student_buy_android.bean.Message.Type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatMessageAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Message> messages;

	public ChatMessageAdapter(Context context, List<Message> messages) {
		mInflater = LayoutInflater.from(context);
		this.messages = messages;
	}

	@Override
	public int getCount() {
		return messages.size();
	}

	@Override
	public Object getItem(int position) {
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 接受到消息为1，发送消息为0
	 */
	@Override
	public int getItemViewType(int position) {
		Message msg = messages.get(position);
		return msg.getType() == Type.INPUT ? 1 : 0;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message chatMessage = messages.get(position);

		ViewHolder viewHolder = null;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			if (chatMessage.getType() == Type.INPUT) {
				convertView = mInflater.inflate(R.layout.main_chat_from_msg,
						parent, false);
				viewHolder.content = (TextView) convertView
						.findViewById(R.id.chat_from_content);
				convertView.setTag(viewHolder);
			} else {
				convertView = mInflater.inflate(R.layout.main_chat_send_msg,
						null);

				viewHolder.content = (TextView) convertView
						.findViewById(R.id.chat_send_content);
				convertView.setTag(viewHolder);
			}

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.content.setText(chatMessage.getMsg());

		return convertView;
	}

	private class ViewHolder {
		public TextView name;
		public TextView content;
	}

}
