package com.example.student_buy_android.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.student_buy_android.MyApplication;
import com.example.student_buy_android.bean.ContactLast;
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.bean.Message;
import com.example.student_buy_android.bean.Message.Type;

public class MessageDao {
	private String username = MyApplication.getInstance()
			.getSharedPreferences("user", Context.MODE_PRIVATE)
			.getString("username", "");// �û�ID

	/**
	 * ����Ϣ�浽���ݿ�
	 */
	public void saveMessage(Message message) {
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		ContentValues content_value = new ContentValues();
		content_value.put("sender", message.getSender());
		content_value.put("receiver", message.getReceiver());
		content_value.put("message", message.getMsg());
		if (message.getDate() == null) {
			content_value.put("save_time", new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss").format(new Date()));
		}
		content_value.put("isread", "no");
		content_value.put("myaccount", username);
		db.insert("chat_content", null, content_value);
		if (db != null) {
			db.close();
		}
	}

	/**
	 * ��ȡ������������
	 * */
	public List<Message> getMessage(String sender, String receiver) {
		// update_receive_status(send);// ��Ϣ��Ϊ�Ѷ�
		List<Message> list = new ArrayList<Message>();
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String sql = "select * from (select * from chat_content where sender=? and receiver=? and myaccount=? union all select * from chat_content where sender=? and receiver=? and myaccount=?)as temp order by temp.id";
		String[] args = { sender, receiver, username, receiver, sender, username };
		cursor = db.rawQuery(sql, args);
		while (cursor.moveToNext()) {
			Message message = new Message();
			message.setSender(cursor.getString(1));
			message.setReceiver(cursor.getString(2));
			message.setMsg(cursor.getString(3));
			// message.setDate(cursor.getString(4));
			message.setType(username.equals(cursor.getString(1)) ? Type.OUTPUT
					: Type.INPUT);
			list.add(message);
		}
		if (db != null) {
			cursor.close();
			db.close();
		}
		return list;
	}

	/**
	 * ��ȡ�����ϵ��
	 */
	public List<FriendBean> get_communication_last() {
		List<FriendBean> list = new ArrayList<FriendBean>();
		FriendBean friendBean;
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String sql = "SELECT sender FROM chat_content WHERE sender<>? and myaccount=? UNION SELECT receiver FROM chat_content WHERE receiver<>? and myaccount=?";
		String[] args = { username, username, username, username };
		cursor = db.rawQuery(sql, args);
		while (cursor.moveToNext()) {
			friendBean = new FriendBean();
			friendBean.setUsername(cursor.getString(0));
			list.add(friendBean);
		}
		return list;
	}

	/**
	 * ��ȡ����û����һ����������
	 */
	public String get_last_message(String send, String receive_id) {
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String message = null;
		String sql = "select * from (select id,sender,receiver,message,save_time,isLeft,who_am_i,send_status from chat_content where sender=? and receiver=? union select id,sender,receiver,message,save_time,isLeft,who_am_i,send_status from chat_content where sender=? and receiver=?) as temp where who_am_i=? order by temp.id";
		String[] args = { send, receive_id, receive_id, send, username };
		cursor = db.rawQuery(sql, args);
		while (cursor.moveToNext()) {
			message = cursor.getString(3);
		}
		if (db != null) {
			cursor.close();
			db.close();
		}
		return message;
	}

	
	/**
	 * ���ķ�����Ϣ״̬
	 */
	public void update_send_status(String user_id_A, String user_id_B,
			String content, String read_or_not, String isLeft,
			String save_time, String send_status) {
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("send_status", send_status);
		String where_clause = "sender=? and receiver=? and message=? and isLeft=? and save_time=? and who_am_i=? and send_status=?";
		String[] where_args = { user_id_A, user_id_B, content, isLeft,
				save_time, username, "failed" };
		db.update("chat_content", cv, where_clause, where_args);
		if (db != null) {
			db.close();
		}
	}

	/**
	 * ���Ľ�����Ϣ״̬Ϊ�Ѷ�
	 */
	public void update_receive_status(String user_id_A) {
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("read_or_not", "1");
		String where_clause = "sender=? and receiver=? and who_am_i=?";
		String[] where_args = { user_id_A, username, username };
		db.update("chat_content", cv, where_clause, where_args);
		if (db != null) {
			db.close();
		}
	}

	/**
	 * ��ȡ����δ����Ϣ����
	 * */
	public int get_unread_message_count() {
		int i = 0;
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String sql = "select count(*) from chat_content where receiver=? and who_am_i=? and read_or_not=?";
		String[] args = { username, username, "0" };
		cursor = db.rawQuery(sql, args);
		while (cursor.moveToNext()) {
			i = cursor.getInt(0);
		}
		if (db != null) {
			cursor.close();
			db.close();
		}
		return i;
	}

	/**
	 * ��ȡĳ����δ����Ϣ����
	 */
	public int get_unread_message_count_one(String user_id) {
		int i = 0;
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String sql = "select count(*) from chat_content where sender=? and receiver=? and who_am_i=? and read_or_not=?";
		String[] args = { user_id, username, username, "0" };
		cursor = db.rawQuery(sql, args);
		while (cursor.moveToNext()) {
			i = cursor.getInt(0);
		}
		if (db != null) {
			cursor.close();
			db.close();
		}
		return i;
	}

	/**
	 * ����ID��ѯ��ʵ����
	 */
	public String get_user_name_by_id(String user_id) {
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String user_name = "";
		String sql = "select user_name from friend_temp where user_id='"
				+ user_id + "' and who_am_i='" + username + "'";// ��ѯ�Ƿ����
		cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			user_name = cursor.getString(0);
		}
		return user_name;
	}

