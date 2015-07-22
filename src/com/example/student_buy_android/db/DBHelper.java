package com.example.student_buy_android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DataBase_NAME = "YUE.db";
	private static final int DataBase_Verision = 1;
	private static final String MESSAGE_TABLE = "create table chat_content(id integer primary key autoincrement,sender,receiver,message,save_time,isread)";// 用来存放聊天内容
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
		System.out.println("第一次安装该APP 创建表");
		db.execSQL(MESSAGE_TABLE);
//		db.execSQL(create_person);
//		db.execSQL(create_contact_last);
//		db.execSQL(create_set_software);
//		db.execSQL(create_preferences);
//		db.execSQL(create_save_head_portrait);
//		db.execSQL(create_friend);
//		db.execSQL(create_friend_temp);
//		db.execSQL(CREATE_FIRST_DEFAULT_APP);
//		db.execSQL(CREATE_SKILL_IMG);
		System.out.println("准备插入数据");
		// 最近联系人表插入数据
		// db.execSQL("insert into contact_last(user_id,user_name,who_am_i) values('service_provider','OCCS客服','')");
		// // software表插数据
		// db.execSQL("insert into software(switch,status) values('messages_notice', 1)");
		// db.execSQL("insert into software(switch,status) values('quit_notice', 1)");
		// db.execSQL("insert into software(switch,status) values('light', 1)");
		// db.execSQL("insert into software(switch,status) values('lock_screen_pop', 1)");
		// db.execSQL("insert into software(switch,status) values('occs_icon', 1)");
		// db.execSQL("insert into software(switch,status) values('messages_details', 1)");
		// db.execSQL("insert into software(switch,status) values('sound', 1)");
		// db.execSQL("insert into software(switch,status) values('shock', 1)");
		// // preferences表插数据
		// db.execSQL("insert into preferences(switch,status) values('ProjectSolutions', 1)");
		// db.execSQL("insert into preferences(switch,status) values('AssessmentProjectReport', 1)");
		// db.execSQL("insert into preferences(switch,status) values('BusinessRequirementsAnalysis', 1)");
		// db.execSQL("insert into preferences(switch,status) values('SystemDesign', 0)");
		// db.execSQL("insert into preferences(switch,status) values('PageFunctionTopology', 0)");
		// db.execSQL("insert into preferences(switch,status) values('PageConfigurationTopology', 0)");
		// db.execSQL("insert into preferences(switch,status) values('ComponentProduction', 0)");
		// db.execSQL("insert into preferences(switch,status) values('UnitTesting', 0)");
		// db.execSQL("insert into preferences(switch,status) values('ProjectImplementation', 0)");
		// db.execSQL("insert into preferences(switch,status) values('OperationAndMaintenance', 0)");
		// db.execSQL("insert into preferences(switch,status) values('Business', 0)");
		System.out.println("插入数据成功");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("升级数据库");
		// String delete_table = "drop table chat_content";
		// db.execSQL(delete_table);//删除存在的表
		// db.execSQL("drop table person");//删除存在的表
		// db.execSQL("drop table software");//删除存在的表
		// db.execSQL(create_message);//重新创建表
	}

}