package com.example.student_buy_android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DataBase_NAME = "YUE.db";
	private static final int DataBase_Verision = 1;
	private static final String MESSAGE_TABLE = "create table chat_content(id integer primary key autoincrement,sender,receiver,message,save_time,isread,myaccount)";// 用来存放聊天内容
	private static final String FRIENDS = "create table friends(id integer primary key autoincrement,username,nickname,email,description,address,city,gender,phoneNumber,alias,myaccount,photoName)";// 用来存放好友列表
	private static final String create_person = "create table person(name primary key,password)";// 创建表
	private static final String create_contact_last = "create table contact_last(user_id ,user_name,who_am_i, primary key(user_id,who_am_i) )";// 创建最近联系人表
	private static final String create_friend = "create table friend(user_id ,user_name,who_am_i, primary key(user_id,who_am_i) )";// 创建最近联系人表的临时表
	private static final String create_friend_temp = "create table friend_temp(user_id ,user_name, who_am_i ,primary key(user_id,who_am_i) )";// 创建最近联系人表的临时表
	private static final String create_set_software = "create table software(switch, status)";// 创建表
																								// 用来存放软件设置信息
	private static final String create_preferences = "create table preferences(switch, status)";// 创建表
																								// 用来存放软件设置信息
	private static final String create_save_head_portrait = "create table portrait"
			+ "(userName varchar(40) primary key," + "imgStr varchar(8000))";// 创建保存图片信息的数据表
	// 创建存储首页默认应用正式表
	private static final String CREATE_FIRST_DEFAULT_APP = "create table defaultApp(userName varchar(40),"
			+ "appName varchar(80),appImg varchar(80),activity varchar(80),"
			+ "appDelImg varchar(80),"
			+ "constraint PK_APP primary key(userName,appName))";

	// 创建设置页面该用户具备技能的图标的相关信息
	private static final String CREATE_SKILL_IMG = "create table skill(userName varchar(40),"
			+ "skillName varchar(80),skillImg varchar(80),"
			+ "constraint PK_SKILL primary key(userName,skillName))";

	public DBHelper(Context context) {
		super(context, DataBase_NAME, null, DataBase_Verision);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 第一次安装该APP 创建表
		db.execSQL(MESSAGE_TABLE);
		db.execSQL(FRIENDS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 升级数据库
	}

}