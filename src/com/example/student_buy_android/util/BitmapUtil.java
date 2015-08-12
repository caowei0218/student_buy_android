package com.example.student_buy_android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;

import com.cloopen.rest.sdk.utils.encoder.BASE64Decoder;
import com.cloopen.rest.sdk.utils.encoder.BASE64Encoder;

/**
 * Բ��ͼƬ
 * */
@SuppressLint("SdCardPath")
public class BitmapUtil {
	private static final int STROKE_WIDTH = 4;

	/**
	 * ��assets��Դ�л�ȡͼƬ
	 * */
	public static Bitmap getBitmap(Context context, String filename) {
		Bitmap image = null;
		AssetManager am = context.getResources().getAssets();
		try {
			InputStream is = am.open(filename);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	@SuppressWarnings("unused")
	public static Bitmap toRoundBitmap(Context context, String filename) {
		Bitmap bitmap = getBitmap(context, filename);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			left = 0;
			bottom = width;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(4);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);

		// ����ɫԲȦ
		paint.reset();
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(STROKE_WIDTH);
		paint.setAntiAlias(true);
		canvas.drawCircle(width / 2, width / 2, width / 2 - STROKE_WIDTH / 2,
				paint);
		return output;
	}

	/**
	 * Base64���벢�����ַ���
	 */
	public static String image2String(File imageFile) {
		InputStream in = null;
		byte[] data = null;
		try {
			// ��ȡͼƬ�ֽ�����
			in = new FileInputStream(imageFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();

			// ���ֽ�����Base64����
			BASE64Encoder encoder = new BASE64Encoder();
			String imageStringData = encoder.encode(data);
			return imageStringData;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Base64���벢����ͼƬ
	 */
	public static void string2Image(String imageStringData, String destFile) {
		try {
			// �����쳣����
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b = decoder.decodeBuffer(imageStringData);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}

			// ����ͼƬ
			OutputStream out = new FileOutputStream(destFile);
			out.write(b);
			out.flush();
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ����ͼƬ������
	 * */
	public static void saveBitmapFile(Bitmap bitmap, String phontName) {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
			return;
		}
		FileOutputStream fileOutputStream = null;
		File file = new File("/sdcard/Excoo/");
		file.mkdirs();// �����ļ���
		String fileName = "/sdcard/Excoo/" + phontName;
		try {
			fileOutputStream = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);// ������д���ļ�
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fileOutputStream.flush();
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �ж�ͼƬ�Ƿ����
	 * */
	public static boolean isExists(String picName) {
		boolean boo = false;
		File file = new File("/sdcard/Excoo/" + picName);
		boo = file.exists();
		return boo;
	}

	/**
	 * ��ñ���ͼƬ
	 * */
	public static Bitmap getLocalBitmap(String picName) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("/sdcard/Excoo/" + picName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(fis);
		return bitmap;
	}

	/**
	 * ɾ������ͼƬ
	 * */
	public void delLocalBitmap(String picName) {
		File file = new File("/sdcard/Excoo/" + picName); // ����Ҫɾ�����ļ�λ��
		if (file.exists())
			file.delete();
	}

	/**
	 * ɾ���ļ����µ������ļ�
	 */
	public void delLocalAllBitmap(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				delLocalAllBitmap(f);
			}
		} else {
			file.delete();
		}
	}

}
