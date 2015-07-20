package com.example.student_buy_android.webservice;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.student_buy_android.activity.FriendsActivity;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.util.JsonBinder;

/**
 * 获得好友列表
 * HttpGet请求
 * */
public class GetFriendListWebservice extends AsyncTask<String, Integer, String> {
	private JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	private FriendsActivity friendsActivity;
	private Context context;
	private String method = "account/friend/list/";

	private HttpClient httpClient;
	private HttpGet get;
	private HttpResponse httpResponse;

	List<FriendBean> friendBeans;

	public GetFriendListWebservice(FriendsActivity friendsActivity,
			Context context) {
		this.friendsActivity = friendsActivity;
		this.context = context;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		friendsActivity.beginWaitDialog("正在登陆", true);
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
			// 发起GET请求
			httpResponse = httpClient.execute(get);
			httpResponse.getStatusLine().getStatusCode();
			// 如果服务器成功地返回响应
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils
						.toString(httpResponse.getEntity(), "utf-8");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		friendsActivity.endWaitDialog(true);

		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
			if ("true".equals(jsonObject.getString("success"))) {

				// 解析获得的好友josn字符串
				try {
					friendBeans = jsonBinder.stringToList(
							jsonObject.getString("friendList"),
							FriendBean.class);
					friendsActivity.setAdapter(friendBeans);

				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else {
				Toast.makeText(context, jsonObject.getString("errors"),
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
