package com.example.student_buy_android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DataBase_NAME = "YUE.db";
	private static final int DataBase_Verision = 1;
	private static final String MESSAGE_TABLE = "create table chat_content(id integer primary key autoincrement,sender,receiver,message,save_time,isread)";// ���������������
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
		System.out.println("��һ�ΰ�װ��APP ������");
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
		System.out.println("׼����������");
		// �����ϵ�˱��������
		// db.execSQL("insert into contact_last(user_id,user_name,who_am_i) values('service_provider','OCCS�ͷ�','')");
		// // software�������
		// db.execSQL("insert into software(switch,status) values('messages_notice', 1)");
		// db.execSQL("insert into software(switch,status) values('quit_notice', 1)");
		// db.execSQL("insert into software(switch,status) values('light', 1)");
		// db.execSQL("insert into software(switch,status) values('lock_screen_pop', 1)");
		// db.execSQL("insert into software(switch,status) values('occs_icon', 1)");
		// db.execSQL("insert into software(switch,status) values('messages_details', 1)");
		// db.execSQL("insert into software(switch,status) values('sound', 1)");
		// db.execSQL("insert into software(switch,status) values('shock', 1)");
		// // preferences�������
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
		System.out.println("�������ݳɹ�");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("�������ݿ�");
		// String delete_table = "drop table chat_content";
		// db.execSQL(delete_table);//ɾ�����ڵı�
		// db.execSQL("drop table person");//ɾ�����ڵı�
		// db.execSQL("drop table software");//ɾ�����ڵı�
		// db.execSQL(create_message);//���´�����
	}

}