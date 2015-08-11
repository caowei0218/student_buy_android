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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.db.MessageDao;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.util.JsonBinder;

/**
 * 获得好友列表
 * */
public class GetFriendsWebservice extends AsyncTask<String, Integer, String> {
	private JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	private Context context;
	private String method = "account/friend/list/";

	private HttpClient httpClient;
	private HttpGet get;
	private HttpResponse httpResponse;

	public GetFriendsWebservice(Context context) {
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
			if (null != Common.SESSIONID) {
				get.setHeader("Cookie", "connect.sid=" + Common.SESSIONID);
			}
			httpResponse = httpClient.execute(get);
			httpResponse.getStatusLine().getStatusCode();
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

		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
			if ("true".equals(jsonObject.getString("success"))) {

				try {
					List<FriendBean> friendBeans = jsonBinder.stringToList(
							jsonObject.getString("friendList"),
							FriendBean.class);
					SharedPreferences sp = this.context.getSharedPreferences(
							"user", Context.MODE_PRIVATE);
					MessageDao messageDao = new MessageDao();
					messageDao.save_friend_list(friendBeans,
							sp.getString("username", ""));

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
