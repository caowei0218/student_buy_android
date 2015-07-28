package com.example.student_buy_android.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.example.student_buy_android.R;
import com.example.student_buy_android.util.Common;
import com.example.student_buy_android.util.QRCode;
import com.example.student_buy_android.util.SysApplication;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class QRCodeActivity extends BaseActivity {

	private ImageView iv_qr_code;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qr_code_layout);
		SysApplication.getInstance().addActivity(this);// ����activity��ӵ���������ȥ��

		init();

		QRCode qrCode = new QRCode();
		Bitmap bm;
		try {
			bm = qrCode.getQRCode(Common.userBean.getUsername(),
					BarcodeFormat.QR_CODE);
			iv_qr_code.setImageBitmap(bm);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		iv_qr_code = (ImageView) findViewById(R.id.iv_qr_code);
	}

	/**
	 * ����
	 * */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			moveTaskToBack(false);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);// ʵ�ֵ���ǳ����Ч��
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