	/**
	 * ���������ϵ��
	 */
	public void save_communication_last(String user_id, String user_name) {
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String i = "";
		if ("service_provider".equals(user_id)) {

		} else {
			String sql = "select count(*) from contact_last where user_id='"
					+ user_id + "' and who_am_i='" + username + "'";// ��ѯ�Ƿ����
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				i = cursor.getString(0);
			}
			if ("0".equals(i)) {
				ContentValues content_value = new ContentValues();
				content_value.put("user_id", user_id);
				if (user_name == null) {
					user_name = get_user_name_by_id(user_id);
				}
				content_value.put("user_name", user_name);
				content_value.put("who_am_i", username);
				db.insert("contact_last", null, content_value);// ������ܲ����ڲ���һ��
			}
		}
	}

	/**
	 * ��ȡ���غ����б�
	 */
	public List<ContactLast> get_friend_list() {
		List<ContactLast> list = new ArrayList<ContactLast>();
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String sql = "select user_id,user_name from friend_temp where who_am_i ='"
				+ username
				+ "' union select user_id,user_name from contact_last where user_id='service_provider'";
		cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			ContactLast contact_last = new ContactLast();
			contact_last.setUser_id(cursor.getString(0));
			contact_last.setUser_name(cursor.getString(1));
			list.add(contact_last);
		}
		return list;
	}

	/**
	 * ��������б����غ�����ʱ��
	 */
	public void save_friend_list(List<ContactLast> list) {
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		db.execSQL("delete from friend_temp");
		System.out.println("���ѱ������" + "---��ǰ��������" + list.size());
		for (int i = 0; i < list.size(); i++) {
			ContentValues content_value = new ContentValues();
			content_value.put("user_name", list.get(i).getUser_name());
			content_value.put("user_id", list.get(i).getUser_id());
			content_value.put("who_am_i", username);
			db.insert("friend_temp", null, content_value);
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * ���±��غ����б�
	 */
	public void update_friend_list() {
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		String sql = "merge into friend a using friend_temp b on a.user_id=b.user_id and a.who_am_i = b.who_am_i when matched then "
				+ "update set a.user_name=b.user_name when not macthed "
				+ "insert values(b.user_id,b.user_name,b.who_am_i)";
		db.execSQL(sql);
		if (db != null) {
			db.close();
		}
	}

	public String getId() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
