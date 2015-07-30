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

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.student_buy_android.util.Common;

/**
 * 修改备注 HttpPost请求
 * */
public class UpdateAliasWebservice extends AsyncTask<String, Integer, String> {
	private Activity activity;
	private Context context = null;
	private String friendUsername = null;
	private String friendAlias = null;
	private String method = "account/friend/alias/";

	private HttpClient httpClient;
	private List<NameValuePair> params;
	private HttpPost post;

	public UpdateAliasWebservice(Activity activity, Context context,
			String friendUsername, String friendAlias) {
		this.activity = activity;
		this.context = context;
		this.friendUsername = friendUsername;
		this.friendAlias = friendAlias;
	}

	protected void onPreExecute() {
		super.onPreExecute();

		httpClient = new DefaultHttpClient();
		post = new HttpPost(WebserviceUtils.HTTPTRANSPORTSE + method);

		params = new ArrayList<NameValuePair>();
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		params.add(new BasicNameValuePair("friendUsername", friendUsername));
		params.add(new BasicNameValuePair("friendAlias", friendAlias));
	}

	protected String doInBackground(String... params) {
		String returnStr = null;
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
				returnStr = new String(EntityUtils.toByteArray(entity), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
			if ("true".equals(jsonObject.getString("success"))) {
				Toast.makeText(context, "修改备注成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "修改备注失败", Toast.LENGTH_SHORT).show();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
