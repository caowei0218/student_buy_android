package com.example.student_buy_android.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student_buy_android.R;

public class CustomProgressDialog extends ProgressDialog {

	private AnimationDrawable animationDrawable;
	@SuppressWarnings("unused")
	private Context context;
	private ImageView loading_imageview;
	private TextView loading_textview;
	private String loadingText;
	@SuppressWarnings("unused")
	private int count = 0;
	@SuppressWarnings("unused")
	private String oldLoadingTip;
	private int mResid;

	public CustomProgressDialog(Context context, String loadingText, int id) {
		super(context);
		this.context = context;
		this.loadingText = loadingText;
		this.mResid = id;
		setCanceledOnTouchOutside(false);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	private void initView() {
		setContentView(R.layout.progress_dialog);
		loading_textview = (TextView) findViewById(R.id.loading_textview);
		loading_imageview = (ImageView) findViewById(R.id.loading_imageview);
	}

	private void initData() {
		loading_imageview.setBackgroundResource(mResid);
		// ͨ��ImageView�����õ�������ʾ��AnimationDrawable
		animationDrawable = (AnimationDrawable) loading_imageview
				.getBackground();
		// Ϊ�˷�ֹ��onCreate������ֻ��ʾ��һ֡�Ľ������֮һ
		loading_imageview.post(new Runnable() {
			public void run() {
				animationDrawable.start();
			}
		});
		loading_textview.setText(loadingText);

	}

	public void setContent(String str) {
		loading_textview.setText(str);
	}
}
