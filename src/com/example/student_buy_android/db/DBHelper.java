package com.example.student_buy_android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DataBase_NAME = "YUE.db";
	private static final int DataBase_Verision = 1;
	private static final String MESSAGE_TABLE = "create table chat_content(id integer primary key autoincrement,sender,receiver,message,save_time,isread,myaccount)";// ���������������
	private static final String FRIENDS = "create table friends(id integer primary key autoincrement,username,nickname,email,description,address,city,gender,phoneNumber,alias,myaccount,photoName)";// ������ź����б�
	private static final String create_person = "create table person(name primary key,password)";// ������
	private static final String create_contact_last = "create table contact_last(user_id ,user_name,who_am_i, primary key(user_id,who_am_i) )";// ���������ϵ�˱�
	private static final String create_friend = "create table friend(user_id ,user_name,who_am_i, primary key(user_id,who_am_i) )";// ���������ϵ�˱����ʱ��
	private static final String create_friend_temp = "create table friend_temp(user_id ,user_name, who_am_i ,primary key(user_id,who_am_i) )";// ���������ϵ�˱����ʱ��
	private static final String create_set_software = "create table software(switch, status)";// ������
																								// ����������������Ϣ
	private static final String create_preferences = "create table preferences(switch, status)";// ������
																								// ����������������Ϣ
	private static final String create_save_head_portrait = "create table portrait"
			+ "(userName varchar(40) primary key," + "imgStr varchar(8000))";// ��������ͼƬ��Ϣ�����ݱ�
	// �����洢��ҳĬ��Ӧ����ʽ��
	private static final String CREATE_FIRST_DEFAULT_APP = "create table defaultApp(userName varchar(40),"
			+ "appName varchar(80),appImg varchar(80),activity varchar(80),"
			+ "appDelImg varchar(80),"
			+ "constraint PK_APP primary key(userName,appName))";

	// ��������ҳ����û��߱����ܵ�ͼ��������Ϣ
	private static final String CREATE_SKILL_IMG = "create table skill(userName varchar(40),"
			+ "skillName varchar(80),skillImg varchar(80),"
			+ "constraint PK_SKILL primary key(userName,skillName))";

	public DBHelper(Context context) {
		super(context, DataBase_NAME, null, DataBase_Verision);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// ��һ�ΰ�װ��APP ������
		db.execSQL(MESSAGE_TABLE);
		db.execSQL(FRIENDS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// �������ݿ�
	}

}