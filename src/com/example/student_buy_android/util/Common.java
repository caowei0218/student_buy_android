package com.example.student_buy_android.util;

import java.util.List;

import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.bean.UserBean;

public class Common {
	public static UserBean userBean;
	public static List<FriendBean> friendBeans;

	public static boolean share = false;
	public static String SESSIONID;// ����һ����̬���ֶΣ�����sessionID

	public static String URL = "http://10.86.255.70:8080/ExcooServerSoa/";

	// //������������
	// public static final String[] IMAGES = new String[] {
	// "http://60.20.141.202/caowei/images/big_img1.jpg",
	// "http://60.20.141.202/caowei/images/big_img2.jpg",
	// "http://60.20.141.202/caowei/images/big_img3.jpg",
	// "http://60.20.141.202/caowei/images/big_img4.jpg",
	// "http://60.20.141.202/caowei/images/big_img5.jpg",
	// "http://60.20.141.202/caowei/images/big_img6.jpg",
	// "http://60.20.141.202/caowei/images/big_img7.jpg",
	// "http://60.20.141.202/caowei/images/big_img8.jpg",
	// "http://60.20.141.202/caowei/images/big_img9.jpg",
	// "http://60.20.141.202/caowei/images/img1.png",
	// "http://60.20.141.202/caowei/images/img2.png",
	// "http://60.20.141.202/caowei/images/img3.png" };

	// ��˾��������
	public static final String IMAGES_URL = "http://10.86.255.26:8080/caowei/images/";

	public static final String[] photoName = new String[] { "big_img1.jpg",
			"big_img2.jpg", "big_img3.jpg", "big_img4.jpg", "big_img5.jpg",
			"big_img6.jpg", "big_img7.jpg", "big_img8.jpg", "big_img9.jpg",
			"img1.png", "img2.png", "img3.png" };
}
