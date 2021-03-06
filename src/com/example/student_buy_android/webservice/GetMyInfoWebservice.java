package com.example.student_buy_android.webservice;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.fragment.MyFragment;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.util.JsonBinder;

/**
 * 获得用户自己的信息 HttpGet请求
 * */
public class GetMyInfoWebservice extends AsyncTask<String, Integer, String> {
	private JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	private MyFragment myFragment;
	private Context context;
	private UserBean userBean;
	private String method = "account/info/";
	private HttpClient httpClient;
	private HttpGet get;

	public GetMyInfoWebservice(MyFragment myFragment, Context context) {
		this.myFragment = myFragment;
		this.context = context;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		httpClient = new DefaultHttpClient();
		get = new HttpGet(WebserviceUtils.HTTPTRANSPORTSE + method);
	}

	protected String doInBackground(String... params) {
		String result = null;
		try {
			// 带上session发请求
			if (null != Common.SESSIONID) {
				get.setHeader("Cookie", "connect.sid=" + Common.SESSIONID);
			}
			// 发送POST请求
			HttpResponse response = httpClient.execute(get);
			// 如果服务器成功地返回响应
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = new String(EntityUtils.toByteArray(entity), "UTF-8");
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
			System.out.println(jsonObject.toString());
			if ("true".equals(jsonObject.getString("success"))) {
				userBean = jsonBinder.jsonToObj(
						jsonObject.getString("userinfo"), UserBean.class);

				if ("GENDER_NONE".equals(userBean.getGender())) {
					userBean.setGender("");
				} else if ("GENDER_MALE".equals(userBean.getGender())) {
					userBean.setGender("男");
				} else if ("GENDER_FEMALE".equals(userBean.getGender())) {
					userBean.setGender("女");
				} else if ("GENDER_UNKNOW".equals(userBean.getGender())) {
					userBean.setGender("中性");
				}
				userBean.setPhotoName(Common.photoName[1]);
				saveUserinfo(userBean);
				Common.userBean = userBean;
				myFragment.updateData(userBean);
			} else {
				Toast.makeText(context, jsonObject.getString("errors"),
						Toast.LENGTH_SHORT).show();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将用户信息保存在SharedPreferences中
	 * */
	private void saveUserinfo(UserBean userBean) {
		SharedPreferences prefereces = context.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		Editor editor = prefereces.edit();
		editor.putString("username", userBean.getUsername());
		editor.putString("password", userBean.getPassword());
		editor.putString("user_id", userBean.getUser_id());
		editor.putString("nickName", userBean.getNickname());
		editor.putString("description", userBean.getDescription());
		editor.putString("age", userBean.getAge());
		editor.putString("gender", userBean.getGender());
		editor.putString("address", userBean.getAddress());
		editor.putString("city", userBean.getCity());
		editor.putString("email", userBean.getEmail());
		editor.putString("phoneNumber", userBean.getPhoneNumber());
		editor.putString("photoName", userBean.getPhotoName());
		editor.commit();
	}
}
