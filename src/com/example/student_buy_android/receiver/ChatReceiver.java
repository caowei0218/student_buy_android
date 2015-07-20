package com.example.student_buy_android.receiver;

import io.yunba.android.manager.YunBaManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ChatReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (YunBaManager.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

			String topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC);
			String msg = intent.getStringExtra(YunBaManager.MQTT_MSG);

			processCustomMessage(context, topic, msg);
		}
	}

	/**
	 * 发送消息到MainActivity(广播)
	 * */
	private void processCustomMessage(Context context, String topic, String msg) {
		Intent intent = new Intent("message_received_action");
		intent.putExtra("topic", topic);
		intent.putExtra("msg", msg);
		context.sendBroadcast(intent);
	}

}
