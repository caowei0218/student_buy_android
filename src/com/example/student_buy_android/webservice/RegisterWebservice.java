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
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.student_buy_android.activity.RegistActivity;
import com.example.student_buy_android.bean.UserBean;

/**
 * 注册
 * HttpPost请求
 * */
public class RegisterWebservice extends AsyncTask<String, Integer, String> {

	private RegistActivity registActivity;
	private Context context = null;
	private UserBean userBean = null;
	private String method = "signup";

	private HttpClient httpClient;
	private List<NameValuePair> params;
	private HttpPost post;

	public RegisterWebservice(RegistActivity registActivity, Context context,
			UserBean userBean) {
		this.registActivity = registActivity;
		this.context = context;
		this.userBean = userBean;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		registActivity.beginWaitDialog("����ע�ᣬ��ȴ�", true);

		httpClient = new DefaultHttpClient();
		post = new HttpPost(WebserviceUtils.HTTPTRANSPORTSE + method);
		// ���ݲ������Ƚ϶�Ļ����ԶԴ��ݵĲ�����з�װ
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", userBean.getAccount()));
		params.add(new BasicNameValuePair("password", userBean.getPassword()));
		params.add(new BasicNameValuePair("repassword", userBean.getRepassword()));
		params.add(new BasicNameValuePair("nickname", userBean.getNickname()));
		params.add(new BasicNameValuePair("email", userBean.getEmail()));

	}

	protected String doInBackground(String... params) {
		String returnStr = null;

		// �����������
		try {
			post.setEntity(new UrlEncodedFormEntity(this.params, HTTP.UTF_8));
			// ����POST����
			HttpResponse response = httpClient.execute(post);
			// ���������ɹ��ط�����Ӧ
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
		registActivity.endWaitDialog(true);
		
		try {
			JSONTokener jsonParser = new JSONTokener(result);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
			if ("true".equals(jsonObject.getString("success"))) {
				Toast.makeText(context, "ע��ɹ�", Toast.LENGTH_SHORT).show();
				registActivity.finish();
			} else {
				Toast.makeText(context, "ע��ʧ��", Toast.LENGTH_SHORT).show();
				System.out.println(jsonObject.getString("errors"));
				System.out.println(jsonObject.getString("errfor"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
