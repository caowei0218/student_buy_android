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
import com.example.student_buy_android.bean.FriendBean;
import com.example.student_buy_android.bean.Message;
import com.example.student_buy_android.bean.Message.Type;

public class MessageDao {
	private String username = MyApplication.getInstance()
			.getSharedPreferences("user", Context.MODE_PRIVATE)
			.getString("username", "");// 用户ID

	/**
	 * 将消息存到数据库
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
	 * 获取本地聊天内容
	 * */
	public List<Message> getMessage(String sender, String receiver) {
		// update_receive_status(send);// 消息改为已读
		List<Message> list = new ArrayList<Message>();
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String sql = "select * from (select * from chat_content where sender=? and receiver=? and myaccount=? union all select * from chat_content where sender=? and receiver=? and myaccount=?)as temp order by temp.id";
		String[] args = { sender, receiver, username, receiver, sender,
				username };
		cursor = db.rawQuery(sql, args);
		while (cursor.moveToNext()) {
			Message message = new Message();
			message.setSender(cursor.getString(1));
			message.setReceiver(cursor.getString(2));
			message.setMsg(cursor.getString(3));
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
	 * 获取最近联系人
	 */
	public List<String> get_communication_last() {
		List<String> list = new ArrayList<String>();
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String sql = "SELECT sender FROM chat_content WHERE sender<>? and myaccount=? UNION SELECT receiver FROM chat_content WHERE receiver<>? and myaccount=?";
		String[] args = { username, username, username, username };
		cursor = db.rawQuery(sql, args);
		while (cursor.moveToNext()) {
			list.add(cursor.getString(0));
		}
		if (db != null) {
			cursor.close();
			db.close();
		}
		return list;
	}

	/**
	 * 获取与该用户最后一条聊天内容
	 */
	public String get_last_message(String send, String receive_id) {
		String message = "";
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String sql = "select * from (select * from chat_content where sender=? and receiver=? union select * from chat_content where sender=? and receiver=?) as temp where myaccount=? order by temp.id";
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
	 * 保存好友列表到本地
	 */
	public void save_friend_list(List<FriendBean> list) {
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		db.execSQL("delete from friends");
		for (int i = 0; i < list.size(); i++) {
			ContentValues content_value = new ContentValues();
			content_value.put("username", list.get(i).getUsername());
			content_value.put("nickname", list.get(i).getNickname());
			content_value.put("email", list.get(i).getEmail());
			content_value.put("description", list.get(i).getDescription());
			content_value.put("address", list.get(i).getAddress());
			content_value.put("city", list.get(i).getCity());
			content_value.put("gender", list.get(i).getGender());
			content_value.put("phoneNumber", list.get(i).getPhoneNumber());
			content_value.put("alias", list.get(i).getAlias());
			db.insert("friends", null, content_value);
		}
		if (db != null) {
			db.close();
		}
	}

	/**
	 * 获取本地好友列表
	 */
	public List<FriendBean> get_friend_list() {
		List<FriendBean> list = new ArrayList<FriendBean>();
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String sql = "select * from friends";
		cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			FriendBean friendBean = new FriendBean();
			friendBean.setUsername(cursor.getString(1));
			friendBean.setNickname(cursor.getString(2));
			friendBean.setEmail(cursor.getString(3));
			friendBean.setDescription(cursor.getString(4));
			friendBean.setAddress(cursor.getString(5));
			friendBean.setCity(cursor.getString(6));
			friendBean.setGender(cursor.getString(7));
			friendBean.setPhoneNumber(cursor.getString(8));
			friendBean.setAlias(cursor.getString(9));
			list.add(friendBean);
		}
		if (db != null) {
			cursor.close();
			db.close();
		}
		return list;
	}

	/**
	 * 获取本地所有未读消息个数
	 * */
	public int get_unread_message_count() {
		int i = 0;
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String sql = "select count(*) from chat_content where receiver=? and myaccount=? and isread='no'";
		String[] args = { username, username };
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
	 * 获取本地某个好友未读消息个数
	 * */
	public int get_personal_unread_message_count(String friendUsername) {
		int i = 0;
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String sql = "select count(*) from chat_content where sender=? and receiver=? and myaccount=? and isread='no'";
		String[] args = { friendUsername, username, username };
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
	 * 更改发送消息状态
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
	 * 更改接收消息状态为已读
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
	 * 获取某个人未读消息个数
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
	 * 根据ID查询真实姓名
	 */
	public String get_user_name_by_id(String user_id) {
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String user_name = "";
		String sql = "select user_name from friend_temp where user_id='"
				+ user_id + "' and who_am_i='" + username + "'";// 查询是否存在
		cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			user_name = cursor.getString(0);
		}
		return user_name;
	}

	/**
	 * 保存最近联系人
	 */
	public void save_communication_last(String user_id, String user_name) {
		DBHelper db_helper = new DBHelper(MyApplication.getInstance());
		SQLiteDatabase db = db_helper.getWritableDatabase();
		Cursor cursor = null;
		String i = "";
		if ("service_provider".equals(user_id)) {

		} else {
			String sql = "select count(*) from contact_last where user_id='"
					+ user_id + "' and who_am_i='" + username + "'";// 查询是否存在
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
				db.insert("contact_last", null, content_value);// 如果表总不存在插入一条
			}
		}
	}

	/**
	 * 更新本地好友列表
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
