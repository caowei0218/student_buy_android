package com.example.student_buy_android.util;

import java.util.List;

import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.bean.UserBean;

public class Common {
	public static UserBean userBean;
	public static List<FriendBean> friendBeans;

	public static boolean share = false;
	public static String SESSIONID;// 定义一个静态的字段，保存sessionID

	// //家里外网环境
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

	// 公司内网环境
	public static final String[] IMAGES = new String[] {
			"http://10.86.255.26:8080/caowei/images/big_img1.jpg",
			"http://10.86.255.26:8080/caowei/images/big_img2.jpg",
			"http://10.86.255.26:8080/caowei/images/big_img3.jpg",
			"http://10.86.255.26:8080/caowei/images/big_img4.jpg",
			"http://10.86.255.26:8080/caowei/images/big_img5.jpg",
			"http://10.86.255.26:8080/caowei/images/big_img6.jpg",
			"http://10.86.255.26:8080/caowei/images/big_img7.jpg",
			"http://10.86.255.26:8080/caowei/images/big_img8.jpg",
			"http://10.86.255.26:8080/caowei/images/big_img9.jpg",
			"http://10.86.255.26:8080/caowei/images/img1.png",
			"http://10.86.255.26:8080/caowei/images/img2.png",
			"http://10.86.255.26:8080/caowei/images/img3.png" };
}
