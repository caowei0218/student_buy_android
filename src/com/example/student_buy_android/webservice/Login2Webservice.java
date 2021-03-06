package com.example.student_buy_android.webservice;

import io.yunba.android.manager.YunBaManager;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.student_buy_android.activity.FragmentActivity;
import com.example.student_buy_android.activity.LoginActivity;
import com.example.student_buy_android.activity.SplashActivity;
import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.util.ChatUtil;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.util.Word;

/**
 * SplashActivity登陆 HttpPost请求
 * */
public class Login2Webservice extends AsyncTask<String, Integer, String> {
	private SplashActivity splashActivity;
	private Context context = null;
	private UserBean userBean = null;
	private String method = "login";

	private DefaultHttpClient httpClient;
	private List<NameValuePair> params;
	private HttpPost post;

	public Login2Webservice(SplashActivity splashActivity, Context context,
			UserBean userBean) {
		this.splashActivity = splashActivity;
		this.context = context;
		this.userBean = userBean;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		httpClient = new DefaultHttpClient();
		post = new HttpPost(WebserviceUtils.HTTPTRANSPORTSE + method);
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", userBean.getUsername()));
		params.add(new BasicNameValuePair("password", userBean.getPassword()));
	}

	protected String doInBackground(String... params) {
		String result = null;
		// 设置请求参数
		try {
			post.setEntity(new UrlEncodedFormEntity(this.params, HTTP.UTF_8));
			// 带上session发请求
			if (null != Common.SESSIONID) {
				post.setHeader("Cookie", "connect.sid=" + Common.SESSIONID);
			}
			// 发送POST请求
			HttpResponse response = httpClient.execute(post);
			// 如果服务器成功地返回响应
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = new String(EntityUtils.toByteArray(entity), "UTF-8");

				// 获得session
				CookieStore cookieStore = httpClient.getCookieStore();
				List<Cookie> cookies = cookieStore.getCookies();
				for (int i = 0; i < cookies.size(); i++) {
					if ("connect.sid".equals(cookies.get(i).getName())) {
						Common.SESSIONID = cookies.get(i).getValue();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
			if ("true".equals(jsonObject.getString("success"))) {
				saveLoginUserName(userBean.getUsername(),
						userBean.getPassword(), userBean.getUser_id());

				// 登录云巴
				YunBaManager.setAlias(splashActivity, userBean.getUsername(),
						new IMqttActionListener() {
							@Override
							public void onSuccess(IMqttToken asyncActionToken) {

							}

							@Override
							public void onFailure(IMqttToken asyncActionToken,
									Throwable exception) {
								if (exception instanceof MqttException) {
									MqttException ex = (MqttException) exception;
									String msg = "setAlias failed with error code : "
											+ ex.getReasonCode();
									ChatUtil.showToast(msg, splashActivity);
								}
							}
						});
				// Intent intent = new Intent(splashActivity,
				// MainActivity.class);
				Intent intent = new Intent(splashActivity,
						FragmentActivity.class);
				context.startActivity(intent);
				splashActivity.overridePendingTransition(
						android.R.anim.fade_in, android.R.anim.fade_out);// 实现淡入浅出的效果
				splashActivity.finish();

			} else {
				Toast.makeText(context, Word.LOGIN_FAIL, Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent(splashActivity, LoginActivity.class);
				context.startActivity(intent);
				splashActivity.overridePendingTransition(
						android.R.anim.fade_in, android.R.anim.fade_out);// 实现淡入浅出的效果
				splashActivity.finish();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将用户名保存在SharedPreferences中
	 * */
	private void saveLoginUserName(String username, String password, String id) {
		SharedPreferences prefereces = context.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		Editor editor = prefereces.edit();
		editor.putString("username", username);
		editor.putString("password", password);
		editor.putString("id", id);
		editor.putString("email", userBean.getEmail());
		editor.putString("phoneNumber", userBean.getPhoneNumber());
		editor.putString("nickName", userBean.getNickname());
		editor.putString("gender", userBean.getGender());
		editor.putString("age_group", userBean.getAge());
		editor.commit();
	}
}
