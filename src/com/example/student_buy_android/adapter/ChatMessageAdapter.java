package com.example.student_buy_android.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.student_buy_android.MyApplication;
import com.example.student_buy_android.R;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.bean.Message;
import com.example.student_buy_android.bean.Message.Type;
import com.example.student_buy_android.util.BitmapUtil;
import com.example.student_buy_android.view.CustomNetworkImageView;

@SuppressLint("InflateParams")
public class ChatMessageAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private List<Message> messages;
	private FriendBean friendBean;

	public ChatMessageAdapter(Context context, List<Message> messages,
			FriendBean friendBean) {
		mInflater = LayoutInflater.from(context);
		this.messages = messages;
		this.context = context;
		this.friendBean = friendBean;
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
				viewHolder.iv_avatar = (CustomNetworkImageView) convertView
						.findViewById(R.id.chat_from_icon);
				// 对方头像
				viewHolder.iv_avatar.setLocalImageBitmap(BitmapUtil
						.getLocalBitmap(friendBean.getPhotoName()));
				convertView.setTag(viewHolder);
			} else {
				convertView = mInflater.inflate(R.layout.main_chat_send_msg,
						null);
				viewHolder.content = (TextView) convertView
						.findViewById(R.id.chat_send_content);
				viewHolder.iv_avatar = (CustomNetworkImageView) convertView
						.findViewById(R.id.chat_send_icon);
				// 本人头像
				viewHolder.iv_avatar.setLocalImageBitmap(BitmapUtil
						.getLocalBitmap(MyApplication
								.getInstance()
								.getSharedPreferences("user",
										Context.MODE_PRIVATE)
								.getString("photoName", "")));
				convertView.setTag(viewHolder);
			}

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.content.setText(chatMessage.getMsg());

		return convertView;
	}

	private class ViewHolder {
		CustomNetworkImageView iv_avatar;
		TextView name;
		TextView content;
	}

}
