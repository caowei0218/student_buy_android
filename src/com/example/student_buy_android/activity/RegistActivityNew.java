package com.example.student_buy_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.util.TimeCountUtil;

public class RegistActivityNew extends Activity {
	private RelativeLayout Regist_phone_layout, Regist_checkNo_layout,
			Regist_regist_layout, Regist_password_layout;
	private DisplayMetrics displayMetrics;
	private int window_height;
	private int window_width;
	private RelativeLayout.LayoutParams rlLayoutPara;
	private EditText etRegistPhone, etRegistcheckNo, etRegistPassword;
	private TextView tvRegistTime;
	private Button btnRegistSend;
	private ImageButton ibRegistReturn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_layout);
		initView();
		initParas();
		setListener();
	}

	private void setListener() {
		btnRegistSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TimeCountUtil tcu = new TimeCountUtil(RegistActivityNew.this,
						60000, 1000, btnRegistSend, tvRegistTime);
				tcu.start();
			}
		});

		Regist_regist_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}

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

		rlLayoutPara = (RelativeLayout.LayoutParams) Regist_regist_layout
				.getLayoutParams();
		rlLayoutPara.height = (int) (window_height * (100.0 / 1334));
		rlLayoutPara.topMargin = (int) (window_height * (14.0 / 1334));
		Regist_regist_layout.setLayoutParams(rlLayoutPara);
	}

	private void initView() {
		Regist_phone_layout = (RelativeLayout) findViewById(R.id.Regist_phone_layout);
		Regist_checkNo_layout = (RelativeLayout) findViewById(R.id.Regist_checkNo_layout);
		Regist_regist_layout = (RelativeLayout) findViewById(R.id.Regist_regist_layout);
		Regist_password_layout = (RelativeLayout) findViewById(R.id.Regist_password_layout);
		etRegistPhone = (EditText) findViewById(R.id.etRegistPhone);
		etRegistcheckNo = (EditText) findViewById(R.id.etRegistcheckNo);
		etRegistPassword = (EditText) findViewById(R.id.etRegistPassword);
		btnRegistSend = (Button) findViewById(R.id.btnRegistSend);
		tvRegistTime = (TextView) findViewById(R.id.tvRegistTime);
		ibRegistReturn = (ImageButton) findViewById(R.id.ibRegistReturn);
		ibRegistReturn.getBackground().setAlpha(0);
	}

}
