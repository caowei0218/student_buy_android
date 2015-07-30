package com.example.student_buy_android.activity;

import io.yunba.android.manager.YunBaManager;

import java.util.ArrayList;
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
	private FriendBean friendBean;
	private ChatMessageAdapter chatMessageAdapter;
	private ListView lv_chat;
	private Message message;
	private List<Message> messages = new ArrayList<Message>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_layout);
		SysApplication.getInstance().addActivity(this);// ����activity��ӵ���������ȥ
		preference = getSharedPreferences("user", Context.MODE_PRIVATE);

		friendBean = (FriendBean) getIntent().getExtras().get("friendBean");

		MessageDao messageDao = new MessageDao();
		messages = messageDao.getMessage(preference.getString("username", ""),
				friendBean.getUsername());

		init();
		setOnClickListener();

		chatMessageAdapter = new ChatMessageAdapter(this, messages);
		lv_chat.setSelection(messages.size() - 1);
		lv_chat.setAdapter(chatMessageAdapter);

		registerBoradcastReceiver();
	}

	/**
	 * ������Ϣ
	 * */
	private void handlePublishAlias(final String msg, final String alias) {
		if (TextUtils.isEmpty(msg) || TextUtils.isEmpty(alias)) {
			Toast.makeText(ChatActivity.this, Word.CHATACTIVITY_MESSAGENOTNULL,
					Toast.LENGTH_SHORT).show();
			return;
		}

		// ��������id��msg����josn��װ
		JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
		message = new Message();
		message.setSender(preference.getString("username", ""));// ���ȡ����ֵ��ȡֵ����Ĳ���
		message.setMsg(msg);
		message.setReceiver(alias);
		message.setType(Type.OUTPUT);
		updateChatView(message);
		// �������¼��ӵ��������ݿ���
		MessageDao messageDao = new MessageDao();
		messageDao.saveMessage(message);

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
	 * ע��㲥
	 * */
	private void registerBoradcastReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("message_received_action");
		MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver();
		registerReceiver(broadcastReceiver, intentFilter);
	}

	/**
	 * �㲥������
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
	 * �������촰��
	 * */
	public void updateChatView(Message message) {
		messages.add(message);
		chatMessageAdapter.notifyDataSetChanged();
		lv_chat.setSelection(messages.size() - 1);
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
		default:
			break;
		}
	}

	private void init() {
		tv_name = (TextView) findViewById(R.id.tv_name);
		et_message = (EditText) findViewById(R.id.et_message);
		send = (Button) findViewById(R.id.send);
		lv_chat = (ListView) findViewById(R.id.lv_chat);

		tv_name.setText(friendBean.getUsername());
	}

	private void setOnClickListener() {
		send.setOnClickListener(this);
	}

	/**
	 * ����
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			moveTaskToBack(false);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
