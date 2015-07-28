package com.example.student_buy_android.webservice;

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

import com.example.student_buy_android.activity.SetInfoActivity;

public class GetNewVersionWebservice extends AsyncTask<String, Integer, String> {

	private SetInfoActivity setInfoActivity;
	private Context context = null;

	private String method = "download/release/android/";

	private HttpClient httpClient;
	private HttpGet get;
	private HttpResponse httpResponse;

	public GetNewVersionWebservice(SetInfoActivity setInfoActivity,
			Context context) {
		this.setInfoActivity = setInfoActivity;
		this.context = context;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		httpClient = new DefaultHttpClient();
		get = new HttpGet(WebserviceUtils.HTTPTRANSPORTSE + method);
	}

	@Override
	protected String doInBackground(String... params) {
		String result = null;
		try {
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

				String version = jsonObject.getString("version");
				String downloadLink = jsonObject.getString("downloadLink");
				String fileName = jsonObject.getString("fileName");
				setInfoActivity.refresh(version, downloadLink, fileName);
			} else {
				Toast.makeText(context, jsonObject.getString("errors"),
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
