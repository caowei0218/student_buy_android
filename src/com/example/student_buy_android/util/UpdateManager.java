package com.example.student_buy_android.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.example.student_buy_android.R;

public class UpdateManager {

	private Context mContext;

	// 提示语
	private String updateMsg = "有最新的软件包哦，亲快下载吧~";

	/** 返回的安装包url */
	private String apkUrl;

	private String fileName;

	private Dialog noticeDialog;

	private Dialog downloadDialog;

	/** 下载包安装路径 */
	@SuppressLint("SdCardPath")
	private static final String savePath = "/sdcard/StudentBuy/";

	/** 进度条与通知ui刷新的handler和msg常量 */
	private ProgressBar mProgress;

	private static final int DOWN_UPDATE = 1;

	private static final int DOWN_OVER = 2;

	private int progress;

	private Thread downLoadThread;

	private boolean interceptFlag = false;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:
				installApk();// 安装
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context, String apkUrl, String fileName) {
		this.mContext = context;
		this.apkUrl = apkUrl;
		this.fileName = fileName;
	}

	// 外部接口让主Activity调用
	public void checkUpdateInfo() {
		showNoticeDialog();
	}

	/**
	 * 下载确认弹窗
	 * */
	private void showNoticeDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");
		builder.setMessage(updateMsg);
		builder.setPositiveButton("下载", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		noticeDialog = builder.create();
		noticeDialog.setCancelable(false);
		noticeDialog.show();
	}

	/**
	 * 下载进度窗
	 * */
	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");

		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);

		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCancelable(false);
		downloadDialog.show();
		downloadApk();
	}

	/**
	 * 下载APK线程
	 * */
	private Runnable mdownApkRunnable = new Runnable() {
		public void run() {
			try {
				URL url = new URL(apkUrl);

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				// String sdStatus = Environment.getExternalStorageState();
				// if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { //
				// 检测sd是否可用
				// Log.i("TestFile",
				// "SD card is not avaiable/writeable right now.");
				// return;
				// }

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				File ApkFile = new File(getFileName());
				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						// 下载完成通知安装
						mHandler.sendEmptyMessage(DOWN_OVER);
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下载.
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 下载apk
	 */
	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 安装apk
	 */
	private void installApk() {
		File apkfile = new File(getFileName());
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}

	/**
	 * 获得文件全名
	 * */
	private String getFileName() {
		return savePath + fileName;
	}
}