package com.example.student_buy_android.webservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.student_buy_android.activity.AddFriendActivity;
import com.example.student_buy_android.activity.MainActivity;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.util.Word;

/**
 * 添加好友 HttpPost请求
 * */
public class AddFriendWebservice extends AsyncTask<String, Integer, String> {
	private AddFriendActivity addFriendActivity;
	private Context context;
	private String friendID;
	private String method = "account/friend/";

	private HttpClient httpClient;
	private List<NameValuePair> params;
	private HttpPost post;

	public AddFriendWebservice(AddFriendActivity addFriendActivity,
			Context context, String friendID) {
		this.addFriendActivity = addFriendActivity;
		this.context = context;
		this.friendID = friendID;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		addFriendActivity.beginWaitDialog(Word.Adding, true);

		httpClient = new DefaultHttpClient();
		post = new HttpPost(WebserviceUtils.HTTPTRANSPORTSE + method);
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("friendUsername", friendID));
	}

	protected String doInBackground(String... params) {
		String result = null;
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
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		addFriendActivity.endWaitDialog(true);

		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
			if ("true".equals(jsonObject.getString("success"))) {

				Intent intent = new Intent(context, MainActivity.class);
				context.startActivity(intent);
				addFriendActivity.finish();
			} else {
				Toast.makeText(context, jsonObject.getString("errors"),
						Toast.LENGTH_SHORT).show();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
