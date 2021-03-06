package com.example.student_buy_android.activity;

import io.yunba.android.manager.YunBaManager;

import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student_buy_android.R;
import com.example.student_buy_android.SysApplication;
import com.example.student_buy_android.adapter.ChatMessageAdapter;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.bean.Message;
import com.example.student_buy_android.bean.Message.Type;
import com.example.student_buy_android.db.MessageDao;
import com.example.student_buy_android.util.JsonBinder;
import com.example.student_buy_android.util.Word;

public class ChatActivity extends BaseActivity implements OnClickListener {
	private SharedPreferences preference;
	private TextView tv_name;
	private EditText et_message;
	private Button send;
	private ImageButton btn_back;
	private ImageButton btn_add_friends;
	private FriendBean friendBean;
	private ChatMessageAdapter chatMessageAdapter;
	private ListView lv_chat;
	private Message message;
	private List<Message> messages;
	private MessageDao messageDao;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_layout);
		SysApplication.getInstance().addActivity(this);// 将该activity添加到管理类中去
		preference = getSharedPreferences("user", Context.MODE_PRIVATE);

		friendBean = (FriendBean) getIntent().getExtras().get("friendBean");
		messageDao = new MessageDao();
		messages = messageDao.getMessage(preference.getString("username", ""),
				friendBean.getUsername());

		init();
		// 判断该人是否是好友
		if (friendBean.getUsername().equals(
				messageDao.getFriendInfo(friendBean.getUsername())
						.getUsername())) {
			// 是好友 隐藏添加按钮
			btn_add_friends.setVisibility(View.INVISIBLE);
		} else {
			// 不是好友 显示添加按钮
			btn_add_friends.setVisibility(View.VISIBLE);
		}

		setOnClickListener();

		chatMessageAdapter = new ChatMessageAdapter(this, messages, friendBean);
		lv_chat.setAdapter(chatMessageAdapter);
		lv_chat.setSelection(messages.size() - 1);// 默认最后一行

		registerBoradcastReceiver();
	}

	/**
	 * 发送信息
	 * */
	private void handlePublishAlias(final String msg, final String alias) {
		if (TextUtils.isEmpty(msg) || TextUtils.isEmpty(alias)) {
			Toast.makeText(ChatActivity.this, Word.CHATACTIVITY_MESSAGENOTNULL,
					Toast.LENGTH_SHORT).show();
			return;
		}

		// 将发送者id和msg进行josn封装
		JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
		message = new Message();
		message.setSender(preference.getString("username", ""));// 如果取不到值就取值后面的参数
		message.setMsg(msg);
		message.setReceiver(alias);
		message.setType(Type.OUTPUT);
		// 将聊天记录添加到本地数据库中
		messageDao = new MessageDao();
		messageDao.saveMessage(message);

		updateChatView(message);

		YunBaManager.publishToAlias(getApplicationContext(), alias,
				jsonBinder.toJson(message), new IMqttActionListener() {
					public void onSuccess(IMqttToken asyncActionToken) {

					}

					@Override
					public void onFailure(IMqttToken asyncActionToken,
							Throwable exception) {
					}
				});
	}

	/**
	 * 注册广播
	 * */
	private void registerBoradcastReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("message_received_action");
		MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver();
		registerReceiver(broadcastReceiver, intentFilter);
	}

	/**
	 * 广播接收器
	 * */
	public class MyBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Message message = (Message) intent.getExtras().get("message");
			message.setType(Type.INPUT);
			updateChatView(message);
		}
	}

	/**
	 * 更新聊天窗口
	 * */
	public void updateChatView(Message message) {
		messageDao = new MessageDao();
		messages = messageDao.getMessage(preference.getString("username", ""),
				friendBean.getUsername());

		chatMessageAdapter = new ChatMessageAdapter(this, messages, friendBean);
		lv_chat.setAdapter(chatMessageAdapter);
		lv_chat.setSelection(messages.size() - 1);// 默认最后一行
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.send:
			if (!"".equals(et_message.getText().toString().trim())) {
				handlePublishAlias(this.et_message.getText().toString().trim(),
						friendBean.getUsername());
				et_message.setText("");
			}
			// YunBaManager.publish(getApplicationContext(),
			// MyApplication.TOPIC, "r", null);
			break;
		case R.id.btn_back:
			moveTaskToBack(false);
			setResult(1);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			break;
		case R.id.btn_add_friends:
			Toast.makeText(this, "待开发", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	private void init() {
		tv_name = (TextView) findViewById(R.id.tv_name);
		et_message = (EditText) findViewById(R.id.et_message);
		send = (Button) findViewById(R.id.send);
		lv_chat = (ListView) findViewById(R.id.lv_chat);
		btn_back = (ImageButton) findViewById(R.id.btn_back);
		btn_add_friends = (ImageButton) findViewById(R.id.btn_add_friends);

		tv_name.setText(friendBean.getUsername());
	}

	private void setOnClickListener() {
		send.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_add_friends.setOnClickListener(this);
	}

	/**
	 * 返回
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			moveTaskToBack(false);
			setResult(1);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// 实现淡入浅出的效果
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
