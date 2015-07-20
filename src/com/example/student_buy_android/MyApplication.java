package com.example.student_buy_android;

import io.yunba.android.manager.YunBaManager;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

	private final static String TAG = "MyApplication";

	public void onCreate() {
		super.onCreate();

		startBlackService();

	}

	/**
	 * ��������
	 * */
	private void startBlackService() {
		// ��ʼ�� YunBa SDK.
		YunBaManager.start(getApplicationContext());

		// ���� ���� һ�����߶�� Topics, �Ա���Խ������� Topic �� Message.
		YunBaManager.subscribe(getApplicationContext(), new String[] { "t1" },
				new IMqttActionListener() {
					@Override
					public void onSuccess(IMqttToken arg0) {
						Log.d(TAG, "Subscribe topic succeed");
					}

					@Override
					public void onFailure(IMqttToken arg0, Throwable arg1) {
						Log.d(TAG, "Subscribe topic failed");
					}
				});

//		// ���û�����������Ϣ, ����ʵ�ֵ�Ե����Ϣ���͡�
//		YunBaManager.publishToAlias(getApplicationContext(), "account",
//				"message", new IMqttActionListener() {
//					@Override
//					public void onSuccess(IMqttToken asyncActionToken) {
//						String topic = DemoUtil.join(asyncActionToken.getTopics(), ", ");
//						String msgLog = "publish to alias succeed : " + topic;
//						DemoUtil.showToast(msgLog, getApplicationContext());
//					}
//
//					@Override
//					public void onFailure(IMqttToken asyncActionToken,
//							Throwable exception) {
//						if (exception instanceof MqttException) {
//							MqttException ex = (MqttException) exception;
//							String msg = "publishToAlias failed with error code : " + ex.getReasonCode();
//							DemoUtil.showToast(msg, getApplicationContext());
//						}
//					}
//				});
	}

}
