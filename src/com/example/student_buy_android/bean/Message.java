package com.example.student_buy_android.bean;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Message implements Serializable {

	private String msg;// 消息内容
	private Type type;// 消息类型 枚举
	private String uid;// 发送人
	private Date date;// 发送日期

	public enum Type {
		INPUT, OUTPUT
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
