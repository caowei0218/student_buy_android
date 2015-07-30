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

import com.example.student_buy_android.activity.MyInfoActivity;
import com.example.student_buy_android.activity.UpdateInfoActivity;
import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.util.JsonBinder;
import com.example.student_buy_android.util.Word;

/**
 * 修改个人信息 HttpPost请求
 * */
public class UpdateInfoWebservice extends AsyncTask<String, Integer, String> {
	private JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();
	private UpdateInfoActivity updateInfoActivity;
	private Context context = null;
	private String updateObject = null;
	private String updateContents = null;
	private UserBean userBean;
	private String method = "account/info/";

	private HttpClient httpClient;
	private List<NameValuePair> params;
	private HttpPost post;

	public UpdateInfoWebservice(UpdateInfoActivity updateInfoActivity,
			Context context, String updateObject, String updateContents) {
		this.updateInfoActivity = updateInfoActivity;
		this.context = context;
		this.updateObject = updateObject;
		this.updateContents = updateContents;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		updateInfoActivity.beginWaitDialog(Word.UPDATE_SUCCESS, true);

		httpClient = new DefaultHttpClient();
		post = new HttpPost(WebserviceUtils.HTTPTRANSPORTSE + method);

		params = new ArrayList<NameValuePair>();
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		params.add(new BasicNameValuePair(updateObject, updateContents));
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
		updateInfoActivity.endWaitDialog(true);

		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
			if ("true".equals(jsonObject.getString("success"))) {
				userBean = jsonBinder.jsonToObj(
						jsonObject.getString("userinfo"), UserBean.class);
				Common.userBean = userBean;
				Toast.makeText(context, Word.UPDATE_SUCCESS, Toast.LENGTH_SHORT)
						.show();
				
				Intent intent = new Intent(updateInfoActivity,
						MyInfoActivity.class);
				context.startActivity(intent);
				updateInfoActivity.finish();
				updateInfoActivity.overridePendingTransition(
						android.R.anim.fade_in, android.R.anim.fade_out);// 实现淡入浅出的效果
				
			} else {
				Toast.makeText(context, Word.UPDATE_FAIL, Toast.LENGTH_SHORT)
						.show();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
