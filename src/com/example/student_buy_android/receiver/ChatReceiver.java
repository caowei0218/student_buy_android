package com.example.student_buy_android.receiver;

import io.yunba.android.manager.YunBaManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;

import com.example.student_buy_android.R;
import com.example.student_buy_android.activity.ChatActivity;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.bean.Message;
import com.example.student_buy_android.db.MessageDao;
import com.example.student_buy_android.util.JsonBinder;

public class ChatReceiver extends BroadcastReceiver {

	private Vibrator vibrator;

	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent) {
		if (YunBaManager.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

			String msg = intent.getStringExtra(YunBaManager.MQTT_MSG);

			JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
			Message message = jsonBinder.jsonToObj(msg, Message.class);
			processCustomMessage(context, message);

			NotificationManager manager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(
					R.drawable.ic_launcher, "你收到一条新信息",
					System.currentTimeMillis());
			// 实例化Intent
			Intent intent2 = new Intent(context, ChatActivity.class);
			FriendBean friendBean = new FriendBean();
			friendBean.setUsername(message.getSender());
			intent2.putExtra("friendBean", friendBean);
			// 获得PendingIntent
			PendingIntent pIntent = PendingIntent.getActivity(context, 0,
					intent2, 0);
			// 设置事件信息
			notification.setLatestEventInfo(context, "学淘", "你收到一条新信息", pIntent);
			// 发出通知
			manager.notify(1, notification);

			// 如果当前是铃音模式，则继续准备下面的
			// 蜂鸣提示音操作，如果是静音或者震动模式。就不要继续了。因为用户选择了无声的模式，我们就也不要出声了。
			AudioManager audioService = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			if (audioService.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {// 非静音模式下响铃
				// shouldPlayBeep = false;
				MediaPlayer music = MediaPlayer.create(context, R.raw.beep);
				music.start();
			} else {// 静音模式下震动
				// 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
				vibrator = (Vibrator) context
						.getSystemService(Context.VIBRATOR_SERVICE);
				long[] pattern = { 100, 400, 100, 400 }; // 停止 开启 停止 开启
				vibrator.vibrate(pattern, -1); // 如果只想震动一次，index设为-1
			}
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
