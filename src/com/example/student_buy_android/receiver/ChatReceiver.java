package com.example.student_buy_android.receiver;

import io.yunba.android.manager.YunBaManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

import com.example.student_buy_android.bean.Message;
import com.example.student_buy_android.db.MessageDao;
import com.example.student_buy_android.util.JsonBinder;

public class ChatReceiver extends BroadcastReceiver {

	private Vibrator vibrator;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (YunBaManager.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

			String msg = intent.getStringExtra(YunBaManager.MQTT_MSG);

			// 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
			vibrator = (Vibrator) context
					.getSystemService(Context.VIBRATOR_SERVICE);
			long[] pattern = { 100, 400, 100, 400 }; // 停止 开启 停止 开启
			vibrator.vibrate(pattern, -1); // 如果只想震动一次，index设为-1

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
