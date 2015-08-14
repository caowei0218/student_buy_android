package com.example.student_buy_android.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

import com.android.util.MD5Util;
import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.student_buy_android.R;
import com.example.student_buy_android.SysApplication;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.util.TimeCountUtil;

public class RegistActivityNew extends BaseActivity implements OnClickListener {
	private RelativeLayout Regist_phone_layout, Regist_checkNo_layout,
			regist_regist_layout, Regist_password_layout;
	private DisplayMetrics displayMetrics;
	private int window_height;
	private int window_width;
	private RelativeLayout.LayoutParams rlLayoutPara;
	private EditText etRegistPhone, etRegistcheckNo, etRegistPassword;
	private TextView tvRegistTime;
	private Button btnRegistSend;
	private ImageButton ibRegistReturn;

	RequestQueue mQueue;
	StringRequest stringRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_layout_new);
		SysApplication.getInstance().addActivity(this);// ����activity��ӵ���������ȥ

		/**
		 * ��ʼ��Bomb����
		 */
		Bmob.initialize(this, "8d4ae863ffebe5a3b006c60a3c4029c8");

		initView();
		initParas();
		mQueue = Volley.newRequestQueue(this);
		initVollery();
		setListener();
	}

	private void setListener() {
		btnRegistSend.setOnClickListener(this);
		regist_regist_layout.setOnClickListener(this);
	}

	private void initView() {
		Regist_phone_layout = (RelativeLayout) findViewById(R.id.Regist_phone_layout);
		Regist_checkNo_layout = (RelativeLayout) findViewById(R.id.Regist_checkNo_layout);
		regist_regist_layout = (RelativeLayout) findViewById(R.id.regist_regist_layout);
		Regist_password_layout = (RelativeLayout) findViewById(R.id.Regist_password_layout);
		etRegistPhone = (EditText) findViewById(R.id.etRegistPhone);
		etRegistcheckNo = (EditText) findViewById(R.id.etRegistcheckNo);
		etRegistPassword = (EditText) findViewById(R.id.etRegistPassword);
		btnRegistSend = (Button) findViewById(R.id.btnRegistSend);
		tvRegistTime = (TextView) findViewById(R.id.tvRegistTime);
		ibRegistReturn = (ImageButton) findViewById(R.id.ibRegistReturn);
		ibRegistReturn.getBackground().setAlpha(0);
	}

	/**
	 * ��ʼ�� ע��ӿ�
	 * */
	private void initVollery() {
		String method = "RegisterServlet";
		stringRequest = new StringRequest(Method.POST, Common.URL + method,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("Excoo", response);
						try {
							JSONTokener jsonParser = new JSONTokener(response);
							JSONObject jsonObject = (JSONObject) jsonParser
									.nextValue();
							if ("0".equals(jsonObject.getString("reCode"))) {
								Toast.makeText(RegistActivityNew.this, "ע��ɹ�",
										Toast.LENGTH_SHORT).show();
								finish();
							} else if ("-1".equals(jsonObject
									.getString("reCode"))) {
								Toast.makeText(RegistActivityNew.this, "δ֪����",
										Toast.LENGTH_SHORT).show();
							} else if ("-2".equals(jsonObject
									.getString("reCode"))) {
								Toast.makeText(RegistActivityNew.this,
										"�û����Ѵ���", Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("Excoo", "����ʧ��");
						Toast.makeText(RegistActivityNew.this, "����ʧ��",
								Toast.LENGTH_SHORT).show();
					}
				}) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("phone", etRegistPhone.getText().toString());
				map.put("pwd",
						MD5Util.str2MD5(etRegistPassword.getText().toString()));
				return map;
			}
		};
	}

	@Override
	public void onClick(View v) {
		String verifyCode = etRegistcheckNo.getText().toString().trim();
		String phone = etRegistPhone.getText().toString().trim();
		String password = etRegistPassword.getText().toString().trim();

		switch (v.getId()) {
		case R.id.btnRegistSend:
			if (!"".equals(phone)) {
				// ������֤��
				BmobSMS.requestSMSCode(RegistActivityNew.this, etRegistPhone
						.getText().toString(), "BmobTest",
						new RequestSMSCodeListener() {
							@Override
							public void done(Integer arg0, BmobException arg1) {
								if (arg1 == null) {// ��֤�뷢�ͳɹ�
									Log.i("Excoo", "����id��" + arg0);// ���ڲ�ѯ���ζ��ŷ�������
								}
							}
						});

				// ��ʱ��ʼ
				TimeCountUtil tcu = new TimeCountUtil(RegistActivityNew.this,
						60000, 1000, btnRegistSend, tvRegistTime);
				tcu.start();
			} else {
				Toast.makeText(RegistActivityNew.this, "�ֻ��Ų���Ϊ��",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.regist_regist_layout:
			if (!"".equals(verifyCode) && !"".equals(password)) {
				// ע��
				BmobSMS.verifySmsCode(RegistActivityNew.this, phone,
						verifyCode, new VerifySMSCodeListener() {
							@Override
							public void done(BmobException ex) {
								if (ex != null) {// ������֤������֤�ɹ�
									// ��֤��У��ʧ��
									Log.i("Excoo",
											"��֤ʧ�ܣ�code =" + ex.getErrorCode()
													+ ",msg = "
													+ ex.getLocalizedMessage());
								} else {
									// ��֤��У��ͨ�� �ύ�ʺ�����
									mQueue.add(stringRequest);
									Log.i("Excoo", "��֤ͨ��");
								}
							}
						});
			} else {
				Toast.makeText(RegistActivityNew.this, "��֤������벻��Ϊ��",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * ��Ļ����
	 * */
	private void initParas() {
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		window_height = displayMetrics.heightPixels;
		window_width = displayMetrics.widthPixels;

		rlLayoutPara = (RelativeLayout.LayoutParams) Regist_phone_layout
				.getLayoutParams();
		rlLayoutPara.topMargin = (int) (window_height * (14.0 / 1334));
		rlLayoutPara.rightMargin = (int) (window_height * (14.0 / 1334));
		Regist_phone_layout.setLayoutParams(rlLayoutPara);
		rlLayoutPara = (RelativeLayout.LayoutParams) Regist_checkNo_layout
				.getLayoutParams();
		rlLayoutPara.topMargin = (int) (window_height * (14.0 / 1334));
		rlLayoutPara.rightMargin = (int) (window_height * (14.0 / 1334));
		Regist_checkNo_layout.setLayoutParams(rlLayoutPara);
		rlLayoutPara = (RelativeLayout.LayoutParams) Regist_password_layout
				.getLayoutParams();
		rlLayoutPara.topMargin = (int) (window_height * (14.0 / 1334));
		rlLayoutPara.rightMargin = (int) (window_height * (14.0 / 1334));
		Regist_password_layout.setLayoutParams(rlLayoutPara);

		rlLayoutPara = (RelativeLayout.LayoutParams) etRegistPhone
				.getLayoutParams();
		rlLayoutPara.height = (int) (window_height * (100.0 / 1334));
		rlLayoutPara.leftMargin = (int) (window_width * (36.0 / 750));
		etRegistPhone.setLayoutParams(rlLayoutPara);
		etRegistcheckNo.setLayoutParams(rlLayoutPara);
		etRegistPassword.setLayoutParams(rlLayoutPara);

		rlLayoutPara = (RelativeLayout.LayoutParams) btnRegistSend
				.getLayoutParams();
		rlLayoutPara.height = (int) (window_height * (100.0 / 1334));
		rlLayoutPara.width = (int) (window_width * (200.0 / 750));
		btnRegistSend.setLayoutParams(rlLayoutPara);
		rlLayoutPara = (RelativeLayout.LayoutParams) tvRegistTime
				.getLayoutParams();
		rlLayoutPara.height = (int) (window_height * (100.0 / 1334));
		rlLayoutPara.width = (int) (window_width * (140.0 / 750));
		tvRegistTime.setLayoutParams(rlLayoutPara);

		rlLayoutPara = (RelativeLayout.LayoutParams) regist_regist_layout
				.getLayoutParams();
		rlLayoutPara.height = (int) (window_height * (100.0 / 1334));
		rlLayoutPara.topMargin = (int) (window_height * (14.0 / 1334));
		regist_regist_layout.setLayoutParams(rlLayoutPara);
	}

}
