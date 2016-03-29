package com.android.tongxunlu.entity;

/**
 *封装一条短信
 */
public class Sms {
	private int id;
	private int photoId;
	private long date;
	private String body;  //短信的内容
	private int type;
	private String number;

	public Sms() {
		// TODO Auto-generated constructor stub
	}

	public Sms(int id, int photoId, long date, String body, int type) {
		super();
		this.id = id;
		this.photoId = photoId;
		this.date = date;
		this.body = body;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	

	
	public String getNumber(){
		return number;
	}
	
	public void setNumber(String number){
		this.number=number;
	}

}
