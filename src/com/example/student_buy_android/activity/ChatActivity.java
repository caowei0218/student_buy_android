package com.example.student_buy_android.activity;

import io.yunba.android.manager.YunBaManager;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student_buy_android.R;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.util.SysApplication;

public class ChatActivity extends BaseActivity implements OnClickListener {
	private TextView jilu;
	private EditText message;
	private Button send;
	private ScrollView scroll;
	private FriendBean friendBean;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_layout);
		SysApplication.getInstance().addActivity(this);// ����activity���ӵ���������ȥ

		friendBean = (FriendBean) getIntent().getExtras().get("friendBean");
		
		init();
		setOnClickListener();

		registerBoradcastReceiver();
	}

	/**
	 * ������Ϣ
	 * */
	private void handlePublishAlias(final String msg, final String alias) {
		if (TextUtils.isEmpty(msg) || TextUtils.isEmpty(alias)) {
			Toast.makeText(ChatActivity.this, "��Ϣ����Ϊ��", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		YunBaManager.publishToAlias(getApplicationContext(), alias, msg,
				new IMqttActionListener() {
					public void onSuccess(IMqttToken asyncActionToken) {

						StringBuilder showMsg = new StringBuilder();
						showMsg.append(msg);
						setCostomMsg(showMsg.toString());
					}

					@Override
					public void onFailure(IMqttToken asyncActionToken,
							Throwable exception) {
						String msg = "���͸�" + alias + " ʧ�� : "
								+ exception.getMessage();
						setCostomMsg(msg);
					}
				});
	}

	private void setCostomMsg(final String msg) {
		setCostomMsg(this, msg);
	}

	public void setCostomMsg(Activity context, final String msg) {
		if (null != jilu) {
			context.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					jilu.append(msg + "\r\n");
					if (null != jilu) {
						scroll.fullScroll(View.FOCUS_DOWN);
					}
				}
			});
		}

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
			String topic = intent.getStringExtra("topic");
			String msg = intent.getStringExtra("msg");
			updateChatView(topic, msg);
		}
	}

	/**
	 * �������촰��
	 * */
	public void updateChatView(String topic, String msg) {
		String str = "��" + topic + "��������Ϣ:" + msg;
		setCostomMsg(str);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.send:
			String msg = this.message.getText().toString().trim();
			handlePublishAlias(msg, friendBean.getUsername());
			// YunBaManager.publish(getApplicationContext(),
			// MyApplication.TOPIC, "r", null);
			break;
		default:
			break;
		}
	}

	private void init() {
		jilu = (TextView) findViewById(R.id.jilu);
		message = (EditText) findViewById(R.id.message);
		send = (Button) findViewById(R.id.send);
		scroll = (ScrollView) findViewById(R.id.scroller);
	}

	private void setOnClickListener() {
		send.setOnClickListener(this);
	}

}