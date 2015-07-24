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
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.student_buy_android.activity.MainActivity;
import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.util.JsonBinder;
import com.example.student_buy_android.util.Word;

/**
 * 获得用户自己的信息 HttpGet请求
 * */
public class GetMyInfoWebservice extends AsyncTask<String, Integer, String> {
	private JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	private MainActivity mainActivity;
	private Context context;
	private UserBean userBean;
	private String method = "account/info/";
	private HttpClient httpClient;
	private HttpGet get;

	public GetMyInfoWebservice(MainActivity mainActivity, Context context) {
		this.mainActivity = mainActivity;
		this.context = context;
	}

	protected void onPreExecute() {
		super.onPreExecute();
//		mainActivity.beginWaitDialog(Word.Adding, true);

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
//		mainActivity.endWaitDialog(true);

		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
			if ("true".equals(jsonObject.getString("success"))) {
				userBean = jsonBinder.jsonToObj(
						jsonObject.getString("userinfo"), UserBean.class);
				mainActivity.updateData(userBean);
			} else {
				Toast.makeText(context, jsonObject.getString("errors"),
						Toast.LENGTH_SHORT).show();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
