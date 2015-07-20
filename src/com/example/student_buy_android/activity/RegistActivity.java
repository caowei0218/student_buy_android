package com.example.student_buy_android.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.util.MD5Util;
import com.example.student_buy_android.R;
import com.example.student_buy_android.bean.UserBean;
import com.example.student_buy_android.webservice.RegisterWebservice;

/**
 * ע��
 * */
public class RegistActivity extends BaseActivity {
	private DisplayMetrics displayMetrics;
	private int window_width, window_height;
	private RelativeLayout tittle;
	private LayoutParams para;
	private ImageButton back;
	private Button btn_regist;
	private EditText account,email,password, password_again;
	private LinearLayout regist_ll_info;
	private UserBean userBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_layout);
		initView();
		intiParas();
		addListener();

	}

	private void intiParas() {
		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		window_height = displayMetrics.heightPixels;
		window_width = displayMetrics.widthPixels;

		para = tittle.getLayoutParams();
		para.height = (int) (window_height * (88.0 / 1334));
		para.width = (int) window_width;
		tittle.setLayoutParams(para);

		para = (RelativeLayout.LayoutParams) regist_ll_info.getLayoutParams();
		para.width = (int) (window_width * (582.0 / 750));
		regist_ll_info.setLayoutParams(para);

	}

	private void addListener() {
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				RegistActivity.this.finish();
			}
		});

		btn_regist.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				regist();
			}
		});
	}

	private void regist() {
		if (success2Login()) {
			initUserBean();
			RegisterWebservice registWebservice = new RegisterWebservice(
					RegistActivity.this, RegistActivity.this, userBean);
			registWebservice.execute();
		} else {
			return;
		}

	}

	private boolean success2Login() {
		boolean boo = true;
		String accountStr = account.getText().toString();
		String passwordStr = password.getText().toString();
		String password_againStr = password_again.getText().toString();
		if (accountStr.equals("") || passwordStr.equals("")) {
			Toast.makeText(RegistActivity.this, "�û������벻��Ϊ��", Toast.LENGTH_SHORT)
					.show();
			boo = false;
			return boo;
		}
		if ("".equals(password_againStr)) {
			Toast.makeText(RegistActivity.this, "������ڶ�������", Toast.LENGTH_SHORT)
					.show();
			boo = false;
			return boo;
		}
		if (!passwordStr.equals(password_againStr)) {
			Toast.makeText(RegistActivity.this, "�������벻һ��", Toast.LENGTH_SHORT)
					.show();
			boo = false;
			return boo;
		}
		if (accountStr.length() < 4 || passwordStr.length() < 4) {
			Toast.makeText(RegistActivity.this, "�û��������벻������4λ",
					Toast.LENGTH_SHORT).show();
			boo = false;
			return boo;
		}
		return boo;
	}

	// @SuppressLint("ShowToast")
	// private String checkCellphone() {
	// String response = "true";
	// String regExp = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
	//
	// Pattern p = Pattern.compile(regExp);
	//
	// Matcher m = p.matcher(mCellphone);
	//
	// if (!m.find()) {
	// Toast.makeText(RegistActivity.this, "�ֻ��Ÿ�ʽ����", 2000).show();
	// response = "fail";
	// }
	// return response;
	// }

	private void initUserBean() {
		userBean = new UserBean();
		userBean.setAccount(account.getText().toString());
		userBean.setPassword(MD5Util.str2MD5(password.getText().toString()));
		userBean.setRepassword(MD5Util.str2MD5(password.getText().toString()));
		userBean.setNickname(account.getText().toString());
		userBean.setEmail(email.getText().toString());
	}

	public void showMsg(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@SuppressLint("CutPasteId")
	private void initView() {
		back = (ImageButton) findViewById(R.id.regist_ib_return);
		account = (EditText) findViewById(R.id.regist_et_account);
		email = (EditText) findViewById(R.id.regist_et_email);
		password = (EditText) findViewById(R.id.regist_et_password);
		password_again = (EditText) findViewById(R.id.regist_et_password_again);
		regist_ll_info = (LinearLayout) findViewById(R.id.regist_ll_info);
		btn_regist = (Button) findViewById(R.id.regist_btn_regist);
		tittle = (RelativeLayout) findViewById(R.id.tittle);
	}
}
