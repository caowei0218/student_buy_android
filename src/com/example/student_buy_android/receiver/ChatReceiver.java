package com.example.student_buy_android.receiver;

import io.yunba.android.manager.YunBaManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.student_buy_android.bean.Message;
import com.example.student_buy_android.db.MessageDao;
import com.example.student_buy_android.util.JsonBinder;

public class ChatReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (YunBaManager.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

			String msg = intent.getStringExtra(YunBaManager.MQTT_MSG);

			JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
			Message message = jsonBinder.jsonToObj(msg, Message.class);
			processCustomMessage(context, message);
		}
	}

	/**
	 * 发送消息到MainActivity(广播)
	 * 
	 * @param send
	 *            发送人
	 * @param send
	 *            发送的消息
	 * */
	private void processCustomMessage(Context context, Message message) {
		Intent intent = new Intent("message_received_action");
		intent.putExtra("message", message);
		context.sendBroadcast(intent);

		MessageDao messageDao = new MessageDao();
		messageDao.saveMessage(message);
	}
}
