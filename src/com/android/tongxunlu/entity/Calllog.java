package com.android.tongxunlu.entity;

/**
 * 封装一条通话记录
 */
public class Calllog {
	private int id;
	private int photoId;   //联系人头像的id
	private String name;
	private String number;
	private int type;
	private long date;

	public Calllog() {
		// TODO Auto-generated constructor stub
	}

	public Calllog(int id, int photoId, String name, String number, int type,long date) {
		super();
		this.id = id;
		this.photoId = photoId;
		this.name = name;
		this.number = number;
		this.type = type;
		this.date = date;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

}
